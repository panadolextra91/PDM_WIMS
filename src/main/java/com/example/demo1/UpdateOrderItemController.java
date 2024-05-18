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

public class UpdateOrderItemController {
    @FXML
    private TextField orderItemIdField;
    @FXML
    private TextField orderIdField;
    @FXML
    private TextField productIdField;
    @FXML
    private TextField quantityField;

    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    public void setOrderItem(OrderItem orderItem) {
        orderItemIdField.setText(String.valueOf(orderItem.getId()));
        orderIdField.setText(String.valueOf(orderItem.getOrderId()));
        productIdField.setText(String.valueOf(orderItem.getProductId()));
        quantityField.setText(String.valueOf(orderItem.getQuantity()));
    }

    @FXML
    private void handleUpdateOrderItem(ActionEvent event) {
        try {
            int orderItemId = Integer.parseInt(orderItemIdField.getText());
            int orderId = Integer.parseInt(orderIdField.getText());
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            OrderItem orderItem = new OrderItem(orderItemId, orderId, productId, quantity);
            orderItemDAO.updateOrderItem(orderItem);
            System.out.println("Order item updated with ID: " + orderItemId);
            handleCancel(event); // Close the form after updating
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
