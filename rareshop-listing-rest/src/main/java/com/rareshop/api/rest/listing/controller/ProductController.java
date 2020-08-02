/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.api.rest.listing.controller;

import com.rareshop.api.rest.listing.constant.APIMapping;
import com.rareshop.api.rest.listing.model.BasicPricingRule;
import com.rareshop.api.rest.listing.model.BasicPricingRuleData;
import com.rareshop.api.rest.listing.model.BasicProduct;
import com.rareshop.api.rest.listing.model.BasicProductData;
import com.rareshop.api.rest.listing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rareshop.api.common.core.model.Acknowledgement;
import rareshop.api.common.core.model.RareResponse;

import java.util.List;

@RestController()
@RequestMapping(APIMapping.PRODUCT_MAPPING)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<RareResponse> getALlProducts() {
        List<BasicProduct> productList = productService.getAllProducts();
        return ResponseEntity.ok(new RareResponse(productList));
    }

    @GetMapping(APIMapping.SINGLE_PRODUCT_MAPPING)
    public ResponseEntity<RareResponse> getProduct(
            @PathVariable(name = APIMapping.PRODUCT_ID_PARAM) long id) {

        BasicProduct product = productService.getProduct(id);
        return ResponseEntity.ok(new RareResponse(product));
    }

    @PostMapping()
    public ResponseEntity<RareResponse> addProduct(
            @RequestBody() BasicProductData product) {

        Acknowledgement response = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RareResponse(response));
    }

    @DeleteMapping(APIMapping.SINGLE_PRODUCT_MAPPING)
    public ResponseEntity<RareResponse> deleteProduct(
            @PathVariable(APIMapping.PRODUCT_ID_PARAM) long id) {

        Acknowledgement response = productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RareResponse(response));
    }

    @PatchMapping(APIMapping.SINGLE_PRODUCT_MAPPING)
    public ResponseEntity<RareResponse> publishProduct(
            @PathVariable(APIMapping.PRODUCT_ID_PARAM) long id,
            @RequestParam(APIMapping.PRODUCT_PUBLISHED_PARAM) boolean published) {

        Acknowledgement response = productService.publishProduct(id, published);
        return ResponseEntity.status(HttpStatus.OK).body(new RareResponse(response));
    }

    @GetMapping(APIMapping.SINGLE_PRODUCT_MAPPING + APIMapping.PRICING_RULE_MAPPING)
    public ResponseEntity<RareResponse> getAllPricingRules(
            @PathVariable(APIMapping.PRODUCT_ID_PARAM) long id) {

        List<BasicPricingRule> pricingRuleList = productService.getAllPricingRules(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RareResponse(pricingRuleList));
    }

    @PostMapping(APIMapping.SINGLE_PRODUCT_MAPPING + APIMapping.PRICING_RULE_MAPPING)
    public ResponseEntity<RareResponse> addPricingRule(
            @PathVariable(APIMapping.PRODUCT_ID_PARAM) long id,
            @RequestBody BasicPricingRuleData pricingRule
    ) {

        Acknowledgement response = productService.addPricingRule(id, pricingRule);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RareResponse(response));
    }
}
