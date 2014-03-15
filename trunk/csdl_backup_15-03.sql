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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `discount` */

insert  into `discount`(`id`,`name`,`type`,`beginDate`,`endDate`,`conditions`,`valueInvoice`,`value`,`detail`,`isActive`) values (18,'Khuyến Mãi Tháng 3',0,'2014-03-10 13:35:00','2014-03-15 13:33:00',0,0,10,'','\0'),(19,'KM',0,'2014-03-13 23:48:00','2014-03-15 13:33:00',0,100000,12,'','\0'),(20,'KM',0,'2014-03-14 00:03:00','2014-03-15 13:33:00',0,100000,5,'','');

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `discount_detail` */

insert  into `discount_detail`(`id`,`id_invoice_id`,`id_discount_id`,`isActive`) values (14,21,20,''),(15,22,20,'');

/*Table structure for table `distributor` */

DROP TABLE IF EXISTS `distributor`;

CREATE TABLE `distributor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(13) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `distributor` */

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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `invoice` */

insert  into `invoice`(`id`,`id_table_reservation_id`,`id_staff_id`,`date`,`cost`,`discount`,`note`,`isActive`) values (19,212,1,'2014-03-15 09:18:06',0,0,'',''),(20,216,1,'2014-03-16 10:54:16',32000,0,'',''),(21,217,1,'2014-03-15 10:57:28',136800,7200,'',''),(22,218,1,'2014-03-15 10:57:55',106400,5600,'','');

/*Table structure for table `payment_invoice` */

DROP TABLE IF EXISTS `payment_invoice`;

CREATE TABLE `payment_invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `id_staff_id` int(11) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
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
  KEY `unit` (`unit`),
  CONSTRAINT `raw_material_ibfk_1` FOREIGN KEY (`unit`) REFERENCES `unit` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `raw_material` */

insert  into `raw_material`(`id`,`name`,`number`,`unit`,`isActive`) values (1,'Thịt Bò',3.491,11,''),(2,'Thịt Heo',3.598,11,''),(3,'Cá Hồi',0.5,8,''),(4,'Cá điêu hồng',10,11,''),(5,'Nem Chua',4,NULL,'\0'),(6,'Nem Chua',89,8,''),(7,'Nếp',9.5,11,''),(8,'Gạo Trắng',50,11,''),(9,'Mỡ Heo',10,11,''),(10,'Thịt Ba Rọi',0.2,11,''),(11,'Tôm Sú',1.2,11,''),(12,'Măng',2.45,11,''),(13,'Bia Sài Gòn Đỏ',29,11,''),(14,'Thịt Gà',5,11,''),(15,'Lá Cách',1,11,'');

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
  CONSTRAINT `raw_material_invoice_ibfk_2` FOREIGN KEY (`id_istributor_id`) REFERENCES `distributor` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `raw_material_invoice_ibfk_1` FOREIGN KEY (`id_staff_id`) REFERENCES `staff` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `raw_material_invoice` */

insert  into `raw_material_invoice`(`id`,`date`,`id_staff_id`,`id_istributor_id`,`isActive`,`note`) values (1,'2014-03-14',2,NULL,'',NULL);

/*Table structure for table `raw_material_invoice_detail` */

DROP TABLE IF EXISTS `raw_material_invoice_detail`;

