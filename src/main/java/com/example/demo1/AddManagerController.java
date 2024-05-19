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

public class AddManagerController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    private ManagerDAO managerDAO = new ManagerDAO();

    @FXML
    private void handleAddManager(ActionEvent event) {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            Manager manager = new Manager(0, name, email);
            int managerId = managerDAO.addManager(manager);
            System.out.println("Manager added with ID: " + managerId);
            handleCancel(event); // Close the form after adding
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        MainController.loadScene("manageManagers.fxml");
    }
}
