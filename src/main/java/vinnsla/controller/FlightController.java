package vinnsla.controller;

import vinnsla.entities.Flight;
import vinnsla.service.FlightServiceInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public List<Flight> searchFlights(String searchCriteria) {
        String[] criteria = searchCriteria.split(",");

        // Brottfarastaður og komustaður
        String departureCountry = criteria[0];
        String arrivalCountry = criteria[1];

        // Dagssetningar
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate departureDate = null;
        LocalDate arrivalDate = null;

        try {
            if (!criteria[2].isEmpty()) {
                departureDate = LocalDate.parse(criteria[2], formatter);
            }
            if (!criteria[3].isEmpty()) {
                arrivalDate = LocalDate.parse(criteria[3], formatter);
            }
        } catch (DateTimeParseException e) {
            // If date parsing fails continue with null dates
            System.out.println("Invalid date format: " + e.getMessage());
        }

        // Fjöldi farþega og hámarksverð
        int numberOfPassengers = Integer.parseInt(criteria[4]);
        int maxPrice = Integer.parseInt(criteria[5]);

        // Beint og one way flug
        boolean isOneWay = Boolean.parseBoolean(criteria[6]);
        boolean isDirectFlight = Boolean.parseBoolean(criteria[7]);

        System.out.println("Searching flights with criteria:");
        System.out.println("From: " + departureCountry);
        System.out.println("To: " + arrivalCountry);
        System.out.println("Departure Date: " + departureDate);
        System.out.println("Arrival Date: " + arrivalDate);
        System.out.println("Passengers: " + numberOfPassengers);
        System.out.println("Max Price: " + maxPrice);
        System.out.println("One Way: " + isOneWay);
        System.out.println("Direct Flight: " + isDirectFlight);

        return flightService.searchFlights(searchCriteria);
        //return flightService.getAllFlights();
    }

    public static void main(String[] args) {

    }
}
