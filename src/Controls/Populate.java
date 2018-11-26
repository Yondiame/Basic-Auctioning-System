package Controls;

import Data.Auth;
import Data.CRUD;
import Entities.Entity;
import Entities.Loan;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Populate {
    static HashMap<String, String> populate(Pane node) {
        HashMap<String, String> details = new HashMap<>();
        node.getChildren().stream().forEach(n -> {
            String id = n.getId();
            String value = "";
            if (n instanceof TextField)
                value = ((TextField) n).getText();
            if (n instanceof PasswordField)
                value = ((PasswordField) n).getText();
            if (n instanceof TextArea)
                value = ((TextArea) n).getText();
            if (n instanceof ComboBox)
                value = (String) ((ComboBox) n).getSelectionModel().getSelectedItem();
            if (n instanceof DatePicker)
                value = ((DatePicker) n).getValue().toString().trim();
            try {
                if (!value.equals(""))
                    details.put(id, value.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return details;
    }

    static void populateClients(AnchorPane boss) {

        boss.getChildren().forEach(n -> {
            if (n instanceof Pane) {
                if (n.getId().equals("Loan1")) {
                    Loan loan = new Loan(Populate.populate((Pane) n));
                    try {
                        loan.calculateRepaymentAmount();
                    } catch (NumberFormatException e) {
                        Dialogue.AlertDialog("Error", "Wrong Input at Amount", "Input only figures in amount");
                        e.printStackTrace();
                    }
                    List<HashMap<String, String>> lists = new ArrayList<>();
                    lists.add(loan.getDetails());
                    Auth.clients.getClients().put("Loan1", lists);
                    Auth.clients.getClients().put("Repayment1", loan.createRepayments());
                }
                if (((Pane) n).getChildren().get(0) instanceof Pane) {
                    List<HashMap<String, String>> list = new ArrayList<>();
                    ((Pane) n).getChildren().forEach(s -> {
                        if (!((TextField) ((Pane) s).getChildren().get(0)).getText().equals("")) {
                            list.add(Populate.populate((Pane) s));
                            System.out.println(s.getId());
                        }
                    });
                    Auth.clients.getClients().put(n.getId(), list);
                    System.out.println(Auth.clients.getClients().toString());
                } else {
                    List<HashMap<String, String>> lists = new ArrayList<>();
                    lists.add(Populate.populate((Pane) n));
                    System.out.println(n.getId());
                    Auth.clients.getClients().put(n.getId(), lists);
                    System.out.println(Auth.clients.getClients().toString());
                }
            }
        });
    }

    //    static
    static void reversePopulateClients(AnchorPane boss) {

        List<HashMap<String, String>> lists = new ArrayList<>();
        boss.getChildren().stream().forEach(n -> {
            if (n instanceof Pane) {
                if (((Pane) n).getChildren().get(0) instanceof Pane)
                    ((Pane) n).getChildren().stream().forEach(s -> {
                        int i = 0;
                        if (s instanceof Pane) {
                            try {
                                reversePopulate((Pane) s, Auth.clients.getClients().get(n.getId()).get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            ++i;
                        }
                    });
                System.out.println(n.getId());
                try {
                    reversePopulate((Pane) n, Auth.clients.getClients().get(n.getId()).get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Auth.clients.getClients().put(n.getId(), lists);
                lists.clear();
            }
        });
    }

    static void reversePopulate(Pane node, HashMap<String, String> details) throws NullPointerException {
        node.getChildren().stream().forEach(n -> {
            if (n instanceof TextField)
                ((TextField) n).setText(details.get(n.getId()));
            if (n instanceof PasswordField)
                ((PasswordField) n).setText(details.get(n.getId()));
            if (n instanceof TextArea)
                ((TextArea) n).setText(details.get(n.getId()));
            if (n instanceof ComboBox)
                ((ComboBox) n).setValue(details.get(n.getId()));
            if (n instanceof DatePicker)
                ((DatePicker) n).setValue(LocalDate.parse(details.get(n.getId())));
        });
    }

    static void table(TableView<Entity> view, String columns, String table, String condition) {
        view.getItems().clear();
        view.getColumns().clear();
        view.refresh();

        List<HashMap<String, String>> data = CRUD.data.select(columns, table, condition);

        data.stream().forEach(h -> {
            view.getItems().add(new Entity(h));
        });

        Callback<TableColumn.CellDataFeatures<Entity, String>, ObservableValue<String>> callBack = param -> param.getValue().getDetails().containsKey(
                param.getTableColumn().getId())
                ? new SimpleStringProperty(param.getValue().getDetails().get(param.getTableColumn().getId()))
                : new SimpleStringProperty("");

        try {
            data.get(0).forEach((k, v) -> {
                TableColumn<Entity, String> col = new TableColumn<>(k);
                col.setId(k);
                col.setCellValueFactory(callBack);
                view.getColumns().add(col);
            });
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    static void updateTable(TableView<Entity> view, Node n, String columns, String table) {
        String value = "";
        String id = "";
        if (n instanceof TextField) {
            value = ((TextField) n).getText();
            id = n.getId();
        }
        if (n instanceof PasswordField) {
            value = ((PasswordField) n).getText();
            id = n.getId();
        }
        if (n instanceof TextArea) {
            value = ((TextArea) n).getText();
            id = n.getId();
        }
        if (n instanceof ComboBox) {
            value = (String) ((ComboBox) n).getSelectionModel().getSelectedItem();
            id = n.getId();
        }
        if (n instanceof DatePicker) {
            value = ((DatePicker) n).getValue().toString();
            id = n.getId();
        }
        Populate.table(view, columns, table, "" + id + " LIKE '%" + value + "%'");
    }
}
