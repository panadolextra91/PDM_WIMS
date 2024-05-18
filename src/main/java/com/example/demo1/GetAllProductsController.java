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

public class GetAllProductsController {
    @FXML
    private ListView<String> productListView;

    private ProductDAO productDAO = new ProductDAO();

    @FXML
    public void initialize() {
        loadProducts();
    }

    private void loadProducts() {
        try {
            List<Product> products = productDAO.getAllProducts();
            for (Product product : products) {
                productListView.getItems().add("ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity());
            }
        } catch (SQLException e) {
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
