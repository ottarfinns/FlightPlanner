module vidmot.flightplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens vidmot.flightplanner to javafx.fxml;
    exports vidmot.flightplanner;
}