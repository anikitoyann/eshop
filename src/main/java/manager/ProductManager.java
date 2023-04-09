package manager;

import com.mysql.cj.jdbc.StatementImpl;
import db.DBConnectionProvider;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    CategoryManager categoryManager = new CategoryManager();
    Connection connection = DBConnectionProvider.getInstance().getConnection();

    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from product");
            while (resultSet.next()) {
                productList.add(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void save(Product product) {
        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO product(name,description,price,quantity) VALUES('%s','%s',%d,'%d')";
            statement.executeUpdate(String.format(sql, product.getName(), product.getDescription(), product.getPrice(), product.getQuantity()), Statement.RETURN_GENERATED_KEYS);
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
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from product where id=" + id);
            if (resultSet.next()) {
                return getProductFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeById(int productId) {
        String sql = "DELETE FROM product where id=" + productId;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printSumOfProducts() {
        String sql = "SELECT SUM(price) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int sum = resultSet.getInt(1);
                System.out.println("Sum of products: " + sum);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void printMaxOfPriceOfProduct() {
        String sql = "SELECT MAX(Price) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int max = resultSet.getInt(1);
                System.out.println("Max of Product " + max);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printMinOfPriceOfProduct() {
        String sql = "SELECT MIN(Price) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int min = resultSet.getInt(1);
                System.out.println("Min of Product " + min);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void printAvgOfPriceOfProduct() {
        String sql = "SELECT AVG(Price) FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
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
        return product;
    }


}
