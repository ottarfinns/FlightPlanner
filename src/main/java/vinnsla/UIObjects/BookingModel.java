package vinnsla.UIObjects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import vinnsla.entities.Booking;
import javafx.scene.control.Alert;

public class BookingModel {
    private Booking booking;
    private SimpleStringProperty name;
    private SimpleStringProperty nationalID;
    private SimpleStringProperty passportNumber;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty country;
    private SimpleStringProperty city;
    private SimpleStringProperty address;

    private SimpleIntegerProperty luggage;
    private SimpleBooleanProperty carryOn;
    private SimpleStringProperty classType;

    public BookingModel() {
        this.booking = new Booking();
        this.name = new SimpleStringProperty("");
        this.nationalID = new SimpleStringProperty("");
        this.passportNumber = new SimpleStringProperty("");
        this.phoneNumber = new SimpleStringProperty("");
        this.country = new SimpleStringProperty("");
        this.city = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");

        this.luggage = new SimpleIntegerProperty(0);
        this.carryOn = new SimpleBooleanProperty(false);
        this.classType = new SimpleStringProperty("");
    }

    public void saveBooking() {
        System.out.println("Booking saved");
    }

    public void confirmBooking() {
        // If none of the fields are empty or null we can confirm the booking
        if (name.get().isEmpty() || nationalID.get().isEmpty() || passportNumber.get().isEmpty() || phoneNumber.get().isEmpty()
                || country.get().isEmpty() || city.get().isEmpty() || address.get().isEmpty() || classType.get().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill in all fields");
            alert.setContentText("Please fill in all fields before confirming the booking");
            alert.showAndWait();
        } else {
            System.out.println("Name: " + name.get());
            System.out.println("National ID: " + nationalID.get());
            System.out.println("Passport Number: " + passportNumber.get());
            System.out.println("Phone Number: " + phoneNumber.get());
            System.out.println("Country: " + country.get());
            System.out.println("City: " + city.get());
            System.out.println("Address: " + address.get());
            System.out.println("Luggage: " + luggage.get());
            System.out.println("Carry On: " + carryOn.get());
            System.out.println("Class Type: " + classType.get());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Booking confirmed");
            alert.setHeaderText("Booking confirmed");
            alert.setContentText("Your booking has been confirmed");
            alert.showAndWait();
        }
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty nationalIDProperty() {
        return nationalID;
    }

    public SimpleStringProperty passportNumberProperty() {
        return passportNumber;
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public SimpleIntegerProperty luggageProperty() {
        return luggage;
    }

    public SimpleBooleanProperty carryOnProperty() {
        return carryOn;
    }

    public SimpleStringProperty classTypeProperty() {
        return classType;
    }

    public String getName() {
        return name.get();
    }

    public String getNationalID() {
        return nationalID.get();
    }

    public String getPassportNumber() {
        return passportNumber.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getCountry() {
        return country.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getAddress() {
        return address.get();
    }

    public int getLuggage() {
        return luggage.get();
    }

    public boolean getCarryOn() {
        return carryOn.get();
    }

    public String getClassType() {
        return classType.get();
    }

}
