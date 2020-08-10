/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.api.rest.listing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rareshop.api.rest.listing.constant.APIMapping;
import com.rareshop.api.rest.listing.constant.InfoMessage;
import com.rareshop.api.rest.listing.model.BasicProductInfo;
import com.rareshop.api.rest.listing.model.BasicProductInfoData;
import com.rareshop.api.rest.listing.service.ProductInfoService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductInfoController.class)
@AutoConfigureMockMvc
public class ProductInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductInfoService productInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<Boolean> publishedCaptor;

    @Captor
    private ArgumentCaptor<BasicProductInfo> productInfoCaptor;

    @Captor
    private ArgumentCaptor<BasicProductInfoData> productInfoDataCaptor;

    @AfterEach
    private void afterEach() {
        reset(productInfoService);
    }

    @Nested
    @DisplayName("Adding Product Info")
    class AddProductInfo {
        @Test
        @DisplayName("When [post] to /product-info with correct body , Product Info should be added")
        void testAddProductInfoWithCorrectBody() throws Exception {
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", 1);
            Acknowledgement acknowledgement =
                    new Acknowledgement(InfoMessage.PRODUCT_INFO_CREATED, payload);

            Mockito.when(productInfoService.addProductInfo(any(BasicProductInfoData.class)))
                    .thenReturn(acknowledgement);

            BasicProductInfoData basicProductInfoData = new BasicProductInfoData();

            ResultActions resultActions = mockMvc.perform(post(APIMapping.PRODUCT_INFO_MAPPING)
                    .content(objectMapper.writeValueAsString(basicProductInfoData))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(productInfoService).addProductInfo(productInfoDataCaptor.capture());

            Assertions.assertEquals(basicProductInfoData, productInfoDataCaptor.getValue());

            resultActions.andExpect(status().isCreated())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(acknowledgement))));

        }

        @Test
        @DisplayName("When [post] to /product-info with incorrect body, should get bad request error:400")
        void testAddProductInfoWithIncorrectBody() throws Exception {

            String errorMessage = "invalid property";
            Mockito.when(productInfoService.addProductInfo(any(BasicProductInfoData.class)))
                    .thenThrow(new BadRequestException(errorMessage));

            BasicProductInfoData basicProductInfoDataWithErrors = new BasicProductInfoData();

            ResultActions resultActions = mockMvc.perform(post(APIMapping.PRODUCT_INFO_MAPPING)
                    .content(objectMapper.writeValueAsString(basicProductInfoDataWithErrors))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(productInfoService).addProductInfo(productInfoDataCaptor.capture());

            Assertions.assertEquals(basicProductInfoDataWithErrors, productInfoDataCaptor.getValue());

            resultActions.andExpect(status().isBadRequest())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));

        }
    }

    @Nested
    @DisplayName("Retrieving Product Info")
    class RetrieveProductInfo {
        @Test
        @DisplayName("When [get] to /product-info , all Product Info objects should be retrieved")
        void testGetAllProductInfo() throws Exception {

            Mockito.when(productInfoService.getAllProductInfo())
                    .thenReturn(Collections.emptyList());

            ResultActions resultActions = mockMvc.perform(get(APIMapping.PRODUCT_INFO_MAPPING));

            Mockito.verify(productInfoService).getAllProductInfo();

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(Collections.emptyList()))));
        }

        @Test
        @DisplayName("When [get] to /product-info/{product-info-id} with existing id, " +
                "matched Product Info object should be retrieved")
        void testGetSingleProductInfoWithExistingId() throws Exception {

            long existingId = 1L;
            BasicProductInfo basicProductInfo = new BasicProductInfo();
            basicProductInfo.setId(existingId);
            basicProductInfo.setDescription("test");

            Mockito.when(productInfoService.getProductInfo(anyLong()))
                    .thenReturn(basicProductInfo);

            String url = APIMapping.PRODUCT_INFO_MAPPING + APIMapping.SINGLE_PRODUCT_INFO_MAPPING;

            ResultActions resultActions = mockMvc.perform(get(url, existingId));

            Mockito.verify(productInfoService).getProductInfo(idCaptor.capture());

            Assertions.assertEquals(existingId, idCaptor.getValue());

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(basicProductInfo))));

        }

        @Test
        @DisplayName("When [get] to /product-info/{product-info-id} with non-existing id, " +
                "should get not found error:404")
        void testGetSingleProductInfoWithNonExistingId() throws Exception {

            String errorMessage = "item not found";
            Mockito.when(productInfoService.getProductInfo(anyLong()))
                    .thenThrow(new NotFoundException(errorMessage));

            long nonExistingId = 0L;
            String url = APIMapping.PRODUCT_INFO_MAPPING + APIMapping.SINGLE_PRODUCT_INFO_MAPPING;

            ResultActions resultActions = mockMvc.perform(get(url, nonExistingId));

            Mockito.verify(productInfoService).getProductInfo(idCaptor.capture());

            Assertions.assertEquals(nonExistingId, idCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));

        }
    }

    @Nested
    @DisplayName("Updating Product Info")
    class UpdateProductInfo {

        @Test
        @DisplayName("When [put] to /product-info/{product-info-id} with non existing id, " +
                "should get not found error:404")
        void testUpdateProductInfoWithNonExistingId() throws Exception {


            String errorMessage = "item not found";
            Mockito.when(productInfoService.updateProductInfo(any(BasicProductInfo.class)))
                    .thenThrow(new NotFoundException(errorMessage));

            long nonExistingId = 0L;
            BasicProductInfoData basicProductInfoData = new BasicProductInfoData();

            String url = APIMapping.PRODUCT_INFO_MAPPING + APIMapping.SINGLE_PRODUCT_INFO_MAPPING;
            ResultActions resultActions = mockMvc.perform(put(url, nonExistingId)
                    .content(objectMapper.writeValueAsString(basicProductInfoData))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(productInfoService).updateProductInfo(productInfoCaptor.capture());

            Assertions.assertEquals(new BasicProductInfo(nonExistingId, basicProductInfoData), productInfoCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));


        }

        @Test
        @DisplayName("When [put] to /product-info/{product-info-id} with incorrect body, " +
                "should get bad request error:400")
        void testUpdateProductInfoWithIncorrectBody() throws Exception {

            long id = 1L;
            String errorMessage = "invalid property";
            Mockito.when(productInfoService.updateProductInfo(any(BasicProductInfo.class)))
                    .thenThrow(new BadRequestException(errorMessage));

            BasicProductInfoData updatedBasicProductInfoDataWithErrors = new BasicProductInfoData();

            String url = APIMapping.PRODUCT_INFO_MAPPING + APIMapping.SINGLE_PRODUCT_INFO_MAPPING;
            ResultActions resultActions = mockMvc.perform(put(url, id)
                    .content(objectMapper.writeValueAsString(updatedBasicProductInfoDataWithErrors))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(productInfoService).updateProductInfo(productInfoCaptor.capture());

            Assertions.assertEquals(
                    new BasicProductInfo(id, updatedBasicProductInfoDataWithErrors),
                    productInfoCaptor.getValue());

            resultActions.andExpect(status().isBadRequest())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));
        }

        @Test
        @DisplayName("When [put] to /product-info/{product-info-id} with correct body, " +
                "Product Info should be updated ")
        void testUpdateProductInfoWithCorrectBody() throws Exception {
            long id = 1L;
            Map<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, id);
            Acknowledgement acknowledgement =
                    new Acknowledgement(InfoMessage.PRODUCT_INFO_UPDATED, payload);

            Mockito.when(productInfoService.updateProductInfo(any(BasicProductInfo.class)))
                    .thenReturn(acknowledgement);

            BasicProductInfoData updatedBasicProductInfo = new BasicProductInfoData();

            String url = APIMapping.PRODUCT_INFO_MAPPING + APIMapping.SINGLE_PRODUCT_INFO_MAPPING;
            ResultActions resultActions = mockMvc.perform(put(url, id)
                    .content(objectMapper.writeValueAsString(updatedBasicProductInfo))
                    .contentType(MediaType.APPLICATION_JSON));

            Mockito.verify(productInfoService).updateProductInfo(productInfoCaptor.capture());

            Assertions.assertEquals(
                    new BasicProductInfo(id, updatedBasicProductInfo),
                    productInfoCaptor.getValue());

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(acknowledgement))));
        }
    }

    @Nested
    @DisplayName("Deleting Product Info")
    class DeleteProductInfo {

        @Test
        @DisplayName("When [delete] to /product-info/{product-info-id} with non existing id, s" +
                "hould get not found error:404")
        void testDeleteProductInfoWithNonExistingId() throws Exception {
            String errorMessage = "item not found";
            Mockito.when(productInfoService.deleteProductInfo(anyLong()))
                    .thenThrow(new NotFoundException(errorMessage));

            long nonExistingId = 0L;
            String url = APIMapping.PRODUCT_INFO_MAPPING + APIMapping.SINGLE_PRODUCT_INFO_MAPPING;

            ResultActions resultActions = mockMvc.perform(delete(url, nonExistingId));

            Mockito.verify(productInfoService).deleteProductInfo(idCaptor.capture());

            Assertions.assertEquals(nonExistingId, idCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));


        }

        @Test
        @DisplayName("When [delete] to /product-info/{product-info-id} with existing id, " +
                "Product Info should be deleted")
        void testDeleteProductInfoWithExistingId() throws Exception {
            long id = 1L;
            Map<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, id);
            Acknowledgement acknowledgement =
                    new Acknowledgement(InfoMessage.PRODUCT_INFO_DELETED, payload);

            Mockito.when(productInfoService.deleteProductInfo(anyLong()))
                    .thenReturn(acknowledgement);


            String url = APIMapping.PRODUCT_INFO_MAPPING + APIMapping.SINGLE_PRODUCT_INFO_MAPPING;
            ResultActions resultActions = mockMvc.perform(delete(url, id));

            Mockito.verify(productInfoService).deleteProductInfo(idCaptor.capture());

            Assertions.assertEquals(id, idCaptor.getValue());

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(acknowledgement))));

        }
    }

    @Nested
    @DisplayName("Publishing Product Info")
    class PublishProductInfo {

        @Test
        @DisplayName("When [patch] to /product-info/{product-info-id}?published=true with non existing id, " +
                "should get not found error:404")
        void testPublishProductInfoWithNonExistingId() throws Exception {

            boolean published = true;
            String errorMessage = "item not found";
            Mockito.when(productInfoService.publishProductInfo(anyLong(), anyBoolean()))
                    .thenThrow(new NotFoundException(errorMessage));

            long nonExistingId = 0L;
            String url = APIMapping.PRODUCT_INFO_MAPPING + APIMapping.SINGLE_PRODUCT_INFO_MAPPING;

            ResultActions resultActions = mockMvc.perform(patch(url, nonExistingId)
                    .param(APIMapping.PRODUCT_INFO_PUBLISHED_PARAM, published ? "true" : "false"));

            Mockito.verify(productInfoService).publishProductInfo(idCaptor.capture(), publishedCaptor.capture());

            Assertions.assertEquals(nonExistingId, idCaptor.getValue());
            Assertions.assertEquals(published, publishedCaptor.getValue());

            resultActions.andExpect(status().isNotFound())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(errorMessage, false))));

        }

        @Test
        @DisplayName("When [patch] to /product-info/{product-info-id}?published=true, " +
                "Product Info should be published")
        void testPublishProductWithExistingId() throws Exception {
            long id = 1L;
            boolean published = true;
            Map<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, id);
            Acknowledgement acknowledgement =
                    new Acknowledgement(
                            published ? InfoMessage.PRODUCT_INFO_PUBLISHED : InfoMessage.PRODUCT_INFO_UNPUBLISHED, payload);

            Mockito.when(productInfoService.publishProductInfo(anyLong(), anyBoolean()))
                    .thenReturn(acknowledgement);


            String url = APIMapping.PRODUCT_INFO_MAPPING + APIMapping.SINGLE_PRODUCT_INFO_MAPPING;
            ResultActions resultActions = mockMvc.perform(patch(url, id)
                    .param(APIMapping.PRODUCT_INFO_PUBLISHED_PARAM, published ? "true" : "false"));

            Mockito.verify(productInfoService).publishProductInfo(idCaptor.capture(), publishedCaptor.capture());

            Assertions.assertEquals(id, idCaptor.getValue());
            Assertions.assertEquals(published, publishedCaptor.getValue());

            resultActions.andExpect(status().isOk())
                    .andExpect(content().string(
                            objectMapper.writeValueAsString(new RareResponse(acknowledgement))));
        }
    }


}