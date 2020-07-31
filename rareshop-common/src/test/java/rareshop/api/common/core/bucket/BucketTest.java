/*
 * Copyright (c) @Vishwa 2020.
 */

package rareshop.api.common.core.bucket;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BucketTest {

    private static final Double ITEM_PRICE = 100D;

    @Spy
    private Bucket bucket;

    @Mock
    private BucketItem bucketItem;

    private Set<BucketItem> items = new HashSet<>();

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        Mockito.when(bucketItem.isFinalPriceCalculated()).thenReturn(true);
        Mockito.when(bucketItem.getFinalPrice()).thenReturn(ITEM_PRICE);
        Mockito.when(bucket.getItems()).thenReturn(items);

    }

    @Nested
    @DisplayName("Final Price Retrievals")
    class FinalPrice {

        @Test
        @DisplayName("When Bucket Items are null, final price should be zero ")
        public void when_bucket_items_are_null_final_price_should_be_zero() {

            Mockito.when(bucket.getItems()).thenReturn(null);
            Assertions.assertEquals(0D, bucket.getFinalPrice());
        }

        @Test
        @DisplayName("When Bucket Items are empty, final price should be zero ")
        public void when_bucket_items_are_empty_final_price_should_be_zero() {

            Mockito.when(bucket.getItems()).thenReturn(Collections.emptySet());
            Assertions.assertEquals(0D, bucket.getFinalPrice());
        }

        @Test
        @DisplayName("When Bucket Items contains null, Those should be filtered")
        public void when_bucket_items_contains_null_then_those_should_be_filtered() {

            items.add(null);
            Assertions.assertEquals(0D, bucket.getFinalPrice());
        }

        @Test
        @DisplayName("Only final price set items should be taken to calculate final price")
        public void only_final_price_set_items_should_be_taken_to_calculate_final_price() {

            Mockito.when(bucketItem.isFinalPriceCalculated()).thenReturn(false);
            items.add(bucketItem);

            Assertions.assertEquals(0D, bucket.getFinalPrice());
        }

        @Test
        @DisplayName("When having final price set bucket items, final price should be summed")
        public void when_having_final_price_set_items_final_price_should_be_summed() {

            items.add(bucketItem);
            items.add(null);

            Assertions.assertEquals(ITEM_PRICE, bucket.getFinalPrice());
        }
    }
}