package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordTextField;
    @FXML
    private ImageView eyeIcon;
    private boolean isPasswordVisible = false;

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

    @FXML
    protected void togglePasswordVisibility(MouseEvent event) {
        if (isPasswordVisible) {
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordTextField.setVisible(false);
            eyeIcon.setImage(new Image(getClass().getResourceAsStream("/images/eye2.png")));
            isPasswordVisible = false;
        } else {
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            passwordField.setVisible(false);
            eyeIcon.setImage(new Image(getClass().getResourceAsStream("/images/eye-white.png")));
            isPasswordVisible = true;
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
