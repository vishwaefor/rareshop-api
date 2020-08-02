package com.rareshop.api.rest.listing.service;

import com.rareshop.api.rest.listing.model.BasicPricingRule;
import org.springframework.stereotype.Service;
import rareshop.api.common.core.model.Acknowledgement;

@Service
public interface PricingRuleService {

    Acknowledgement updatePricingRule(BasicPricingRule basicPricingRule);

    Acknowledgement deletePricingRule(long id);

}
