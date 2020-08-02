package com.rareshop.api.rest.listing.service;

import com.rareshop.api.rest.listing.model.*;
import org.springframework.stereotype.Service;
import rareshop.api.common.core.model.Acknowledgement;

import java.util.List;

@Service
public interface ProductService {
    Acknowledgement addProduct(BasicProductData product);

    List<BasicProduct> getAllProducts();

    BasicProduct getProduct(long id);

    Acknowledgement deleteProduct(long id);

    Acknowledgement publishProduct(long id, boolean published);

    Acknowledgement addPricingRule(long productId, BasicPricingRuleData basicPricingRule);

    Acknowledgement addDiscountRule(long productId, BasicDiscountRuleData basicDiscountRule);

    List<BasicPricingRule> getAllPricingRules(long productId);

    List<BasicDiscountRule> getAllDiscountRules(long productId);
}
