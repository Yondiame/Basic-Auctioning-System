package Controls;

import Data.Auth;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Guarantors implements Initializable, Window {

    public AnchorPane boss;
    public Button confirm, x;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Populate.reversePopulateClients(boss);

        confirm.setOnAction(e -> {
            Populate.populateClients(boss);
            Auth.clients.setClients();
            close(e);
        });

        x.setOnAction(this::close);
    }
}
