package com.example.demo1;

import javafx.event.ActionEvent;

import java.io.IOException;

public class SidebarController {

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
}
