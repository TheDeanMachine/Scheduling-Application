package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomerDAO;
import DAO.UsersDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
        // collect information
        int appointmentId = Integer.parseInt(appointmentIdText.getText());

//        Customers selectedCustomer = customerIdComboBox.getValue();
//        int customerId = selectedCustomer.getCustomerId();

        int customerId = customerIdComboBox.getValue().getCustomerId();

        int userId = userIdComboBox.getValue().getUserID();

        Contacts selectedContact = contactComboBox.getValue();
        int contactId = selectedContact.getContactId();

        Appointments selectedType = typeComboBox.getValue();
        String type = selectedType.getType();

        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();

        LocalDate appDay = appointmentDatePicker.getValue();
        LocalTime startTime = appointmentStartComboBox.getValue();
        LocalTime endTime = appointmentEndComboBox.getValue();

        LocalDateTime start = LocalDateTime.of(appDay, startTime);
        LocalDateTime end = LocalDateTime.of(appDay, endTime);

        Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end,
                customerId, userId, contactId);

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

        LocalDateTime localDateTime = item.getStart();
        LocalDate localDate = localDateTime.toLocalDate();
        appointmentDatePicker.setValue(localDate);

        LocalTime startTime = localDateTime.toLocalTime();
        appointmentStartComboBox.setValue(startTime);

        LocalDateTime localDateTime2 = item.getEnd();
        LocalTime endTime = localDateTime2.toLocalTime();
        appointmentEndComboBox.setValue(endTime);



    }
}
