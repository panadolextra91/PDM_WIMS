package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ManageAreasController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Area> areaTableView;
    @FXML
    private TableColumn<Area, Integer> areaIdColumn;
    @FXML
    private TableColumn<Area, String> areaNameColumn;
    @FXML
    private TableColumn<Area, Integer> managerIdColumn;

    private AreaDAO areaDAO = new AreaDAO();

    @FXML
    public void initialize() {
        areaIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        areaNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerIdColumn.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        loadAreas();
    }

    private void loadAreas() {
        try {
            List<Area> areas = areaDAO.getAllAreas();
            ObservableList<Area> areaListItems = FXCollections.observableArrayList(areas);
            areaTableView.setItems(areaListItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();
        try {
            List<Area> areas = areaDAO.getAllAreas();
            List<Area> filteredAreas = areas.stream()
                    .filter(area -> String.valueOf(area.getId()).contains(searchQuery))
                    .collect(Collectors.toList());
            ObservableList<Area> filteredListItems = FXCollections.observableArrayList(filteredAreas);
            areaTableView.setItems(filteredListItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addArea(ActionEvent event) throws IOException {
        MainController.loadScene("addArea.fxml");
    }

    @FXML
    private void updateArea(ActionEvent event) {
        Area selectedArea = areaTableView.getSelectionModel().getSelectedItem();
        if (selectedArea != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateArea.fxml"));
                Parent root = loader.load();

                UpdateAreaController updateAreaController = loader.getController();
                updateAreaController.setArea(selectedArea);

                MainController.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void deleteArea(ActionEvent event) {
        Area selectedArea = areaTableView.getSelectionModel().getSelectedItem();
        if (selectedArea != null) {
            try {
                areaDAO.deleteArea(selectedArea.getId());
                loadAreas();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void backToMainMenu(ActionEvent event) throws IOException {
        MainController.loadScene("mainMenu.fxml");
    }
}
