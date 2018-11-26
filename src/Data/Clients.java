package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Clients {
//    public ArrayList<String> entities = new ArrayList<String>();
//
//    public Clients () {
//        entities.add("Address");
//        entities.add("Bank");
//        entities.add("BankSignatory");
//        entities.add("Business");
//        entities.add("Client");
//        entities.add("ClientGuarantor");
//        entities.add("Collateral");
//        entities.add("Company");
//        entities.add("Corporate");
//        entities.add("Document");
//        entities.add("ExternalLoan");
//        entities.add("Guarantor");
//        entities.add("Identification");
//        entities.add("Individual");
//        entities.add("IndividualDetail");
//        entities.add("LandedProperty");
//        entities.add("Loan");
//        entities.add("Management");
//        entities.add("Repayment");
//        entities.add("SoleProprietor");
//        entities.add("SourceOfFinance");
//        entities.add("Staff");
//        entities.add("StaffClient");
//        entities.add("Vehicle");
//        entities.add("Work");
//    }

    String collateral_id = "";
    private HashMap<String, List<HashMap<String, String>>> clients = new HashMap<>();

    public void setClients() {
        String client_id = "" + CRUD.data.insert("Client", clients.get("Client1").get(0));
        clients.get("Loan1").get(0).put("client_id", client_id);
        String loan_id = "" + CRUD.data.insert("Loan", clients.get("Loan1").get(0));
        clients.get("Guarantor1").get(0).put("loan_id", loan_id);
        String guarantor_id = "" + CRUD.data.insert("Guarantor", clients.get("Guarantor1").get(0));

        if (clients.containsKey("Collateral1")) {
            if (!clients.get("Collateral1").isEmpty()) {
                clients.get("Collateral1").get(0).put("loan_id", loan_id);
                collateral_id = "" + CRUD.data.insert("Collateral", clients.get("Collateral1").get(0));
            }
        }

        clients.get("Bank1").forEach((l) -> {
            l.put("client_id", client_id);
            l.put("loan_id", loan_id);
            String bank_id = "" + CRUD.data.insert("Bank", l);
            if (clients.containsKey("Corporate"))
                clients.get("BankSignatory1").forEach(s -> {
                    if (s.get("bank").equals(l.get("name"))) {
                        s.put("bank_id", bank_id);
                        CRUD.data.insert("BankSignatory", s);
                    }
                });
        });

        clients.forEach((k, v) -> {
            if (!k.equals("Bank1") && !k.equals("Loan1") && !k.equals("Client1") && !k.equals("BankSignatory1") && !k.equals("Collateral1") && !k.equals("Guarantor1"))
                v.stream().forEach(l -> {
                    if (k.contains("2")) {
                        if (CRUD.data.column(k.substring(0, k.length() - 1)).contains("guarantor_id")) {
                            l.put("guarantor_id", guarantor_id);
                        }
                    } else if (k.contains("1")) {
                        if (CRUD.data.column(k.substring(0, k.length() - 1)).contains("client_id")) {
                            l.put("client_id", client_id);
                        }
                    } else if (k.contains("3")) {
                        if (CRUD.data.column(k.substring(0, k.length() - 1)).contains("guarantor_id")) {
                            l.put("guarantor_id", guarantor_id);
                        }
                        if (CRUD.data.column(k.substring(0, k.length() - 1)).contains("client_id")) {
                            l.put("client_id", client_id);
                        }
                    }
                    if (CRUD.data.column(k.substring(0, k.length() - 1)).contains("collateral_id")) {
                        l.put("collateral_id", collateral_id);
                    }
                    if (CRUD.data.column(k.substring(0, k.length() - 1)).contains("loan_id")) {
                        l.put("loan_id", loan_id);
                    }
                    CRUD.data.insert(k.substring(0, k.length() - 1), l);
                });
        });
    }

    public void updateClients() {
        clients.forEach((k, v) -> {
            v.stream().forEach(l -> {
                CRUD.data.update(k.substring(0, k.length() - 1), l, " id = " + l.get("id"));
            });
        });
    }

    public HashMap<String, List<HashMap<String, String>>> getClients(HashMap<String, String> client) throws NullPointerException, IndexOutOfBoundsException {
        HashMap<String, List<HashMap<String, String>>> cli = new HashMap<>();
        String client_id = client.get("id");
        String type = client.get("type");
        String status = client.get("status");
        ArrayList<HashMap<String, String>> cliet = new ArrayList<>();
        cliet.add(client);
        cli.put("Client1", cliet);
        cli.put("Loan1", CRUD.data.select("*", "Loan", " client_id = " + client_id + " ORDER BY id DESC"));

        if (type.equals("Individual1") || type.equals("Banker") || type.equals("Investors")) {
            cli.put("IndividualDetail1", CRUD.data.select("*", "IndividualDetail", " client_id = " + client_id));
            cli.put("Work1", CRUD.data.select("*", "Work", " loan_id = " + cli.get("Loan1").get(0).get("id")));
        }

        if (type.equals("Corporate")) {
            cli.put("Corporate1", CRUD.data.select("*", "Corporate", " client_id = " + client_id));
            cli.put("SourceOfFinance1", CRUD.data.select("*", "SourceOfFinance", " loan_id = " + cli.get("Loan1").get(0).get("id")));
            cli.put("Management1", CRUD.data.select("*", "Management", " loan_id = " + cli.get("Loan1").get(0).get("id")));
            cli.put("BankSignatory1", CRUD.data.select("*", "BankSignatory", " loan_id = " + cli.get("Loan1").get(0).get("id")));
        }
        if (type.equals("soleProprietor")) {
            cli.put("IndividualDetail1", CRUD.data.select("*", "IndividualDetail", " client_id = " + client_id));
            cli.put("Business1", CRUD.data.select("*", "Business", " client_id = " + client_id));
        }

        cli.put("ExternalLoan1", CRUD.data.select("*", "ExternalLoan", " loan_id = " + cli.get("Loan1").get(0).get("id")));
        cli.put("Bank1", CRUD.data.select("*", "Bank", " loan_id = " + cli.get("Loan1").get(0).get("id")));
        cli.put("Guarantor1", CRUD.data.select("Guarantor.*", "Guarantor , ClientGuarantor", " Guarantor.id = ClientGuarantor.guarantor_id" + " AND ClientGuarantor.loan_id = " + cli.get("Loan1").get(0).get("id")));
        cli.put("Company2", CRUD.data.select("*", "Company", " loan_id = " + cli.get("Loan1").get(0).get("id") + " AND guarantor_id = " + cli.get("Guarantor1").get(0).get("id")));
        cli.put("ClientGuarantor3", CRUD.data.select("*", "ClientGuarantor", " loan_id = " + cli.get("Loan1").get(0).get("id")));
        cli.put("Address1", CRUD.data.select("*", "Address", " loan_id = " + cli.get("Loan1").get(0).get("id") + " AND client_id = " + client_id));
        cli.put("Address2", CRUD.data.select("*", "Address", " loan_id = " + cli.get("Loan1").get(0).get("id") + " AND guarantor_id = " + cli.get("Guarantor1").get(0).get("id")));
        cli.put("Identification1", CRUD.data.select("*", "Identification", " loan_id = " + cli.get("Loan1").get(0).get("id") + " AND client_id = " + client_id));
        cli.put("Identification2", CRUD.data.select("*", "Identification", " loan_id = " + cli.get("Loan1").get(0).get("id") + " AND guarantor_id = " + cli.get("Guarantor1").get(0).get("id")));
        cli.put("Document1", CRUD.data.select("*", "Document", " client_id = " + client_id + " AND loan_id = " + cli.get("Loan1").get(0).get("id")));
        cli.put("Collateral1", CRUD.data.select("*", "Collateral", " loan_id = " + cli.get("Loan1").get(0).get("id")));
        if (!cli.get("Collateral1").isEmpty())
            cli.put(cli.get("Collateral1").get(0).get("type"), CRUD.data.select("*", cli.get("Collateral1").get(0).get("type"), " id = " + cli.get("Collateral1").get(0).get("id")));
        cli.put("Repayment1", CRUD.data.select("*", "Repayment", " loan_id = " + cli.get("Loan1").get(0).get("id")));

        this.clients = cli;
        return clients;
    }

    public HashMap<String, List<HashMap<String, String>>> getClients() {
        return clients;
    }

    public void setClients(HashMap<String, List<HashMap<String, String>>> clients) {
        this.clients = clients;
    }
}
