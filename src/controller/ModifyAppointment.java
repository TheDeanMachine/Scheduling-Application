package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomerDAO;
import DAO.UsersDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import utilities.TimeHelper;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ModifyAppointment extends SuperController implements Initializable {

    /// Appointment Fields fx:id ///
    @FXML
    private TextField appointmentIdText;

    @FXML
    private ComboBox<Customers> customerIdComboBox;

    @FXML
    private ComboBox<Users> userIdComboBox;

    @FXML
    private ComboBox<Contacts> contactComboBox;

    @FXML
    private ComboBox<Appointments> typeComboBox;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private ComboBox<LocalTime> appointmentStartComboBox;

    @FXML
    private ComboBox<LocalTime> appointmentEndComboBox;

    @FXML
    private Button cancelButton;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(cancelButton, "/view/Appointments.fxml");
    }

    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {
         Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        // collect information and check for nulls
        int appointmentId = Integer.parseInt(appointmentIdText.getText());
        int customerId = customerIdComboBox.getValue().getCustomerId();
        int userId = userIdComboBox.getValue().getUserID();
        int contactId = contactComboBox.getValue().getContactId();
        String type = typeComboBox.getValue().getType();

        // only place where user could set a null in modify appointment
        String title = null;
        try {
            title = titleText.getText();
            if(title == null || titleText.getText().isBlank()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please enter an appointment Title");
            errorAlert.setContentText("Appointment title cannot be blank \n" +
                    "Please enter a title into the text field to continue");
            errorAlert.showAndWait();
            return;
        }

        String description = null;
        try {
            description =  descriptionText.getText();
            if(description == null || descriptionText.getText().isBlank()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please enter an appointment Description");
            errorAlert.setContentText("Appointment description cannot be blank \n" +
                    "Please enter a description into the text field to continue");
            errorAlert.showAndWait();
            return;
        }

        String location = null;
        try {
            location = locationText.getText();
            if(location == null || locationText.getText().isBlank()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please enter an appointment Location");
            errorAlert.setContentText("Appointment Location cannot be blank \n" +
                    "Please enter a location into the text field to continue");
            errorAlert.showAndWait();
            return;
        }

        LocalDate date = null;
        try {
            date = appointmentDatePicker.getValue();
            if(appointmentDatePicker.getValue() == null){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select a Date");
            errorAlert.setContentText("Date cannot be blank \n" +
                    "Please select a choice from the Date Picker to continue");
            errorAlert.showAndWait();
            return;
        }

        LocalDateTime start = LocalDateTime.of(date, appointmentStartComboBox.getValue());
        LocalDateTime end = LocalDateTime.of(date, appointmentEndComboBox.getValue());

        // create appointment object
        Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end,
                customerId, userId, contactId);

        // pass it to dao for updating
        AppointmentsDAO dao = new AppointmentsDAO();
        dao.update(appointment);

        displayNewScreen(updateAppointmentButton, "/view/Appointments.fxml");
    }

    private static Appointments item = null;

    public static void holdAppData(Appointments selectedAppointment) {
        item = selectedAppointment;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the combo boxes with data
        customerIdComboBox.setItems(new CustomerDAO().read());
        userIdComboBox.setItems(new UsersDAO().read());
        contactComboBox.setItems(new ContactsDAO().read());
        typeComboBox.setItems(new AppointmentsDAO().getListOfTypes());
        appointmentStartComboBox.setItems(TimeHelper.initializeTimeSlots());
        appointmentEndComboBox.setItems(TimeHelper.initializeTimeSlots());

        // set the field values with the data passed in
        appointmentIdText.setText(String.valueOf(item.getAppointmentId()));
        customerIdComboBox.getSelectionModel().select(item.getCustomerObject());
        userIdComboBox.getSelectionModel().select(item.getUserObject());
        contactComboBox.getSelectionModel().select(item.getContactObject());
        typeComboBox.getSelectionModel().select(item);
        titleText.setText(item.getTitle());
        descriptionText.setText(item.getDescription());
        locationText.setText(item.getLocation());
        appointmentDatePicker.setValue(item.getStart().toLocalDate());
        appointmentStartComboBox.setValue(item.getStart().toLocalTime());
        appointmentEndComboBox.setValue(item.getEnd().toLocalTime());
    }
}
