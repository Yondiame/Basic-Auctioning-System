package Entities;

import Data.CRUD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Loan implements CRUD {
    public HashMap<String, String> details = new HashMap<>();

    public Loan(HashMap<String, String> details) {
        this.details = details;
    }

    public Loan() {

    }

    public HashMap<String, String> getDetails() {
        return details;
    }

    public void setDetails(HashMap<String, String> details) {
        this.details = details;
    }

    public void calculateRepaymentAmount() throws NumberFormatException {
        int repaymentAmount = ((Integer.parseInt(details.get("amount")) * Integer.parseInt(details.get("repayment_percentage")) * Integer.parseInt(details.get("term"))) / 100) + Integer.parseInt(details.get("amount"));
        details.put("repayment_amount", "" + repaymentAmount);
    }

    public List<HashMap<String, String>> createRepayments() {
        List<HashMap<String, String>> lists = new ArrayList<>();
        HashMap<String, String> hash = new HashMap<>();

        LocalDate date = LocalDate.now();
        int amount = Integer.parseInt(details.get("repayment_amount")) / Integer.parseInt(details.get("term"));


        for (int i = 1; i <= Integer.parseInt(details.get("term")); ++i) {
            date = date.plusMonths(1);
            hash.put("due_date", date.toString());
            hash.put("amount", "" + amount);
            hash.put("repayment_method", details.get("repayment_method"));
            lists.add(hash);
        }
        return lists;
    }
}
