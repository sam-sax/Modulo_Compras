/*
SQLyog Ultimate v12.09 (32 bit)
MySQL - 10.4.32-MariaDB : Database - pms_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pms_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `pms_db`;

/*Table structure for table `detalles_orden_compra` */

DROP TABLE IF EXISTS `detalles_orden_compra`;

CREATE TABLE `detalles_orden_compra` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(12,2) NOT NULL,
  `precio_unitario` decimal(12,2) NOT NULL,
  `subtotal` decimal(12,2) DEFAULT NULL,
  `orden_id` bigint(20) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKplrx9cnikfjqupt28xx651mr5` (`orden_id`),
  KEY `FKsrrojuur7rpgcsyhjd62cs653` (`producto_id`),
  CONSTRAINT `FKplrx9cnikfjqupt28xx651mr5` FOREIGN KEY (`orden_id`) REFERENCES `ordenes_compra` (`id`),
  CONSTRAINT `FKsrrojuur7rpgcsyhjd62cs653` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `detalles_orden_compra` */

insert  into `detalles_orden_compra`(`id`,`cantidad`,`precio_unitario`,`subtotal`,`orden_id`,`producto_id`) values (1,'1.00','3500000.00','3500000.00',1,1),(2,'2.00','60000.00','120000.00',2,2),(3,'1.00','250000.00','250000.00',3,3),(4,'1.00','1500000.00','1500000.00',4,4),(5,'1.00','2200000.00','2200000.00',5,5);

/*Table structure for table `log_personas` */

DROP TABLE IF EXISTS `log_personas`;

CREATE TABLE `log_personas` (
  `id_log` int(11) NOT NULL AUTO_INCREMENT,
  `id_persona` int(11) DEFAULT NULL,
  `nombres` varchar(50) DEFAULT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `eliminado_por` varchar(50) DEFAULT NULL,
  `fecha_eliminacion` datetime DEFAULT current_timestamp(),
  `fecha_ingreso` datetime DEFAULT NULL,
  PRIMARY KEY (`id_log`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `log_personas` */

insert  into `log_personas`(`id_log`,`id_persona`,`nombres`,`apellidos`,`telefono`,`direccion`,`fecha_nacimiento`,`eliminado_por`,`fecha_eliminacion`,`fecha_ingreso`) values (4,18,'k2','k','dfdfsdfsdfdsfdsfsdfd','k','2002-03-21','admin','2025-11-23 00:55:09','2025-11-22 13:35:08'),(5,18,'k2','k','dfdfsdfsdfdsfdsfsdfd','k','2002-03-21','Sistema','2025-11-23 00:55:09','2025-11-22 13:35:08'),(6,19,'r','r','2','s','2002-03-21','admin','2025-11-23 01:14:40','2025-11-23 01:14:38'),(7,19,'r','r','2','s','2002-03-21','Sistema','2025-11-23 01:14:40','2025-11-23 01:14:38'),(8,17,'Sergio','Gimenez','0994349744','SIRIA 666','2002-03-21','Usuarioprueba','2025-11-23 01:27:05','2025-11-22 12:07:44'),(9,17,'Sergio','Gimenez','0994349744','SIRIA 666','2002-03-21','Sistema','2025-11-23 01:27:05','2025-11-22 12:07:44'),(10,20,'s','s','s','s','2002-02-21','Usuarioprueba','2025-11-23 01:27:16','2025-11-23 01:27:14'),(11,20,'s','s','s','s','2002-02-21','Sistema','2025-11-23 01:27:16','2025-11-23 01:27:14'),(12,16,'Se','e','2','dsds','2002-03-21','admin','2025-11-23 01:28:20','2025-11-22 09:53:20'),(13,16,'Se','e','2','dsds','2002-03-21','Sistema','2025-11-23 01:28:20','2025-11-22 09:53:20'),(14,26,'Camila','Alvarez','099449744','Daponte 664','2002-03-21','Sistema','2025-12-15 14:30:34','2025-12-15 14:27:49'),(15,24,'Lorena','Mendez','0994657934','Rodriguez de Francia 996','1995-08-25','Sistema','2025-12-15 14:30:36','2025-12-15 12:57:37'),(16,25,'Camila','Alvarez','0994347555','Daponte 6666','2002-03-21','Sistema','2025-12-15 14:30:38','2025-12-15 14:14:20'),(17,23,'Lil','Selva','0944734834','Noruega','2001-05-25','admin','2025-12-15 14:31:38','2025-12-09 16:35:21'),(18,23,'Lil','Selva','0944734834','Noruega','2001-05-25','Sistema','2025-12-15 14:31:38','2025-12-09 16:35:21'),(19,22,'Nahiara','Gonzales','09943432','Barrio Santa Rosa','2005-02-21','admin','2025-12-15 14:31:39','2025-12-09 15:55:17'),(20,22,'Nahiara','Gonzales','09943432','Barrio Santa Rosa','2005-02-21','Sistema','2025-12-15 14:31:39','2025-12-09 15:55:17'),(21,21,'Roberto','Espinola','0983434567','Ñemby Cañadita','1980-05-12','admin','2025-12-15 14:31:41','2025-11-24 12:52:49'),(22,21,'Roberto','Espinola','0983434567','Ñemby Cañadita','1980-05-12','Sistema','2025-12-15 14:31:41','2025-11-24 12:52:49'),(24,15,'Antonio','Britez','0994333444','Jara 663','1995-02-21','admin','2025-12-16 18:47:59','2025-11-21 14:42:10'),(25,15,'Antonio','Britez','0994333444','Jara 663','1995-02-21','Sistema','2025-12-16 18:47:59','2025-11-21 14:42:10'),(27,28,'Rodrigo','Britez','0994348733','Peru 343','2002-02-21','admin','2025-12-16 19:20:58','2025-12-16 19:05:19'),(28,28,'Rodrigo','Britez','0994348733','Peru 343','2002-02-21','Sistema','2025-12-16 19:20:58','2025-12-16 19:05:19'),(29,32,'Lila','Selva','094636433','Avda. Francia 343','1993-02-04','admin','2025-12-16 20:21:50','2025-12-16 20:17:19'),(30,32,'Lila','Selva','094636433','Avda. Francia 343','1993-02-04','Sistema','2025-12-16 20:21:50','2025-12-16 20:17:19'),(31,31,'Lila','Selva','094636433','Avda. Francia 343','1993-02-04','admin','2025-12-16 20:21:56','2025-12-16 20:12:13'),(32,31,'Lila','Selva','094636433','Avda. Francia 343','1993-02-04','Sistema','2025-12-16 20:21:56','2025-12-16 20:12:13'),(33,30,'Lila','Selva','094636433','Avda. Francia 343','1993-02-04','admin','2025-12-16 20:22:03','2025-12-16 20:12:02'),(34,30,'Lila','Selva','094636433','Avda. Francia 343','1993-02-04','Sistema','2025-12-16 20:22:03','2025-12-16 20:12:02'),(35,29,'Lila','Selva','094636433','Avda. Francia 343','1993-02-04','admin','2025-12-18 11:54:22','2025-12-16 19:21:53'),(36,29,'Lila','Selva','094636433','Avda. Francia 343','1993-02-04','Sistema','2025-12-18 11:54:22','2025-12-16 19:21:53'),(37,33,'Martin','Britez','0981288025','Peru 666','2003-02-12','admin','2025-12-18 11:55:08','2025-12-18 11:25:57'),(38,33,'Martin','Britez','0981288025','Peru 666','2003-02-12','Sistema','2025-12-18 11:55:08','2025-12-18 11:25:57'),(39,27,'Camila','Alvarez','099434945','dapinte 664','2002-03-21','admin','2025-12-18 11:58:58','2025-12-15 14:31:24'),(40,27,'Camila','Alvarez','099434945','dapinte 664','2002-03-21','Sistema','2025-12-18 11:58:58','2025-12-15 14:31:24'),(41,34,'lol','g','232','fddfsd','2002-02-21','admin','2025-12-18 12:03:17','2025-12-18 11:58:40'),(42,35,'Sergio','h','0944394744','sds','2001-03-21','admin','2025-12-18 13:10:07','2025-12-18 12:58:29'),(43,39,'dsd','sds','','',NULL,'Sergio','2025-12-19 11:45:07','2025-12-19 11:39:20'),(44,38,'dsd','sds','','',NULL,'Sergio','2025-12-19 11:45:13','2025-12-19 11:39:12'),(45,40,'Lalo','Montez','099434321','Daponte 666','1994-02-21','Sergio','2025-12-19 11:56:13','2025-12-19 11:45:03');

/*Table structure for table `log_usuarios` */

DROP TABLE IF EXISTS `log_usuarios`;

CREATE TABLE `log_usuarios` (
  `id_log` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `usuario` varchar(50) DEFAULT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `rol` varchar(20) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `modificado_por` varchar(50) DEFAULT NULL,
  `fecha_modificacion` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id_log`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `log_usuarios` */

