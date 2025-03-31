package vinnsla.entities;

public class Booking {

    private int bookingId;

    private Flight flight;
    private Customer customer;

    private boolean carryon;
    private boolean firstClass;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isCarryon() {
        return carryon;
    }

    public void setCarryon(boolean carryon) {
        this.carryon = carryon;
    }

    public boolean isFirstClass() {
        return firstClass;
    }

    public void setFirstClass(boolean firstClass) {
        this.firstClass = firstClass;
    }

    public static void main(String[] args) {

    }
}
