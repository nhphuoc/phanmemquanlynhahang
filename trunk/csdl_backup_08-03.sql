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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `customer` */

insert  into `customer`(`id`,`name`,`sex`,`birthDay`,`phone`,`address`,`email`,`isActive`) values (1,'Nguyễn Hữu Phước','','1995-07-20','0977941517','Kiên Giang','it.nhphuoc@gmail.com',''),(2,'Trần Thanh Bình','','1991-09-09','0978678567','Hậu Giang','ttbinh@gmail.com','\0'),(3,'Huỳnh Lê','\0','1993-08-09','01688841937','Đồng Tháp','huynhle@gmail.com',''),(4,'Nguyễn Phương Khánh','','1992-03-05','01699907500','Hậu Giang','npkhanh@yahoo.com.vn',''),(5,'Nguyễn Minh Nam','','1980-04-12','0978378909','Hà Nội','mmnam@hotmail.com',''),(6,'Trần Thanh An Nam','','1959-12-24','046907890','Sóc Trăng','annamst@gmail.com',''),(7,'âcsc bạkbds','\0','1988-03-05','0977941517','ámklnlwef','it.nhphuoc@gmail.com.mm.xx','\0'),(8,'Cao Thanh Nam','','1989-04-08','0909189098','Số 119, Ấp 2, xã Bình An, huyện Kiên Lương, tỉnh Kiên Giang','ctnam@gmail.com',''),(9,'Lý Quốc Cường','','1990-10-26','','Hậu Giang','lqcuong@gmail.com',''),(10,'Hồ Duy Khánh','','2014-03-07','','','hdkhanh@gmail.com','');

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `invoice` */

insert  into `invoice`(`id`,`id_table_reservation_id`,`id_staff_id`,`date`,`cost`,`discount`,`note`,`isActive`) values (6,NULL,1,'2014-03-08 14:46:52',19118400,461600,'',''),(7,NULL,1,'2014-03-08 14:50:44',38697400,382600,'','');

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
  `unit` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `store` int(11) DEFAULT '0',
  `detail` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` text COLLATE utf8_unicode_ci,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  CONSTRAINT `service_ibfk_1` FOREIGN KEY (`type`) REFERENCES `service_type` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service` */

insert  into `service`(`id`,`name`,`type`,`unit`,`store`,`detail`,`image`,`isActive`) values (18,'Súp Chân Đà Điểu',9,'Bát',10,'','E:\\LUAN-VAN-2014\\image\\supchandadieu.jpg','\0'),(19,'Súp Thịt Đà Điểu',9,'Bát',9000,'','E:\\Menu 1.jpg',''),(20,'Súp Gân Đà Điểu',9,'Bát',8,'','D:\\Photos Center\\PHOTOS TONG HOP\\1.JPG',''),(21,'Salad Tổng Hợp',9,'Bát',10,'','E:\\LUAN-VAN-2014\\image\\salad.jpg',''),(23,'Nộm Bình Minh',9,'Đĩa',1,'','E:\\LUAN-VAN-2014\\image\\nombinhmonh.jpg',''),(25,'Nộm Gân Cần Guốc',9,'Đĩa',0,'','E:\\Menu 1.jpg',''),(26,'Bánh tét',9,'Đĩa',10,'fgbngfbgfhvccccccccccccccccccc','D:\\Photos Center\\phuoc2 (2).png',''),(30,'dàvs',11,'fsdf',3,'sdfdsf','C:\\Users\\nhphuoc\\Desktop\\av.png','\0'),(34,'uih   buu',17,'dk',10,'ng','D:\\Photos Center\\309949_205038499640604_88110147_n.jpg','\0');

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
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service_cost` */

insert  into `service_cost`(`id`,`id_service_id`,`beginDate`,`cost`,`isActive`) values (38,18,'2014-03-04 21:17:41',15000,''),(39,19,'2014-03-04 22:35:04',15000,''),(40,20,'2014-03-04 22:37:27',15000,''),(41,21,'2014-03-04 22:40:47',20000,''),(43,23,'2014-03-04 22:46:50',35000,''),(45,18,'2014-03-05 00:07:48',15000,''),(46,23,'2014-03-05 00:09:16',35000,''),(49,19,'2014-03-05 00:10:23',15000,''),(50,19,'2014-03-05 08:55:32',2000000,''),(52,25,'2014-03-05 10:44:35',35000,''),(53,26,'2014-03-05 15:38:01',12000,''),(54,19,'2014-03-05 15:47:30',2000000,''),(58,30,'2014-03-06 16:12:45',35345,''),(62,34,'2014-03-06 16:43:14',38490,''),(63,34,'2014-03-06 16:50:16',38490,''),(64,21,'2014-03-06 16:52:24',20000,''),(65,34,'2014-03-06 16:54:21',38490,''),(66,34,'2014-03-06 16:56:28',38490,''),(67,34,'2014-03-06 16:57:18',38490,''),(68,26,'2014-03-06 16:59:03',15000,''),(69,26,'2014-03-06 22:40:00',15000,''),(71,26,'2014-03-06 22:41:53',15000,''),(72,23,'2014-03-08 15:53:51',35000,''),(73,23,'2014-03-08 15:56:08',35000,''),(74,23,'2014-03-08 16:05:46',35000,''),(75,21,'2014-03-08 16:46:08',20000,''),(76,21,'2014-03-08 16:47:02',20000,''),(77,21,'2014-03-08 16:49:16',20000,''),(78,21,'2014-03-08 16:50:48',20000,''),(79,21,'2014-03-08 16:59:49',20000,''),(80,23,'2014-03-08 16:59:55',35000,''),(81,20,'2014-03-08 17:00:58',50000,''),(82,20,'2014-03-08 17:01:04',50000,''),(83,20,'2014-03-08 17:01:35',60000,'');

/*Table structure for table `service_type` */

DROP TABLE IF EXISTS `service_type`;

CREATE TABLE `service_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `service_type` */

