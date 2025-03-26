package vinnsla.service;

import vinnsla.entities.Flight;

import java.util.List;

public interface FlightRepository {

    boolean addFlight(Flight flight);

    Flight searchFlight(int flightNumber);

    boolean removeFlight(int flightNumber);

    List<Flight> searchFlights(String args);
}
