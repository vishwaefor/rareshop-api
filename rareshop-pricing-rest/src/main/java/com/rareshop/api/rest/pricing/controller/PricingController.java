package com.rareshop.api.rest.pricing.controller;

import com.rareshop.api.rest.pricing.constant.APIMapping;
import com.rareshop.api.rest.pricing.model.BasicBucketData;
import com.rareshop.api.rest.pricing.model.PricedBucketData;
import com.rareshop.api.rest.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rareshop.api.common.core.model.RareResponse;

@RestController
@RequestMapping(APIMapping.PRICING_MAPPING)
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @PostMapping(APIMapping.BUCKETS_MAPPING)
    public ResponseEntity<RareResponse> calculateBucketPrice
            (@RequestBody BasicBucketData bucketData){

        PricedBucketData response = pricingService.calculatePrice(bucketData);
        return  ResponseEntity.ok(new RareResponse(response));

    }

}