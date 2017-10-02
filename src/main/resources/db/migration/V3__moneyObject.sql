ALTER TABLE `billing_contract`.`product_a`
DROP COLUMN `charge`,
DROP COLUMN `setup_charge`,
ADD COLUMN `charge_value` DECIMAL(10,2) NULL,
ADD COLUMN `charge_currency` VARCHAR (5) NULL,
ADD COLUMN `setup_charge_value` DECIMAL(10,2) NULL,
ADD COLUMN `setup_charge_currency` VARCHAR (5) NULL;