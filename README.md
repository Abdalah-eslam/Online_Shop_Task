# Online Store Backend — Spring Data Access & JPA

An individual capstone project built to practice **manual Spring ORM + JPA integration** without Spring Boot or Spring Data JPA.

The project simulates the backend of a small online store that manages **customers, products, orders, order items, and payments**, with a focus on persistence, transaction management, entity relationships, and database consistency.

## Project Overview

The application is a backend-only system driven by a Java `Main` class and automated tests.

The main goal is to understand what happens underneath higher-level frameworks by configuring Spring, JPA, and Hibernate manually.

The project focuses especially on maintaining data consistency when multiple operations happen within the same transaction, such as preventing stock inconsistencies when multiple customers attempt to purchase limited inventory.

## Technologies

* Java
* Spring Framework
* Spring ORM
* Spring Transactions
* Jakarta Persistence (JPA)
* Hibernate
* H2 Database
* Maven
* JPQL
* JPA Criteria API
* JUnit

## Architecture

The project uses a layered architecture:

```text
Main / Tests
     │
     ▼
Service Layer
     │
     ▼
Repository Layer
     │
     ▼
JPA EntityManager
     │
     ▼
Hibernate
     │
     ▼
H2 Database
```

Spring is responsible for dependency injection and transaction management, while JPA provides the persistence API and Hibernate acts as the JPA implementation.

## Main Entities

The domain model contains:

* `Customer`
* `Product`
* `Category`
* `Order`
* `OrderItem`
* `Payment`

### Relationships

The project demonstrates different JPA relationships, including:

* One-to-One
* One-to-Many
* Many-to-One
* Many-to-Many

A join table is used where required for the many-to-many relationship between products and categories.

## Spring Configuration

The project uses **Java-based Spring configuration** instead of Spring Boot auto-configuration.

The persistence infrastructure is configured manually using:

* `@Configuration`
* `@ComponentScan`
* `@EnableTransactionManagement`
* `LocalContainerEntityManagerFactoryBean`
* `JpaTransactionManager`
* `HibernateJpaVendorAdapter`
* `PersistenceExceptionTranslationPostProcessor`

The application uses:

```java
@PersistenceContext
private EntityManager entityManager;
```

inside repository classes.

## Persistence Layer

Repositories are handwritten and use JPA's `EntityManager`.

Example:

```java
@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Product product) {
        entityManager.persist(product);
    }
}
```

The project intentionally does **not** use Spring Data repositories.

## Querying

Two JPA query approaches are demonstrated.

### JPQL

JPQL is used for object-oriented queries such as:

```java
SELECT p
FROM Product p
WHERE p.stock > :stock
```

### Criteria API

The JPA Criteria API is used for dynamically building type-safe queries.

This demonstrates how queries can be constructed programmatically without relying only on string-based JPQL.

## Transaction Management

Transactions are managed by Spring using:

```java
@Transactional
```

and:

```java
JpaTransactionManager
```

The goal is to keep related database operations atomic.

For example, placing an order may involve:

```text
Create Order
     ↓
Create Order Items
     ↓
Decrease Product Stock
     ↓
Create Payment
     ↓
Commit Transaction
```

If an operation fails, the transaction can be rolled back so that the database does not remain in an inconsistent state.

## Concurrency & Stock Consistency

One of the important scenarios in the project is handling concurrent purchases.

For example:

```text
Product stock = 1

Customer A ──┐
              ├──> Purchase
Customer B ──┘
```

The application must prevent both transactions from successfully purchasing the same final item.

The project therefore explores transaction boundaries and database-level consistency when modifying inventory.

## Requirements

The project intentionally follows the course constraints.

### Used

* Spring ORM + JPA
* `LocalContainerEntityManagerFactoryBean`
* `JpaTransactionManager`
* `HibernateJpaVendorAdapter`
* `@EnableTransactionManagement`
* `@Transactional`
* `@PersistenceContext`
* `EntityManager`
* `PersistenceExceptionTranslationPostProcessor`
* Java-based Spring configuration
* `@Repository`
* JPQL
* JPA Criteria API
* JPA annotations

### Not Used

The following technologies are intentionally excluded:

* Spring Data JPA
* `JpaRepository`
* `@EnableJpaRepositories`
* Derived Query Methods
* `JdbcTemplate`
* Spring JDBC data-access APIs
* Native Hibernate `Session`
* Native Hibernate `SessionFactory`
* HQL-only features
* Spring Boot

This keeps the project focused on understanding the underlying **Spring + JPA integration**.

## Project Structure

```text
src
├── main
│   └── java
│       └── org.Task
│           ├── config
│           │   └── AppConfig.java
│           │
│           ├── model
│           │   ├── Customer.java
│           │   ├── Product.java
│           │   ├── Category.java
│           │   ├── Order.java
│           │   ├── OrderItem.java
│           │   └── Payment.java
│           │
│           ├── repository
│           │   └── ...
│           │
│           └── service
│               └── ...
│
└── test
    └── java
        └── ...
```

## Running the Project

Clone the repository:

```bash
git clone <repository-url>
```

Open the project using IntelliJ IDEA or another Java IDE.

Then run the Maven tests:

```bash
mvn test
```

The application can also be executed through the main application class.

## Learning Objectives

This project demonstrates practical understanding of:

* JPA entity mapping
* Entity relationships
* Owning and inverse sides
* Join tables
* Cascade operations
* Orphan removal
* Lazy and eager loading
* Persistence context
* Entity lifecycle
* `EntityManager`
* JPQL
* Criteria API
* Spring dependency injection
* Transaction boundaries
* Rollback behavior
* Exception translation
* Concurrent data modifications
* Database consistency

## Project Goal

The goal of this capstone is not to build a production-ready e-commerce platform, but to demonstrate a strong understanding of how **Spring integrates with JPA and Hibernate at the persistence layer without relying on Spring Boot or Spring Data JPA abstractions**.

---

### Course

**Spring Data Access — Spring ORM & JPA Integration**

**Type:** Individual Capstone Project

**Duration:** 2 Weeks

**Score:** 100 Points + Up to 10 Bonus Points
