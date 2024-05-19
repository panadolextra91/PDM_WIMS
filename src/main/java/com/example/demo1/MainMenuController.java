package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    private void manageOrders(ActionEvent event) {
        MainController.loadScene("manageOrders.fxml");
    }

    @FXML
    private void addOrder(ActionEvent event) {
        MainController.loadScene("addOrder.fxml");
    }

    @FXML
    private void updateOrder(ActionEvent event) {
        MainController.loadScene("updateOrder.fxml");
    }

    @FXML
    private void manageOrderItems(ActionEvent event) {
        MainController.loadScene("manageOrderItems.fxml");
    }

    @FXML
    private void addOrderItem(ActionEvent event) {
        MainController.loadScene("addOrderItem.fxml");
    }

    @FXML
    private void updateOrderItem(ActionEvent event) {
        MainController.loadScene("updateOrderItem.fxml");
    }

    @FXML
    private void manageCustomers(ActionEvent event) {
        MainController.loadScene("manageCustomers.fxml");
    }

    @FXML
    private void addCustomer(ActionEvent event) {
        MainController.loadScene("addCustomer.fxml");
    }

    @FXML
    private void updateCustomer(ActionEvent event) {
        MainController.loadScene("updateCustomer.fxml");
    }

    @FXML
    private void manageProducts(ActionEvent event) {
        MainController.loadScene("manageProducts.fxml");
    }

    @FXML
    private void addProduct(ActionEvent event) {
        MainController.loadScene("addProduct.fxml");
    }

    @FXML
    private void updateProduct(ActionEvent event) {
        MainController.loadScene("updateProduct.fxml");
    }

    @FXML
    private void manageManagers(ActionEvent event) {
        MainController.loadScene("manageManagers.fxml");
    }

    @FXML
    private void addManager(ActionEvent event) {
        MainController.loadScene("addManager.fxml");
    }

    @FXML
    private void updateManager(ActionEvent event) {
        MainController.loadScene("updateManager.fxml");
    }

    @FXML
    private void manageAreas(ActionEvent event) {
        MainController.loadScene("manageAreas.fxml");
    }

    @FXML
    private void addArea(ActionEvent event) {
        MainController.loadScene("addArea.fxml");
    }

    @FXML
    private void updateArea(ActionEvent event) {
        MainController.loadScene("updateArea.fxml");
    }

    @FXML
    private void showAnalytics(ActionEvent event) {
        MainController.loadScene("analytics.fxml");
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        System.exit(0);
    }
}
