package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateManagerController {
    @FXML
    private TextField managerIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    private ManagerDAO managerDAO = new ManagerDAO();

    @FXML
    private Manager manager;

    @FXML
    public void setManager(Manager manager) {
        this.manager = manager;
        managerIdField.setText(String.valueOf(manager.getId()));
        nameField.setText(manager.getName());
        emailField.setText(manager.getEmail());
    }

    @FXML
    private void handleUpdateManager(ActionEvent event) {
        try {
            int managerId = Integer.parseInt(managerIdField.getText());
            String name = nameField.getText();
            String email = emailField.getText();
            Manager updatedManager = new Manager(managerId, name, email);
            managerDAO.updateManager(updatedManager);
            System.out.println("Manager updated with ID: " + managerId);
            handleCancel(event); // Close the form after updating
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("manageManagers.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
