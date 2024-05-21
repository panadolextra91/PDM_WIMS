package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class UpdateCustomerController {
    @FXML
    private TextField customerIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    private CustomerDAO customerDAO = new CustomerDAO();

    public void setCustomer(Customer customer) {
        customerIdField.setText(String.valueOf(customer.getId()));
        nameField.setText(customer.getName());
        emailField.setText(customer.getEmail());
    }

    @FXML
    private void handleUpdateCustomer(ActionEvent event) {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());
            String name = nameField.getText();
            String email = emailField.getText();
            Customer customer = new Customer(customerId, name, email);
            customerDAO.updateCustomer(customer);
            System.out.println("Customer updated with ID: " + customerId);
            handleCancel(event); // Close the form after updating
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        MainController.loadScene("manageCustomers.fxml");
    }
}
