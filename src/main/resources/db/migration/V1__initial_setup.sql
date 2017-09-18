DROP TABLE IF EXISTS `product_a`;

CREATE TABLE `product_a` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT(8),
  `charge` DECIMAL(10,2) NULL,
  `setup_charge` DECIMAL(10,2) NULL,
  `start_date` TIMESTAMP,
  `contract_length` INT(3),
  PRIMARY KEY (`id`));
