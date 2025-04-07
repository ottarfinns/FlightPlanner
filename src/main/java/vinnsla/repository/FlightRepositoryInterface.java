package vinnsla.repository;

import vinnsla.entities.Flight;
import java.util.List;

public interface FlightRepositoryInterface {
    boolean addFlight(Flight flight);
    Flight searchFlightNumber(String flightNumber);
    boolean removeFlight(String flightNumber);
    List<Flight> searchFlights(String args);
    List<Flight> searchReturnFlights(String args);
    List<Flight> getAllFlights();
}
