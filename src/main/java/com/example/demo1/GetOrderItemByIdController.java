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

public class GetOrderItemByIdController {
    @FXML
    private TextField orderItemIdField;

    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    @FXML
    private void handleGetOrderItemById(ActionEvent event) {
        try {
            int orderItemId = Integer.parseInt(orderItemIdField.getText());
            OrderItem orderItem = orderItemDAO.getOrderItemById(orderItemId);
            if (orderItem != null) {
                System.out.println("Order Item ID: " + orderItem.getId() + ", Order ID: " + orderItem.getOrderId() + ", Product ID: " + orderItem.getProductId() + ", Quantity: " + orderItem.getQuantity());
            } else {
                System.out.println("Order item not found.");
            }
            handleCancel(event); // Close the form after displaying
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("manageOrderItems.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
