package com.rareshop.api.rest.pricing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rareshop.api.rest.pricing.constant.APIMapping;
import com.rareshop.api.rest.pricing.model.BasicBucketData;
import com.rareshop.api.rest.pricing.model.BasicBucketItemData;
import com.rareshop.api.rest.pricing.model.PricedBucketData;
import com.rareshop.api.rest.pricing.model.PricedBucketItemData;
import com.rareshop.api.rest.pricing.service.PricingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import rareshop.api.common.core.model.RareResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(PricingController.class)
@AutoConfigureMockMvc
class PricingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricingService pricingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<BasicBucketData> bucketDataCaptor;

    private BasicBucketData bucketData;

    private BasicBucketItemData itemDataOne;

    private BasicBucketItemData itemDataTwo;

    private PricedBucketData pricedBucketData;

    private PricedBucketItemData pricedItemDataOne;

    private PricedBucketItemData pricedItemDataTwo;


    @BeforeEach
    void setUp(){

        bucketData = new BasicBucketData(1L);
        itemDataOne = new BasicBucketItemData(1L,1L,2);
        itemDataTwo = new BasicBucketItemData(2L,2L,2);
        bucketData.addItem(itemDataOne);
        bucketData.addItem(itemDataTwo);

        pricedBucketData = new PricedBucketData(1L);
        pricedItemDataOne = new PricedBucketItemData();
        pricedItemDataTwo = new PricedBucketItemData();
        pricedBucketData.addItem(pricedItemDataOne);
        pricedBucketData.addItem(pricedItemDataTwo);

    }

    @Test
    @DisplayName("When [post] tp /pricing/buckets with a bucket items, then bucket price should be calculated")
    void testItemPricing() throws Exception {


        when(pricingService.calculatePrice(any(BasicBucketData.class)))
                .thenReturn(pricedBucketData);

        String url = APIMapping.PRICING_MAPPING + APIMapping.BUCKETS_MAPPING;

        ResultActions resultActions = mockMvc.perform(post(url)
                .content(objectMapper.writeValueAsString(bucketData))
                .contentType(MediaType.APPLICATION_JSON));

        verify(pricingService).calculatePrice(bucketDataCaptor.capture());
        Assertions.assertEquals(bucketData,bucketDataCaptor.getValue());

        resultActions.andExpect(content().string(
                objectMapper.writeValueAsString(new RareResponse(pricedBucketData))));
    }

}