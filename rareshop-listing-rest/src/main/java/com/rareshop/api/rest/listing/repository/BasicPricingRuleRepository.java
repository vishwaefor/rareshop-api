package com.rareshop.api.rest.listing.repository;

import com.rareshop.api.rest.listing.model.BasicPricingRule;
import com.rareshop.api.rest.listing.model.BasicProduct;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BasicPricingRuleRepository
        extends PagingAndSortingRepository<BasicPricingRule, Long> {

    List<BasicPricingRule> findByBasicProduct(BasicProduct product);
}
