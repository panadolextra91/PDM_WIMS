package com.example.demo1;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
    private TableColumn<Product, Integer> areaIdColumn; // New column for area_id
    @FXML
    private TextField searchField;

    private ProductDAO productDAO = new ProductDAO();

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

    public void showAnalytics(ActionEvent event) throws IOException {
        MainController.loadScene("analytics.fxml");
    }
    @FXML
    private VBox sidebar;
    private boolean sidebarVisible = false;
    @FXML
    private AnchorPane contentPane;

    @FXML
    private void toggleSidebar(ActionEvent event) {
        if (sidebarVisible) {
            hideSidebar();
        } else {
            showSidebar();
        }
        sidebarVisible = !sidebarVisible;
    }

    private void showSidebar() {
        sidebar.setVisible(true);
        TranslateTransition sidebarTransition = new TranslateTransition(Duration.millis(300), sidebar);
        sidebarTransition.setFromX(-200);
        sidebarTransition.setToX(0);

        TranslateTransition contentTransition = new TranslateTransition(Duration.millis(300), contentPane);
        contentTransition.setFromX(0);
        contentTransition.setToX(200);

        sidebarTransition.play();
        contentTransition.play();
    }

    private void hideSidebar() {
        TranslateTransition sidebarTransition = new TranslateTransition(Duration.millis(300), sidebar);
        sidebarTransition.setFromX(0);
        sidebarTransition.setToX(-200);
        sidebarTransition.setOnFinished(e -> sidebar.setVisible(false));

        TranslateTransition contentTransition = new TranslateTransition(Duration.millis(300), contentPane);
        contentTransition.setFromX(200);
        contentTransition.setToX(0);

        sidebarTransition.play();
        contentTransition.play();
    }
    @FXML
    public void initialize() {
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        areaIdColumn.setCellValueFactory(new PropertyValueFactory<>("areaId")); // Set cell value factory for area_id
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
        MainController.loadScene("addProduct.fxml");
    }

    @FXML
    private void updateProduct(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateProduct.fxml"));
                Parent root = loader.load();

                UpdateProductController updateProductController = loader.getController();
                updateProductController.setProduct(selectedProduct);

                MainController.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        MainController.loadScene("mainMenu.fxml");
    }
}
