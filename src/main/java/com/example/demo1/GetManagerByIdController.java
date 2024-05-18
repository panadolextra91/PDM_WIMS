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

public class GetManagerByIdController {
    @FXML
    private TextField managerIdField;

    private ManagerDAO managerDAO = new ManagerDAO();

    @FXML
    private void handleGetManagerById(ActionEvent event) {
        try {
            int managerId = Integer.parseInt(managerIdField.getText());
            Manager manager = managerDAO.getManagerById(managerId);
            if (manager != null) {
                System.out.println("Manager ID: " + manager.getId() + ", Name: " + manager.getName() + ", Email: " + manager.getEmail());
            } else {
                System.out.println("Manager not found.");
            }
            handleCancel(event); // Close the form after displaying
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
