package com.example.demo1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AreaDAO {
    public int addArea(Area area) throws SQLException {
        String query = "INSERT INTO areas (name, manager_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, area.getName());
            statement.setInt(2, area.getManagerId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating area failed, no ID obtained.");
                }
            }
        }
    }

    public Area getAreaById(int id) throws SQLException {
        String query = "SELECT * FROM areas WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Area(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("manager_id")
                );
            }
            return null;
        }
    }

    public List<Area> getAllAreas() throws SQLException {
        List<Area> areas = new ArrayList<>();
        String query = "SELECT * FROM areas";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                areas.add(new Area(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("manager_id")
                ));
            }
        }
        return areas;
    }

    public void updateArea(Area area) throws SQLException {
        String query = "UPDATE areas SET name = ?, manager_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, area.getName());
            statement.setInt(2, area.getManagerId());
            statement.setInt(3, area.getId());
            statement.executeUpdate();
        }
    }

    public void deleteArea(int id) throws SQLException {
        String query = "DELETE FROM areas WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
