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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `account` */

insert  into `account`(`id`,`id_staff_id`,`username`,`password`,`type`,`isActive`) values (6,2,'nbminh','c20ad4d76fe97759aa27a0c99bff6710',1,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `customer` */

insert  into `customer`(`id`,`name`,`sex`,`birthDay`,`phone`,`address`,`email`,`isActive`) values (1,'Nguyễn Hữu Phước','','1995-07-20','0977941517','Kiên Giang','it.nhphuoc@gmail.com',''),(2,'Trần Thanh Bình','','1991-09-09','0978678567','Hậu Giang','ttbinh@gmail.com','\0'),(3,'Huỳnh Lê','\0','1993-08-09','01688841937','Đồng Tháp','huynhle@gmail.com',''),(4,'Nguyễn Phương Khánh','','1992-03-05','01699907500','Hậu Giang','npkhanh@yahoo.com.vn',''),(5,'Nguyễn Minh Nam','','1980-04-12','0978378909','Hà Nội','mmnam@hotmail.com',''),(6,'Trần Thanh An Nam','','1959-12-24','046907890','Sóc Trăng','annamst@gmail.com',''),(7,'âcsc bạkbds','\0','1988-03-05','0977941517','ámklnlwef','it.nhphuoc@gmail.com.mm.xx','\0'),(8,'Cao Thanh Nam1','','1989-04-08','0909189098','Số 119, Ấp 2, xã Bình An, huyện Kiên Lương, tỉnh Kiên Giang','ctnam@gmail.com',''),(9,'Lý Quốc Cường','','1990-10-26','','Hậu Giang','lqcuong@gmail.com',''),(10,'Hồ Duy Khánh','','2014-03-07','','','hdkhanh@gmail.com',''),(11,'asfcsacwq wefwefewfwf','\0','1989-04-08','32423325','ascefewfwef','abc@gmail.com','\0');

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

insert  into `discount`(`id`,`name`,`type`,`beginDate`,`endDate`,`conditions`,`valueInvoice`,`value`,`detail`,`isActive`) values (1,'KM',1,'2014-03-24 16:17:00','2014-03-25 16:15:00',1,5000,50000,'','\0'),(2,'KMM',0,'2014-03-24 16:19:00','2014-03-25 16:17:00',0,0,4,'','');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `distributor` */

insert  into `distributor`(`id`,`name`,`address`,`phone`,`email`,`isActive`) values (1,'Tường An','48/5 Phan Huy Ích, phường 15, quận Tân Bình, Tp. Hồ Chí Minh, Việt Nam. ','(84.8) 381539',' tuongan@tuongan.com.v',''),(2,'An Bình',NULL,NULL,NULL,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `invoice` */

insert  into `invoice`(`id`,`id_table_reservation_id`,`id_staff_id`,`date`,`cost`,`discount`,`note`,`isActive`) values (19,69,1,'2014-03-21 17:53:38',50000,0,'',''),(20,70,1,'2014-03-21 17:55:13',150000,0,'',''),(21,72,1,'2014-03-21 18:03:41',75000,0,'',''),(22,74,1,'2014-03-21 18:04:14',50000,0,'',''),(23,75,1,'2014-03-21 18:05:33',75000,0,'',''),(24,77,1,'2014-03-21 18:06:13',75000,0,'',''),(25,78,1,'2014-03-21 18:09:50',175000,0,'',''),(26,82,1,'2014-03-22 09:55:57',75000,0,'',''),(27,81,1,'2014-03-22 09:56:19',0,0,'',''),(28,83,1,'2014-03-22 10:02:41',0,0,'',''),(29,84,1,'2014-03-22 10:03:45',0,0,'',''),(30,86,1,'2014-03-24 00:05:43',30000,0,'',''),(31,87,1,'2014-03-24 10:31:55',45000,0,'','');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `payment_invoice` */

/*Table structure for table `raw_material` */

DROP TABLE IF EXISTS `raw_material`;

CREATE TABLE `raw_material` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number` float DEFAULT NULL,
  `unit` int(11) DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `raw_material_ibfk_1` (`unit`),
  CONSTRAINT `raw_material_ibfk_1` FOREIGN KEY (`unit`) REFERENCES `unit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `raw_material` */

insert  into `raw_material`(`id`,`name`,`number`,`unit`,`isActive`) values (19,'Cá Tầm',10,21,''),(20,'Măng Củ',6.59999,21,''),(21,'Cà Chua',10,21,''),(22,'Dứa Xanh',1.29999,21,''),(24,'Ớt',9.65999,21,''),(25,'Hành',10,15,''),(29,'Cải Xanh',8.5,21,''),(30,'Cá Điêu Hồng',22,21,''),(31,'Cá Basa',11.5,21,''),(32,'Cá Hồi',10,21,''),(33,'Chanh',12,21,''),(34,'Nước Tương',13.95,28,''),(35,'Bia',20,20,''),(36,'snkjsnd',3,21,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `raw_material_invoice` */

insert  into `raw_material_invoice`(`id`,`date`,`id_staff_id`,`id_istributor_id`,`isActive`,`note`) values (1,'2014-03-24',1,1,'',''),(2,'2014-03-24',1,1,'',''),(3,'2014-03-24',1,1,'',''),(4,'2014-03-24',1,1,'',''),(5,'2014-03-24',1,1,'',''),(6,'2014-03-24',1,1,'',''),(7,'2014-03-24',1,1,'',''),(8,'2014-03-24',1,1,'','');

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `raw_material_invoice_detail` */

insert  into `raw_material_invoice_detail`(`id`,`id_raw_material_id`,`id_raw_material_invoice`,`number`,`cost`,`isActive`) values (1,22,1,5.5,50000,''),(2,22,2,1,45000,''),(3,34,3,1,150000,''),(4,34,4,0.5,150000,''),(5,34,5,5,15000,''),(6,36,6,3,50000,''),(7,34,7,2,15000,''),(8,35,8,5,8000,'');

/*Table structure for table `recipes` */

DROP TABLE IF EXISTS `recipes`;

CREATE TABLE `recipes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_raw_material_id` int(11) DEFAULT NULL,
  `id_service_id` int(11) DEFAULT NULL,
  `id_unit_id` int(11) DEFAULT NULL,
  `number` float DEFAULT NULL,
  `note` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `recipes_ibfk_3` (`id_unit_id`),
  KEY `recipes_ibfk_1` (`id_raw_material_id`),
  KEY `recipes_ibfk_2` (`id_service_id`),
  CONSTRAINT `recipes_ibfk_1` FOREIGN KEY (`id_raw_material_id`) REFERENCES `raw_material` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `recipes_ibfk_2` FOREIGN KEY (`id_service_id`) REFERENCES `service` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `recipes_ibfk_3` FOREIGN KEY (`id_unit_id`) REFERENCES `unit` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `recipes` */

insert  into `recipes`(`id`,`id_raw_material_id`,`id_service_id`,`id_unit_id`,`number`,`note`,`isActive`) values (1,22,56,22,50,NULL,''),(2,20,56,22,100,NULL,''),(3,24,56,22,10,NULL,''),(5,19,57,22,350,NULL,''),(8,29,59,22,100,NULL,''),(13,29,57,22,500,NULL,''),(14,22,57,22,500,NULL,''),(16,34,68,20,1,NULL,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service` */

insert  into `service`(`id`,`name`,`type`,`unit`,`detail`,`image`,`isActive`) values (56,'Cá tầm nấu măng chua',19,15,'iehfwe f ewfwe fw è','images/Beer-icon.png','\0'),(57,'Canh Chua',19,15,'weqnfjkwe fnwen fnwe fnwef','images/Beer-icon.png',''),(59,'Thịt Bò Xào',25,16,'wenfjbewjfwe fnwe fwefwe','images/a.png','\0'),(60,'klasnfknsd',25,15,'nwkjefbkjw  wejfbwenfnwefhwe fnw','images/Beer-icon.png',''),(65,'bsjc klsanckj',25,16,'acsdcsdcsdc','images/strawberry-icon.png',''),(66,'ànbvdsvdsvdsv',19,17,'acsa sdvsdvdw','images/Beer-icon.png',''),(67,';jvbnl\';lkjhgf',24,17,'zxcc','images/strawberry-icon.png',''),(68,'âcsc',24,16,'acsac','images/saladdaudam.jpg','');

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
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service_cost` */

insert  into `service_cost`(`id`,`id_service_id`,`beginDate`,`cost`,`isActive`) values (138,56,'2014-03-19 10:55:55',25000,''),(140,59,'2014-03-21 17:40:09',50000,''),(141,57,'2014-03-22 16:51:25',30000,''),(142,60,'2014-03-22 16:51:32',50000,''),(147,65,'2014-03-22 17:17:32',45000,''),(148,66,'2014-03-22 17:18:31',35000,''),(149,67,'2014-03-22 17:20:11',56000,''),(150,68,'2014-03-22 17:20:27',45000,''),(151,68,'2014-03-22 19:00:08',45000,''),(152,68,'2014-03-22 19:01:20',45000,''),(153,68,'2014-03-22 19:02:36',45000,'');

/*Table structure for table `service_type` */

DROP TABLE IF EXISTS `service_type`;

CREATE TABLE `service_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service_type` */

insert  into `service_type`(`id`,`name`,`isActive`) values (19,'Canh',''),(20,'Chiên',''),(21,'Gỏi - Cuốn',''),(22,'Hấp-Luộc',''),(23,'Kho',''),(24,'Nướng',''),(25,'Hấp Xào',''),(26,'Chiên','');

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

insert  into `staff`(`id`,`name`,`sex`,`birthDay`,`phone`,`address`,`email`,`pin`,`isActive`) values (1,'Nguyễn Hữu Phước','','1992-11-30','0977941517','Hà Tiên, Kiên Giang','it.nhphuoc@gmail.com','',''),(2,'Trần Bình Minh','','1990-11-30','0977941517','Châu Thành A, Hậu Giang','1051190036@stu.vttu.edu.vn','',''),(3,'Huỳnh Lê','\0','1993-09-08',NULL,'Lấp Vò',NULL,NULL,'');

/*Table structure for table `system_log` */

DROP TABLE IF EXISTS `system_log`;

CREATE TABLE `system_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `info` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `system_log` */

insert  into `system_log`(`id`,`info`,`date`) values (14,'1','2014-03-19 16:16:46');

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
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table` */

insert  into `table`(`id`,`name`,`type`,`location`,`numOfChair`,`status`,`isActive`) values (111,'Bàn 1',NULL,1,10,1,''),(113,'Bàn 2',NULL,1,1,1,''),(114,'Bàn 3',NULL,1,1,1,''),(115,'Bàn 4',NULL,2,1,1,''),(116,'Bàn 5',NULL,2,1,1,''),(117,'Bàn 6',NULL,1,1,1,''),(118,'Bàn 7',NULL,1,1,1,''),(119,'Bàn 8',NULL,2,1,1,''),(120,'Bàn 9',NULL,1,1,1,''),(121,'Bàn 10',NULL,1,1,1,''),(122,'Bàn 11',NULL,1,1,1,''),(123,'Bàn 12',NULL,1,1,1,''),(124,'Bàn 13',NULL,1,1,1,''),(125,'Bàn 14',NULL,1,1,1,''),(126,'Bàn 15',NULL,1,1,1,''),(127,'Bàn 16',NULL,1,1,1,''),(128,'Bàn 17',NULL,1,1,1,''),(129,'Bàn 18',NULL,1,1,1,''),(130,'Bàn 19',NULL,1,1,1,''),(131,'Bàn 20',NULL,1,1,1,''),(132,'Bàn 21',NULL,1,1,1,''),(133,'Bàn 22',NULL,1,1,1,''),(134,'Bàn 23',NULL,1,1,1,''),(135,'Bàn 24',NULL,1,1,1,''),(136,'Bàn 25',NULL,1,1,1,''),(138,'Bàn 26',2,1,10,1,'');

/*Table structure for table `table_location` */

DROP TABLE IF EXISTS `table_location`;

CREATE TABLE `table_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `detail` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_location` */

insert  into `table_location`(`id`,`name`,`detail`,`isActive`) values (1,'Tầng 1',NULL,''),(2,'Tầng 2',NULL,''),(3,'Tầng 3',NULL,''),(4,'Tầng 4',NULL,''),(5,'Tầng 5',NULL,''),(6,'Tầng 6',NULL,''),(7,'Tầng 7',NULL,''),(8,'Tầng 8',NULL,''),(9,'Tầng 9',NULL,''),(10,'Tầng 3',NULL,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_reservation` */

insert  into `table_reservation`(`id`,`id_customer_id`,`prepay`,`beginDate`,`endDate`,`status`,`party`,`isActive`) values (69,NULL,0,'2014-03-21 17:53:31','2014-03-21 17:53:38','','\0','\0'),(70,3,0,'2014-03-21 17:54:50','2014-03-21 17:55:13','','\0','\0'),(71,NULL,0,'2014-03-21 17:54:55','0000-00-00 00:00:00','','\0',''),(72,NULL,0,'2014-03-21 18:03:27','2014-03-21 18:03:41','','\0','\0'),(73,NULL,0,'2014-03-21 18:03:29','0000-00-00 00:00:00','','\0',''),(74,NULL,0,'2014-03-21 18:04:00','2014-03-21 18:04:14','','\0','\0'),(75,NULL,0,'2014-03-21 18:05:17','2014-03-21 18:05:33','','\0','\0'),(76,NULL,0,'2014-03-21 18:05:18','0000-00-00 00:00:00','','\0',''),(77,10,0,'2014-03-21 18:06:05','2014-03-21 18:06:13','','','\0'),(78,NULL,0,'2014-03-21 18:09:25','2014-03-21 18:09:50','','\0','\0'),(79,NULL,0,'2014-03-21 18:09:26','0000-00-00 00:00:00','','\0',''),(80,NULL,0,'2014-03-21 22:12:20','2014-03-21 22:13:56','','\0','\0'),(81,4,0,'2014-03-22 09:51:27','2014-03-22 09:56:19','','','\0'),(82,8,0,'2014-03-22 09:55:37','2014-03-22 09:55:57','','','\0'),(83,NULL,0,'2014-03-22 10:02:32','2014-03-22 10:02:41','','\0','\0'),(84,NULL,0,'2014-03-22 10:03:37','2014-03-22 10:03:45','','\0','\0'),(85,NULL,0,'2014-03-22 19:30:58','2014-03-24 09:00:05','','\0','\0'),(86,NULL,0,'2014-03-24 00:01:22','2014-03-24 00:05:43','','\0','\0'),(87,NULL,0,'2014-03-24 10:30:48','2014-03-24 10:31:55','','\0','\0');

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
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_reservation_detail` */

insert  into `table_reservation_detail`(`id`,`id_table_id`,`id_table_reservation_id`,`isActive`) values (152,120,69,''),(153,117,70,''),(154,116,70,''),(155,120,72,''),(156,119,74,''),(157,123,74,''),(158,123,75,''),(159,125,75,''),(160,111,77,''),(161,113,77,''),(162,114,77,''),(163,117,77,''),(164,118,77,''),(165,120,77,''),(166,121,77,''),(167,122,77,''),(168,124,77,''),(169,122,78,''),(170,119,78,''),(171,117,80,''),(172,111,81,''),(173,113,81,''),(174,114,81,''),(175,117,81,''),(176,118,81,''),(177,120,82,''),(178,121,82,''),(179,122,82,''),(180,123,82,''),(181,124,82,''),(182,125,82,''),(183,116,83,''),(184,116,84,''),(185,120,85,''),(186,119,86,''),(187,117,87,'');

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
  CONSTRAINT `table_service_ibfk_2` FOREIGN KEY (`id_service_id`) REFERENCES `service` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `table_service_ibfk_3` FOREIGN KEY (`id_table_reservation_id`) REFERENCES `table_reservation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_service` */

insert  into `table_service`(`id`,`id_table_reservation_id`,`id_service_id`,`id_service_cost_id`,`number`,`note`,`status`,`isActive`) values (26,69,59,50000,1,NULL,'\0',''),(27,70,59,50000,2,NULL,'\0',''),(28,70,56,25000,2,NULL,'\0',''),(29,72,59,50000,1,NULL,'\0',''),(30,72,56,25000,1,NULL,'\0',''),(31,74,59,50000,1,NULL,'\0',''),(32,75,59,50000,1,NULL,'\0',''),(33,75,56,25000,1,NULL,'\0',''),(34,77,59,50000,1,NULL,'\0',''),(35,77,56,25000,1,NULL,'\0',''),(36,78,59,50000,2,NULL,'\0',''),(37,78,56,25000,3,NULL,'\0',''),(38,82,59,50000,1,NULL,'\0',''),(39,82,56,25000,1,NULL,'\0',''),(40,86,57,30000,1,NULL,'\0',''),(41,87,68,45000,1,NULL,'\0','');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_type` */

insert  into `table_type`(`id`,`name`,`isActive`) values (1,'Vip',''),(2,NULL,'');

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
  KEY `unit_ibfk_1` (`id_unit_id`),
  CONSTRAINT `unit_ibfk_1` FOREIGN KEY (`id_unit_id`) REFERENCES `unit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `unit` */

insert  into `unit`(`id`,`name`,`id_unit_id`,`cast`,`isParent`,`isActive`) values (15,'Tô',15,1,'',''),(16,'Đĩa',16,1,'',''),(17,'Chén',17,1,'',''),(18,'Cái',18,1,'',''),(19,'Thùng',19,1,'',''),(20,'Lon',19,20,'\0',''),(21,'Kg',21,1,'',''),(22,'Gam',21,1000,'\0',''),(23,'Lon',20,1,'',''),(27,'Gam',22,1,'',''),(28,'Chai',19,24,'\0','');

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
FROM customer WHERE customer.isActive=TRUE;
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
				and customer.isActive=TRUE;
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

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `discount_get_by_date`()
BEGIN
		select * from discount where discount.isActive=true and (now() between beginDate and endDate) order by discount.beginDate desc limit 0,1;
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

/* Procedure structure for procedure `distributor_add_name` */

/*!50003 DROP PROCEDURE IF EXISTS  `distributor_add_name` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `distributor_add_name`(IN _name VARCHAR(50))
BEGIN
		insert into distributor(distributor.name) values(_name);
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
	(SELECT COUNT(raw_material_invoice.id)
	FROM raw_material_invoice 
	WHERE  (raw_material_invoice.date BETWEEN DATE(dtFrom) AND DATE(dtTo)) 
	GROUP BY raw_material_invoice.date) AS sohoadon,
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
	SELECT ngay AS 'Ngày','Hóa Đơn Nhập Hàng' AS 'HĐ Nhập Hàng/Phiếu Chi',sohoadon AS 'Số Lượng',TongTien AS 'Tổng Tiền' FROM tb1 
	UNION	
	SELECT ngay AS 'Ngày','Phiếu Chi',sophieuchi AS 'HĐ Nhập Hàng/Phiếu Chi',TongTien AS 'Tổng Tiền' FROM tb2;
	
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
	(SELECT COUNT(raw_material_invoice.id)
		FROM raw_material_invoice 
		WHERE  (month(raw_material_invoice.date) BETWEEN month(dtFrom) AND MONTH(dtTo)) 
		GROUP BY raw_material_invoice.date) AS sohoadon,
	SUM(raw_material_invoice_detail.cost*raw_material_invoice_detail.number) AS tongtiennhap
	FROM raw_material_invoice INNER JOIN raw_material_invoice_detail
	ON raw_material_invoice.id=raw_material_invoice_detail.id_raw_material_invoice
	where  (MONTH(raw_material_invoice.date) BETWEEN MONTH(dtFrom) AND MONTH(dtTo)) 
	GROUP BY raw_material_invoice.date);
	
  CREATE TEMPORARY TABLE tb2 AS(
	SELECT 
	payment_invoice.date  as ngay,
	COUNT(*) AS sophieuchi,
	SUM(payment_invoice.cost) AS tongtienchi 
	FROM payment_invoice 
	WHERE  (MONTH(payment_invoice.date) BETWEEN MONTH(dtFrom) AND MONTH(dtTo)) 
	GROUP BY payment_invoice.date
  );
			
  SELECT 
  date_format(ngay,'%d/%m/%Y') as 'Ngày',
	sohoadon AS 'Số Hóa Đơn Nhập',
	tongtiennhap AS 'Tổng Tiền Hóa Đơn Nhập (1)',
	sophieuchi AS 'Số Phiếu Chi',
	tongtienchi AS 'Tổng Tiền Phiếu Chi (2)',
	(tongtiennhap+tongtienchi) AS 'Tổng Cộng (1+2)'
 FROM tb1,tb2;
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
	(SELECT COUNT(raw_material_invoice.id)
		FROM raw_material_invoice 
		WHERE  (year(raw_material_invoice.date) BETWEEN year(dtFrom) AND YEAR(dtTo)) 
		GROUP BY raw_material_invoice.date) AS sohoadon,
	SUM(raw_material_invoice_detail.cost*raw_material_invoice_detail.number) AS tongtiennhap
	FROM raw_material_invoice INNER JOIN raw_material_invoice_detail
	ON raw_material_invoice.id=raw_material_invoice_detail.id_raw_material_invoice
	where  (year(raw_material_invoice.date) BETWEEN YEAR(dtFrom) AND YEAR(dtTo)) 
	GROUP BY raw_material_invoice.date);
	
  CREATE TEMPORARY TABLE tb2 AS(
	SELECT 
	payment_invoice.date  as ngay,
	COUNT(*) AS sophieuchi,
	SUM(payment_invoice.cost) AS tongtienchi 
	FROM payment_invoice 
	WHERE  (year(payment_invoice.date) BETWEEN YEAR(dtFrom) AND YEAR(dtTo)) 
	GROUP BY payment_invoice.date
  );
			
  SELECT 
  DATE_FORMAT(ngay,'%d/%m/%Y') as 'Ngày',
	sohoadon AS 'Số Hóa Đơn Nhập',
	tongtiennhap AS 'Tổng Tiền Hóa Đơn Nhập (1)',
	sophieuchi AS 'Số Phiếu Chi',
	tongtienchi AS 'Tổng Tiền Phiếu Chi (2)',
	(tongtiennhap+tongtienchi) AS 'Tổng Cộng (1+2)'
 FROM tb1,tb2;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_add`(in _name varchar(50), in _number float, in _unit int)
BEGIN
		insert into raw_material(raw_material.name,raw_material.number,raw_material.unit)
		values(_name,_number,_unit);
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
			unit.id		
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
	FORMAT(raw_material_invoice_detail.number,0) AS 'Số Lượng',
	FORMAT(raw_material_invoice_detail.cost,0) AS 'Đơn Giá',
	FORMAT((raw_material_invoice_detail.number*raw_material_invoice_detail.cost),0) AS 'Thành Tiền'
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
	FORMAT(SUM(raw_material_invoice_detail.number*raw_material_invoice_detail.cost),0) AS 'Tổng Tiền',
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

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_update`(in _name varchar(50), in _unit int, in _id int)
BEGIN
		update raw_material set
		raw_material.name=_name,		
		raw_material.unit=_unit
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
FROM table_location INNER JOIN `table` ON table_location.id=table.location
WHERE table.isActive=TRUE;
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
	SUM(table_service.number*recipes.number) AS SoLuong	
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
	and table_reservation.status=false
	and table_service.status=true
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
		table_reservation_detail.id_table_id
	FROM 
		table_reservation INNER JOIN table_reservation_detail ON table_reservation.id=table_reservation_detail.id_table_reservation_id
	WHERE		
		DATE(endDate)='00-00-00'
		AND table_reservation.isActive= TRUE
		AND table_reservation_detail.isActive=TRUE		
		AND TIMESTAMPDIFF(MINUTE,beginDate,now())=numHour;
		
		
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
	service.id
	
FROM 
	service inner join table_service on service.id=table_service.id_service_id
	left join unit on service.unit=unit.id
WHERE 
	service.isActive=true
	and table_service.isActive=true and	
	table_service.id_table_reservation_id=idReser
	and table_service.status=TRUE ;
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
