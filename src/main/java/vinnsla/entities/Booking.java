package vinnsla.entities;

public class Booking {

    private int bookingId;

    private Flight flight;
    //private Customer customer;
    private Passenger passenger;
    private String seat;

    private int totalPrice;

    private boolean carryon;
    private String className;
    private int baggage;

    public Booking(Flight flight, Passenger passenger, String seat, int totalPrice,
                   boolean carryon, String className, int baggage) {
        this.flight = flight;
        this.passenger = passenger;
        this.seat = seat;
        this.totalPrice = totalPrice;
        this.carryon = carryon;
        this.className = className;
        this.baggage = baggage;
    }

    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setPrice(int price) {
        this.totalPrice = price;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getSeat() {
        return seat;
    }

    public void setCarryon(boolean carryon) {
        this.carryon = carryon;
    }
    public boolean getCarryOn() {
        return carryon;
    }

    public void setBaggage(int baggage) {
        this.baggage = baggage;
    }

    public int getBaggage() {
        return baggage;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public static void main(String[] args) {

    }
}
