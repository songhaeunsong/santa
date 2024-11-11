CREATE TABLE IF NOT EXISTS `groups`
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`        VARCHAR(30)  NOT NULL,
    `exp`         BIGINT   DEFAULT 0,
    `admin_id`    BIGINT,
    `description` VARCHAR(100),
    `create_at`   DATETIME DEFAULT now(),
    `delete_at`   DATETIME DEFAULT NULL,
    FOREIGN KEY(admin_id) REFERENCES members(id)
    ON DELETE CASCADE
)
    ENGINE = InnoDB;