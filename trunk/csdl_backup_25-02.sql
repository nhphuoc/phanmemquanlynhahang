/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.25a : Database - vttu_restaurant
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vttu_restaurant` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `vttu_restaurant`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_staff_id` int(11) DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_username` (`username`),
  KEY `id_staff_id` (`id_staff_id`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`id_staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `account` */

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` bit(1) DEFAULT NULL,
  `birthDay` date DEFAULT NULL,
  `phone` varchar(13) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `customer` */

insert  into `customer`(`id`,`name`,`sex`,`birthDay`,`phone`,`address`,`email`,`isActive`) values (1,'Nguyễn Hữu Phước','','1995-07-20','0977941517','Kiên Giang','it.nhphuoc@gmail.com',''),(2,'Trần Thanh Bình','','1991-09-09','0978678567','Hậu Giang','ttbinh@gmail.com',''),(3,'Huỳnh Lê','\0','1993-09-08',NULL,NULL,NULL,''),(4,'Nguyễn Phương Khánh','',NULL,'01699907500',NULL,NULL,'');

/*Table structure for table `discount` */

DROP TABLE IF EXISTS `discount`;

CREATE TABLE `discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `value` int(11) DEFAULT NULL,
  `detail` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `discount` */

/*Table structure for table `discount_detail` */

DROP TABLE IF EXISTS `discount_detail`;

CREATE TABLE `discount_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_invoice_id` int(11) DEFAULT NULL,
  `id_discount_id` int(11) DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_discount_id` (`id_discount_id`),
  KEY `id_invoice_id` (`id_invoice_id`),
  CONSTRAINT `discount_detail_ibfk_1` FOREIGN KEY (`id_discount_id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `discount_detail_ibfk_2` FOREIGN KEY (`id_invoice_id`) REFERENCES `invoice` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `discount_detail` */

/*Table structure for table `invoice` */

DROP TABLE IF EXISTS `invoice`;

CREATE TABLE `invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_table_reservation_id` int(11) DEFAULT NULL,
  `id_staff_id` int(11) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cost` int(11) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_table_reservation_id` (`id_table_reservation_id`),
  KEY `id_staff_id` (`id_staff_id`),
  CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`id_table_reservation_id`) REFERENCES `table_reservation` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `invoice_ibfk_2` FOREIGN KEY (`id_staff_id`) REFERENCES `staff` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `invoice` */

insert  into `invoice`(`id`,`id_table_reservation_id`,`id_staff_id`,`date`,`cost`,`discount`,`note`,`isActive`) values (30,33,1,'2014-02-25 12:06:42',423000,0,'',''),(31,34,1,'2014-02-25 12:07:37',1664200,15800,'',''),(32,35,1,'2014-02-25 12:08:49',45000,0,'',''),(33,36,1,'2014-02-25 12:10:00',69000,0,'','');

/*Table structure for table `schedule` */

DROP TABLE IF EXISTS `schedule`;

CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `beginDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `schedule` */

/*Table structure for table `service` */

DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `store` int(11) DEFAULT NULL,
  `detail` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` text COLLATE utf8_unicode_ci,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  CONSTRAINT `service_ibfk_1` FOREIGN KEY (`type`) REFERENCES `service_type` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service` */

insert  into `service`(`id`,`name`,`type`,`store`,`detail`,`image`,`isActive`) values (1,'Cocacola',2,NULL,NULL,NULL,''),(2,'Pepsi',2,NULL,NULL,NULL,''),(3,'Bò Nướng',2,NULL,NULL,NULL,''),(4,'Trà Xanh',2,NULL,NULL,NULL,''),(5,'Bia Tiger',1,NULL,NULL,NULL,''),(6,'Bia Heniken',1,NULL,NULL,NULL,''),(7,'Nước Suối',2,NULL,NULL,NULL,''),(8,'Cafe',2,NULL,NULL,NULL,''),(9,'Bia 333',1,NULL,NULL,NULL,''),(10,'Lẩu Thái',5,NULL,NULL,NULL,'');

/*Table structure for table `service_cost` */

DROP TABLE IF EXISTS `service_cost`;

CREATE TABLE `service_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_service_id` int(11) DEFAULT NULL,
  `beginDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cost` int(11) DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_service_id` (`id_service_id`),
  CONSTRAINT `service_cost_ibfk_1` FOREIGN KEY (`id_service_id`) REFERENCES `service` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service_cost` */

insert  into `service_cost`(`id`,`id_service_id`,`beginDate`,`cost`,`isActive`) values (10,1,'2014-01-26 19:34:06',10000,''),(11,2,'2014-01-26 19:34:06',12000,''),(12,3,'2014-01-26 19:34:06',9000,''),(16,4,'2014-02-16 22:17:53',9000,''),(17,5,'2014-02-16 22:18:00',14000,''),(18,6,'2014-02-16 22:18:11',18000,''),(19,7,'2014-02-16 22:18:22',8000,''),(20,8,'2014-02-16 22:18:30',5000,''),(21,9,'2014-02-16 22:18:40',9000,''),(22,10,'2014-02-16 22:18:47',120000,''),(23,1,'2014-02-17 07:40:01',99999,''),(24,2,'2014-02-17 07:42:18',88888,''),(25,3,'2014-02-17 07:42:56',77777,''),(27,6,'2014-02-17 15:21:25',20000,''),(29,4,'2014-02-17 15:25:18',120000,''),(30,2,'2014-02-20 18:46:22',12000,'');

/*Table structure for table `service_type` */

DROP TABLE IF EXISTS `service_type`;

CREATE TABLE `service_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service_type` */

insert  into `service_type`(`id`,`name`,`isActive`) values (1,'Bia',''),(2,'Nước Ngọt',''),(3,'Thức Ăn',''),(4,'Phục Vụ',''),(5,'Lẩu',''),(6,'Nướng',''),(7,'Xào',''),(8,'Chiên','');

/*Table structure for table `shift` */

DROP TABLE IF EXISTS `shift`;

CREATE TABLE `shift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_workday_id` int(11) DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `beginHour` time DEFAULT NULL,
  `endHour` time DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_workday_id` (`id_workday_id`),
  CONSTRAINT `shift_ibfk_1` FOREIGN KEY (`id_workday_id`) REFERENCES `workday` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `shift` */

/*Table structure for table `shift_detail` */

DROP TABLE IF EXISTS `shift_detail`;

CREATE TABLE `shift_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_staff_id` int(11) DEFAULT NULL,
  `id_shift_id` int(11) DEFAULT NULL,
  `isAcitve` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_shift_id` (`id_shift_id`),
  KEY `id_staff_id` (`id_staff_id`),
  CONSTRAINT `shift_detail_ibfk_1` FOREIGN KEY (`id_shift_id`) REFERENCES `shift` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `shift_detail_ibfk_2` FOREIGN KEY (`id_staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `shift_detail` */

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` bit(1) DEFAULT NULL,
  `birthDay` date DEFAULT NULL,
  `phone` varchar(13) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pin` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `staff` */

insert  into `staff`(`id`,`name`,`sex`,`birthDay`,`phone`,`address`,`email`,`pin`,`isActive`) values (1,'Nguyễn Hữu Phước','','2013-06-11','0977941517','Hà Tiên Kiên Giang','it.nhphuoc@gmail.com',NULL,'');

/*Table structure for table `table` */

DROP TABLE IF EXISTS `table`;

CREATE TABLE `table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  `numOfChair` tinyint(4) DEFAULT '10',
  `status` tinyint(4) DEFAULT '0',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  KEY `location` (`location`),
  CONSTRAINT `table_ibfk_1` FOREIGN KEY (`type`) REFERENCES `table_type` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `table_ibfk_2` FOREIGN KEY (`location`) REFERENCES `table_location` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table` */

