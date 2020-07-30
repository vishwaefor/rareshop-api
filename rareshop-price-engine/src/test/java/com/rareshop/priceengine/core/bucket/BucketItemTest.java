/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.priceengine.core.bucket;

import com.rareshop.priceengine.core.product.Product;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class BucketItemTest {

    private static final String PRODUCT_ID = "PRODUCT_ID";

    @Spy
    private BucketItem bucketItem;

    @Mock
    private Product product;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("Product id should be returned as bucket item id")
    void id_should_be_same_as_product_id() {

        Mockito.when(product.getId()).thenReturn(PRODUCT_ID);
        Mockito.when(bucketItem.getProduct()).thenReturn(product);

        String productId = bucketItem.getId();

        Mockito.verify(product).getId();
        Assertions.assertEquals(PRODUCT_ID, productId);


    }
    @Nested
    @DisplayName("Pricing rules")
    class Pricing{

    }
}