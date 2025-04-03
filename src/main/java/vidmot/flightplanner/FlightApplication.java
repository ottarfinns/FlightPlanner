package vidmot.flightplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vinnsla.service.FlightService;
import vinnsla.service.FlightServiceInterface;

import java.io.IOException;

public class FlightApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Set up the service
        FlightServiceInterface flightService = new FlightService();

        // Create the FXML loader
        FXMLLoader fxmlLoader = new FXMLLoader(FlightApplication.class.getResource("FlightPlanner-View.fxml"));

        // Load the FXML first
        fxmlLoader.load();

        // Then get the controller and set the service
        FlightControllerUI controller = fxmlLoader.getController();
        controller.setFlightService(flightService);

        // Create the scene with the loaded FXML
        Scene scene = new Scene(fxmlLoader.getRoot(), 600, 700);
        stage.setTitle("Flight Planner - 3F");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
