package vinnsla.UIObjects;

import javafx.beans.property.SimpleStringProperty;

public class BookingModel {

    private SimpleStringProperty name;
    private SimpleStringProperty nationalID;
    private SimpleStringProperty passportNumber;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty country;
    private SimpleStringProperty city;
    private SimpleStringProperty address;

    public BookingModel() {
        this.name = new SimpleStringProperty("");
        this.nationalID = new SimpleStringProperty("");
        this.passportNumber = new SimpleStringProperty("");
        this.phoneNumber = new SimpleStringProperty("");
        this.country = new SimpleStringProperty("");
        this.city = new SimpleStringProperty("");
        this.address = new SimpleStringProperty("");
    }

    public void saveBooking() {
        System.out.println("Booking saved");
    }

}
