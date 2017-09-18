#DROP DATABASE IF EXISTS `billing_contract`;

#CREATE DATABASE `billing_contract`;

DROP TABLE IF EXISTS `billing_contract`.`product_a`;

CREATE TABLE `billing_contract`.`product_a` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT(8),
  `charge` DECIMAL(10,2) NULL,
  `setup_charge` DECIMAL(10,2) NULL,
  `start_date` TIMESTAMP,
  `contract_length` INT(3),
  PRIMARY KEY (`id`));
