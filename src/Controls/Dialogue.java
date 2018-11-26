package Controls;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by Balogun Muhammad Jr on 4/28/2017.
 */
public interface Dialogue {
    static boolean ConfirmationDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // creates an object of Alert.
        alert.setTitle(title);//sets the alert title to "title"
        alert.setHeaderText(header); //sets the alert header text to "header"
        alert.setContentText(content); //sets the alert content to "content"

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    static void AlertDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
