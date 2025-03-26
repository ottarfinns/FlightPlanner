package vinnsla.service;

import vinnsla.entities.Flight;

import java.util.List;

public interface FlightService {

    boolean addFlight(Flight flight);
    Flight searchFlight(String flightNumber);
    boolean removeFlight(String flightNumber);
    List<Flight> searchFlights(String args);
}
