package com.example.onlineauction.dao;

import com.example.onlineauction.DatabaseConnector;
import com.example.onlineauction.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static Connection connection;

    static {
        try {
            connection = DatabaseConnector.ConnectDb();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CategoryDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Category category) throws SQLException {
        String query = "INSERT INTO category (name_category) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getName());

            statement.executeUpdate();
        }
    }

    public void update(Category category) throws SQLException {
        String query = "UPDATE category SET name_category = ? WHERE idcategory = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());

            statement.executeUpdate();
        }
    }

    public void delete(int categoryId) throws SQLException {
        String query = "DELETE FROM category WHERE idcategory = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);

            statement.executeUpdate();
        }
    }

    public boolean isCategoryUsed(int categoryId) throws SQLException {
        String query = "SELECT COUNT(*) FROM lots WHERE category_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }

        return false;
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM category";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idcategory");
                String name = resultSet.getString("name_category");

                Category category = new Category(id, name);
                categories.add(category);
            }
        }

        return categories;
    }

    public List<String> getAllStringCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        String query = "SELECT * FROM category";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name_category");

                String category = name;
                categories.add(category);
            }
        }
        return categories;
    }

    public static String getCategoryById(int id) throws SQLException{
        String query = "SELECT name_category FROM category where idcategory= " + id;

        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                return resultSet.getString("name_category");
            }
        }
        return "";
    }

    public static int getCategoryIdByString(String category) throws SQLException{
        int result = 0;
        String query = "SELECT idcategory FROM category where name_category= " + category;

        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                result = resultSet.getInt("idcategory");
            }
        }
        return result;
    }

    public static List<Category> getAllCategoryById(int id) throws SQLException{
        List<Category> list = new ArrayList<>();

        String query = "SELECT * FROM lots where category_id= " + id;

        try(PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                Category category = new Category(
                  resultSet.getString("name_lots"),
                        resultSet.getDouble("start_price"),
                        resultSet.getDouble("current_price"),
                        resultSet.getDate("dosing_date"),
                        resultSet.getDouble("")
                );
            }
        }
    }
}

