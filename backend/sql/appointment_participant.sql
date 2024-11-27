CREATE TABLE IF NOT EXISTS `appointment_participants`
(
    `id`             BIGINT AUTO_INCREMENT PRIMARY KEY,
    `appointment_id` BIGINT      NOT NULL,
    `member_id`      BIGINT      NOT NULL,
    `role`           VARCHAR(30) NOT NULL,
    FOREIGN KEY (appointment_id) REFERENCES `appointments` (id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES `members` (id) ON DELETE CASCADE
)
    ENGINE = InnoDB;