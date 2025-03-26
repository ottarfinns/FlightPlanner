package vinnsla.service;

import vinnsla.entities.Flight;
import vinnsla.entities.FlightList;

import java.util.List;

public class FlightServiceImplementation implements FlightService {
    private FlightList flightList;

    public FlightServiceImplementation() {
        this.flightList = new FlightList();
    }


    public Flight createFlight(Flight flight) {
        return null;
    }

    public boolean deleteFlight(Flight flight) {
        return false;
    }

    public Flight findFlight(String flightNumber) {
        return null;
    }

    public List<Flight> searchFlights(String departureCountry, String arrivalCountry) {
        return List.of();
    }

    public List<Flight> getAllFlights() {
        return List.of();
    }

    public boolean updateFlight(String flightNumber, double newPrice) {
        return false;
    }
}
