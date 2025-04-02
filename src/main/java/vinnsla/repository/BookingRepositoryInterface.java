package vinnsla.repository;

import vinnsla.entities.Booking;

import java.util.List;

public interface BookingRepositoryInterface {
    boolean addBooking(Booking booking);
    boolean updateBooking(Booking booking);
    boolean deleteBooking(int bookingID);
    boolean payBooking(int bookingID, double pricePaid);
    //boolean confirmBooking();
    List<Booking> getAllBookings();
    Booking getBookingByID(int bookingID);
    //List<Booking> findBookingByFlightNumber(String flightNumber);


}
