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
        navigateTo(event, "addCustomer.fxml");
    }

    @FXML
    private void updateCustomer(ActionEvent event) throws IOException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateCustomer.fxml"));
            Parent root = loader.load();

            UpdateCustomerController updateCustomerController = loader.getController();
            updateCustomerController.setCustomer(selectedCustomer);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
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
    private void backToMainMenu(ActionEvent event) throws IOException {
        navigateTo(event, "mainMenu.fxml");
    }

    private void navigateTo(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
