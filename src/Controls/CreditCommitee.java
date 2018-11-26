package Controls;

import Entities.Entity;
import Entities.Loan;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditCommitee implements Initializable {
    public TableView<Entity> view;
    public Pane loan;
    public Button confirm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Populate.table(view, "*", "Loan", " status = 'pending'");

        view.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    Populate.reversePopulate(loan, view.getSelectionModel().getSelectedItem().getDetails());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        confirm.setOnAction(e -> {
            new Loan(Populate.populate(loan)).update();
            Populate.table(view, "*", "Loan", " status = 'pending'");
        });

    }
}
