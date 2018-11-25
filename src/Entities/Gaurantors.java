package Entities;

import Data.CRUD;

import java.util.HashMap;

public class Gaurantors implements CRUD {
    public HashMap<String, String> details = new HashMap<>();

    public HashMap<String, String> getDetails() {
        return details;
    }

    public void setDetails(HashMap<String, String> details) {
        this.details = details;
    }
}
