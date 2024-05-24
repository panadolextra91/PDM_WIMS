package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class AddProductController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private ComboBox<Area> areaComboBox;

    private ProductDAO productDAO = new ProductDAO();
    private AreaDAO areaDAO = new AreaDAO();

    @FXML
    public void initialize() {
        loadAreas();
    }

    private void loadAreas() {
        try {
            List<Area> areas = areaDAO.getAllAreas();
            areaComboBox.getItems().setAll(areas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProduct(ActionEvent event) {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            Area selectedArea = areaComboBox.getValue();
            Product product = new Product(0, name, price, quantity, selectedArea != null ? selectedArea.getId() : null);
            int productId = productDAO.addProduct(product);
            System.out.println("Product added with ID: " + productId);
            handleCancel(event); // Close the form after adding
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        MainController.loadScene("manageProducts.fxml");
    }
}
