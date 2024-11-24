CREATE TABLE IF NOT EXISTS `mountain_completes`
(
    `id`       BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_id` BIGINT,
    `mountain_id` BIGINT,
    `create_at` DATETIME NOT NULL,
    FOREIGN KEY (member_id) REFERENCES `members`(id) ON DELETE CASCADE,
    FOREIGN KEY (mountain_id) REFERENCES mountains (id)
    )
    ENGINE = InnoDB;