package com.inosphere.friday.address;

import lombok.Value;

@Value
public class Address {
    public static final Address emptyAddress = new Address("", "");

    private String street;
    private String houseNumber;

    public Address(String street) {
        this.street = (street != null) ? street : "";
        this.houseNumber = "";
    }

    public Address(String street, String houseNumber) {
        this.street = street;
        this.houseNumber = houseNumber;
    }
}
