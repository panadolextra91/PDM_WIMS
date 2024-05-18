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

public class GetProductByIdController {
    @FXML
    private TextField productIdField;

    private ProductDAO productDAO = new ProductDAO();

    @FXML
    private void handleGetProductById(ActionEvent event) {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            Product product = productDAO.getProductById(productId);
            if (product != null) {
                System.out.println("Product ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity());
            } else {
                System.out.println("Product not found.");
            }
            handleCancel(event); // Close the form after displaying
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("manageProducts.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
