package vinnsla.repository;

import vinnsla.entities.Booking;
import vinnsla.entities.SeatingArrangement;

public interface BookingRepositoryInterface {
    boolean addBooking(Booking booking);
    boolean bookSeat(String flightNumber, String seatNumber);
    SeatingArrangement getBookedSeats(String flightNumber);
/*
    boolean addBooking(Booking booking);
    boolean updateBooking(Booking booking);
    boolean deleteBooking(int bookingID);
    boolean payBooking(int bookingID, double pricePaid);
*/
    //boolean confirmBooking();
    //List<Booking> getAllBookings();
    //Booking getBookingByID(int bookingID);
    //List<Booking> findBookingByFlightNumber(String flightNumber);


}
