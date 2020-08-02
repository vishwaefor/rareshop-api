/*
 * Copyright (c) @Vishwa 2020.
 */

package com.rareshop.api.rest.listing.repository;

import com.rareshop.api.rest.listing.model.BasicProductInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BasicProductInfoRepository
        extends PagingAndSortingRepository<BasicProductInfo, Long> {

}