package org.Task.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class ShippingAddress {

    private String street;
    private String city;
    private String country;

    public ShippingAddress(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }

    public ShippingAddress() {
    }
}
