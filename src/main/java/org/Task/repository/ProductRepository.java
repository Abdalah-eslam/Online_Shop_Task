package org.Task.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.Task.model.Category;
import org.Task.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager em;

    public Product save(Product product) {
        if (product.getId() == null) em.persist(product);
        else em.merge(product);
        return product;
    }

    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    public void delete(Product product) {
        em.remove(product);
    }

    public Product findBySku(String sku) {
        return em.createQuery("SELECT p FROM Product p WHERE p.sku = :sku", Product.class)
                .setParameter("sku", sku)
                .getSingleResult();
    }

    public List<Product> findByCategory(String categoryName) {
       return em.createQuery("SELECT p FROM Product p WHERE p.category.name = :categoryName", Product.class)
                .setParameter("categoryName", categoryName)
                .getResultList();
    }

    public List<Product> search(
            String keyword,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String category
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Product> query = cb.createQuery(Product.class);

        Root<Product> product = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        // keyword
        if (keyword != null && !keyword.isBlank()) {
            predicates.add(
                    cb.like(
                            cb.lower(product.get("name")),
                            "%" + keyword.toLowerCase() + "%"
                    )
            );
        }

        // minPrice
        if (minPrice != null) {
            predicates.add(
                    cb.greaterThanOrEqualTo(
                            product.get("price"),
                            minPrice
                    )
            );
        }

        // maxPrice
        if (maxPrice != null) {
            predicates.add(
                    cb.lessThanOrEqualTo(
                            product.get("price"),
                            maxPrice
                    )
            );
        }

        // category
        if (category != null && !category.isBlank()) {
            Join<Product, Category> categoryJoin =
                    product.join("categories");

            predicates.add(
                    cb.equal(
                            cb.lower(categoryJoin.get("name")),
                            category.toLowerCase()
                    )
            );
        }

        query.select(product)
                .where(cb.and(predicates.toArray(new Predicate[0])))
                .distinct(true);

        return em
                .createQuery(query)
                .getResultList();
    }

    public List<Product> findLowStock(int threshold){
        return em.createQuery("SELECT p FROM Product p WHERE p.stock < :threshold", Product.class)
                .setParameter("threshold", threshold)
                .getResultList();
    }

    public List<Product> findPage (int page, int size ){
        return em.createQuery("SELECT p FROM Product p", Product.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    public long count(){
        return em.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                .getSingleResult();
    }
}