insert  into `table`(`id`,`name`,`type`,`location`,`numOfChair`,`status`,`isActive`) values (1,'Bàn 01',1,1,10,0,''),(2,'Bàn 02',1,1,10,0,''),(3,'Bàn 03',2,2,10,0,''),(4,'Bàn 04',2,2,10,0,''),(5,'Bàn 05',1,2,10,0,''),(6,'Bàn 06',3,2,10,0,''),(7,'Bàn 07',2,2,10,0,''),(8,'Bàn 08',2,2,10,0,''),(9,'Bàn 09',3,3,10,0,''),(10,'Bàn 10',1,3,10,0,''),(11,'Bàn 11',3,2,10,1,''),(12,'Bàn 12',3,2,10,0,''),(13,'Bàn 13',2,1,10,0,''),(14,'Bàn 14',2,1,10,0,''),(15,'Bàn 15',2,2,10,0,''),(16,'Bàn 16',1,1,10,0,''),(17,'Bàn 17',2,2,10,0,''),(18,'Bàn 18',2,2,10,0,''),(19,'Bàn 19',2,2,10,0,''),(20,'Bàn 20',2,2,10,0,''),(21,'Bàn 21',2,2,10,2,''),(22,'Bàn 22',3,3,10,0,''),(23,'Bàn 23',2,2,10,0,''),(24,'Bàn 24',2,1,10,0,''),(25,'Bàn 25',2,2,10,0,''),(26,'Bàn 26',1,1,10,0,''),(27,'Bàn 27',2,2,10,0,''),(28,'Bàn 28',1,1,10,0,''),(29,'Bàn 29',1,1,10,0,''),(30,'Bàn 30',2,3,10,0,''),(31,'Bàn 01',1,1,10,0,''),(32,'Bàn 31',1,2,10,0,''),(33,'Bàn 32',1,1,10,0,''),(34,'Bàn 33',1,2,10,0,''),(35,'Bàn 35',2,2,10,0,''),(36,'Bàn 36',2,2,10,0,''),(37,'Bàn 37',2,2,10,0,''),(38,'Bàn 38',1,1,10,0,''),(39,'Bàn 39',1,1,10,0,''),(40,'Bàn 40',2,2,10,0,''),(41,'Bàn 41',1,1,10,0,''),(42,'Bàn 42',2,2,10,0,''),(43,'Bàn 43',1,1,10,0,''),(44,'Bàn 44',1,1,10,0,''),(45,'Bàn 45',1,1,10,0,''),(46,'Bàn 46',1,1,10,0,''),(47,'Bàn 47',1,1,10,0,''),(48,'Bàn 48',2,2,10,0,''),(49,'Bàn 49',2,2,10,0,''),(50,'Bàn 50',2,1,10,0,''),(51,'Bàn 51',1,1,10,0,''),(52,'Bàn 52',1,1,10,0,''),(53,'Bàn 53',1,1,10,0,''),(54,'Bàn 54',2,2,10,0,''),(55,'Bàn 55',2,2,10,0,''),(56,'Bàn 56',2,2,10,0,''),(57,'Bàn 57',2,2,10,0,''),(58,'Bàn 58',2,2,10,0,''),(59,'Bàn 59',2,1,10,0,''),(60,'Bàn 60',1,1,10,0,''),(61,'Bàn 61`',1,1,10,0,'');

/*Table structure for table `table_location` */

DROP TABLE IF EXISTS `table_location`;

CREATE TABLE `table_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `detail` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_location` */

insert  into `table_location`(`id`,`name`,`detail`,`isActive`) values (1,'Tầng 1',NULL,''),(2,'Tầng 2',NULL,''),(3,'Tầng 2',NULL,'');

/*Table structure for table `table_reservation` */

DROP TABLE IF EXISTS `table_reservation`;

CREATE TABLE `table_reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_customer_id` int(11) DEFAULT NULL,
  `beginDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` bit(1) DEFAULT b'1',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_customer_id` (`id_customer_id`),
  CONSTRAINT `table_reservation_ibfk_1` FOREIGN KEY (`id_customer_id`) REFERENCES `customer` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_reservation` */

insert  into `table_reservation`(`id`,`id_customer_id`,`beginDate`,`endDate`,`status`,`isActive`) values (33,NULL,'2014-02-25 12:06:42','2014-02-25 12:06:42','',''),(34,NULL,'2014-02-25 12:07:37','2014-02-25 12:07:37','',''),(35,NULL,'2014-02-25 12:08:49','2014-02-25 12:08:49','',''),(36,NULL,'2014-02-25 12:10:00','2014-02-25 12:10:00','',''),(37,2,'2014-02-28 08:40:00','0000-00-00 00:00:00','\0',''),(38,1,'2014-03-01 21:40:00','0000-00-00 00:00:00','\0',''),(39,3,'2014-03-03 23:55:00','0000-00-00 00:00:00','\0',''),(40,NULL,'2014-02-25 14:02:42','0000-00-00 00:00:00','',''),(41,3,'2014-02-28 22:50:00','0000-00-00 00:00:00','\0','');

