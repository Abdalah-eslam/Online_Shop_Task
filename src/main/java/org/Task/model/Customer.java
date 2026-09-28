package org.Task.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer extends BaseEntity {
    private String name;
    @Column(unique = true)
    private String email;
    @Embedded
    private ShippingAddress shipingAddress;
    private String phone;
    @OneToMany(mappedBy = "customer" ,cascade = CascadeType.ALL ,orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    public Customer(String name, String email, ShippingAddress shipingAddress, String phone) {
        this.name = name;
        this.email = email;
        this.shipingAddress = shipingAddress;
        this.phone = phone;
    }

    protected Customer() {
    }

    public void addOrder(Order order) {
            orders.add(order);
            order.setCustomer(this);
        }

        public void removeOrder(Order order) {
            orders.remove(order);
            order.setCustomer(null);
        }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ShippingAddress getShipingAddress() {
        return shipingAddress;
    }

    public String getPhone() {
        return phone;
    }

    public Set<Order> getOrders() {
        return orders;
    }
}
