package vidmot.flightplanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vinnsla.UIObjects.BookingModel;
import vinnsla.entities.Flight;
import vinnsla.entities.SeatNumber;
import vinnsla.entities.SeatingArrangement;
import vinnsla.service.FlightServiceInterface;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class BookingControllerUI {
    private BookingModel bookingModel;
    private Flight selectedFlight;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String selectedSeat;
    private SeatingArrangement seating;
    //private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

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
    @FXML
    private Label totalPriceLabel;

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

    private double basePrice;
    private double totalPrice;
    private static final double LUGGAGE_PRICE = 20.0;
    private static final double FIRST_CLASS_MULTIPLIER = 1.5;
    private static final double BUSINESS_CLASS_MULTIPLIER = 1.2;

    private FlightServiceInterface flightService;

    public BookingControllerUI(Flight flight) {
        this.selectedFlight = flight;
        bookingModel = new BookingModel();
        this.basePrice = flight.getPrice();
        this.totalPrice = flight.getPrice();
        this.selectedSeat = "";
        this.seating = flight.getSeatingArrangement();
    }

    @FXML
    private void initialize() {
        initializeBindings();
        initializeFlightInfo();
        setupPriceListeners();
        updateTotalPrice();
    }

    private void setupPriceListeners() {
        // Listen for changes in luggage count
        luggageSpinner.valueProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());

        // Listen for changes in class selection
        classComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());

        // Listen for changes in carry-on selection
        carryOnCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());
    }

    private void updateTotalPrice() {
        double total = basePrice;

        // Add luggage cost
        total += luggageSpinner.getValue() * LUGGAGE_PRICE;

        // Apply class multiplier
        String selectedClass = classComboBox.getValue();
        if (selectedClass != null) {
            switch (selectedClass) {
                case "First Class":
                    total *= FIRST_CLASS_MULTIPLIER;
                    break;
                case "Business Class":
                    total *= BUSINESS_CLASS_MULTIPLIER;
                    break;
                // Economy class has no multiplier
            }
        }

        // Update the total price and label
        this.totalPrice = total;
        totalPriceLabel.setText(String.format("%.2f kr.", total));
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
        classComboBox.getItems().addAll("Economy Class", "Business Class", "First Class");
        classComboBox.setValue("Economy Class"); // Set default value
        bookingModel.luggageProperty().bind(luggageSpinner.valueProperty());
        bookingModel.carryOnProperty().bind(carryOnCheckBox.selectedProperty());
        bookingModel.classTypeProperty().bind(classComboBox.valueProperty());
    }

    private void initializeFlightInfo() {
        if (selectedFlight != null) {
            flightNumberLabel.setText(selectedFlight.getFlightNumber());
            airlineLabel.setText(selectedFlight.getAirline());
            departureLabel.setText(selectedFlight.getDepartureCountry());
            arrivalLabel.setText(selectedFlight.getArrivalCountry());
            departureTimeLabel.setText(dateFormat.format(selectedFlight.getDepartureDate()) + " " + selectedFlight.getDepartureTime());
            arrivalTimeLabel.setText(dateFormat.format(selectedFlight.getArrivalDate()) + " " + selectedFlight.getArrivalTime());
            priceLabel.setText(String.format("%.2f kr.", selectedFlight.getPrice()));
        }
    }

    public void confirmBooking() {
        bookingModel.confirmBooking(totalPrice);
    }

    public void onPickSeats() {
        if (!selectedSeat.isEmpty()) {
            // Seat already selected, alert
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Change Seat");
            confirmDialog.setHeaderText("You have already selected a seat");
            confirmDialog.setContentText("Do you want to change your seat from " + selectedSeat + "?");

            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                SeatNumber oldSeatNumber = SeatNumber.fromString(selectedSeat);
                seating.cancelSeat(oldSeatNumber);
                selectedFlight.setSeatingArrangement(seating);
            } else {
                return;
            }
        }

        // Open seat selection dialog
        SeatSelectionControllerUI dialog = new SeatSelectionControllerUI(selectedFlight);
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(seatNumber -> {
            // Update the selected seat
            selectedSeat = seatNumber;
            System.out.println("Selected seat: " + seatNumber);
        });
    }

    public void onBack() {
        try {
            // Load the flight search view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FlightPlanner-View.fxml"));
            Parent root = loader.load();

            // Get the controller and set the flight service
            FlightControllerUI controller = loader.getController();
            controller.setFlightService(FlightApplication.getFlightService());

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Flight Search");

        } catch (IOException e) {
            e.printStackTrace();
            // Show error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Navigation Error");
            alert.setContentText("Could not return to flight search page.");
            alert.showAndWait();
        }
    }

    public void onConfirmBooking() {
        // TODO: Update the seating in the database such that the selected seat is now booked
    }

    public void setFlightService(FlightServiceInterface flightService) {
        this.flightService = flightService;
    }
}
