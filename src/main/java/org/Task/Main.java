package org.Task;

import org.Task.config.TestDataSeeder;
import org.Task.config.appconfig;
import org.Task.model.Enum.PaymentMethod;
import org.Task.model.Order;
import org.Task.model.Payment;
import org.Task.model.Product;
import org.Task.service.OrderService;
import org.Task.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(appconfig.class);

        context.getBean(TestDataSeeder.class).seed();

      ProductService productService =  context.getBean(ProductService.class );
        OrderService orderService = context.getBean(OrderService.class);


       Product product = productService.getProductById(1L);
        orderService.pay(1L, PaymentMethod.CARD);


        Order order = orderService.getOrderById(1L);

        System.out.println(order);


    }
}