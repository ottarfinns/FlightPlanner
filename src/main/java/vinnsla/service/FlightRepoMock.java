package vinnsla.service;

import vinnsla.entities.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightRepoMock{
    private List<Flight> flights = new ArrayList<>();

    public boolean addFlight(Flight flight) {
        if (flight == null || flight.getFlightNumber() == null) {
            return false;
        }
        if (flights.contains(flight)) {
            return false;
        }

        flights.add(flight);
        return true;
    }

    public Flight searchFlight(String flightNumber) {
        return (Flight) flights.stream()
                .filter(f -> f.getFlightNumber().equals(flightNumber))
                .findFirst()
                .orElse(null);
    }

    public boolean removeFlight(String flightNumber) {
        return flights.removeIf(f -> f.getFlightNumber().equals(flightNumber));
    }

    public List<Flight> searchFlights(String args) {
        return List.of();
    }

    public List<Flight> getAllFlights() {
        return flights;
    }
}
