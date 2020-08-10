package com.rareshop.api.rest.pricing.service;

import com.rareshop.api.rest.pricing.model.BasicBucketData;
import com.rareshop.api.rest.pricing.model.PricedBucketData;
import org.springframework.stereotype.Service;

@Service
public interface PricingService {

    PricedBucketData calculatePrice(BasicBucketData bucketData);

}
