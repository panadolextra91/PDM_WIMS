package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ManageOrdersController {

    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableColumn<Order, Integer> orderIdColumn;
    @FXML
    private TableColumn<Order, Integer> customerIdColumn;
    @FXML
    private TableColumn<Order, Date> orderDateColumn;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;
    @FXML
    private TextField searchField;

    private OrderDAO orderDAO = new OrderDAO();

    @FXML
    public void initialize() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        loadOrders();
    }

    private void loadOrders() {
        try {
            List<Order> orders = orderDAO.getAllOrders();
            orderTableView.getItems().setAll(orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        try {
            List<Order> orders = orderDAO.getAllOrders();
            List<Order> filteredOrders = orders.stream()
                    .filter(order -> String.valueOf(order.getId()).contains(searchText))
                    .collect(Collectors.toList());
            orderTableView.getItems().setAll(filteredOrders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addOrder(ActionEvent event) {
        MainController.loadScene("addOrder.fxml");
    }

    @FXML
    private void updateOrder(ActionEvent event) {
        Order selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateOrder.fxml"));
                Parent root = loader.load();

                UpdateOrderController updateOrderController = loader.getController();
                updateOrderController.setOrder(selectedOrder);

                MainController.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteOrder(ActionEvent event) {
        Order selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                orderDAO.deleteOrder(selectedOrder.getId());
                loadOrders();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addOrderItem(ActionEvent event) {
        Order selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addOrderItem.fxml"));
                Parent root = loader.load();

                AddOrderItemController addOrderItemController = loader.getController();
                addOrderItemController.setOrderId(selectedOrder.getId());

                MainController.setScene(new Scene(root));
                loadOrders(); // Reload orders to update the total price
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void backToMainMenu(ActionEvent event) {
        MainController.loadScene("mainMenu.fxml");
    }
}
