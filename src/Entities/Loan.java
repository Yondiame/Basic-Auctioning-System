package Entities;

import Data.CRUD;

import java.util.HashMap;

public class Loan implements CRUD {
    public HashMap<String, String> details = new HashMap<>();

    public HashMap<String, String> getDetails() {
        return details;
    }

    public void setDetails(HashMap<String, String> details) {
        this.details = details;
    }

    public void calculateRepaymentAmount() {
        int percentage = Integer.parseInt(details.get("repayment_percentage"));
        int repaymentAmount = (percentage / 100) * Integer.parseInt(details.get("amount"));
    }
}
