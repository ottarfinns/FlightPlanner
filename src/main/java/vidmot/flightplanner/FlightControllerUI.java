package vidmot.flightplanner;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vinnsla.UIObjects.FlightModel;
import vinnsla.entities.Flight;
import vinnsla.repository.BookingRepository;
import vinnsla.service.FlightServiceInterface;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FlightControllerUI {
    private FlightModel flightModel;
    private FlightServiceInterface flightService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @FXML
    private ComboBox<String> dCountryCBox;
    @FXML
    private ComboBox<String> aCountryCBox;
    @FXML
    private DatePicker dDatePicker;
    @FXML
    private DatePicker rDatePicker;
    @FXML
    private Spinner<Integer> passengerSpinner;
    @FXML
    private Slider maxPriceSlider;
    @FXML
    private Label priceLabel;
    @FXML
    private CheckBox oneWayBox;
    @FXML
    private CheckBox maxPriceBox;
    @FXML
    private TableView<Flight> flightTableView;
    @FXML
    private TableView<Flight> returnFlightsTableView;
    @FXML
    private VBox returnFlightsSection;

    @FXML
    private TableColumn<Flight, String> flightNumberColumn;
    @FXML
    private TableColumn<Flight, String> airlineColumn;
    @FXML
    private TableColumn<Flight, String> departureColumn;
    @FXML
    private TableColumn<Flight, String> departureAirportColumn;
    @FXML
    private TableColumn<Flight, String> arrivalColumn;
    @FXML
    private TableColumn<Flight, String> arrivalAirportColumn;
    @FXML
    private TableColumn<Flight, String> departureTimeColumn;
    @FXML
    private TableColumn<Flight, String> arrivalTimeColumn;
    @FXML
    private TableColumn<Flight, Double> priceColumn;

    @FXML
    private TableColumn<Flight, String> returnFlightNumberColumn;
    @FXML
    private TableColumn<Flight, String> returnAirlineColumn;
    @FXML
    private TableColumn<Flight, String> returnDepartureColumn;
    @FXML
    private TableColumn<Flight, String> returnDepartureAirportColumn;
    @FXML
    private TableColumn<Flight, String> returnArrivalColumn;
    @FXML
    private TableColumn<Flight, String> returnArrivalAirportColumn;
    @FXML
    private TableColumn<Flight, String> returnDepartureTimeColumn;
    @FXML
    private TableColumn<Flight, String> returnArrivalTimeColumn;
    @FXML
    private TableColumn<Flight, Double> returnPriceColumn;

    @FXML
    private Button bookFlightButton;

    private Flight selectedFlight;
    private Flight selectedReturnFlight;

    public void setFlightService(FlightServiceInterface flightService) {
        this.flightService = flightService;
        this.flightModel = new FlightModel(flightService);
        initializeBindings();
        setupTableView();
        setupReturnTableView();
        setupSelectionListener();
        setupBoxListeners();
    }

    private void initializeBindings() {
        // Binda brottfara- og komustaðsetningar
        dCountryCBox.getItems().setAll("Iceland", "United States", "Canada", "United Kingdom", "France", "Germany");
        aCountryCBox.getItems().addAll("Iceland", "United States", "Canada", "United Kingdom", "France", "Germany");

        flightModel.departureCountryProperty().bind(dCountryCBox.getSelectionModel().selectedItemProperty());
        flightModel.arrivalCountryProperty().bind(aCountryCBox.getSelectionModel().selectedItemProperty());

        // Binda dagsetningar
        flightModel.departureDateProperty().bind(dDatePicker.valueProperty());
        flightModel.arrivalDateProperty().bind(rDatePicker.valueProperty());

        // Binda checkboxin
        flightModel.oneWayProperty().bind(oneWayBox.selectedProperty());

        // Binda fjölda farþega
        flightModel.numberOfPassengersProperty().bind(passengerSpinner.valueProperty());
    }

    private void setupSelectionListener() {
        flightTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedFlight = newSelection;
            if (oneWayBox.isSelected()) {
                bookFlightButton.setDisable(newSelection == null);
            }
        });

        returnFlightsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedReturnFlight = newSelection;
            if (!oneWayBox.isSelected()) {
                bookFlightButton.setDisable(newSelection == null);
            }
        });
    }

    private void setupBoxListeners() {
        oneWayBox.selectedProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection) {
                rDatePicker.setDisable(true);
                returnFlightsSection.setVisible(false);
                // Enable book button if a departure flight is selected
                if (selectedFlight != null) {
                    bookFlightButton.setDisable(false);
                }
            } else {
                rDatePicker.setDisable(false);
                returnFlightsSection.setVisible(true);
                // Disable book button if no return flight is selected
                if (selectedReturnFlight == null) {
                    bookFlightButton.setDisable(true);
                }
            }
        });

        maxPriceBox.selectedProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection) {
                maxPriceSlider.setDisable(false);
                flightModel.maxPriceProperty().bind(maxPriceSlider.valueProperty());
                priceLabel.textProperty().bind(flightModel.maxPriceProperty().asString().concat(" kr."));
            } else {
                maxPriceSlider.setDisable(true);
                flightModel.maxPriceProperty().unbind();
                flightModel.setMaxPrice(0);
                priceLabel.textProperty().unbind();
                priceLabel.setText("0 kr.");
            }
        });
    }

    private void setupTableView() {
        // Set up the columns
        flightNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightNumber()));
        airlineColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAirline()));
        departureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartureCountry()));
        departureAirportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartureAirport()));
        arrivalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivalCountry()));
        arrivalAirportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivalAirport()));

        // Format dates and times for display
        departureTimeColumn.setCellValueFactory(cellData -> {
            Date departureDate = cellData.getValue().getDepartureDate();
            String departureTime = cellData.getValue().getDepartureTime();
            if (departureDate != null && departureTime != null) {
                return new SimpleStringProperty(dateFormat.format(departureDate) + " " + departureTime);
            }
            return new SimpleStringProperty("");
        });

        arrivalTimeColumn.setCellValueFactory(cellData -> {
            Date arrivalDate = cellData.getValue().getArrivalDate();
            String arrivalTime = cellData.getValue().getArrivalTime();
            if (arrivalDate != null && arrivalTime != null) {
                return new SimpleStringProperty(dateFormat.format(arrivalDate) + " " + arrivalTime);
            }
            return new SimpleStringProperty("");
        });

        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        // Set up return flight columns
        returnFlightNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightNumber()));
        returnAirlineColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAirline()));
        returnDepartureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartureCountry()));
        returnDepartureAirportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartureAirport()));
        returnArrivalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivalCountry()));
        returnArrivalAirportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivalAirport()));

        returnDepartureTimeColumn.setCellValueFactory(cellData -> {
            Date departureDate = cellData.getValue().getDepartureDate();
            String departureTime = cellData.getValue().getDepartureTime();
            if (departureDate != null && departureTime != null) {
                return new SimpleStringProperty(dateFormat.format(departureDate) + " " + departureTime);
            }
            return new SimpleStringProperty("");
        });

        returnArrivalTimeColumn.setCellValueFactory(cellData -> {
            Date arrivalDate = cellData.getValue().getArrivalDate();
            String arrivalTime = cellData.getValue().getArrivalTime();
            if (arrivalDate != null && arrivalTime != null) {
                return new SimpleStringProperty(dateFormat.format(arrivalDate) + " " + arrivalTime);
            }
            return new SimpleStringProperty("");
        });

        returnPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        // Initially disable the book button
        bookFlightButton.setDisable(true);
    }

    private void setupReturnTableView() {
        // Set up the columns
        returnFlightNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightNumber()));
        returnAirlineColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAirline()));
        returnDepartureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartureCountry()));
        returnDepartureAirportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartureAirport()));
        returnArrivalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivalCountry()));
        returnArrivalAirportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivalAirport()));

        // Format dates and times for display
        returnDepartureTimeColumn.setCellValueFactory(cellData -> {
            Date departureDate = cellData.getValue().getDepartureDate();
            String departureTime = cellData.getValue().getDepartureTime();
            if (departureDate != null && departureTime != null) {
                return new SimpleStringProperty(dateFormat.format(departureDate) + " " + departureTime);
            }
            return new SimpleStringProperty("");
        });

        returnArrivalTimeColumn.setCellValueFactory(cellData -> {
            Date arrivalDate = cellData.getValue().getArrivalDate();
            String arrivalTime = cellData.getValue().getArrivalTime();
            if (arrivalDate != null && arrivalTime != null) {
                return new SimpleStringProperty(dateFormat.format(arrivalDate) + " " + arrivalTime);
            }
            return new SimpleStringProperty("");
        });

        returnPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }

    public void onSearch(ActionEvent actionEvent) {
        List<Flight> departureFlights = flightModel.search();
        List<Flight> returnFlights = flightModel.searchReturnFlights();

        // Update the TableView with the search results
        ObservableList<Flight> departureFlightData = FXCollections.observableArrayList(departureFlights);
        flightTableView.setItems(departureFlightData);

        // Update the return flights table if it's not a one-way trip
        if (!flightModel.isOneWay()) {
            ObservableList<Flight> returnFlightData = FXCollections.observableArrayList(returnFlights);
            returnFlightsTableView.setItems(returnFlightData);
            returnFlightsSection.setVisible(true);
        } else {
            returnFlightsSection.setVisible(false);
        }
    }


    public void onBookFlight(ActionEvent event) {
        if (selectedFlight != null) {
            try {
                // Load the booking view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Booking-View.fxml"));

                // Create the controller with the selected flight
                selectedFlight.setSeatingArrangement(BookingRepository.getInstance().getBookedSeats(selectedFlight.getFlightNumber()));

                // Only set return flight seating if a return flight is selected
                if (selectedReturnFlight != null) {
                    selectedReturnFlight.setSeatingArrangement(BookingRepository.getInstance().getBookedSeats(selectedReturnFlight.getFlightNumber()));
                }

                BookingControllerUI bookingController = new BookingControllerUI(selectedFlight, selectedReturnFlight);
                loader.setController(bookingController);

                // Load the scene
                Parent bookingRoot = loader.load();
                Scene bookingScene = new Scene(bookingRoot);

                // Get the current stage and set the new scene
                Stage currentStage = (Stage) bookFlightButton.getScene().getWindow();
                currentStage.setScene(bookingScene);
                currentStage.setTitle("Flight Booking");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
