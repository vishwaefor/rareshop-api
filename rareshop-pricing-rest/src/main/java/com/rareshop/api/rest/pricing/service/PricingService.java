package com.rareshop.api.rest.pricing.service;

import com.rareshop.api.rest.pricing.engine.BasicPricingEngine;
import com.rareshop.api.rest.pricing.model.BasicBucketData;
import com.rareshop.api.rest.pricing.model.PricedBucketData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface PricingService {

    PricedBucketData calculatePrice(BasicBucketData bucketData);

}
