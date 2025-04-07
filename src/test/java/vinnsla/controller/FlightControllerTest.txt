package vinnsla.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import vinnsla.entities.Flight;
import vinnsla.service.FlightServiceInterface;
import vinnsla.service.FlightService;
import vinnsla.repository.FlightRepositoryInterface;
import vinnsla.repository.FlightRepoMock;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightControllerTest {
    private FlightController flightController;
    private FlightServiceInterface flightService;
    private FlightRepositoryInterface flightRepository;
    private Flight testFlight;

    @BeforeEach
    void setUp() {
        flightRepository = new FlightRepoMock();
        flightService = new FlightService(flightRepository);
        flightController = new FlightController(flightService);

        // Valid test flight
        testFlight = new Flight(
            "FI123",
            "Icelandair",
            "Iceland",
            "United States",
            "Keflavik",
            "Portland",
            new Date(System.currentTimeMillis()),
            new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000),
            30,
            6,
            40000.0
        );
    }

    @AfterEach
    void tearDown() {
        flightController = null;
        flightService = null;
        flightRepository = null;
        testFlight = null;
    }

    @Test
    void testAddFlight() {
        assertTrue(flightController.addFlight(testFlight));

        assertFalse(flightController.addFlight(null));

        Flight invalidFlight = new Flight(
            null,  // null flight number
            "Icelandair",
            "Iceland",
            "Norway",
            "Keflavik",
            "Oslo",
            new Date(),
            new Date(),
            30,
            6,
            400.0
        );
        assertFalse(flightController.addFlight(invalidFlight));
    }

    @Test
    void testSearchFlight() {
        flightController.addFlight(testFlight);

        Flight found = flightController.searchFlightNumber(testFlight.getFlightNumber());
        assertNotNull(found);
        assertEquals(testFlight.getFlightNumber(), found.getFlightNumber());

        assertNull(flightController.searchFlightNumber("Ekki rétt flightnumber"));
    }

    @Test
    void testRemoveFlight() {
        flightController.addFlight(testFlight);

        assertTrue(flightController.removeFlight(testFlight.getFlightNumber()));

        assertNull(flightController.searchFlightNumber(testFlight.getFlightNumber()));

        assertFalse(flightController.removeFlight("NONEXISTENT"));
    }

    @Test
    void testSearchFlights() {
        flightController.addFlight(testFlight);

        List<Flight> results = flightController.searchFlights("Icelandair");
        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(f -> f.getFlightNumber().equals(testFlight.getFlightNumber())));

        assertTrue(flightController.searchFlights("").isEmpty());

        assertTrue(flightController.searchFlights(null).isEmpty());
    }
}
