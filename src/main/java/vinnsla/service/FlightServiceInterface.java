package vinnsla.service;

import vinnsla.entities.Flight;

import java.util.List;

public interface FlightServiceInterface {

    boolean addFlight(Flight flight);
    Flight searchFlightNumber(String flightNumber);
    boolean removeFlight(String flightNumber);
    List<Flight> searchFlights(String args);
}
