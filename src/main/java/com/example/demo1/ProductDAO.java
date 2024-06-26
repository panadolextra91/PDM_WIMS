package com.example.demo1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public int addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (name, price, quantity, area_id) VALUES (?, ?, ?, ?)"; // Add area_id here
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setInt(4, product.getAreaId()); // Add this line
            preparedStatement.executeUpdate();

            // Retrieve the generated ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating product failed, no ID obtained.");
            }
        }
    }

    public Product getProductById(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("area_id") // Add this line
                );
            }
            return null;
        }
    }

    public Product getProductByName(String name) throws SQLException {
        String query = "SELECT * FROM products WHERE name = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("area_id") // Add this line
                );
            }
            return null;
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("area_id") // Add this line
                ));
            }
        }
        return products;
    }

    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET name = ?, price = ?, quantity = ?, area_id = ? WHERE id = ?"; // Add area_id here
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setInt(4, product.getAreaId()); // Add this line
            statement.setInt(5, product.getId());
            statement.executeUpdate();
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void decreaseProductStock(int productId, int quantity) throws SQLException {
        String query = "UPDATE products SET quantity = quantity - ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            statement.executeUpdate();
        }
    }
}
