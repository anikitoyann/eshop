import manager.CategoryManager;
import manager.ProductManager;
import model.Category;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class CategoryProductMain {
    private static Scanner scanner = new Scanner(System.in);
    private static CategoryManager categoryManager = new CategoryManager();
    private static ProductManager productManager = new ProductManager();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            System.out.println("Please input 0 for exit");
            System.out.println("Please input 1 for add Category");
            System.out.println("Please input 2 for Update Category by Id");
            System.out.println("Please input 3 for Delete Category by Id");
            System.out.println("Please input 4 for add Product");
            System.out.println("Please input 5 for delete Product by id");
            System.out.println("Please input 6 for Print SUM Of Products");
            System.out.println("Please input 7 for Print MAX  Of price Products");
            System.out.println("Please input 8 for Print MIN  Of price Products");
            System.out.println("Please input 9 for Print Avg  Of price Products");
            String command = scanner.nextLine();
            switch (command) {
                case "0":
                    isRun = false;
                    break;
                case "1":
                    addCategory();
                    break;
                case "2":
                    updateCategoryById();
                    break;
                case "3":
                    deleteCategoryById();
                    break;
                case "4":
                    addProduct();
                    break;
                case "5":
                    deleteProductById();
                    break;
                case "6":
                    productManager.printSumOfProducts();
                    break;
                case "7":
                    productManager.printMaxOfPriceOfProduct();
                    break;
                case "8":
                    productManager.printMinOfPriceOfProduct();
                    break;
                case "9":
                    productManager.printAvgOfPriceOfProduct();
                    break;

                default:
                    System.out.println("Wrong Commands");
            }
        }
    }

    private static void deleteProductById() {
        List<Product> all = productManager.getAll();
        for (Product product : all) {
            System.out.println(product);
        }
        System.out.println("Please choose productId");
        int productId = Integer.parseInt(scanner.nextLine());
        productManager.removeById(productId);
        System.out.println("Product removed");
    }

    private static void deleteCategoryById() {
        List<Category> all = categoryManager.getAll();
        for (Category category : all) {
            System.out.println(category);
        }
        System.out.println("Please choose categoryId");
        int categoryId = Integer.parseInt(scanner.nextLine());
        categoryManager.removeById(categoryId);
        System.out.println("Category removed");
    }

    private static void addProduct() {

        System.out.println("please input product name,description,price,quantity");
        String productStr = scanner.nextLine();
        String[] productData = productStr.split(",");
        Product product = new Product();
        product.setName(productData[0]);
        product.setDescription(productData[1]);
        product.setPrice(Integer.parseInt(productData[2]));
        product.setQuantity(Integer.parseInt(productData[3]));
        productManager.save(product);

    }


    private static void updateCategoryById() {
        List<Category> all = categoryManager.getAll();
        for (Category category : all) {
            System.out.println(category);
        }
        System.out.println("Please choose categoryId");
        int id = Integer.parseInt(scanner.nextLine());
        if (categoryManager.getById(id) != null) {
            System.out.println("please input category name");
            String name = scanner.nextLine();
            Category category = new Category();
            category.setId(id);
            category.setName(name);
            categoryManager.update(category);
            System.out.println("Category updated");

        } else {
            System.out.println("Company does not exists!!!!");
        }
    }


    private static void addCategory() {
        System.out.println("please input category name");
        String name = scanner.nextLine();
        Category category = new Category();
        category.setName(name);
        categoryManager.save(category);
    }
}
