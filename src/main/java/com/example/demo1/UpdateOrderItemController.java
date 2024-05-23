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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UpdateOrderItemController {
    @FXML
    private TextField orderItemIdField;
    @FXML
    private TextField orderIdField;
    @FXML
    private TextField productIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<Product> productNameComboBox;
    private ProductDAO productDAO = new ProductDAO();

    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    public void initialize() {
        loadProductNames();
    }
    private void loadProductNames() {
        try {
            List<Product> products = productDAO.getAllProducts();
            ObservableList<Product> productNames = FXCollections.observableArrayList(products);
            productNameComboBox.setItems(productNames);

            productNameComboBox.setCellFactory(new Callback<ListView<Product>, ListCell<Product>>() {
                @Override
                public ListCell<Product> call(ListView<Product> param) {
                    return new ListCell<Product>() {
                        @Override
                        protected void updateItem(Product item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item == null || empty) {
                                setText(null);
                                setDisable(false);
                            } else {
                                setText(item.getName() + " (Qty: " + item.getQuantity() + ")");
                                setDisable(item.getQuantity() == 0);
                            }
                        }
                    };
                }
            });

            productNameComboBox.setButtonCell(new ListCell<Product>() {
                @Override
                protected void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getName() + " (Qty: " + item.getQuantity() + ")");
                    }
                }
            });

            // Add listener to ComboBox selection change
            productNameComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                if (newValue != null) {
                    productIdField.setText(String.valueOf(newValue.getId()));
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        MainController.loadScene("manageOrderItems.fxml");
    }
}
