package org.Task.service;

import org.Task.exception.CustomerNotFoundException;
import org.Task.exception.InvalidOrderStateException;
import org.Task.model.*;
import org.Task.model.Enum.OrderStatus;
import org.Task.model.Enum.PaymentMethod;
import org.Task.repository.CustomerRepository;
import org.Task.repository.OrderRepository;
import org.Task.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id);
    }
    @Transactional
    public Order placeOrder( Long customerId,
                            Map<Long,Integer>
                              productQuantities){
        //fetching customer
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        //fetching products
        List<Product> products = productQuantities.keySet().stream()
                .map(productRepository::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Order order = new Order();
        order.setCustomer(customer);
        // check stock availability
        for (Product product : products) {
            if(product.getStock()< productQuantities.get(product.getId())){
                throw new InvalidOrderStateException("Not enough stock for product: " + product.getName());
            }
            //reduce stock
            product.setStock(product.getStock() - productQuantities.get(product.getId()));


            //add order item
            order.addOrderItem(new OrderItem(
                    product,
                    productQuantities.get(product.getId()),
                    BigDecimal.valueOf( product.getPrice()),
                    "USD"
            ));
        }
        order.setStatus(OrderStatus.NEW);
        //saving order
        orderRepository.save(order);
        return order;
    }


    @Transactional
    public void pay(Long orderId , PaymentMethod paymentMethod){
        //fetching order from DB
        Order order = orderRepository.findById(orderId);
        if(order == null){
            throw new InvalidOrderStateException("Order not found");
        }

        if(order.getStatus() != OrderStatus.NEW){
            throw new InvalidOrderStateException("Order is not in a valid state for payment");
        }

        //creating payment
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        order.setPayment(payment);
        order.setStatus(OrderStatus.PAID);
        //saving payment and order
        orderRepository.save(order);
    }
    @Transactional
    public void ship(long orderId){
        Order order = orderRepository.findById(orderId);
        if (order != null && order.getStatus() == OrderStatus.PAID) {
            order.setStatus(OrderStatus.SHIPPED);
            orderRepository.save(order);
        } else {
            throw new InvalidOrderStateException("Order is not in a valid state for shipping");
        }
    }
    @Transactional
    public void cancel(long orderId){
        //fetching Oder form DB
        Order order = orderRepository.findById(orderId);
        if (order != null && (order.getStatus() == OrderStatus.NEW || order.getStatus() == OrderStatus.PAID)) {
            order.setStatus(OrderStatus.CANCELLED);
            //restocking Products
            order.getOrderItems().stream().forEach(orderItem -> {
                Product product = orderItem.getProduct();
                product.setStock(product.getStock() + orderItem.getQuantity());
                productRepository.save(product);
            });
            //save order
            orderRepository.save(order);
        } else {
            throw new InvalidOrderStateException("Order is not in a valid state for cancellation");
        }
    }


    public Order getOrderSummary(long orderId){
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new InvalidOrderStateException("Order not found");
        }
        return order;
    }

}