/*Table structure for table `table_reservation_detail` */

DROP TABLE IF EXISTS `table_reservation_detail`;

CREATE TABLE `table_reservation_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_table_id` int(11) DEFAULT NULL,
  `id_table_reservation_id` int(11) DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_table_reservation_id` (`id_table_reservation_id`),
  KEY `id_table_id` (`id_table_id`),
  CONSTRAINT `table_reservation_detail_ibfk_1` FOREIGN KEY (`id_table_reservation_id`) REFERENCES `table_reservation` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `table_reservation_detail_ibfk_2` FOREIGN KEY (`id_table_id`) REFERENCES `table` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_reservation_detail` */

insert  into `table_reservation_detail`(`id`,`id_table_id`,`id_table_reservation_id`,`isActive`) values (33,14,33,''),(34,14,34,''),(35,14,35,''),(36,14,36,''),(37,11,37,''),(38,11,38,''),(39,11,39,''),(40,11,40,''),(41,21,41,'');

/*Table structure for table `table_service` */

DROP TABLE IF EXISTS `table_service`;

CREATE TABLE `table_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_table_reservation_id` int(11) DEFAULT NULL,
  `id_service_id` int(11) DEFAULT NULL,
  `id_service_cost_id` int(11) DEFAULT NULL,
  `number` tinyint(4) DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` bit(1) DEFAULT b'1',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_service_id` (`id_service_id`),
  KEY `id_table_service_id` (`id_table_reservation_id`),
  CONSTRAINT `table_service_ibfk_2` FOREIGN KEY (`id_service_id`) REFERENCES `service` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `table_service_ibfk_3` FOREIGN KEY (`id_table_reservation_id`) REFERENCES `table_reservation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_service` */

insert  into `table_service`(`id`,`id_table_reservation_id`,`id_service_id`,`id_service_cost_id`,`number`,`note`,`status`,`isActive`) values (31,33,4,9000,27,NULL,'\0',''),(32,33,2,12000,9,NULL,'\0',''),(33,33,7,8000,9,NULL,'\0',''),(34,34,4,120000,9,NULL,'\0',''),(35,35,4,9000,5,NULL,'\0',''),(36,36,5,14000,3,NULL,'\0',''),(37,36,4,9000,3,NULL,'\0',''),(38,37,5,14000,6,NULL,'','');

/*Table structure for table `table_type` */

DROP TABLE IF EXISTS `table_type`;

CREATE TABLE `table_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_type` */

insert  into `table_type`(`id`,`name`,`isActive`) values (1,'Vip',''),(2,'Medium',''),(3,'Special','');

/*Table structure for table `workday` */

DROP TABLE IF EXISTS `workday`;

CREATE TABLE `workday` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_schedule_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_schedule_id` (`id_schedule_id`),
  CONSTRAINT `workday_ibfk_1` FOREIGN KEY (`id_schedule_id`) REFERENCES `schedule` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `workday` */

/* Procedure structure for procedure `abc` */

/*!50003 DROP PROCEDURE IF EXISTS  `abc` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `abc`(IN ngay int)
BEGIN
	SELECT *
	FROM `table` AS tb
	WHERE tb.id NOT IN(
	SELECT tb.id
	FROM `table` AS tb,table_reservation_detail AS tb_r_d,table_reservation tb_r
	WHERE tb.id=tb_r_d.id_table_id AND tb_r_d.id_table_reservation_id=tb_r.id
	AND tb_r.status=FALSE AND
	DAY(begindate)=ngay AND MONTH(begindate)=2 AND YEAR(begindate)=2014);
END */$$
DELIMITER ;

/* Procedure structure for procedure `customer_get_limit` */

/*!50003 DROP PROCEDURE IF EXISTS  `customer_get_limit` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `customer_get_limit`()
BEGIN
	
	SELECT 
	id as 'Mã',
	name as 'Họ Tên',
	phone as 'Điện Thoại',
	address as 'Địa Chỉ'
	FROM customer
	where isActive=TRUE
	order by id desc;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `customer_search_name_phone` */

/*!50003 DROP PROCEDURE IF EXISTS  `customer_search_name_phone` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `customer_search_name_phone`(IN search text)
BEGIN
	SELECT 
	id AS 'Mã',
	NAME AS 'Họ Tên',
	phone AS 'Điện Thoại',
	address AS 'Địa Chỉ'
	FROM customer
	WHERE isActive=TRUE and customer.name LIKE CONCAT('%',search,'%') or customer.phone LIKE CONCAT('%',search,'%')
	ORDER BY id DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `invoice_insert_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `invoice_insert_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `invoice_insert_all`(in id_reservation int, in id_staff int, in _cost int, in _discount int, in _note text)
BEGIN
	insert into invoice(id_table_reservation_id,id_staff_id,`date`,cost,discount,note)
	values(id_reservation,id_staff,now(),_cost,_discount,_note);
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_cost_insert` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_cost_insert` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_cost_insert`(IN sv_id int, IN sv_cost INT)
BEGIN
	
	insert into service_cost(service_cost.id_service_id,service_cost.cost)
	values(sv_id,sv_cost);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `service_getAll` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_getAll` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_getAll`()
