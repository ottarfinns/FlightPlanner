package vinnsla.controller;

import vinnsla.entities.Flight;
import vinnsla.service.FlightService;

import java.util.List;

public class FlightController {
    private FlightService flightService;

    public boolean addFlight(Flight flight) {
        return flightService.addFlight(flight);
    }

    public Flight searchFlight(String flightNumber) {
        return flightService.searchFlight(flightNumber);
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
