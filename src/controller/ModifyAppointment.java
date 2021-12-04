package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ModifyAppointment {

    @FXML
    private TextField appointmentIdText;

    @FXML
    private ComboBox<?> customerIdComboBox;

    @FXML
    private ComboBox<?> userIdComboBox;

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private ComboBox<?> appointmentStartComboBox;

    @FXML
    private ComboBox<?> appointmentEndComboBox;

    @FXML
    private Button cancelButton;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    void onActionBackToMain(ActionEvent event) {

    }

    @FXML
    void onActionUpdateAppointment(ActionEvent event) {

    }

}
