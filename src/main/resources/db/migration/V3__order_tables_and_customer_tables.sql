CREATE TABLE IF NOT EXISTS `customer` (
                            `id`  bigint NOT NULL AUTO_INCREMENT,
                            `user_id` bigint DEFAULT NULL,
                            `user_name` varchar(50) DEFAULT NULL,
                            `customer_name` varchar(50) NOT NULL,
                            `address` varchar(30) DEFAULT NULL,
                            `zip_code` varchar(30) DEFAULT NULL,
                            `phone` varchar(20) DEFAULT NULL,
                            `email` varchar(255) DEFAULT NULL,
                            `created_date` timestamp ,
                            `last_modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `version` int(11) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            CONSTRAINT `user_id_fk` FOREIGN KEY (user_id) REFERENCES `user`(`id`)
) ENGINE=InnoDB;
ALTER TABLE `user`
    ADD COLUMN `customer_id` bigint NOT NULL;
ALTER TABLE `user`
    ADD CONSTRAINT `customer_id_fk`
        FOREIGN KEY (`customer_id`) REFERENCES `customer`(`id`);

CREATE TABLE IF NOT EXISTS `order_header` (
                                `id`  bigint NOT NULL AUTO_INCREMENT,
                                `status` varchar(30) DEFAULT  NULL,
                                levels INTEGER DEFAULT 1,
                                `billing_address` varchar(30) DEFAULT NULL,
                                `billing_zip_code` varchar(30) DEFAULT NULL,
                                `shipping_address` varchar(30) DEFAULT NULL,
                                `shipping_zip_code` varchar(30) DEFAULT NULL,
                                `order_status` varchar(30) DEFAULT NULL,
                                `created_date` timestamp ,
                                `last_modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `customer_id`  bigint NOT NULL,
                                `approval_id` bigint default NULL,
                                PRIMARY KEY (`id`),
                                CONSTRAINT `order_customer_fk` FOREIGN KEY (`customer_id`)
                                    REFERENCES `customer` (`id`)
) ENGINE=InnoDB;
CREATE INDEX `order_header_customer_id_index`
    ON `order_header`(customer_id);

CREATE TABLE IF NOT EXISTS `order_approval` (
                                  `id`  bigint NOT NULL AUTO_INCREMENT,
                                  `approved_by_id` bigint NOT NULL,
                                  `created_date` timestamp ,
                                  `last_modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  `order_header_id`  bigint NOT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
ALTER TABLE `order_approval`
    ADD CONSTRAINT `order_header_fk`
        FOREIGN KEY (`order_header_id`) REFERENCES `order_header`(`id`);
ALTER TABLE `order_approval`
    ADD CONSTRAINT `approved_by_id_fk`
        FOREIGN KEY (`approved_by_id`) REFERENCES `user`(`id`);
ALTER TABLE `order_header`
    ADD CONSTRAINT `approval_id_fk`
        FOREIGN KEY (`approval_id`) REFERENCES `order_approval`(`id`);
