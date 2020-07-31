/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.api.rest.listing.impl.service;

import com.rareshop.api.rest.listing.constant.ErrorMessage;
import com.rareshop.api.rest.listing.constant.InfoMessage;
import com.rareshop.api.rest.listing.impl.repository.BasicProductInfoRepository;
import com.rareshop.api.rest.listing.model.BasicProductInfo;
import com.rareshop.api.rest.listing.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rareshop.api.common.core.constant.Params;
import rareshop.api.common.core.exception.BadRequestException;
import rareshop.api.common.core.exception.NotFoundException;
import rareshop.api.common.core.model.Acknowledgement;
import rareshop.api.common.core.product.ProductInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BasicProductInfoService implements ProductInfoService {

    @Autowired
    private BasicProductInfoRepository repository;

    public void setRepository(BasicProductInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Acknowledgement addProductInfo(BasicProductInfo productInfo) {

        if (productInfo.getName() == null || productInfo.getName().isBlank()) {
            throw new BadRequestException(ErrorMessage.NAME_IS_BLANK);
        }

        if (productInfo.getDescription() == null || productInfo.getDescription().isBlank()) {
            throw new BadRequestException(ErrorMessage.DESCRIPTION_IS_BLANK);
        }

        ProductInfo saved = repository.save(productInfo);
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(Params.ID, saved.getId());

        return new Acknowledgement(InfoMessage.PRODUCT_INFO_CREATED, payload);
    }

    @Override
    public List<BasicProductInfo> getAllProductInfo() {
        List<BasicProductInfo> productInfoList = new ArrayList<>();
        repository.findAll().forEach(productInfoList::add);
        return productInfoList;

    }

    @Override
    public BasicProductInfo getProductInfo(long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PRODUCT_INFO_NOT_FOUND));
    }

    @Override
    public Acknowledgement updateProductInfo(BasicProductInfo productInfo) {
        if (productInfo.getId() <= 0L) {
            throw new BadRequestException(ErrorMessage.ID_IS_BLANK);
        }

        if (productInfo.getName() == null || productInfo.getName().isBlank()) {
            throw new BadRequestException(ErrorMessage.NAME_IS_BLANK);
        }

        if (productInfo.getDescription() == null || productInfo.getDescription().isBlank()) {
            throw new BadRequestException(ErrorMessage.DESCRIPTION_IS_BLANK);
        }

        if (repository.existsById(productInfo.getId())) {
            repository.save(productInfo);
            HashMap<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, productInfo.getId());
            return new Acknowledgement(InfoMessage.PRODUCT_INFO_UPDATED, payload);
        } else {
            throw new NotFoundException(ErrorMessage.PRODUCT_INFO_NOT_FOUND);
        }
    }

    @Override
    public Acknowledgement deleteProductInfo(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            HashMap<String, Object> payload = new HashMap<>();
            payload.put(Params.ID, id);
            return new Acknowledgement(InfoMessage.PRODUCT_INFO_DELETED, payload);
        } else {
            throw new NotFoundException(ErrorMessage.PRODUCT_INFO_NOT_FOUND);
        }
    }

    @Override
    public Acknowledgement publishProductInfo(long id, boolean published) {

        BasicProductInfo basicProductInfo = getProductInfo(id);

        basicProductInfo.setPublished(published);

        repository.save(basicProductInfo);

        HashMap<String, Object> payload = new HashMap<>();
        payload.put(Params.ID, id);

        return new Acknowledgement(
                published ? InfoMessage.PRODUCT_INFO_PUBLISHED :
                        InfoMessage.PRODUCT_INFO_UNPUBLISHED, payload);


    }

}
