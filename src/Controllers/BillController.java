package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import models.Item;

public class BillController implements Initializable {

    @FXML
    private TextArea billingArea;
    @FXML
    private Label retailLabel;
    private static double retail;
    private static ObservableList<Item> items;
    private static String barcode;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        retailLabel.setText("Change: Rs." + retail);

        StringBuilder details = new StringBuilder("Item Name\t" + "Cost\t\t" + "\tQuantity\t\t" + "\tTotal\n\n");

        POSController pos = new POSController();
        for(int i =0;i<pos.itemNames.size();i++){
//            for (Item k : items) {
                details.append(POSController.itemNames.get(i))
                        .append("\t\t\t")
                        .append(POSController.prices.get(i))
                        .append("\t\t\t")
                        .append(POSController.quantities.get(i))
                        .append("\t\t\t")
                        .append(POSController.subTotals.get(i))
                        .append("\n");
//            }
        }


//        for (Item i : items) {
//            details.append(i.getItemName())
//                    .append("\t\t\t")
//                    .append(i.getUnitPrice())
//                    .append("\t\t\t")
//                    .append(i.getQuantity())
//                    .append("\t\t\t")
//                    .append(i.getTotal())
//                    .append("\n");
//        }

        billingArea.setText(details.toString());
    }

    public void setData(double retail, ObservableList<Item> items, String barcode) {
        this.retail = retail;
        this.items = items;
        this.barcode = barcode;
    }

    @FXML
    public void doneAction(ActionEvent event) {
        billingArea.setText("");
        PrintController pi = new PrintController(items, barcode);
        pi.generateReport();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}