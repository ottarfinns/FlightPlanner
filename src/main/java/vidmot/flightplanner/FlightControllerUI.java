package vidmot.flightplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import vinnsla.UIObjects.FlightModel;

public class FlightControllerUI {
    private FlightModel flightModel;
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


    public void initialize() {
        flightModel = new FlightModel();

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
        //priceLabel.textProperty().bind(flightModel.maxPriceProperty().asString());
        priceLabel.textProperty().bind(flightModel.maxPriceProperty().asString().concat(" kr."));

    }

    public void onSearch(ActionEvent actionEvent) {
        System.out.println("Departure country: " + flightModel.getDepartureCountry());
        System.out.println("Arrival country: " + flightModel.getArrivalCountry());
        System.out.println("Departure date: " + flightModel.getDepartureDate());
        System.out.println("Arrival date: " + flightModel.getArrivalDate());
        System.out.println("One way: " + flightModel.isOneWay());
        System.out.println("Direct: " +flightModel.isDirectFlight());
        flightModel.search();
    }

}
