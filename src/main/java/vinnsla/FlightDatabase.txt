package vinnsla;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import vinnsla.entities.Flight;

public class FlightDatabase {
    private static final String DB_DIR = "data";
    private static final String DB_URL;
    private static FlightDatabase instance;
    private Connection connection;

    static {
        // Búa til directory og gagnagrunn ef það er ekki til staðar
        new File(DB_DIR).mkdirs();
        DB_URL = "jdbc:sqlite:" + DB_DIR + "/flights.db";
    }

    private FlightDatabase() {
        initializeDatabase();
    }

    public static FlightDatabase getInstance() {
        if (instance == null) {
            instance = new FlightDatabase();
        }
        return instance;
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
            insertSampleFlights();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSampleFlights() throws SQLException {
        // Athugum hvort að það séu flug í gagnagrunninum
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM flights")) {
            if (rs.next() && rs.getInt(1) > 0) {
                return; // Flug eru nú þegar í gagnagrunninum
            }
        }

        // Some sample flights
        String[][] sampleFlights = {
            {"Icelandair", "Iceland", "United States", "Keflavik", "New York JFK", "2024-03-25 10:00:00", "2024-03-25 12:30:00", "30", "6", "450.00"},
            {"Icelandair", "Iceland", "United Kingdom", "Keflavik", "London Heathrow", "2024-03-25 14:00:00", "2024-03-25 16:30:00", "30", "6", "380.00"},
            {"Icelandair", "Iceland", "Germany", "Keflavik", "Berlin", "2024-03-25 16:00:00", "2024-03-25 19:30:00", "30", "6", "420.00"},
            {"Icelandair", "Iceland", "France", "Keflavik", "Paris CDG", "2024-03-26 09:00:00", "2024-03-26 12:30:00", "30", "6", "410.00"},
            {"Icelandair", "Iceland", "Denmark", "Keflavik", "Copenhagen", "2024-03-26 11:00:00", "2024-03-26 13:30:00", "30", "6", "350.00"}
        };

        String sql = """
            INSERT INTO flights (
                airline, departure_country, arrival_country,
                departure_airport, arrival_airport, departure_time,
                arrival_time, total_rows, total_cols, price
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (String[] flight : sampleFlights) {
                pstmt.setString(1, flight[0]); // airline
                pstmt.setString(2, flight[1]); // departure_country
                pstmt.setString(3, flight[2]); // arrival_country
                pstmt.setString(4, flight[3]); // departure_airport
                pstmt.setString(5, flight[4]); // arrival_airport
                pstmt.setTimestamp(6, Timestamp.valueOf(flight[5])); // departure_time
                pstmt.setTimestamp(7, Timestamp.valueOf(flight[6])); // arrival_time
                pstmt.setInt(8, Integer.parseInt(flight[7])); // total_rows
                pstmt.setInt(9, Integer.parseInt(flight[8])); // total_cols
                pstmt.setDouble(10, Double.parseDouble(flight[9])); // price

                pstmt.executeUpdate();
            }
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

    public void addFlight(Flight flight) throws SQLException {
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

            pstmt.executeUpdate();
        }
    }

    public List<Flight> getAllFlights() throws SQLException {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Flight flight = new Flight(
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
                flights.add(flight);
            }
        }
        return flights;
    }

    public Flight getFlightByNumber(String flightNumber) throws SQLException {
        String sql = "SELECT * FROM flights WHERE flight_number = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, flightNumber);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Flight(
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
            }
        }
        return null;
    }

    public void updateFlightPrice(String flightNumber, double newPrice) throws SQLException {
        String sql = "UPDATE flights SET price = ? WHERE flight_number = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, newPrice);
            pstmt.setString(2, flightNumber);
            pstmt.executeUpdate();
        }
    }

    public void deleteFlight(String flightNumber) throws SQLException {
        String sql = "DELETE FROM flights WHERE flight_number = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, flightNumber);
            pstmt.executeUpdate();
        }
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