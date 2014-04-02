/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.6.11 : Database - vttu_restaurant
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
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`id_staff_id`) REFERENCES `staff` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `account` */

insert  into `account`(`id`,`id_staff_id`,`username`,`password`,`type`,`isActive`) values (8,3,'nhphuoc','202cb962ac59075b964b07152d234b70',1,'');

/*Table structure for table `client_request` */

DROP TABLE IF EXISTS `client_request`;

CREATE TABLE `client_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_request_id` int(11) DEFAULT NULL,
  `id_table_id` int(11) DEFAULT NULL,
  `status` bit(1) DEFAULT b'0',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_request_id` (`id_request_id`),
  KEY `id_table_id` (`id_table_id`),
  CONSTRAINT `client_request_ibfk_1` FOREIGN KEY (`id_request_id`) REFERENCES `client_request_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `client_request_ibfk_2` FOREIGN KEY (`id_table_id`) REFERENCES `table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `client_request` */

insert  into `client_request`(`id`,`id_request_id`,`id_table_id`,`status`,`date`,`isActive`) values (1,1,114,'','2014-03-30 20:29:19','\0'),(2,1,118,'','2014-03-31 09:15:54','\0');

/*Table structure for table `client_request_info` */

DROP TABLE IF EXISTS `client_request_info`;

CREATE TABLE `client_request_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `client_request_info` */

insert  into `client_request_info`(`id`,`name`) values (1,'Gọi Bàn'),(2,'Thanh Toán'),(3,'Gọi Món');

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` bit(1) DEFAULT NULL,
  `birthDay` date DEFAULT NULL,
  `phone` varchar(13) COLLATE utf8_unicode_ci DEFAULT 'Chưa rõ',
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT 'Chưa rõ',
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT 'Chưa rõ',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `customer` */

insert  into `customer`(`id`,`name`,`sex`,`birthDay`,`phone`,`address`,`email`,`isActive`) values (1,'Nguyễn Văn A','','1991-03-30','0909189789','','','');

/*Table structure for table `discount` */

DROP TABLE IF EXISTS `discount`;

CREATE TABLE `discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `beginDate` timestamp NULL DEFAULT NULL,
  `endDate` timestamp NULL DEFAULT NULL,
  `conditions` int(11) DEFAULT NULL,
  `valueInvoice` int(11) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `detail` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `discount` */

insert  into `discount`(`id`,`name`,`type`,`beginDate`,`endDate`,`conditions`,`valueInvoice`,`value`,`detail`,`isActive`) values (1,'km',0,'2014-03-30 00:28:00','2014-03-30 00:32:00',1,1,1,'','\0'),(2,'nbg',1,'2014-03-30 00:37:00','2014-04-08 00:36:00',1,1,1,'','\0');

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `discount_detail` */

insert  into `discount_detail`(`id`,`id_invoice_id`,`id_discount_id`,`isActive`) values (11,11,2,''),(12,12,2,''),(13,13,2,''),(14,14,2,''),(15,15,2,''),(16,16,2,''),(17,17,2,''),(18,18,2,''),(19,19,2,''),(20,20,2,''),(21,21,2,''),(22,22,2,''),(23,23,2,''),(24,24,2,''),(25,25,2,''),(26,26,2,''),(27,27,2,''),(28,28,2,''),(29,29,2,'');

/*Table structure for table `distributor` */

DROP TABLE IF EXISTS `distributor`;

CREATE TABLE `distributor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `distributor` */

insert  into `distributor`(`id`,`name`,`address`,`phone`,`email`,`isActive`) values (1,'Bình An',NULL,NULL,NULL,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `invoice` */

insert  into `invoice`(`id`,`id_table_reservation_id`,`id_staff_id`,`date`,`cost`,`discount`,`note`,`isActive`) values (7,12,1,'2014-03-30 00:22:40',0,0,'',''),(8,13,1,'2014-03-30 00:23:57',0,0,'',''),(11,15,1,'2014-03-30 00:37:03',79999,1,'',''),(12,16,1,'2014-03-30 00:43:00',79999,1,'',''),(13,17,1,'2014-03-30 00:45:27',79999,1,'',''),(14,18,1,'2014-03-30 00:46:27',79999,1,'',''),(15,14,1,'2014-03-30 00:48:04',79999,1,'',''),(16,19,1,'2014-03-30 00:48:28',79999,1,'',''),(17,20,1,'2014-03-30 00:49:53',79999,1,'',''),(18,21,1,'2014-03-30 00:50:49',79999,1,'',''),(19,22,1,'2014-03-30 00:52:37',79999,1,'',''),(20,23,1,'2014-03-30 00:57:00',79999,1,'',''),(21,24,1,'2014-03-30 01:03:38',79999,1,'',''),(22,25,1,'2014-03-30 01:05:30',79999,1,'',''),(23,26,1,'2014-03-30 01:05:44',79999,1,'',''),(24,27,1,'2014-03-30 01:06:52',79999,1,'',''),(25,28,1,'2014-03-30 01:07:14',399999,1,'',''),(26,29,1,'2014-03-30 17:03:03',79999,1,'',''),(27,31,1,'2014-03-30 19:18:53',79999,1,'',''),(28,32,1,'2014-03-30 19:27:19',79999,1,'',''),(29,33,1,'2014-03-30 19:29:19',79999,1,'',''),(31,34,1,'2014-03-30 19:37:33',80000,0,'',''),(32,40,1,'2014-03-30 19:54:50',80000,0,'',''),(33,41,1,'2014-03-30 19:55:14',80000,0,'',''),(34,36,1,'2014-03-30 20:00:40',160000,0,'',''),(35,39,1,'2014-03-30 20:01:08',80000,0,'',''),(36,43,1,'2014-03-30 20:29:26',0,0,'',''),(37,44,1,'2014-03-31 11:34:03',420000,0,'',''),(38,56,1,'2014-03-31 13:23:11',80000,0,'',''),(39,55,1,'2014-03-31 13:23:41',80000,0,'',''),(40,57,1,'2014-03-31 13:35:27',0,0,'',''),(41,58,1,'2014-03-31 15:24:41',205000,0,'',''),(42,61,1,'2014-03-31 15:25:29',0,0,'',''),(43,62,1,'2014-03-31 16:03:14',125000,0,'','');

/*Table structure for table `password_change` */

DROP TABLE IF EXISTS `password_change`;

CREATE TABLE `password_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `password_change` */

/*Table structure for table `payment_invoice` */

DROP TABLE IF EXISTS `payment_invoice`;

CREATE TABLE `payment_invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `id_staff_id` int(11) DEFAULT NULL,
  `cost` int(11) DEFAULT '0',
  `info` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_staff_id` (`id_staff_id`),
  CONSTRAINT `payment_invoice_ibfk_1` FOREIGN KEY (`id_staff_id`) REFERENCES `staff` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `payment_invoice` */

insert  into `payment_invoice`(`id`,`date`,`id_staff_id`,`cost`,`info`,`note`,`isActive`) values (1,'2014-03-31',1,600000,'Tra luong','','');

/*Table structure for table `raw_material` */

DROP TABLE IF EXISTS `raw_material`;

CREATE TABLE `raw_material` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number` float DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `raw_material` */

insert  into `raw_material`(`id`,`name`,`number`,`isActive`) values (9,'Thịt Bò',65.5,''),(11,'Cá Trê',7.8,''),(12,'Thịt Ba Rọi',54.3,'');

/*Table structure for table `raw_material_invoice` */

DROP TABLE IF EXISTS `raw_material_invoice`;

CREATE TABLE `raw_material_invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `id_staff_id` int(11) DEFAULT NULL,
  `id_istributor_id` int(11) DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_staff_id` (`id_staff_id`),
  KEY `id_istributor_id` (`id_istributor_id`),
  CONSTRAINT `raw_material_invoice_ibfk_1` FOREIGN KEY (`id_staff_id`) REFERENCES `staff` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `raw_material_invoice_ibfk_2` FOREIGN KEY (`id_istributor_id`) REFERENCES `distributor` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `raw_material_invoice` */

insert  into `raw_material_invoice`(`id`,`date`,`id_staff_id`,`id_istributor_id`,`isActive`,`note`) values (5,'2014-03-30',1,1,'',''),(6,'2014-03-30',1,1,'',''),(7,'2014-03-30',1,1,'',''),(8,'2014-03-31',1,1,'',''),(9,'2014-03-31',1,1,'',''),(10,'2014-03-31',1,1,'',''),(11,'2014-03-31',1,1,'','');

/*Table structure for table `raw_material_invoice_detail` */

DROP TABLE IF EXISTS `raw_material_invoice_detail`;

CREATE TABLE `raw_material_invoice_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_raw_material_id` int(11) DEFAULT NULL,
  `id_raw_material_invoice` int(11) DEFAULT NULL,
  `number` float DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_raw_material_id` (`id_raw_material_id`),
  KEY `raw_material_invoice_detail_ibfk_2` (`id_raw_material_invoice`),
  CONSTRAINT `raw_material_invoice_detail_ibfk_1` FOREIGN KEY (`id_raw_material_id`) REFERENCES `raw_material` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `raw_material_invoice_detail_ibfk_2` FOREIGN KEY (`id_raw_material_invoice`) REFERENCES `raw_material_invoice` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `raw_material_invoice_detail` */

insert  into `raw_material_invoice_detail`(`id`,`id_raw_material_id`,`id_raw_material_invoice`,`number`,`cost`,`isActive`) values (6,NULL,5,2,45000,''),(7,12,6,50,35000,''),(8,12,7,5.5,35000,''),(9,11,8,5,45000,''),(10,12,9,1,30000,''),(11,12,10,0.5,30000,''),(12,NULL,11,1,14000,'');

/*Table structure for table `raw_material_unit` */

DROP TABLE IF EXISTS `raw_material_unit`;

CREATE TABLE `raw_material_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_raw_material_id` int(11) DEFAULT NULL,
  `id_unit_id` int(11) DEFAULT NULL,
  `isParentUnit` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `id_unit_id` (`id_unit_id`),
  KEY `id_raw_material_id` (`id_raw_material_id`),
  CONSTRAINT `raw_material_unit_ibfk_2` FOREIGN KEY (`id_raw_material_id`) REFERENCES `raw_material` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `raw_material_unit_ibfk_1` FOREIGN KEY (`id_unit_id`) REFERENCES `unit` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `raw_material_unit` */

/*Table structure for table `recipes` */

DROP TABLE IF EXISTS `recipes`;

CREATE TABLE `recipes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_raw_material_id` int(11) DEFAULT NULL,
  `id_service_id` int(11) DEFAULT NULL,
  `id_raw_material_unit_id` int(11) DEFAULT NULL,
  `number` float DEFAULT NULL,
  `note` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `recipes_ibfk_1` (`id_raw_material_id`),
  KEY `recipes_ibfk_2` (`id_service_id`),
  KEY `id_raw_material_unit_id` (`id_raw_material_unit_id`),
  CONSTRAINT `recipes_ibfk_2` FOREIGN KEY (`id_service_id`) REFERENCES `service` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `recipes_ibfk_1` FOREIGN KEY (`id_raw_material_id`) REFERENCES `raw_material` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `recipes_ibfk_3` FOREIGN KEY (`id_raw_material_unit_id`) REFERENCES `raw_material_unit` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `recipes` */

insert  into `recipes`(`id`,`id_raw_material_id`,`id_service_id`,`id_raw_material_unit_id`,`number`,`note`,`isActive`) values (3,9,5,NULL,500,NULL,''),(4,12,6,NULL,300,NULL,''),(5,11,5,NULL,100,NULL,''),(6,9,6,NULL,200,NULL,'');

/*Table structure for table `restaurant_info` */

DROP TABLE IF EXISTS `restaurant_info`;

CREATE TABLE `restaurant_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(13) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `logo` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `hourReservationNomal` int(11) DEFAULT NULL,
  `hourReservationParty` int(11) DEFAULT NULL,
  `minuteWarning` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `restaurant_info` */

insert  into `restaurant_info`(`id`,`name`,`phone`,`address`,`email`,`logo`,`hourReservationNomal`,`hourReservationParty`,`minuteWarning`) values (1,'Hương Biển','0977941517','Châu Thành A, Hậu Giang','huongbien@gmail.com','images/caicucnautom.jpgw600',2,3,1);

/*Table structure for table `service` */

DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `unit` int(11) DEFAULT NULL,
  `detail` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` text COLLATE utf8_unicode_ci,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  KEY `service_ibfk_2` (`unit`),
  CONSTRAINT `service_ibfk_1` FOREIGN KEY (`type`) REFERENCES `service_type` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `service_ibfk_2` FOREIGN KEY (`unit`) REFERENCES `unit` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service` */

insert  into `service`(`id`,`name`,`type`,`unit`,`detail`,`image`,`isActive`) values (2,'abc',27,NULL,'','images/thit-ga-nau-canh-la-cach.jpg','\0'),(3,'Thịt Bò Nướng',28,NULL,'Thịt Bò hong khói','images/canhmuopnaumam.jpg',''),(4,'abc',27,29,'nsjnjksnbjkdbnsadkj','images/canhmuopnaumam.jpg',''),(5,'Bò Nướng',28,38,'','images/canhmuopnaumam.jpg',''),(6,'ABC',27,46,'','images/banhcanhgioheo.jpg',''),(7,'bkgjkg',27,46,'','images/canhmuopnaumam.jpg','');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service_cost` */

