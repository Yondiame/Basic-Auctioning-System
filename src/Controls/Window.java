package Controls;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Balogun Muhammad Jr on 5/1/2017.
 */
public interface Window {
    Stage window = new Stage();

    default void display(String view, String title) {
        Parent root = null;
        Scene scene = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/views/" + view.trim() + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(root);
        window.setScene(scene);
        window.setTitle(title);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();

    }

    default void transition(String view, String title, Event e) {
        display(view, title);
        close(e);
    }

    default void loadFxml(String view, AnchorPane pane) {
        AnchorPane newLoadedPane = null;
        try {
            newLoadedPane = FXMLLoader.load(getClass().getResource("/views/" + view + ".fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            pane.getChildren().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pane.getChildren().add(newLoadedPane);
    }

    default void close(Event e) {
        ((Node) e.getSource()).getScene().getWindow().hide();
    }
}