insert  into `service_type`(`id`,`name`,`isActive`) values (9,'Khai Vị',''),(10,'Nướng - Hấp',''),(11,'Hầm - Lẩu',''),(12,'Chiên - Xào',''),(13,'Cơm - Mì - Cháo - Sốt',''),(14,'Canh Rau',''),(15,'Rượu',''),(16,'Bia',''),(17,'Nước Ngọt','');

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `staff` */

insert  into `staff`(`id`,`name`,`sex`,`birthDay`,`phone`,`address`,`email`,`pin`,`isActive`) values (1,'Nguyễn Hữu Phước','','2013-06-11','0977941517','Hà Tiên Kiên Giang','it.nhphuoc@gmail.com',NULL,''),(2,'Trịnh Quốc Chung',NULL,'1989-06-21','0989789098','Châu Thành A, Hậu Giang','trinhqchung@gmail.com',NULL,''),(3,'Võ Hồng Loan','\0','1990-09-19','01689909090','Sóc Trăng','vhloang@gmail.com','',''),(4,'dsfsdf','\0','1991-07-09','324324242','ưefwef','','',''),(5,'dsfsdf','','1991-09-07','324324242','ưefwef','','',''),(6,'dsfsdf sabnd shc hd jfv','','1991-07-09','324324242','ưefwef','','',''),(7,'dsfsdf','','1991-07-09','324324242','ưefwef','jdsfs@gmail.com','',''),(8,'dsfsdf sabnd shc hd jfv','','1991-09-07','324324242','kiên giang','','',''),(9,'dsfsdf sabnd shc hd jfv','','1991-07-09','324324242','kiên giang xxxxxxxxxxxx','','',''),(10,'dsfsdf sabnd shc hd jfv','','1991-07-09','324324242','kiên giang','','',''),(11,'dsfsdf','','1991-09-07','324324242','ưefwef bbbbbbbbbbbbbbbbbbbbbbbbb','jdsfs@gmail.com','',''),(12,'dsfsdf sabnd shc hd jfv','','1991-07-09','324324242','kiên','','','');

/*Table structure for table `system_log` */

DROP TABLE IF EXISTS `system_log`;