insert  into `service_cost`(`id`,`id_service_id`,`beginDate`,`cost`,`isActive`) values (2,5,'2014-03-29 23:28:16',80000,''),(3,6,'2014-03-31 00:38:17',45000,''),(4,7,'2014-03-31 19:42:47',70000,'');

/*Table structure for table `service_type` */

DROP TABLE IF EXISTS `service_type`;

CREATE TABLE `service_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service_type` */

insert  into `service_type`(`id`,`name`,`isActive`) values (27,'Món Canh',''),(28,'Nướng','');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `staff` */

insert  into `staff`(`id`,`name`,`sex`,`birthDay`,`phone`,`address`,`email`,`pin`,`isActive`) values (1,'Nguyễn Hữu Phước','','1992-11-30','0977941517','Hà Tiên, Kiên Giang','it.nhphuoc@gmail.com','',''),(2,'Trần Bình Minh','','1990-11-30','0977941517','Châu Thành A, Hậu Giang','1051190036@stu.vttu.edu.vn','','\0'),(3,'Huỳnh Lê','\0','1993-09-08',NULL,'Lấp Vò',NULL,NULL,'');

/*Table structure for table `system_log` */

DROP TABLE IF EXISTS `system_log`;

CREATE TABLE `system_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `info` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `system_log` */

insert  into `system_log`(`id`,`info`,`date`) values (25,'service','2014-03-27 15:38:57'),(26,'table','2014-03-27 15:38:59'),(27,'location','2014-03-27 15:39:03'),(28,'table','2014-03-27 19:03:09'),(29,'service','2014-03-27 19:09:34'),(30,'service','2014-03-28 13:00:36'),(31,'service','2014-03-29 12:34:09'),(32,'service','2014-03-29 23:28:16'),(33,'service','2014-03-31 00:38:18'),(34,'service','2014-03-31 19:42:47');

/*Table structure for table `table` */

DROP TABLE IF EXISTS `table`;

CREATE TABLE `table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `location` int(11) DEFAULT NULL,
  `numOfChair` tinyint(4) DEFAULT '10',
  `status` int(4) DEFAULT '1',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  KEY `location` (`location`),
  KEY `status` (`status`),
  CONSTRAINT `table_ibfk_1` FOREIGN KEY (`type`) REFERENCES `table_type` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `table_ibfk_2` FOREIGN KEY (`location`) REFERENCES `table_location` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `table_ibfk_3` FOREIGN KEY (`status`) REFERENCES `table_status` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table` */

insert  into `table`(`id`,`name`,`type`,`location`,`numOfChair`,`status`,`isActive`) values (111,'Bàn 1',1,1,10,1,''),(113,'Bàn 2',1,1,1,1,''),(114,'Bàn 3',1,1,1,1,''),(115,'Bàn 4',1,2,1,1,''),(116,'Bàn 5',1,2,1,1,''),(117,'Bàn 6',1,1,1,1,''),(118,'Bàn 7',1,1,1,2,''),(119,'Bàn 8',1,2,1,1,''),(120,'Bàn 9',1,1,1,3,''),(121,'Bàn 10',1,1,1,1,''),(122,'Bàn 11',1,1,1,1,''),(123,'Bàn 12',1,1,1,1,''),(124,'Bàn 13',1,1,1,1,''),(125,'Bàn 14',1,1,1,1,''),(126,'Bàn 15',1,1,1,1,''),(127,'Bàn 16',1,1,1,1,''),(128,'Bàn 17',1,1,1,1,''),(129,'Bàn 18',1,1,1,1,''),(130,'Bàn 19',1,1,1,1,''),(131,'Bàn 20',1,1,1,1,''),(132,'Bàn 21',1,1,1,1,''),(133,'Bàn 22',1,1,1,1,''),(134,'Bàn 23',1,1,1,1,''),(135,'Bàn 24',1,1,1,1,''),(136,'Bàn 25',2,1,1,1,''),(138,'Bàn 26',2,1,10,1,''),(139,'Bafn 27',1,1,10,1,''),(141,'Bàn 28',1,1,12,1,'');

/*Table structure for table `table_location` */

DROP TABLE IF EXISTS `table_location`;

CREATE TABLE `table_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `detail` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_location` */

insert  into `table_location`(`id`,`name`,`detail`,`isActive`) values (1,'Tầng 1',NULL,''),(2,'Tầng 2',NULL,''),(3,'Tầng 3',NULL,''),(4,'Tầng 4',NULL,''),(5,'Tầng 5',NULL,''),(6,'Tầng 6',NULL,''),(7,'Tầng 7',NULL,''),(8,'Tầng 8',NULL,''),(9,'Tầng 9',NULL,'');

/*Table structure for table `table_reservation` */

DROP TABLE IF EXISTS `table_reservation`;

CREATE TABLE `table_reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_customer_id` int(11) DEFAULT NULL,
  `prepay` int(11) DEFAULT '0',
  `beginDate` timestamp NULL DEFAULT NULL,
  `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` bit(1) DEFAULT b'1',
  `party` bit(1) DEFAULT b'0',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_customer_id` (`id_customer_id`),
  CONSTRAINT `table_reservation_ibfk_1` FOREIGN KEY (`id_customer_id`) REFERENCES `customer` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_reservation` */

insert  into `table_reservation`(`id`,`id_customer_id`,`prepay`,`beginDate`,`endDate`,`status`,`party`,`isActive`) values (12,NULL,0,'2014-03-29 23:14:25','2014-03-30 00:22:40','','\0','\0'),(13,NULL,0,'2014-03-30 00:23:49','2014-03-30 00:23:57','','\0','\0'),(14,NULL,0,'2014-03-30 00:25:26','2014-03-30 00:48:04','','\0','\0'),(15,NULL,0,'2014-03-30 00:27:41','2014-03-30 00:37:03','','\0','\0'),(16,NULL,0,'2014-03-30 00:42:53','2014-03-30 00:43:00','','\0','\0'),(17,NULL,0,'2014-03-30 00:45:20','2014-03-30 00:45:27','','\0','\0'),(18,NULL,0,'2014-03-30 00:46:19','2014-03-30 00:46:27','','\0','\0'),(19,NULL,0,'2014-03-30 00:48:09','2014-03-30 00:48:28','','\0','\0'),(20,NULL,0,'2014-03-30 00:49:44','2014-03-30 00:49:53','','\0','\0'),(21,NULL,0,'2014-03-30 00:50:41','2014-03-30 00:50:49','','\0','\0'),(22,NULL,0,'2014-03-30 00:52:31','2014-03-30 00:52:37','','\0','\0'),(23,NULL,0,'2014-03-30 00:56:54','2014-03-30 00:57:00','','\0','\0'),(24,NULL,0,'2014-03-30 01:03:33','2014-03-30 01:03:38','','\0','\0'),(25,NULL,0,'2014-03-30 01:04:35','2014-03-30 01:05:30','','\0','\0'),(26,NULL,0,'2014-03-30 01:05:39','2014-03-30 01:05:44','','\0','\0'),(27,NULL,0,'2014-03-30 01:06:43','2014-03-30 01:06:52','','\0','\0'),(28,NULL,0,'2014-03-30 01:06:58','2014-03-30 01:07:14','','\0','\0'),(29,NULL,0,'2014-03-30 17:02:41','2014-03-30 17:03:03','','\0','\0'),(30,NULL,0,'2014-03-30 17:03:09','2014-03-30 17:03:15','','\0','\0'),(31,1,0,'2014-03-30 19:17:01','2014-03-30 19:18:53','','\0','\0'),(32,NULL,0,'2014-03-30 19:26:45','2014-03-30 19:27:19','','\0','\0'),(33,NULL,0,'2014-03-30 19:29:08','2014-03-30 19:29:19','','\0','\0'),(34,NULL,0,'2014-03-30 19:29:35','2014-03-30 19:37:33','','\0','\0'),(35,1,500000,'2014-03-30 19:40:00','2014-03-31 11:27:34','\0','\0','\0'),(36,1,100000,'2014-03-30 19:55:54','2014-03-30 20:00:40','','','\0'),(37,1,500000,'2014-03-30 23:39:00','2014-03-30 20:30:09','\0','\0','\0'),(38,1,0,'2014-03-30 20:28:34','2014-03-30 20:28:58','','\0','\0'),(39,1,0,'2014-03-30 20:00:54','2014-03-30 20:01:08','','\0','\0'),(40,NULL,0,'2014-03-30 19:54:37','2014-03-30 19:54:50','','\0','\0'),(41,NULL,0,'2014-03-30 19:55:04','2014-03-30 19:55:14','','\0','\0'),(42,1,0,'2014-03-30 20:13:00','2014-03-30 20:30:05','\0','\0','\0'),(43,NULL,0,'2014-03-30 20:29:20','2014-03-30 20:29:26','','\0','\0'),(44,1,0,'2014-03-31 09:14:04','2014-03-31 11:34:03','','\0','\0'),(45,NULL,0,'2014-03-31 09:15:54','2014-03-31 11:34:07','','\0','\0'),(46,1,0,'2014-04-01 10:20:00','2014-03-31 11:27:54','\0','\0','\0'),(47,1,0,'2014-03-31 10:16:16','2014-03-31 11:27:36','\0','\0','\0'),(48,1,0,'2014-03-31 10:16:59','2014-03-31 11:27:46','\0','\0','\0'),(49,1,500000,'2014-03-31 11:20:00','2014-03-31 11:27:31','\0','','\0'),(50,1,500000,'2014-03-31 10:40:07','2014-03-31 11:27:49','\0','\0','\0'),(51,1,500000,'2014-03-31 12:41:00','2014-03-31 11:27:51','\0','\0','\0'),(52,1,0,'2014-03-31 14:32:00','2014-03-31 12:24:08','\0','\0','\0'),(53,1,0,'2014-03-31 12:37:49','2014-03-31 12:24:15','\0','\0','\0'),(54,1,0,'2014-03-31 11:59:00','2014-03-31 12:24:23','\0','\0','\0'),(55,1,0,'2014-03-31 13:17:55','2014-03-31 13:23:41','','\0','\0'),(56,1,0,'2014-03-31 13:17:57','2014-03-31 13:23:11','','\0','\0'),(57,NULL,0,'2014-03-31 13:35:18','2014-03-31 13:35:27','','\0','\0'),(58,1,0,'2014-03-31 13:40:03','2014-03-31 15:24:41','','\0','\0'),(59,NULL,0,'2014-03-31 13:50:35','0000-00-00 00:00:00','','\0',''),(60,1,0,'2014-04-02 13:50:00','0000-00-00 00:00:00','\0','\0',''),(61,NULL,0,'2014-03-31 15:25:20','2014-03-31 15:25:29','','\0','\0'),(62,NULL,0,'2014-03-31 15:27:19','2014-03-31 16:03:14','','\0','\0'),(63,NULL,0,'2014-03-31 15:44:39','0000-00-00 00:00:00','','\0','');

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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_reservation_detail` */

insert  into `table_reservation_detail`(`id`,`id_table_id`,`id_table_reservation_id`,`isActive`) values (12,123,12,''),(13,120,13,''),(14,123,14,''),(15,126,15,''),(16,126,16,''),(17,126,17,''),(18,129,18,''),(19,122,19,''),(20,126,20,''),(21,123,21,''),(22,125,22,''),(23,126,23,''),(24,126,24,''),(25,124,25,''),(26,123,26,''),(27,123,27,''),(28,126,28,''),(29,117,29,''),(30,117,30,''),(31,116,31,''),(32,120,32,''),(33,126,33,''),(34,123,34,''),(35,123,35,''),(36,111,36,''),(37,113,36,''),(38,114,36,''),(39,117,36,''),(40,117,37,''),(41,113,38,''),(42,113,39,''),(43,122,40,''),(44,125,41,''),(45,126,42,''),(46,114,43,''),(47,116,44,''),(48,118,45,''),(49,119,46,''),(50,119,47,''),(51,128,48,''),(52,111,49,''),(53,113,49,''),(54,114,49,''),(55,120,49,''),(56,126,50,''),(57,126,51,''),(58,111,48,''),(59,116,52,''),(60,119,53,''),(61,122,54,''),(62,113,55,''),(63,116,56,''),(64,125,57,''),(65,117,58,''),(66,111,58,''),(67,120,60,''),(68,126,61,''),(69,123,62,''),(70,118,63,'');

/*Table structure for table `table_service` */

DROP TABLE IF EXISTS `table_service`;