insert  into `log_usuarios`(`id_log`,`id_usuario`,`usuario`,`clave`,`rol`,`estado`,`modificado_por`,`fecha_modificacion`) values (2,2,'admin','$2a$10$Quw4nICkNMRKXzVdc6yKoeI/Two57L9LKpwz36hYmwp4Cf2P2E2wW','ADMIN',1,'admin','2025-11-23 01:14:52'),(3,2,'admin','$2a$10$Quw4nICkNMRKXzVdc6yKoeI/Two57L9LKpwz36hYmwp4Cf2P2E2wW','ADMIN',1,'Sistema','2025-11-23 01:14:52'),(4,16,'Usuarioprueba','$2a$10$IKp7hLp03gFyptwjNCjcWuZP3x6bzdqa5o7c2detz1ANfx7p9I/7C','USER',1,'Usuarioprueba','2025-11-23 01:26:52'),(5,16,'Usuarioprueba','$2a$10$IKp7hLp03gFyptwjNCjcWuZP3x6bzdqa5o7c2detz1ANfx7p9I/7C','USER',1,'Sistema','2025-11-23 01:26:52'),(6,17,'adminprueba','$2a$10$Rom8spPlEil77GMLHj2b6uc5M88dEcH5GBEN87EbnlMpz133fXsAG','ADMIN',1,'adminprueba','2025-11-23 01:27:42'),(7,17,'adminprueba','$2a$10$Rom8spPlEil77GMLHj2b6uc5M88dEcH5GBEN87EbnlMpz133fXsAG','ADMIN',1,'Sistema','2025-11-23 01:27:42'),(8,2,'admin','$2a$10$oj.PVrPJRz.9WJisGMy6.OrGwEHNZJFXAYH7Bq52th0LYUaH8FFHK','ADMIN',1,'admin','2025-11-23 01:28:11'),(9,2,'admin','$2a$10$oj.PVrPJRz.9WJisGMy6.OrGwEHNZJFXAYH7Bq52th0LYUaH8FFHK','ADMIN',1,'Sistema','2025-11-23 01:28:11'),(10,2,'admin','$2a$10$rq9aTE0CcBxGOlc8cZ5hdOIZVliIKMjzKqKlKdnkXfRjaNLxv9zCq','ADMIN',1,'admin','2025-11-24 12:53:26'),(11,2,'admin','$2a$10$rq9aTE0CcBxGOlc8cZ5hdOIZVliIKMjzKqKlKdnkXfRjaNLxv9zCq','ADMIN',1,'Sistema','2025-11-24 12:53:26'),(12,2,'admin','$2a$10$j7W1dAhELEbFGbTVmay.9OiuPOBMEbjbubaX4ts.NJziNsQHI7p26','ADMIN',1,'admin','2025-11-24 12:53:53'),(13,2,'admin','$2a$10$j7W1dAhELEbFGbTVmay.9OiuPOBMEbjbubaX4ts.NJziNsQHI7p26','ADMIN',1,'Sistema','2025-11-24 12:53:53'),(14,2,'admin','$2a$10$D7FWiUVmA3YSQfxT0UY.jOOlS4kYOKESUhPWOsjbGKrkmMBXCr5De','ADMIN',1,'admin','2025-12-09 15:55:53'),(15,2,'admin','$2a$10$D7FWiUVmA3YSQfxT0UY.jOOlS4kYOKESUhPWOsjbGKrkmMBXCr5De','ADMIN',1,'Sistema','2025-12-09 15:55:53'),(16,2,'admin','$2a$10$YYiuBp9wmNIjbIh/ijNoK.7Crg305aQEX1zJ61hgw48X9LDaCQfiO','ADMIN',1,'admin','2025-12-09 16:25:00'),(17,2,'admin','$2a$10$YYiuBp9wmNIjbIh/ijNoK.7Crg305aQEX1zJ61hgw48X9LDaCQfiO','ADMIN',1,'Sistema','2025-12-09 16:25:00'),(18,2,'admin','$2a$10$SVzDvFjEn.l8WeB1FXk6xOdeQPN95lp3u3kR1E4GmnYrKi2N7ngvS','ADMIN',1,'admin','2025-12-15 12:58:13'),(19,2,'admin','$2a$10$SVzDvFjEn.l8WeB1FXk6xOdeQPN95lp3u3kR1E4GmnYrKi2N7ngvS','ADMIN',1,'Sistema','2025-12-15 12:58:13'),(20,24,'Lorena','$2a$10$ILKnHLinFPUzF7RvLIUlgOp4Gts/9dTx.1klBNx38AAlbes.Zu526','USER',1,'Lorena','2025-12-15 13:44:49'),(21,24,'Lorena','$2a$10$ILKnHLinFPUzF7RvLIUlgOp4Gts/9dTx.1klBNx38AAlbes.Zu526','USER',1,'Sistema','2025-12-15 13:44:49'),(22,27,'Camila','$2a$10$/TVS1E.NqRK.Nz4LQuwgbO2VMhqTzP4AdsErWT/3KFpYOwlxB9g9m','USER',1,'admin','2025-12-15 18:46:42'),(23,27,'Camila','$2a$10$/TVS1E.NqRK.Nz4LQuwgbO2VMhqTzP4AdsErWT/3KFpYOwlxB9g9m','USER',1,'Sistema','2025-12-15 18:46:42'),(24,27,'Camila','$2a$10$/TVS1E.NqRK.Nz4LQuwgbO2VMhqTzP4AdsErWT/3KFpYOwlxB9g9m','ADMIN',1,'admin','2025-12-15 18:46:45'),(25,27,'Camila','$2a$10$/TVS1E.NqRK.Nz4LQuwgbO2VMhqTzP4AdsErWT/3KFpYOwlxB9g9m','ADMIN',1,'Sistema','2025-12-15 18:46:45'),(26,27,'Camila','$2a$10$/TVS1E.NqRK.Nz4LQuwgbO2VMhqTzP4AdsErWT/3KFpYOwlxB9g9m','ADMIN',1,'admin','2025-12-15 18:46:51'),(27,27,'Camila','$2a$10$/TVS1E.NqRK.Nz4LQuwgbO2VMhqTzP4AdsErWT/3KFpYOwlxB9g9m','ADMIN',1,'Sistema','2025-12-15 18:46:51'),(28,2,'admin','$2a$10$SzMp8qWwPG7MVPZdw6YTEOG718dLnn90fGlv.Of.0DPhrEq/QrzNq','ADMIN',1,'admin','2025-12-15 18:56:19'),(29,2,'admin','$2a$10$SzMp8qWwPG7MVPZdw6YTEOG718dLnn90fGlv.Of.0DPhrEq/QrzNq','ADMIN',1,'Sistema','2025-12-15 18:56:19'),(30,26,'Antonio',NULL,'ADMIN',1,'admin','2025-12-15 19:02:02'),(31,20,'user','$2a$10$mXunlIii/VpuxSd6phY...O2fKdtqr5C7ocUpxMiZNLWMKc1n4r2C','ADMIN',1,'admin','2025-12-15 19:02:27'),(32,20,'user','$2a$10$mXunlIii/VpuxSd6phY...O2fKdtqr5C7ocUpxMiZNLWMKc1n4r2C','ADMIN',1,'Sistema','2025-12-15 19:02:27'),(33,20,'user','$2a$10$mXunlIii/VpuxSd6phY...O2fKdtqr5C7ocUpxMiZNLWMKc1n4r2C','USER',1,'admin','2025-12-15 19:02:44'),(34,20,'user','$2a$10$mXunlIii/VpuxSd6phY...O2fKdtqr5C7ocUpxMiZNLWMKc1n4r2C','USER',1,'Sistema','2025-12-15 19:02:44'),(35,30,'Rodrigo','$2a$10$kj60ofnLIwXVegLO2JG4I.V75rr7zzGK8X5U1DezT3qDJHlGTIgMO','USER',1,'admin','2025-12-16 19:20:50'),(36,30,'Rodrigo','$2a$10$kj60ofnLIwXVegLO2JG4I.V75rr7zzGK8X5U1DezT3qDJHlGTIgMO','USER',1,'Sistema','2025-12-16 19:20:50'),(37,30,'Rodrigo',NULL,'ADMIN',1,'admin','2025-12-16 19:20:52'),(38,29,'Camila',NULL,'USER',1,'admin','2025-12-16 20:00:06'),(39,32,'Lila',NULL,'USER',1,'admin','2025-12-16 20:22:53'),(40,2,'admin','$2a$10$gIylNVEhuddezpP0qPfmEesWYEE1v3W6O/vNfMSYl2CedhM6PaDNC','ADMIN',1,'admin','2025-12-16 20:23:06'),(41,2,'admin','$2a$10$gIylNVEhuddezpP0qPfmEesWYEE1v3W6O/vNfMSYl2CedhM6PaDNC','ADMIN',1,'Sistema','2025-12-16 20:23:06'),(43,33,'Martin','$2a$10$OYQNJsOrmr9iXkEOgTSzB.YeRCXYdaJLA0jlKDXfjQZZyVEMYiB6.','USER',1,'admin','2025-12-18 11:30:21'),(44,33,'Martin','$2a$10$OYQNJsOrmr9iXkEOgTSzB.YeRCXYdaJLA0jlKDXfjQZZyVEMYiB6.','USER',1,'Sistema','2025-12-18 11:30:21'),(45,33,'Martin','$2a$10$OYQNJsOrmr9iXkEOgTSzB.YeRCXYdaJLA0jlKDXfjQZZyVEMYiB6.','ADMIN',1,'admin','2025-12-18 11:30:44'),(46,33,'Martin','$2a$10$OYQNJsOrmr9iXkEOgTSzB.YeRCXYdaJLA0jlKDXfjQZZyVEMYiB6.','ADMIN',1,'Sistema','2025-12-18 11:30:44'),(50,33,'Martin',NULL,'USER',1,'admin','2025-12-18 11:43:31'),(51,33,'Martin','$2a$10$OYQNJsOrmr9iXkEOgTSzB.YeRCXYdaJLA0jlKDXfjQZZyVEMYiB6.','USER',1,'Sistema','2025-12-18 11:43:31'),(52,33,'Martin',NULL,'USER',0,'admin','2025-12-18 11:43:45'),(53,33,'Martin','$2a$10$OYQNJsOrmr9iXkEOgTSzB.YeRCXYdaJLA0jlKDXfjQZZyVEMYiB6.','USER',0,'Sistema','2025-12-18 11:43:45'),(56,33,'Martin',NULL,'USER',0,'admin','2025-12-18 11:53:45'),(57,31,'Camila',NULL,'USER',1,'admin','2025-12-18 11:54:06'),(58,34,'Camila','$2a$10$0xODBYNbx0vb3v8wVpri.u9jYvdx/ybNNtccCjmo.g/3tkVW2KR0W','USER',1,'admin','2025-12-18 11:55:51'),(59,34,'Camila','$2a$10$0xODBYNbx0vb3v8wVpri.u9jYvdx/ybNNtccCjmo.g/3tkVW2KR0W','USER',1,'Sistema','2025-12-18 11:55:51'),(60,34,'Camila','$2a$10$0xODBYNbx0vb3v8wVpri.u9jYvdx/ybNNtccCjmo.g/3tkVW2KR0W','ADMIN',1,'admin','2025-12-18 11:55:53'),(61,34,'Camila','$2a$10$0xODBYNbx0vb3v8wVpri.u9jYvdx/ybNNtccCjmo.g/3tkVW2KR0W','ADMIN',1,'Sistema','2025-12-18 11:55:53'),(62,34,'Camila',NULL,'USER',1,'admin','2025-12-18 11:55:56'),(63,35,'Sergio',NULL,'USER',1,'admin','2025-12-18 11:57:14'),(64,38,'Camil',NULL,'USER',1,'admin','2025-12-18 11:58:00'),(65,41,'Camila','$2a$10$YeTWoCxIJQsfYWMO4j4w4ekWvaoWKc2m3bfpv1.odRAszU5q0caAi','USER',1,'admin','2025-12-18 13:33:51'),(66,41,'Camila','$2a$10$YeTWoCxIJQsfYWMO4j4w4ekWvaoWKc2m3bfpv1.odRAszU5q0caAi','ADMIN',1,'admin','2025-12-18 13:33:53'),(67,41,'Camila',NULL,'USER',1,'admin','2025-12-18 13:34:02'),(68,42,'Camila','$2a$10$0FB2SGFCXb4lZToG3Y7Jl.Z1ymnGORKoLwUanvbMD9WBAtEtR0g7G','USER',1,'admin','2025-12-18 13:35:01'),(69,42,'Camila','$2a$10$0FB2SGFCXb4lZToG3Y7Jl.Z1ymnGORKoLwUanvbMD9WBAtEtR0g7G','ADMIN',1,'Camila','2025-12-18 13:35:37'),(70,43,'Lalo',NULL,'USER',1,'admin','2025-12-19 11:56:00'),(71,44,'Sergiovendedor',NULL,'VENDEDOR',1,'admin','2025-12-20 10:54:24'),(72,45,'Sergiovendedor',NULL,'VENDEDOR',1,'admin','2025-12-20 11:07:29'),(74,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','ADMIN',1,'admin','2025-12-20 11:12:37'),(75,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','VENDEDOR',1,'admin','2025-12-20 11:13:06'),(76,46,'Sergiovendor','$2a$10$3LkQR8tp/b2WKq8S4.ljMOXpLrcltL9k6lmxvZ12JQYlSfb1v8mNS','VENDEDOR',1,'admin','2025-12-20 11:13:13'),(77,46,'Sergiovendor','$2a$10$3LkQR8tp/b2WKq8S4.ljMOXpLrcltL9k6lmxvZ12JQYlSfb1v8mNS','ADMIN',1,'admin','2025-12-20 11:13:31'),(79,46,'Sergiovendor','$2a$10$3LkQR8tp/b2WKq8S4.ljMOXpLrcltL9k6lmxvZ12JQYlSfb1v8mNS','VENDEDOR',1,'admin','2025-12-20 11:23:02'),(80,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','USER',1,'admin','2025-12-20 11:23:37'),(81,46,'Sergiovendor','$2a$10$3LkQR8tp/b2WKq8S4.ljMOXpLrcltL9k6lmxvZ12JQYlSfb1v8mNS','USER',1,'admin','2025-12-20 11:23:40'),(82,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','VENDEDOR',1,'admin','2025-12-20 11:24:16'),(83,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','USER',1,'admin','2025-12-20 11:24:25'),(84,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','VENDEDOR',1,'admin','2025-12-20 11:24:40'),(85,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','USER',1,'admin','2025-12-20 11:24:45'),(86,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','VENDEDOR',1,'admin','2025-12-20 11:25:01'),(90,46,'Sergiovendor','$2a$10$3LkQR8tp/b2WKq8S4.ljMOXpLrcltL9k6lmxvZ12JQYlSfb1v8mNS','VENDEDOR',1,'admin','2025-12-20 11:31:04'),(91,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','USER',1,'admin','2025-12-20 11:44:42'),(92,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','USER',1,'admin','2025-12-20 11:52:02'),(93,42,'Camila','$2a$10$xil0yJp4JLGKgP5tq.5sJukKtLvpo8TXmm8DCIQUwTReDDGCiTvOq','VENDEDOR',1,'Camila','2025-12-20 12:00:07'),(94,20,'user','$2a$10$mXunlIii/VpuxSd6phY...O2fKdtqr5C7ocUpxMiZNLWMKc1n4r2C','USER',1,'admin','2025-12-20 12:05:34'),(95,47,'Maria','$2a$10$RR5mMBL.U3Jiz.WNbn7l7OBfFLjvy8iE3DiRp0fGCTCEEGfKgCPlu','VENDEDOR',1,'admin','2025-12-20 12:17:17'),(96,42,'Camila','$2a$10$5avCm3hLoBq6o06gYryDE.q5FiL47Bu5UzuqO2cap2od5rZs6isQa','VENDEDOR',1,'admin','2025-12-22 14:07:08'),(97,42,'Camila','$2a$10$5avCm3hLoBq6o06gYryDE.q5FiL47Bu5UzuqO2cap2od5rZs6isQa','USER',1,'admin','2025-12-22 14:07:16'),(99,39,'Sergio','$2a$10$A8kq5lWHGVbMdFJkSiDIW.c6PITezAVf0kVqpTONESTmPqxAzS4.K','USER',1,'admin','2025-12-22 14:19:36'),(100,42,'Camila','$2a$10$5avCm3hLoBq6o06gYryDE.q5FiL47Bu5UzuqO2cap2od5rZs6isQa','VENDEDOR',0,'admin','2025-12-22 14:21:18'),(101,47,'Maria','$2a$10$RR5mMBL.U3Jiz.WNbn7l7OBfFLjvy8iE3DiRp0fGCTCEEGfKgCPlu','USER',0,'admin','2025-12-22 14:21:20'),(102,39,'Sergio','$2a$10$A8kq5lWHGVbMdFJkSiDIW.c6PITezAVf0kVqpTONESTmPqxAzS4.K','VENDEDOR',1,'admin','2025-12-22 14:21:27'),(103,42,'Camila','$2a$10$5avCm3hLoBq6o06gYryDE.q5FiL47Bu5UzuqO2cap2od5rZs6isQa','USER',1,'admin','2025-12-22 14:22:18'),(104,47,'Maria','$2a$10$RR5mMBL.U3Jiz.WNbn7l7OBfFLjvy8iE3DiRp0fGCTCEEGfKgCPlu','USER',0,'admin','2025-12-22 14:33:47'),(105,39,'Sergio','$2a$10$A8kq5lWHGVbMdFJkSiDIW.c6PITezAVf0kVqpTONESTmPqxAzS4.K','VENDEDOR',1,'admin','2025-12-22 14:33:53'),(106,47,'Maria','$2a$10$RR5mMBL.U3Jiz.WNbn7l7OBfFLjvy8iE3DiRp0fGCTCEEGfKgCPlu','VENDEDOR',0,'admin','2025-12-22 14:34:38'),(107,47,'Maria','$2a$10$RR5mMBL.U3Jiz.WNbn7l7OBfFLjvy8iE3DiRp0fGCTCEEGfKgCPlu','USER',0,'Sistema','2025-12-22 14:35:10'),(108,48,'Maria','$2a$10$oRJvH3QtRVGvdDOe.//Q/.EhS76uP3YippmPH5r1DDTSRJgwB61hK','USER',0,'Sistema','2025-12-22 14:42:22'),(109,49,'Maria','$2a$10$hPMJVE9nvSFPnhKvbkxA5Ox8oXdd1sbeoU9tOp4HxUaiRVFiRM80C','VENDEDOR',1,'Sistema','2025-12-22 14:43:32');

