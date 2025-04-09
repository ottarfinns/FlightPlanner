package vidmot.flightplanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vinnsla.UIObjects.BookingModel;
import vinnsla.entities.Flight;
import vinnsla.entities.SeatNumber;
import vinnsla.entities.SeatingArrangement;
import vinnsla.repository.BookingRepository;
import vinnsla.service.BookingServiceInterface;
import vinnsla.service.FlightServiceInterface;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class BookingControllerUI {
    private BookingModel bookingModel;
    private BookingServiceInterface bookingService;
    private Flight selectedFlight;
    private Flight selectedReturnFlight;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String selectedSeat;
    private SeatingArrangement seating;
    private String selectedReturnSeat;

    // Flight Information Labels
    @FXML
    private Label flightNumberLabel;
    @FXML
    private Label airlineLabel;
    @FXML
    private Label departureLabel;
    @FXML
    private Label departureAirportLabel;
    @FXML
    private Label arrivalLabel;
    @FXML
    private Label arrivalAirportLabel;
    @FXML
    private Label departureTimeLabel;
    @FXML
    private Label arrivalTimeLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label totalPriceLabel;

    // Return flight information labels
    @FXML
    private Label returnFlightNumberLabel;
    @FXML
    private Label returnAirlineLabel;
    @FXML
    private Label returnDepartureLabel;
    @FXML
    private Label returnDepartureAirportLabel;
    @FXML
    private Label returnArrivalLabel;
    @FXML
    private Label returnArrivalAirportLabel;
    @FXML
    private Label returnDepartureTimeLabel;
    @FXML
    private Label returnArrivalTimeLabel;
    @FXML
    private Label returnPriceLabel;
    @FXML
    private VBox returnFlightSection;

    // Flight Add-ons
    @FXML
    private ComboBox<String> classComboBox;
    @FXML
    private Spinner<Integer> luggageSpinner;
    @FXML
    private CheckBox carryOnCheckBox;
    @FXML
    private Button pickSeatsButton;
    @FXML
    private Button pickReturnSeatsButton;
    @FXML
    private TextField promoCodeField;
    @FXML
    private Button applyPromoButton;

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

    private double basePrice;
    private double totalPrice;
    private static final double LUGGAGE_PRICE = 20.0;
    private static final double FIRST_CLASS_MULTIPLIER = 1.5;
    private static final double BUSINESS_CLASS_MULTIPLIER = 1.2;
    private static final double PROMO_CODE_DISCOUNT = 0.3;
    private boolean promoApplied = false;

    private FlightServiceInterface flightService;

    public BookingControllerUI(Flight flight, Flight returnFlight) {
        this.selectedFlight = flight;
        this.selectedReturnFlight = returnFlight;
        if (selectedReturnFlight != null) {
            System.out.println("Return flight " + selectedReturnFlight.getFlightNumber());
        }
        //BookingRepositoryInterface bookingRepository = new BookingRepository();
        //bookingService = new BookingService(bookingRepository);
        bookingModel = new BookingModel(selectedFlight, selectedReturnFlight);
        this.basePrice = flight.getPrice();
        if (selectedReturnFlight != null) {
            this.basePrice = this.selectedFlight.getPrice() + this.selectedReturnFlight.getPrice();
        } else {
            this.basePrice = this.selectedFlight.getPrice();
        }
        this.totalPrice = basePrice;
        this.selectedSeat = "";
        this.selectedReturnSeat = "";
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
        updateTotalPriceLabel();
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
            departureAirportLabel.setText(selectedFlight.getDepartureAirport());
            arrivalLabel.setText(selectedFlight.getArrivalCountry());
            arrivalAirportLabel.setText(selectedFlight.getArrivalAirport());
            departureTimeLabel.setText(dateFormat.format(selectedFlight.getDepartureDate()) + " " + selectedFlight.getDepartureTime());
            arrivalTimeLabel.setText(dateFormat.format(selectedFlight.getArrivalDate()) + " " + selectedFlight.getArrivalTime());
            priceLabel.setText(String.format("%.2f kr.", selectedFlight.getPrice()));
        }

        // Set up return flight information if it exists
        if (selectedReturnFlight != null) {
            returnFlightSection.setVisible(true);
            pickReturnSeatsButton.setVisible(true);
            returnFlightNumberLabel.setText(selectedReturnFlight.getFlightNumber());
            returnAirlineLabel.setText(selectedReturnFlight.getAirline());
            returnDepartureLabel.setText(selectedReturnFlight.getDepartureCountry());
            returnDepartureAirportLabel.setText(selectedReturnFlight.getDepartureAirport());
            returnArrivalLabel.setText(selectedReturnFlight.getArrivalCountry());
            returnArrivalAirportLabel.setText(selectedReturnFlight.getArrivalAirport());
            returnDepartureTimeLabel.setText(dateFormat.format(selectedReturnFlight.getDepartureDate()) + " " + selectedReturnFlight.getDepartureTime());
            returnArrivalTimeLabel.setText(dateFormat.format(selectedReturnFlight.getArrivalDate()) + " " + selectedReturnFlight.getArrivalTime());
            returnPriceLabel.setText(String.format("%.2f kr.", selectedReturnFlight.getPrice()));
        } else {
            returnFlightSection.setVisible(false);
            pickReturnSeatsButton.setVisible(false);
        }
    }

    public void confirmBooking() {
        boolean confirmed = bookingModel.confirmBooking(totalPrice, selectedSeat);
        if (confirmed) {
            onBack();
        }
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

    public void onPickReturnSeats() {
        if (!selectedReturnSeat.isEmpty()) {
            // Seat already selected, alert
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Change Return Seat");
            confirmDialog.setHeaderText("You have already selected a return seat");
            confirmDialog.setContentText("Do you want to change your return seat from " + selectedReturnSeat + "?");

            Optional<ButtonType> result = confirmDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                SeatNumber oldSeatNumber = SeatNumber.fromString(selectedReturnSeat);
                selectedReturnFlight.getSeatingArrangement().cancelSeat(oldSeatNumber);
            } else {
                return;
            }
        }

        // Open seat selection dialog
        SeatSelectionControllerUI dialog = new SeatSelectionControllerUI(selectedReturnFlight);
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(seatNumber -> {
            // Update the selected seat
            selectedReturnSeat = seatNumber;
            System.out.println("Selected return seat: " + seatNumber);
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
            Scene scene = new Scene(root, 900, 900);
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

    public void setFlightService(FlightServiceInterface flightService) {
        this.flightService = flightService;
    }

    public void onApplyPromo() {
        String promoCode = promoCodeField.getText().trim();
        if (promoCode.isEmpty()) {
            showAlert("Error", "Please enter a promo code");
            return;
        }

        if (BookingRepository.getInstance().inPromoCodes(promoCode)) {
            if (!promoApplied) {
                totalPrice *= (1 - PROMO_CODE_DISCOUNT);
                promoApplied = true;
                updateTotalPriceLabel();
                applyPromoButton.setDisable(true);
                promoCodeField.setDisable(true);
            } else {
                showAlert("Info", "A promo code has already been applied");
            }
        } else {
            showAlert("Error", "Invalid promo code");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateTotalPriceLabel() {
        totalPriceLabel.setText(String.format("%.2f kr.", totalPrice));
    }
}
