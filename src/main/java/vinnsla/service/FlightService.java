package vinnsla.service;

import vinnsla.entities.Flight;

import java.util.List;

public interface FlightService {
    // Service layer contains the business logic
    // Acts as an intermediary between controllers (handle requests) and repositories (handle data storage)

    Flight createFlight(Flight flight);
    boolean deleteFlight(Flight flight);
    Flight findFlight(String flightNumber);
    List<Flight> searchFlights(String departureCountry, String arrivalCountry);
    List<Flight> getAllFlights();
    boolean updateFlight(String flightNumber, double newPrice);
}
