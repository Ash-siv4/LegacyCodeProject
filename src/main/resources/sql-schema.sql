drop schema legacy;
CREATE SCHEMA IF NOT EXISTS `legacy`;
USE `legacy`;
CREATE TABLE IF NOT EXISTS `legacy`.`items` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `item_name` VARCHAR(40) NULL DEFAULT NULL,
    `item_price` DOUBLE NULL DEFAULT NULL,
    `item_quantity` INT(11) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `legacy`.`cart` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `fk_item_id` INT(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_item_id` FOREIGN KEY (`fk_item_id`) REFERENCES items(`id`)
);