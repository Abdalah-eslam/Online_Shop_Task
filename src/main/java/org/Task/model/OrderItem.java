    package org.Task.model;

    import jakarta.persistence.Entity;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;

    import java.math.BigDecimal;

    @Entity
    public class OrderItem extends BaseEntity {
        @ManyToOne
        @JoinColumn(name = "product_id" , nullable = false)
        private Product product;
        private Integer quantity;
        private BigDecimal price;
        @ManyToOne
        @JoinColumn(name = "order_id" , nullable = false)
        private Order order;
        private String currency ;

        public OrderItem(
                Product product,
                Integer quantity,
                BigDecimal price,
                String currency
        ) {
            this.product = product;
            this.quantity = quantity;
            this.price = price;
            this.currency = currency;
        }

        public OrderItem() {
        }


        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;

        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;

        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public BigDecimal getTotalPrice() {
            if (price == null || quantity == null) {
                return BigDecimal.ZERO;
            }

            return price.multiply(BigDecimal.valueOf(quantity));
        }

        @Override
        public String toString() {
            return "OrderItem{" +
                    "product=" + product +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    ", currency='" + currency + '\'' +
                    '}';
        }
    }
