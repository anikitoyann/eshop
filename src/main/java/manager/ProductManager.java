package manager;

import com.mysql.cj.jdbc.StatementImpl;
import db.DBConnectionProvider;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    static CategoryManager categoryManager = new CategoryManager();
    Connection connection = DBConnectionProvider.getInstance().getConnection();

    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * from product")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                productList.add(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void save(Product product) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO product(name,description,price,quantity,category_id) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.setInt(5, product.getCategory().getId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
            System.out.println("product inserted into DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * from product where id=?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getProductFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeById(int productId) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM product where id=?")) {
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printSumOfProducts() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT SUM(price) FROM product")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int sum = resultSet.getInt(1);
                System.out.println("Sum of products: " + sum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void printMaxOfPriceOfProduct() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT MAX(Price) FROM product")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int max = resultSet.getInt(1);
                System.out.println("Max of Product " + max);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printMinOfPriceOfProduct() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT MIN(Price) FROM product")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int min = resultSet.getInt(1);
                System.out.println("Min of Product " + min);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAvgOfPriceOfProduct() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT AVG(Price) FROM product")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double avg = resultSet.getInt(1);
                System.out.println("Avg of Product " + avg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getInt("price"));
        product.setQuantity(resultSet.getInt("quantity"));
        int category_id = resultSet.getInt("category_id");
        product.setCategory(categoryManager.getById(category_id));
        return product;
    }


}
