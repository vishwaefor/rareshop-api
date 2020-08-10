package com.rareshop.api.rest.pricing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rareshop.api.rest.pricing.constant.APIMapping;
import com.rareshop.api.rest.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIMapping.PRICING_MAPPING)
public class PricingController {

    @Autowired
    private PricingService pricingService;

    //TODO Calculate Price for PriceBucket

}