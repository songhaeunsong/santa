CREATE TABLE IF NOT EXISTS `mountains`
(
    `id`       BIGINT PRIMARY KEY,
    `name`     VARCHAR(100) NOT NULL,
    `province` VARCHAR(30)  NOT NULL,
    `city`     VARCHAR(255) NOT NULL,
    `height`   INT          NOT NULL
)
    ENGINE = InnoDB;