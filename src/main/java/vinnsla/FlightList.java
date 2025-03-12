package vinnsla;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FlightList {

    List<Flight> flights;
    private static final Set<String> assignedFlightNums = new HashSet<>();
    private static final Random random = new Random();

    public static String generateFlightNumber(String airline) {
        String flightNumber;

        do {
            int flightNum = 100 + random.nextInt(900);
            flightNumber = airline.substring(0, 2).toUpperCase() + flightNum;
        } while (assignedFlightNums.contains(flightNumber));

        assignedFlightNums.add(flightNumber);
        return flightNumber;
    }

    public static void main(String[] args) {

        System.out.println(FlightList.generateFlightNumber("Icelandair"));

    }
}
