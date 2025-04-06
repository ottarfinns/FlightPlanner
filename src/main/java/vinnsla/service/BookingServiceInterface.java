package vinnsla.service;

import vinnsla.entities.Booking;

public interface BookingServiceInterface {
    boolean addBooking(Booking booking);
    boolean bookSeat(String flightNumber, String seatNumber);
/*
    boolean createBooking(Booking booking);
    List<Booking> getAllBookings();
    Booking getBooking(int bookingId);
    boolean updateBooking(Booking booking);
    boolean deleteBooking(int bookingId);
    boolean processPayment(int bookingId, double amount);
    double calculateTotalPrice(Booking booking);
*/
}
