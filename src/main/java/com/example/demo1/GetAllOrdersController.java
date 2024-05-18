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

public class GetAllOrdersController {
    @FXML
    private ListView<String> orderListView;

    private OrderDAO orderDAO = new OrderDAO();

    @FXML
    public void initialize() {
        loadOrders();
    }

    private void loadOrders() {
        try {
            List<Order> orders = orderDAO.getAllOrders();
            for (Order order : orders) {
                orderListView.getItems().add("ID: " + order.getId() + ", Customer ID: " + order.getCustomerId() + ", Order Date: " + order.getOrderDate() + ", Total Price: " + order.getTotalPrice());
            }
        } catch (SQLException e) {
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
