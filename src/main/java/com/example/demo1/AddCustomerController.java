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

public class AddCustomerController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    private CustomerDAO customerDAO = new CustomerDAO();

    @FXML
    private void handleAddCustomer(ActionEvent event) {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            Customer customer = new Customer(0, name, email);
            int customerId = customerDAO.addCustomer(customer);
            System.out.println("Customer added with ID: " + customerId);
            handleCancel(event); // Close the form after adding
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        MainController.loadScene("manageCustomers.fxml");
    }
}
