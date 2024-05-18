package com.example.demo1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {
    public int addManager(Manager manager) throws SQLException {
        String query = "INSERT INTO managers (name, email) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, manager.getName());
            statement.setString(2, manager.getEmail());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating manager failed, no ID obtained.");
                }
            }
        }
    }

    public Manager getManagerById(int id) throws SQLException {
        String query = "SELECT * FROM managers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Manager(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
            }
            return null;
        }
    }

    public List<Manager> getAllManagers() throws SQLException {
        List<Manager> managers = new ArrayList<>();
        String query = "SELECT * FROM managers";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                managers.add(new Manager(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                ));
            }
        }
        return managers;
    }

    public void updateManager(Manager manager) throws SQLException {
        String query = "UPDATE managers SET name = ?, email = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manager.getName());
            statement.setString(2, manager.getEmail());
            statement.setInt(3, manager.getId());
            statement.executeUpdate();
        }
    }

    public void deleteManager(int id) throws SQLException {
        String query = "DELETE FROM managers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
