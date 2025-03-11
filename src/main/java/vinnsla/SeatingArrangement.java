package vinnsla;

public class SeatingArrangement {
    private Seat[][] seats;
    private int rows;
    private int cols;
    private int totalSeats;
    private int availableSeats;

    public SeatingArrangement(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        totalSeats = rows * cols;
        availableSeats = totalSeats;

        initSeats();
    }

    private void initSeats() {
        seats = new Seat[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int row = i + 1;
                char col = (char) ('A' + j);
                SeatNumber seatNumber = new SeatNumber(row, col);
                String seatClass = (i < 2) ? "First Class" : (i < 5) ? "Business" : "Economy";
                seats[i][j] = new Seat(seatNumber, seatClass);
            }
        }
    }

    private Seat getSeat(String seatNumber) {
        if (seatNumber.length() != 2) {
            return null;
        }
        else{
            int c = seatNumber.charAt(1) - 'A';
            int r = Integer.parseInt(seatNumber.substring(0, 1)) - 1;
            return seats[r][c];
        }
    }

    public void printSeating() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(seats[i][j].getSeatNumber() + (seats[i][j].isAvailable() ? "[ ] " : "[X] "));
            }
            System.out.println();
        }
    }



    public boolean cancelSeat(String seatNumber) {

        Seat seat = getSeat(seatNumber);

        if (seat == null) {
            return false;
        }
        else {
            seat.cancelSeat();
            availableSeats++;
            return true;
        }
    }


    public boolean bookSeat(String seatNumber) {
        Seat seat = getSeat(seatNumber);
        if (seat == null) {
            return false;
        }
        else {
            seat.bookSeat();
            availableSeats--;
            return true;
        }

    }

    public static void main(String[] args) {
        SeatingArrangement seating = new SeatingArrangement(20, 6);
        System.out.println(seating.bookSeat("2A"));
        System.out.println(seating.bookSeat("20F"));
        seating.printSeating();
    }
}
