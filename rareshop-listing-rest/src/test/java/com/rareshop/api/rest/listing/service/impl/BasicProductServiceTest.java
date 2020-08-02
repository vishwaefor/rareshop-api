package com.rareshop.api.rest.listing.service.impl;

import com.rareshop.api.rest.listing.constant.ErrorMessage;
import com.rareshop.api.rest.listing.constant.InfoMessage;
import com.rareshop.api.rest.listing.model.*;
import com.rareshop.api.rest.listing.repository.BasicPricingRuleRepository;
import com.rareshop.api.rest.listing.repository.BasicProductInfoRepository;
import com.rareshop.api.rest.listing.repository.BasicProductRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import rareshop.api.common.core.constant.Params;
import rareshop.api.common.core.exception.BadRequestException;
import rareshop.api.common.core.exception.ForbiddenException;
import rareshop.api.common.core.exception.NotFoundException;
import rareshop.api.common.core.model.Acknowledgement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BasicProductServiceTest {


    // @InjectMocks Not working since need to reset after each run due to usage of @Nested
    @Spy
    private BasicProductService basicProductService;

    @Mock
    private BasicProductRepository repository;

    @Mock
    private BasicPricingRuleRepository pricingRuleRepository;

    @Mock
    private BasicProductInfoRepository productInfoRepository;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<BasicProduct> productCaptor;

    @Captor
    private ArgumentCaptor<BasicPricingRule> pricingRuleCaptor;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        basicProductService.setRepository(repository); //@InjectMocks not used and setter used
        basicProductService.setProductInfoRepository(productInfoRepository); //@InjectMocks not used and setter used
        basicProductService.setPricingRuleRepository(pricingRuleRepository); //@InjectMocks not used and setter used

    }

    @AfterEach
    private void afterEach() {

        reset(repository);
        reset(basicProductService);
    }

    @Nested
    @DisplayName("Adding Product")
    class AddProduct {

        @Test
        @DisplayName("When added Product without ProductInfo, BadRequestException should be thrown")
        public void addProductInfoWithoutName() throws Exception {

            BasicProductData basicProductData = new BasicProductData();

            Exception exception = Assertions.assertThrows(BadRequestException.class,
                    () -> basicProductService.addProduct(basicProductData));

            verify(repository, never()).save(any(BasicProduct.class));

            Assertions.assertEquals(ErrorMessage.NAME_NOT_SET, exception.getMessage());

        }

        @Test
        @DisplayName("When added Product without ProductInfo, BadRequestException should be thrown")
        public void addProductInfoWithoutProductInfo() throws Exception {

            BasicProductData basicProductData = new BasicProductData();
            basicProductData.setName("name");

            Exception exception = Assertions.assertThrows(BadRequestException.class,
                    () -> basicProductService.addProduct(basicProductData));

            verify(repository, never()).save(any(BasicProduct.class));

            Assertions.assertEquals(ErrorMessage.INVALID_PRODUCT_INFO, exception.getMessage());

        }

        @Test
        @DisplayName("When added Product with not existing ProductInfo, NotFoundException should be thrown")
        public void addProductInfoWithNonExistingProductInfo() throws Exception {

            BasicProductData basicProductData = new BasicProductData();
            basicProductData.setProductInfoId(1L);
            basicProductData.setName("name");

            when(productInfoRepository.existsById(anyLong()))
                    .thenThrow(new NotFoundException(ErrorMessage.PRODUCT_INFO_NOT_FOUND));

            Exception exception = Assertions.assertThrows(NotFoundException.class,
                    () -> basicProductService.addProduct(basicProductData));

            verify(repository, never()).save(any(BasicProduct.class));

            Assertions.assertEquals(ErrorMessage.PRODUCT_INFO_NOT_FOUND, exception.getMessage());

        }

        @Test
        @DisplayName("When added Product with name and ProductInfo, It should be saved")
        public void addProductWithProductInfo() throws Exception {

            long savedId = 1L;
            BasicProduct savedBasicProduct = new BasicProduct();
            savedBasicProduct.setId(savedId);

            when(productInfoRepository.existsById(anyLong()))
                    .thenReturn(true);

            when(repository.save(any(BasicProduct.class))).thenReturn(savedBasicProduct);

            BasicProductData basicProductData = new BasicProductData();
            basicProductData.setProductInfoId(1L);
            basicProductData.setName("name");

            Acknowledgement acknowledgement = basicProductService.addProduct(basicProductData);

            verify(repository).save(productCaptor.capture());
            Assertions.assertEquals(new BasicProduct(basicProductData), productCaptor.getValue());

            Assertions.assertNotNull(acknowledgement);
            Assertions.assertNotNull(acknowledgement.get(Params.PAYLOAD));
            Assertions.assertNotNull(((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertEquals(savedId, ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));

        }

    }

    @Nested
    @DisplayName("Retrieving Product")
    class retrieveProduct {

        @Test
        @DisplayName("All Products should be retrieved")
        void getAllProducts() throws Exception {

            when(repository.findAll()).thenReturn(Collections.emptyList());

            basicProductService.getAllProducts();

            verify(repository).findAll();
        }

        @Test
        @DisplayName("When retrieving with non existing id, NotFoundException should be thrown")
        void getProductByNonExistingId() throws Exception {

            long nonExistingId = 0L;
            when(repository.findById(nonExistingId))
                    .thenReturn(Optional.ofNullable(null));

            Assertions.assertThrows(NotFoundException.class,
                    () -> basicProductService.getProduct(nonExistingId));

            verify(repository).findById(idCaptor.capture());
            Assertions.assertEquals(nonExistingId, idCaptor.getValue());

        }

        @Test
        @DisplayName("When retrieving with existing id, Product should be retrieved")
        void getProductByExistingId() throws Exception {

            long existingId = 1L;
            BasicProduct basicProduct = new BasicProduct();
            basicProduct.setId(existingId);

            when(repository.findById(existingId))
                    .thenReturn(Optional.of(basicProduct));

            Assertions.assertEquals(basicProduct,
                    basicProductService.getProduct(existingId));

            verify(repository).findById(idCaptor.capture());
            Assertions.assertEquals(existingId, idCaptor.getValue());

        }

    }

    @Nested
    @DisplayName("Deleting Product")
    class DeleteProduct {
        @Test
        @DisplayName("When deleting with non existing id, NotFoundException should be thrown")
        void deleteProductByNonExistingId() throws Exception {

            long nonExistingId = 0L;
            when(repository.existsById(anyLong()))
                    .thenReturn(false);

            Assertions.assertThrows(NotFoundException.class,
                    () -> basicProductService.deleteProduct(nonExistingId));

            verify(repository, never()).deleteById(anyLong());


        }

        @Test
        @DisplayName("When deleting with existing id, Product should be deleted")
        void deleteProductByExistingId() throws Exception {

            long existingId = 1L;
            BasicProduct basicProduct = new BasicProduct();
            basicProduct.setId(existingId);

            when(repository.existsById(anyLong()))
                    .thenReturn(true);

            doNothing().when(repository).deleteById(existingId);

            basicProductService.deleteProduct(existingId);

            verify(repository).deleteById(idCaptor.capture());
            Assertions.assertEquals(existingId, idCaptor.getValue());

        }

    }

    @Nested
    @DisplayName("Publishing Product")
    class PublishProduct {
        @Test
        @DisplayName("When publishing with non existing id, NotFoundException should be thrown")
        void publishProductByNonExistingId() throws Exception {

            long nonExistingId = 0L;
            when(repository.existsById(anyLong()))
                    .thenReturn(false);

            Assertions.assertThrows(NotFoundException.class,
                    () -> basicProductService.publishProduct(nonExistingId, true));

            verify(repository, never()).save(any(BasicProduct.class));


        }

        @Test
        @DisplayName("When publishing with unpublished ProductInfo, ForbiddenException should be thrown")
        void publishProductWithUnpublishedProductInfo() throws Exception {

            long existingId = 1L;
            BasicProduct basicProduct = new BasicProduct();
            basicProduct.setId(existingId);

            BasicProductInfo basicProductInfo = new BasicProductInfo();
            basicProduct.setPublished(false);
            basicProduct.setProductInfo(basicProductInfo);

            when(repository.existsById(anyLong()))
                    .thenReturn(true);

            when(repository.findById(anyLong()))
                    .thenReturn(Optional.of(basicProduct));

            Assertions.assertThrows(ForbiddenException.class,
                    () -> basicProductService.publishProduct(1L, true));

            verify(repository, never()).save(any(BasicProduct.class));

        }

        @Test
        @DisplayName("When publishing with existing id ans published ProductInfo, Product should be published")
        void publishProductByExistingIdWithPublishedProductInfo() throws Exception {

            long existingId = 1L;
            BasicProduct basicProduct = new BasicProduct();
            basicProduct.setId(existingId);

            BasicProductInfo basicProductInfo = new BasicProductInfo();
            basicProduct.setPublished(true);
            basicProduct.setProductInfo(basicProductInfo);

            when(repository.existsById(anyLong()))
                    .thenReturn(true);

            when(repository.findById(anyLong()))
                    .thenReturn(Optional.of(basicProduct));

            when(repository.save(any())).thenReturn(basicProduct);

            Acknowledgement acknowledgement =
                    basicProductService.publishProduct(existingId, true);

            verify(repository).save(productCaptor.capture());
            Assertions.assertEquals(basicProduct, productCaptor.getValue());

            Assertions.assertNotNull(acknowledgement);
            Assertions.assertNotNull(acknowledgement.get(Params.PAYLOAD));
            Assertions.assertNotNull(
                    ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertEquals(basicProduct.getId(),
                    ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertNotNull(acknowledgement.get(Params.MESSAGE));
            Assertions.assertEquals(
                    InfoMessage.PRODUCT_PUBLISHED, acknowledgement.get(Params.MESSAGE));

        }

        @Test
        @DisplayName("When unpublishing with existing id, Product should be unpublished")
        void unPublishProductInfoByExistingId() throws Exception {

            long existingId = 1L;
            BasicProduct basicProduct = new BasicProduct();
            basicProduct.setId(existingId);

            when(repository.existsById(anyLong()))
                    .thenReturn(true);

            when(repository.findById(anyLong()))
                    .thenReturn(Optional.of(basicProduct));

            when(repository.save(any())).thenReturn(basicProduct);

            Acknowledgement acknowledgement =
                    basicProductService.publishProduct(existingId, false);

            verify(repository).save(productCaptor.capture());
            Assertions.assertEquals(basicProduct, productCaptor.getValue());

            Assertions.assertNotNull(acknowledgement);
            Assertions.assertNotNull(acknowledgement.get(Params.PAYLOAD));
            Assertions.assertNotNull(
                    ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertEquals(basicProduct.getId(),
                    ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertNotNull(acknowledgement.get(Params.MESSAGE));
            Assertions.assertEquals(
                    InfoMessage.PRODUCT_UNPUBLISHED, acknowledgement.get(Params.MESSAGE));

        }

    }

    @Nested
    @DisplayName("Adding Pricing Rules to Product")
    class AddPricingRules {

        @Test
        @DisplayName("When add Pricing Rules to non existing Product," +
                "NotFoundException should be thrown ")
        public void addPricingRulesToNonExistingProduct() {

            long nonExistingId = 0L;
            when(repository.findById(anyLong()))
                    .thenReturn(Optional.ofNullable(null));

            BasicPricingRuleData basicPricingRuleData = new BasicPricingRuleData();
            basicPricingRuleData.setUnitId(1);
            basicPricingRuleData.setUnitPrice(1D);

            Exception exception = Assertions.assertThrows(NotFoundException.class,
                    () -> basicProductService.addPricingRule(nonExistingId, basicPricingRuleData));

            verify(pricingRuleRepository, never()).save(any(BasicPricingRule.class));

            Assertions.assertEquals(exception.getMessage(), ErrorMessage.PRODUCT_NOT_FOUND);


        }

        @Test
        @DisplayName("When retrieve Pricing Rules with bad values," +
                "BadRequestException should be thrown ")
        public void addPricingRuleWithBadValues() {
            when(repository.findById(anyLong()))
                    .thenReturn(Optional.of(new BasicProduct()));

            Assertions.assertAll(
                    () -> {
                        BasicPricingRuleData basicPricingRuleData = new BasicPricingRuleData();

                        Exception exception = Assertions.assertThrows(BadRequestException.class,
                                () -> basicProductService.addPricingRule(1L, basicPricingRuleData));

                        verify(pricingRuleRepository, never()).save(any(BasicPricingRule.class));

                        Assertions.assertEquals(exception.getMessage(), ErrorMessage.UNIT_NOT_SET);
                    },
                    () -> {

                        BasicPricingRuleData basicPricingRuleData = new BasicPricingRuleData();
                        basicPricingRuleData.setUnitId(1);

                        Exception exception = Assertions.assertThrows(BadRequestException.class,
                                () -> basicProductService.addPricingRule(1L, basicPricingRuleData));

                        verify(pricingRuleRepository, never()).save(any(BasicPricingRule.class));

                        Assertions.assertEquals(exception.getMessage(), ErrorMessage.INVALID_UNIT_PRICE);
                    },
                    () -> {

                        BasicPricingRuleData basicPricingRuleData = new BasicPricingRuleData();
                        basicPricingRuleData.setUnitId(1);
                        basicPricingRuleData.setUnitPrice(-100);

                        Exception exception = Assertions.assertThrows(BadRequestException.class,
                                () -> basicProductService.addPricingRule(1L, basicPricingRuleData));

                        verify(pricingRuleRepository, never()).save(any(BasicPricingRule.class));

                        Assertions.assertEquals(exception.getMessage(), ErrorMessage.INVALID_UNIT_PRICE);
                    },
                    () -> {

                        BasicPricingRuleData basicPricingRuleData = new BasicPricingRuleData();
                        basicPricingRuleData.setUnitId(5);
                        basicPricingRuleData.setUnitPrice(100);

                        Exception exception = Assertions.assertThrows(BadRequestException.class,
                                () -> basicProductService.addPricingRule(1L, basicPricingRuleData));

                        verify(pricingRuleRepository, never()).save(any(BasicPricingRule.class));

                        Assertions.assertEquals(exception.getMessage(), ErrorMessage.INVALID_UNIT);
                    },
                    () -> {

                        BasicPricingRuleData basicPricingRuleData = new BasicPricingRuleData();
                        basicPricingRuleData.setUnitId(1);
                        basicPricingRuleData.setUnitPrice(100);
                        basicPricingRuleData.setAdditionalPricingFactorUsed(true);

                        Exception exception = Assertions.assertThrows(BadRequestException.class,
                                () -> basicProductService.addPricingRule(1L, basicPricingRuleData));

                        verify(pricingRuleRepository, never()).save(any(BasicPricingRule.class));

                        Assertions.assertEquals(exception.getMessage(), ErrorMessage.INVALID_ADDITIONAL_FACTOR);
                    },
                    () -> {

                        BasicPricingRuleData basicPricingRuleData = new BasicPricingRuleData();
                        basicPricingRuleData.setUnitId(1);
                        basicPricingRuleData.setUnitPrice(100);
                        basicPricingRuleData.setAdditionalPricingFactorUsed(true);
                        basicPricingRuleData.setAdditionalPricingFactor(0.99D);

                        Exception exception = Assertions.assertThrows(BadRequestException.class,
                                () -> basicProductService.addPricingRule(1L, basicPricingRuleData));

                        verify(pricingRuleRepository, never()).save(any(BasicPricingRule.class));

                        Assertions.assertEquals(exception.getMessage(), ErrorMessage.INVALID_ADDITIONAL_FACTOR);
                    }
            );
        }

        @Test
        @DisplayName("When add Pricing Rules to existing Product with valid values," +
                "Pricing Rules should be added to Product")
        public void addPricingRuleWithValidValues() {

            long savedId = 1L;
            long unitId = 10L;
            BasicPricingRule savedBasicPricingRule = new BasicPricingRule();
            savedBasicPricingRule.setId(savedId);
            when(pricingRuleRepository.save(any(BasicPricingRule.class)))
                    .thenReturn(savedBasicPricingRule);

            BasicProduct basicProduct = new BasicProduct();
            basicProduct.addUnit(new BasicUnit(unitId, "Test", 100));
            when(repository.findById(anyLong())).thenReturn(Optional.of(basicProduct));

            BasicPricingRuleData basicPricingRuleData = new BasicPricingRuleData();
            basicPricingRuleData.setUnitId(unitId);
            basicPricingRuleData.setUnitPrice(100);
            basicPricingRuleData.setAdditionalPricingFactorUsed(true);
            basicPricingRuleData.setAdditionalPricingFactor(1D);

            Acknowledgement acknowledgement = basicProductService.addPricingRule(1L, basicPricingRuleData);

            verify(pricingRuleRepository).save(pricingRuleCaptor.capture());

            Assertions.assertNotNull(acknowledgement);
            Assertions.assertNotNull(acknowledgement.get(Params.PAYLOAD));
            Assertions.assertNotNull(((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertEquals(savedId, ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));

        }

    }

    @Nested
    @DisplayName("Retrieving Pricing Rules of Product")
    class GetAllPricingRules {

        @Test
        @DisplayName("When retrieve Pricing Rules from non existing Product," +
                "NotFoundException should be thrown ")
        public void getAllPricingRulesOfNonExistingProduct() {

            long nonExistingId = 0L;
            when(repository.existsById(anyLong())).thenReturn(false);

            Exception exception = Assertions.assertThrows(NotFoundException.class,
                    () -> basicProductService.getAllPricingRules(nonExistingId));

            verify(pricingRuleRepository, never()).findAll();

            Assertions.assertEquals(ErrorMessage.PRODUCT_NOT_FOUND, exception.getMessage());

        }

        @Test
        @DisplayName("When retrieve Pricing Rules from existing Product," +
                "Pricing Rules of Product should be retrieved")
        public void getAllPricingRulesOfExistingProduct() {

            long existingId = 1L;

            BasicProduct foundProduct = new BasicProduct();
            foundProduct.setId(existingId);
            when(repository.findById(anyLong())).thenReturn(Optional.of(foundProduct));

            when(pricingRuleRepository.findByBasicProduct(any(BasicProduct.class)))
                    .thenReturn(Collections.emptyList());

            basicProductService.getAllPricingRules(existingId);

            verify(pricingRuleRepository).findByBasicProduct(productCaptor.capture());

            Assertions.assertEquals(existingId, productCaptor.getValue().getId());


        }

    }

}