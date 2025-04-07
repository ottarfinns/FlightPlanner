package vinnsla.entities;

import java.util.Date;
import java.util.Objects;

public class Flight {

    private String flightNumber;
    private String airline;

    private String departureCountry;
    private String arrivalCountry;

    private String departureAirport;
    private String arrivalAirport;

    private Date departureDate;
    private Date arrivalDate;

    private String departureTime;
    private String arrivalTime;

    private int totalSeats;

    private int totalRows;
    private int totalCols;

    private SeatingArrangement seating;

    private Double price;

    public Flight(String flightNumber, String airline, String departureCountry, String arrivalCountry,
                 String departureAirport, String arrivalAirport, Date departureDate, Date arrivalDate,
                 String departureTime, String arrivalTime, int totalRows, int totalCols, double price) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        //flightNumber = FlightList.generateFlightNumber(airline);

        this.departureCountry = departureCountry;
        this.arrivalCountry = arrivalCountry;

        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;

        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;

        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;

        this.price = price;

        this.totalRows = totalRows;
        this.totalCols = totalCols;

        this.totalSeats = totalRows * totalCols;
        this.seating = new SeatingArrangement(totalRows, totalCols);
    }

    public boolean isFull() {
        return seating.isFull();
    }

    public void updatePrice(double price) {
        this.price = price;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public String getArrivalCountry() {
        return arrivalCountry;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }

    public Double getPrice() {
        return price;
    }

    public SeatingArrangement getSeatingArrangement() {
        return seating;
    }

    public void setSeatingArrangement(SeatingArrangement seatingArrangement) {
        this.seating = seatingArrangement;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Flight flight = (Flight) obj;
        return Objects.equals(this.flightNumber, flight.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber);
    }

    public static void main(String[] args) {
        //Flight flight = new Flight("");
    }
}
