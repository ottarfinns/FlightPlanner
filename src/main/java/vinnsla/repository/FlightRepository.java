package vinnsla.repository;

import vinnsla.entities.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.text.SimpleDateFormat;

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
                        departure_date DATE NOT NULL,
                        arrival_date DATE NOT NULL,
                        departure_time TEXT NOT NULL,
                        arrival_time TEXT NOT NULL,
                        total_rows INTEGER NOT NULL,
                        total_cols INTEGER NOT NULL,
                        price REAL NOT NULL,
                        is_full boolean NOT NULL
                    )
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createFlightsTable);
            populateSampleData();
        }
    }

    private void populateSampleData() {
        try {
            // Check if the table is empty
            String checkEmpty = "SELECT COUNT(*) FROM flights";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(checkEmpty)) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return; // Table already has data
                }
            }

            // Sample flights
            String[] airlines = {"Icelandair", "WOW Air", "Delta", "British Airways", "Lufthansa"};
            String[] departureCountries = {"Iceland", "United States", "United Kingdom", "Germany", "France"};
            String[] arrivalCountries = {"United States", "Iceland", "Germany", "France", "United Kingdom"};
            String[] departureAirports = {"KEF", "JFK", "LHR", "FRA", "CDG"};
            String[] arrivalAirports = {"JFK", "KEF", "FRA", "CDG", "LHR"};
            String[] times = {"08:00", "12:00", "15:00", "18:00", "21:00"};

            // Current date for base calculations
            long currentDate = System.currentTimeMillis();

            // Insert sample flights
            for (int i = 0; i < 10; i++) {
                String airline = airlines[i % airlines.length];
                String flightNumber = airline.substring(0, 2).toUpperCase() + (100 + i);

                // Create dates (one day apart)
                Date departureDate = new Date(currentDate + (i * 86400000L));
                Date arrivalDate = new Date(currentDate + (i * 86400000L));

                // Select times
                String departureTime = times[i % times.length];
                String arrivalTime = times[(i + 2) % times.length]; // 2 time slots later

                String insertFlight = """
                            INSERT INTO flights (
                                flight_number, airline, departure_country, arrival_country,
                                departure_airport, arrival_airport, departure_date, arrival_date,
                                departure_time, arrival_time, total_rows, total_cols, price, is_full
                            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, false)
                        """;

                try (PreparedStatement pstmt = connection.prepareStatement(insertFlight)) {
                    pstmt.setString(1, flightNumber);
                    pstmt.setString(2, airline);
                    pstmt.setString(3, departureCountries[i % departureCountries.length]);
                    pstmt.setString(4, arrivalCountries[i % arrivalCountries.length]);
                    pstmt.setString(5, departureAirports[i % departureAirports.length]);
                    pstmt.setString(6, arrivalAirports[i % arrivalAirports.length]);
                    pstmt.setDate(7, new java.sql.Date(departureDate.getTime()));
                    pstmt.setDate(8, new java.sql.Date(arrivalDate.getTime()));
                    pstmt.setString(9, departureTime);
                    pstmt.setString(10, arrivalTime);
                    pstmt.setInt(11, 20); // total_rows
                    pstmt.setInt(12, 6); // total_cols
                    pstmt.setDouble(13, 500 + (i * 100)); // price from 500 to 1400

                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                        departure_airport, arrival_airport, departure_date, arrival_date,
                        departure_time, arrival_time, total_rows, total_cols, price, is_full
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, false)
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, flight.getFlightNumber());
            pstmt.setString(2, flight.getAirline());
            pstmt.setString(3, flight.getDepartureCountry());
            pstmt.setString(4, flight.getArrivalCountry());
            pstmt.setString(5, flight.getDepartureAirport());
            pstmt.setString(6, flight.getArrivalAirport());
            pstmt.setDate(7, new java.sql.Date(flight.getDepartureDate().getTime()));
            pstmt.setDate(8, new java.sql.Date(flight.getArrivalDate().getTime()));
            pstmt.setString(9, flight.getDepartureTime());
            pstmt.setString(10, flight.getArrivalTime());
            pstmt.setInt(11, flight.getTotalRows());
            pstmt.setInt(12, flight.getTotalCols());
            pstmt.setDouble(13, flight.getPrice());

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

        StringBuilder sql = new StringBuilder("SELECT * FROM flights WHERE 1=1 AND is_full = false");
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
            try {
                // Convert the date string to a timestamp at the start of the day
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = sdf.parse(departureDate);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                sql.append(" AND departure_date >= ?");
                params.add(sqlDate);
            } catch (Exception e) {
                System.out.println("Error parsing departure date: " + e.getMessage());
            }
        }

        if (arrivalDate != null && !arrivalDate.isEmpty()) {
            try {
                // Convert the date string to a timestamp at the start of the day
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = sdf.parse(arrivalDate);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                sql.append(" AND arrival_date >= ?");
                params.add(sqlDate);
            } catch (Exception e) {
                System.out.println("Error parsing arrival date: " + e.getMessage());
            }
        }

        if (maxPrice > 0) {
            sql.append(" AND price <= ?");
            params.add(maxPrice);
        }

        // Print the complete SQL query with parameter values
        String completeQuery = sql.toString();
        for (Object param : params) {
            completeQuery = completeQuery.replaceFirst("\\?", "'" + param.toString() + "'");
        }
        System.out.println("Complete SQL Query: " + completeQuery);

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
                rs.getDate("departure_date"),
                rs.getDate("arrival_date"),
                rs.getString("departure_time"),
                rs.getString("arrival_time"),
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
