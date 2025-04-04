package vidmot.flightplanner;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import vinnsla.UIObjects.BookingModel;
import vinnsla.entities.Flight;

public class BookingControllerUI {
    private BookingModel bookingModel;

    // Flight Information Labels
    @FXML
    private Label flightNumberLabel;
    @FXML
    private Label airlineLabel;
    @FXML
    private Label departureLabel;
    @FXML
    private Label arrivalLabel;
    @FXML
    private Label departureTimeLabel;
    @FXML
    private Label arrivalTimeLabel;
    @FXML
    private Label priceLabel;

    // Flight Add-ons
    @FXML
    private ComboBox<String> classComboBox;
    @FXML
    private Spinner<Integer> luggageSpinner;
    @FXML
    private CheckBox carryOnCheckBox;
    @FXML
    private Button pickSeatsButton;

    // Customer Information
    @FXML
    private TextField nameField;
    @FXML
    private TextField nationalIdField;
    @FXML
    private TextField passportField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField addressField;

    // Action Buttons
    @FXML
    private Button backButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button payButton;

    public BookingControllerUI() {
        bookingModel = new BookingModel();

        initializeBindings();
    }

    private void initializeBindings() {
        bookingModel.nameProperty().bind(nameField.textProperty());
        bookingModel.nationalIDProperty().bind(nationalIdField.textProperty());
        bookingModel.passportNumberProperty().bind(passportField.textProperty());
        bookingModel.phoneNumberProperty().bind(phoneField.textProperty());
        bookingModel.countryProperty().bind(countryField.textProperty());
        bookingModel.cityProperty().bind(cityField.textProperty());
        bookingModel.addressProperty().bind(addressField.textProperty());

        // Add ons
        classComboBox.getItems().addAll("Economy", "Business", "First");
        bookingModel.luggageProperty().bind(luggageSpinner.valueProperty());
        bookingModel.carryOnProperty().bind(carryOnCheckBox.selectedProperty());
        bookingModel.classTypeProperty().bind(classComboBox.valueProperty());
    }

    private void initializeFlightInfo(Flight flight) {

    }
}
