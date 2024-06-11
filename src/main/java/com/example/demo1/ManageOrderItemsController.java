package com.example.demo1;

import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ManageOrderItemsController {

    @FXML
    private TableView<OrderItem> orderItemTableView;
    @FXML
    private TableColumn<OrderItem, Integer> orderItemIdColumn;
    @FXML
    private TableColumn<OrderItem, Integer> orderIdColumn;
    @FXML
    private TableColumn<OrderItem, Integer> productIdColumn;
    @FXML
    private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML
    private TextField searchField;

    private OrderItemDAO orderItemDAO = new OrderItemDAO();
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
        orderItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        loadOrderItems();
    }

    private void loadOrderItems() {
        try {
            List<OrderItem> orderItems = orderItemDAO.getAllOrderItems();
            orderItemTableView.getItems().setAll(orderItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();
        try {
            List<OrderItem> orderItems = orderItemDAO.getAllOrderItems();
            List<OrderItem> filteredOrderItems = orderItems.stream()
                    .filter(orderItem -> String.valueOf(orderItem.getId()).contains(searchQuery))
                    .collect(Collectors.toList());
            orderItemTableView.getItems().setAll(filteredOrderItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addOrderItem(ActionEvent event) throws IOException {
        MainController.loadScene("addOrderItem.fxml");
    }

    @FXML
    private void updateOrderItem(ActionEvent event) {
        OrderItem selectedOrderItem = orderItemTableView.getSelectionModel().getSelectedItem();
        if (selectedOrderItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateOrderItem.fxml"));
                Parent root = loader.load();

                UpdateOrderItemController updateOrderItemController = loader.getController();
                updateOrderItemController.setOrderItem(selectedOrderItem);

                MainController.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteOrderItem(ActionEvent event) {
        OrderItem selectedOrderItem = orderItemTableView.getSelectionModel().getSelectedItem();
        if (selectedOrderItem != null) {
            try {
                orderItemDAO.deleteOrderItem(selectedOrderItem.getId());
                loadOrderItems();
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
