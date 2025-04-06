package vinnsla.controller;

import vinnsla.entities.Booking;
import vinnsla.service.BookingServiceInterface;

public class BookingController {
    private final BookingServiceInterface bookingService;

    public BookingController(BookingServiceInterface bookingService) {
        this.bookingService = bookingService;
    }

    public boolean addBooking(Booking booking) {
        return bookingService.addBooking(booking);
    }

/*
    public boolean createBooking(Booking booking) {
        return bookingService.createBooking(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    public Booking getBooking(int bookingId) {
        return bookingService.getBooking(bookingId);
    }

    public boolean updateBooking(Booking booking) {
        return bookingService.updateBooking(booking);
    }

    public boolean deleteBooking(int bookingId) {
        return bookingService.deleteBooking(bookingId);
    }

    public boolean processPayment(int bookingId, double amount) {
        return bookingService.processPayment(bookingId, amount);
    }

    public double calculateTotalPrice(Booking booking) {
        return bookingService.calculateTotalPrice(booking);
    }
*/
}
