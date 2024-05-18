package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetAllAreasController {
    @FXML
    private ListView<String> areaListView;

    private AreaDAO areaDAO = new AreaDAO();

    @FXML
    public void initialize() {
        loadAreas();
    }

    private void loadAreas() {
        try {
            List<Area> areas = areaDAO.getAllAreas();
            for (Area area : areas) {
                areaListView.getItems().add("ID: " + area.getId() + ", Name: " + area.getName() + ", Manager ID: " + area.getManagerId());
            }
        } catch (SQLException e) {
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
