package com.example.demo1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public int addOrder(Order order) throws SQLException {
        String query = "INSERT INTO orders (customer_id, order_date, total_price) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getCustomerId());
            statement.setDate(2, order.getOrderDate());
            statement.setDouble(3, order.getTotalPrice());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }

    public Order getOrderById(int id) throws SQLException {
        String query = "SELECT * FROM orders WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getDate("order_date"),
                        resultSet.getDouble("total_price")
                );
            }
            return null;
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                orders.add(new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getDate("order_date"),
                        resultSet.getDouble("total_price")
                ));
            }
        }
        return orders;
    }

    public void updateOrder(Order order) throws SQLException {
        String query = "UPDATE orders SET customer_id = ?, order_date = ?, total_price = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getCustomerId());
            statement.setDate(2, order.getOrderDate());
            statement.setDouble(3, order.getTotalPrice());
            statement.setInt(4, order.getId());
            statement.executeUpdate();
        }
    }

    public void deleteOrder(int id) throws SQLException {
        String query = "DELETE FROM orders WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
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
}