/*Table structure for table `movimientos_stock` */

DROP TABLE IF EXISTS `movimientos_stock`;

CREATE TABLE `movimientos_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` decimal(38,2) DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `producto_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK52bo6ht9yrbmkldis6p3c0we1` (`producto_id`),
  CONSTRAINT `FK52bo6ht9yrbmkldis6p3c0we1` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `movimientos_stock` */

insert  into `movimientos_stock`(`id`,`cantidad`,`fecha`,`tipo`,`producto_id`) values (1,'5.00','2025-12-06 00:00:00.000000','ENTRADA',1),(2,'10.00','2025-12-07 00:00:00.000000','ENTRADA',2),(3,'3.00','2025-12-08 00:00:00.000000','SALIDA',3),(4,'2.00','2025-12-09 00:00:00.000000','ENTRADA',4),(5,'1.00','2025-12-10 00:00:00.000000','SALIDA',5);

/*Table structure for table `ordenes_compra` */

DROP TABLE IF EXISTS `ordenes_compra`;

CREATE TABLE `ordenes_compra` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `total` decimal(38,2) DEFAULT NULL,
  `proveedor_id` bigint(20) DEFAULT NULL,
  `iva` decimal(12,2) DEFAULT NULL,
  `subtotal` decimal(12,2) DEFAULT NULL,
  `total_final` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7ximp03n72hmygxmikaapavac` (`proveedor_id`),
  CONSTRAINT `FK7ximp03n72hmygxmikaapavac` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `ordenes_compra` */

insert  into `ordenes_compra`(`id`,`estado`,`fecha`,`total`,`proveedor_id`,`iva`,`subtotal`,`total_final`) values (1,'CREADA','2025-12-01','3500000.00',1,'318181.82','3181818.18','3500000.00'),(2,'CREADA','2025-12-02','120000.00',2,'10909.09','109090.91','120000.00'),(3,'CREADA','2025-12-03','250000.00',3,'22727.27','227272.73','250000.00'),(4,'CREADA','2025-12-04','1500000.00',4,'136363.64','1363636.36','1500000.00'),(5,'CREADA','2025-12-05','2200000.00',5,'200000.00','2000000.00','2200000.00');

/*Table structure for table `personas` */

DROP TABLE IF EXISTS `personas`;

CREATE TABLE `personas` (
  `id_persona` bigint(20) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `fecha_ingreso` datetime(6) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `nombres` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  `usuario_asociado_id` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `estado_civil` varchar(255) DEFAULT NULL,
  `ciudad` varchar(255) DEFAULT NULL,
  `pais` varchar(255) DEFAULT NULL,
  `numero_cedula` varchar(255) DEFAULT NULL,
  `ruc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_persona`),
  KEY `FKn07bnub0pbsug2fxbetrdcutx` (`usuario_id`),
  CONSTRAINT `FKn07bnub0pbsug2fxbetrdcutx` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `personas` */

insert  into `personas`(`id_persona`,`apellidos`,`direccion`,`fecha_ingreso`,`fecha_nacimiento`,`nombres`,`telefono`,`usuario_id`,`usuario_asociado_id`,`email`,`estado_civil`,`ciudad`,`pais`,`numero_cedula`,`ruc`) values (10,'Gimenez','tte daponte casi siria','2025-11-20 15:06:48.000000','2001-03-21','Sergio','0944394744',2,NULL,'s@gmail.com','soltero','Luque','Paraguay','29392239','29392239-0'),(36,'Britez','km 13','2025-12-18 13:10:41.000000','2002-03-21','Sergio','0944394743',2,NULL,'ss@gmail.com','Soltero','Capiata','Paraguay','3393383','3393383-0 '),(37,'Gimenez','Colon 322','2025-12-18 13:22:29.000000','2002-03-21','Camila','0994349744',2,NULL,'camila@gmail.com','Soltera','Asuncion','Paraguay','3293321','3293321-0'),(41,'System',NULL,NULL,'2000-01-01','Admin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(42,'Montez','daponte 434','2025-12-20 12:07:17.000000','2002-03-21','Lalo','0995949343',2,NULL,'lalo@gmail.com','Soltero','Capiata','Paraguay ','3832823','3832823-0'),(43,'Montez','Isla Bogado 554','2025-12-20 12:16:05.000000','2002-02-21','Maria','23232',2,50,'maria@gmail.com','sads','Luque','Paraguay','3384393','3384393-0');

/*Table structure for table `productos` */

DROP TABLE IF EXISTS `productos`;

CREATE TABLE `productos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` decimal(38,2) DEFAULT NULL,
  `stock_actual` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `productos` */

insert  into `productos`(`id`,`activo`,`descripcion`,`nombre`,`precio`,`stock_actual`) values (1,'','Laptop Lenovo ThinkPad','Laptop Lenovo','3500000.00','10.00'),(2,'','Mouse inalámbrico Logitech','Mouse Logitech','120000.00','50.00'),(3,'','Teclado mecánico Redragon','Teclado Redragon','250000.00','30.00'),(4,'','Monitor Samsung 24 pulgadas','Monitor Samsung','1500000.00','20.00'),(5,'','Impresora HP LaserJet','Impresora HP','2200000.00','15.00');

/*Table structure for table `proveedores` */

DROP TABLE IF EXISTS `proveedores`;

CREATE TABLE `proveedores` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `razon_social` varchar(255) DEFAULT NULL,
  `ruc` varchar(15) NOT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `proveedores` */

insert  into `proveedores`(`id`,`email`,`razon_social`,`ruc`,`telefono`) values (1,'proveedorA@mail.com','Proveedor A S.A.','80011111-1','021555111'),(2,'proveedorB@mail.com','Proveedor B S.R.L.','80022222-2','021555222'),(3,'proveedorC@mail.com','Proveedor C Importaciones','80033333-3','021555333'),(4,'proveedorD@mail.com','Proveedor D Distribuciones','80044444-4','021555444'),(5,'proveedorE@mail.com','Proveedor E Mayorista','80055555-5','021555555');

/*Table structure for table `usuarios` */

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `id_usuario` bigint(20) NOT NULL AUTO_INCREMENT,
  `clave` varchar(255) NOT NULL,
  `estado` bit(1) NOT NULL,
  `fecha_alta` datetime DEFAULT current_timestamp(),
  `usuario` varchar(255) NOT NULL,
  `rol` varchar(255) NOT NULL,
  `id_persona` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UK3m5n1w5trapxlbo2s42ugwdmd` (`usuario`),
  UNIQUE KEY `UK27s1llon5naewhedd1qfhhvce` (`id_persona`),
  CONSTRAINT `FKtmank41bd4off23q2o9dx13y9` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id_persona`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `usuarios` */

insert  into `usuarios`(`id_usuario`,`clave`,`estado`,`fecha_alta`,`usuario`,`rol`,`id_persona`) values (2,'$2a$10$XpgVh2joCkz2RO4f.UTKvO1equQN.ndwQW4w88U4.psi2ib/klNAu','','2025-11-21 11:31:41','admin','ADMIN',41),(20,'$2a$10$mXunlIii/VpuxSd6phY...O2fKdtqr5C7ocUpxMiZNLWMKc1n4r2C','','2025-11-24 13:07:46','user','ADMIN',NULL),(39,'$2a$10$A8kq5lWHGVbMdFJkSiDIW.c6PITezAVf0kVqpTONESTmPqxAzS4.K','','2025-12-18 12:04:22','Sergio','USER',10),(42,'$2a$10$5avCm3hLoBq6o06gYryDE.q5FiL47Bu5UzuqO2cap2od5rZs6isQa','',NULL,'Camila','VENDEDOR',37),(50,'$2a$10$0Ov88vjG6Lhno8MTERz9/uiHWMAZamkGbcwAZgZsSUX1pTdH5hd8G','','2025-12-22 14:46:06','Maria','VENDEDOR',43);

/*Table structure for table `vendedores` */

DROP TABLE IF EXISTS `vendedores`;

CREATE TABLE `vendedores` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `codigo_vendedor` varchar(255) NOT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `persona_id` bigint(20) NOT NULL,
  `usuario_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKd7wc9vb6jeqh6ffr9l9jubbjh` (`codigo_vendedor`),
  UNIQUE KEY `UKe99uss461jlq6v2bxnb6g4piv` (`persona_id`),
  KEY `FKtek1eni71uapxj1c5amyojdjg` (`usuario_id`),
  CONSTRAINT `FKssvr6f9hsvt53b70huydol920` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id_persona`),
  CONSTRAINT `FKtek1eni71uapxj1c5amyojdjg` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `vendedores` */