BEGIN
	SELECT 
		sv.id as 'Mã Dịch Vụ',
		sv.name as 'Tên Dịch Vụ',		
		sv_cost.cost as 'Đơn Giá'
	FROM 
		service sv, 
		service_cost sv_cost, 
		service_type sv_type
	WHERE 
		sv.id=sv_cost.id_service_id AND
		sv.type=sv_type.id AND
		sv.isActive=TRUE AND
		sv_cost.isActive=TRUE AND
		sv_type.isActive=TRUE
	ORDER BY sv.id desc;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_search_byName` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_search_byName` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_search_byName`(IN search Varchar(20))
BEGIN
	SELECT 
		sv.id AS 'Mã Dịch Vụ',
		sv.name AS 'Tên Dịch Vụ',		
		sv_cost.cost,0 AS 'Đơn Giá'
	FROM 
		service sv, 
		service_cost sv_cost, 
		service_type sv_type
	WHERE 
		sv.name LIKE concat('%',search,'%') AND
		sv.id=sv_cost.id_service_id AND
		sv.type=sv_type.id AND
		sv.isActive=TRUE AND
		sv_cost.isActive=TRUE AND
		sv_type.isActive=TRUE
	ORDER BY sv.id DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_type_getAll` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_type_getAll` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_type_getAll`()
BEGIN
	SELECT * FROM `service_type` WHERE `service_type`.`isActive`=TRUE;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `service_type_getByType` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_type_getByType` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_type_getByType`(in id int)
BEGIN
	SELECT
		sv.id as 'Mã Dịch Vụ',
		sv.name AS 'Tên Dịch Vụ',	
		svCost.cost AS 'Giá',
		sv.detail AS 'Chi Tiết'
	FROM 
		service sv LEFT JOIN service_cost svCost ON sv.id=svCost.id_service_id
	WHERE
		sv.isActive=TRUE AND
		sv.type=id;
		
 END */$$
DELIMITER ;