CREATE TABLE `system_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `info` int(11) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `system_log` */

insert  into `system_log`(`id`,`info`,`date`) values (6,1,'2014-03-03 11:31:31'),(7,2,'2014-03-03 11:43:09'),(8,3,'2014-03-03 11:44:48'),(9,4,'2014-03-03 12:05:32'),(10,5,'2014-03-03 12:05:39'),(11,6,'2014-03-03 12:37:10'),(12,6,'2014-03-03 12:44:11');

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
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table` */

insert  into `table`(`id`,`name`,`type`,`location`,`numOfChair`,`status`,`isActive`) values (1,'Bàn 01',1,1,10,0,''),(2,'Bàn 02',1,1,10,0,''),(3,'Bàn 03',2,1,10,0,''),(4,'Bàn 04',2,1,10,0,''),(5,'Bàn 05',1,1,10,0,''),(6,'Bàn 06',3,1,10,0,''),(7,'Bàn 07',2,1,10,0,''),(8,'Bàn 08',2,1,10,0,''),(9,'Bàn 09',3,1,10,0,''),(10,'Bàn 10',1,1,10,0,''),(11,'Bàn 11',3,1,10,0,''),(12,'Bàn 12',3,1,10,1,''),(13,'Bàn 13',2,1,10,0,''),(14,'Bàn 14',2,1,10,0,''),(15,'Bàn 15',2,1,10,0,''),(16,'Bàn 16',1,1,10,0,''),(17,'Bàn 17',2,1,10,0,''),(18,'Bàn 18',2,1,10,0,''),(19,'Bàn 19',2,1,10,0,''),(20,'Bàn 20',2,1,10,0,''),(21,'Bàn 21',2,1,10,0,''),(22,'Bàn 22',3,1,10,0,''),(23,'Bàn 23',2,1,10,0,''),(24,'Bàn 24',2,1,10,0,''),(25,'Bàn 25',2,1,10,0,''),(26,'Bàn 26',1,2,10,0,''),(27,'Bàn 27',2,2,10,0,''),(28,'Bàn 28',1,2,10,0,''),(29,'Bàn 29',1,2,10,0,''),(30,'Bàn 30',2,2,10,0,''),(31,'Bàn 331',1,2,10,0,''),(32,'Bàn 31',1,2,10,0,''),(33,'Bàn 32',1,2,10,0,''),(34,'Bàn 33',1,2,10,0,''),(35,'Bàn 35',2,2,10,0,''),(36,'Bàn 36',2,2,10,0,''),(37,'Bàn 37',2,2,10,0,''),(38,'Bàn 38',1,2,10,0,''),(39,'Bàn 39',1,2,10,0,''),(40,'Bàn 40',2,2,10,0,''),(41,'Bàn 41',1,2,10,0,''),(42,'Bàn 42',2,2,10,0,''),(43,'Bàn 43',1,2,10,0,''),(44,'Bàn 44',1,2,10,0,''),(45,'Bàn 45',1,2,10,0,''),(46,'Bàn 46',1,2,10,0,''),(47,'Bàn 47',1,2,10,0,''),(48,'Bàn 48',2,2,10,0,''),(49,'Bàn 49',2,2,10,0,''),(50,'Bàn 50',2,2,10,0,''),(51,'Bàn 51',1,3,10,0,''),(52,'Bàn 52',1,3,10,0,''),(53,'Bàn 53',1,3,10,0,''),(54,'Bàn 54',2,3,10,0,''),(55,'Bàn 55',2,3,10,0,''),(56,'Bàn 56',2,3,10,0,''),(57,'Bàn 57',2,3,10,0,''),(58,'Bàn 58',2,3,10,0,''),(59,'Bàn 59',2,3,10,0,''),(60,'Bàn 60',1,3,10,0,''),(61,'Bàn 61',1,3,10,0,''),(62,'Bàn 62',1,3,10,0,''),(63,'Bàn 63',1,3,10,0,''),(64,'Bàn 64',1,3,10,0,''),(65,'Bàn 65',1,3,10,0,''),(66,'Bàn 66',1,3,10,0,''),(67,'Bàn 67',1,3,10,0,''),(68,'Bàn 68',1,3,10,0,''),(69,'Bàn 69',1,3,10,0,''),(70,'Bàn 70',1,3,10,0,''),(71,'Bàn 71',1,3,10,0,''),(72,'Bàn 72',1,3,10,0,''),(73,'Bàn 73',1,3,10,0,''),(74,'Bàn 74',1,3,10,0,''),(75,'Bàn 75',1,3,10,0,''),(76,'Bàn 76',1,3,10,0,''),(77,'Bàn 77',1,3,10,0,''),(78,'Bàn 78',1,3,10,0,''),(79,'Bàn 79',1,3,10,0,''),(80,'Bàn 80',1,3,10,0,''),(81,'Bàn 81',1,3,10,0,''),(82,'Bàn 82',1,3,10,0,''),(83,'Bàn 83',1,4,10,0,''),(84,'Bàn 84',1,4,10,0,''),(85,'Bàn 85',1,4,10,0,''),(86,'Bàn 86',1,4,10,0,''),(87,'Bàn 87',1,4,10,0,''),(88,'Bàn 88',1,4,10,0,''),(89,'Bàn 89',1,4,10,0,''),(90,'Bàn 90',1,4,10,0,''),(91,'Bàn 91',1,4,10,0,''),(92,'Bàn 92',1,4,10,0,''),(93,'Bàn 93',1,4,10,0,''),(94,'Bàn 94',1,4,10,0,''),(95,'Bàn 95',1,4,10,0,''),(96,'Bàn 96',1,4,10,0,''),(97,'Bàn 97',1,4,10,0,''),(98,'Bàn 98',1,4,10,0,''),(99,'Bàn 99',1,4,10,0,''),(100,'Bàn 100',1,4,10,0,''),(101,'Ban 101',1,4,10,0,''),(102,'Bàn 102',2,4,3,0,''),(103,'Bàn 103',2,4,3,0,''),(104,'Bàn 104',2,4,4,0,''),(105,'Bàng 104',1,5,3,0,''),(106,'Bàn 105',3,3,1,0,''),(107,'Bàn 106',3,4,1,0,''),(108,'Bàng Mới',2,2,1,0,'');

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
  `beginDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` bit(1) DEFAULT b'1',
  `party` bit(1) DEFAULT b'0',
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `id_customer_id` (`id_customer_id`),
  CONSTRAINT `table_reservation_ibfk_1` FOREIGN KEY (`id_customer_id`) REFERENCES `customer` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_reservation` */

insert  into `table_reservation`(`id`,`id_customer_id`,`beginDate`,`endDate`,`status`,`party`,`isActive`) values (87,9,'2014-03-08 16:13:05','0000-00-00 00:00:00','','\0','');

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
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_reservation_detail` */

