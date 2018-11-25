package Controls;

import Data.CRUD;
import Entities.Entity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class Populate {
    public static HashMap<String, String> populate(Pane node) {
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
                value = ((DatePicker) n).getValue().format(DateTimeFormatter.ofPattern("YYYY-MM-DD"));
            if (value != "")
                details.put(id, value);
        });
        return details;
    }

    public static void table(TableView<Entity> view, String columns, String table, String condition) {
        view.getItems().clear();
        view.getColumns().clear();
        view.refresh();

        new Thread(() -> {

        }).start();
        List<HashMap<String, String>> data = CRUD.data.select(columns, table, condition);

        data.stream().forEach(h -> {
            view.getItems().add(new Entity(h));
        });

        Callback<TableColumn.CellDataFeatures<Entity, String>, ObservableValue<String>> callBack = param -> new SimpleStringProperty(param.getValue().getDetails().get(param.getTableColumn().getId()));

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

    public static void updateTable(TableView<Entity> view, Node n, String columns, String table) {
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
            value = ((DatePicker) n).getValue().format(DateTimeFormatter.ofPattern("YYYY-MM-DD"));
            id = n.getId();
        }
        Populate.table(view, columns, table, "" + id + " LIKE '%" + value + "%'");
    }
}
