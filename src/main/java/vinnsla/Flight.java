package vinnsla;

import java.util.Date;

public class Flight {

    private int flightNumber;
    private double price;
    private String departureCountry;
    private String arrivalCountry;
    private String departureAirport;
    private String arrivalAirport;
    private Date departureTime;
    private Date arrivalTime;
    private int totalSeats;
    private int availableSeats;

    //constructor
    public Flight(int flightNumber, double price, String departureCountry, String arrivalCountry, String departureAirport,
                  String arrivalAirport, Date departureTime, Date arrivalTime, int totalSeats, int availableSeats) {
        this.flightNumber = flightNumber;
        this.price = price;
        this.departureCountry = departureCountry;
        this.arrivalCountry = arrivalCountry;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;

    }

    // Aðferð til að athuga hvort flugið sé fullt
    public boolean isFull() {
        return availableSeats <= 0;
    }

    // Aðferð til að bóka sæti
    public boolean bookFlight() {
        if (!isFull()) {
            availableSeats--;
            return true; //Bókun tóks
        }
        return false; //Flug er fullt
    }

    // Getters and setters
    public int getFlightNumber() {
        return flightNumber;
    }

    public double getPrice() {
        return price;
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

    public Date getDepartureTime() {
        return departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public static void main(String[] args) {

    }
}
