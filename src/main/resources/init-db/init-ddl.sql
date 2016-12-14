-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 52.79.85.39    Database: gstar
-- ------------------------------------------------------
-- Server version	5.5.52

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `common_code`
--

DROP TABLE IF EXISTS `common_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `common_code` (
  `group_cd` varchar(20) NOT NULL COMMENT '그룹코드',
  `code` varchar(20) NOT NULL COMMENT '코드',
  `code_nm` varchar(45) DEFAULT NULL COMMENT '코드명',
  `order_seq` smallint(6) DEFAULT NULL COMMENT '정렬순서',
  `cd_desc` varchar(100) DEFAULT NULL,
  `hidden` bit(1) DEFAULT NULL,
  PRIMARY KEY (`group_cd`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common_code`
--

LOCK TABLES `common_code` WRITE;
/*!40000 ALTER TABLE `common_code` DISABLE KEYS */;
INSERT INTO `common_code` VALUES ('account_type','1','gstar계정',1,NULL,'\0'),('account_type','2','facebook 계정',2,NULL,'\0'),('account_type','3','카카오 계정',3,NULL,'\0'),('account_type','4','관리자 계정',4,NULL,'\0'),('admin_role','ROLE_ADMIN','ROLE_ADMIN',13,'슈퍼 관리자','\0'),('admin_role','ROLE_BATTLE_READ','ROLE_BATTLE_READ',5,'대결영상관리 메뉴 read 만 가능','\0'),('admin_role','ROLE_BATTLE_WRITE','ROLE_BATTLE_WRITE',6,'대결영상관리 메뉴 read & write 모두 가능','\0'),('admin_role','ROLE_BOARD_READ','ROLE_BOARD_READ',9,'공지사항관리 메뉴 read 만 가능','\0'),('admin_role','ROLE_BOARD_WRITE','ROLE_BOARD_WRITE',10,'공지사항관리 메뉴 read & write 모두 가능','\0'),('admin_role','ROLE_MEMBER_READ','ROLE_MEMBER_READ',7,'회원관리 메뉴 read 만 가능','\0'),('admin_role','ROLE_MEMBER_WRITE','ROLE_MEMBER_WRITE',8,'회원관리 메뉴 read & write 모두 가능','\0'),('admin_role','ROLE_RMV_READ','ROLE_RMV_READ',1,'추천영상관리 메뉴 read 만 가능','\0'),('admin_role','ROLE_RMV_WRITE','ROLE_RMV_WRITE',2,'추천영상관리 메뉴 read & write 모두 가능','\0'),('admin_role','ROLE_TAB_READ','ROLE_TAB_READ',11,'탭메뉴관리 메뉴 read 만 가능','\0'),('admin_role','ROLE_TAB_WRITE','ROLE_TAB_WRITE',12,'탭메뉴관리 메뉴 read & write 모두 가능','\0'),('admin_role','ROLE_WBTL_READ','ROLE_WBTL_READ',3,'주간배틀관리 메뉴 read 만 가능','\0'),('admin_role','ROLE_WBTL_WRITE','ROLE_WBTL_WRITE',4,'주간배틀관리 메뉴 read & write 모두 가능','\0'),('bank_cmpy','1','농협중앙회',1,NULL,'\0'),('bank_cmpy','10','부산은행',10,NULL,'\0'),('bank_cmpy','11','광주은행',11,NULL,'\0'),('bank_cmpy','12','대구은행',12,NULL,'\0'),('bank_cmpy','13','경남은행',13,NULL,'\0'),('bank_cmpy','14','전북은행',14,NULL,'\0'),('bank_cmpy','15','제주은행',15,NULL,'\0'),('bank_cmpy','16','축협',16,NULL,'\0'),('bank_cmpy','17','SC제일은행',17,NULL,'\0'),('bank_cmpy','2','신협',2,NULL,'\0'),('bank_cmpy','3','우체국',3,NULL,'\0'),('bank_cmpy','4','새마을금고',4,NULL,'\0'),('bank_cmpy','5','우리은행',5,NULL,'\0'),('bank_cmpy','6','KEB하나은행',6,NULL,'\0'),('bank_cmpy','7','신한은행',7,NULL,'\0'),('bank_cmpy','8','국민은행',8,NULL,'\0'),('bank_cmpy','9','기업은행',9,NULL,'\0'),('battle_status','1','대결준비중',1,NULL,'\0'),('battle_status','2','대결중',2,NULL,'\0'),('battle_status','3','대결종료',3,NULL,'\0'),('board_type_cd','1','공지사항',1,'','\0'),('board_type_cd','2','이벤트',2,'','\0'),('board_type_cd','3','공지이벤트',3,'','\0'),('cnts_div','1','사용자 컨텐츠',1,NULL,'\0'),('cnts_div','2','추천',2,NULL,'\0'),('cnts_div','3','HOT',3,NULL,'\0'),('cnts_div','4','명예의전당',4,NULL,'\0'),('cnts_member_type','1','master',1,NULL,'\0'),('cnts_member_type','2','challenge',2,NULL,'\0'),('cnts_status','1','신규',1,NULL,'\0'),('cnts_status','2','포기',2,NULL,'\0'),('cnts_status','3','중지',3,NULL,'\0'),('gender','1','남자',1,NULL,'\0'),('gender','2','여자',2,NULL,'\0'),('group_cd','account_type','계정타입',NULL,NULL,'\0'),('group_cd','admin_role','관리자 계정 메뉴별 권한',11,'','\0'),('group_cd','bank_cmpy','은행사코드',NULL,NULL,'\0'),('group_cd','battle_status','대결방 상태',8,'gstar_room.battle_status_cd','\0'),('group_cd','board_type_cd','board 타입',12,'gstar_board.board_type_cd','\0'),('group_cd','cnts_div','컨텐츠 분류',NULL,NULL,'\0'),('group_cd','cnts_member_type','컨텐츠 멤버 타입',NULL,NULL,'\0'),('group_cd','cnts_status','컨텐츠 상태코드',NULL,NULL,'\0'),('group_cd','gender','성별코드',NULL,NULL,'\0'),('group_cd','point_hs_status','사용한포인트 상태',11,'gstar_point_history.status_cd','\0'),('group_cd','point_pc_status','포인트구매상태',10,'gstar_user_point.pc_status_cd','\0'),('group_cd','warn_type','신고유형코드',7,NULL,'\0'),('group_cd','warn_type_cd','신고유형코드',NULL,NULL,'\0'),('group_cd','week_battle_status','주간배틀상태',9,'gstar_week_battle.status_cd','\0'),('point_hs_status','1','정상사용',1,'','\0'),('point_hs_status','2','지불완료',2,'컨텐츠 소유자에게 계좌 입금 완료상태','\0'),('point_hs_status','3','사용취소',3,'포인트 사용자에게 되돌려준 상태.','\0'),('point_pc_status','1','정상구매',1,NULL,'\0'),('point_pc_status','2','환불요청',2,NULL,'\0'),('point_pc_status','3','환불완료',3,NULL,'\0'),('warn_type','1','음란',1,'음란물 영상','\0'),('warn_type','2','광고',2,'법적으로 허가되지 않은 온라인 도박 홍보','\0'),('warn_type','3','저작권침해',3,'타사 혹은 타인의 저작권을 침해하는 영상','\0'),('warn_type','4','명예회손',4,'아동학대, 장애인 비하 등 타인을 비방할 목적으로 하는 영상','\0'),('warn_type','5','청소년유해',5,'청소년에게 유해한 정보를 포함한 영상','\0'),('warn_type','6','불법',6,'법령에 위반되는 영상','\0'),('week_battle_status','1','대결중',1,NULL,'\0'),('week_battle_status','2','대결종료',2,NULL,'\0');
/*!40000 ALTER TABLE `common_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_account`
--

DROP TABLE IF EXISTS `gstar_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gstar_user_id` bigint(20) unsigned NOT NULL,
  `account_type_cd` varchar(20) NOT NULL DEFAULT '1' COMMENT '계정타입: (1:자체계정, 2: facbook계정, 3: 카카오계정)',
  `login_id` varchar(45) DEFAULT NULL COMMENT '로그인ID : email or etc',
  `passwd` varchar(20) DEFAULT NULL COMMENT '비밀번호',
  `last_login_dt` datetime DEFAULT NULL COMMENT '마지막 로그인 일시',
  `enabled` bit(1) DEFAULT b'1',
  `locked` bit(1) DEFAULT b'0',
  `create_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_id_UNIQUE` (`login_id`),
  KEY `fk_gstar_account_gstar_user1_idx` (`gstar_user_id`),
  CONSTRAINT `fk_gstar_account_gstar_user1` FOREIGN KEY (`gstar_user_id`) REFERENCES `gstar_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_account`
--

LOCK TABLES `gstar_account` WRITE;
/*!40000 ALTER TABLE `gstar_account` DISABLE KEYS */;
INSERT INTO `gstar_account` VALUES (1,1,'4','admin','1234',NULL,'','\0',NULL);
/*!40000 ALTER TABLE `gstar_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_account_auth`
--

DROP TABLE IF EXISTS `gstar_account_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_account_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gstar_account_id` bigint(20) unsigned NOT NULL,
  `authority` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gstar_user_auth_gstar_account1_idx` (`gstar_account_id`),
  CONSTRAINT `fk_gstar_user_auth_gstar_account1` FOREIGN KEY (`gstar_account_id`) REFERENCES `gstar_account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_account_auth`
--

LOCK TABLES `gstar_account_auth` WRITE;
/*!40000 ALTER TABLE `gstar_account_auth` DISABLE KEYS */;
INSERT INTO `gstar_account_auth` VALUES (1,1,'ROLE_ADMIN');
/*!40000 ALTER TABLE `gstar_account_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_ad`
--

DROP TABLE IF EXISTS `gstar_ad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_ad` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(90) DEFAULT NULL,
  `ad_url` varchar(255) DEFAULT NULL,
  `img_url` varchar(255) NOT NULL,
  `img_file_path` varchar(150) DEFAULT NULL,
  `note` varchar(900) DEFAULT NULL,
  `view_cnt` int(11) DEFAULT NULL,
  `click_cnt` int(11) DEFAULT NULL,
  `start_dt` datetime DEFAULT NULL,
  `end_dt` datetime DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_ad`
--

LOCK TABLES `gstar_ad` WRITE;
/*!40000 ALTER TABLE `gstar_ad` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_ad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_board`
--

DROP TABLE IF EXISTS `gstar_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_board` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `board_type_cd` varchar(20) NOT NULL DEFAULT '1',
  `subject` varchar(90) NOT NULL,
  `contents` text NOT NULL,
  `gstar_user_id` bigint(20) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `youtube_id` varchar(45) DEFAULT NULL,
  `start_dt` datetime DEFAULT NULL,
  `end_dt` datetime DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_board`
--

LOCK TABLES `gstar_board` WRITE;
/*!40000 ALTER TABLE `gstar_board` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_category`
--

DROP TABLE IF EXISTS `gstar_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `categeory` varchar(45) DEFAULT NULL COMMENT '카테고리명',
  `depth` smallint(5) unsigned DEFAULT NULL,
  `parent_category_id` int(10) unsigned DEFAULT NULL COMMENT '상위 카테고리ID',
  PRIMARY KEY (`id`),
  KEY `fk_gstar_category_gstar_category1_idx` (`parent_category_id`),
  CONSTRAINT `fk_gstar_category_gstar_category1` FOREIGN KEY (`parent_category_id`) REFERENCES `gstar_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_category`
--

LOCK TABLES `gstar_category` WRITE;
/*!40000 ALTER TABLE `gstar_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_contents`
--

DROP TABLE IF EXISTS `gstar_contents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_contents` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gstar_user_id` bigint(20) unsigned NOT NULL,
  `subject` varchar(45) DEFAULT NULL COMMENT '제목',
  `url` varchar(255) NOT NULL COMMENT '컨텐츠URL(유투브URL)',
  `memo` varchar(1200) DEFAULT NULL,
  `thumbnail_url` varchar(255) DEFAULT NULL COMMENT '컨텐츠 썸네일 이미지 url',
  `gstar_category_id` int(10) unsigned DEFAULT NULL,
  `gstar_room_id` bigint(20) unsigned DEFAULT NULL,
  `member_type_cd` varchar(20) DEFAULT NULL COMMENT '멤버타입 (1: master, 2: challenge)',
  `status_cd` varchar(45) DEFAULT '1' COMMENT '컨텐츠상태 (1:신규, 2:포기, 3:중지)',
  `div_cd` varchar(45) DEFAULT NULL COMMENT '컨텐츠분류 (1: 사용자컨텐츠, 2: 추천, 3: HOT, 4: 명예의전당)',
  `lyrics` varchar(45) DEFAULT NULL,
  `composition` varchar(45) DEFAULT NULL,
  `arrangement` varchar(45) DEFAULT NULL,
  `s3key` varchar(200) DEFAULT NULL,
  `locale` varchar(10) DEFAULT 'ko',
  `order_seq` smallint(6) DEFAULT NULL COMMENT '정렬순서',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `create_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_GSTAR_CONTENTS_gstar_contents_idx` (`gstar_user_id`),
  KEY `fk_gstar_contents_gstar_category1_idx` (`gstar_category_id`),
  KEY `fk_gstar_contents_gstar_team1_idx` (`gstar_room_id`),
  CONSTRAINT `fk_gstar_contents_gstar_category1` FOREIGN KEY (`gstar_category_id`) REFERENCES `gstar_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_GSTAR_CONTENTS_gstar_contents` FOREIGN KEY (`gstar_user_id`) REFERENCES `gstar_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_contents_gstar_team1` FOREIGN KEY (`gstar_room_id`) REFERENCES `gstar_room` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_contents`
--

LOCK TABLES `gstar_contents` WRITE;
/*!40000 ALTER TABLE `gstar_contents` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_contents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_contents_tags`
--

DROP TABLE IF EXISTS `gstar_contents_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_contents_tags` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gstar_contents_id` bigint(20) unsigned NOT NULL,
  `gstar_hash_tag_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gstar_contents_tags_gstar_contents1_idx` (`gstar_contents_id`),
  KEY `fk_gstar_contents_tags_gstar_hash_tag1_idx` (`gstar_hash_tag_id`),
  CONSTRAINT `fk_gstar_contents_tags_gstar_contents1` FOREIGN KEY (`gstar_contents_id`) REFERENCES `gstar_contents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_contents_tags_gstar_hash_tag1` FOREIGN KEY (`gstar_hash_tag_id`) REFERENCES `gstar_hash_tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_contents_tags`
--

LOCK TABLES `gstar_contents_tags` WRITE;
/*!40000 ALTER TABLE `gstar_contents_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_contents_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_contents_warn`
--

DROP TABLE IF EXISTS `gstar_contents_warn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_contents_warn` (
  `gstar_user_id` bigint(20) unsigned NOT NULL,
  `gstar_contents_id` bigint(20) unsigned NOT NULL,
  `warn_memo` varchar(150) DEFAULT NULL,
  `warn_type_cd` varchar(20) DEFAULT NULL,
  `warn_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`gstar_user_id`,`gstar_contents_id`),
  KEY `fk_gstar_contents_warn_gstar_user1_idx` (`gstar_user_id`),
  KEY `fk_gstar_contents_warn_gstar_contents1_idx` (`gstar_contents_id`),
  CONSTRAINT `fk_gstar_contents_warn_gstar_contents1` FOREIGN KEY (`gstar_contents_id`) REFERENCES `gstar_contents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_contents_warn_gstar_user1` FOREIGN KEY (`gstar_user_id`) REFERENCES `gstar_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_contents_warn`
--

LOCK TABLES `gstar_contents_warn` WRITE;
/*!40000 ALTER TABLE `gstar_contents_warn` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_contents_warn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_hash_tag`
--

DROP TABLE IF EXISTS `gstar_hash_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_hash_tag` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(30) NOT NULL,
  `cnt` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag_UNIQUE` (`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_hash_tag`
--

LOCK TABLES `gstar_hash_tag` WRITE;
/*!40000 ALTER TABLE `gstar_hash_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_hash_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_info`
--

DROP TABLE IF EXISTS `gstar_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gstar_contents_id` bigint(20) unsigned NOT NULL COMMENT '컨텐츠ID',
  `victory_cnt` smallint(5) unsigned DEFAULT NULL COMMENT '우승횟수',
  `warn_cnt` int(10) unsigned DEFAULT NULL COMMENT '신고횟수',
  `point_cnt` bigint(20) unsigned DEFAULT NULL COMMENT '포인트(하트)수',
  `view_cnt` bigint(20) unsigned DEFAULT NULL COMMENT '조회수',
  `update_dt` datetime DEFAULT NULL COMMENT '업데이트 일시',
  PRIMARY KEY (`id`),
  KEY `fk_gstar_info_gstar_contents1_idx` (`gstar_contents_id`),
  CONSTRAINT `fk_gstar_info_gstar_contents1` FOREIGN KEY (`gstar_contents_id`) REFERENCES `gstar_contents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_info`
--

LOCK TABLES `gstar_info` WRITE;
/*!40000 ALTER TABLE `gstar_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_passreset_token`
--

DROP TABLE IF EXISTS `gstar_passreset_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_passreset_token` (
  `gstar_account_id` bigint(20) unsigned NOT NULL,
  `token` varchar(45) NOT NULL,
  `expire_dt` datetime NOT NULL,
  `create_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`gstar_account_id`),
  KEY `fk_gstar_passreset_token_gstar_account1_idx` (`gstar_account_id`),
  CONSTRAINT `fk_gstar_passreset_token_gstar_account1` FOREIGN KEY (`gstar_account_id`) REFERENCES `gstar_account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_passreset_token`
--

LOCK TABLES `gstar_passreset_token` WRITE;
/*!40000 ALTER TABLE `gstar_passreset_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_passreset_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_point`
--

DROP TABLE IF EXISTS `gstar_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_point` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `point_name` varchar(90) DEFAULT NULL,
  `point` int(11) DEFAULT NULL COMMENT '포인트수',
  `price` int(11) DEFAULT NULL COMMENT '포인트 가격',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_point`
--

LOCK TABLES `gstar_point` WRITE;
/*!40000 ALTER TABLE `gstar_point` DISABLE KEYS */;
INSERT INTO `gstar_point` VALUES (1,'잼 10개',10,1100),(2,'잼 30개',30,3300),(3,'잼 50개',50,5500),(4,'잼 100개',100,11000),(5,'잼 300개',300,33000),(6,'잼 500개',500,55000),(90,'잼 1개 (가입선물)',1,0);
/*!40000 ALTER TABLE `gstar_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_point_history`
--

DROP TABLE IF EXISTS `gstar_point_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_point_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gstar_user_id` bigint(20) unsigned NOT NULL,
  `gstar_contents_id` bigint(20) unsigned NOT NULL,
  `gstar_user_point_id` bigint(20) unsigned NOT NULL,
  `use_point` int(11) NOT NULL COMMENT '사용포인트',
  `use_dt` datetime NOT NULL COMMENT '사용일시',
  `status_cd` varchar(20) NOT NULL DEFAULT '1' COMMENT '포인트상태(1:정상사용, 2:지급완료, 3:사용취소)',
  `gstar_point_payment_id` int(10) unsigned DEFAULT NULL COMMENT '지급내역ID',
  PRIMARY KEY (`id`),
  KEY `fk_gstar_point_history_gstar_contents1_idx` (`gstar_contents_id`),
  KEY `fk_gstar_point_history_gstar_user1_idx` (`gstar_user_id`),
  KEY `fk_gstar_point_history_gstar_user_point1_idx` (`gstar_user_point_id`),
  KEY `fk_gstar_point_history_gstar_point_payment1_idx` (`gstar_point_payment_id`),
  CONSTRAINT `fk_gstar_point_history_gstar_contents1` FOREIGN KEY (`gstar_contents_id`) REFERENCES `gstar_contents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_point_history_gstar_point_payment1` FOREIGN KEY (`gstar_point_payment_id`) REFERENCES `gstar_point_payment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_point_history_gstar_user1` FOREIGN KEY (`gstar_user_id`) REFERENCES `gstar_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_point_history_gstar_user_point1` FOREIGN KEY (`gstar_user_point_id`) REFERENCES `gstar_user_point` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_point_history`
--

LOCK TABLES `gstar_point_history` WRITE;
/*!40000 ALTER TABLE `gstar_point_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_point_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_point_payment`
--

DROP TABLE IF EXISTS `gstar_point_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_point_payment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gstar_user_id` bigint(20) unsigned DEFAULT NULL COMMENT '지급 대상 사용자ID',
  `user_name` varchar(60) DEFAULT NULL COMMENT '예금주명',
  `gstar_contents_id` bigint(20) unsigned NOT NULL COMMENT '지금대상 컨텐츠id',
  `point` int(11) DEFAULT NULL COMMENT '지급 쨈(포인트) 합계',
  `deposit_ammount` int(11) NOT NULL COMMENT '입금액(지급액)',
  `bank_name` varchar(60) DEFAULT NULL COMMENT '은행명',
  `bank_account` varchar(45) DEFAULT NULL COMMENT '은행계좌',
  `deposit_user_name` varchar(60) DEFAULT NULL COMMENT '입금자명',
  `deposit_dt` datetime DEFAULT NULL COMMENT '입금일시',
  `create_dt` datetime DEFAULT NULL COMMENT '생성일시',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '생성자ID',
  PRIMARY KEY (`id`),
  KEY `fk_gstar_point_payment_gstar_user1_idx` (`gstar_user_id`),
  KEY `fk_gstar_point_payment_gstar_contents1_idx` (`gstar_contents_id`),
  CONSTRAINT `fk_gstar_point_payment_gstar_contents1` FOREIGN KEY (`gstar_contents_id`) REFERENCES `gstar_contents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_point_payment_gstar_user1` FOREIGN KEY (`gstar_user_id`) REFERENCES `gstar_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_point_payment`
--

LOCK TABLES `gstar_point_payment` WRITE;
/*!40000 ALTER TABLE `gstar_point_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_point_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_room`
--

DROP TABLE IF EXISTS `gstar_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_room` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `subject` varchar(45) DEFAULT NULL COMMENT '방재목',
  `master_contents_id` bigint(20) unsigned NOT NULL,
  `view_sum` bigint(20) unsigned DEFAULT '0' COMMENT '조회수 총합계',
  `point_sum` bigint(20) unsigned DEFAULT '0' COMMENT '하트수 총합계',
  `battle_status_cd` varchar(20) DEFAULT '1',
  `battle_term` smallint(6) NOT NULL DEFAULT '7',
  `battle_max` smallint(6) DEFAULT NULL COMMENT '대결총횟수',
  `start_dt` datetime DEFAULT NULL COMMENT '배틀 시작일시',
  `end_dt` datetime DEFAULT NULL,
  `battle_seq` smallint(6) DEFAULT '1' COMMENT '배틀 순차수',
  `create_dt` datetime DEFAULT NULL,
  `gstar_week_battle_id` int(10) unsigned DEFAULT NULL,
  `gstar_tab_menu_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gstar_room_gstar_contents1_idx` (`master_contents_id`),
  KEY `fk_gstar_room_gstar_week_battle1_idx` (`gstar_week_battle_id`),
  KEY `fk_gstar_room_gstar_tab_menu1_idx` (`gstar_tab_menu_id`),
  CONSTRAINT `fk_gstar_room_gstar_contents1` FOREIGN KEY (`master_contents_id`) REFERENCES `gstar_contents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_room_gstar_tab_menu1` FOREIGN KEY (`gstar_tab_menu_id`) REFERENCES `gstar_tab_menu` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_room_gstar_week_battle1` FOREIGN KEY (`gstar_week_battle_id`) REFERENCES `gstar_week_battle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_room`
--

LOCK TABLES `gstar_room` WRITE;
/*!40000 ALTER TABLE `gstar_room` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_room_contents`
--

DROP TABLE IF EXISTS `gstar_room_contents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_room_contents` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gstar_room_id` bigint(20) unsigned NOT NULL,
  `gstar_contents_id` bigint(20) unsigned NOT NULL,
  `start_dt` datetime DEFAULT NULL COMMENT '배틀 참여시작일시',
  `end_dt` datetime DEFAULT NULL COMMENT '배틀 참여종료일시',
  PRIMARY KEY (`id`),
  KEY `fk_gstar_room_contents_gstar_room1_idx` (`gstar_room_id`),
  KEY `fk_gstar_room_contents_gstar_contents1_idx` (`gstar_contents_id`),
  CONSTRAINT `fk_gstar_room_contents_gstar_contents1` FOREIGN KEY (`gstar_contents_id`) REFERENCES `gstar_contents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_room_contents_gstar_room1` FOREIGN KEY (`gstar_room_id`) REFERENCES `gstar_room` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_room_contents`
--

LOCK TABLES `gstar_room_contents` WRITE;
/*!40000 ALTER TABLE `gstar_room_contents` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_room_contents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_room_contents_week`
--

DROP TABLE IF EXISTS `gstar_room_contents_week`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_room_contents_week` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gstar_room_id` bigint(20) unsigned NOT NULL,
  `gstar_contents_id` bigint(20) unsigned NOT NULL,
  `battle_seq` int(11) DEFAULT NULL,
  `point_cnt` bigint(20) unsigned DEFAULT '0' COMMENT '하트수(좋아요수)',
  `view_cnt` bigint(20) unsigned DEFAULT '0' COMMENT '조회수',
  PRIMARY KEY (`id`),
  KEY `fk_gstar_room_contents_week_gstar_room1_idx` (`gstar_room_id`),
  KEY `fk_gstar_room_contents_week_gstar_contents1_idx` (`gstar_contents_id`),
  CONSTRAINT `fk_gstar_room_contents_week_gstar_contents1` FOREIGN KEY (`gstar_contents_id`) REFERENCES `gstar_contents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_room_contents_week_gstar_room1` FOREIGN KEY (`gstar_room_id`) REFERENCES `gstar_room` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_room_contents_week`
--

LOCK TABLES `gstar_room_contents_week` WRITE;
/*!40000 ALTER TABLE `gstar_room_contents_week` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_room_contents_week` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_room_week`
--

DROP TABLE IF EXISTS `gstar_room_week`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_room_week` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gstar_room_id` bigint(20) unsigned NOT NULL,
  `battle_seq` int(11) NOT NULL,
  `start_dt` datetime DEFAULT NULL,
  `end_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_gstar_room_week_gstar_room1` (`gstar_room_id`),
  CONSTRAINT `fk_gstar_room_week_gstar_room1` FOREIGN KEY (`gstar_room_id`) REFERENCES `gstar_room` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_room_week`
--

LOCK TABLES `gstar_room_week` WRITE;
/*!40000 ALTER TABLE `gstar_room_week` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_room_week` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_tab_menu`
--

DROP TABLE IF EXISTS `gstar_tab_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_tab_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `menu_type_cd` varchar(20) NOT NULL,
  `order_seq` smallint(6) DEFAULT NULL,
  `hidden` bit(1) NOT NULL DEFAULT b'0',
  `create_dt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_tab_menu`
--

LOCK TABLES `gstar_tab_menu` WRITE;
/*!40000 ALTER TABLE `gstar_tab_menu` DISABLE KEYS */;
INSERT INTO `gstar_tab_menu` VALUES (1,'탭메뉴11','1',1,'\0','2016-09-26 05:32:26'),(2,'탭메뉴22','1',2,'\0','2016-09-26 05:35:10');
/*!40000 ALTER TABLE `gstar_tab_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_user`
--

DROP TABLE IF EXISTS `gstar_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL COMMENT '사용자명',
  `nickname` varchar(60) NOT NULL,
  `email` varchar(30) NOT NULL,
  `gender_cd` varchar(20) DEFAULT NULL COMMENT '성별코드 ( 1: 남자, 2: 여자)',
  `age` smallint(5) unsigned DEFAULT NULL,
  `bank_cmpy_cd` varchar(20) DEFAULT NULL COMMENT '은행사코드',
  `bank_account` varchar(45) DEFAULT NULL COMMENT '은행계좌',
  `fcm_token` varchar(300) DEFAULT NULL,
  `create_dt` datetime DEFAULT NULL COMMENT '생성일시',
  `mobile_noti` bit(1) NOT NULL DEFAULT b'1',
  `email_noti` bit(1) NOT NULL DEFAULT b'1',
  `mobile_app_ver` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_user`
--

LOCK TABLES `gstar_user` WRITE;
/*!40000 ALTER TABLE `gstar_user` DISABLE KEYS */;
INSERT INTO `gstar_user` VALUES (1,'관리자','관리자','jchoi@osci.kr','4',0,'2','3333',NULL,'2016-07-18 15:51:18','','','sdgwt235235');
/*!40000 ALTER TABLE `gstar_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_user_point`
--

DROP TABLE IF EXISTS `gstar_user_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_user_point` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `point` int(11) DEFAULT NULL,
  `gstar_user_id` bigint(20) unsigned NOT NULL,
  `price` int(11) DEFAULT NULL COMMENT '총구매가격',
  `pg_id` varchar(45) DEFAULT NULL COMMENT '결제id',
  `create_dt` datetime DEFAULT NULL COMMENT '구매일자',
  `gstar_point_id` int(10) unsigned NOT NULL,
  `pc_status_cd` varchar(20) DEFAULT NULL COMMENT '구매상태 (1: 정상구매, 2: 환불요청, 3: 환불완료)',
  `cancel_dt` datetime DEFAULT NULL COMMENT '환불완료일자',
  `cancel_req_dt` datetime DEFAULT NULL COMMENT '환불요청일자',
  `cancel_reason` varchar(120) DEFAULT NULL COMMENT '환불사유',
  PRIMARY KEY (`id`),
  KEY `fk_gstar_point_gstar_user1_idx` (`gstar_user_id`),
  KEY `fk_gstar_user_point_gstar_point1_idx` (`gstar_point_id`),
  CONSTRAINT `fk_gstar_point_gstar_user1` FOREIGN KEY (`gstar_user_id`) REFERENCES `gstar_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_user_point_gstar_point1` FOREIGN KEY (`gstar_point_id`) REFERENCES `gstar_point` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

insert into gstar_user_point(id, point, gstar_user_id, price, pg_id, create_dt, gstar_point_id, pc_status_cd) 
values (1, 100000, 1, 0, 'free', '2016-11-29 11:38:16', 1, 1);

--
-- Dumping data for table `gstar_user_point`
--

LOCK TABLES `gstar_user_point` WRITE;
/*!40000 ALTER TABLE `gstar_user_point` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_user_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_victory`
--

DROP TABLE IF EXISTS `gstar_victory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_victory` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gstar_room_id` bigint(20) unsigned NOT NULL,
  `gstar_contents_id` bigint(20) unsigned NOT NULL,
  `victory_dt` datetime DEFAULT NULL COMMENT '우승일자',
  PRIMARY KEY (`id`),
  KEY `fk_gstar_juge_gstar_contents1_idx` (`gstar_contents_id`),
  KEY `fk_gstar_victory_gstar_room1_idx` (`gstar_room_id`),
  CONSTRAINT `fk_gstar_juge_gstar_contents1` FOREIGN KEY (`gstar_contents_id`) REFERENCES `gstar_contents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_victory_gstar_room1` FOREIGN KEY (`gstar_room_id`) REFERENCES `gstar_room` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_victory`
--

LOCK TABLES `gstar_victory` WRITE;
/*!40000 ALTER TABLE `gstar_victory` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_victory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_view`
--

DROP TABLE IF EXISTS `gstar_view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_view` (
  `gstar_user_id` bigint(20) unsigned NOT NULL,
  `gstar_contents_id` bigint(20) unsigned NOT NULL,
  `view_cnt` bigint(20) unsigned DEFAULT NULL COMMENT '조회 횟수',
  PRIMARY KEY (`gstar_user_id`,`gstar_contents_id`),
  KEY `fk_gstar_like_gstar_user1_idx` (`gstar_user_id`),
  KEY `fk_gstar_like_gstar_contents1_idx` (`gstar_contents_id`),
  CONSTRAINT `fk_gstar_like_gstar_contents1` FOREIGN KEY (`gstar_contents_id`) REFERENCES `gstar_contents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gstar_like_gstar_user1` FOREIGN KEY (`gstar_user_id`) REFERENCES `gstar_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_view`
--

LOCK TABLES `gstar_view` WRITE;
/*!40000 ALTER TABLE `gstar_view` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gstar_week_battle`
--

DROP TABLE IF EXISTS `gstar_week_battle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gstar_week_battle` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tag` varchar(30) NOT NULL,
  `status_cd` varchar(20) DEFAULT '1' COMMENT '주간배틀상태(1:진행중, 2: 중지, 3: 종료)',
  `battle_seq` int(11) DEFAULT '1' COMMENT '배틀 주차수',
  `start_dt` datetime DEFAULT NULL COMMENT '배틀 시작일시',
  `end_dt` datetime DEFAULT NULL COMMENT '배틀 종료일시',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gstar_week_battle`
--

LOCK TABLES `gstar_week_battle` WRITE;
/*!40000 ALTER TABLE `gstar_week_battle` DISABLE KEYS */;
/*!40000 ALTER TABLE `gstar_week_battle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-13 18:22:43