insert  into `vendedores`(`id`,`activo`,`codigo_vendedor`,`fecha_creacion`,`persona_id`,`usuario_id`) values (2,'','VEND-1766240617',NULL,37,42),(4,'\0','VEND-1766423976',NULL,10,39),(6,'','VEND-1766425566255','2025-12-22 14:46:06.000000',43,50);

/* Procedure structure for procedure `sp_actualizarPersona` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_actualizarPersona` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizarPersona`(
    IN p_id INT,
    IN p_nombres VARCHAR(255),
    IN p_apellidos VARCHAR(255),
    IN p_telefono VARCHAR(50),
    IN p_direccion VARCHAR(255),
    IN p_fechaNacimiento DATE,
    IN p_usuarioId INT,
    IN p_usuarioAsociadoId INT,  -- AÑADIDO
    IN p_email VARCHAR(255),
    IN p_estadoCivil VARCHAR(50),
    IN p_ciudad VARCHAR(100),
    IN p_pais VARCHAR(100),
    IN p_numeroCedula VARCHAR(20),
    IN p_ruc VARCHAR(20)
)
BEGIN
    UPDATE personas
    SET nombres = p_nombres,
        apellidos = p_apellidos,
        telefono = p_telefono,
        direccion = p_direccion,
        fecha_nacimiento = p_fechaNacimiento,
        usuario_id = p_usuarioId,
        usuario_asociado_id = p_usuarioAsociadoId,  -- AÑADIDO
        email = p_email,
        estado_civil = p_estadoCivil,
        ciudad = p_ciudad,
        pais = p_pais,
        numero_cedula = p_numeroCedula,
        ruc = p_ruc
    WHERE id_persona = p_id;
    SELECT ROW_COUNT() AS filas_afectadas;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_actualizarUsuario` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_actualizarUsuario` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizarUsuario`(
    IN p_id INT,
    IN p_usuario VARCHAR(50),
    IN p_clave VARCHAR(255),
    IN p_estado BOOLEAN,
    IN p_rol VARCHAR(20),
    IN p_usuario_modificador VARCHAR(50)
)
BEGIN
    INSERT INTO log_usuarios (id_usuario, usuario, clave, rol, estado, modificado_por, fecha_modificacion)
    SELECT id_usuario, usuario, clave, rol, estado, p_usuario_modificador, NOW()
    FROM usuarios
    WHERE id_usuario = p_id;
    UPDATE usuarios
    SET usuario = p_usuario,
        clave = p_clave,
        estado = p_estado,
        rol = p_rol
    WHERE id_usuario = p_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_buscarPersona` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_buscarPersona` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarPersona`(
    IN p_id INT
)
BEGIN
    SELECT id_persona, nombres, apellidos, telefono, direccion, fecha_nacimiento, fecha_ingreso, usuario_id
    FROM personas
    WHERE id_persona = p_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_buscarPersonaPorId` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_buscarPersonaPorId` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarPersonaPorId`(
    IN p_id INT
)
BEGIN
    SELECT 
        id_persona,
        nombres,
        apellidos,
        telefono,
        direccion,
        fecha_nacimiento,
        fecha_ingreso,
        usuario_id,
        email,
        estado_civil,
        ciudad,
        pais,
        numero_cedula,
        ruc
    FROM personas
    WHERE id_persona = p_id
    LIMIT 1;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_buscarPersonaPorNombre` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_buscarPersonaPorNombre` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarPersonaPorNombre`(
    IN p_nombres VARCHAR(255)
)
BEGIN
    SELECT 
        id_persona,
        nombres,
        apellidos,
        telefono,
        direccion,
        fecha_nacimiento,
        fecha_ingreso,
        usuario_id,
        email,
        estado_civil,
        ciudad,
        pais,
        numero_cedula,
        ruc
    FROM personas
    WHERE nombres LIKE CONCAT('%', p_nombres, '%')
    LIMIT 1;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_buscarUsuarioPorNombre` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_buscarUsuarioPorNombre` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarUsuarioPorNombre`(
    IN p_usuario VARCHAR(50)
)
BEGIN
    SELECT id_usuario, usuario, clave, rol, estado, fecha_alta, id_persona
    FROM usuarios
    WHERE usuario = p_usuario
    LIMIT 1;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_cambiarClaveUsuario` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_cambiarClaveUsuario` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_cambiarClaveUsuario`(
    IN p_id_usuario INT,
    IN p_nueva_clave VARCHAR(255),
    IN p_usuario_modificador VARCHAR(50)
)
BEGIN
    UPDATE usuarios
    SET clave = p_nueva_clave
    WHERE id_usuario = p_id_usuario;
    SELECT ROW_COUNT() AS filas_afectadas;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_cambiarRolUsuario` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_cambiarRolUsuario` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_cambiarRolUsuario`(
    IN p_id INT,
    IN p_rol VARCHAR(20),
    IN p_estado BOOLEAN,
    IN p_usuario_modificador VARCHAR(50)
)
BEGIN
    DECLARE v_persona_id INT;
    DECLARE v_codigo_vendedor VARCHAR(50);
    -- Registrar log antes de modificar
    INSERT INTO log_usuarios (id_usuario, usuario, clave, rol, estado, modificado_por, fecha_modificacion)
    SELECT id_usuario, usuario, clave, rol, estado, p_usuario_modificador, NOW()
    FROM usuarios
    WHERE id_usuario = p_id;
    -- Actualizar solo rol y estado
    UPDATE usuarios
    SET rol = p_rol,
        estado = p_estado
    WHERE id_usuario = p_id;
    -- Manejar tabla vendedores
    SELECT id_persona INTO v_persona_id
    FROM usuarios
    WHERE id_usuario = p_id;
    IF p_rol = 'VENDEDOR' THEN
        -- Verificar si ya existe un vendedor para esta persona
        SELECT codigo_vendedor INTO v_codigo_vendedor
        FROM vendedores
        WHERE persona_id = v_persona_id
        LIMIT 1;
        IF v_codigo_vendedor IS NOT NULL THEN
            -- Activar vendedor existente
            UPDATE vendedores
            SET activo = TRUE
            WHERE persona_id = v_persona_id;
        ELSE
            -- Crear nuevo vendedor
            INSERT INTO vendedores (persona_id, usuario_id, codigo_vendedor, activo)
            VALUES (v_persona_id, p_id, CONCAT('VEND-', UNIX_TIMESTAMP()), TRUE);
        END IF;
    ELSE
        -- Si ya no es vendedor, desactivar vendedor existente
        UPDATE vendedores
        SET activo = FALSE
        WHERE persona_id = v_persona_id;
    END IF;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_eliminarPersona` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_eliminarPersona` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminarPersona`(
    IN p_id INT,
    IN p_usuario VARCHAR(50)
)
BEGIN
    -- ❌ Bloquear eliminación si tiene usuario asociado
    IF EXISTS (
        SELECT 1 FROM usuarios WHERE id_persona = p_id
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede eliminar la persona porque tiene un usuario asociado';
    END IF;
    -- 📄 Log
    INSERT INTO log_personas(
        id_persona, nombres, apellidos, telefono, direccion,
        fecha_nacimiento, fecha_ingreso,
        eliminado_por, fecha_eliminacion
    )
    SELECT 
        id_persona, nombres, apellidos, telefono, direccion,
        fecha_nacimiento, fecha_ingreso,
        p_usuario, NOW()
    FROM personas
    WHERE id_persona = p_id;
    -- 🗑️ Eliminar
    DELETE FROM personas WHERE id_persona = p_id;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_eliminarUsuario` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_eliminarUsuario` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminarUsuario`(
    IN p_id INT,
    IN p_usuario_modificador VARCHAR(50)
)
BEGIN
    DECLARE existeVendedor INT;
    -- Verificar si el usuario tiene un vendedor asociado
    SELECT COUNT(*) INTO existeVendedor
    FROM vendedores
    WHERE usuario_id = p_id;
    -- Registrar log antes de cualquier acción
    INSERT INTO log_usuarios (id_usuario, usuario, clave, rol, estado, modificado_por, fecha_modificacion)
    SELECT id_usuario, usuario, clave, rol, estado, p_usuario_modificador, NOW()
    FROM usuarios
    WHERE id_usuario = p_id;
    IF existeVendedor > 0 THEN
        -- Solo desactivar usuario
        UPDATE usuarios
        SET estado = FALSE
        WHERE id_usuario = p_id;
    ELSE
        -- Usuario sin vendedor, se puede eliminar
        DELETE FROM usuarios
        WHERE id_usuario = p_id;
    END IF;
    -- Retornar número de filas afectadas
    SELECT ROW_COUNT() AS filas_afectadas;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_insertarPersona` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_insertarPersona` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertarPersona`(
    IN p_nombres VARCHAR(255),
    IN p_apellidos VARCHAR(255),
    IN p_telefono VARCHAR(50),
    IN p_direccion VARCHAR(255),
    IN p_fechaNacimiento DATE,
    IN p_usuarioId INT,
    IN p_usuarioAsociadoId INT,
    IN p_email VARCHAR(255),
    IN p_estadoCivil VARCHAR(50),
    IN p_ciudad VARCHAR(100),
    IN p_pais VARCHAR(100),
    IN p_numeroCedula VARCHAR(20),
    IN p_ruc VARCHAR(20)
)
BEGIN
    INSERT INTO personas(
        nombres, apellidos, telefono, direccion,
        fecha_nacimiento, fecha_ingreso,
        usuario_id, usuario_asociado_id,
        email, estado_civil, ciudad, pais,
        numero_cedula, ruc
    )
    VALUES(
        p_nombres, p_apellidos, p_telefono, p_direccion,
        p_fechaNacimiento, NOW(),
        p_usuarioId, p_usuarioAsociadoId,
        p_email, p_estadoCivil, p_ciudad, p_pais,
        p_numeroCedula, p_ruc
    );
    SELECT LAST_INSERT_ID() AS id_generado;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_insertarUsuario` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_insertarUsuario` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertarUsuario`(
    IN p_usuario VARCHAR(50),
    IN p_clave VARCHAR(255),
    IN p_estado BOOLEAN,
    IN p_rol VARCHAR(20),
    IN p_id_persona INT
)
BEGIN
    DECLARE nuevo_usuario_id BIGINT;
    -- Insertar el usuario
    INSERT INTO usuarios(usuario, clave, estado, rol, id_persona)
    VALUES(p_usuario, p_clave, p_estado, p_rol, p_id_persona);
    -- Obtener el ID generado
    SET nuevo_usuario_id = LAST_INSERT_ID();
    -- Actualizar la persona con el usuario asociado
    UPDATE personas
    SET usuario_asociado_id = nuevo_usuario_id
    WHERE id_persona = p_id_persona;
    -- Retornar el ID del nuevo usuario
    SELECT nuevo_usuario_id AS id_usuario;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_listarPersona` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_listarPersona` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarPersona`()
BEGIN
    SELECT 
        id_persona,
        nombres,
        apellidos,
        telefono,
        direccion,
        fecha_nacimiento,
        fecha_ingreso,
        usuario_id,
        email,
        estado_civil,
        ciudad,
        pais,
        numero_cedula,
        ruc
    FROM personas;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_listarPersonasSinUsuario` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_listarPersonasSinUsuario` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarPersonasSinUsuario`()
BEGIN
    SELECT p.* 
    FROM personas p
    LEFT JOIN usuarios u ON p.id_persona = u.id_persona
    WHERE u.id_usuario IS NULL;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_listarUsuario` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_listarUsuario` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarUsuario`()
BEGIN
    SELECT id_usuario, usuario, clave, rol, estado, fecha_alta, id_persona
    FROM usuarios;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_listarUsuarioSeguro` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_listarUsuarioSeguro` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listarUsuarioSeguro`()
BEGIN
    SELECT id_usuario, usuario, rol, estado, fecha_alta, id_persona
    FROM usuarios;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_loginUsuario` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_loginUsuario` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_loginUsuario`(
    IN p_usuario VARCHAR(50),
    IN p_clave VARCHAR(255)
)
BEGIN
    SELECT id_usuario, usuario, rol, estado
    FROM usuarios
    WHERE usuario = p_usuario AND clave = p_clave
    LIMIT 1;
END */$$
DELIMITER ;

/* Procedure structure for procedure `sp_tieneUsuarioAsociado` */

/*!50003 DROP PROCEDURE IF EXISTS  `sp_tieneUsuarioAsociado` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_tieneUsuarioAsociado`(IN p_id_persona INT)
BEGIN
    SELECT COUNT(*) 
    FROM usuarios 
    WHERE id_persona = p_id_persona;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
