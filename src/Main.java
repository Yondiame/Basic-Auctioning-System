import Data.CRUD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class Main extends Application {

    public Scene primaryScene;
    public Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        primaryScene = new Scene(root);
        primaryScene.setRoot(root);
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(primaryScene);
        window.show();
        System.out.println(CRUD.data.select("DATEDIFF('" + LocalDate.now().toString() + "', due_date) AS days", "Repayment", " 1=1"));
    }
}
