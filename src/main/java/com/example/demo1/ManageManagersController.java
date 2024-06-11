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

public class ManageManagersController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Manager> managerTableView;
    @FXML
    private TableColumn<Manager, Integer> managerIdColumn;
    @FXML
    private TableColumn<Manager, String> managerNameColumn;
    @FXML
    private TableColumn<Manager, String> managerEmailColumn;

    private ManagerDAO managerDAO = new ManagerDAO();
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
        managerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        managerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        loadManagers();
    }

    private void loadManagers() {
        try {
            List<Manager> managers = managerDAO.getAllManagers();
            ObservableList<Manager> managerListItems = FXCollections.observableArrayList(managers);
            managerTableView.setItems(managerListItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();
        try {
            List<Manager> managers = managerDAO.getAllManagers();
            List<Manager> filteredManagers = managers.stream()
                    .filter(manager -> String.valueOf(manager.getId()).contains(searchQuery))
                    .collect(Collectors.toList());
            ObservableList<Manager> filteredListItems = FXCollections.observableArrayList(filteredManagers);
            managerTableView.setItems(filteredListItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addManager(ActionEvent event) throws IOException {
        MainController.loadScene("addManager.fxml");
    }

    @FXML
    private void updateManager(ActionEvent event) {
        Manager selectedManager = managerTableView.getSelectionModel().getSelectedItem();
        if (selectedManager != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateManager.fxml"));
                Parent root = loader.load();

                UpdateManagerController controller = loader.getController();
                controller.setManager(selectedManager);

                MainController.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No manager selected");
        }
    }


    @FXML
    private void deleteManager(ActionEvent event) {
        Manager selectedManager = managerTableView.getSelectionModel().getSelectedItem();
        if (selectedManager != null) {
            try {
                managerDAO.deleteManager(selectedManager.getId());
                loadManagers();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No manager selected");
        }
    }

    @FXML
    private void backToMainMenu(ActionEvent event) throws IOException {
        MainController.loadScene("mainMenu.fxml");
    }
}
