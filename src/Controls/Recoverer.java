package Controls;

import Data.Auth;
import Data.CRUD;
import Entities.Entity;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Recoverer implements Initializable, Window {

    public TableView<Entity> view;
    public Pane loan, repayment;
    public TextField id, client_id, amount, repayment_percentage, term, purpose, repayment_amount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Populate.table(view, "*, DATEDIFF('" + LocalDate.now().toString() + "', due_date) AS days", "Repayment", " 1=1 HAVING DATEDIFF('" + LocalDate.now().toString() + "', due_date) < 3 ORDER BY days");

//        loan.getChildren().forEach( s -> {
//            ((TextField) s).setOnAction( e -> {
//                Populate.updateTable(view, s, "*", "Loan");
//            });
//        });

//        repayment.getChildren().forEach( s -> {
//            if (s instanceof TextField)
//            ((TextField) s).setOnAction( e -> {
//                Populate.updateTable(view, s, "*", "Repayment");
//            });
//            else if (s instanceof DatePicker) {
//                ((DatePicker) s).setOnAction( event -> Populate.updateTable(view, s, "*", "Repayment"));
//            }
//        });

        client_id.setOnAction(e -> {
//            Auth.clients.getClients().put("Client1", );
            Auth.clients.getClients(CRUD.data.select("*", "Client", " id = " + client_id.getText()).get(0));
            System.out.println(Auth.clients.getClients().toString());
            display(Auth.clients.getClients().get("Client1").get(0).get("type"), "");

        });

        view.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    Populate.reversePopulate(repayment, view.getSelectionModel().getSelectedItem().getDetails());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                try {
                    Populate.reversePopulate(loan, CRUD.data.select("*", "Loan", " id = " + view.getSelectionModel().getSelectedItem().getDetails().get("loan_id")).get(0));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
