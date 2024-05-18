package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private void manageCustomers(ActionEvent event) throws IOException {
        navigateTo(event, "manageCustomers.fxml");
    }

    @FXML
    private void manageOrders(ActionEvent event) throws IOException {
        navigateTo(event, "manageOrders.fxml");
    }

    @FXML
    private void manageOrderItems(ActionEvent event) throws IOException {
        navigateTo(event, "manageOrderItems.fxml");
    }

    @FXML
    private void manageProducts(ActionEvent event) throws IOException {
        navigateTo(event, "manageProducts.fxml");
    }

    @FXML
    private void manageManagers(ActionEvent event) throws IOException {
        navigateTo(event, "manageManagers.fxml");
    }

    @FXML
    private void manageAreas(ActionEvent event) throws IOException {
        navigateTo(event, "manageAreas.fxml");
    }
    @FXML
    private void showAnalytics(ActionEvent event) throws IOException {
        navigateTo(event, "analytics.fxml");
    }
    @FXML
    private void exitApplication(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void navigateTo(ActionEvent event, String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
