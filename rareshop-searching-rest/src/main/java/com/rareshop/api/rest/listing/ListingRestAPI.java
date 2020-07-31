/*
 * Copyright (c) @Vishwa 2020.
 */

/**
 * Listing API is used to define Product Info and Products
 * Pricing Rules and Discount Rules are set to Products here
 * Used by Admins so needs to implement security later
 * All the info
 */

package com.rareshop.api.rest.listing;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ListingRestAPI {

    public static void main(String[] args) {
        SpringApplication.run(ListingRestAPI.class, args);
    }

}