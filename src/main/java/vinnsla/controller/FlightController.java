package vinnsla.controller;

import vinnsla.entities.Flight;
import vinnsla.service.FlightServiceInterface;

import java.util.List;

public class FlightController {
    private FlightServiceInterface flightService;

    public FlightController(FlightServiceInterface flightService) {
        this.flightService = flightService;
    }

    public boolean addFlight(Flight flight) {
        return flightService.addFlight(flight);
    }

    public Flight searchFlightNumber(String flightNumber) {
        return flightService.searchFlightNumber(flightNumber);
    }

    public boolean removeFlight(String flightNumber) {
        return flightService.removeFlight(flightNumber);
    }

    public List<Flight> searchFlights(String args) {
        return flightService.searchFlights(args);
    }

    public static void main(String[] args) {

    }
}
