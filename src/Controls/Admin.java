package Controls;

import Entities.Entity;
import Entities.Staff;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Admin implements Initializable {

    public Pane staff;
    public Button add, edit, delete, close;
    public TableView<Entity> view;

    public TextField name, password, phone_number, id;
    public ComboBox role;
    public TextArea address;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Populate.table(view, "*", "Staff", "1=1");
        add.setOnAction(e -> {
            Entity n = new Entity(Populate.populate(staff));
            new Staff(n.getDetails()).create();
            view.getItems().add(n);
        });

        delete.setOnAction(e -> {
            Entity s = view.getSelectionModel().getSelectedItem();
            new Staff(s.getDetails()).delete();
            view.getItems().remove(s);
            view.refresh();
        });

        edit.setOnAction(e -> {
            view.getSelectionModel().getSelectedItem().setDetails(Populate.populate(staff));
            new Staff(Populate.populate(staff)).update();
            view.refresh();
        });

        view.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Entity p = view.getSelectionModel().getSelectedItem();
                id.setText(p.getDetails().get("id"));
                name.setText(p.getDetails().get("name"));
                password.setText(p.getDetails().get("password"));
                phone_number.setText(p.getDetails().get("phone_number"));
                address.setText(p.getDetails().get("address"));
                role.setValue(p.getDetails().get("role"));
            }
        });

        name.setOnAction(e -> {
            Populate.updateTable(view, name, "*", "Staff");
        });

        password.setOnAction(e -> {
            Populate.updateTable(view, password, "*", "Staff");
        });

        phone_number.setOnAction(e -> {
            Populate.updateTable(view, phone_number, "*", "Staff");
        });

        role.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null)
                Populate.updateTable(view, role, "*", "Staff");
        });

        close.setOnAction(Window::close);
    }
}
