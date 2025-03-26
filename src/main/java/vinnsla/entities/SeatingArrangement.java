package vinnsla.entities;

public class SeatingArrangement {
    private Seat[][] seats;
    private final int rows;
    private final int cols;
    private final int totalSeats;
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

    private Seat getSeat(String seatNum) {
        SeatNumber seatNumber = SeatNumber.fromString(seatNum);
        int r = seatNumber.getRow();
        int c = seatNumber.getCol();
        return seats[r][c];
    }

    private Seat getSeat(SeatNumber seatNumber) {
        int r = seatNumber.getRow();
        int c = seatNumber.getCol();
        return seats[r][c];
    }

    public void printSeating() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(seats[i][j].getSeatNumber() + (seats[i][j].isAvailable() ? "[ ] " : "[X] "));
            }
            System.out.println();
        }
    }


    public boolean cancelSeat(SeatNumber seatNumber) {
        Seat seat = getSeat(seatNumber);
        availableSeats++;
        return seat.cancelSeat();
    }


    public boolean bookSeat(SeatNumber seatNumber) {
        Seat seat = getSeat(seatNumber);

        availableSeats--;
        return seat.bookSeat();
    }

    public boolean isFull() {
        return availableSeats == totalSeats;
    }

    public static void main(String[] args) {
        SeatingArrangement seating = new SeatingArrangement(20, 6);
        SeatNumber seatNumber1 = SeatNumber.fromString("20A");
        System.out.println(seatNumber1.getCol());
        System.out.println(seatNumber1.getRow());
        System.out.println(seating.bookSeat(seatNumber1));
        System.out.println(seating.bookSeat(SeatNumber.fromString("16F")));
        seating.printSeating();
        System.out.println(seating.cancelSeat(SeatNumber.fromString("20A")));
        seating.printSeating();
    }
}
