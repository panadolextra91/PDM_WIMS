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

public class DeleteOrderItemController {
    @FXML
    private TextField orderItemIdField;

    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    @FXML
    private void handleDeleteOrderItem(ActionEvent event) {
        try {
            int orderItemId = Integer.parseInt(orderItemIdField.getText());
            orderItemDAO.deleteOrderItem(orderItemId);
            System.out.println("Order item deleted with ID: " + orderItemId);
            handleCancel(event); // Close the form after deleting
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
