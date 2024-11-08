CREATE DATABASE santa DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `members`
(
    `id`          BIGINT PRIMARY KEY,
    `nickname`    VARCHAR(100) NOT NULL,
    `email`       VARCHAR(255) NOT NULL,
    `exp`         INT      DEFAULT 0,
    `uuid`        VARCHAR(255) NOT NULL,
    `profile_url` VARCHAR(1000),
    `tier`        VARCHAR(50)  NOT NULL,
    `create_at`   DATETIME DEFAULT now(),
    `delete_at`   DATETIME DEFAULT NULL
)
    ENGINE = InnoDB;