/* Procedure structure for procedure `service_update_name` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_update_name` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_update_name`(IN sv_name varchar(20), in sv_id int)
BEGIN
	
	UPDATE service SET 	
	service.name=sv_name
	WHERE service.id=sv_id;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_getAll` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_getAll` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_getAll`()
BEGIN
	SELECT * FROM `table` where `table`.`isActive`=true;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_getById` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_getById` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_getById`(in `_id` int)
BEGIN
	SELECT 
	table.id,
	table.name AS name_table,
	table.type,
	table.location,
	table.numOfChair,
	table.status,
	table_location.name AS name_location
	FROM `table`,`table_location`
	WHERE table.location=table_location.id AND `table`.id=_id AND `table`.`isActive`=TRUE;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_getByType` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_getByType` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_getByType`(IN `_type` INT)
BEGIN
	SELECT * FROM `table` WHERE `table`.`type`=`_type` AND `table`.`isActive`=TRUE;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_location_getAll` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_location_getAll` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_location_getAll`()
BEGIN
	SELECT * FROM table_location WHERE table_location.isActive=TRUE;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_detail_getMaxID` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_detail_getMaxID` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_detail_getMaxID`()
BEGIN
	SELECT max(id) as id FROM `table_reservation_detail` WHERE isActive=TRUE;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_detail_insert` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_detail_insert` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_detail_insert`(in idTable int, in idTableReservation int)
BEGIN
	INSERT INTO `table_reservation_detail`(id_table_id,id_table_reservation_id)
	VALUES(idTable,idTableReservation);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_getby_IdTable` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_getby_IdTable` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_getby_IdTable`(IN `idTable` INT)
BEGIN
	SELECT  
		table_reservation.id,
		id_customer_id,
		customer.name,
		beginDate
	FROM 
		table_reservation 
		LEFT JOIN table_reservation_detail ON table_reservation.id=table_reservation_detail.id_table_reservation_id
		LEFT JOIN customer ON table_reservation.id_customer_id=customer.id		
	WHERE 
		
		table_reservation_detail.id_table_id=idTable
		AND DATE(endDate)='00-00-00'
		and table_reservation.isActive= TRUE
		and table_reservation_detail.isActive=TRUE
		and customer.isActive=TRUE
		ORDER BY begindate ASC
		limit 0,1;	
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_getMaxID` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_getMaxID` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_getMaxID`()
BEGIN
	SELECT MAX(table_reservation.id) as id FROM table_reservation where
	table_reservation.isActive=TRUE;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_getMaxIdByTable` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_getMaxIdByTable` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_getMaxIdByTable`(IN idtb INT)
BEGIN
	SELECT MAX(id) as m
	FROM table_reservation
	WHERE id_table_id=idtb
	and table_reservation.status=TRUE;		
 END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_getmaxid_by_Reservation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_getmaxid_by_Reservation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_getmaxid_by_Reservation`(in idTable int)
BEGIN
	SELECT MAX(table_reservation.id) FROM table_reservation,table_reservation_detail
	WHERE table_reservation.id=table_reservation_detail.id_table_reservation_id AND
	table_reservation.isActive=TRUE AND table_reservation.status=FALSE AND id_table_id=idTable;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_insert` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_insert` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_insert`(in stt boolean)
BEGIN
	insert into `table_reservation`(`status`)
	values(stt);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_insert_customerid` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_insert_customerid` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_insert_customerid`(IN stt BOOLEAN, in idCustomer int, in dt text)
BEGIN
	INSERT INTO table_reservation(`status`,id_customer_id,beginDate)
	VALUES(stt,idCustomer,dt);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_select_list_table_reservation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_select_list_table_reservation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_select_list_table_reservation`(IN idTable INT)
BEGIN
	SELECT table_reservation.id
	FROM table_reservation,table_reservation_detail
	WHERE table_reservation_detail.id_table_id=idTable
	AND table_reservation.id=table_reservation_detail.id_table_reservation_id
	and table_reservation.isActive=true
	and table_reservation_detail.isActive=true
	AND DATE(endDate)='00-00-00';
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_update_customer` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_update_customer` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_update_customer`(IN idCustomer INT, in idReservation int)
BEGIN
	
	UPDATE table_reservation SET 	
	table_reservation.id_customer_id=idCustomer,
	table_reservation.beginDate=table_reservation.beginDate	
	WHERE table_reservation.id=idReservation;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_update_enddate` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_update_enddate` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_update_enddate`(IN idReservation INT)
BEGIN
	update table_reservation 
	set table_reservation.endDate=now()
	where table_reservation.id=idReservation;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_update_status` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_update_status` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_update_status`(IN idTable INT)
BEGIN
	UPDATE 
	table_reservation 
	SET table_reservation.status=true 
	WHERE id=(SELECT MAX(id_table_reservation_id) FROM table_reservation_detail WHERE id_table_id=idTable);
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_view_list` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_view_list` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_view_list`()
BEGIN
	SELECT 
	re.id AS 'ID',
	tb.name AS 'Bàn',
	cus.name AS 'Khách Hàng',
	cus.phone AS 'SĐT',
	DATE_FORMAT(re.beginDate,'%H:%i %d/%m/%Y ') AS 'Ngày Nhận'
	FROM 
	table_reservation AS re,
	table_reservation_detail AS re_de,
	`table` AS tb,
	customer AS cus
	WHERE 
	re.id=re_de.id_table_reservation_id 
	AND re_de.id_table_id=tb.id 
	AND re.id_customer_id=cus.id
	and cus.`isActive`=true
	AND re.`isActive`=TRUE
	AND re_de.`isActive`=TRUE
	AND tb.`isActive`=TRUE
	ORDER BY begindate ASC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_count_service_byIdReservation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_count_service_byIdReservation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_count_service_byIdReservation`(IN idService INT, in idReservation int)
BEGIN
	SELECT COUNT(*) AS n
	FROM table_service
	WHERE table_service.id_service_id=idService AND table_service.id_table_reservation_id=idReservation
	and table_service.isActive=TRUE;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_delete` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_delete` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_delete`(IN idTableService INT)
BEGIN
	
	delete from table_service where table_service.id=idTableService;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_detail_delete` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_detail_delete` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_detail_delete`(IN idSer INT)
BEGIN
	UPDATE table_service_detail SET isActive=FALSE WHERE id=idSer;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_detail_getbyReservation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_detail_getbyReservation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_detail_getbyReservation`(IN idReser INT)
BEGIN
	select
	tbservicedetail.id as ID,
	sv.name AS 'Tên Dịch Vụ',
	svCost.cost AS 'Giá',
	tbservicedetail.number AS 'Số Lượng',
	(svCost.cost*tbservicedetail.number) AS 'Thành Tiền'
	FROM
	table_reservation svReser JOIN table_service_detail tbservicedetail ON svReser.id=tbservicedetail.id_table_reservation_id
	JOIN	
	service sv ON sv.id=tbservicedetail.id_service_id LEFT JOIN
	service_cost svCost ON sv.id=svCost.id_service_id
	WHERE	
	table_reservation.isActive=TRUE and
	sv.isActive=TRUE AND	
	svReser.`id`=11 AND
	tbservicedetail.isActive=TRUE;
	
		
 END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_detail_insert` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_detail_insert` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_detail_insert`(IN idTableReservation INT, in idService int, in num int)
BEGIN
	insert into table_service_detail(id_table_reservation_id,id_service_id,number) values(idTableReservation,idService,num);
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_detail_update` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_detail_update` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_detail_update`(in idSer int, IN num INT)
BEGIN
	UPDATE table_service_detail SET number=num WHERE id=idSer;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_getByIdReservation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_getByIdReservation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_getByIdReservation`(IN `idReser` INT)
BEGIN
SELECT
	table_service.id AS 'Mã',
	service.name AS 'Tên Dịch Vụ',
	number AS 'Số Lượng',
	FORMAT(id_service_cost_id,0) AS 'Đơn Giá',
	FORMAT((number*id_service_cost_id),0) AS 'Thành Tiền'
	
FROM 
	service, 
	table_service
WHERE 
	service.isActive=true
	and table_service.isActive=true and
	service.id=table_service.id_service_id AND
	table_service.id_table_reservation_id=idReser
	and table_service.status=TRUE ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_insert` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_insert` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_insert`(IN id_reservation int, in idService int, in num int, in cost int)
BEGIN
	INSERT INTO table_service(id_table_reservation_id,id_service_id,number,id_service_cost_id)
	VALUES(id_reservation,idService,num,cost);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_total_payment` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_total_payment` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_total_payment`(in idReservation int)
BEGIN
	SELECT SUM(id_service_cost_id*number) AS total
	FROM table_service
	WHERE table_service.id_table_reservation_id=idReservation and table_service.status=true
	and table_service.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_update` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_update` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_update`(IN idService INT, in num int, in idReservation int)
BEGIN
	
	update table_service set 	
	number=number+num
	where id_service_id=idService and id_table_reservation_id=idReservation;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_update_number_byidTableService` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_update_number_byidTableService` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_update_number_byidTableService`(IN idTableService INT, IN num INT)
BEGIN
	
	UPDATE table_service SET 	
	number=num
	WHERE table_service.id=idTableService;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_update_status_byIdReservation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_update_status_byIdReservation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_update_status_byIdReservation`(IN idReservation INT)
BEGIN
	UPDATE table_service
	SET table_service.status=FALSE
	WHERE table_service.id_table_reservation_id=idReservation;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_update_status` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_update_status` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_update_status`(IN `idTable` INT, in state int)
BEGIN
	update `table` set `table`.`status`=state where `table`.`id`=idTable;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
