-- Active: 1700452364000@@127.0.0.1@3306@clothingstore

DROP DATABASE IF EXISTS clothingstore;

SET GLOBAL max_allowed_packet = 16777216;

CREATE DATABASE
    clothingstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE clothingstore;

CREATE TABLE
    `users` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `username` VARCHAR(50) NOT NULL,
        `password` VARCHAR(100) NOT NULL,
        `email` VARCHAR(100) NOT NULL,
        `name` VARCHAR(50) NOT NULL,
        `phone` VARCHAR(10) NOT NULL,
        `gender` INT,
        `image` VARCHAR(255) NOT NULL,
        `role_id` INT,
        `address` VARCHAR(100) NOT NULL,
        `status` ENUM ('active', 'inactive', 'banned') NOT NULL DEFAULT "active",
        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `roles` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `name` ENUM ('admin', 'manager', 'employee') NOT NULL DEFAULT "employee",
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `permissions` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(50),
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `role_permissions` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `role_id` INT,
        `permission_id` INT,
        `status` ENUM ('active', 'inactive') NOT NULL DEFAULT "inactive",
        PRIMARY KEY (`id`)
    );

CREATE TABLE 
    `user_permissions` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `user_id` INT,
        `permission_id` INT,
        `status` ENUM ('active', 'inactive') NOT NULL DEFAULT "inactive",
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `customers` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(50) NOT NULL,
        `phone` VARCHAR(10) NOT NULL,
        `email` VARCHAR(100) DEFAULT NULL,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `points` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `customer_id` INT,
        `points_earned` INT NOT NULL DEFAULT 0,
        `points_used` INT NOT NULL DEFAULT 0,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `point_transactions` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `customer_id` INT,
        `transaction_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `points_earned` INT NOT NULL DEFAULT 0,
        `points_used` INT NOT NULL DEFAULT 0,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `orders` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `customer_id` INT,
        `user_id` INT,
        `order_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `total_amount` DOUBLE NOT NULL,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `order_items` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `order_id` INT,
        `product_id` INT,
        `size_id` INT,
        `quantity` INT,
        `price` DOUBLE NOT NULL,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `payment_methods` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `method_name` VARCHAR(50),
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `payments` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `order_id` INT,
        `method_id` INT,
        `payment_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `total_price` DOUBLE NOT NULL,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `products` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(50) NOT NULL,
        `category_id` INT NOT NULL,
        `image` LONGTEXT NOT NULL,
        `gender` INT NOT NULL,
        `price` DOUBLE NOT NULL,
        `status` INT NOT NULL DEFAULT 1,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `imports` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `user_id` INT NOT NULL,
        `import_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `total_price` INT,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `import_items` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `import_id` INT,
        `product_id` INT,
        `size_id` INT,
        `quantity` INT,
        `price` DOUBLE NOT NULL,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `sizes` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(255) NOT NULL,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `size_items` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `product_id` INT,
        `size_id` INT,
        `quantity` INT NOT NULL DEFAULT 0,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `categories` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(50),
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `discounts` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `name` VARCHAR(50) NOT NULL,
        `discount_percentage` INT,
        `start_day` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `end_day` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`)
    );

CREATE TABLE
    `discount_items` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `category_id` INT NOT NULL,
        `discount_id` INT NOT NULL,
        PRIMARY KEY (`id`)
    );

ALTER TABLE `users`
ADD
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

ALTER TABLE `role_permissions`
ADD
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

ALTER TABLE `user_permissions`
ADD
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `role_permissions`
ADD
    FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`);

ALTER TABLE `points`
ADD
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`);

ALTER TABLE
    `point_transactions`
ADD
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`);

ALTER TABLE `orders`
ADD
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`);

ALTER TABLE `orders`
ADD
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `order_items`
ADD
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `order_items`
ADD
    FOREIGN KEY (`size_id`) REFERENCES `sizes` (`id`);

ALTER TABLE `payments`
ADD
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `payments`
ADD
    FOREIGN KEY (`method_id`) REFERENCES `payment_methods` (`id`);

ALTER TABLE `order_items`
ADD
    FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `import_items`
ADD
    FOREIGN KEY (`import_id`) REFERENCES `imports` (`id`);

ALTER TABLE `import_items`
ADD
    FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `import_items`
ADD
    FOREIGN KEY (`size_id`) REFERENCES `sizes` (`id`);

ALTER TABLE `size_items`
ADD
    FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `size_items`
ADD
    FOREIGN KEY (`size_id`) REFERENCES `sizes` (`id`);

ALTER TABLE `discount_items`
ADD
    FOREIGN KEY (`discount_id`) REFERENCES `discounts` (`id`);

ALTER TABLE `discount_items`
ADD
    FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `products`
ADD
    FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `imports`
ADD
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);