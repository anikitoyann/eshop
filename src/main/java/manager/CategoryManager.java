package manager;

import db.DBConnectionProvider;
import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    public static Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void save(Category category) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO category(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<Category> getAll() {
        List<Category> categoryList = new ArrayList<>();
        try (PreparedStatement prepareStatement = connection.prepareStatement("SELECT * from category")) {
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                categoryList.add(getCategoryFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }


    public Category getById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * from category where id=?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getCategoryFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Category category) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE category SET name=? WHERE id=?")) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeById(int categoryId) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM category where id=?")) {
            statement.setInt(1, categoryId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Category getCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }
}
