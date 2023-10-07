ALTER TABLE `order_header`
    ADD COLUMN `count` INTEGER DEFAULT 0 AFTER `levels`;