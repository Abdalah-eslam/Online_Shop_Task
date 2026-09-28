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

    protected Payment() {
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
}

