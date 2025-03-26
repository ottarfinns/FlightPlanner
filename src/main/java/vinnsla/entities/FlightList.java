package vinnsla.entities;

import java.util.*;
import java.sql.SQLException;
import vinnsla.FlightDatabase;

public class FlightList {
    private List<Flight> flights;
    private static final Set<String> assignedFlightNums = new HashSet<>();
    private static final Random random = new Random();
    private final FlightDatabase database;

    public FlightList() {
        flights = new ArrayList<>();
        database = FlightDatabase.getInstance();
        loadFlightsFromDatabase();
    }

    private void loadFlightsFromDatabase() {
        try {
            flights = database.getAllFlights();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFlight(Flight flight) {
        try {
            database.addFlight(flight);
            flights.add(flight);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String generateFlightNumber(String airline) {
        String flightNumber;

        do {
            int flightNum = 100 + random.nextInt(900);
            flightNumber = airline.substring(0, 2).toUpperCase() + flightNum;
        } while (assignedFlightNums.contains(flightNumber));

        assignedFlightNums.add(flightNumber);
        return flightNumber;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Flight getFlightByNumber(String flightNumber) {
        try {
            return database.getFlightByNumber(flightNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateFlightPrice(String flightNumber, double newPrice) {
        try {
            database.updateFlightPrice(flightNumber, newPrice);
            Flight flight = getFlightByNumber(flightNumber);
            if (flight != null) {
                flight.updatePrice(newPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFlight(String flightNumber) {
        try {
            database.deleteFlight(flightNumber);
            flights.removeIf(flight -> flight.getFlightNumber().equals(flightNumber));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(FlightList.generateFlightNumber("Icelandair"));
    }
}
