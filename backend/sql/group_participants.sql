CREATE TABLE IF NOT EXISTS `group_participants`
(
    `id`        BIGINT AUTO_INCREMENT PRIMARY KEY,
    `member_id` BIGINT NOT NULL,
    `group_id`  BIGINT NOT NULL,
    `role`      VARCHAR(20) NOT NULL,
    `create_at` DATETIME DEFAULT now(),
    FOREIGN KEY(member_id) REFERENCES members(id) ON DELETE CASCADE,
    FOREIGN KEY(group_id) REFERENCES `groups`(id) ON DELETE CASCADE
)
    ENGINE = InnoDB;