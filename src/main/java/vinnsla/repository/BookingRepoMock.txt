package vinnsla.repository;

import vinnsla.entities.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingRepoMock implements BookingRepositoryInterface {
    private List<Booking> bookings;

    public BookingRepoMock() {
        this.bookings = new ArrayList<>();
    }

    @Override
    public boolean addBooking(Booking booking) {
        if (booking == null) {
            return false;
        }

        for (Booking existingBooking : bookings) {
            if (existingBooking.getBookingId() == booking.getBookingId()) {
                return false;
            }
        }

        bookings.add(booking);
        return true;
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookings;
    }

    @Override
    public Booking getBookingByID(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }
        return null;
    }

    @Override
    public boolean updateBooking(Booking booking) {
        if (booking == null) {
            return false;
        }

        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId() == booking.getBookingId()) {
                bookings.set(i, booking);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteBooking(int bookingId) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId() == bookingId) {
                bookings.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean payBooking(int bookingID, double pricePaid) {
        Booking booking = getBookingByID(bookingID);
        if (booking == null) {
            return false;
        }
        return booking.getFlight().getPrice() == pricePaid;
    }
}
