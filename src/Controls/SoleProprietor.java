package Controls;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SoleProprietor implements Initializable, Window {

    public AnchorPane boss;
    public Button confirm, x;
    public TextField office_number, email;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Populate.reversePopulateClients(boss);

        confirm.setOnAction(e -> {
            Populate.populateClients(boss);
            transition("guarantors", "", e);
        });

        x.setOnAction(this::close);
    }

}
