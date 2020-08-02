package com.rareshop.api.rest.listing.controller;

import com.rareshop.api.rest.listing.constant.APIMapping;
import com.rareshop.api.rest.listing.service.PricingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(APIMapping.PRICING_RULE_MAPPING)
public class PricingRuleController {

    @Autowired
    private PricingRuleService pricingRuleService;

    //TODO Update Pricing Rule

    //TODO Delete Pricing Rule
}
