package com.rareshop.api.rest.listing.service;

import com.rareshop.api.rest.listing.model.BasicDiscountRule;
import com.rareshop.api.rest.listing.model.BasicPricingRule;
import com.rareshop.api.rest.listing.model.BasicProduct;
import org.springframework.stereotype.Service;
import rareshop.api.common.core.discount.DiscountRule;
import rareshop.api.common.core.model.Acknowledgement;
import rareshop.api.common.core.pricing.PricingRule;

import java.util.List;

@Service
public interface ProductService {
    Acknowledgement addProduct(BasicProduct product);

    List<BasicProduct> getAllProducts();

    BasicProduct getProduct(long id);

    Acknowledgement updateProduct(BasicProduct product);

    Acknowledgement deleteProduct(long id);

    Acknowledgement publishProduct(long id, boolean published);

    Acknowledgement addPricingRule(long productId, BasicPricingRule basicPricingRule);

    Acknowledgement deletePricingRule(long pricingRuleId);

    Acknowledgement addDiscountRule(long productId, BasicDiscountRule basicDiscountRule);

    Acknowledgement deleteDiscountRule(long discountRuleId);

    List<BasicPricingRule> getAllPricingRules(long productId);

    List<BasicDiscountRule> getAllDiscountRules(long productId);
}
