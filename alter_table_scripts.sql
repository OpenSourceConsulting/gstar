

CREATE TABLE IF NOT EXISTS `gstar`.`gstar_week_battle` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag` VARCHAR(30) NOT NULL,
  `status_cd` VARCHAR(20) NULL DEFAULT '1' COMMENT '주간배틀상태(1:진행중, 2: 중지, 3: 종료)',
  `battle_seq` INT NULL DEFAULT 1 COMMENT '배틀 주차수',
  `start_dt` DATETIME NULL COMMENT '배틀 시작일시',
  `end_dt` DATETIME NULL COMMENT '배틀 종료일시',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

ALTER TABLE `gstar`.`gstar_room` 
ADD COLUMN `gstar_week_battle_id` INT UNSIGNED NULL AFTER `create_dt`,
ADD INDEX `fk_gstar_room_gstar_week_battle1_idx` (`gstar_week_battle_id` ASC);
ALTER TABLE `gstar`.`gstar_room` 
ADD CONSTRAINT `fk_gstar_room_gstar_week_battle1`
  FOREIGN KEY (`gstar_week_battle_id`)
  REFERENCES `gstar`.`gstar_week_battle` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
CREATE TABLE IF NOT EXISTS `gstar`.`gstar_room_week` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `gstar_room_id` BIGINT UNSIGNED NOT NULL,
  `battle_seq` INT NOT NULL,
  `start_dt` DATETIME NULL,
  `end_dt` DATETIME NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_gstar_room_week_gstar_room1`
    FOREIGN KEY (`gstar_room_id`)
    REFERENCES `gstar`.`gstar_room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `gstar`.`gstar_room_contents_week` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `gstar_room_id` BIGINT UNSIGNED NOT NULL,
  `gstar_contents_id` BIGINT UNSIGNED NOT NULL,
  `battle_seq` INT NULL,
  `point_cnt` BIGINT UNSIGNED NULL DEFAULT 0 COMMENT '하트수(좋아요수)',
  `view_cnt` BIGINT UNSIGNED NULL DEFAULT 0 COMMENT '조회수',
  PRIMARY KEY (`id`),
  INDEX `fk_gstar_room_contents_week_gstar_room1_idx` (`gstar_room_id` ASC),
  INDEX `fk_gstar_room_contents_week_gstar_contents1_idx` (`gstar_contents_id` ASC),
  CONSTRAINT `fk_gstar_room_contents_week_gstar_room1`
    FOREIGN KEY (`gstar_room_id`)
    REFERENCES `gstar`.`gstar_room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_room_contents_week_gstar_contents1`
    FOREIGN KEY (`gstar_contents_id`)
    REFERENCES `gstar`.`gstar_contents` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


DELETE FROM gstar.common_code where group_cd = 'bank_cmpy';

INSERT INTO `common_code` VALUES ('bank_cmpy', '1', '농협중앙회', 1, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '2', '신협', 2, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '3', '우체국', 3, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '4', '새마을금고', 4, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '5', '우리은행', 5, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '6', 'KEB하나은행', 6, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '7', '신한은행', 7, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '8', '국민은행', 8, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '9', '기업은행', 9, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '10', '부산은행', 10, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '11', '광주은행', 11, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '12', '대구은행', 12, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '13', '경남은행', 13, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '14', '전북은행', 14, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '15', '제주은행', 15, NULL,'\0');
INSERT INTO `common_code` VALUES ('bank_cmpy', '16', '축협', 16, NULL,'\0');