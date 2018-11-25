package Entities;

import Data.CRUD;

import java.util.HashMap;

public class Staff implements CRUD {
    public HashMap<String, String> details = new HashMap<>();

    public Staff() {

    }

    public Staff(HashMap<String, String> details) {
        this.details = details;
    }

    public HashMap<String, String> getDetails() {
        return details;
    }

    public void setDetails(HashMap<String, String> details) {
        this.details = details;
    }
}