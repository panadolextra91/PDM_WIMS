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

public class GetAreaByIdController {
    @FXML
    private TextField areaIdField;

    private AreaDAO areaDAO = new AreaDAO();

    @FXML
    private void handleGetAreaById(ActionEvent event) {
        try {
            int areaId = Integer.parseInt(areaIdField.getText());
            Area area = areaDAO.getAreaById(areaId);
            if (area != null) {
                System.out.println("Area ID: " + area.getId() + ", Name: " + area.getName() + ", Manager ID: " + area.getManagerId());
            } else {
                System.out.println("Area not found.");
            }
            handleCancel(event); // Close the form after displaying
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
