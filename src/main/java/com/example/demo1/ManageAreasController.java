package com.example.demo1;

import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    public void manageOrders(ActionEvent event) throws IOException {
        MainController.loadScene("manageOrders.fxml");
    }

    public void manageOrderItems(ActionEvent event) throws IOException {
        MainController.loadScene("manageOrderItems.fxml");
    }

    public void manageCustomers(ActionEvent event) throws IOException {
        MainController.loadScene("manageCustomers.fxml");
    }

    public void manageProducts(ActionEvent event) throws IOException {
        MainController.loadScene("manageProducts.fxml");
    }

    public void manageManagers(ActionEvent event) throws IOException {
        MainController.loadScene("manageManagers.fxml");
    }

    public void manageAreas(ActionEvent event) throws IOException {
        MainController.loadScene("manageAreas.fxml");
    }
    public void showAnalytics(ActionEvent event) throws IOException {
        MainController.loadScene("analytics.fxml");
    }

    @FXML
    private VBox sidebar;
    private boolean sidebarVisible = false;
    @FXML
    private AnchorPane contentPane;

    @FXML
    private void toggleSidebar(ActionEvent event) {
        if (sidebarVisible) {
            hideSidebar();
        } else {
            showSidebar();
        }
        sidebarVisible = !sidebarVisible;
    }

    private void showSidebar() {
        sidebar.setVisible(true);
        TranslateTransition sidebarTransition = new TranslateTransition(Duration.millis(300), sidebar);
        sidebarTransition.setFromX(-200);
        sidebarTransition.setToX(0);

        TranslateTransition contentTransition = new TranslateTransition(Duration.millis(300), contentPane);
        contentTransition.setFromX(0);
        contentTransition.setToX(200);

        sidebarTransition.play();
        contentTransition.play();
    }

    private void hideSidebar() {
        TranslateTransition sidebarTransition = new TranslateTransition(Duration.millis(300), sidebar);
        sidebarTransition.setFromX(0);
        sidebarTransition.setToX(-200);
        sidebarTransition.setOnFinished(e -> sidebar.setVisible(false));

        TranslateTransition contentTransition = new TranslateTransition(Duration.millis(300), contentPane);
        contentTransition.setFromX(200);
        contentTransition.setToX(0);

        sidebarTransition.play();
        contentTransition.play();
    }
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
