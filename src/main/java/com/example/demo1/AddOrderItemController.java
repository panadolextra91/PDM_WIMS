package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddOrderItemController {

    @FXML
    private TextField orderIdField;
    @FXML
    private TextField productIdField;
    @FXML
    private TextField quantityField;

    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private int orderId;
    @FXML
    private ComboBox<String> productNameComboBox;
    private OrderDAO orderDAO = new OrderDAO();

    private ProductDAO productDAO = new ProductDAO();

    public void setOrderId(int orderId) {
        this.orderId = orderId;
        this.orderIdField.setText(String.valueOf(orderId));
    }

    @FXML
    public void initialize() {
        loadProductNames();
    }

    private void loadProductNames() {
        try {
            List<Product> products = productDAO.getAllProducts();
            ObservableList<String> productNames = FXCollections.observableArrayList();
            for (Product product : products) {
                productNames.add(product.getName());
            }
            productNameComboBox.setItems(productNames);

            // Add listener to ComboBox selection change
            productNameComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                if (newValue != null) {
                    try {
                        Product selectedProduct = productDAO.getProductByName(newValue);
                        productIdField.setText(String.valueOf(selectedProduct.getId()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProductSelection() {
        String selectedProductName = productNameComboBox.getSelectionModel().getSelectedItem();
        if (selectedProductName != null) {
            try {
                Product selectedProduct = productDAO.getProductByName(selectedProductName);
                if (selectedProduct != null) {
                    productIdField.setText(String.valueOf(selectedProduct.getId()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleAddOrderItem(ActionEvent event) {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            // Check if orderId is provided in the orderIdField
            int orderIdToUse = orderId;
            if (!orderIdField.getText().isEmpty()) {
                orderIdToUse = Integer.parseInt(orderIdField.getText());
            }

            // Check if orderId exists in orders table
            if (orderDAO.getOrderById(orderIdToUse) == null) {
                throw new SQLException("Order ID " + orderIdToUse + " does not exist in orders table.");
            }

            OrderItem orderItem = new OrderItem(0, orderIdToUse, productId, quantity);
            orderItemDAO.addOrderItem(orderItem);
            orderItemDAO.updateOrderTotalPrice(orderIdToUse); // Update the total price of the order

            // Decrease the stock of the product
            productDAO.decreaseProductStock(productId, quantity);

            System.out.println("Order item added to Order ID: " + orderIdToUse);
            handleCancel(event);
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleCancel(ActionEvent event) {
        MainController.loadScene("manageOrderItems.fxml");
    }
}