CREATE TABLE `table_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_table_reservation_id` int(11) DEFAULT NULL,
  `id_service_id` int(11) DEFAULT NULL,
  `id_service_cost_id` int(11) DEFAULT NULL,
  `number` tinyint(4) DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` bit(1) DEFAULT b'0' COMMENT 'false chưa phục vụ true đã phục vụ',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_service_id` (`id_service_id`),
  KEY `id_table_service_id` (`id_table_reservation_id`),
  CONSTRAINT `table_service_ibfk_2` FOREIGN KEY (`id_service_id`) REFERENCES `service` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `table_service_ibfk_3` FOREIGN KEY (`id_table_reservation_id`) REFERENCES `table_reservation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_service` */

insert  into `table_service`(`id`,`id_table_reservation_id`,`id_service_id`,`id_service_cost_id`,`number`,`note`,`status`,`isActive`) values (23,12,5,80000,5,NULL,'\0',''),(24,13,5,80000,1,NULL,'\0',''),(25,14,5,80000,1,NULL,'\0',''),(26,15,5,80000,1,NULL,'\0',''),(27,16,5,80000,1,NULL,'\0',''),(28,17,5,80000,1,NULL,'\0',''),(29,18,5,80000,1,NULL,'\0',''),(30,19,5,80000,1,NULL,'\0',''),(31,20,5,80000,1,NULL,'\0',''),(32,21,5,80000,1,NULL,'\0',''),(33,22,5,80000,1,NULL,'\0',''),(34,23,5,80000,1,NULL,'\0',''),(35,24,5,80000,1,NULL,'\0',''),(36,25,5,80000,1,NULL,'\0',''),(37,26,5,80000,1,NULL,'\0',''),(38,27,5,80000,1,NULL,'\0',''),(39,28,5,80000,5,NULL,'\0',''),(40,29,5,80000,1,NULL,'\0',''),(41,31,5,80000,1,NULL,'\0',''),(42,32,5,80000,1,NULL,'\0',''),(43,33,5,80000,1,NULL,'\0',''),(44,34,5,80000,1,NULL,'\0',''),(45,40,5,80000,1,NULL,'\0',''),(46,41,5,80000,1,NULL,'\0',''),(47,35,5,80000,7,NULL,'\0',''),(48,36,5,80000,2,NULL,'\0',''),(49,39,5,80000,1,NULL,'\0',''),(50,35,6,45000,2,NULL,'\0',''),(51,44,5,80000,3,NULL,'',''),(52,44,6,45000,4,NULL,'',''),(53,49,5,80000,1,NULL,'\0',''),(54,49,6,45000,1,NULL,'\0',''),(55,52,5,80000,1,NULL,'\0',''),(57,56,5,80000,1,NULL,'\0',''),(58,55,5,80000,1,NULL,'\0',''),(59,57,5,80000,1,NULL,'\0',''),(60,58,5,80000,2,NULL,'',''),(61,58,6,45000,1,NULL,'\0',''),(64,61,5,80000,1,NULL,'\0',''),(65,61,6,45000,1,NULL,'\0',''),(66,62,5,80000,1,NULL,'',''),(67,62,6,45000,1,NULL,'',''),(68,63,5,80000,2,NULL,'\0',''),(69,63,6,45000,2,NULL,'\0','');

/*Table structure for table `table_status` */

DROP TABLE IF EXISTS `table_status`;

