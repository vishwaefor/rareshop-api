package com.rareshop.api.rest.listing.impl.service;

import com.rareshop.api.rest.listing.constant.ErrorMessage;
import com.rareshop.api.rest.listing.constant.InfoMessage;
import com.rareshop.api.rest.listing.impl.repository.BasicProductInfoRepository;
import com.rareshop.api.rest.listing.model.BasicProductInfo;
import org.junit.jupiter.api.*;
import org.mockito.*;
import rareshop.api.common.core.constant.Params;
import rareshop.api.common.core.exception.BadRequestException;
import rareshop.api.common.core.exception.NotFoundException;
import rareshop.api.common.core.model.Acknowledgement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BasicProductInfoServiceTest {

    //    @InjectMocks
    @Spy
    private BasicProductInfoService basicProductInfoService;

    @Mock
    private BasicProductInfoRepository repository;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<BasicProductInfo> productInfoCaptor;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        basicProductInfoService.setRepository(repository);

    }

    @AfterEach
    private void afterEach() {

        reset(repository);
        reset(basicProductInfoService);
    }

    @Nested
    @DisplayName("Adding Product Info")
    class AddProductInfo {

        @Test
        @DisplayName("When added ProductInfo without name, BadRequestException should be thrown")
        public void addProductInfoWithoutName() throws Exception {

            BasicProductInfo basicProductInfo = new BasicProductInfo();

            verify(repository, never()).save(any(BasicProductInfo.class));

            Exception exception = Assertions.assertThrows(BadRequestException.class,
                    () -> basicProductInfoService.addProductInfo(basicProductInfo));

            Assertions.assertEquals(ErrorMessage.NAME_IS_BLANK, exception.getMessage());

        }

        @Test
        @DisplayName("When added ProductInfo without description, BadRequestException should be thrown")
        public void addProductInfoWithoutDescription() throws Exception {

            BasicProductInfo basicProductInfo = new BasicProductInfo();
            basicProductInfo.setName("Name");

            Exception exception = Assertions.assertThrows(BadRequestException.class,
                    () -> basicProductInfoService.addProductInfo(basicProductInfo));

            verify(repository, never()).save(any(BasicProductInfo.class));

            Assertions.assertEquals(ErrorMessage.DESCRIPTION_IS_BLANK, exception.getMessage());

        }

        @Test
        @DisplayName("When added ProductInfo with name and description, It should be saved")
        public void addProductInfoWithNameAndDescription() throws Exception {

            long savedId = 1L;
            BasicProductInfo savedBasicProductInfo = new BasicProductInfo();
            savedBasicProductInfo.setId(savedId);
            when(repository.save(any(BasicProductInfo.class))).thenReturn(savedBasicProductInfo);

            BasicProductInfo basicProductInfo = new BasicProductInfo();
            basicProductInfo.setName("Name");
            basicProductInfo.setDescription("Description");

            Acknowledgement acknowledgement = basicProductInfoService.addProductInfo(basicProductInfo);

            verify(repository).save(productInfoCaptor.capture());
            Assertions.assertEquals(basicProductInfo, productInfoCaptor.getValue());

            Assertions.assertNotNull(acknowledgement);
            Assertions.assertNotNull(acknowledgement.get(Params.PAYLOAD));
            Assertions.assertNotNull(((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertEquals(savedId, ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));

        }

    }

    @Nested
    @DisplayName("Retrieving Product Info")
    class retrieveProductInfo {

        @Test
        @DisplayName("All Product Info should be retrieved")
        void getAllProductInfo() throws Exception {

            when(repository.findAll()).thenReturn(Collections.emptyList());

            basicProductInfoService.getAllProductInfo();

            verify(repository).findAll();
        }

        @Test
        @DisplayName("When retrieving with non existing id, NotFoundException should be thrown")
        void getProductInfoByNonExistingId() throws Exception {

            long nonExistingId = 0L;
            when(repository.findById(nonExistingId))
                    .thenReturn(Optional.ofNullable(null));

            Assertions.assertThrows(NotFoundException.class,
                    () -> basicProductInfoService.getProductInfo(nonExistingId));

            verify(repository).findById(idCaptor.capture());
            Assertions.assertEquals(nonExistingId, idCaptor.getValue());

        }

        @Test
        @DisplayName("When retrieving with existing id, Product Info should be retrieved")
        void getProductInfoByExistingId() throws Exception {

            long existingId = 1L;
            BasicProductInfo basicProductInfo = new BasicProductInfo();
            basicProductInfo.setId(existingId);

            when(repository.findById(existingId))
                    .thenReturn(Optional.of(basicProductInfo));

            Assertions.assertEquals(basicProductInfo,
                    basicProductInfoService.getProductInfo(existingId));

            verify(repository).findById(idCaptor.capture());
            Assertions.assertEquals(existingId, idCaptor.getValue());

        }

    }


    @Nested
    @DisplayName("Updating Product Info")
    class UpdateProductInfo {

        @Test
        @DisplayName("When updating Product Info with missing id or name or description, BadRequestException should be thrown")
        void updateProductInfoWithBadInfo() throws Exception {

            Assertions.assertAll(
                    () -> {
                        BasicProductInfo basicProductInfo = new BasicProductInfo();

                        Exception exception = Assertions.assertThrows(BadRequestException.class,
                                () -> basicProductInfoService.updateProductInfo(basicProductInfo));

                        Assertions.assertEquals(ErrorMessage.ID_IS_BLANK, exception.getMessage());
                        verify(repository, never()).save(any(BasicProductInfo.class));
                    },
                    () -> {
                        BasicProductInfo basicProductInfo = new BasicProductInfo();
                        basicProductInfo.setId(1L);

                        Exception exception = Assertions.assertThrows(BadRequestException.class,
                                () -> basicProductInfoService.updateProductInfo(basicProductInfo));

                        Assertions.assertEquals(ErrorMessage.NAME_IS_BLANK, exception.getMessage());
                        verify(repository, never()).save(any(BasicProductInfo.class));
                    },
                    () -> {
                        BasicProductInfo basicProductInfo = new BasicProductInfo();
                        basicProductInfo.setId(1L);
                        basicProductInfo.setName("name");

                        Exception exception = Assertions.assertThrows(BadRequestException.class,
                                () -> basicProductInfoService.updateProductInfo(basicProductInfo));

                        Assertions.assertEquals(ErrorMessage.DESCRIPTION_IS_BLANK, exception.getMessage());
                        verify(repository, never()).save(any(BasicProductInfo.class));
                    }
            );
        }

        @Test
        @DisplayName("When updating Product Info with id or name or description, it should be saved")
        void updateProductInfoWithCorrectInfo() throws Exception {

            BasicProductInfo basicProductInfo = new BasicProductInfo();
            basicProductInfo.setId(1L);
            basicProductInfo.setName("name");
            basicProductInfo.setDescription("description");

            when(repository.findById(anyLong()))
                    .thenReturn(Optional.of(basicProductInfo));

            when(repository.existsById(anyLong()))
                    .thenReturn(true);

            when(repository.save(any(BasicProductInfo.class)))
                    .thenReturn(basicProductInfo);

            Acknowledgement acknowledgement =
                    basicProductInfoService.updateProductInfo(basicProductInfo);

            verify(repository).save(productInfoCaptor.capture());
            Assertions.assertEquals(basicProductInfo, productInfoCaptor.getValue());

            Assertions.assertNotNull(acknowledgement);
            Assertions.assertNotNull(acknowledgement.get(Params.PAYLOAD));
            Assertions.assertNotNull(((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertEquals(basicProductInfo.getId(),
                    ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));

        }

    }

    @Nested
    @DisplayName("Deleting Product Info")
    class DeleteProductInfo {
        @Test
        @DisplayName("When deleting with non existing id, NotFoundException should be thrown")
        void deleteProductInfoByNonExistingId() throws Exception {

            long nonExistingId = 0L;
            when(repository.existsById(anyLong()))
                    .thenReturn(false);

            Assertions.assertThrows(NotFoundException.class,
                    () -> basicProductInfoService.deleteProductInfo(nonExistingId));

            verify(repository, never()).deleteById(anyLong());


        }

        @Test
        @DisplayName("When deleting with existing id, Product Info should be deleted")
        void deleteProductInfoByExistingId() throws Exception {

            long existingId = 1L;
            BasicProductInfo basicProductInfo = new BasicProductInfo();
            basicProductInfo.setId(existingId);

            when(repository.existsById(anyLong()))
                    .thenReturn(true);

            doNothing().when(repository).deleteById(existingId);

            basicProductInfoService.deleteProductInfo(existingId);

            verify(repository).deleteById(idCaptor.capture());
            Assertions.assertEquals(existingId, idCaptor.getValue());

        }

    }

    @Nested
    @DisplayName("Publishing Product Info")
    class PublishProductInfo {
        @Test
        @DisplayName("When publishing with non existing id, NotFoundException should be thrown")
        void publishProductInfoByNonExistingId() throws Exception {

            long nonExistingId = 0L;
            when(repository.existsById(anyLong()))
                    .thenReturn(false);

            Assertions.assertThrows(NotFoundException.class,
                    () -> basicProductInfoService.publishProductInfo(nonExistingId, true));

            verify(repository, never()).save(any(BasicProductInfo.class));


        }

        @Test
        @DisplayName("When publishing with existing id, Product Info should be published")
        void publishProductInfoByExistingId() throws Exception {

            long existingId = 1L;
            BasicProductInfo basicProductInfo = new BasicProductInfo();
            basicProductInfo.setId(existingId);

            when(repository.existsById(anyLong()))
                    .thenReturn(true);

            when(repository.findById(anyLong()))
                    .thenReturn(Optional.of(basicProductInfo));

            when(repository.save(any())).thenReturn(basicProductInfo);

            Acknowledgement acknowledgement =
                    basicProductInfoService.publishProductInfo(existingId, true);

            verify(repository).save(productInfoCaptor.capture());
            Assertions.assertEquals(basicProductInfo, productInfoCaptor.getValue());

            Assertions.assertNotNull(acknowledgement);
            Assertions.assertNotNull(acknowledgement.get(Params.PAYLOAD));
            Assertions.assertNotNull(
                    ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertEquals(basicProductInfo.getId(),
                    ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertNotNull(acknowledgement.get(Params.MESSAGE));
            Assertions.assertEquals(
                    InfoMessage.PRODUCT_INFO_PUBLISHED, acknowledgement.get(Params.MESSAGE));

        }

        @Test
        @DisplayName("When unpublishing with existing id, Product Info should be unpublished")
        void unPublishProductInfoByExistingId() throws Exception {

            long existingId = 1L;
            BasicProductInfo basicProductInfo = new BasicProductInfo();
            basicProductInfo.setId(existingId);

            when(repository.existsById(anyLong()))
                    .thenReturn(true);

            when(repository.findById(anyLong()))
                    .thenReturn(Optional.of(basicProductInfo));

            when(repository.save(any())).thenReturn(basicProductInfo);

            Acknowledgement acknowledgement =
                    basicProductInfoService.publishProductInfo(existingId, false);

            verify(repository).save(productInfoCaptor.capture());
            Assertions.assertEquals(basicProductInfo, productInfoCaptor.getValue());

            Assertions.assertNotNull(acknowledgement);
            Assertions.assertNotNull(acknowledgement.get(Params.PAYLOAD));
            Assertions.assertNotNull(
                    ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertEquals(basicProductInfo.getId(),
                    ((HashMap<String, Object>) acknowledgement.get(Params.PAYLOAD)).get(Params.ID));
            Assertions.assertNotNull(acknowledgement.get(Params.MESSAGE));
            Assertions.assertEquals(
                    InfoMessage.PRODUCT_INFO_UNPUBLISHED, acknowledgement.get(Params.MESSAGE));

        }

    }
}