CREATE TABLE IF NOT EXISTS `order_line` (
                                            `id`  bigint NOT NULL AUTO_INCREMENT,
                                            level integer not null,
                                            status varchar(30) not null default 'active',
                                            `quantity_ordered` int(11) DEFAULT NULL,
                                            `order_header_id`  bigint DEFAULT NULL,
                                            `created_date` timestamp ,
                                            `last_modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            `product_id`  bigint NOT NULL,
                                            PRIMARY KEY (`id`),
                                            KEY `order_header_pk` (`order_header_id`),
                                            KEY `order_line_prosuct_FK` (`product_id`),
                                            CONSTRAINT `order_header_pk` FOREIGN KEY (`order_header_id`) REFERENCES `order_header` (`id`)
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `product` (
                                         `id`  bigint NOT NULL AUTO_INCREMENT,
                                         colour varchar(30) not null,
                                         `date_of_arrival` timestamp DEFAULT CURRENT_TIMESTAMP,
                                         `description` varchar(100) DEFAULT NULL,
                                         `product_status` varchar(20) DEFAULT NULL,
                                         `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
                                         `last_modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         PRIMARY KEY (`id`)
) ENGINE=InnoDB;

ALTER TABLE order_line
    ADD CONSTRAINT `order_line_product_FK` FOREIGN KEY
        (`product_id`) REFERENCES `product` (`id`);
CREATE TABLE IF NOT EXISTS `category` (
                                          `id`  bigint NOT NULL AUTO_INCREMENT,
                                          `description` varchar(100) DEFAULT NULL,
                                          `created_date` timestamp,
                                          `last_modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `product_category` (
                                                  `product_id`  bigint NOT NULL,
                                                  `category_id`  bigint NOT NULL,
                                                  PRIMARY KEY (`product_id`,`category_id`),
                                                  KEY `pk_category_id_fk` (`category_id`),
                                                  CONSTRAINT `pk_category_id_fk` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
                                                  CONSTRAINT `pk_product_id_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB;

