package org.Task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Category extends BaseEntity {
    @Column(unique = true)
    private String name;

    public Category(String name) {
        this.name = name;
    }
    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
