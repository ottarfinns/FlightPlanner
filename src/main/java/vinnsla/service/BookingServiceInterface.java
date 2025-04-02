package vinnsla.service;

import vinnsla.entities.Booking;
import java.util.List;

public interface BookingServiceInterface {
    boolean createBooking(Booking booking);
    List<Booking> getAllBookings();
    Booking getBooking(int bookingId);
    boolean updateBooking(Booking booking);
    boolean deleteBooking(int bookingId);
    boolean processPayment(int bookingId, double amount);
    double calculateTotalPrice(Booking booking);
}
