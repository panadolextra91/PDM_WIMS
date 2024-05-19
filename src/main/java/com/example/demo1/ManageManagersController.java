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
