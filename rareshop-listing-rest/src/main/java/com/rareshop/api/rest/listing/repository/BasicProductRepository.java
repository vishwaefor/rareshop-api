package com.rareshop.api.rest.listing.repository;

import com.rareshop.api.rest.listing.model.BasicProduct;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BasicProductRepository
        extends PagingAndSortingRepository<BasicProduct, Long> {


}
