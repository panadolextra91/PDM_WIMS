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

public class AddOrderItemController {

    @FXML
    private TextField orderIdField;
    @FXML
    private TextField productIdField;
    @FXML
    private TextField quantityField;

    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private int orderId;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
        this.orderIdField.setText(String.valueOf(orderId));
    }

    @FXML
    private void handleAddOrderItem(ActionEvent event) {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            OrderItem orderItem = new OrderItem(0, orderId, productId, quantity);
            orderItemDAO.addOrderItem(orderItem);
            orderItemDAO.updateOrderTotalPrice(orderId); // Update the total price of the order
            System.out.println("Order item added to Order ID: " + orderId);
            handleCancel(event);
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
