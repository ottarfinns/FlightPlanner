package vinnsla.service;

import vinnsla.entities.Flight;
import vinnsla.repository.FlightRepositoryInterface;

import java.util.List;

public class FlightServiceImplementation implements FlightService {
    private FlightRepositoryInterface flightRepository;

    public FlightServiceImplementation(FlightRepositoryInterface flightRepository) {
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
    public Flight searchFlightNumber(String flightNumber) {
        return flightRepository.searchFlightNumber(flightNumber);
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
