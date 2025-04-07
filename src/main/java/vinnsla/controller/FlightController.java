package vinnsla.controller;

import vinnsla.entities.Flight;
import vinnsla.service.FlightServiceInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FlightController {
    private final FlightServiceInterface flightService;

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

    public List<Flight> searchFlights(String searchCriteria) {
        return flightService.searchFlights(searchCriteria);
    }

    public List<Flight> searchReturnFlights(String searchCriteria) {
        return flightService.searchReturnFlights(searchCriteria);
    }

    public static void main(String[] args) {

    }
}
