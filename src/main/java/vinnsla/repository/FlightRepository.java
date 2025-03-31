package vinnsla.repository;

import vinnsla.entities.Flight;

import java.util.List;

public class FlightRepository implements FlightRepositoryInterface {

    @Override
    public boolean addFlight(Flight flight) {
        return false;
    }

    @Override
    public Flight searchFlightNumber(String flightNumber) {
        return null;
    }

    @Override
    public boolean removeFlight(String flightNumber) {
        return false;
    }

    @Override
    public List<Flight> searchFlights(String args) {
        return List.of();
    }

    @Override
    public List<Flight> getAllFlights() {
        return List.of();
    }
}
