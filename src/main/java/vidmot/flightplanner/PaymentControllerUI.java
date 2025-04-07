package vidmot.flightplanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

public class PaymentControllerUI extends Dialog<Boolean> {
    @FXML
    private Label totalPriceLabel;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField cardHolderField;
    @FXML
    private TextField expiryDateField;
    @FXML
    private TextField cvvField;
    @FXML
    private ButtonType confirmButton;
    @FXML
    private ButtonType cancelButton;

    private int totalPrice;

    public PaymentControllerUI(int totalPrice) {
        this.totalPrice = totalPrice;

        setTitle("Payment Information");
        setHeaderText("Please enter your payment details");

        // Load the FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Payment-Dialog.fxml"));
        fxmlLoader.setController(this);

        try {
            DialogPane dialogPane = fxmlLoader.load();
            setDialogPane(dialogPane);

            // Set the total price
            totalPriceLabel.setText(totalPrice + " kr.");

            // Set up the result converter
            setResultConverter(buttonType -> {
                if (buttonType == confirmButton) {
                    // Check if all fields are filled
                    if (cardNumberField.getText().isEmpty() ||
                        cardHolderField.getText().isEmpty() ||
                        expiryDateField.getText().isEmpty() ||
                        cvvField.getText().isEmpty()) {
                        showAlert("Error", "Please fill in all payment details");
                        return false;
                    }
                    return true;
                }
                return false;
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
