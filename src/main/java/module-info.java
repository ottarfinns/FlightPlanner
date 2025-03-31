module vidmot.flightplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens vidmot.flightplanner to javafx.fxml;
    exports vidmot.flightplanner;
}