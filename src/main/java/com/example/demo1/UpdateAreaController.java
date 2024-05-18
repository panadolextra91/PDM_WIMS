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

public class UpdateAreaController {
    @FXML
    private TextField areaIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField managerIdField;

    private AreaDAO areaDAO = new AreaDAO();
    private Area selectedArea;

    public void setArea(Area area) {
        this.selectedArea = area;
        areaIdField.setText(String.valueOf(area.getId()));
        nameField.setText(area.getName());
        managerIdField.setText(String.valueOf(area.getManagerId()));
    }

    @FXML
    private void handleUpdateArea(ActionEvent event) {
        try {
            int areaId = Integer.parseInt(areaIdField.getText());
            String name = nameField.getText();
            int managerId = Integer.parseInt(managerIdField.getText());
            Area area = new Area(areaId, name, managerId);
            areaDAO.updateArea(area);
            System.out.println("Area updated with ID: " + areaId);
            handleCancel(event); // Close the form after updating
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
