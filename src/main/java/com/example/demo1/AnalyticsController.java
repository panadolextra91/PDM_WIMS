package com.example.demo1;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsController {

    @FXML
    private BarChart<String, Number> salesChart;

    @FXML
    private TableView<Product> lowStockTable;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> currentStockColumn;

    @FXML
    private PieChart revenueChart;

    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();
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
    public void initialize() {
        productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        currentStockColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        // Load and populate the sales chart
        loadSalesData();

        // Load and populate the low stock table
        loadLowStockData();

        // Load and populate the revenue chart
        loadRevenueData();
    }

    private void loadSalesData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales");

        try {
            List<OrderItem> orderItems = orderItemDAO.getAllOrderItems();
            Map<String, Integer> productSales = new HashMap<>();

            for (OrderItem orderItem : orderItems) {
                String productName = productDAO.getProductById(orderItem.getProductId()).getName();
                productSales.put(productName, productSales.getOrDefault(productName, 0) + orderItem.getQuantity());
            }

            for (Map.Entry<String, Integer> entry : productSales.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the y-axis to integer
        NumberAxis yAxis = (NumberAxis) salesChart.getYAxis();
        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);
        yAxis.setAutoRanging(false);
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return String.format("%d", object.intValue());
            }
        });

        // Set the lower and upper bound of y-axis based on max value of the sales
        int maxSales = series.getData().stream().mapToInt(data -> data.getYValue().intValue()).max().orElse(10);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(maxSales + 1);

        salesChart.getData().add(series);
    }

    private void loadLowStockData() {
        ObservableList<Product> data = FXCollections.observableArrayList();

        try {
            List<Product> products = productDAO.getAllProducts();
            for (Product product : products) {
                if (product.getQuantity() < 10) { // Assuming low stock is less than 10
                    data.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lowStockTable.setItems(data);
    }

    private void loadRevenueData() {
        try {
            List<Order> orders = orderDAO.getAllOrders();
            Map<String, Double> productRevenue = new HashMap<>();

            for (Order order : orders) {
                List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(order.getId());
                for (OrderItem orderItem : orderItems) {
                    String productName = productDAO.getProductById(orderItem.getProductId()).getName();
                    double revenue = orderItem.getQuantity() * productDAO.getProductById(orderItem.getProductId()).getPrice();
                    productRevenue.put(productName, productRevenue.getOrDefault(productName, 0.0) + revenue);
                }
            }

            for (Map.Entry<String, Double> entry : productRevenue.entrySet()) {
                PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
                revenueChart.getData().add(slice);

                // Create the tooltip
                Tooltip tooltip = new Tooltip("Revenue: $" + String.format("%.2f", entry.getValue()));

                // Set the tooltip to the node
                slice.getNode().setOnMouseEntered(event -> {
                    Tooltip.install(slice.getNode(), tooltip);
                });

                // Ensure the node picks up the mouse events
                slice.getNode().setPickOnBounds(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}