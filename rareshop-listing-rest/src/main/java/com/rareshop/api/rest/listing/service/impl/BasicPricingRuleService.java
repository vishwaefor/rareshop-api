package com.rareshop.api.rest.listing.service.impl;

import com.rareshop.api.rest.listing.model.BasicPricingRule;
import com.rareshop.api.rest.listing.repository.BasicPricingRuleRepository;
import com.rareshop.api.rest.listing.service.PricingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rareshop.api.common.core.model.Acknowledgement;

@Service
public class BasicPricingRuleService implements PricingRuleService {

    @Autowired
    private BasicPricingRuleRepository repository;

    /**
     * Used for testing purposes.
     *
     * @param repository
     * @InjectMocks Not working since need to reset after each run due to usage of @Nested
     */
    public void setRepository(BasicPricingRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Acknowledgement updatePricingRule(BasicPricingRule basicPricingRule) {
        //TODO
        return null;
    }

    @Override
    public Acknowledgement deletePricingRule(long id) {
        //TODO
        return null;
    }
}
