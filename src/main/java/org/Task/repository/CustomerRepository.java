package org.Task.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.Task.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class CustomerRepository {

    @PersistenceContext
    private EntityManager em;

    public Customer save(Customer customer) {
        if (customer.getId() == null) em.persist(customer);
        else em.merge(customer);
        return customer;
    }

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public void deleteById(Long id) {
        em.remove(findById(id));
    }
    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(em.createQuery("select c from Customer c where c.email = :email", Customer.class)
                .setParameter("email", email)
                .getSingleResult());
    }

    public List<Customer> findAll() {
        return em.createQuery("select c from Customer c", Customer.class).getResultList();
    }


}

