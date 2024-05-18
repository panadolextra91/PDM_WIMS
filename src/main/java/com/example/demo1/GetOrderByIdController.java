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

public class GetOrderByIdController {
    @FXML
    private TextField orderIdField;

    private OrderDAO orderDAO = new OrderDAO();

    @FXML
    private void handleGetOrderById(ActionEvent event) {
        try {
            int orderId = Integer.parseInt(orderIdField.getText());
            Order order = orderDAO.getOrderById(orderId);
            if (order != null) {
                System.out.println("Order ID: " + order.getId() + ", Customer ID: " + order.getCustomerId() + ", Order Date: " + order.getOrderDate() + ", Total Price: " + order.getTotalPrice());
            } else {
                System.out.println("Order not found.");
            }
            handleCancel(event); // Close the form after displaying
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
