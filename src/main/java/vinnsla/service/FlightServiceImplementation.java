package vinnsla.service;

import vinnsla.entities.Flight;

import java.util.List;

public class FlightServiceImplementation implements FlightService {
    private FlightRepository flightRepository;

    public FlightServiceImplementation(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public boolean addFlight(Flight flight) {
        if (flight == null || flight.getFlightNumber() == null) {
            return false;
        }
        return flightRepository.addFlight(flight);
    }

    @Override
    public Flight searchFlight(String flightNumber) {
        return flightRepository.searchFlight(flightNumber);
    }

    @Override
    public boolean removeFlight(String flightNumber) {
        return flightRepository.removeFlight(flightNumber);
    }

    @Override
    public List<Flight> searchFlights(String args) {
        return flightRepository.searchFlights(args);
    }
}
