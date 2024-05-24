package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class UpdateProductController {
    @FXML
    private TextField productIdField;
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

    public void setProduct(Product product) {
        productIdField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        quantityField.setText(String.valueOf(product.getQuantity()));
        // Set the selected area in the ComboBox
        areaComboBox.getSelectionModel().select(new Area(product.getAreaId(), "", 0)); // assuming the Area object is uniquely identified by id
    }

    @FXML
    private void handleUpdateProduct(ActionEvent event) {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            Area selectedArea = areaComboBox.getValue();
            Product product = new Product(productId, name, price, quantity, selectedArea != null ? selectedArea.getId() : null);
            productDAO.updateProduct(product);
            System.out.println("Product updated with ID: " + productId);
            handleCancel(event); // Close the form after updating
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        MainController.loadScene("manageProducts.fxml");
    }
}
