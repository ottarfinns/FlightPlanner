package vidmot.flightplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vinnsla.service.BookingService;
import vinnsla.service.BookingServiceInterface;
import vinnsla.service.FlightService;
import vinnsla.service.FlightServiceInterface;

import java.io.IOException;

public class FlightApplication extends Application {
    private static FlightServiceInterface flightService;
    private static BookingServiceInterface bookingService;
    @Override
    public void start(Stage stage) throws IOException {
        // Set up the service
        flightService = new FlightService();
        bookingService = new BookingService();

        // Create the FXML loader
        FXMLLoader fxmlLoader = new FXMLLoader(FlightApplication.class.getResource("FlightPlanner-View.fxml"));

        // Load the FXML first
        fxmlLoader.load();

        // Then get the controller and set the service
        FlightControllerUI controller = fxmlLoader.getController();
        controller.setFlightService(flightService);

        // Create the scene with the loaded FXML
        Scene scene = new Scene(fxmlLoader.getRoot(), 900, 900);
        stage.setTitle("Flight Planner - 3F");
        stage.setScene(scene);
        stage.show();
    }

    public static FlightServiceInterface getFlightService() {
        return flightService;
    }

    public static BookingServiceInterface getBookingService() {
        return bookingService;
    }

    public static void main(String[] args) {
        launch();
    }
}
