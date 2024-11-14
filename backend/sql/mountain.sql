CREATE TABLE IF NOT EXISTS `mountains`
(
    `id`       BIGINT PRIMARY KEY,
    `name`     VARCHAR(30) NOT NULL,
    `province` VARCHAR(100),
    `city`     VARCHAR(100),
    `height`    INT,
    `lat`      VARCHAR(100),
    `lon`      VARCHAR(100),
    `image`    VARCHAR(1000)
)
    ENGINE = InnoDB;