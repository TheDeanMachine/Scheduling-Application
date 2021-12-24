package controller;

import DAO.AppointmentsDAO;
import DAO.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customers;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersScreen extends SuperController implements Initializable {

    /// Customers TableView Fields fx:id ///
    @FXML
    private TableView<Customers> customersTableView;

    @FXML
    private TableColumn<Customers, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customers, String> customerNameColumn;

    @FXML
    private TableColumn<Customers, String> addressColumn;

    @FXML
    private TableColumn<Customers, String> postalCodeColumn;

    @FXML
    private TableColumn<Customers, String> phoneColumn;

    @FXML
    private TableColumn<Customers, String> divisionIdColumn;

    @FXML
    private TableColumn<Customers, String> countryColumn;

    /// Customers Button Fields fx:id ///
    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(backButton, "/view/Appointments.fxml");
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws IOException {
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        Alert alertConf = new Alert(Alert.AlertType.CONFIRMATION);

        if(customersTableView.getSelectionModel().isEmpty()) {
            alertInfo.setHeaderText("Please select a customer to delete");
            alertInfo.showAndWait();
            return;
        } else {
            alertConf.setTitle("Confirmation Dialog");
            alertConf.setHeaderText("Are you sure you want to delete this customer?");
            alertConf.setContentText("Press ok to delete, and cancel to go back");

            Optional<ButtonType> result = alertConf.showAndWait();
            if (result.get() == ButtonType.OK) {
                // get users selection
                Customers selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
                int selection = selectedCustomer.getCustomerId();

                // all the customer’s appointments must be deleted first
                AppointmentsDAO.deleteCustomerAppointments(selection);

                // delete the customer
                CustomerDAO dao = new CustomerDAO();
                dao.delete(selection);

                // custom message display in the user interface after deletion
                alertInfo.setHeaderText("Customer has been deleted");
                alertInfo.setContentText("There associated appointments where also deleted");
                alertInfo.showAndWait();

                // refresh the tableview
                customersTableView.setItems(dao.read());
            } else {
                return;
            }
        }
    }

    @FXML
    void onActionOpenAddForm(ActionEvent event) throws IOException {
        displayNewScreen(addButton, "/view/AddCustomer.fxml");
    }

    @FXML
    void onActionOpenModifyFrom(ActionEvent event) throws IOException {
        // if the user selects the modify button, without selecting an item display an alert box
        if(customersTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Please select a customer to modify");
            alert.showAndWait();
            return;
        } else {
            // get user
            Customers selectedItem = customersTableView.getSelectionModel().getSelectedItem();
            // pass the user to modify form
            ModifyCustomer.holdData(selectedItem);
        }

        displayNewScreen(modifyButton, "/view/ModifyCustomer.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the customers' tableview with the data it will be working with
        customersTableView.setItems(new CustomerDAO().read());

        // set the columns with the data
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

    }
}
