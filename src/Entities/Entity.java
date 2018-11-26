package Entities;

import java.util.HashMap;

public class Entity {
    public HashMap<String, String> details = new HashMap<>();

    public HashMap<String, String> getDetails() {
        return details;
    }

    public void setDetails(HashMap<String, String> details) {
        this.details = details;
    }

    public Entity() {

    }

    public Entity(HashMap<String, String> details) {
        this.details = details;
    }
}
