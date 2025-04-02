package vinnsla.repository;

import vinnsla.entities.Flight;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class FlightRepository implements FlightRepositoryInterface {
    private static final String DB_DIR = "data";
    private static final String DB_URL;
    private static FlightRepository instance;
    private Connection connection;

    static {
        // Create directory and database if they don't exist
        new File(DB_DIR).mkdirs();
        DB_URL = "jdbc:sqlite:" + DB_DIR + "/flights.db";
    }

    private FlightRepository() {
        initializeDatabase();
    }

    public static FlightRepository getInstance() {
        if (instance == null) {
            instance = new FlightRepository();
        }
        return instance;
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        String createFlightsTable = """
            CREATE TABLE IF NOT EXISTS flights (
                flight_number TEXT PRIMARY KEY,
                airline TEXT NOT NULL,
                departure_country TEXT NOT NULL,
                arrival_country TEXT NOT NULL,
                departure_airport TEXT NOT NULL,
                arrival_airport TEXT NOT NULL,
                departure_time TIMESTAMP NOT NULL,
                arrival_time TIMESTAMP NOT NULL,
                total_rows INTEGER NOT NULL,
                total_cols INTEGER NOT NULL,
                price REAL NOT NULL
            )
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createFlightsTable);
        }
    }

    @Override
    public boolean addFlight(Flight flight) {
        if (flight == null || flight.getFlightNumber() == null) {
            return false;
        }

        String sql = """
            INSERT INTO flights (
                flight_number, airline, departure_country, arrival_country,
                departure_airport, arrival_airport, departure_time, arrival_time,
                total_rows, total_cols, price
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, flight.getFlightNumber());
            pstmt.setString(2, flight.getAirline());
            pstmt.setString(3, flight.getDepartureCountry());
            pstmt.setString(4, flight.getArrivalCountry());
            pstmt.setString(5, flight.getDepartureAirport());
            pstmt.setString(6, flight.getArrivalAirport());
            pstmt.setTimestamp(7, new Timestamp(flight.getDepartureTime().getTime()));
            pstmt.setTimestamp(8, new Timestamp(flight.getArrivalTime().getTime()));
            pstmt.setInt(9, flight.getTotalRows());
            pstmt.setInt(10, flight.getTotalCols());
            pstmt.setDouble(11, flight.getPrice());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Flight searchFlightNumber(String flightNumber) {
        String sql = "SELECT * FROM flights WHERE flight_number = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, flightNumber);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return createFlightFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeFlight(String flightNumber) {
        String sql = "DELETE FROM flights WHERE flight_number = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, flightNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Flight> searchFlights(String searchCriteria) {
        if (searchCriteria == null || searchCriteria.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String[] criteria = searchCriteria.split(",");
        String departureCountry = criteria[0];
        String arrivalCountry = criteria[1];
        String departureDate = criteria[2];
        String arrivalDate = criteria[3];
        int numberOfPassengers = Integer.parseInt(criteria[4]);
        int maxPrice = Integer.parseInt(criteria[5]);
        boolean isOneWay = Boolean.parseBoolean(criteria[6]);
        boolean isDirectFlight = Boolean.parseBoolean(criteria[7]);

        StringBuilder sql = new StringBuilder("SELECT * FROM flights WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (departureCountry != null && !departureCountry.isEmpty()) {
            sql.append(" AND departure_country = ?");
            params.add(departureCountry);
        }

        if (arrivalCountry != null && !arrivalCountry.isEmpty()) {
            sql.append(" AND arrival_country = ?");
            params.add(arrivalCountry);
        }

        if (departureDate != null && !departureDate.isEmpty()) {
            sql.append(" AND DATE(departure_time) = ?");
            params.add(departureDate);
        }

        if (arrivalDate != null && !arrivalDate.isEmpty()) {
            sql.append(" AND DATE(arrival_time) = ?");
            params.add(arrivalDate);
        }

        if (maxPrice > 0) {
            sql.append(" AND price <= ?");
            params.add(maxPrice);
        }

        List<Flight> flights = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    flights.add(createFlightFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    @Override
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                flights.add(createFlightFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    private Flight createFlightFromResultSet(ResultSet rs) throws SQLException {
        return new Flight(
            rs.getString("flight_number"),
            rs.getString("airline"),
            rs.getString("departure_country"),
            rs.getString("arrival_country"),
            rs.getString("departure_airport"),
            rs.getString("arrival_airport"),
            rs.getTimestamp("arrival_time"),
            rs.getTimestamp("departure_time"),
            rs.getInt("total_rows"),
            rs.getInt("total_cols"),
            rs.getDouble("price")
        );
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
