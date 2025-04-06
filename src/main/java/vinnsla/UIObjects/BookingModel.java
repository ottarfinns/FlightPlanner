package vinnsla.UIObjects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import vidmot.flightplanner.FlightApplication;
import vinnsla.controller.BookingController;
import vinnsla.entities.Booking;
import vinnsla.entities.Flight;
import vinnsla.entities.Passenger;
import vinnsla.entities.SeatingArrangement;

public class BookingModel {
    private BookingController bookingController;
    private Flight flight;
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

    public BookingModel(Flight flight) {
        this.flight = flight;
        this.bookingController = new BookingController(FlightApplication.getBookingService());
        this.booking = new Booking(null, null, null, 0, false, "", 0);
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

    public void confirmBooking(double totalPrice, String seat) {
        // If none of the fields are empty or null we can confirm the booking
        if (name.get().isEmpty() || nationalID.get().isEmpty() || passportNumber.get().isEmpty() || phoneNumber.get().isEmpty()
                || country.get().isEmpty() || city.get().isEmpty() || address.get().isEmpty() || classType.get() == null
                || classType.get().isEmpty() || seat.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill in all fields");
            alert.setContentText("Please fill in all fields before confirming the booking");
            alert.showAndWait();
        } else {
            Passenger passenger = new Passenger(nationalID.get(), passportNumber.get(), name.get(), "ottarfinns@gmail.com", phoneNumber.get(), country.get(), address.get(), city.get());

            // TODO: Bæta við eigindum í customer þegar búið er að setja upp customer klasana

            booking.setFlight(flight);
            booking.setPassenger(passenger);
            booking.setSeat(seat);
            booking.setTotalPrice((int) totalPrice);
            booking.setCarryon(carryOn.get());
            booking.setClassName(classType.get());
            booking.setBaggage(luggage.get());

            boolean result1 = bookingController.addBooking(booking);
            boolean result2 = bookingController.bookSeat(flight.getFlightNumber(), seat);

            if (result1 && result2) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking confirmed");
                alert.setHeaderText("Booking confirmed");
                alert.setContentText("Your booking has been confirmed");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Booking failed");
                alert.setContentText("Booking failed");
                alert.showAndWait();
            }

        }
    }

    public SeatingArrangement getBookedSeats() {
        return bookingController.getBookedSeats(flight.getFlightNumber());
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
