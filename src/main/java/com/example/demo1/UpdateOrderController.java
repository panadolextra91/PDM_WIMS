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
import java.sql.Date;
import java.sql.SQLException;

public class UpdateOrderController {
    @FXML
    private TextField orderIdField;
    @FXML
    private TextField customerIdField;
    @FXML
    private TextField orderDateField;

    private OrderDAO orderDAO = new OrderDAO();

    public void setOrder(Order order) {
        orderIdField.setText(String.valueOf(order.getId()));
        customerIdField.setText(String.valueOf(order.getCustomerId()));
        orderDateField.setText(order.getOrderDate().toString());
    }

    @FXML
    private void handleUpdateOrder(ActionEvent event) {
        try {
            int orderId = Integer.parseInt(orderIdField.getText());
            int customerId = Integer.parseInt(customerIdField.getText());
            Date orderDate = Date.valueOf(orderDateField.getText());
            Order order = new Order(orderId, customerId, orderDate, 0.0); // Total price will be calculated in the backend
            orderDAO.updateOrder(order);
            System.out.println("Order updated with ID: " + orderId);
            handleCancel(event); // Close the form after updating
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("manageOrders.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
