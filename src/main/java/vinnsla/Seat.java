package vinnsla;

public class Seat {

    //private String seatNumber;
    private final String seatClass;
    private final SeatNumber seatNumber;
    private boolean isBooked;

    public Seat(SeatNumber seatNumber, String seatClass){
        //this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        isBooked = false;
    }

    public boolean isAvailable() {
        return !isBooked;
    }

    public SeatNumber getSeatNumber() {
        return seatNumber;
    }

    public boolean bookSeat() {
        if (!isBooked) {
            isBooked = true;
            return true;
        }
        return false;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public boolean cancelSeat() {
        if (isBooked) {
            isBooked = false;
            return true;
        }
        return false;

    }

    public static void main(String[] args) {

    }
}
