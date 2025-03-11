package vinnsla;

public class SeatNumber {

    private final int row;
    private final char col;

    public SeatNumber(int row, Character col) {
        if (row < 1) {
            throw new IllegalArgumentException("Row number must be positive.");
        }
        if (col < 'A' || col > 'Z') {
            throw new IllegalArgumentException("Invalid seat column: " + col);
        }
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public Character getCol() {
        return col;
    }

    @Override
    public String toString() {
        return String.valueOf(row) + col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatNumber that = (SeatNumber) o;
        return row == that.row && col == that.col;
    }


    public static void main(String[] args) {
        SeatNumber seatNumber = new SeatNumber(1, 'A');
        System.out.println(seatNumber);
        System.out.println(seatNumber.getRow());
        System.out.println(seatNumber.getCol());
    }
}
