package vinnsla.UIObjects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.BooleanProperty;

import java.time.LocalDate;

public class FlightModel {
    private final SimpleStringProperty departureCountry;
    private final SimpleStringProperty arrivalCountry;
    private final SimpleObjectProperty<LocalDate> departureDate;
    private final SimpleObjectProperty<LocalDate> arrivalDate;
    private final SimpleIntegerProperty numberOfPassengers;
    private final SimpleIntegerProperty maxPrice;
    private final SimpleBooleanProperty oneWay;
    private final SimpleBooleanProperty directFlight;

    public FlightModel() {
        this.departureCountry = new SimpleStringProperty("");
        this.arrivalCountry = new SimpleStringProperty("");
        this.departureDate = new SimpleObjectProperty<>(LocalDate.now());
        this.arrivalDate = new SimpleObjectProperty<>(LocalDate.now());
        this.numberOfPassengers = new SimpleIntegerProperty(0);
        this.maxPrice = new SimpleIntegerProperty(0);
        this.oneWay = new SimpleBooleanProperty(false);
        this.directFlight = new SimpleBooleanProperty(false);
    }

    // Property getters
    public StringProperty departureCountryProperty() {
        return departureCountry;
    }

    public StringProperty arrivalCountryProperty() {
        return arrivalCountry;
    }

    public ObjectProperty<LocalDate> departureDateProperty() {
        return departureDate;
    }

    public ObjectProperty<LocalDate> arrivalDateProperty() {
        return arrivalDate;
    }

    public IntegerProperty numberOfPassengersProperty() {
        return numberOfPassengers;
    }

    public IntegerProperty maxPriceProperty() {
        return maxPrice;
    }

    public BooleanProperty oneWayProperty() {
        return oneWay;
    }

    public BooleanProperty directFlightProperty() {
        return directFlight;
    }

    // Value getters
    public String getDepartureCountry() {
        return departureCountry.get();
    }

    public String getArrivalCountry() {
        return arrivalCountry.get();
    }

    public LocalDate getDepartureDate() {
        return departureDate.get();
    }

    public LocalDate getArrivalDate() {
        return arrivalDate.get();
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers.get();
    }

    public int getMaxPrice() {
        return maxPrice.get();
    }

    public boolean isOneWay() {
        return oneWay.get();
    }

    public boolean isDirectFlight() {
        return directFlight.get();
    }

    // Value setters
    public void setDepartureCountry(String value) {
        departureCountry.set(value);
    }

    public void setArrivalCountry(String value) {
        arrivalCountry.set(value);
    }

    public void setDepartureDate(LocalDate value) {
        departureDate.set(value);
    }

    public void setArrivalDate(LocalDate value) {
        arrivalDate.set(value);
    }

    public void setNumberOfPassengers(int value) {
        numberOfPassengers.set(value);
    }

    public void setMaxPrice(int value) {
        maxPrice.set(value);
    }

    public void setOneWay(boolean value) {
        oneWay.set(value);
    }

    public void setDirectFlight(boolean value) {
        directFlight.set(value);
    }


    public void search() {

    }
}
