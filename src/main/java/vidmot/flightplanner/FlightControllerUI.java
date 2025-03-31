package vidmot.flightplanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FlightControllerUI {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
