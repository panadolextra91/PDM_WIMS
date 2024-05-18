package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetAllOrderItemsController {
    @FXML
    private ListView<String> orderItemListView;

    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    @FXML
    public void initialize() {
        loadOrderItems();
    }

    private void loadOrderItems() {
        try {
            List<OrderItem> orderItems = orderItemDAO.getAllOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItemListView.getItems().add("ID: " + orderItem.getId() + ", Order ID: " + orderItem.getOrderId() + ", Product ID: " + orderItem.getProductId() + ", Quantity: " + orderItem.getQuantity());
            }
        } catch (SQLException e) {
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
