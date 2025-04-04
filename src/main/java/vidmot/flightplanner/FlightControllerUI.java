package vidmot.flightplanner;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import vinnsla.UIObjects.FlightModel;
import vinnsla.entities.Flight;
import vinnsla.service.FlightServiceInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FlightControllerUI {
    private FlightModel flightModel;
    private FlightServiceInterface flightService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
    private CheckBox directBox;
    @FXML
    private TableView<Flight> flightTableView;

    @FXML
    private TableColumn<Flight, String> flightNumberColumn;
    @FXML
    private TableColumn<Flight, String> airlineColumn;
    @FXML
    private TableColumn<Flight, String> departureColumn;
    @FXML
    private TableColumn<Flight, String> arrivalColumn;
    @FXML
    private TableColumn<Flight, String> departureTimeColumn;
    @FXML
    private TableColumn<Flight, String> arrivalTimeColumn;
    @FXML
    private TableColumn<Flight, Double> priceColumn;

    public void setFlightService(FlightServiceInterface flightService) {
        this.flightService = flightService;
        this.flightModel = new FlightModel(flightService);
        initializeBindings();
        setupTableView();
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
        flightModel.directFlightProperty().bind(directBox.selectedProperty());

        // Binda verðið
        flightModel.maxPriceProperty().bind(maxPriceSlider.valueProperty());
        priceLabel.textProperty().bind(flightModel.maxPriceProperty().asString().concat(" kr."));

        // Binda fjölda farþega
        flightModel.numberOfPassengersProperty().bind(passengerSpinner.valueProperty());
    }

    private void setupTableView() {
        // Set up the columns
        flightNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightNumber()));
        airlineColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAirline()));
        departureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartureCountry()));
        arrivalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrivalCountry()));
        
        // Format dates for display
        departureTimeColumn.setCellValueFactory(cellData -> {
            Date departureTime = cellData.getValue().getDepartureTime();
            return new SimpleStringProperty(departureTime != null ? dateFormat.format(departureTime) : "");
        });
        
        arrivalTimeColumn.setCellValueFactory(cellData -> {
            Date arrivalTime = cellData.getValue().getArrivalTime();
            return new SimpleStringProperty(arrivalTime != null ? dateFormat.format(arrivalTime) : "");
        });
        
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }

    public void onSearch(ActionEvent actionEvent) {
        System.out.println("Departure country: " + flightModel.getDepartureCountry());
        System.out.println("Arrival country: " + flightModel.getArrivalCountry());
        System.out.println("Departure date: " + flightModel.getDepartureDate());
        System.out.println("Arrival date: " + flightModel.getArrivalDate());
        System.out.println("One way: " + flightModel.isOneWay());
        System.out.println("Direct: " + flightModel.isDirectFlight());
        System.out.println("Number of passengers: " + flightModel.getNumberOfPassengers());

        List<Flight> results = flightModel.search();

        // Update the TableView with the search results
        ObservableList<Flight> flightData = FXCollections.observableArrayList(results);
        flightTableView.setItems(flightData);
    }
}