CREATE TABLE `table_status` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_status` */

insert  into `table_status`(`id`,`name`) values (1,'Bàn Đang Trống'),(2,'Bàn Đang Được sử dụn'),(3,'Bàn Được Đặt Trước'),(4,'Cảnh Báo Đến Thời gi'),(5,'Cảnh Báo Bàn Đang Sử');

/*Table structure for table `table_status_detail` */

DROP TABLE IF EXISTS `table_status_detail`;

CREATE TABLE `table_status_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_table_id` int(11) DEFAULT NULL,
  `id_table_status_id` int(11) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id_table_status_id` (`id_table_status_id`),
  KEY `id_table_id` (`id_table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_status_detail` */

/*Table structure for table `table_type` */

DROP TABLE IF EXISTS `table_type`;

CREATE TABLE `table_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_type` */

insert  into `table_type`(`id`,`name`,`isActive`) values (1,'Vip',''),(2,'Trung Bình',''),(3,'Thường',''),(4,'Bình Dân','');

/*Table structure for table `unit` */

DROP TABLE IF EXISTS `unit`;

CREATE TABLE `unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_unit_id` int(11) DEFAULT NULL,
  `cast` int(11) DEFAULT NULL,
  `isParent` bit(1) DEFAULT b'1',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `unit_ibfk_1` (`id_unit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `unit` */

insert  into `unit`(`id`,`name`,`id_unit_id`,`cast`,`isParent`,`isActive`) values (29,'Kg',NULL,NULL,'',''),(30,'gam',29,1000,'',''),(38,'Thùng',NULL,NULL,'',''),(39,'Lon',38,24,'\0',''),(40,'Chai',38,24,'\0',''),(41,'Lít',NULL,NULL,'',''),(42,'ml',41,1000,'',''),(45,'mg',30,1000,'\0',''),(46,'Đĩa',NULL,NULL,'','');

/* Procedure structure for procedure `account_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `account_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `account_add`(
	IN _id INT,
	in _user varchar(50),
	in _pass varchar(50),
	in _type int,
	in _active boolean
	)
BEGIN
	insert into account(
		account.id_staff_id,
		account.username,
		account.password,
		account.type,
		account.isActive)
		
		values(
		_id,
		_user,
		_pass,
		_type,
		_active
		);
END */$$
DELIMITER ;

/* Procedure structure for procedure `account_del` */

/*!50003 DROP PROCEDURE IF EXISTS  `account_del` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `account_del`(
	IN _id INT
	)
BEGIN
	delete from account where account.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `account_del_by_staff` */

/*!50003 DROP PROCEDURE IF EXISTS  `account_del_by_staff` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `account_del_by_staff`(
	IN _id_staff INT
	)
BEGIN
	delete from account where account.id_staff_id=_id_staff;
END */$$
DELIMITER ;

/* Procedure structure for procedure `account_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `account_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `account_get_all`()
BEGIN
	SELECT
		account.id as 'ID',
		staff.name as 'Nhân Viên',
		staff.email as 'Email',
		account.username as 'Tên Đăng Nhập',
		CASE account.type WHEN 0 THEN 'Administrator'
		ELSE 'Nhân Viên' END AS 'Quyền',
		CASE account.isActive WHEN TRUE THEN 'Hoạt Động'
		ELSE 'Không Hoạt Động' END AS 'Trạng Thái',
		account.id_staff_id,
		account.password
	FROM account JOIN staff
	ON account.id_staff_id=staff.id;	
END */$$
DELIMITER ;

/* Procedure structure for procedure `account_login` */

/*!50003 DROP PROCEDURE IF EXISTS  `account_login` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `account_login`(
	IN _user VARCHAR(50),
	IN _pass VARCHAR(50)
	)
BEGIN
	select * from account where account.username=_user
	and account.password=_pass and account.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `account_test_staff` */

/*!50003 DROP PROCEDURE IF EXISTS  `account_test_staff` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `account_test_staff`(
	in _id int
	)
BEGIN
	select count(*) from account where account.id_staff_id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `account_test_user` */

/*!50003 DROP PROCEDURE IF EXISTS  `account_test_user` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `account_test_user`(
	in _user varchar(50)
	)
BEGIN
	select count(*) from account where account.username=_user;
END */$$
DELIMITER ;

/* Procedure structure for procedure `account_update` */

/*!50003 DROP PROCEDURE IF EXISTS  `account_update` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `account_update`(
	IN _id_staff INT,
	in _user varchar(50),
	in _pass varchar(50),
	in _type int,
	in _active boolean,
	in _id int
	)
BEGIN
	update account set
		account.id_staff_id=_id_staff,
		account.username=_user,
		account.password=_pass,
		account.type=_type,
		account.isActive=_active
	where account.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `account_update_pass` */

/*!50003 DROP PROCEDURE IF EXISTS  `account_update_pass` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `account_update_pass`(
	in _user varchar(50),
	in _pass varchar(50)
	)
BEGIN
	update account set
		account.password=_pass
	where account.username=_user;
END */$$
DELIMITER ;

/* Procedure structure for procedure `client_request_accept` */

/*!50003 DROP PROCEDURE IF EXISTS  `client_request_accept` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `client_request_accept`(in _id int, in accept boolean)
BEGIN
update client_request set client_request.status=accept, client_request.isActive=false
where client_request.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `client_request_getAll` */

/*!50003 DROP PROCEDURE IF EXISTS  `client_request_getAll` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `client_request_getAll`()
BEGIN
	SELECT	
	table.id AS 'Mã Bàn',
	table.name AS 'Tên Bàn',
	client_request_info.name AS 'Trạng Thái',
	client_request.id
FROM
	client_request JOIN `table` ON client_request.id_table_id=table.id
	JOIN client_request_info ON client_request.id_request_id=client_request_info.id
WHERE client_request.status=false and client_request.isActive=true 
and client_request.id_request_id<=2;
END */$$
DELIMITER ;

/* Procedure structure for procedure `client_request_get_by_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `client_request_get_by_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `client_request_get_by_id`(in _id int)
BEGIN
select * from client_request where client_request.id=_id and client_request.status=false and client_request.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `client_request_get_max_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `client_request_get_max_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `client_request_get_max_id`()
BEGIN
select 
	max(client_request.id) as id,
	client_request.id_request_id
	from client_request;
END */$$
DELIMITER ;

/* Procedure structure for procedure `customer_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `customer_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `customer_add`(in _name varchar(50),in _sex boolean, in _birtday date, in _phone varchar(13), in _address varchar(100), in _email varchar(100))
BEGIN
	insert into customer(customer.name,customer.sex,customer.birthDay,customer.phone,customer.address,customer.email)
	values(_name,_sex,_birtday,_phone,_address,_email);
END */$$
DELIMITER ;

/* Procedure structure for procedure `customer_count_using_table` */

/*!50003 DROP PROCEDURE IF EXISTS  `customer_count_using_table` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `customer_count_using_table`(IN _id INT)
BEGIN
SELECT COUNT(table_reservation.id_customer_id) AS n
FROM table_reservation
WHERE table_reservation.id_customer_id=_id AND DATE(endDate)='00-00-00';
END */$$
DELIMITER ;

/* Procedure structure for procedure `customer_delete` */

/*!50003 DROP PROCEDURE IF EXISTS  `customer_delete` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `customer_delete`(IN _id INT)
BEGIN
 update customer set customer.isActive=false where customer.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `customer_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `customer_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `customer_get_all`()
BEGIN
		SELECT
	customer.id AS 'ID',
	customer.name AS 'Họ Tên',
	CASE customer.sex WHEN TRUE THEN 'Nam' ELSE 'Nữ' END AS 'Giới Tính',
	date_format(customer.birthDay,'%d/%m/%Y') AS 'Ngày Sinh',	
	ROUND(DATEDIFF(NOW(),customer.birthDay)/360) AS 'Tuổi',
	customer.phone AS 'Điện Thoại',
	customer.address AS 'Địa Chỉ',
	customer.email AS 'Email'
FROM customer WHERE customer.isActive=TRUE
order by customer.id desc
;
END */$$
DELIMITER ;

/* Procedure structure for procedure `customer_get_by_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `customer_get_by_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `customer_get_by_id`(IN _id INT)
BEGIN
	select customer.id,customer.name
	from customer
	where customer.id=_id
	and customer.isActive=true;
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

/* Procedure structure for procedure `customer_search_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `customer_search_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `customer_search_get_all`(IN search VARCHAR(50))
BEGIN
	SELECT
	customer.id AS 'ID',
	customer.name AS 'Họ Tên',
	CASE customer.sex WHEN TRUE THEN 'Nam' ELSE 'Nữ' END AS 'Giới Tính',
	DATE_FORMAT(customer.birthDay,'%d/%m/%Y') AS 'Ngày Sinh',	
	ROUND(DATEDIFF(NOW(),customer.birthDay)/360) AS 'Tuổi',
	customer.phone AS 'Điện Thoại',
	customer.address AS 'Địa Chỉ',
	customer.email AS 'Email'
	FROM customer 
	WHERE (customer.name like concat('%',search,'%') 
				or customer.phone like concat('%',search,'%')
				or customer.address like CONCAT('%',search,'%')
				or customer.email like CONCAT('%',search,'%'))
				and customer.isActive=TRUE
	order by customer.id desc;
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

/* Procedure structure for procedure `customer_update_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `customer_update_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `customer_update_all`(in _id int,IN _name VARCHAR(50),IN _sex BOOLEAN, IN _birtday DATE, IN _phone VARCHAR(13), IN _address VARCHAR(100), IN _email VARCHAR(100))
BEGIN
	update customer set
		customer.name=_name,
		customer.sex=_sex,
		customer.birthDay=_birtday,
		customer.phone=_phone,
		customer.address=_address,
		customer.email=_email
	where customer.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `discount_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `discount_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `discount_add`(
		in _name varchar(50),
		in _type int,
		in _beginDate timestamp,
		in _endDate timestamp,
		in _condition int,
		in _valueInvoice int,
		in _value int,
		in _detail text
	)
BEGIN
	insert into discount(
		discount.name,
		discount.type,
		discount.beginDate,
		discount.endDate,
		discount.conditions,
		discount.valueInvoice,
		discount.value,
		discount.detail
	)
	values(
		_name,
		_type,
		_beginDate,
		_endDate,
		_condition,
		_valueInvoice,
		_value,
		_detail
	);
	
			
END */$$
DELIMITER ;

/* Procedure structure for procedure `discount_delete` */

/*!50003 DROP PROCEDURE IF EXISTS  `discount_delete` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `discount_delete`(in _id int)
BEGIN
	update discount set discount.isActive=false where discount.id=_id;
			
END */$$
DELIMITER ;

/* Procedure structure for procedure `discount_detail_insert` */

/*!50003 DROP PROCEDURE IF EXISTS  `discount_detail_insert` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `discount_detail_insert`(in _id_invoice int, in _id_discout int)
BEGIN
	insert into discount_detail(discount_detail.id_invoice_id,discount_detail.id_discount_id)			
	values(_id_invoice,_id_discout);
END */$$
DELIMITER ;

/* Procedure structure for procedure `discount_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `discount_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `discount_get_all`()
BEGIN
	SELECT
			discount.id AS 'ID',
			discount.name AS 'Tên Chương Trình',			
			date_format(discount.beginDate,'%d/%m/%Y %H:%i') AS 'Bắt Đầu',			
			DATE_FORMAT(discount.endDate,'%d/%m/%Y %H:%i') AS  'Kết Thúc',
			(CASE discount.type WHEN 0
			THEN 'Phần Trăm' ELSE 'Tiền Mặt' END) AS 'Hình Thức',
			(CASE discount.conditions WHEN 0
			THEN 'Tất Cả Hóa Đơn' ELSE 'Hoán Đơn Trên' END) AS 'Áp Dụng',
			discount.valueInvoice as 'Điều Kiện',			
			discount.value AS 'Giá Trị',
			discount.detail AS 'Chi Tiết',
			discount.type,
			discount.conditions
	FROM discount
	WHERE discount.isActive=TRUE
	order by discount.beginDate desc;
			
END */$$
DELIMITER ;

/* Procedure structure for procedure `discount_get_by_date` */

/*!50003 DROP PROCEDURE IF EXISTS  `discount_get_by_date` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `discount_get_by_date`(in dt timestamp)
BEGIN
		select * from discount where discount.isActive=true and (date(dt) between date(beginDate) and date(endDate)) order by discount.beginDate desc limit 0,1;
END */$$
DELIMITER ;

/* Procedure structure for procedure `discount_test_date` */

/*!50003 DROP PROCEDURE IF EXISTS  `discount_test_date` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `discount_test_date`(in dt timestamp)
begin
		select * from discount where dt between discount.beginDate and discount.endDate and discount.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `discount_update` */

/*!50003 DROP PROCEDURE IF EXISTS  `discount_update` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `discount_update`(
		IN _name VARCHAR(50),
		IN _type INT,
		IN _beginDate TIMESTAMP,
		IN _endDate TIMESTAMP,
		IN _condition INT,
		IN _valueInvoice INT,
		IN _value INT,
		IN _detail TEXT,
		in _id int
	)
BEGIN
	update discount set
		discount.name=_name,
		discount.type=_type,
		discount.beginDate=_beginDate,
		discount.endDate=_endDate,
		discount.conditions=_condition,
		discount.valueInvoice=_valueInvoice,
		discount.value=_value,
		discount.detail=_detail
	where discount.id=_id;
	
END */$$
DELIMITER ;

/* Procedure structure for procedure `discount_update_status` */

/*!50003 DROP PROCEDURE IF EXISTS  `discount_update_status` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `discount_update_status`(in _id int)
BEGIN
	update discount set discount.isActive=false where discount.id=_id;
			
END */$$
DELIMITER ;

/* Procedure structure for procedure `distributor_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `distributor_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `distributor_add`(
	in _name varchar(50),
	in _phone varchar(20),
	in _email varchar(100),
	in _address varchar(100)
)
BEGIN
	insert into distributor(
		distributor.name,
		distributor.phone,
		distributor.email,
		distributor.address
	)
	values(
		_name,
		_phone,
		_email,
		_address
	);
END */$$
DELIMITER ;

/* Procedure structure for procedure `distributor_add_name` */

/*!50003 DROP PROCEDURE IF EXISTS  `distributor_add_name` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `distributor_add_name`(IN _name VARCHAR(50))
BEGIN
		insert into distributor(distributor.name) values(_name);
END */$$
DELIMITER ;

/* Procedure structure for procedure `distributor_del` */

/*!50003 DROP PROCEDURE IF EXISTS  `distributor_del` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `distributor_del`(
	in _id int
)
BEGIN
		update distributor set
		distributor.isActive=false
		where distributor.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `distributor_gel_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `distributor_gel_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `distributor_gel_all`()
BEGIN
	SELECT 
	distributor.id as 'ID',
	distributor.name as 'Tên Nhà Cung Cấp',
	distributor.phone as 'Điện Thoại',
	distributor.email as 'Email',
	distributor.address as 'Địa Chỉ'
	FROM distributor
	WHERE distributor.isActive=TRUE
	ORDER BY distributor.id DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `distributor_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `distributor_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `distributor_get_all`()
BEGIN
		SELECT
	distributor.id as 'ID',
	distributor.name as 'Tên Công Ty',
	distributor.address as 'Địa Chỉ',
	distributor.phone as 'SĐT',
	distributor.email as 'Email'
	FROM distributor
	WHERE distributor.isActive=TRUE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `distributor_get_by_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `distributor_get_by_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `distributor_get_by_id`(in _id int)
BEGIN
		SELECT
	distributor.id as 'ID',
	distributor.name as 'Tên Công Ty',
	distributor.address as 'Địa Chỉ',
	distributor.phone as 'SĐT',
	distributor.email as 'Email'
	FROM distributor
	WHERE distributor.isActive=TRUE
	and distributor.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `distributor_search` */

/*!50003 DROP PROCEDURE IF EXISTS  `distributor_search` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `distributor_search`(
	in _key varchar(50)
)
BEGIN
		SELECT 
	distributor.id AS 'ID',
	distributor.name AS 'Tên Nhà Cung Cấp',
	distributor.phone AS 'Điện Thoại',
	distributor.email AS 'Email',
	distributor.address AS 'Địa Chỉ'
	FROM distributor
	WHERE distributor.isActive=TRUE
	and distributor.name like concat('%',_key,'%')
	ORDER BY distributor.id DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `distributor_update` */

/*!50003 DROP PROCEDURE IF EXISTS  `distributor_update` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `distributor_update`(
	in _name varchar(50),
	in _phone varchar(20),
	in _email varchar(100),
	in _address varchar(100),
	in _id int
)
BEGIN
		update distributor set
		distributor.name=_name,
		distributor.phone=_phone,
		distributor.email=_email,
		distributor.address=_address
		where distributor.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `Invoice_getMaxID` */

/*!50003 DROP PROCEDURE IF EXISTS  `Invoice_getMaxID` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `Invoice_getMaxID`()
BEGIN
	select max(invoice.id) from invoice where invoice.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `Invoice_get_between_date_staff_customer` */

/*!50003 DROP PROCEDURE IF EXISTS  `Invoice_get_between_date_staff_customer` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `Invoice_get_between_date_staff_customer`(in dt1 timestamp, in dt2 timestamp, in idStaff int, in idCustomer int, in choise int)
BEGIN
	
	SELECT	
	DATE_FORMAT(table_reservation.endDate,'%d/%m/%Y - %H:%i') AS 'Thanh Toán',
	group_CONCAT(table.name,' - ',table_location.name) AS 'Bàn  - Tầng',
	case discount.type when 0 then FORMAT(discount.value,0) else '-' end AS 'Khuyến Mãi(%)',
	case discount.type when 0 then '-' else FORMAT(discount.value,0) end AS 'Khuyến Mãi(VND)',
	invoice.discount AS 'Giảm Giá',
	invoice.cost AS 'Thanh Toán',
	invoice.note AS 'Ghi Chú',
	table_reservation.id
FROM 
	invoice INNER JOIN table_reservation 
		ON invoice.id_table_reservation_id=table_reservation.id
	INNER JOIN table_reservation_detail
		ON table_reservation.id=table_reservation_detail.id_table_reservation_id
	INNER JOIN `table`
		ON table.id=table_reservation_detail.id_table_id
	INNER JOIN table_location
		ON table.location=table_location.id
	left JOIN discount_detail 
		ON invoice.id=discount_detail.id_invoice_id
	left JOIN discount
		ON discount_detail.id_discount_id=discount.id
WHERE 
	case choise 
	when 0 then /* khi ma khach hang va ma nhan vien khong duoc chon*/
			DATE(table_reservation.endDate)<>'00-00-00'
			AND (DATE(table_reservation.beginDate) BETWEEN DATE(dt1) AND DATE(dt2))
	when 1 then /* ma khach hang duoc chon */
			DATE(table_reservation.endDate)<>'00-00-00'
			AND (DATE(table_reservation.beginDate) BETWEEN DATE(dt1) AND DATE(dt2))
			AND table_reservation.id_customer_id=idCustomer
	when 2 then /* ma nhan vien duoc chon*/
			DATE(table_reservation.endDate)<>'00-00-00'
			AND (DATE(table_reservation.beginDate) BETWEEN DATE(dt1) AND DATE(dt2))
			AND invoice.id_staff_id=idStaff
	when 3 then /* ma khach hang va ma nhan vien duoc chon */
			DATE(table_reservation.endDate)<>'00-00-00'
			AND (DATE(table_reservation.beginDate) BETWEEN DATE(dt1) AND DATE(dt2))
			AND invoice.id_staff_id=idStaff
			and table_reservation.id_customer_id=idCustomer
	end
	group by table_reservation.id;
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

/* Procedure structure for procedure `invoice_statitics_by_day` */

/*!50003 DROP PROCEDURE IF EXISTS  `invoice_statitics_by_day` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `invoice_statitics_by_day`(in dtStart date,in dtEnd date)
BEGIN
	SELECT 
	DATE_FORMAT(invoice.date,'%d/%m/%Y') AS 'Thời Gian',
	COUNT(id) AS 'Số Hóa Đơn',
	SUM(discount) AS 'Tổng Tiền Giảm Giá',
	SUM(cost) AS 'Tổng Tiền Thu'
	FROM invoice
	WHERE date(invoice.date) BETWEEN date(dtStart)  AND date(dtEnd)
	GROUP BY DAY(invoice.date);
			
END */$$
DELIMITER ;

/* Procedure structure for procedure `invoice_statitics_by_month` */

/*!50003 DROP PROCEDURE IF EXISTS  `invoice_statitics_by_month` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `invoice_statitics_by_month`(IN dtStart DATE,IN dtEnd DATE)
BEGIN
	SELECT 
	CONCAT('Tháng ',DATE_FORMAT(invoice.date,'%m/%Y')) AS 'Thời Gian',
	COUNT(id) AS 'Số Hóa Đơn',
	SUM(discount) AS 'Tổng Tiền Giảm Giá',
	SUM(cost) AS 'Tổng Tiền Thu'
	FROM invoice
	WHERE DATE(invoice.date) BETWEEN DATE(dtStart)  AND DATE(dtEnd)
	GROUP BY MONTH(invoice.date);
			
END */$$
DELIMITER ;

/* Procedure structure for procedure `invoice_statitics_by_year` */

/*!50003 DROP PROCEDURE IF EXISTS  `invoice_statitics_by_year` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `invoice_statitics_by_year`(IN dtStart DATE,IN dtEnd DATE)
BEGIN
	SELECT 
	CONCAT('Năm ',DATE_FORMAT(invoice.date,'%Y')) AS 'Thời Gian',
	COUNT(id) AS 'Số Hóa Đơn',
	SUM(discount) AS 'Tổng Tiền Giảm Giá',
	SUM(cost) AS 'Tổng Tiền Thu'
	FROM invoice
	WHERE DATE(invoice.date) BETWEEN DATE(dtStart)  AND DATE(dtEnd)
	GROUP BY YEAR(invoice.date);
			
END */$$
DELIMITER ;

/* Procedure structure for procedure `payment_invoice_add_invoice` */

/*!50003 DROP PROCEDURE IF EXISTS  `payment_invoice_add_invoice` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `payment_invoice_add_invoice`(in dt timestamp,in idstaff int, in _cost int, in info varchar(100),in note varchar(200)
	)
BEGIN
	insert into payment_invoice(
		payment_invoice.date,
		payment_invoice.id_staff_id,
		payment_invoice.cost,
		payment_invoice.info,
		payment_invoice.note
		)
		values(
		dt,
		idstaff,
		_cost,
		info,
		note
		);
	
END */$$
DELIMITER ;

/* Procedure structure for procedure `payment_invoice_get_by_date` */

/*!50003 DROP PROCEDURE IF EXISTS  `payment_invoice_get_by_date` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `payment_invoice_get_by_date`(IN _fromDate timestamp, _toDate timestamp)
BEGIN
	SELECT
		date_format(payment_invoice.date,'%d/%m/%Y') as 'Ngày Lập',
		staff.name as 'Người Lập',
		payment_invoice.cost as 'Số Tiền',
		payment_invoice.info as 'Thông Tin',
		payment_invoice.note as 'Ghi Chú'
	FROM 
	payment_invoice LEFT JOIN staff
	ON payment_invoice.id_staff_id=staff.id
	WHERE (payment_invoice.date BETWEEN DATE(_fromDate) AND DATE(_toDate));
	
END */$$
DELIMITER ;

/* Procedure structure for procedure `payment_invoice_raw_material_invoice_statitics_by_day` */

/*!50003 DROP PROCEDURE IF EXISTS  `payment_invoice_raw_material_invoice_statitics_by_day` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `payment_invoice_raw_material_invoice_statitics_by_day`(IN dtFrom timestamp, in dtTo timestamp)
BEGIN
  DROP TABLE IF EXISTS tb1;
	DROP TABLE IF EXISTS tb2;
	CREATE TEMPORARY TABLE tb1 AS(			
	SELECT 	
	raw_material_invoice.date AS ngay,
	'Hóa Đơn Nhập Hàng'  AS  t,
	count(raw_material_invoice_detail.id_raw_material_invoice) as sohoadon,
	SUM(raw_material_invoice_detail.cost*raw_material_invoice_detail.number) AS TongTien
	
	FROM raw_material_invoice INNER JOIN raw_material_invoice_detail
	ON raw_material_invoice.id=raw_material_invoice_detail.id_raw_material_invoice
	WHERE  (raw_material_invoice.date BETWEEN DATE(dtFrom) AND DATE(dtTo))
	GROUP BY raw_material_invoice.date);
	CREATE TEMPORARY TABLE tb2 AS(
	SELECT 
	payment_invoice.date  AS ngay,
	'Phiếu Chi' AS t,
	COUNT(*) AS sophieuchi,
	SUM(payment_invoice.cost) AS TongTien 
	FROM payment_invoice 
	WHERE  (payment_invoice.date BETWEEN DATE(dtFrom) AND DATE(dtTo))
	GROUP BY payment_invoice.date
	);
	SELECT date_format(ngay, '%d/%m/%Y') AS 'Ngày','Hóa Đơn Nhập Hàng' AS 'HĐ Nhập Hàng/Phiếu Chi',sohoadon AS 'Số Lượng',TongTien AS 'Tổng Tiền' FROM tb1
	UNION	
	SELECT DATE_FORMAT(ngay, '%d/%m/%Y') AS 'Ngày','Phiếu Chi',sophieuchi AS 'HĐ Nhập Hàng/Phiếu Chi',TongTien AS 'Tổng Tiền' FROM tb2
	order by 'Ngày' desc;
	
END */$$
DELIMITER ;

/* Procedure structure for procedure `payment_invoice_raw_material_invoice_statitics_by_month` */

/*!50003 DROP PROCEDURE IF EXISTS  `payment_invoice_raw_material_invoice_statitics_by_month` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `payment_invoice_raw_material_invoice_statitics_by_month`(IN dtFrom timestamp, in dtTo timestamp)
BEGIN
DROP TABLE IF EXISTS tb1;
	DROP TABLE IF EXISTS tb2;
	CREATE TEMPORARY TABLE tb1 AS(			
	SELECT 	
	raw_material_invoice.date AS ngay,
	'Hóa Đơn Nhập Hàng'  AS  t,
	COUNT(raw_material_invoice_detail.id_raw_material_invoice) AS sohoadon,
	SUM(raw_material_invoice_detail.cost*raw_material_invoice_detail.number) AS TongTien
	
	FROM raw_material_invoice INNER JOIN raw_material_invoice_detail
	ON raw_material_invoice.id=raw_material_invoice_detail.id_raw_material_invoice
	WHERE  (month(raw_material_invoice.date) BETWEEN MONTH(dtFrom) AND MONTH(dtTo))
	GROUP BY MONTH(raw_material_invoice.date));
	CREATE TEMPORARY TABLE tb2 AS(
	SELECT 
	payment_invoice.date  AS ngay,
	'Phiếu Chi' AS t,
	COUNT(*) AS sophieuchi,
	SUM(payment_invoice.cost) AS TongTien 
	FROM payment_invoice 
	WHERE  (MONTH(payment_invoice.date) BETWEEN MONTH(dtFrom) AND MONTH(dtTo))
	GROUP BY month(payment_invoice.date)
	);
	SELECT DATE_FORMAT(ngay, '%m/%Y') AS 'Tháng','Hóa Đơn Nhập Hàng' AS 'HĐ Nhập Hàng/Phiếu Chi',sohoadon AS 'Số Lượng',TongTien AS 'Tổng Tiền' FROM tb1
	UNION	
	SELECT DATE_FORMAT(ngay, '%m/%Y') AS 'Tháng','Phiếu Chi',sophieuchi AS 'HĐ Nhập Hàng/Phiếu Chi',TongTien AS 'Tổng Tiền' FROM tb2
	ORDER BY 'Tháng' DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `payment_invoice_raw_material_invoice_statitics_by_year` */

/*!50003 DROP PROCEDURE IF EXISTS  `payment_invoice_raw_material_invoice_statitics_by_year` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `payment_invoice_raw_material_invoice_statitics_by_year`(IN dtFrom timestamp, in dtTo timestamp)
BEGIN
DROP TABLE IF EXISTS tb1;
	DROP TABLE IF EXISTS tb2;
	CREATE TEMPORARY TABLE tb1 AS(			
	SELECT 	
	raw_material_invoice.date AS ngay,
	'Hóa Đơn Nhập Hàng'  AS  t,
	COUNT(raw_material_invoice_detail.id_raw_material_invoice) AS sohoadon,
	SUM(raw_material_invoice_detail.cost*raw_material_invoice_detail.number) AS TongTien
	
	FROM raw_material_invoice INNER JOIN raw_material_invoice_detail
	ON raw_material_invoice.id=raw_material_invoice_detail.id_raw_material_invoice
	WHERE  (Year(raw_material_invoice.date) BETWEEN YEAR(dtFrom) AND YEAR(dtTo))
	GROUP BY YEAR(raw_material_invoice.date));
	CREATE TEMPORARY TABLE tb2 AS(
	SELECT 
	payment_invoice.date  AS ngay,
	'Phiếu Chi' AS t,
	COUNT(*) AS sophieuchi,
	SUM(payment_invoice.cost) AS TongTien 
	FROM payment_invoice 
	WHERE  (YEAR(payment_invoice.date) BETWEEN YEAR(dtFrom) AND YEAR(dtTo))
	GROUP BY YEAR(payment_invoice.date)
	);
	SELECT DATE_FORMAT(ngay, '%Y') AS 'Năm','Hóa Đơn Nhập Hàng' AS 'HĐ Nhập Hàng/Phiếu Chi',sohoadon AS 'Số Lượng',TongTien AS 'Tổng Tiền' FROM tb1
	UNION	
	SELECT DATE_FORMAT(ngay, '%Y') AS 'Năm','Phiếu Chi',sophieuchi AS 'HĐ Nhập Hàng/Phiếu Chi',TongTien AS 'Tổng Tiền' FROM tb2
	ORDER BY 'Năm' DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_add`(
	in _name varchar(50), 
	in _number float, 
	in _unit int,
	in _unit_sub int
	)
BEGIN
		insert into raw_material(
			raw_material.name,
			raw_material.number,
			raw_material.unit,
			raw_material.id_unit_sub
			)
		values(
		_name,
		_number,
		_unit,
		_unit_sub
		);
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_del` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_del` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_del`(in _id int)
BEGIN
		delete from raw_material
	where raw_material.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_get_all`()
BEGIN
		SELECT 
			raw_material.id AS 'ID',
			raw_material.name AS 'Tên Thực Phẩm',
			raw_material.number AS 'Số Lượng Tồn Kho',
			unit.name AS 'Đơn Vị Tính',
			unit.id		
		FROM raw_material LEFT JOIN unit ON raw_material.unit=unit.id
		WHERE raw_material.isActive=TRUE
		ORDER BY raw_material.id DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_get_by_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_get_by_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_get_by_id`(in _id int)
BEGIN
		SELECT 
			raw_material.id AS 'ID',
			raw_material.name AS 'Tên Thực Phẩm',
			raw_material.number AS 'Số Lượng Tồn Kho',
			unit.name AS 'Đơn Vị Tính',
			unit.id,
			raw_material.id_unit_sub
			
		FROM raw_material LEFT JOIN unit ON raw_material.unit=unit.id
		WHERE raw_material.isActive=TRUE
		and raw_material.id=_id
		ORDER BY raw_material.id DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_get_number` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_get_number` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_get_number`(IN _id INT)
BEGIN		
		SELECT raw_material.number,raw_material.unit
		FROM raw_material
		WHERE raw_material.id=_id
		AND raw_material.isActive=TRUE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_invoice_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_invoice_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_invoice_add`(in _idstaff int, in _id_distributor int, in _note varchar(100))
BEGIN
	insert into raw_material_invoice(
		raw_material_invoice.date,
		raw_material_invoice.id_staff_id,
		raw_material_invoice.id_istributor_id,
		raw_material_invoice.note
		)
		values(
			current_timestamp,
			_idstaff,
			_id_distributor,
			_note
		);
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_invoice_detail_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_invoice_detail_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_invoice_detail_add`(
	in _id_raw_material int,
	in _id_raw_material_invoice int,
	in _number float,
	in _cost int
	)
BEGIN
	insert into raw_material_invoice_detail(
		raw_material_invoice_detail.id_raw_material_id,
		raw_material_invoice_detail.id_raw_material_invoice,
		raw_material_invoice_detail.number,
		raw_material_invoice_detail.cost
		)
		values(
			_id_raw_material,
			_id_raw_material_invoice,
			_number,
			_cost
		);
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_invoice_detail_get_by_id_raw_material_invoice` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_invoice_detail_get_by_id_raw_material_invoice` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_invoice_detail_get_by_id_raw_material_invoice`(in _id int)
begin
		SELECT 
	raw_material.name AS 'Mặt Hàng',
	unit.name AS 'ĐVT',
	raw_material_invoice_detail.number AS 'Số Lượng',
	raw_material_invoice_detail.cost AS 'Đơn Giá',
	(raw_material_invoice_detail.number*raw_material_invoice_detail.cost) AS 'Thành Tiền'
FROM 
	raw_material_invoice_detail INNER JOIN raw_material
		ON raw_material_invoice_detail.id_raw_material_id=raw_material.id
	INNER JOIN unit
		ON raw_material.unit=unit.id
where raw_material_invoice_detail.id_raw_material_invoice=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_invoice_get_by_date` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_invoice_get_by_date` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_invoice_get_by_date`(in _from timestamp, in _to timestamp)
BEGIN
	SELECT 
	raw_material_invoice.id AS 'ID',
	DATE_FORMAT(raw_material_invoice.date,'%d/%m/%Y') AS 'Ngày Lập',
	staff.name AS 'Nhân Viên',	
	SUM(raw_material_invoice_detail.number*raw_material_invoice_detail.cost) AS 'Tổng Tiền',
	raw_material_invoice.note AS 'Ghi Chú'
FROM 
	raw_material_invoice INNER JOIN staff
		ON raw_material_invoice.id_staff_id=staff.id
	INNER JOIN raw_material_invoice_detail
		ON raw_material_invoice.id=raw_material_invoice_detail.id_raw_material_invoice
WHERE raw_material_invoice.isActive=TRUE
and (raw_material_invoice.date between _from and _to)
GROUP BY raw_material_invoice_detail.id_raw_material_invoice;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_invoice_get_max_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_invoice_get_max_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_invoice_get_max_id`()
BEGIN
	SELECT MAX(raw_material_invoice.id) as id FROM raw_material_invoice WHERE raw_material_invoice.isActive=TRUE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_search` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_search` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_search`(in _search varchar(50))
BEGIN
		SELECT 
			raw_material.id AS 'ID',
			raw_material.name AS 'Tên Thực Phẩm',
			raw_material.number AS 'Số Lượng Tồn Kho',
			unit.name AS 'Đơn Vị Tính',
			unit.id		
		FROM raw_material LEFT JOIN unit ON raw_material.unit=unit.id
		WHERE raw_material.isActive=TRUE
		and (raw_material.name like concat('%',_search,'%'))
		ORDER BY raw_material.id DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_test_name` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_test_name` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_test_name`(in _name varchar(50))
BEGIN
		SELECT 
			*
		FROM raw_material
		WHERE raw_material.isActive=TRUE
		and raw_material.name=_name
		ORDER BY raw_material.id DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_update` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_update` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_update`(
	in _name varchar(50),
	in _unit int,
	in _id int,
	in _id_unit_sub int,
	in _number float
	)
BEGIN
		update raw_material set
		raw_material.name=_name,		
		raw_material.unit=_unit,
		raw_material.id_unit_sub=_id_unit_sub,
		raw_material.number=_number
	where raw_material.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_update_number` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_update_number` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_update_number`(in _number float, in _id int)
BEGIN
		update raw_material set		
		raw_material.number=raw_material.number-_number
	where raw_material.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `recipes_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `recipes_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `recipes_add`(in _id_raw_material int, in _id_service int, in _number float,in _unit_sub int)
BEGIN
	insert into recipes(recipes.id_raw_material_id,recipes.id_service_id,recipes.number,recipes.id_unit_id)
	values(_id_raw_material,_id_service,_number,_unit_sub);
END */$$
DELIMITER ;

/* Procedure structure for procedure `recipes_count_by_idService` */

/*!50003 DROP PROCEDURE IF EXISTS  `recipes_count_by_idService` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `recipes_count_by_idService`(IN idService INT)
BEGIN
	SELECT COUNT(*) AS n
	FROM recipes
	WHERE recipes.id_service_id=idService
	and recipes.isActive=TRUE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `recipes_count_by_idService_id_raw` */

/*!50003 DROP PROCEDURE IF EXISTS  `recipes_count_by_idService_id_raw` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `recipes_count_by_idService_id_raw`(IN idService INT, in id_rawmaterial int)
BEGIN
	SELECT COUNT(*) AS n
	FROM recipes
	WHERE recipes.id_service_id=idService and recipes.id_raw_material_id=id_rawmaterial
	and recipes.isActive=TRUE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `recipes_delete` */

/*!50003 DROP PROCEDURE IF EXISTS  `recipes_delete` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `recipes_delete`(in id_recipes int)
BEGIN
	delete from recipes where recipes.id=id_recipes;
END */$$
DELIMITER ;

/* Procedure structure for procedure `recipes_get_by_service` */

/*!50003 DROP PROCEDURE IF EXISTS  `recipes_get_by_service` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `recipes_get_by_service`(in _id int)
BEGIN
	SELECT 
	recipes.id AS 'ID',
	raw_material.name AS 'Tên NL',
	unit.name AS 'ĐVT',
	recipes.number AS 'Số Lượng',
	raw_material.id,
	recipes.id_unit_id
		
FROM 
	recipes INNER JOIN raw_material ON recipes.id_raw_material_id=raw_material.id
	INNER JOIN service ON recipes.id_service_id=service.id
	LEFT JOIN unit ON recipes.id_unit_id=unit.id
	WHERE recipes.isActive=TRUE
	AND service.isActive=TRUE
	AND raw_material.isActive=TRUE
	AND recipes.id_service_id=_id
	ORDER BY recipes.id DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `recipes_update_by_id_recipes` */

/*!50003 DROP PROCEDURE IF EXISTS  `recipes_update_by_id_recipes` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `recipes_update_by_id_recipes`(in id_recipes int,in _number float)
BEGIN
	update recipes set recipes.number=_number where recipes.id=id_recipes;
END */$$
DELIMITER ;

/* Procedure structure for procedure `recipes_update_by_id_service_id_raw_material` */

/*!50003 DROP PROCEDURE IF EXISTS  `recipes_update_by_id_service_id_raw_material` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `recipes_update_by_id_service_id_raw_material`(in _idService int,in _id_raw_material_id int, in _number float)
BEGIN
	update recipes set recipes.number=recipes.number+_number where recipes.id_service_id=_idService and recipes.id_raw_material_id=_id_raw_material_id
	and recipes.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `restaurant_info_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `restaurant_info_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `restaurant_info_get_all`()
BEGIN
	select * from restaurant_info;
END */$$
DELIMITER ;

/* Procedure structure for procedure `restaurant_info_update` */

/*!50003 DROP PROCEDURE IF EXISTS  `restaurant_info_update` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `restaurant_info_update`(
	in _name varchar(100),
	IN _phone VARCHAR(100),
	IN _address VARCHAR(100),
	IN _email VARCHAR(100),
	IN _logo VARCHAR(100),
	IN _nomal int,
	IN _party int,
	IN _warming int
	)
BEGIN
	update restaurant_info set		
		restaurant_info.name=_name,
		restaurant_info.phone=_phone,
		restaurant_info.address=_address,
		restaurant_info.email=_email,
		restaurant_info.logo=_logo,
		restaurant_info.hourReservationNomal=_nomal,
		restaurant_info.hourReservationParty=_party,
		restaurant_info.minuteWarning=_warming;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_add`(in _name varchar(50), in _type int, in _unit int,in _detail text, in img text)
BEGIN
	insert into service(service.name,service.type,service.unit,service.detail,service.image)
	values(_name,_type,_unit,_detail,img);
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

/* Procedure structure for procedure `service_count_using` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_count_using` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_count_using`(in _id int)
BEGIN
SELECT
	count(table_service.id_service_id) as n
	
FROM 
	service, 
	table_service
WHERE 
	table_service.isActive=TRUE AND
	service.id=table_service.id_service_id
	and table_service.id_service_id=_id
	AND table_service.status=TRUE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_delete` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_delete` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_delete`(IN _id INT)
BEGIN
update service set service.isActive=false where service.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_getByID` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_getByID` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_getByID`(in _id int)
BEGIN
	DROP TABLE IF EXISTS tb;
	CREATE TEMPORARY TABLE tb AS(
	SELECT id_service_id,MAX(beginDate) AS bgDate FROM service_cost
	WHERE service_cost.isActive=TRUE
	GROUP BY id_service_id
	);
	SELECT service.id,
			 service.name,
			 service_type.name AS type_name,			 
			 service.unit,
			 service_cost.cost AS dongia,
			 service.store,
			 service.detail,
			 service.image,
			 service_type.id AS idType
			 			 			 
	FROM service INNER JOIN tb ON service.id=tb.id_service_id
			INNER JOIN service_cost ON tb.id_service_id=service_cost.id_service_id
			INNER JOIN service_type ON service.type=service_type.id
	WHERE tb.bgDate=service_cost.beginDate AND service.isActive=TRUE
	AND service.id=_id
	ORDER BY service.name ASC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_get_all`()
BEGIN
	DROP TABLE IF EXISTS tb;
	CREATE TEMPORARY TABLE tb AS(
	SELECT id_service_id,MAX(beginDate) AS bgDate FROM service_cost
	WHERE service_cost.isActive=TRUE
	GROUP BY id_service_id
	);
	SELECT 
			 service.id as 'ID',
			 service.name as 'Tên DV',
			 service_type.name as 'Loại DV',			 
			 unit.name as 'ĐVT',
			 service_cost.cost as 'Đơn Giá',			 
			 service.detail as 'Chi Tiết',
			 service.image,
			 service_type.id AS idType,
			 unit.id
			 			 			 
	FROM service INNER JOIN tb ON service.id=tb.id_service_id
			left JOIN service_cost ON tb.id_service_id=service_cost.id_service_id
			left JOIN service_type ON service.type=service_type.id
			LEFT JOIN unit ON service.unit=unit.id
	WHERE tb.bgDate=service_cost.beginDate AND service.isActive=TRUE
	ORDER BY service.id desc;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_get_limit` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_get_limit` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_get_limit`()
BEGIN
	DROP TABLE IF EXISTS tb;
	CREATE TEMPORARY TABLE tb AS(
	SELECT id_service_id,MAX(beginDate) AS bgDate FROM service_cost
	where service_cost.isActive=true
	GROUP BY id_service_id
	);
	SELECT service.id AS 'Mã Dịch Vụ',
			 service.name AS 'Tên Dịch Vụ',
			 service_cost.cost  AS 'Đơn Giá',
			 unit.name as 'ĐVT'
	FROM service INNER JOIN tb ON service.id=tb.id_service_id
			INNER JOIN service_cost ON tb.id_service_id=service_cost.id_service_id
			left join unit on service.unit=unit.id
	WHERE tb.bgDate=service_cost.beginDate and service.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_get_max_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_get_max_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_get_max_id`()
BEGIN
	select max(id) from service where service.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_search_byName` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_search_byName` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_search_byName`(IN search Varchar(20))
BEGIN
	DROP TABLE IF EXISTS tb;
	CREATE TEMPORARY TABLE tb AS(
	SELECT id_service_id,MAX(beginDate) AS bgDate FROM service_cost
	WHERE service_cost.isActive=TRUE
	GROUP BY id_service_id
	);
	SELECT service.id AS 'Mã Dịch Vụ',
			 service.name AS 'Tên Dịch Vụ',
			 service_cost.cost  AS 'Đơn Giá',
			 unit.name AS 'ĐVT'
	FROM service INNER JOIN tb ON service.id=tb.id_service_id
			INNER JOIN service_cost ON tb.id_service_id=service_cost.id_service_id
			LEFT JOIN unit ON service.unit=unit.id
	WHERE tb.bgDate=service_cost.beginDate AND service.isActive=TRUE
	and service.name like concat('%',search,'%');
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_search_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_search_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_search_get_all`(in search varchar(50))
BEGIN
	DROP TABLE IF EXISTS tb;
	CREATE TEMPORARY TABLE tb AS(
	SELECT id_service_id,MAX(beginDate) AS bgDate FROM service_cost
	WHERE service_cost.isActive=TRUE
	GROUP BY id_service_id
	);
	SELECT service.id AS 'Mã Dịch Vụ',
			 service.name AS 'Tên Dịch Vụ',
			 service_type.name AS 'Loại',			 
			 service.unit AS 'ĐVT',
			 FORMAT(service_cost.cost,0)  AS 'Đơn Giá',			 
			 service.detail AS 'Chi Tiết',
			 service.image AS 'Hình Ảnh',
			 service_type.id AS id
			 			 			 
	FROM service INNER JOIN tb ON service.id=tb.id_service_id
			INNER JOIN service_cost ON tb.id_service_id=service_cost.id_service_id
			INNER JOIN service_type ON service.type=service_type.id
	WHERE tb.bgDate=service_cost.beginDate AND service.isActive=TRUE
	and (service.name like concat('%',search,'%') or service_type.name like concat('%',search,'%'))
	ORDER BY service.name ASC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_type_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_type_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_type_add`(in svName varchar(50))
BEGIN
	insert into service_type(service_type.name) values(svName);
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

/* Procedure structure for procedure `service_type_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_type_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_type_get_all`()
BEGIN
	select service_type.id,service_type.name from service_type where service_type.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_type_test_name` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_type_test_name` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_type_test_name`(in _name varchar(30))
BEGIN
select * from service_type where service_type.name=_name;
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_update_by_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_update_by_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_update_by_id`(in _id int,IN _name VARCHAR(50), IN _type INT, IN _unit int,IN _detail TEXT, IN img TEXT)
BEGIN
	update service set 
				service.name=_name,
				service.type=_type,
				service.unit=_unit,				
				service.detail=_detail,
				service.image=img
	where service.id=_id;
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

/* Procedure structure for procedure `staff_add_staff` */

/*!50003 DROP PROCEDURE IF EXISTS  `staff_add_staff` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `staff_add_staff`(in _name varchar(50),in _sex boolean, in _birthday date, in _phone varchar(13),in _address varchar(100), in _email varchar(100), in _pin varchar(10))
BEGIN
		insert into staff(staff.name,staff.sex,staff.birthDay,staff.phone,staff.address,staff.email,staff.pin)
		values(_name,_sex,_birthday,_phone,_address,_email,_pin);		
END */$$
DELIMITER ;

/* Procedure structure for procedure `staff_delete` */

/*!50003 DROP PROCEDURE IF EXISTS  `staff_delete` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `staff_delete`(
	IN _id int
	)
BEGIN
	update staff set staff.isActive=false where staff.id=_id;
 END */$$
DELIMITER ;

/* Procedure structure for procedure `staff_get_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `staff_get_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `staff_get_all`()
BEGIN
	SELECT
	staff.id AS 'Mã NV',
	staff.name AS 'Họ Tên',
	CASE staff.sex WHEN TRUE THEN 'Nam' ELSE 'Nữ' END AS 'Giới tính',
	date_format(staff.birthDay,'%d/%m/%Y') AS 'Ngày sinh',
	staff.phone AS 'Điện thoại',
	staff.email AS 'Email',
	staff.address AS 'Địa chỉ'
	
FROM staff
WHERE staff.isActive=TRUE
order by staff.id desc;	
END */$$
DELIMITER ;

/* Procedure structure for procedure `staff_get_by_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `staff_get_by_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `staff_get_by_id`(in _id int)
BEGIN
	SELECT
	staff.id AS 'Mã NV',
	staff.name AS 'Họ Tên',
	CASE staff.sex WHEN TRUE THEN 'Nam' ELSE 'Nữ' END AS 'Giới tính',
	date_format(staff.birthDay,'%d/%m/%Y') AS 'Ngày sinh',
	staff.phone AS 'Điện thoại',
	staff.email AS 'Email',
	staff.address AS 'Địa chỉ'
	
FROM staff
WHERE staff.isActive=TRUE and
staff.id=_id
order by staff.id desc;	
END */$$
DELIMITER ;

/* Procedure structure for procedure `staff_update_staff` */

/*!50003 DROP PROCEDURE IF EXISTS  `staff_update_staff` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `staff_update_staff`(in _name varchar(50),in _sex boolean, in _birthday date, in _phone varchar(13),in _address varchar(100), in _email varchar(100), in _pin varchar(10),in _id int)
BEGIN
		update staff set 
			staff.name=_name,
			staff.sex=_sex,
			staff.birthDay=_birthday,
			staff.phone=_phone,
			staff.address=_address,
			staff.email=_email,
			staff.pin=_pin
		where staff.id=_id;	
END */$$
DELIMITER ;

/* Procedure structure for procedure `system_log_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `system_log_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `system_log_add`(IN _info VARCHAR(30))
BEGIN
	insert into system_log(system_log.info) values(_info);
END */$$
DELIMITER ;

/* Procedure structure for procedure `system_log_get_max_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `system_log_get_max_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `system_log_get_max_id`()
BEGIN
 SELECT  * FROM system_log
WHERE id IN(SELECT MAX(id) FROM system_log);
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

/* Procedure structure for procedure `table_getByLocation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_getByLocation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_getByLocation`(IN idlocation INT)
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
	WHERE table.location=table_location.id AND `table`.`location`=idlocation AND `table`.`isActive`=TRUE;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_getByName` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_getByName` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_getByName`(IN tbName varchar(30))
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
	WHERE table.location=table_location.id AND `table`.`name`=tbName AND `table`.`isActive`=TRUE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_getbyStatus` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_getbyStatus` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_getbyStatus`()
BEGIN
	SELECT tb.id AS ID,tb.name AS 'Tên Bàn',table_location.name AS 'Vị Trí'
	FROM `table` AS tb,table_location
	WHERE tb.location=table_location.id AND
	tb.status <> 2;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_getByStatus_Search` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_getByStatus_Search` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_getByStatus_Search`(in search varchar(50))
BEGIN
	SELECT tb.id as ID,tb.name as 'Tên Bàn',table_location.name as 'Vị Trí'
	FROM `table` AS tb,table_location
	WHERE tb.location=table_location.id AND
	tb.status <> 1 and (tb.name like concat('%',search,'%') or table_location.name like concat('%',search,'%'));
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

/* Procedure structure for procedure `table_get_All` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_get_All` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_get_All`()
BEGIN
		SELECT 
		table.id AS 'Mã Bàn',
		table.name AS 'Tên Bàn',
		table_location.name AS 'Vị Trí',
		table_type.name AS 'Loại',
		table.numOfChair AS 'Số Khách',
		table.location,
		table.type,
		table.status
	FROM `table` LEFT JOIN table_location ON table.location=table_location.id
	LEFT JOIN table_type ON table.type=table_type.id
	WHERE `table`.`isActive`=TRUE
	order by table.id desc;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_get_by_not_reservation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_get_by_not_reservation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_get_by_not_reservation`(IN dt TIMESTAMP, IN num INT)
BEGIN
SELECT table.id as 'ID',table.name as 'Tên Bàn'
FROM `table`
WHERE table.id NOT IN(
	SELECT table_reservation_detail.id_table_id
	FROM
	table_reservation INNER JOIN table_reservation_detail
	ON table_reservation.id=table_reservation_detail.id_table_reservation_id
	INNER JOIN `table` ON table_reservation_detail.id_table_id=table.id
	WHERE DATE(table_reservation.endDate)='00-00-00'
	AND table_reservation_detail.isActive=TRUE
	AND table_reservation.isActive=TRUE
	AND (TIMESTAMPDIFF(HOUR,dt,beginDate)<num AND TIMESTAMPDIFF(HOUR,dt,beginDate)>-num));
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_get_by_not_reservation_1` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_get_by_not_reservation_1` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_get_by_not_reservation_1`(in dt timestamp, in num int,in _location int)
BEGIN
	SELECT table.id AS 'ID',table.name AS 'Tên Bàn'
FROM `table`
WHERE table.id NOT IN(
SELECT table_reservation_detail.id_table_id
FROM
			table_reservation INNER JOIN table_reservation_detail
			ON table_reservation.id=table_reservation_detail.id_table_reservation_id
			INNER JOIN `table` ON table_reservation_detail.id_table_id=table.id
WHERE DATE(table_reservation.endDate)='00-00-00'
AND table_reservation_detail.isActive=TRUE
AND table_reservation.isActive=TRUE
AND (timestampdiff(hour,dt,beginDate)<num and TIMESTAMPDIFF(HOUR,dt,beginDate)>-num)) and table.location=_location;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_insert_new_table` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_insert_new_table` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_insert_new_table`(in tbName varchar(30), in tbType int, in tbLocation int, in chair int)
BEGIN
	INSERT INTO `table`(table.name,table.type,table.location,table.numOfChair)
	VALUES(tbName,tbType,tbLocation,chair);
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_location_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_location_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_location_add`(in tbLovationName varchar(30), in detail varchar(100))
BEGIN
		insert into table_location(table_location.name,table_location.detail) values(tbLovationName,detail);
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_location_getAll` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_location_getAll` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_location_getAll`()
BEGIN
SELECT DISTINCT table_location.id,table_location.name
FROM table_location
WHERE table_location.isActive=TRUE;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_location_get_min_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_location_get_min_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_location_get_min_id`()
BEGIN
	select min(id) as id from table_location;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_location_test_name` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_location_test_name` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_location_test_name`(in tablename varchar(30))
BEGIN
	select * from table_location where table_location.name=tablename
	and table_location.isActive=true;
 END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_cancel` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_cancel` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_cancel`(IN idReservation int)
BEGIN
	update table_reservation set 
		table_reservation.isActive=false 
		where table_reservation.id=idReservation;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_count_table_reservation_by_table` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_count_table_reservation_by_table` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_count_table_reservation_by_table`(in idTable int)
BEGIN
	SELECT COUNT(*) AS num
	FROM 
	table_reservation,
	table_reservation_detail
	WHERE 
	table_reservation.id=table_reservation_detail.id_table_reservation_id
	AND table_reservation_detail.id_table_id=idTable
	AND table_reservation.status=FALSE
	AND table_reservation.isActive=TRUE
	AND table_reservation_detail.isActive=TRUE
	AND DATE(table_reservation.endDate)='00-00-00';
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_count_table_using` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_count_table_using` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_count_table_using`(IN idTable INT)
BEGIN
	SELECT table_reservation.id
	FROM table_reservation,table_reservation_detail
	WHERE table_reservation_detail.id_table_id=idTable
	AND table_reservation.id=table_reservation_detail.id_table_reservation_id
	AND table_reservation.isActive=TRUE
	AND table_reservation.status=true
	AND table_reservation_detail.isActive=TRUE
	AND DATE(endDate)='00-00-00';
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_detail_delete` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_detail_delete` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_detail_delete`(IN _id_tb INT, in _id_reservation int)
BEGIN
delete from table_reservation_detail 
where table_reservation_detail.id_table_id=_id_tb 
	and table_reservation_detail.id_table_reservation_id=_id_reservation;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_detail_getList_idTable_by_idReservation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_detail_getList_idTable_by_idReservation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_detail_getList_idTable_by_idReservation`(IN id_reservation INT)
BEGIN
	select id_table_id from table_reservation_detail where id_table_reservation_id=id_reservation;
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

/* Procedure structure for procedure `table_reservation_detail_set_active_false` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_detail_set_active_false` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_detail_set_active_false`(IN idReservation int, in idTable int)
BEGIN
	update table_reservation_detail set 
		table_reservation_detail.isActive=false 
		where table_reservation_detail.id_table_reservation_id=idReservation and table_reservation_detail.id_table_id=idTable;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_detail_update_id_table_Reservation_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_detail_update_id_table_Reservation_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_detail_update_id_table_Reservation_id`(IN idReservation1 INT, in idReservation2 int,in idTable int)
BEGIN
	UPDATE table_reservation_detail
SET id_table_reservation_id=idReservation1
WHERE id_table_reservation_id=idReservation2 AND id_table_id=idTable;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_detail_update_table_by_idReservationDetail` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_detail_update_table_by_idReservationDetail` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_detail_update_table_by_idReservationDetail`(IN idReservationDetail INT, in idTable int)
BEGIN
	UPDATE table_reservation_detail
	SET table_reservation_detail.id_table_id=idTable
	WHERE table_reservation_detail.id=idReservationDetail;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_detail_update_table_by_idTable_idReservation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_detail_update_table_by_idTable_idReservation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_detail_update_table_by_idTable_idReservation`(IN idReservation INT,IN idTable INT, IN idTableChange INT)
BEGIN
	UPDATE table_reservation_detail
	SET table_reservation_detail.id_table_id=idTableChange
	WHERE table_reservation_detail.id_table_reservation_id=idReservation AND table_reservation_detail.id_table_id=idTable;
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
		beginDate,
		prepay
	FROM 
		table_reservation 
		LEFT JOIN table_reservation_detail ON table_reservation.id=table_reservation_detail.id_table_reservation_id
		LEFT JOIN customer ON table_reservation.id_customer_id=customer.id		
	WHERE 
		
		table_reservation_detail.id_table_id=idTable
		AND DATE(endDate)='00-00-00'
		and table_reservation.isActive= TRUE
		and table_reservation_detail.isActive=TRUE		
		ORDER BY begindate asc
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

/* Procedure structure for procedure `table_reservation_get_list_recipes` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_get_list_recipes` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_get_list_recipes`()
BEGIN
	SELECT
	raw_material.name AS TenNguyenLieu,	 		
	unit.id  AS DVT,
	SUM(table_service.number*recipes.number) AS SoLuong,
	raw_material.id as idRaw
FROM 
	table_reservation  JOIN table_service
		ON table_reservation.id=table_service.id_table_reservation_id
	 JOIN service
		ON table_service.id_service_id=service.id
	JOIN recipes 
		ON recipes.id_service_id=service.id
	JOIN raw_material
		ON recipes.id_raw_material_id=raw_material.id
	JOIN unit
		ON recipes.id_unit_id=unit.id
where date(table_reservation.endDate)='00-00-00'
	and table_reservation.isActive=true	
GROUP BY recipes.id;
 END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_get_list_table` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_get_list_table` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_get_list_table`(IN _id INT)
BEGIN
	SELECT table.id as 'ID',table.name as 'Tên Bàn'
FROM
table_reservation JOIN table_reservation_detail
ON table_reservation.id=table_reservation_detail.id_table_reservation_id
JOIN `table` ON table_reservation_detail.id_table_id=table.id
WHERE table_reservation.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_get_list_table_by_id_reservation` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_get_list_table_by_id_reservation` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_get_list_table_by_id_reservation`(IN idReser INT)
BEGIN
	SELECT table_reservation_detail.id_table_id
FROM table_reservation,table_reservation_detail
WHERE table_reservation.id=table_reservation_detail.id_table_reservation_id
AND table_reservation.id=idReser and table_reservation_detail.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_get_list_warning` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_get_list_warning` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_get_list_warning`(in numHour int)
BEGIN
	SELECT  
		table_reservation_detail.id_table_id,TIMESTAMPDIFF(MINUTE,beginDate,NOW())
	FROM 
		table_reservation INNER JOIN table_reservation_detail 
		ON table_reservation.id=table_reservation_detail.id_table_reservation_id
	WHERE		
		DATE(endDate)='00-00-00'
		AND table_reservation.isActive= TRUE
		AND table_reservation_detail.isActive=TRUE		
		AND (TIMESTAMPDIFF(MINUTE,NOW(),beginDate)<=numHour );
		
		
 END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_get_status_party` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_get_status_party` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_get_status_party`(in _id int, in dt timestamp,in numHour int)
BEGIN
		SELECT table_reservation.party
		FROM table_reservation,table_reservation_detail
		WHERE table_reservation.id=table_reservation_detail.id_table_reservation_id AND
		(TIMESTAMPDIFF(HOUR,table_reservation.beginDate,dt)<numHour AND TIMESTAMPDIFF(HOUR,table_reservation.beginDate,dt)>-numHour)
		AND table_reservation_detail.id_table_id=_id
		AND 	table_reservation.party=TRUE
		AND table_reservation_detail.isActive=TRUE
		AND DATE(table_reservation.endDate)='00-00-00';
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_get_status_party_not_by_date` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_get_status_party_not_by_date` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_get_status_party_not_by_date`(in _id int)
BEGIN
		SELECT table_reservation.party
		FROM table_reservation,table_reservation_detail
		WHERE table_reservation.id=table_reservation_detail.id_table_reservation_id
		AND table_reservation_detail.id_table_id=_id
		AND 	table_reservation.party=TRUE
		AND table_reservation_detail.isActive=TRUE
		AND DATE(table_reservation.endDate)='00-00-00';
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_insert` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_insert` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_insert`(in stt boolean)
BEGIN
	insert into `table_reservation`(`status`,beginDate)
	values(stt,current_timestamp);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_insert_customerid` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_insert_customerid` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_insert_customerid`(IN stt BOOLEAN, in idCustomer int, in dt text,in _party boolean, in _prepay int)
BEGIN
	INSERT INTO table_reservation(`status`,id_customer_id,beginDate,party,prepay)
	VALUES(stt,idCustomer,dt,_party,_prepay);
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
	AND table_reservation.status=FALSE
	and table_reservation_detail.isActive=true
	AND DATE(endDate)='00-00-00';
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_test_by_date_time` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_test_by_date_time` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_test_by_date_time`(in idTable int,IN test timestamp,in _time int)
BEGIN
	SELECT  
		table_reservation.id,
		id_customer_id,
		customer.name,
		DATE_FORMAT(beginDate,'%d/%m/%Y %H:%i')
	FROM 
		table_reservation 
		LEFT JOIN table_reservation_detail ON table_reservation.id=table_reservation_detail.id_table_reservation_id
		LEFT JOIN customer ON table_reservation.id_customer_id=customer.id		
	WHERE
		table_reservation_detail.id_table_id=idTable
		AND DATE(endDate)='00-00-00'
		AND table_reservation.isActive= TRUE
		AND table_reservation_detail.isActive=TRUE		
		and (timestampdiff(hour,test,table_reservation.beginDate)<_time and TIMESTAMPDIFF(HOUR,test,table_reservation.beginDate)>-_time);
		
 END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_update_begindate` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_update_begindate` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_update_begindate`(IN idReservation INT)
BEGIN
	UPDATE table_reservation 
	SET table_reservation.beginDate=NOW()
	WHERE table_reservation.id=idReservation;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_update_begindate_customer_prepay` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_update_begindate_customer_prepay` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_update_begindate_customer_prepay`(IN idReservation INT, in dt Timestamp,in idcustomer int, in _prepay int)
BEGIN
	UPDATE table_reservation 
	SET table_reservation.beginDate=dt,
			table_reservation.id_customer_id=idcustomer,
			table_reservation.prepay=_prepay
	WHERE table_reservation.id=idReservation;
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

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_update_enddate`(IN idReservation INT, in dt timestamp)
BEGIN
	update table_reservation 
	set table_reservation.endDate=dt,table_reservation.isActive=false
	where table_reservation.id=idReservation;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_update_status` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_update_status` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_update_status`(IN idReservation INT)
BEGIN
	UPDATE 
	table_reservation 
	SET table_reservation.status=true 
	WHERE id=idReservation;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_view_list` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_view_list` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_view_list`()
BEGIN
	SELECT 
	re.id AS 'ID',
	GROUP_CONCAT(tb.name) AS 'Bàn',	
	cus.name AS 'Khách Hàng',
	cus.phone AS 'SĐT',
	DATE_FORMAT(re.beginDate,'%d/%m/%Y %H:%i') AS 'Ngày Nhận',
	tb.id,
	re.beginDate,
	re_de.id,
	FORMAT(re.prepay,0) AS 'Đưa trước',
	cus.id
	FROM 
	table_reservation AS re,
	table_reservation_detail AS re_de,
	`table` AS tb,
	customer AS cus
	
	WHERE 
	re.id=re_de.id_table_reservation_id 
	AND re_de.id_table_id=tb.id 
	AND re.id_customer_id=cus.id	
	AND re.`isActive`=TRUE
	AND re_de.`isActive`=TRUE
	AND tb.`isActive`=TRUE	
	AND re.status=FALSE
	GROUP BY re.id	
	ORDER BY begindate ASC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_view_list_search` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_view_list_search` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_view_list_search`(in search text)
BEGIN
	SELECT 
	re.id AS 'ID',
	tb.name AS 'Bàn',	
	cus.name AS 'Khách Hàng',
	cus.phone AS 'SĐT',
	DATE_FORMAT(re.beginDate,'%d/%m/%Y %H:%i') AS 'Ngày Nhận',
	tb.id as idtb,
	re.beginDate,
	re_de.id,
	FORMAT(re.prepay,0) AS 'Đưa trước',
	cus.id	
		FROM 
		table_reservation AS re,
		table_reservation_detail AS re_de,
		`table` AS tb,
		customer AS cus
		WHERE 
		re.id=re_de.id_table_reservation_id 
		AND re_de.id_table_id=tb.id 
		AND re.id_customer_id=cus.id
		AND cus.`isActive`=TRUE
		AND re.`isActive`=TRUE
		AND re_de.`isActive`=TRUE
		AND tb.`isActive`=TRUE	
		AND re.status=FALSE
		and (tb.name like CONCAT('%',search,'%') or cus.name like concat('%',search,'%') or cus.phone like CONCAT('%',search,'%'))
		ORDER BY begindate ASC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_view_list_search_by_date` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_view_list_search_by_date` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_view_list_search_by_date`(IN search timestamp)
BEGIN
	SELECT 
	re.id AS 'ID',
	tb.name AS 'Bàn',	
	cus.name AS 'Khách Hàng',
	cus.phone AS 'SĐT',
	DATE_FORMAT(re.beginDate,'%d/%m/%Y %H:%i') AS 'Ngày Nhận',
	tb.id AS idtb,
	re.beginDate,
	re_de.id,
	FORMAT(re.prepay,0) AS 'Đưa trước',
	cus.id	
		FROM 
		table_reservation AS re,
		table_reservation_detail AS re_de,
		`table` AS tb,
		customer AS cus
		WHERE 
		re.id=re_de.id_table_reservation_id 
		AND re_de.id_table_id=tb.id 
		AND re.id_customer_id=cus.id
		AND cus.`isActive`=TRUE
		AND re.`isActive`=TRUE
		AND re_de.`isActive`=TRUE
		AND tb.`isActive`=TRUE	
		AND re.status=FALSE
		AND datediff(beginDate,search)=0
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
	unit.name as 'ĐVT',
	id_service_cost_id AS 'Đơn Giá',
	(number*id_service_cost_id) AS 'Thành Tiền',
	service.id,
	case table_service.status when true then 'Đã Phục Vụ'
	when false then 'Đang Chế Biến' end as 'Trạng Thái'
	
FROM 
	service inner join table_service on service.id=table_service.id_service_id
	left join unit on service.unit=unit.id
	inner join  table_reservation on table_reservation.id=table_service.id_table_reservation_id
WHERE 
	service.isActive=true
	and table_service.isActive=true
	and table_reservation.isActive=true
	and 
	table_service.id_table_reservation_id=idReser;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_getByIdReservation_invoice` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_getByIdReservation_invoice` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_getByIdReservation_invoice`(IN idReser INT)
BEGIN
SELECT
	table_service.id AS 'Mã',
	service.name AS 'Tên Dịch Vụ',
	number AS 'Số Lượng',	
	unit.name AS 'ĐVT',
	id_service_cost_id AS 'Đơn Giá',
	(number*id_service_cost_id) AS 'Thành Tiền',
	service.id
	
FROM 
	service INNER JOIN table_service ON service.id=table_service.id_service_id
	LEFT JOIN unit ON service.unit=unit.id
WHERE 	
	table_service.isActive=TRUE AND	
	table_service.id_table_reservation_id=idReser
	AND table_service.status=FALSE ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_get_status` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_get_status` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_get_status`(
	IN id_reservation int
	)
BEGIN
	select  table_service.status from
	table_reservation join table_service on table_reservation.id=table_service.id_table_reservation_id
	where table_reservation.id=id_reservation;
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

/* Procedure structure for procedure `table_service_statitics` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_statitics` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_statitics`(IN dt1 TIMESTAMP, IN dt2 TIMESTAMP)
BEGIN
	SELECT 		
		service_type.name AS 'Loại Dịch Vụ',
		service.name AS 'Tên Dịch Vụ',
		unit.name AS 'Đơn Vị Tính',
		SUM(table_service.number) AS 'Số Lượng Bán',
		SUM(table_service.id_service_cost_id*table_service.number) AS 'Tổng Tiền'			
FROM 
	table_service JOIN service ON table_service.id_service_id=service.id
	JOIN service_type ON service.type=service_type.id
	JOIN unit ON service.unit=unit.id
	JOIN table_reservation ON table_service.id_table_reservation_id=table_reservation.id
	WHERE table_reservation.beginDate BETWEEN DATE(dt1) AND DATE(dt2)
GROUP BY table_service.id_service_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_service_total_payment` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_total_payment` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_total_payment`(in idReservation int)
BEGIN
	SELECT SUM(id_service_cost_id*number) AS total
	FROM table_service
	WHERE table_service.id_table_reservation_id=idReservation
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

/* Procedure structure for procedure `table_service_update_id_reservation_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_service_update_id_reservation_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_service_update_id_reservation_id`(IN idReservation1 INT, in idReservation2 int)
BEGIN
	UPDATE table_service
	SET id_table_reservation_id=idReservation1
	WHERE id_table_reservation_id=idReservation2;
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

/* Procedure structure for procedure `table_test_table_name` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_test_table_name` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_test_table_name`(in tbName varchar(30))
BEGIN
		SELECT table.name FROM `table` WHERE table.name=tbName and table.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_type_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_type_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_type_add`(IN tbTypeName VARCHAR(30))
BEGIN
		INSERT INTO table_type(table_type.name) values(tbTypeName);
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_type_getAll` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_type_getAll` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_type_getAll`()
BEGIN
		SELECT * FROM table_type WHERE table_type.isActive=TRUE;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_type_get_max_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_type_get_max_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_type_get_max_id`()
BEGIN
	select max(id) as id from table_type;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_update` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_update` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_update`(
		iN tbName VARCHAR(30),
		IN tbType INT,
		IN tbLocation INT,
		IN chair INT,
		in idtable int
)
BEGIN
	update `table` set 
		table.name=tbName,
		table.type=tbType,
		table.location=tbLocation,
		table.numOfChair=chair
		where table.id=idtable;
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_update_name` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_update_name` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_update_name`(in tbName varchar(30), in idTable int)
BEGIN
		update `table` set table.name=tbName where table.id=idTable;
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

/* Procedure structure for procedure `table_update_status_by_name` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_update_status_by_name` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_update_status_by_name`(IN tbName text, IN state INT)
BEGIN
	UPDATE `table` SET `table`.`status`=state WHERE `table`.`name`=tbName;
END */$$
DELIMITER ;

/* Procedure structure for procedure `unit_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `unit_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `unit_add`(in _name varchar(30))
BEGIN
		insert into unit(unit.name) values(_name);
END */$$
DELIMITER ;

/* Procedure structure for procedure `unit_del` */

/*!50003 DROP PROCEDURE IF EXISTS  `unit_del` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `unit_del`(in _id int)
BEGIN
		update unit set unit.isActive=false where unit.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `unit_getAll` */

/*!50003 DROP PROCEDURE IF EXISTS  `unit_getAll` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `unit_getAll`()
begin
SELECT
	unit.id AS  'ID',
	unit.name AS 'Tên ĐVT',	
	unit.cast AS 'Gía Trị',
	unit.id_unit_id
FROM
	unit
WHERE
	unit.isActive=TRUE
	and
	unit.isParent=true
	ORDER BY unit.id DESC;
END */$$
DELIMITER ;

/* Procedure structure for procedure `unit_get_by_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `unit_get_by_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `unit_get_by_id`(in _id int)
begin
SELECT
	unit.id AS  'ID',
	unit.name AS 'Tên ĐVT',	
	unit.cast AS 'Gía Trị',	
	unit.id_unit_id,
	unit.isParent
FROM
	unit
WHERE
	unit.isActive=TRUE	
	and unit.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `unit_sub_get_by_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `unit_sub_get_by_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `unit_sub_get_by_id`(IN _id int)
BEGIN
	select * from unit where unit.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `unit_sub_get_by_sub_id` */

/*!50003 DROP PROCEDURE IF EXISTS  `unit_sub_get_by_sub_id` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `unit_sub_get_by_sub_id`(IN _id int)
BEGIN
select * from unit where  unit.id=_id
union
	select * from unit where unit.id_unit_id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `unit_test_name` */

/*!50003 DROP PROCEDURE IF EXISTS  `unit_test_name` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `unit_test_name`(in _name varchar(30))
BEGIN
		select * from unit where unit.name=_name and unit.isActive=true;
END */$$
DELIMITER ;

/* Procedure structure for procedure `unit_update` */

/*!50003 DROP PROCEDURE IF EXISTS  `unit_update` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `unit_update`(in _name varchar(30), in _id int)
BEGIN
		update unit
		set unit.name=_name
		where unit.id=_id;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
