package vidmot.flightplanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import vinnsla.entities.Flight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionControllerUI extends Dialog<String> {
    @FXML
    private GridPane seatGrid;

    private CheckBox selectedSeat;
    private Flight flight;
    private List<String> occupiedSeats;

    public SeatSelectionControllerUI(Flight flight) {
        this.flight = flight;
        this.occupiedSeats = new ArrayList<>();
        // Add some sample occupied seats
        // TODO: Get occupied seats from database. Need to have all occupied seats connected to a flight
        occupiedSeats.add("1A");
        occupiedSeats.add("2B");
        occupiedSeats.add("3C");

        setTitle("Select Your Seat");
        setHeaderText("Please select your preferred seat");

        // Load the FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SeatSelection-Dialog.fxml"));

        try {
            fxmlLoader.setController(this);
            DialogPane dialogPane = fxmlLoader.load();
            setDialogPane(dialogPane);

            createSeatLayout();

            // Handle the confirm and cancel
            setResultConverter(b -> {
                if (b.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    return (String) selectedSeat.getUserData();
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

        // Row numbers added
        for (int row = 1; row <= rows; row++) {
            Label rowLabel = new Label(String.valueOf(row));
            seatGrid.add(rowLabel, 0, row);
        }

        // Add seat checkboxes
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= cols; col++) {
                String seatNumber = row + String.valueOf((char) ('A' + col - 1));
                CheckBox seat = new CheckBox();
                seat.setUserData(seatNumber);

                // Check occupied seats
                if (occupiedSeats.contains(seatNumber)) {
                    seat.setDisable(true);
                    seat.setSelected(true);
                }

                // Add listener to handle seat selection
                seat.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal) {
                        if (selectedSeat != null && selectedSeat != seat) {
                            selectedSeat.setSelected(false);
                        }
                        selectedSeat = seat;
                    } else {
                        if (selectedSeat == seat) {
                            selectedSeat = null;
                        }
                        seat.setStyle("");
                    }
                });

                seatGrid.add(seat, col, row);
            }
        }
    }
}
