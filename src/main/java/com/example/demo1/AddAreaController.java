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

public class AddAreaController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField managerIdField;

    private AreaDAO areaDAO = new AreaDAO();

    @FXML
    private void handleAddArea(ActionEvent event) {
        try {
            String name = nameField.getText();
            int managerId = Integer.parseInt(managerIdField.getText());
            Area area = new Area(0, name, managerId);
            int areaId = areaDAO.addArea(area);
            System.out.println("Area added with ID: " + areaId);
            handleCancel(event); // Close the form after adding
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("manageAreas.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
