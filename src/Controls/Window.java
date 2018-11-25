package Controls;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Balogun Muhammad Jr on 5/1/2017.
 */
public class Window {
    Stage window;
    Parent root;
    Scene scene;

    public Window() {
        this.window = new Stage();
    }

    public static void close(Event e) {
        ((Node) e.getSource()).getScene().getWindow().hide();
    }

    public void display(String view, String title) throws IOException {
        window.setTitle("MoodReader");
        root = FXMLLoader.load(getClass().getResource("/views/" + view.trim() + ".fxml"));
        scene = new Scene(root);
        window.setScene(scene);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();
        window.setTitle(title);
    }

    public void transition(String view, String title, Event e) {
        try {
            display(view, title);
            close(e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
