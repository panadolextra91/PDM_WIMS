package com.example.demo1;

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
    private TableView<ProductStock> lowStockTable;

    @FXML
    private TableColumn<ProductStock, String> productNameColumn;

    @FXML
    private TableColumn<ProductStock, Integer> currentStockColumn;

    @FXML
    private PieChart revenueChart;

    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private ProductDAO productDAO = new ProductDAO();

    @FXML
    public void initialize() {
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

        salesChart.getData().add(series);
    }

    private void loadLowStockData() {
        ObservableList<ProductStock> data = FXCollections.observableArrayList();

        try {
            List<Product> products = productDAO.getAllProducts();
            for (Product product : products) {
                if (product.getQuantity() < 10) { // Assuming low stock is less than 10
                    data.add(new ProductStock(product.getName(), product.getQuantity()));
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

    @FXML
    private void backToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static class ProductStock {
        private final String name;
        private final int quantity;

        public ProductStock(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
