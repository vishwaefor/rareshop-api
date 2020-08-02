/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.api.rest.listing.controller;

import com.rareshop.api.rest.listing.constant.APIMapping;
import com.rareshop.api.rest.listing.model.BasicProductInfo;
import com.rareshop.api.rest.listing.model.BasicProductInfoData;
import com.rareshop.api.rest.listing.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rareshop.api.common.core.model.Acknowledgement;
import rareshop.api.common.core.model.RareResponse;

import java.util.List;

@RestController()
@RequestMapping(APIMapping.PRODUCT_INFO_MAPPING)
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @GetMapping()
    public ResponseEntity<RareResponse> getALlProductInfo() {
        List<BasicProductInfo> productInfoList = productInfoService.getAllProductInfo();
        return ResponseEntity.ok(new RareResponse(productInfoList));
    }

    @GetMapping(APIMapping.SINGLE_PRODUCT_INFO_MAPPING)
    public ResponseEntity<RareResponse> getProductInfo(
            @PathVariable(name = APIMapping.PRODUCT_INFO_ID_PARAM) long id) {

        BasicProductInfo productInfo = productInfoService.getProductInfo(id);
        return ResponseEntity.ok(new RareResponse(productInfo));
    }

    @PostMapping()
    public ResponseEntity<RareResponse> addProductInfo(
            @RequestBody() BasicProductInfoData productInfo) {

        Acknowledgement response = productInfoService.addProductInfo(productInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RareResponse(response));
    }

    @PutMapping(APIMapping.SINGLE_PRODUCT_INFO_MAPPING)
    public ResponseEntity<RareResponse> updateProductInfo(
            @PathVariable(APIMapping.PRODUCT_INFO_ID_PARAM) long productInfoId,
            @RequestBody() BasicProductInfoData productInfo) {


        Acknowledgement response = productInfoService
                .updateProductInfo(new BasicProductInfo(productInfoId, productInfo));
        return ResponseEntity.status(HttpStatus.OK).body(new RareResponse(response));
    }


    @DeleteMapping(APIMapping.SINGLE_PRODUCT_INFO_MAPPING)
    public ResponseEntity<RareResponse> deleteProductInfo(
            @PathVariable(APIMapping.PRODUCT_INFO_ID_PARAM) long id) {

        Acknowledgement response = productInfoService.deleteProductInfo(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RareResponse(response));
    }

    @PatchMapping(APIMapping.SINGLE_PRODUCT_INFO_MAPPING)
    public ResponseEntity<RareResponse> publishProductInfo(
            @PathVariable(APIMapping.PRODUCT_INFO_ID_PARAM) long id,
            @RequestParam(APIMapping.PRODUCT_INFO_PUBLISHED_PARAM) boolean published) {

        Acknowledgement response = productInfoService.publishProductInfo(id, published);
        return ResponseEntity.status(HttpStatus.OK).body(new RareResponse(response));
    }


}
