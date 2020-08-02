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


import com.rareshop.api.rest.listing.model.BasicProductInfo;
import com.rareshop.api.rest.listing.repository.BasicProductInfoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ListingRestAPI {

    public static void main(String[] args) {
        SpringApplication.run(ListingRestAPI.class, args);
    }

    /**
     * Uncomment @Bean to populate DB
     * Comment @Bean to run unit tests
     *
     * @param repository
     * @return
     */
    //@Bean
    public CommandLineRunner demo(BasicProductInfoRepository repository) {
        return (args) -> {
            BasicProductInfo penguinEar = new BasicProductInfo();
            penguinEar.setName("Penguin Ear");
            penguinEar.setDescription("Penguin Ear is a rare asset. " +
                    "When you look at a penguin's head you can see their eyes," +
                    " their beak, even a tongue if they open up. But you will not see any ears.");
            penguinEar.setImageURI("https://www.dropbox.com/s/d427597breh9x87/pengin-ear.JPG?dl=0");
            repository.save(penguinEar);

            BasicProductInfo horsehoe = new BasicProductInfo();
            horsehoe.setName("Horsehoe");
            horsehoe.setDescription("A horseshoe is a fabricated product, normally made of metal," +
                    " although sometimes made partially or wholly of modern synthetic materials," +
                    " designed to protect a horse hoof from wear");
            horsehoe.setImageURI("https://www.dropbox.com/s/qjn8r22yprv4inx/horse-hoe.jpg?dl=0");
            repository.save(horsehoe);
        };
    }
}