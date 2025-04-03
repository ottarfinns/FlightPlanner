package vinnsla.service;

import vinnsla.entities.Flight;
import vinnsla.repository.FlightRepository;
import vinnsla.repository.FlightRepositoryInterface;

import java.util.List;

public class FlightService implements FlightServiceInterface {
    private final FlightRepositoryInterface flightRepository;

    public FlightService() {
        this.flightRepository = FlightRepository.getInstance();
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
        System.out.println("Search flights in Service " + args);
        return flightRepository.searchFlights(args);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.getAllFlights();
    }
}
