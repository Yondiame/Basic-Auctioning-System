package Entities;

import Data.CRUD;

import java.util.HashMap;

public class Address implements CRUD {
    public HashMap<String, String> details = new HashMap<>();

    public Address() {

    }

    public Address(HashMap<String, String> details) {
        this.details = details;
    }

    public HashMap<String, String> getDetails() {
        return details;
    }

    public void setDetails(HashMap<String, String> details) {
        this.details = details;
    }
}
