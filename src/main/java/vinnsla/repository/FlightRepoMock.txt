package vinnsla.repository;

import vinnsla.entities.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightRepoMock implements FlightRepositoryInterface {
    private List<Flight> flights;

    public FlightRepoMock() {
        flights = new ArrayList<>();
    }

    @Override
    public boolean addFlight(Flight flight) {
        if (flight == null || flight.getFlightNumber() == null) {
            return false;
        }
        return flights.add(flight);
    }

    @Override
    public Flight searchFlightNumber(String flightNumber) {
        return flights.stream()
                .filter(f -> f.getFlightNumber().equals(flightNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean removeFlight(String flightNumber) {
        return flights.removeIf(f -> f.getFlightNumber().equals(flightNumber));
    }

    @Override
    public List<Flight> searchFlights(String airline) {
        if (airline == null || airline.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return flights.stream()
                .filter(f -> f.getAirline().toLowerCase().contains(airline.toLowerCase()))
                .toList();
    }

    public List<Flight> getAllFlights() {
        return flights;
    }
}
