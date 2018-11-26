package Controls;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditManager implements Initializable, Window {

    public ComboBox views;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        views.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue != null)
                display("" + newValue, "");
        });
    }
}
