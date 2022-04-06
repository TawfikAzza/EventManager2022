package bll.utils;

import javafx.scene.control.Alert;

public class DisplayError {

    public static void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
    public static void displayMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You are missing data");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
