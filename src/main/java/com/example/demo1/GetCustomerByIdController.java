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

public class GetCustomerByIdController {
    @FXML
    private TextField customerIdField;

    private CustomerDAO customerDAO = new CustomerDAO();

    @FXML
    private void handleGetCustomerById(ActionEvent event) {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());
            Customer customer = customerDAO.getCustomerById(customerId);
            if (customer != null) {
                System.out.println("Customer ID: " + customer.getId() + ", Name: " + customer.getName() + ", Email: " + customer.getEmail());
            } else {
                System.out.println("Customer not found.");
            }
            handleCancel(event); // Close the form after displaying
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("manageCustomers.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
