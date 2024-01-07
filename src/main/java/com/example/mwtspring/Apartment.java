package com.example.mwtspring;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Apartment {

    @Id
    private String apartmentName;

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }
}
