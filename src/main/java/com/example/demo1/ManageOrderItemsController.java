package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ManageOrderItemsController {

    @FXML
    private TableView<OrderItem> orderItemTableView;
    @FXML
    private TableColumn<OrderItem, Integer> orderItemIdColumn;
    @FXML
    private TableColumn<OrderItem, Integer> orderIdColumn;
    @FXML
    private TableColumn<OrderItem, Integer> productIdColumn;
    @FXML
    private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML
    private TextField searchField;

    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    public void manageOrders(ActionEvent event) throws IOException {
        MainController.loadScene("manageOrders.fxml");
    }

    public void manageOrderItems(ActionEvent event) throws IOException {
        MainController.loadScene("manageOrderItems.fxml");
    }

    public void manageCustomers(ActionEvent event) throws IOException {
        MainController.loadScene("manageCustomers.fxml");
    }

    public void manageProducts(ActionEvent event) throws IOException {
        MainController.loadScene("manageProducts.fxml");
    }

    public void manageManagers(ActionEvent event) throws IOException {
        MainController.loadScene("manageManagers.fxml");
    }

    public void manageAreas(ActionEvent event) throws IOException {
        MainController.loadScene("manageAreas.fxml");
    }

    @FXML
    public void initialize() {
        orderItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        loadOrderItems();
    }

    private void loadOrderItems() {
        try {
            List<OrderItem> orderItems = orderItemDAO.getAllOrderItems();
            orderItemTableView.getItems().setAll(orderItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();
        try {
            List<OrderItem> orderItems = orderItemDAO.getAllOrderItems();
            List<OrderItem> filteredOrderItems = orderItems.stream()
                    .filter(orderItem -> String.valueOf(orderItem.getId()).contains(searchQuery))
                    .collect(Collectors.toList());
            orderItemTableView.getItems().setAll(filteredOrderItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addOrderItem(ActionEvent event) throws IOException {
        MainController.loadScene("addOrderItem.fxml");
    }

    @FXML
    private void updateOrderItem(ActionEvent event) {
        OrderItem selectedOrderItem = orderItemTableView.getSelectionModel().getSelectedItem();
        if (selectedOrderItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateOrderItem.fxml"));
                Parent root = loader.load();

                UpdateOrderItemController updateOrderItemController = loader.getController();
                updateOrderItemController.setOrderItem(selectedOrderItem);

                MainController.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteOrderItem(ActionEvent event) {
        OrderItem selectedOrderItem = orderItemTableView.getSelectionModel().getSelectedItem();
        if (selectedOrderItem != null) {
            try {
                orderItemDAO.deleteOrderItem(selectedOrderItem.getId());
                loadOrderItems();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void backToMainMenu(ActionEvent event) throws IOException {
        MainController.loadScene("mainMenu.fxml");
    }
}