insert  into `table_reservation_detail`(`id`,`id_table_id`,`id_table_reservation_id`,`isActive`) values (278,12,87,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_service` */

insert  into `table_service`(`id`,`id_table_reservation_id`,`id_service_id`,`id_service_cost_id`,`number`,`note`,`status`,`isActive`) values (24,87,25,35000,10,NULL,'',''),(26,87,23,35000,9,NULL,'',''),(28,87,20,60000,2,NULL,'','');

/*Table structure for table `table_type` */

DROP TABLE IF EXISTS `table_type`;

CREATE TABLE `table_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isActive` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `table_type` */

insert  into `table_type`(`id`,`name`,`isActive`) values (1,'Vip',''),(2,'Medium',''),(3,'Special',''),(4,'Expensive','');

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

/* Procedure structure for procedure `invoice_insert_all` */

/*!50003 DROP PROCEDURE IF EXISTS  `invoice_insert_all` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `invoice_insert_all`(in id_reservation int, in id_staff int, in _cost int, in _discount int, in _note text)
BEGIN
	insert into invoice(id_table_reservation_id,id_staff_id,`date`,cost,discount,note)
	values(id_reservation,id_staff,now(),_cost,_discount,_note);
END */$$
DELIMITER ;

/* Procedure structure for procedure `service_add` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_add` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_add`(in _name varchar(50), in _type int, in _unit varchar(30), in _store int,in _detail text, in img text)
BEGIN
	insert into service(service.name,service.type,service.unit,service.store,service.detail,service.image)
	values(_name,_type,_unit,_store,_detail,img);
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
	SELECT service.id,
			 service.name,
			 service_type.name as type_name,			 
			 service.unit,
			 service_cost.cost as dongia,
			 service.store,
			 service.detail,
			 service.image,
			 service_type.id AS idType
			 			 			 
	FROM service INNER JOIN tb ON service.id=tb.id_service_id
			INNER JOIN service_cost ON tb.id_service_id=service_cost.id_service_id
			INNER JOIN service_type ON service.type=service_type.id
	WHERE tb.bgDate=service_cost.beginDate AND service.isActive=TRUE
	order by service.name asc;
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
			 service.unit as 'ĐVT'
	FROM service INNER JOIN tb ON service.id=tb.id_service_id
			INNER JOIN service_cost ON tb.id_service_id=service_cost.id_service_id
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

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_update_by_id`(in _id int,IN _name VARCHAR(50), IN _type INT, IN _unit VARCHAR(30), IN _store INT,IN _detail TEXT, IN img TEXT)
BEGIN
	update service set 
				service.name=_name,
				service.type=_type,
				service.unit=_unit,
				service.store=_store,
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

/* Procedure structure for procedure `service_update_store` */

/*!50003 DROP PROCEDURE IF EXISTS  `service_update_store` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `service_update_store`(in _id int,in num int)
BEGIN
		update service set service.store=service.store-(num) where service.id=_id;
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
	tb.status <> 1;
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

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_get_by_not_reservation`(in dt timestamp)
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
AND DATEDIFF(dt,beginDate)=0);
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
	SELECT * FROM table_location WHERE table_location.isActive=TRUE;
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

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_get_list_warning`()
BEGIN
	SELECT  
		table_reservation.id,
		id_customer_id,
		customer.name,
		table_reservation_detail.id_table_id,
		table.id
	FROM 
		table_reservation 
		LEFT JOIN table_reservation_detail ON table_reservation.id=table_reservation_detail.id_table_reservation_id
		LEFT JOIN customer ON table_reservation.id_customer_id=customer.id
		INNER JOIN `table`	ON table.id=table_reservation_detail.id_table_id
	WHERE		
		DATE(endDate)='00-00-00'
		AND table_reservation.isActive= TRUE
		AND table_reservation_detail.isActive=TRUE		
		AND TIMESTAMPDIFF(MINUTE,beginDate,NOW())>=0;
		
		
 END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_get_status_party` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_get_status_party` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_get_status_party`(in _id int, in dt timestamp)
BEGIN
		SELECT table_reservation.party
		FROM table_reservation,table_reservation_detail
		WHERE table_reservation.id=table_reservation_detail.id_table_reservation_id AND
		DATEDIFF(table_reservation.beginDate,DATE(dt))=0
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
	insert into `table_reservation`(`status`)
	values(stt);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `table_reservation_insert_customerid` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_insert_customerid` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_insert_customerid`(IN stt BOOLEAN, in idCustomer int, in dt text,in _party boolean)
BEGIN
	INSERT INTO table_reservation(`status`,id_customer_id,beginDate,party)
	VALUES(stt,idCustomer,dt,_party);
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

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_test_by_date_time`(in idTable int,IN test timestamp)
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
		and (datediff(beginDate,test)=0 and hour(beginDate)=hour(test));
		
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

/* Procedure structure for procedure `table_reservation_update_begindate_date` */

/*!50003 DROP PROCEDURE IF EXISTS  `table_reservation_update_begindate_date` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `table_reservation_update_begindate_date`(IN idReservation INT, in dt text)
BEGIN
	UPDATE table_reservation 
	SET table_reservation.beginDate=dt
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
	re_de.id
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
		tb.id,
		re.beginDate,
		re_de.id
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
		tb.id,
		re.beginDate,
		re_de.id
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
	FORMAT(id_service_cost_id,0) AS 'Đơn Giá',
	FORMAT((number*id_service_cost_id),0) AS 'Thành Tiền',
	service.id
	
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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
