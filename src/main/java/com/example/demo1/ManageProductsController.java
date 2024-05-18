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

public class ManageProductsController {

    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TextField searchField;

    private ProductDAO productDAO = new ProductDAO();

    @FXML
    public void initialize() {
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        loadProducts();
    }

    private void loadProducts() {
        try {
            List<Product> products = productDAO.getAllProducts();
            productTableView.getItems().setAll(products);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();
        try {
            List<Product> products = productDAO.getAllProducts();
            List<Product> filteredProducts = products.stream()
                    .filter(product -> String.valueOf(product.getId()).contains(searchQuery) ||
                            product.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                    .collect(Collectors.toList());
            productTableView.getItems().setAll(filteredProducts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addProduct(ActionEvent event) throws IOException {
        navigateTo(event, "addProduct.fxml");
    }

    @FXML
    private void updateProduct(ActionEvent event) throws IOException {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateProduct.fxml"));
            Parent root = loader.load();

            UpdateProductController updateProductController = loader.getController();
            updateProductController.setProduct(selectedProduct);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                productDAO.deleteProduct(selectedProduct.getId());
                loadProducts();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void backToMainMenu(ActionEvent event) throws IOException {
        navigateTo(event, "mainMenu.fxml");
    }

    private void navigateTo(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
