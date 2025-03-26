package vinnsla.service;

import vinnsla.entities.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightRepoMock implements FlightRepository{
    private List<Flight> flights = new ArrayList<>();

    public boolean addFlight(Flight flight) {
        return false;
    }

    public Flight searchFlight(int flightNumber) {
        return null;
    }

    public boolean removeFlight(int flightNumber) {
        return false;
    }

    public List<Flight> searchFlights(String args) {
        return List.of();
    }
}
