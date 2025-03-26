package vinnsla.entities;

public class SeatNumber {

    private final int row;
    private final char col;

    public SeatNumber(int row, char col) {
        if (row < 1) {
            throw new IllegalArgumentException("Row number must be positive.");
        }
        if (col < 'A' || col > 'Z') {
            throw new IllegalArgumentException("Invalid seat column: " + col);
        }
        this.row = row;
        this.col = col;
    }

    public static SeatNumber fromString(String seatString) {

        int length = seatString.length();

        if (length < 2 || length > 3) {
            throw new IllegalArgumentException("Invalid seat number format: " + seatString);
        }
        int row;
        char col;

        try {
            row = Integer.parseInt(seatString.substring(0, length - 1));
            col = seatString.charAt(length - 1);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid seat number format: " + seatString);
        }

        return new SeatNumber(row, col);
    }

    public int getRow() {
        return row - 1;
    }

    public int getCol() {
        return col - 'A';
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
