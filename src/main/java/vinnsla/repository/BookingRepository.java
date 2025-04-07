package vinnsla.repository;

import vinnsla.entities.Booking;
import vinnsla.entities.SeatingArrangement;
import vinnsla.entities.Flight;
import vinnsla.entities.SeatNumber;

import java.io.File;
import java.sql.*;

public class BookingRepository implements BookingRepositoryInterface {
    private static final String DB_DIR = "data";
    private static final String DB_URL;
    private static BookingRepository instance;
    private Connection connection;

    static {
        new File(DB_DIR).mkdirs();
        DB_URL = "jdbc:sqlite:" + DB_DIR + "/flights.db";
    }

    private BookingRepository() {
        initializeDatabase();
    }

    public static BookingRepository getInstance() {
        if (instance == null) {
            instance = new BookingRepository();
        }
        return instance;
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private void createTables() throws SQLException {
        String createBookingsTable = """
                CREATE TABLE IF NOT EXISTS bookings (
                flight_number TEXT NOT NULL,
                name TEXT NOT NULL,
                nationalId TEXT NOT NULL,
                passportNr TEXT NOT NULL,
                phoneNr TEXT NOT NULL,
                country TEXT NOT NULL,
                city TEXT NOT NULL,
                address TEXT NOT NULL,
                seat TEXT NOT NULL,
                carryOn boolean NOT NULL,
                class TEXT NOT NULL,
                baggage INTEGER NOT NULL,
                totalPrice INTEGER NOT NULL,
                PRIMARY KEY (flight_number, nationalId),
                FOREIGN KEY (flight_number) REFERENCES flights(flight_number)
                )
                """; // flight_number + nationalid er lykillinn

        String createBookedSeatsTable = """
                CREATE TABLE IF NOT EXISTS bookedSeats (
                flight_number TEXT NOT NULL,
                seat_number TEXT NOT NULL,
                PRIMARY KEY (flight_number, seat_number),
                FOREIGN KEY (flight_number) REFERENCES flights(flight_number)
                )
                """; // flight_number + seat_number er lykillinn

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createBookingsTable);
            stmt.execute(createBookedSeatsTable);
        }
    }

    @Override
    public boolean addBooking(Booking booking) {
        if (booking == null || booking.getFlight().getFlightNumber() == null) {
            return false;
        }

        String sql = """
                INSERT INTO bookings (
                flight_number, name, nationalId, passportNr, phoneNr,
                country, city, address, seat, carryOn, class,
                baggage, totalPrice
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, booking.getFlight().getFlightNumber());
            pstmt.setString(2, booking.getPassenger().getName());
            pstmt.setString(3, booking.getPassenger().getNationalId());
            pstmt.setString(4, booking.getPassenger().getPassportNr());
            pstmt.setString(5, booking.getPassenger().getPhoneNr());
            pstmt.setString(6, booking.getPassenger().getCountry());
            pstmt.setString(7, booking.getPassenger().getCity());
            pstmt.setString(8, booking.getPassenger().getAddress());
            pstmt.setString(9, booking.getSeat());
            pstmt.setBoolean(10, booking.getCarryOn());
            pstmt.setString(11, booking.getClassName());
            pstmt.setInt(12, booking.getBaggage());
            pstmt.setInt(13, booking.getTotalPrice());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
           e.printStackTrace();
           return false;
        }
    }

    @Override
    public boolean bookSeat(String flightNumber, String seatNumber) {
        if (flightNumber == null || seatNumber == null) {
            return false;
        }
        else {
            String sql = """
                    INSERT INTO bookedSeats (
                    flight_number, seat_number
                    ) VALUES (?, ?)
                    """;

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, flightNumber);
                pstmt.setString(2, seatNumber);
                return pstmt.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public SeatingArrangement getBookedSeats(String flightNumber) {
        if (flightNumber == null) {
            return null;
        }

        // First get the flight to get its dimensions
        Flight flight = FlightRepository.getInstance().searchFlightNumber(flightNumber);
        if (flight == null) {
            return null;
        }

        // Create a new seating arrangement with the flight's dimensions
        SeatingArrangement seating = new SeatingArrangement(flight.getTotalRows(), flight.getTotalCols());

        String sql = """
                SELECT seat_number FROM bookedSeats WHERE flight_number = ?
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, flightNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String seatNumber = rs.getString("seat_number");
                    try {
                        SeatNumber seatNum = SeatNumber.fromString(seatNumber);
                        seating.bookSeat(seatNum);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid seat number format: " + seatNumber);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return seating;
    }


}
