package com.rareshop.api.rest.listing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rareshop.api.rest.listing.constant.APIMapping;
import com.rareshop.api.rest.listing.constant.InfoMessage;
import com.rareshop.api.rest.listing.model.BasicPricingRule;
import com.rareshop.api.rest.listing.model.BasicPricingRuleData;
import com.rareshop.api.rest.listing.model.BasicProduct;
import com.rareshop.api.rest.listing.model.BasicProductData;
import com.rareshop.api.rest.listing.service.ProductService;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import rareshop.api.common.core.constant.Params;
import rareshop.api.common.core.exception.BadRequestException;
import rareshop.api.common.core.exception.NotFoundException;
import rareshop.api.common.core.model.Acknowledgement;
import rareshop.api.common.core.model.RareResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<Boolean> publishedCaptor;

    @Captor
    private ArgumentCaptor<BasicProduct> productCaptor;

    @Captor
    private ArgumentCaptor<BasicProductData> productDataCaptor;

    @Captor
    private ArgumentCaptor<BasicPricingRule> pricingRuleCaptor;

    @Captor
    private ArgumentCaptor<BasicPricingRuleData> pricingRuleDataCaptor;

    @AfterEach
    private void afterEach() {
        reset(productService);
    }

    @Nested
    @DisplayName("Adding Product")
    class AddProduct {
        @Test
        @DisplayName("When [post] to /products with correct body , Product should be added")
        public void testAddProductWithCorrectBody() throws Exception {
            Map<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, 1);
            Acknowledgement acknowledgement =
                    new Acknowledgement(InfoMessage.PRODUCT_CREATED, payload);

            Mockito.when(productService.addProduct(any(BasicProductData.class)))
                    .thenReturn(acknowledgement);

            BasicProductData basicProductData = new BasicProductData();

            ResultActions resultActions = mockMvc.perform(post(APIMapping.PRODUCT_MAPPING)
                    .content(objectMapper.writeValueAsString(basicProductData))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(productService).addProduct(productDataCaptor.capture());

            Assertions.assertEquals(basicProductData, productDataCaptor.getValue());

            resultActions.andExpect(status().isCreated())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(acknowledgement))));

        }

        @Test
        @DisplayName("When [post] to /products with incorrect body, should get bad request error:400")
        public void testAddProductWithIncorrectBody() throws Exception {

            String errorMessage = "invalid property";
            Mockito.when(productService.addProduct(any(BasicProductData.class)))
                    .thenThrow(new BadRequestException(errorMessage));

            BasicProductData basicProductDataWithErrors = new BasicProductData();

            ResultActions resultActions = mockMvc.perform(post(APIMapping.PRODUCT_MAPPING)
                    .content(objectMapper.writeValueAsString(basicProductDataWithErrors))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(productService).addProduct(productDataCaptor.capture());

            Assertions.assertEquals(basicProductDataWithErrors, productDataCaptor.getValue());

            resultActions.andExpect(status().isBadRequest())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));

        }
    }

    @Nested
    @DisplayName("Retrieving Product")
    class RetrieveProduct {
        @Test
        @DisplayName("When [get] to /product , all Product objects should be retrieved")
        public void testGetAllProductInfo() throws Exception {

            Mockito.when(productService.getAllProducts())
                    .thenReturn(Collections.emptyList());

            ResultActions resultActions = mockMvc.perform(get(APIMapping.PRODUCT_MAPPING));

            Mockito.verify(productService).getAllProducts();

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(Collections.emptyList()))));
        }

        @Test
        @DisplayName("When [get] to /products/{product-id} with existing id, " +
                "matched Product object should be retrieved")
        public void testGetSingleProductInfoWithExistingId() throws Exception {

            long existingId = 1L;
            BasicProduct basicProduct = new BasicProduct();
            basicProduct.setId(existingId);

            Mockito.when(productService.getProduct(anyLong()))
                    .thenReturn(basicProduct);

            String url = APIMapping.PRODUCT_MAPPING + APIMapping.SINGLE_PRODUCT_MAPPING;

            ResultActions resultActions = mockMvc.perform(get(url, existingId));

            Mockito.verify(productService).getProduct(idCaptor.capture());

            Assertions.assertEquals(existingId, idCaptor.getValue());

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(basicProduct))));

        }

        @Test
        @DisplayName("When [get] to /products/{product-id} with non-existing id, " +
                "should get not found error:404")
        public void testGetSingleProductInfoWithNonExistingId() throws Exception {

            String errorMessage = "item not found";
            Mockito.when(productService.getProduct(anyLong()))
                    .thenThrow(new NotFoundException(errorMessage));

            long nonExistingId = 0L;
            String url = APIMapping.PRODUCT_MAPPING + APIMapping.SINGLE_PRODUCT_MAPPING;

            ResultActions resultActions = mockMvc.perform(get(url, nonExistingId));

            Mockito.verify(productService).getProduct(idCaptor.capture());

            Assertions.assertEquals(nonExistingId, idCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));

        }
    }

    @Nested
    @DisplayName("Deleting Product")
    class DeleteProduct {

        @Test
        @DisplayName("When [delete] to /products/{product-id} with non existing id," +
                " should get not found error:404")
        public void testDeleteProductWithNonExistingId() throws Exception {
            String errorMessage = "item not found";
            Mockito.when(productService.deleteProduct(anyLong()))
                    .thenThrow(new NotFoundException(errorMessage));

            long nonExistingId = 0L;
            String url = APIMapping.PRODUCT_MAPPING + APIMapping.SINGLE_PRODUCT_MAPPING;

            ResultActions resultActions = mockMvc.perform(delete(url, nonExistingId));

            Mockito.verify(productService).deleteProduct(idCaptor.capture());

            Assertions.assertEquals(nonExistingId, idCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));


        }

        @Test
        @DisplayName("When [delete] to /products/{product-id} with existing id, Product should be deleted")
        public void testDeleteProductWithExistingId() throws Exception {
            long id = 1L;
            Map<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, id);
            Acknowledgement acknowledgement =
                    new Acknowledgement(InfoMessage.PRODUCT_DELETED, payload);

            Mockito.when(productService.deleteProduct(anyLong()))
                    .thenReturn(acknowledgement);

            String url = APIMapping.PRODUCT_MAPPING + APIMapping.SINGLE_PRODUCT_MAPPING;
            ResultActions resultActions = mockMvc.perform(delete(url, id));

            Mockito.verify(productService).deleteProduct(idCaptor.capture());

            Assertions.assertEquals(id, idCaptor.getValue());

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(acknowledgement))));

        }
    }

    @Nested
    @DisplayName("Publishing Product")
    class PublishProduct {

        @Test
        @DisplayName("When [patch] to /products/{product-id}?published=true with non existing id, " +
                "should get not found error:404")
        public void testPublishProductWithNonExistingId() throws Exception {

            boolean published = true;
            String errorMessage = "item not found";
            Mockito.when(productService.publishProduct(anyLong(), anyBoolean()))
                    .thenThrow(new NotFoundException(errorMessage));

            long nonExistingId = 0L;
            String url = APIMapping.PRODUCT_MAPPING + APIMapping.SINGLE_PRODUCT_MAPPING;

            ResultActions resultActions = mockMvc.perform(patch(url, nonExistingId)
                    .param(APIMapping.PRODUCT_PUBLISHED_PARAM, published ? "true" : "false"));

            Mockito.verify(productService).publishProduct(idCaptor.capture(), publishedCaptor.capture());

            Assertions.assertEquals(nonExistingId, idCaptor.getValue());
            Assertions.assertEquals(published, publishedCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));

        }

        @Test
        @DisplayName("When [patch] to /products/{product-id}?published=true , Product should be published")
        public void testPublishProductWithExistingId() throws Exception {
            long id = 1L;
            boolean published = true;
            Map<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, id);
            Acknowledgement acknowledgement =
                    new Acknowledgement(
                            published ? InfoMessage.PRODUCT_PUBLISHED : InfoMessage.PRODUCT_UNPUBLISHED, payload);

            Mockito.when(productService.publishProduct(anyLong(), anyBoolean()))
                    .thenReturn(acknowledgement);


            String url = APIMapping.PRODUCT_MAPPING + APIMapping.SINGLE_PRODUCT_MAPPING;
            ResultActions resultActions = mockMvc.perform(patch(url, id)
                    .param(APIMapping.PRODUCT_PUBLISHED_PARAM, published ? "true" : "false"));

            Mockito.verify(productService).publishProduct(idCaptor.capture(), publishedCaptor.capture());

            Assertions.assertEquals(id, idCaptor.getValue());
            Assertions.assertEquals(published, publishedCaptor.getValue());

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(acknowledgement))));
        }
    }

    @Nested
    @DisplayName("Adding Pricing Rules to Product")
    class AddPricingRules {

        @Test
        @DisplayName("When [post] to /products/{product-id}/pricing-rules to non existing Product ," +
                " should get not found error:404")
        public void testAddPricingRuleToNonExistingProduct() throws Exception {
            long nonExistingProductId = 0L;
            String errorMessage = "product not found";
            Mockito.when(productService.addPricingRule(anyLong(), any(BasicPricingRuleData.class)))
                    .thenThrow(new NotFoundException(errorMessage));

            BasicPricingRuleData basicPricingRuleData = new BasicPricingRuleData();

            ResultActions resultActions = mockMvc.perform(post(APIMapping.PRICING_RULE_OF_PRODUCT_MAPPING, nonExistingProductId)
                    .content(objectMapper.writeValueAsString(basicPricingRuleData))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(productService).addPricingRule(idCaptor.capture(), pricingRuleDataCaptor.capture());

            Assertions.assertEquals(nonExistingProductId, idCaptor.getValue());
            Assertions.assertEquals(basicPricingRuleData, pricingRuleDataCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));

        }

        @Test
        @DisplayName("When [post] to /products/{product-id}/pricing-rules with correct body," +
                " Producing Rule should be added")
        public void testAddPricingRuleWithCorrectBody() throws Exception {
            long existingProductId = 1L;
            Map<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, existingProductId);
            Acknowledgement acknowledgement =
                    new Acknowledgement(InfoMessage.PRICING_RULE_ADDED, payload);

            Mockito.when(productService.addPricingRule(anyLong(), any(BasicPricingRuleData.class)))
                    .thenReturn(acknowledgement);

            BasicPricingRuleData basicPricingRuleData = new BasicPricingRuleData();

            ResultActions resultActions = mockMvc.perform(post(APIMapping.PRICING_RULE_OF_PRODUCT_MAPPING, existingProductId)
                    .content(objectMapper.writeValueAsString(basicPricingRuleData))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(productService).addPricingRule(idCaptor.capture(), pricingRuleDataCaptor.capture());

            Assertions.assertEquals(existingProductId, idCaptor.getValue());
            Assertions.assertEquals(basicPricingRuleData, pricingRuleDataCaptor.getValue());

            resultActions.andExpect(status().isCreated())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(acknowledgement))));

        }

        @Test
        @DisplayName("When [post] to /products/{product-id}/pricing-rules with incorrect body," +
                " should get bad request error:400")
        public void testAddPricingRuleWithIncorrectBody() throws Exception {
            long existingProductId = 1L;
            String errorMessage = "invalid property";
            Mockito.when(productService.addPricingRule(anyLong(), any(BasicPricingRuleData.class)))
                    .thenThrow(new BadRequestException(errorMessage));

            BasicPricingRuleData basicPricingRuleDataWithErrors = new BasicPricingRuleData();

            ResultActions resultActions = mockMvc.perform(post(APIMapping.PRICING_RULE_OF_PRODUCT_MAPPING, existingProductId)
                    .content(objectMapper.writeValueAsString(basicPricingRuleDataWithErrors))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(productService).addPricingRule(idCaptor.capture(), pricingRuleDataCaptor.capture());

            Assertions.assertEquals(existingProductId, idCaptor.getValue());
            Assertions.assertEquals(basicPricingRuleDataWithErrors, pricingRuleDataCaptor.getValue());

            resultActions.andExpect(status().isBadRequest())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));

        }
    }

    @Nested
    @DisplayName("Retrieving Pricing Rules of Product")
    class RetrievePricingRules {
        @Test
        @DisplayName("When [get] to /product/{product-id}/pricing-rules with existing product id," +
                " all PricingRule objects of Product should be retrieved")
        public void testGetAllPricingRulesOfExistingProduct() throws Exception {

            long existingProductId = 1L;
            Mockito.when(productService.getAllPricingRules(anyLong()))
                    .thenReturn(Collections.emptyList());

            ResultActions resultActions = mockMvc.perform(get(APIMapping.PRICING_RULE_OF_PRODUCT_MAPPING, existingProductId));

            Mockito.verify(productService).getAllPricingRules(idCaptor.capture());
            Assertions.assertEquals(existingProductId, idCaptor.getValue());

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(Collections.emptyList()))));
        }

        @Test
        @DisplayName("When [get] to /product/{product-id}/pricing-rules with non existing product id," +
                " should get not found error:404")
        public void testGetAllPricingRulesOfNonExistingProduct() throws Exception {

            long nonExistingId = 0L;
            String errorMessage = "product not found";
            Mockito.when(productService.getAllPricingRules(anyLong()))
                    .thenThrow(new NotFoundException(errorMessage));

            ResultActions resultActions = mockMvc.perform(get(APIMapping.PRICING_RULE_OF_PRODUCT_MAPPING, nonExistingId));

            Mockito.verify(productService).getAllPricingRules(idCaptor.capture());
            Assertions.assertEquals(nonExistingId, idCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));

        }
    }


}