package vinnsla;

import java.util.Date;

public class Flight {

    private String flightNumber;
    private String airline;

    private String departureCountry;
    private String arrivalCountry;

    private String departureAirport;
    private String arrivalAirport;

    private Date departureTime;
    private Date arrivalTime;

    private int totalSeats;

    private int totalRows;
    private int totalCols;

    private SeatingArrangement seating;

    private Double price;

    public Flight(String airline, String departureCountry, String arrivalCountry, String departureAirport, String arrivalAirport,
                  Date arrivalTime, Date departureTime, int totalRows, int totalCols, double price) {

        this.airline = airline;
        flightNumber = FlightList.generateFlightNumber(airline);

        this.departureCountry = departureCountry;
        this.arrivalCountry = arrivalCountry;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.totalRows = totalRows;
        this.totalCols = totalCols;
        this.price = price;
        totalSeats = totalRows * totalCols;
        seating = new SeatingArrangement(totalRows, totalCols);
    }

    public boolean isFull() {
        return seating.isFull();
    }

    public void updatePrice(double price) {
        this.price = price;
    }

    // Getters and setters

    public static void main(String[] args) {
        //Flight flight = new Flight("");

    }
}
