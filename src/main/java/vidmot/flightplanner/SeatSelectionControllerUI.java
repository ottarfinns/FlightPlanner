package vidmot.flightplanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import vinnsla.entities.Flight;
import vinnsla.entities.Seat;
import vinnsla.entities.SeatNumber;
import vinnsla.entities.SeatingArrangement;

import java.io.IOException;

public class SeatSelectionControllerUI extends Dialog<String> {
    @FXML
    private GridPane seatGrid;

    private CheckBox selectedSeat;
    private Flight flight;
    private SeatingArrangement seating;

    public SeatSelectionControllerUI(Flight flight) {
        this.flight = flight;
        this.seating = flight.getSeatingArrangement();

        setTitle("Select Your Seat");
        setHeaderText("Please select your preferred seat");

        // Load the FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SeatSelection-Dialog.fxml"));
        fxmlLoader.setController(this);

        try {
            DialogPane dialogPane = fxmlLoader.load();
            setDialogPane(dialogPane);

            createSeatLayout();

            // Handle the confirm and cancel
            setResultConverter(buttonType -> {
                if (buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE && selectedSeat != null) {
                    String seatNumber = selectedSeat.getUserData().toString();
                    SeatNumber seatNum = SeatNumber.fromString(seatNumber);
                    if (seating.bookSeat(seatNum)) {
                        return seatNumber;
                    }
                }
                return null;
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSeatLayout() {
        int rows = flight.getTotalRows();
        int cols = flight.getTotalCols();

        // Add column headers (A, B, C, etc.)
        for (int col = 1; col <= cols; col++) {
            Label colLabel = new Label(String.valueOf((char) ('A' + col - 1)));
            seatGrid.add(colLabel, col, 0);
        }

        // Add seat checkboxes
        for (int row = 1; row <= rows; row++) {
            // Add row number
            Label rowLabel = new Label(String.valueOf(row));
            seatGrid.add(rowLabel, 0, row);

            for (int col = 1; col <= cols; col++) {
                String seatNumber = row + String.valueOf((char) ('A' + col - 1));
                CheckBox seat = new CheckBox();
                seat.setUserData(seatNumber);

                // Get the seat from the seating arrangement
                SeatNumber seatNum = SeatNumber.fromString(seatNumber);
                Seat actualSeat = seating.getSeat(seatNum);

                // Set seat properties based on availability
                if (!actualSeat.isAvailable()) {
                    seat.setDisable(true);
                    seat.setSelected(true);
                    seat.setStyle("-fx-background-color: #ffcccc;"); // Red for occupied
                } else {
                    // Add listener to handle seat selection
                    seat.selectedProperty().addListener((obs, oldVal, newVal) -> {
                        if (newVal) {
                            if (selectedSeat != null && selectedSeat != seat) {
                                selectedSeat.setSelected(false);
                            }
                            selectedSeat = seat;
                            seat.setStyle("-fx-background-color: #ccffcc;"); // Green for selected
                        } else {
                            if (selectedSeat == seat) {
                                selectedSeat = null;
                            }
                            seat.setStyle("");
                        }
                    });
                }

                seatGrid.add(seat, col, row);
            }
        }
    }
}
