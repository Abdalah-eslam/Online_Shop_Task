package org.Task.model;

import jakarta.persistence.*;
import org.Task.model.Enum.PaymentMethod;

import java.math.BigDecimal;

@Entity
public class Payment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Payment() {
    }

    public Payment(
            PaymentMethod paymentMethod,
            BigDecimal amount,
            Order order
    ) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.order = order;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

