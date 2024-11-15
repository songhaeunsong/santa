CREATE TABLE IF NOT EXISTS `appointments`
(
    `id`               BIGINT AUTO_INCREMENT PRIMARY KEY,
    `group_id`         BIGINT   NOT NULL,
    `mountain_id`      BIGINT   NOT NULL,
    `admin_id`         BIGINT   NOT NULL,
    `count_of_members` BIGINT DEFAULT 1,
    `meet_at`          DATETIME NOT NULL,
    FOREIGN KEY (group_id) REFERENCES `groups` (id) ON DELETE CASCADE,
    FOREIGN KEY (mountain_id) REFERENCES `mountains` (id) ON DELETE CASCADE,
    FOREIGN KEY (admin_id) REFERENCES `members` (id) ON DELETE CASCADE
)
    ENGINE = InnoDB;