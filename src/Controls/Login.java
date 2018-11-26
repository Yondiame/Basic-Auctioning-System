package Controls;

import Data.CRUD;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable, Window {

    public Pane login;
    public TextField uname;
    public PasswordField password;
    public Button sign, x;
    String role;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sign.setOnAction(e -> {
            try {
                role = CRUD.data.select("role", "Staff", " name = '" + uname.getText() + "' AND password = '" + password.getText() + "'").get(0).get("role");
            } catch (Exception p) {
                Dialogue.AlertDialog("Login", "Moimoi", "wrong user name and password");
                p.printStackTrace();
            }
            transition(role, role.toUpperCase(), e);
        });

        x.setOnAction(this::close);
    }
}
