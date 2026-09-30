package org.Task.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.Task.model.*;
import org.Task.model.Enum.OrderStatus;
import org.Task.model.Enum.PaymentMethod;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class TestDataSeeder {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void seed() {

        // =========================
        // 1. Customers
        // =========================

        Customer abdallah = new Customer();
        abdallah.setName("Abdallah");
        abdallah.setEmail("abdallah@gmail.com");
        abdallah.setShipingAddress(new ShippingAddress("12 Main St", "Banha", "Egypt"));

        Customer ahmed = new Customer();
        ahmed.setName("Ahmed");
        ahmed.setEmail("ahmed@gmail.com");
        ahmed.setShipingAddress(new ShippingAddress("12 Main St", "Cairo", "Egypt"));

        entityManager.persist(abdallah);
        entityManager.persist(ahmed);


        // =========================
        // 2. Categories
        // =========================

        Category electronics = new Category();
        electronics.setName("Electronics");

        Category laptops = new Category();
        laptops.setName("Laptops");

        entityManager.persist(electronics);
        entityManager.persist(laptops);


        // =========================
        // 3. Products
        // =========================

        Product laptop = new Product();
        laptop.setSku("LAP-001");
        laptop.setName("Lenovo Laptop");
        laptop.setPrice(30000.0);
        laptop.setStock(10);

        Product mouse = new Product();
        mouse.setSku("MOU-001");
        mouse.setName("Logitech Mouse");
        mouse.setPrice(1000.0);
        mouse.setStock(20);

        Product keyboard = new Product();
        keyboard.setSku("KEY-001");
        keyboard.setName("Mechanical Keyboard");
        keyboard.setPrice(2500.0);
        keyboard.setStock(5);

        entityManager.persist(laptop);
        entityManager.persist(mouse);
        entityManager.persist(keyboard);


        // =========================
        // 4. Product Categories
        // =========================

        laptop.getCategories().add(laptops);
        laptop.getCategories().add(electronics);

        mouse.getCategories().add(electronics);
        keyboard.getCategories().add(electronics);


        // =========================
        // 5. Order #1
        // =========================

        Order order1 = new Order();

        order1.setCustomer(abdallah);
        order1.setStatus(OrderStatus.NEW);

        OrderItem item1 = new OrderItem(
                laptop,
                1,
                BigDecimal.valueOf(laptop.getPrice()),
                "USD"
        );

        OrderItem item2 = new OrderItem(
                mouse,
                2,
                BigDecimal.valueOf(mouse.getPrice()),
                "USD"
        );

        order1.addOrderItem(item1);
        order1.addOrderItem(item2);

        entityManager.persist(order1);


        // =========================
        // 6. Order #2
        // =========================

        Order order2 = new Order();

        order2.setCustomer(ahmed);
        order2.setStatus(OrderStatus.PAID);

        OrderItem item3 = new OrderItem(
                keyboard,
                1,
                BigDecimal.valueOf(keyboard.getPrice()),
                "USD"
        );

        order2.addOrderItem(item3);

        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.CARD);
        payment.setAmount(BigDecimal.valueOf(2500));
        payment.setOrder(order2);

        order2.setPayment(payment);

        entityManager.persist(order2);


        // =========================
        // 7. Order #3
        // =========================

        Order order3 = new Order();

        order3.setCustomer(abdallah);
        order3.setStatus(OrderStatus.CANCELLED);

        OrderItem item4 = new OrderItem(
                mouse,
                3,
                BigDecimal.valueOf(mouse.getPrice()),
                "USD"
        );

        order3.addOrderItem(item4);

        entityManager.persist(order3);


        System.out.println("=================================");
        System.out.println("Test data inserted successfully");
        System.out.println("=================================");
    }
}
