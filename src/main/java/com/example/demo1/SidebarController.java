package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SidebarController {

    private void navigateTo(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void manageOrders(ActionEvent event) throws IOException {
        navigateTo(event, "manageOrders.fxml");
    }

    public void manageOrderItems(ActionEvent event) throws IOException {
        navigateTo(event, "manageOrderItems.fxml");
    }

    public void manageCustomers(ActionEvent event) throws IOException {
        navigateTo(event, "manageCustomers.fxml");
    }

    public void manageProducts(ActionEvent event) throws IOException {
        navigateTo(event, "manageProducts.fxml");
    }

    public void manageManagers(ActionEvent event) throws IOException {
        navigateTo(event, "manageManagers.fxml");
    }

    public void manageAreas(ActionEvent event) throws IOException {
        navigateTo(event, "manageAreas.fxml");
    }
}
