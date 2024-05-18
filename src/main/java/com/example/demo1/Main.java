package com.example.demo1;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductDAO productDAO = new ProductDAO();
    private static final CustomerDAO customerDAO = new CustomerDAO();
    private static final ManagerDAO managerDAO = new ManagerDAO();
    private static final OrderDAO orderDAO = new OrderDAO();
    private static final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private static final AreaDAO areaDAO = new AreaDAO();

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageOrders();
                    break;
                case 2:
                    manageOrderItems();
                    break;
                case 3:
                    manageCustomers();
                    break;
                case 4:
                    manageProducts();
                    break;
                case 5:
                    manageManagers();
                    break;
                case 6:
                    manageAreas();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Manage Orders");
        System.out.println("2. Manage Order Items");
        System.out.println("3. Manage Customers");
        System.out.println("4. Manage Products");
        System.out.println("5. Manage Managers");
        System.out.println("6: Manage Areas");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void showCRUDMenu(String entity) {
        System.out.println("Choose an operation for " + entity + ":");
        System.out.println("1. Add");
        System.out.println("2. Get by ID");
        System.out.println("3. Get All");
        System.out.println("4. Update");
        System.out.println("5. Delete");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private static void manageOrders() {
        while (true) {
            showCRUDMenu("Orders");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        addOrder();
                        break;
                    case 2:
                        getOrderById();
                        break;
                    case 3:
                        getAllOrders();
                        break;
                    case 4:
                        updateOrder();
                        break;
                    case 5:
                        deleteOrder();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void manageOrderItems() {
        while (true) {
            showCRUDMenu("Order Items");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        addOrderItem();
                        break;
                    case 2:
                        getOrderItemById();
                        break;
                    case 3:
                        getAllOrderItems();
                        break;
                    case 4:
                        updateOrderItem();
                        break;
                    case 5:
                        deleteOrderItem();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void manageCustomers() {
        while (true) {
            showCRUDMenu("Customers");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        addCustomer();
                        break;
                    case 2:
                        getCustomerById();
                        break;
                    case 3:
                        getAllCustomers();
                        break;
                    case 4:
                        updateCustomer();
                        break;
                    case 5:
                        deleteCustomer();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void manageProducts() {
        while (true) {
            showCRUDMenu("Products");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        getProductById();
                        break;
                    case 3:
                        getAllProducts();
                        break;
                    case 4:
                        updateProduct();
                        break;
                    case 5:
                        deleteProduct();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void manageManagers() {
        while (true) {
            showCRUDMenu("Managers");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        addManager();
                        break;
                    case 2:
                        getManagerById();
                        break;
                    case 3:
                        getAllManagers();
                        break;
                    case 4:
                        updateManager();
                        break;
                    case 5:
                        deleteManager();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void manageAreas() {
        while (true) {
            showCRUDMenu("Areas");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        addArea();
                        break;
                    case 2:
                        getAreaById();
                        break;
                    case 3:
                        getAllAreas();
                        break;
                    case 4:
                        updateArea();
                        break;
                    case 5:
                        deleteArea();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addProduct() throws SQLException {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product product = new Product(0, name, price, quantity);
        productDAO.addProduct(product);
        System.out.println("Product added: " + product.getName());
    }

    private static void getProductById() throws SQLException {
        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product product = productDAO.getProductById(id);
        if (product != null) {
            System.out.println("Product retrieved: " + product.getName() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity());
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void getAllProducts() throws SQLException {
        List<Product> products = productDAO.getAllProducts();
        for (Product product : products) {
            System.out.println("Product ID: " + product.getId() + ", Product Name: " + product.getName() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity());
        }
    }

    private static void updateProduct() throws SQLException {
        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new product quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product product = new Product(id, name, price, quantity);
        productDAO.updateProduct(product);
        System.out.println("Product updated: " + product.getName());
    }

    private static void deleteProduct() throws SQLException {
        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        productDAO.deleteProduct(id);
        System.out.println("Product deleted.");
    }

    private static void addCustomer() throws SQLException {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(0, name, email);
        int customerId = customerDAO.addCustomer(customer);
        System.out.println("Customer added: " + customer.getName() + " with ID: " + customerId);
    }

    private static void getCustomerById() throws SQLException {
        System.out.print("Enter customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Customer customer = customerDAO.getCustomerById(id);
        if (customer != null) {
            System.out.println("Customer retrieved: " + customer.getName() + ", Email: " + customer.getEmail());
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void getAllCustomers() throws SQLException {
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println("Customer ID: " + customer.getId() + ", Name: " + customer.getName() + ", Email: " + customer.getEmail());
        }
    }

    private static void updateCustomer() throws SQLException {
        System.out.print("Enter customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new customer email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(id, name, email);
        customerDAO.updateCustomer(customer);
        System.out.println("Customer updated: " + customer.getName());
    }

    private static void deleteCustomer() throws SQLException {
        System.out.print("Enter customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        customerDAO.deleteCustomer(id);
        System.out.println("Customer deleted.");
    }

    private static void addManager() throws SQLException {
        System.out.print("Enter manager name: ");
        String name = scanner.nextLine();
        System.out.print("Enter manager email: ");
        String email = scanner.nextLine();

        Manager manager = new Manager(0, name, email);
        int managerId = managerDAO.addManager(manager);
        System.out.println("Manager added: " + manager.getName() + " with ID: " + managerId);
    }

    private static void getManagerById() throws SQLException {
        System.out.print("Enter manager ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Manager manager = managerDAO.getManagerById(id);
        if (manager != null) {
            System.out.println("Manager retrieved: " + manager.getName() + ", Email: " + manager.getEmail());
        } else {
            System.out.println("Manager not found.");
        }
    }

    private static void getAllManagers() throws SQLException {
        List<Manager> managers = managerDAO.getAllManagers();
        for (Manager manager : managers) {
            System.out.println("Manager ID: " + manager.getId() + ", Name: " + manager.getName() + ", Email: " + manager.getEmail());
        }
    }

    private static void updateManager() throws SQLException {
        System.out.print("Enter manager ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new manager name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new manager email: ");
        String email = scanner.nextLine();

        Manager manager = new Manager(id, name, email);
        managerDAO.updateManager(manager);
        System.out.println("Manager updated: " + manager.getName());
    }

    private static void deleteManager() throws SQLException {
        System.out.print("Enter manager ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        managerDAO.deleteManager(id);
        System.out.println("Manager deleted.");
    }

    private static void addOrder() throws SQLException {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter order date (YYYY-MM-DD): ");
        String orderDateStr = scanner.nextLine();
        Date orderDate = Date.valueOf(orderDateStr);

        Order order = new Order(0, customerId, orderDate, 0.0);
        int orderId = orderDAO.addOrder(order);
        System.out.println("Order added with ID: " + orderId);
    }

    private static void getOrderById() throws SQLException {
        System.out.print("Enter order ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Order order = orderDAO.getOrderById(id);
        if (order != null) {
            System.out.println("Order retrieved: ID = " + order.getId() + ", Customer ID = " + order.getCustomerId() + ", Order Date = " + order.getOrderDate() + ", Total Price = " + order.getTotalPrice());
        } else {
            System.out.println("Order not found.");
        }
    }

    private static void getAllOrders() throws SQLException {
        List<Order> orders = orderDAO.getAllOrders();
        for (Order order : orders) {
            System.out.println("Order: ID = " + order.getId() + ", Customer ID = " + order.getCustomerId() + ", Order Date = " + order.getOrderDate() + ", Total Price = " + order.getTotalPrice());
        }
    }

    private static void updateOrder() throws SQLException {
        System.out.print("Enter order ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new order date (YYYY-MM-DD): ");
        String orderDateStr = scanner.nextLine();
        Date orderDate = Date.valueOf(orderDateStr);
        double totalPrice = 0.0; // Recalculate the total price based on the order items

        Order order = new Order(id, customerId, orderDate, totalPrice);
        orderDAO.updateOrder(order);
        System.out.println("Order updated: ID = " + order.getId());
    }

    private static void deleteOrder() throws SQLException {
        System.out.print("Enter order ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        orderDAO.deleteOrder(id);
        System.out.println("Order deleted.");
    }


    private static void addOrderItem() throws SQLException {
        System.out.print("Enter order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        OrderItem orderItem = new OrderItem(0, orderId, productId, quantity);
        int orderItemId = orderItemDAO.addOrderItem(orderItem);
        orderDAO.updateOrderTotalPrice(orderId); // Update the order's total price after adding the item
        System.out.println("Order item added with ID: " + orderItemId);
    }

    private static void getOrderItemById() throws SQLException {
        System.out.print("Enter order item ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        OrderItem orderItem = orderItemDAO.getOrderItemById(id);
        if (orderItem != null) {
            System.out.println("Order item retrieved: ID = " + orderItem.getId() + ", Order ID = " + orderItem.getOrderId() + ", Product ID = " + orderItem.getProductId() + ", Quantity = " + orderItem.getQuantity());
        } else {
            System.out.println("Order item not found.");
        }
    }

    private static void getAllOrderItems() throws SQLException {
        List<OrderItem> orderItems = orderItemDAO.getAllOrderItems();
        for (OrderItem orderItem : orderItems) {
            System.out.println("Order item: ID = " + orderItem.getId() + ", Order ID = " + orderItem.getOrderId() + ", Product ID = " + orderItem.getProductId() + ", Quantity = " + orderItem.getQuantity());
        }
    }

    private static void updateOrderItem() throws SQLException {
        System.out.print("Enter order item ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new product ID: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        OrderItem orderItem = new OrderItem(id, orderId, productId, quantity);
        orderItemDAO.updateOrderItem(orderItem);
        orderDAO.updateOrderTotalPrice(orderId); // Update the order's total price after updating the item
        System.out.println("Order item updated: ID = " + orderItem.getId());
    }

    private static void deleteOrderItem() throws SQLException {
        System.out.print("Enter order item ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        int orderId = orderItemDAO.getOrderItemById(id).getOrderId();
        orderItemDAO.deleteOrderItem(id);
        orderDAO.updateOrderTotalPrice(orderId); // Update the order's total price after deleting the item
        System.out.println("Order item deleted.");
    }

    private static void addArea() throws SQLException {
        System.out.print("Enter area name: ");
        String name = scanner.nextLine();
        System.out.print("Enter manager ID: ");
        int managerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Area area = new Area(0, name, managerId);
        int areaId = areaDAO.addArea(area);
        System.out.println("Area added with ID: " + areaId);
    }

    private static void getAreaById() throws SQLException {
        System.out.print("Enter area ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Area area = areaDAO.getAreaById(id);
        if (area != null) {
            System.out.println("Area retrieved: ID = " + area.getId() + ", Name = " + area.getName() + ", Manager ID = " + area.getManagerId());
        } else {
            System.out.println("Area not found.");
        }
    }

    private static void getAllAreas() throws SQLException {
        List<Area> areas = areaDAO.getAllAreas();
        for (Area area : areas) {
            System.out.println("Area: ID = " + area.getId() + ", Name = " + area.getName() + ", Manager ID = " + area.getManagerId());
        }
    }

    private static void updateArea() throws SQLException {
        System.out.print("Enter area ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new area name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new manager ID: ");
        int managerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Area area = new Area(id, name, managerId);
        areaDAO.updateArea(area);
        System.out.println("Area updated: ID = " + area.getId());
    }

    private static void deleteArea() throws SQLException {
        System.out.print("Enter area ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        areaDAO.deleteArea(id);
        System.out.println("Area deleted.");
    }
}
