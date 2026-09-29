package org.Task.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.Task.model.Order;

import java.util.List;

public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Order order) {
        if (order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
    }

    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    public void delete(Order order) {
        em.remove(order);
    }

    public List<Order> findByIdWithItems(Long id) {
        return em.createQuery("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.id = :id", Order.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Order>findByCustomerId(Long customerId) {
        return em.createQuery("SELECT o FROM Order o WHERE o.customer.id = :customerId", Order.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public List<Order> findByStatus (String status) {
        return em.createQuery("SELECT o FROM Order o WHERE o.status = :status", Order.class)
                .setParameter("status", status)
                .getResultList();
    }
}
