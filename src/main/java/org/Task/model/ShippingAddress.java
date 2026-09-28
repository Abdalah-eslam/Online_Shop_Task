package org.Task.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class ShippingAddress {

    private String street;
    private String city;
    private String country;
}
