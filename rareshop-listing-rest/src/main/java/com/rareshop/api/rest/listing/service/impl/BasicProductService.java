package com.rareshop.api.rest.listing.service.impl;

import com.rareshop.api.rest.listing.constant.ErrorMessage;
import com.rareshop.api.rest.listing.constant.InfoMessage;
import com.rareshop.api.rest.listing.model.*;
import com.rareshop.api.rest.listing.repository.BasicPricingRuleRepository;
import com.rareshop.api.rest.listing.repository.BasicProductInfoRepository;
import com.rareshop.api.rest.listing.repository.BasicProductRepository;
import com.rareshop.api.rest.listing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rareshop.api.common.core.constant.Params;
import rareshop.api.common.core.exception.BadRequestException;
import rareshop.api.common.core.exception.ForbiddenException;
import rareshop.api.common.core.exception.NotFoundException;
import rareshop.api.common.core.model.Acknowledgement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BasicProductService implements ProductService {

    @Autowired
    private BasicProductRepository repository;

    @Autowired
    private BasicProductInfoRepository productInfoRepository;

    @Autowired
    private BasicPricingRuleRepository pricingRuleRepository;

    /**
     * Used for testing purposes.
     *
     * @param repository
     * @InjectMocks Not working since need to reset after each run due to usage of @Nested
     */
    public void setRepository(BasicProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Used for testing purposes.
     *
     * @param repository
     * @InjectMocks Not working since need to reset after each run due to usage of @Nested
     */
    public void setProductInfoRepository(BasicProductInfoRepository repository) {
        this.productInfoRepository = repository;
    }

    /**
     * Used for testing purposes.
     *
     * @param repository
     * @InjectMocks Not working since need to reset after each run due to usage of @Nested
     */
    public void setPricingRuleRepository(BasicPricingRuleRepository repository) {
        this.pricingRuleRepository = repository;
    }


    @Override
    public Acknowledgement addProduct(BasicProductData product) {

        if (product.getName() == null || product.getName().isBlank()) {
            throw new BadRequestException(ErrorMessage.NAME_NOT_SET);
        }

        if (product.getProductInfoId() == 0) {
            throw new BadRequestException(ErrorMessage.INVALID_PRODUCT_INFO);
        }

        if (!productInfoRepository.existsById(product.getProductInfoId())) {
            throw new NotFoundException(ErrorMessage.PRODUCT_INFO_NOT_FOUND);
        }

        BasicProduct saved = repository.save(new BasicProduct(product));
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(Params.ID, saved.getId());

        return new Acknowledgement(InfoMessage.PRODUCT_CREATED, payload);
    }

    @Override
    public List<BasicProduct> getAllProducts() {
        List<BasicProduct> productList = new ArrayList<>();
        repository.findAll().forEach(productList::add);
        return productList;
    }

    @Override
    public BasicProduct getProduct(long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public Acknowledgement deleteProduct(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            HashMap<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, id);
            return new Acknowledgement(InfoMessage.PRODUCT_DELETED, payload);
        } else {
            throw new NotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public Acknowledgement publishProduct(long id, boolean published) {
        BasicProduct basicProduct = getProduct(id);

        if (published && !basicProduct.isPublished()) {
            throw new ForbiddenException(ErrorMessage.PRODUCT_INFO_NOT_PUBLISHED);
        }

        basicProduct.setPublished(published);

        repository.save(basicProduct);

        HashMap<String, Object> payload = new HashMap<>();
        payload.put(Params.ID, id);

        return new Acknowledgement(
                published ? InfoMessage.PRODUCT_PUBLISHED :
                        InfoMessage.PRODUCT_UNPUBLISHED, payload);
    }

    @Override
    public Acknowledgement addPricingRule(long productId, BasicPricingRuleData basicPricingRuleData) {

        if (basicPricingRuleData.getUnitId() == 0) {
            throw new BadRequestException(ErrorMessage.UNIT_NOT_SET);
        }

        if (basicPricingRuleData.getUnitPrice() <= 0) {
            throw new BadRequestException(ErrorMessage.INVALID_UNIT_PRICE);
        }


        if (basicPricingRuleData.isAdditionalPricingFactorUsed()
                && basicPricingRuleData.getAdditionalPricingFactor() < 1) {
            throw new BadRequestException(ErrorMessage.INVALID_ADDITIONAL_FACTOR);
        }


        BasicProduct basicProduct = getProduct(productId);
        basicProduct.getUnits()
                .stream()
                .filter(u -> u.getId() == basicPricingRuleData.getUnitId())
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ErrorMessage.INVALID_UNIT));


        BasicPricingRule basicPricingRule = new BasicPricingRule(basicPricingRuleData);
        basicPricingRule.setBasicProduct(basicProduct);
        BasicPricingRule saved = pricingRuleRepository.save(basicPricingRule);

        HashMap<String, Object> payload = new HashMap<>();
        payload.put(Params.ID, saved.getId());

        return new Acknowledgement(InfoMessage.PRICING_RULE_ADDED, payload);
    }

    @Override
    public Acknowledgement addDiscountRule(long productId, BasicDiscountRuleData basicDiscountRule) {
        return null;
    }

    @Override
    public List<BasicPricingRule> getAllPricingRules(long productId) {

        BasicProduct basicProduct = getProduct(productId);

        List<BasicPricingRule> pricingRuleList = new ArrayList<>();
        pricingRuleRepository.findByBasicProduct(basicProduct).forEach(pricingRuleList::add);
        return pricingRuleList;
    }

    @Override
    public List<BasicDiscountRule> getAllDiscountRules(long productId) {
        return null;
    }
}
