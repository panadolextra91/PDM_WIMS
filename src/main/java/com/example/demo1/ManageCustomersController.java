package com.example.demo1;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ManageCustomersController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerEmailColumn;

    private CustomerDAO customerDAO = new CustomerDAO();
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
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        loadCustomers();
    }

    private void loadCustomers() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            ObservableList<Customer> customerListItems = FXCollections.observableArrayList(customers);
            customerTableView.setItems(customerListItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            List<Customer> filteredCustomers = customers.stream()
                    .filter(customer -> String.valueOf(customer.getId()).contains(searchQuery))
                    .collect(Collectors.toList());
            ObservableList<Customer> filteredListItems = FXCollections.observableArrayList(filteredCustomers);
            customerTableView.setItems(filteredListItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addCustomer(ActionEvent event) throws IOException {
        MainController.loadScene("addCustomer.fxml");
    }

    @FXML
    private void updateCustomer(ActionEvent event) {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateCustomer.fxml"));
                Parent root = loader.load();

                UpdateCustomerController updateCustomerController = loader.getController();
                updateCustomerController.setCustomer(selectedCustomer);

                MainController.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteCustomer(ActionEvent event) {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            try {
                customerDAO.deleteCustomer(selectedCustomer.getId());
                loadCustomers();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addOrderForCustomer(ActionEvent event) {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addOrder.fxml"));
                Parent root = loader.load();

                AddOrderController addOrderController = loader.getController();
                addOrderController.setCustomerId(selectedCustomer.getId());

                Scene scene = new Scene(root);
                MainController.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void backToMainMenu(ActionEvent event) throws IOException {
        MainController.loadScene("mainMenu.fxml");
    }
}
