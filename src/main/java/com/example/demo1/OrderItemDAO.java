package com.example.demo1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    public int addOrderItem(OrderItem orderItem) throws SQLException {
        String query = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getProductId());
            statement.setInt(3, orderItem.getQuantity());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    updateOrderTotalPrice(orderItem.getOrderId());
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order item failed, no ID obtained.");
                }
            }
        }
    }

    public OrderItem getOrderItemById(int id) throws SQLException {
        String query = "SELECT * FROM order_items WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new OrderItem(
                        resultSet.getInt("id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity")
                );
            }
            return null;
        }
    }

    public List<OrderItem> getAllOrderItems() throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM order_items";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                orderItems.add(new OrderItem(
                        resultSet.getInt("id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity")
                ));
            }
        }
        return orderItems;
    }

    public void updateOrderItem(OrderItem orderItem) throws SQLException {
        String query = "UPDATE order_items SET order_id = ?, product_id = ?, quantity = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getProductId());
            statement.setInt(3, orderItem.getQuantity());
            statement.setInt(4, orderItem.getId());
            statement.executeUpdate();
            updateOrderTotalPrice(orderItem.getOrderId());
        }
    }

    public void deleteOrderItem(int id) throws SQLException {
        int orderId = getOrderItemById(id).getOrderId();
        String query = "DELETE FROM order_items WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            updateOrderTotalPrice(orderId);
        }
    }

    public void updateOrderTotalPrice(int orderId) throws SQLException {
        String query = "UPDATE orders SET total_price = (SELECT SUM(o.quantity * p.price) FROM order_items o JOIN products p ON o.product_id = p.id WHERE o.order_id = ?) WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        }
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderItems.add(new OrderItem(
                        resultSet.getInt("id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity")
                ));
            }
        }
        return orderItems;
    }
}
