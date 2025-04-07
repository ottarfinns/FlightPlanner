package vinnsla.service;

import vinnsla.entities.Booking;
import vinnsla.entities.SeatingArrangement;
import vinnsla.repository.BookingRepository;
import vinnsla.repository.BookingRepositoryInterface;

public class BookingService implements BookingServiceInterface {
    private final BookingRepositoryInterface bookingRepository;

    public BookingService() {
        this.bookingRepository = BookingRepository.getInstance();
    }


    @Override
    public boolean addBooking(Booking booking) {
        return bookingRepository.addBooking(booking);
    }

    @Override
    public boolean bookSeat(String flightNumber, String seatNumber) {
       return bookingRepository.bookSeat(flightNumber, seatNumber);
    }

    @Override
    public SeatingArrangement getBookedSeats(String flightNumber) {
        return bookingRepository.getBookedSeats(flightNumber);
    }

    /*@Override
    public boolean createBooking(Booking booking) {
        if (booking == null || booking.getFlight() == null || booking.getCustomer() == null) {
            return false;
        }

        if (!isValidBooking(booking)) {
            return false;
        }

        return bookingRepository.addBooking(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.getAllBookings();
    }

    @Override
    public Booking getBooking(int bookingId) {
        if (bookingId <= 0) {
            return null;
        }
        return bookingRepository.getBookingByID(bookingId);
    }

    @Override
    public boolean updateBooking(Booking booking) {
        if (booking == null || booking.getFlight() == null || booking.getCustomer() == null) {
            return false;
        }

        if (!isValidBooking(booking)) {
            return false;
        }

        return bookingRepository.updateBooking(booking);
    }

    @Override
    public boolean deleteBooking(int bookingId) {
        if (bookingId <= 0) {
            return false;
        }
        return bookingRepository.deleteBooking(bookingId);
    }

    @Override
    public boolean processPayment(int bookingId, double amount) {
        if (bookingId <= 0 || amount <= 0) {
            return false;
        }
        return bookingRepository.payBooking(bookingId, amount);
    }

    @Override
    public double calculateTotalPrice(Booking booking) {
        if (!isValidBooking(booking)) {
            return 0.0;
        }

        double basePrice = booking.getFlight().getPrice();
        double totalPrice = basePrice;

        if (booking.isFirstClass()) {
            totalPrice += basePrice * 0.5;
        }

        if (booking.isCarryon()) {
            totalPrice += 20.0;
        }

        return totalPrice;
    }

    private boolean isValidBooking(Booking booking) {
        if (booking.getFlight() == null || booking.getCustomer() == null) {
            return false;
        }

        if (booking.getFlight().getPrice() <= 0) {
            return false;
        }

        if (booking.getBookingId() <= 0) {
            return false;
        }

        return true;
    }*/
}
