package Controls;

import Data.Auth;
import Entities.Entity;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class Appraiser implements Initializable, Window {

    public TableView<Entity> clients, loans;
    public Button editClient, x;
    public ComboBox addClient;
    public TextField search;
    public AnchorPane pane;

    HashMap<String, List<HashMap<String, String>>> cli = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Populate.table(clients, "*", "client", " 1=1 ");
        clients.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                Auth.clients.getClients(clients.getSelectionModel().getSelectedItem().getDetails());
                Populate.table(loans, "*", "Loan", " client_id = " + Auth.clients.getClients().get("Client1").get(0).get("id"));

            }
        });

        addClient.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null) {
                List<HashMap<String, String>> li = new ArrayList<>();
                li.add(new HashMap<>());
                li.get(0).put("type", (String) newValue);
                li.get(0).put("status", "first_timer");
                Auth.clients.getClients().clear();
                Auth.clients.getClients().put("Client1", li);


                if (newValue.equals("Individual"))
                    display("Individual", "");
                else if (newValue.equals("Investors"))
                    display("Individual", "");
                else if (newValue.equals("Banker"))
                    display("Individual", "");
                else if (newValue.equals("Corporate"))
                    display("corporate", "");
                else if (newValue.equals("Sole Proprietor"))
                    display("soleProprietor", "");
            }
        });

        x.setOnAction(this::close);
    }
}
