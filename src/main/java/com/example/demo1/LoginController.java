package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    protected void handleLogin(MouseEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("admin123")) {
            try {
                // Close the current login window
                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.close();

                // Load the main menu scene
                MainController.loadScene("mainMenu.fxml");
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAlert("Failed to load main menu.");
            }
        } else {
            showErrorAlert("User name or password is incorrect.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