CREATE TABLE `raw_material_invoice_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_raw_material_id` int(11) DEFAULT NULL,
  `id_raw_material_invoice` int(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_raw_material_id` (`id_raw_material_id`),
  KEY `raw_material_invoice_detail_ibfk_2` (`id_raw_material_invoice`),
  CONSTRAINT `raw_material_invoice_detail_ibfk_1` FOREIGN KEY (`id_raw_material_id`) REFERENCES `raw_material` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `raw_material_invoice_detail_ibfk_2` FOREIGN KEY (`id_raw_material_invoice`) REFERENCES `raw_material_invoice` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `raw_material_invoice_detail` */

insert  into `raw_material_invoice_detail`(`id`,`id_raw_material_id`,`id_raw_material_invoice`,`number`,`cost`,`isActive`) values (1,1,1,3,50000,''),(2,2,1,6,80000,'');

/*Table structure for table `recipes` */

DROP TABLE IF EXISTS `recipes`;

CREATE TABLE `recipes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_raw_material_id` int(11) DEFAULT NULL,
  `id_service_id` int(11) DEFAULT NULL,
  `number` float DEFAULT NULL,
  `note` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_raw_material_id` (`id_raw_material_id`),
  KEY `id_service_id` (`id_service_id`),
  CONSTRAINT `recipes_ibfk_1` FOREIGN KEY (`id_raw_material_id`) REFERENCES `raw_material` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `recipes_ibfk_2` FOREIGN KEY (`id_service_id`) REFERENCES `service` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `recipes` */

insert  into `recipes`(`id`,`id_raw_material_id`,`id_service_id`,`number`,`note`,`isActive`) values (2,6,25,11,NULL,''),(8,2,21,0.01,NULL,''),(9,3,25,9,NULL,''),(11,7,23,0.4,NULL,''),(12,1,23,0.009,NULL,''),(13,2,23,0.002,NULL,''),(15,2,25,0.4,NULL,''),(16,7,25,0.1,NULL,''),(17,10,36,0.15,NULL,''),(18,11,36,0.2,NULL,''),(19,12,36,0.15,NULL,''),(20,13,37,1,NULL,''),(21,13,41,1,NULL,''),(22,12,21,1,NULL,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service` */

insert  into `service`(`id`,`name`,`type`,`unit`,`detail`,`image`,`isActive`) values (18,'Súp Chân Đà Điểu',9,11,'','E:\\LUAN-VAN-2014\\image\\supchandadieu.jpg','\0'),(19,'Súp Thịt Đà Điểu',9,10,'','E:\\Menu 1.jpg',''),(20,'Súp Gân Đà Điểu',9,9,'','D:\\Photos Center\\PHOTOS TONG HOP\\1.JPG',''),(21,'Salad Tổng Hợp',9,9,'','E:\\LUAN-VAN-2014\\image\\salad.jpg',''),(23,'Nộm Bình Minh',9,9,'','E:\\LUAN-VAN-2014\\image\\nombinhmonh.jpg',''),(25,'Nộm Gân Cần Guốc',9,9,'','E:\\Menu 1.jpg',''),(26,'Bánh tét',9,8,'fgbngfbgfhvccccccccccccccccccc','D:\\Photos Center\\phuoc2 (2).png',''),(30,'dàvs',11,11,'sdfdsf','C:\\Users\\nhphuoc\\Desktop\\av.png','\0'),(34,'uih   buu',17,11,'ng','D:\\Photos Center\\309949_205038499640604_88110147_n.jpg','\0'),(36,'Tôm Thịt Kho Măng',18,9,'','E:\\Menu 1.jpg',''),(37,'Bia Sài Gòn Đỏ',16,4,'','E:\\LUAN-VAN-2014\\image\\saigondo.jpg',''),(41,'Bia heniken 111',16,4,'sadcvsd sdvds','E:\\Menu 1.jpg',''),(42,'egfwfwef',15,12,'fefwefwf','images/hai huoc.jpg',''),(43,'davdsvsfdvsdvs',9,10,'sfvbgfg','images/Photo1134.png',''),(44,'esdfg',9,10,'esdfgv','images/tom-thit-kho-mang-32ss379.jpg',''),(45,'Ốc Xào',12,9,'lkjhgc hcytcygcgh','E:\\thit-ga-nau-canh-la-cach.jpg',''),(46,'klsjzxcv ',9,12,'ádfhgfvxzv','E:\\thit-ga-nau-canh-la-cach.jpg',''),(47,'kljhgx',9,9,'ưedfgbn','images/oc-rau.jpg',''),(48,'o;likujhgf',9,9,'','images/thit-ga-nau-canh-la-cach.jpg',''),(50,'hlgjygfugugu',9,10,'qewfew wegewg3','images/medium-c72481dc7612488e8b34d0257f74c12a-650.jpg','');

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
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service_cost` */

insert  into `service_cost`(`id`,`id_service_id`,`beginDate`,`cost`,`isActive`) values (38,18,'2014-03-04 21:17:41',15000,''),(39,19,'2014-03-04 22:35:04',15000,''),(40,20,'2014-03-04 22:37:27',15000,''),(41,21,'2014-03-04 22:40:47',20000,''),(43,23,'2014-03-04 22:46:50',35000,''),(45,18,'2014-03-05 00:07:48',15000,''),(46,23,'2014-03-05 00:09:16',35000,''),(49,19,'2014-03-05 00:10:23',15000,''),(50,19,'2014-03-05 08:55:32',2000000,''),(52,25,'2014-03-05 10:44:35',35000,''),(53,26,'2014-03-05 15:38:01',12000,''),(54,19,'2014-03-05 15:47:30',2000000,''),(58,30,'2014-03-06 16:12:45',35345,''),(62,34,'2014-03-06 16:43:14',38490,''),(63,34,'2014-03-06 16:50:16',38490,''),(64,21,'2014-03-06 16:52:24',20000,''),(65,34,'2014-03-06 16:54:21',38490,''),(66,34,'2014-03-06 16:56:28',38490,''),(67,34,'2014-03-06 16:57:18',38490,''),(68,26,'2014-03-06 16:59:03',15000,''),(69,26,'2014-03-06 22:40:00',15000,''),(71,26,'2014-03-06 22:41:53',15000,''),(72,23,'2014-03-08 15:53:51',35000,''),(73,23,'2014-03-08 15:56:08',35000,''),(74,23,'2014-03-08 16:05:46',35000,''),(75,21,'2014-03-08 16:46:08',20000,''),(76,21,'2014-03-08 16:47:02',20000,''),(77,21,'2014-03-08 16:49:16',20000,''),(78,21,'2014-03-08 16:50:48',20000,''),(79,21,'2014-03-08 16:59:49',20000,''),(80,23,'2014-03-08 16:59:55',35000,''),(81,20,'2014-03-08 17:00:58',50000,''),(82,20,'2014-03-08 17:01:04',50000,''),(83,20,'2014-03-08 17:01:35',60000,''),(84,20,'2014-03-08 23:55:31',60000,''),(85,23,'2014-03-08 23:55:36',35000,''),(87,36,'2014-03-11 11:01:30',35000,''),(88,36,'2014-03-11 11:03:06',45000,''),(89,36,'2014-03-11 11:03:12',45000,''),(90,19,'2014-03-11 13:54:30',20000,''),(91,19,'2014-03-11 13:54:34',20000,''),(92,37,'2014-03-11 15:46:31',15000,''),(94,20,'2014-03-11 16:30:46',60000,''),(95,37,'2014-03-11 16:31:16',15000,''),(96,26,'2014-03-11 16:31:25',15000,''),(97,23,'2014-03-11 16:31:41',35000,''),(98,25,'2014-03-11 16:31:46',35000,''),(99,21,'2014-03-11 16:31:54',20000,''),(100,19,'2014-03-11 16:32:14',20000,''),(102,37,'2014-03-11 16:34:27',15000,''),(103,37,'2014-03-11 16:34:33',15000,''),(104,36,'2014-03-11 16:36:41',45000,''),(105,23,'2014-03-11 16:37:56',35001,''),(106,26,'2014-03-11 16:39:36',7000,''),(107,37,'2014-03-11 19:06:25',15000,''),(108,37,'2014-03-11 19:08:20',13000,''),(112,41,'2014-03-11 20:06:21',15000,''),(113,41,'2014-03-11 20:06:41',16000,''),(114,41,'2014-03-11 20:06:47',16000,''),(115,37,'2014-03-11 20:15:50',15000,''),(116,37,'2014-03-12 08:51:18',16000,''),(117,42,'2014-03-14 17:10:02',44444,''),(118,43,'2014-03-14 21:45:15',4555,''),(119,44,'2014-03-14 21:46:53',56756,''),(120,45,'2014-03-14 21:50:21',6666,''),(121,46,'2014-03-14 22:56:40',3434,''),(122,45,'2014-03-14 22:58:39',6666,''),(123,45,'2014-03-14 22:59:19',6666,''),(124,47,'2014-03-14 22:59:55',9876,''),(125,45,'2014-03-14 23:00:28',6666,''),(126,48,'2014-03-14 23:00:49',7653,''),(128,50,'2014-03-15 08:47:41',40000,'');

/*Table structure for table `service_type` */

DROP TABLE IF EXISTS `service_type`;

CREATE TABLE `service_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service_type` */

insert  into `service_type`(`id`,`name`,`isActive`) values (9,'Khai Vị',''),(10,'Nướng - Hấp',''),(11,'Hầm - Lẩu',''),(12,'Chiên - Xào',''),(13,'Cơm - Mì - Cháo - Sốt',''),(14,'Canh Rau',''),(15,'Rượu',''),(16,'Bia',''),(17,'Nước Ngọt',''),(18,'Kho','');

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `staff` */

insert  into `staff`(`id`,`name`,`sex`,`birthDay`,`phone`,`address`,`email`,`pin`,`isActive`) values (1,'Nguyễn Hữu Phước','','2013-06-11','0977941517','Hà Tiên Kiên Giang','it.nhphuoc@gmail.com',NULL,''),(2,'Trịnh Quốc Chung',NULL,'1989-06-21','0989789098','Châu Thành A, Hậu Giang','trinhqchung@gmail.com',NULL,''),(3,'Võ Hồng Loan','\0','1990-09-19','01689909090','Sóc Trăng','vhloang@gmail.com','',''),(4,'dsfsdf','\0','1991-07-09','324324242','ưefwef','','',''),(5,'dsfsdf','','1991-09-07','324324242','ưefwef','','',''),(6,'dsfsdf sabnd shc hd jfv','','1991-07-09','324324242','ưefwef','','',''),(7,'dsfsdf','','1991-07-09','324324242','ưefwef','jdsfs@gmail.com','',''),(8,'dsfsdf sabnd shc hd jfv','','1991-09-07','324324242','kiên giang','','',''),(9,'dsfsdf sabnd shc hd jfv','','1991-07-09','324324242','kiên giang xxxxxxxxxxxx','','',''),(10,'dsfsdf sabnd shc hd jfv','','1991-07-09','324324242','kiên giang','','',''),(11,'dsfsdf','','1991-09-07','324324242','ưefwef bbbbbbbbbbbbbbbbbbbbbbbbb','jdsfs@gmail.com','',''),(12,'dsfsdf sabnd shc hd jfv','','1991-07-09','324324242','kiên','','','');

/*Table structure for table `system_log` */

DROP TABLE IF EXISTS `system_log`;

CREATE TABLE `system_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `info` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `system_log` */

insert  into `system_log`(`id`,`info`,`date`) values (6,'1','2014-03-03 11:31:31'),(7,'2','2014-03-03 11:43:09'),(8,'3','2014-03-03 11:44:48'),(9,'4','2014-03-03 12:05:32'),(10,'5','2014-03-03 12:05:39'),(11,'6','2014-03-03 12:37:10'),(12,'6','2014-03-03 12:44:11'),(13,'1','2014-03-15 10:45:54');

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
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table` */

insert  into `table`(`id`,`name`,`type`,`location`,`numOfChair`,`status`,`isActive`) values (111,'Bàn 1',1,1,10,1,''),(113,'Bàn 2',1,1,1,1,''),(114,'Bàn 3',1,1,1,1,''),(115,'Bàn 4',1,1,1,1,''),(116,'Bàn 5',1,1,1,1,''),(117,'Bàn 6',1,1,1,1,''),(118,'Bàn 7',1,1,1,1,''),(119,'Bàn 8',1,1,1,3,''),(120,'Bàn 9',1,1,1,1,''),(121,'Bàn 10',1,1,1,1,''),(122,'Bàn 11',1,1,1,1,''),(123,'Bàn 12',1,1,1,1,''),(124,'Bàn 13',1,1,1,1,''),(125,'Bàn 14',1,1,1,1,''),(126,'Bàn 15',1,1,1,1,''),(127,'Bàn 16',1,1,1,1,''),(128,'Bàn 17',1,1,1,1,''),(129,'Bàn 18',1,1,1,1,''),(130,'Bàn 19',1,1,1,1,''),(131,'Bàn 20',1,1,1,1,''),(132,'Bàn 21',1,1,1,1,''),(133,'Bàn 22',1,1,1,1,''),(134,'Bàn 23',1,1,1,1,''),(135,'Bàn 24',1,1,1,1,''),(136,'Bàn 25',1,1,1,1,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_reservation` */

insert  into `table_reservation`(`id`,`id_customer_id`,`prepay`,`beginDate`,`endDate`,`status`,`party`,`isActive`) values (212,NULL,0,'2014-03-15 09:17:52','2014-03-15 09:18:06','','\0','\0'),(213,6,0,'2014-03-13 09:19:00','0000-00-00 00:00:00','\0','\0',''),(214,6,500000,'2014-03-15 09:21:00','2014-03-15 09:24:17','\0','','\0'),(215,NULL,0,'2014-03-15 09:34:03','2014-03-15 09:34:10','','\0','\0'),(216,NULL,0,'2014-03-15 10:15:37','2014-03-15 10:54:16','','\0','\0'),(217,NULL,0,'2014-03-15 10:57:20','2014-03-15 10:57:28','','\0','\0'),(218,NULL,0,'2014-03-15 10:57:38','2014-03-15 10:57:55','','\0','\0');

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
) ENGINE=InnoDB AUTO_INCREMENT=475 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_reservation_detail` */

insert  into `table_reservation_detail`(`id`,`id_table_id`,`id_table_reservation_id`,`isActive`) values (465,123,212,''),(466,119,213,''),(467,111,214,''),(468,113,214,''),(469,114,214,''),(470,115,214,''),(471,120,215,''),(472,122,216,''),(473,126,217,''),(474,126,218,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_service` */

insert  into `table_service`(`id`,`id_table_reservation_id`,`id_service_id`,`id_service_cost_id`,`number`,`note`,`status`,`isActive`) values (18,216,41,16000,1,NULL,'\0',''),(19,216,37,16000,1,NULL,'\0',''),(20,217,37,16000,9,NULL,'\0',''),(21,218,37,16000,7,NULL,'\0','');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_type` */

insert  into `table_type`(`id`,`name`,`isActive`) values (1,'Vip',''),(2,'Medium',''),(3,'Special',''),(4,'Expensive',''),(5,NULL,'');

/*Table structure for table `unit` */

DROP TABLE IF EXISTS `unit`;

CREATE TABLE `unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `unit` */

insert  into `unit`(`id`,`name`,`isActive`) values (2,'g',''),(3,'Chai','\0'),(4,'Lon',''),(5,'Thùng',''),(6,'Ly',''),(7,'Chai 2','\0'),(8,'Cái',''),(9,'Đĩa',''),(10,'Chén',''),(11,'Kg',''),(12,'Chai','');

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
	CONCAT(table.name,' - ',table_location.name) AS 'Bàn  - Tầng',
	case discount.type when 0 then FORMAT(discount.value,0) else '-' end AS 'Khuyến Mãi(%)',
	case discount.type when 0 then '-' else FORMAT(discount.value,0) end AS 'Khuyến Mãi(VND)',
	FORMAT(invoice.discount,0) AS 'Giảm Giá',
	FORMAT(invoice.cost,0) AS 'Thanh Toán',
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
	end;	
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
	CONCAT('Trong Ngày ',DATE_FORMAT(invoice.date,'%d/%m/%Y')) AS 'Thời Gian',
	COUNT(id) AS 'Số Hóa Đơn',
	format(SUM(discount),0) AS 'Tổng Tiền Giảm Giá',
	format(SUM(cost),0) AS 'Tổng Tiền Thu'
	FROM invoice
	WHERE date(invoice.date) BETWEEN date(dtStart)  AND date(dtEnd)
	GROUP BY DAY(invoice.date);
			
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
		update raw_material set
		raw_material.isActive=false
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

/* Procedure structure for procedure `raw_material_get_number` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_get_number` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_get_number`(IN _id INT)
BEGIN		
		SELECT raw_material.number
		FROM raw_material
		WHERE raw_material.id=_id
		AND raw_material.isActive=TRUE;
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

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_update`(in _name varchar(50), in _number float, in _unit int, in _id int)
BEGIN
		update raw_material set
		raw_material.name=_name,
		raw_material.number=_number,
		raw_material.unit=_unit
	where raw_material.id=_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `raw_material_update_number` */

/*!50003 DROP PROCEDURE IF EXISTS  `raw_material_update_number` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `raw_material_update_number`(in _id int, in _number float)
BEGIN		
		update raw_material set raw_material.number=raw_material.number-(_number)
		where raw_material.id=_id
		and raw_material.number>=_number;			
END */$$
DELIMITER ;

/* Procedure structure for procedure `recipes_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `recipes_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `recipes_add`(in _id_raw_material int, in _id_service int, in _number float)
BEGIN
	insert into recipes(recipes.id_raw_material_id,recipes.id_service_id,recipes.number)
	values(_id_raw_material,_id_service,_number);
END */$$
DELIMITER ;

/* Procedure structure for procedure `recipes_count_by_idService` */

/*!50003 DROP PROCEDURE IF EXISTS  `recipes_count_by_idService` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `recipes_count_by_idService`(IN idService INT, in id_rawmaterial int)
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
	recipes.id as 'ID',
	raw_material.name as 'Tên NL',
	unit.name as 'ĐVT',
	recipes.number as 'Số Lượng',
	raw_material.id
		
FROM 
	recipes INNER JOIN raw_material ON recipes.id_raw_material_id=raw_material.id
	INNER JOIN service ON recipes.id_service_id=service.id
	LEFT JOIN unit ON unit.id=raw_material.unit
	WHERE recipes.isActive=TRUE
	AND service.isActive=TRUE
	AND raw_material.isActive=TRUE
	AND recipes.id_service_id=_id
	order by recipes.id desc;
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
			 format(service_cost.cost,0) as 'Đơn Giá',			 
			 service.detail as 'Chi Tiết',
			 service.image,
			 service_type.id AS idType,
			 unit.id
			 			 			 
	FROM service INNER JOIN tb ON service.id=tb.id_service_id
			INNER JOIN service_cost ON tb.id_service_id=service_cost.id_service_id
			INNER JOIN service_type ON service.type=service_type.id
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
			 format(service_cost.cost,0)  AS 'Đơn Giá',
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
			 service.unit AS 'ĐVT'
	FROM service INNER JOIN tb ON service.id=tb.id_service_id
			INNER JOIN service_cost ON tb.id_service_id=service_cost.id_service_id
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
			 service.store AS'Tồn Kho',
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

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_get_by_not_reservation`(in dt timestamp, in num int)
BEGIN
	SELECT table.id,table.name
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
AND (timestampdiff(hour,dt,beginDate)<num and TIMESTAMPDIFF(HOUR,dt,beginDate)>-num));
END */$$
DELIMITER ;

/* Procedure structure for procedure `table_get_by_not_reservation_1` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_get_by_not_reservation_1` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_get_by_not_reservation_1`(in dt timestamp, in num int,in _location int)
BEGIN
	SELECT table.id,table.name
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
		(TIMESTAMPDIFF(HOUR,table_reservation.beginDate,dt)<numHour AND TIMESTAMPDIFF(HOUR,table_reservation.beginDate,NOW())>-numHour)
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
	tb.name AS 'Bàn',	
	cus.name AS 'Khách Hàng',
	cus.phone AS 'SĐT',
	DATE_FORMAT(re.beginDate,'%d/%m/%Y %H:%i') AS 'Ngày Nhận',
	tb.id,
	re.beginDate,
	re_de.id,
	format(re.prepay,0) as 'Đưa trước',
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
	and re.status=false
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
	FORMAT(id_service_cost_id,0) AS 'Đơn Giá',
	FORMAT((number*id_service_cost_id),0) AS 'Thành Tiền',
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
	FORMAT(id_service_cost_id,0) AS 'Đơn Giá',
	FORMAT((number*id_service_cost_id),0) AS 'Thành Tiền',
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
BEGIN
		select
	unit.id as  'ID',
	unit.name as 'Tên ĐVT'
from
	unit
where
	unit.isActive=true
	order by unit.id desc;
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
