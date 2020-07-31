/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.bucket;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import rareshop.api.common.core.product.Product;

class BucketItemTest {

    private static final Long PRODUCT_ID = 1L;

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

        long productId = bucketItem.getId();

        Mockito.verify(product).getId();
        Assertions.assertEquals(PRODUCT_ID, productId);


    }

    @Nested
    @DisplayName("Pricing rules")
    class Pricing {

    }
}