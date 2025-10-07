-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `branch_managers`
--

DROP TABLE IF EXISTS `branch_managers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch_managers` (
  `managerId` varchar(45) NOT NULL,
  `branchId` varchar(100) NOT NULL,
  PRIMARY KEY (`managerId`),
  KEY `fk_branch` (`branchId`),
  CONSTRAINT `fk_branchId` FOREIGN KEY (`branchId`) REFERENCES `branches` (`branchId`),
  CONSTRAINT `fk_manager` FOREIGN KEY (`managerId`) REFERENCES `users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_managers`
--

LOCK TABLES `branch_managers` WRITE;
/*!40000 ALTER TABLE `branch_managers` DISABLE KEYS */;
INSERT INTO `branch_managers` VALUES ('1','1'),('123','2'),('124','31'),('125','32'),('126','4');
/*!40000 ALTER TABLE `branch_managers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branches`
--

DROP TABLE IF EXISTS `branches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branches` (
  `branchId` varchar(100) NOT NULL,
  `branchName` varchar(100) NOT NULL,
  `location` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`branchId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branches`
--

LOCK TABLES `branches` WRITE;
/*!40000 ALTER TABLE `branches` DISABLE KEYS */;
INSERT INTO `branches` VALUES ('1','Vivino ','Haifa'),('2','Alena ','Aviv'),('31','Disfrutar','Madrid'),('32','Disfrutar','Barcelona'),('4','YasserFlafel','Bosmat Tivon');
/*!40000 ALTER TABLE `branches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_types`
--

DROP TABLE IF EXISTS `delivery_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_types` (
  `delivery_type` varchar(45) NOT NULL,
  `delivery_price` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`delivery_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_types`
--

LOCK TABLES `delivery_types` WRITE;
/*!40000 ALTER TABLE `delivery_types` DISABLE KEYS */;
INSERT INTO `delivery_types` VALUES (' Take Away','0'),('Business Delivery','20'),('Regular Delivery','25'),('Robot Delivery','0');
/*!40000 ALTER TABLE `delivery_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish_id_counter`
--

DROP TABLE IF EXISTS `dish_id_counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish_id_counter` (
  `id` varchar(45) NOT NULL,
  `dishId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish_id_counter`
--

LOCK TABLES `dish_id_counter` WRITE;
/*!40000 ALTER TABLE `dish_id_counter` DISABLE KEYS */;
INSERT INTO `dish_id_counter` VALUES ('1','5012');
/*!40000 ALTER TABLE `dish_id_counter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dishes`
--

DROP TABLE IF EXISTS `dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dishes` (
  `dishId` varchar(45) NOT NULL,
  `restaurant_name` varchar(45) DEFAULT NULL,
  `dish_name` varchar(45) DEFAULT NULL,
  `meal_type_id` varchar(45) DEFAULT NULL,
  `meal_type_name` varchar(45) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`dishId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dishes`
--

LOCK TABLES `dishes` WRITE;
/*!40000 ALTER TABLE `dishes` DISABLE KEYS */;
INSERT INTO `dishes` VALUES ('1','YasserFalfel','Cola','909','Drinks','7'),('10','Vivino','Pomodoro Spaghetti','123','MainCourse','64'),('11','Vivino','Veal Fillet Gnocchi','123','MainCourse','91'),('12','Vivino','Salmon Linguini','123','MainCourse','91'),('13','Vivino','Tiramisu','222','Dessert','46'),('14','Vivino','Tres Leches','222','Dessert','48'),('15','Vivino','Strawberries Mascarpone','222','Dessert','53'),('16','Vivino','Panzanella','321','Salad','62'),('17','Vivino','Mercato','321','Salad','68'),('18','Vivino','Caesar','321','Salad','58'),('19','Alena','Water','444','Drinks','25'),('2','YasserFalfel','Fanta','909','Drinks','7'),('20','Alena','Yuzu','444','Drinks','50'),('21','Alena','Grey Goose','444','Drinks','50'),('22','Alena','Fuyu','444','Drinks','82'),('23','Alena','Sea bass Usuzukuri','456','MainCourse','81'),('24','Alena','Sake-steamed chicken','456','MainCourse','84'),('25','Alena','Sake-steamed Seabass','456','MainCourse','148'),('26','Alena','Gimlet','456','MainCourse','62'),('27','Alena','Chocolate Tart','555','Dessert','52'),('28','Alena','Sesame Crème Brûlée','555','Dessert','48'),('29','Alena','Ice Cream & Sorbet','555','Dessert','28'),('3','YasserFalfel','Cola Zero','909','Drinks','7'),('30','Alena','Spinach salad','654','Salad','58'),('31','Alena','Charred gem lettuce salad','654','Salad','72'),('32','Disfrutar','Cola','777','Drinks','12'),('33','Disfrutar','Fanta','777','Drinks','12'),('34','Disfrutar','Sprite','777','Drinks','12'),('35','Disfrutar','Cola Zero','777','Drinks','12'),('36','Disfrutar','Sea Fish Tartar Bruschetta','789','MainCourse','85'),('37','Disfrutar','Beef Tartar','789','MainCourse','94'),('38','Disfrutar','Chocolate Souffle a La Minut','888','Dessert','52'),('39','Disfrutar','Chocolate Mousse Praline','888','Dessert','60'),('4','YasserFalfel','Water','909','Drinks','7'),('40','Disfrutar','Flan Tart','888','Dessert','55'),('41','Disfrutar','Green Salad','987','Salad','47'),('42','Disfrutar','Root Vegetable Tartar','789','MainCourse','74'),('5','YasserFalfel','Regular Falafel','999','Falafel','20'),('5011','Alena','ColaMango','444','Drinks','14'),('6','YasserFalfel','Cheese Falafel','999','Falafel','25'),('7','Vivino','Water','111','Drinks','13'),('8','Vivino','Lemonade','111','Drinks','14'),('9','Vivino','San Pellegrino','111','Drinks','13');
/*!40000 ALTER TABLE `dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `income_reports`
--

DROP TABLE IF EXISTS `income_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `income_reports` (
  `reportId` varchar(45) NOT NULL,
  `branchId` varchar(45) NOT NULL,
  `month` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `totalIncome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`reportId`),
  KEY `income_reports_ibfk_1` (`branchId`),
  CONSTRAINT `income_reports_ibfk_1` FOREIGN KEY (`branchId`) REFERENCES `branches` (`branchId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `income_reports`
--

LOCK TABLES `income_reports` WRITE;
/*!40000 ALTER TABLE `income_reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `income_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `list_number`
--

DROP TABLE IF EXISTS `list_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `list_number` (
  `id` varchar(45) NOT NULL,
  `list_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list_number`
--

LOCK TABLES `list_number` WRITE;
/*!40000 ALTER TABLE `list_number` DISABLE KEYS */;
INSERT INTO `list_number` VALUES ('1','76');
/*!40000 ALTER TABLE `list_number` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mealtype`
--

DROP TABLE IF EXISTS `mealtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mealtype` (
  `restaurant_name` varchar(45) DEFAULT NULL,
  `mealTypeId` varchar(45) NOT NULL,
  `mealTypeName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`mealTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mealtype`
--

LOCK TABLES `mealtype` WRITE;
/*!40000 ALTER TABLE `mealtype` DISABLE KEYS */;
INSERT INTO `mealtype` VALUES ('Vivino','111','Drinks'),('Vivino','123','MainCourse'),('Vivino','222','Dessert'),('Vivino','321','Salad'),('Alena','444','Drinks'),('Alena','456','MainCourse'),('Alena','555','Dessert'),('Alena','654','Salad'),('Disfrutar','777','Drinks'),('Disfrutar','789','MainCourse'),('Disfrutar','888','Dessert'),('YasserFalfel','909','Drinks'),('Disfrutar','987','Salad'),('YasserFalfel','999','Falafel');
/*!40000 ALTER TABLE `mealtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_list`
--

DROP TABLE IF EXISTS `order_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_list` (
  `user_id` varchar(45) DEFAULT NULL,
  `order_list_number` varchar(45) NOT NULL,
  `restaurant` varchar(45) DEFAULT NULL,
  `order_date` varchar(45) DEFAULT NULL,
  `requested_date` varchar(45) DEFAULT NULL,
  `total_price` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `delivery_service` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `approval` varchar(45) DEFAULT NULL,
  `arrival_time` varchar(45) DEFAULT NULL,
  `requested_time` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_list_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_list`
--

LOCK TABLES `order_list` WRITE;
/*!40000 ALTER TABLE `order_list` DISABLE KEYS */;
INSERT INTO `order_list` VALUES ('77777','0','YasserFalfel','2024-08-08','2024-08-10','48','NoAddress','','Ready','Away','Ready','WaitingForApproval'),('55555','1','YasserFalfel','2024-08-08','2024-08-31','14','NoAddress','TakeAway','Ready','WaitingForApproval','23:53','14:00'),('6987','10','YasserFalfel','2025-09-15','2025-09-11','50','Tivon','RegularDelivery','Procressing','WaitingForApproval','06:16','12:00'),('6987','11','YasserFalfel','2025-09-15','2025-09-10','32','cdf','RegularDelivery','Procressing','WaitingForApproval','06:20','12'),('6987','12','Vivino','2025-10-04','2025-10-01','48','NoAddress','TakeAway','Ready','Approval','12:00','12'),('6987','13','Vivino','2025-10-05','2025-10-07','64','NoAddress','TakeAway','Procressing','WaitingForApproval','01:21','12:00'),('6987','14','Vivino','2025-10-05','2025-10-07','64','NoAddress','TakeAway','Procressing','WaitingForApproval','01:27','8:00'),('6987','15','Vivino','2025-10-05','2025-10-06','64','NoAddress','TakeAway','Procressing','WaitingForApproval','01:33','12:12'),('6987','16','Vivino','2025-10-05','2025-10-16','64','NoAddress','TakeAway','Procressing','WaitingForApproval','01:36','12:12'),('6987','17','Vivino,Alena','2025-10-05','2025-10-08','98','NoAddress','TakeAway','Procressing','WaitingForApproval','01:42','23:23'),('6987','18','Vivino,Disfrutar','2025-10-05','2025-10-24','60','NoAddress','TakeAway','Procressing','WaitingForApproval','01:49','12:12'),('6987','19','Vivino,Alena','2025-10-05','2025-10-14','206','NoAddress','TakeAway','Procressing','WaitingForApproval','01:52','12:!2'),('77777','2','YasserFalfel','2024-08-08','2024-08-25','14','NoAddress','TakeAway','Ready','WaitingForApproval','00:12','17:00'),('6987','20','Vivino','2025-10-05','2025-10-08','62','NoAddress','TakeAway','Procressing','WaitingForApproval','01:54','11'),('6987','21','Vivino','2025-10-05','2025-10-15','64','NoAddress','TakeAway','Procressing','WaitingForApproval','01:58','12:12'),('6987','22','Vivino','2025-10-06','2025-10-08','64','NoAddress','TakeAway','Procressing','WaitingForApproval','02:00','12'),('6987','23','Disfrutar','2025-10-06','2025-10-08','55','NoAddress','TakeAway','Procressing','WaitingForApproval','02:04','22:22'),('6987','24','Vivino','2025-10-06','2025-10-02','91','NoAddress','TakeAway','Procressing','WaitingForApproval','02:06','12:!2'),('6987','25','Alena','2025-10-06','2025-10-15','25','NoAddress','TakeAway','Procressing','WaitingForApproval','02:09','12:12'),('6987','26','Disfrutar','2025-10-06','2025-10-07','60','NoAddress','TakeAway','Procressing','WaitingForApproval','02:11','12:12'),('6987','27','Vivino','2025-10-06','2025-10-08','68','bosmat','BusinessDelivery','Procressing','WaitingForApproval','02:16','12:12'),('6987','28','Disfrutar,Alena','2025-10-06','2025-10-07','132','NoAddress','TakeAway','Take It','WaitingForApproval','02:27','12:12'),('6987','29','YasserFalfel,YasserFalfel','2025-10-06','2025-09-30','63','NoAddress','TakeAway','Procressing','WaitingForApproval','02:39','12:12'),('77777','3','YasserFalfel','2024-08-08','2024-08-26','58','NoAddress','TakeAway','Ready','WaitingForApproval','00:13','15:00'),('6987','30','YasserFalfel','2025-10-06','2025-11-05','7','NoAddress','TakeAway','Procressing','WaitingForApproval','02:41','12:12'),('6987','31','Vivino','2025-10-06','2025-10-10','48','NoAddress','TakeAway','Procressing','WaitingForApproval','02:44','12:12'),('6987','32','Disfrutar','2025-10-06','2025-10-21','12','NoAddress','TakeAway','Take It','WaitingForApproval','02:46','12:12'),('6987','33','Vivino','2025-10-06','2025-10-07','53','NoAddress','TakeAway','Procressing','WaitingForApproval','02:47','12:12'),('6987','34','Alena','2025-10-06','2025-10-07','148','NoAddress','TakeAway','Procressing','WaitingForApproval','02:54','12:12'),('6987','35','Alena','2025-10-06','2025-09-29','50','NoAddress','TakeAway','Procressing','WaitingForApproval','02:59','12:12'),('6987','36','Vivino','2025-10-06','2025-10-07','91','NoAddress','TakeAway','Procressing','WaitingForApproval','03:06','12:12'),('6987','37','Vivino','2025-10-06','2025-10-13','48','NoAddress','TakeAway','Procressing','WaitingForApproval','03:07','12:12'),('6987','38','Vivino','2025-10-06','2025-10-07','64','NoAddress','TakeAway','Procressing','WaitingForApproval','03:10','12:12'),('6987','39','Alena','2025-10-06','2025-09-30','72','NoAddress','TakeAway','Procressing','WaitingForApproval','03:14','12:12'),('66666','4','YasserFalfel','2024-08-09','2024-08-25','10','NoAddress','TakeAway','Ready','WaitingForApproval','02:53','13:00'),('6987','40','Vivino','2025-10-06','2025-10-08','18','NoAddress','TakeAway','Procressing','WaitingForApproval','03:28','12:12'),('6987','41','Vivino','2025-10-06','2025-10-14','91','NoAddress','TakeAway','Procressing','WaitingForApproval','03:36','12:12'),('6987','42','Alena','2025-10-06','2025-09-30','48','NoAddress','TakeAway','Procressing','WaitingForApproval','03:38','12:12'),('6987','43','Vivino','2025-10-06','2025-10-15','91','NoAddress','TakeAway','Procressing','WaitingForApproval','03:42','12:12'),('6987','44','Vivino','2025-10-06','2025-09-30','64','NoAddress','TakeAway','Procressing','WaitingForApproval','03:45','12:12'),('6987','45','Vivino','2025-10-06','2025-10-29','64','NoAddress','TakeAway','Procressing','WaitingForApproval','03:50','12:12'),('6987','46','Vivino','2025-10-06','2025-10-09','91','NoAddress','TakeAway','Procressing','WaitingForApproval','04:00','12::12'),('6987','47','Alena','2025-10-06','2025-10-16','50','NoAddress','TakeAway','Procressing','WaitingForApproval','04:01','12:12'),('6987','48','Vivino,YasserFalfel','2025-10-06','2025-10-09','84','NoAddress','TakeAway','Take It','WaitingForApproval','10:27','12:12'),('6987','49','Vivino','2025-10-06','2025-10-09','91','NoAddress','TakeAway','Procressing','WaitingForApproval','10:37','12:12'),('88888','5','YasserFalfel','2024-08-09','2024-08-25','34','BosmatTivon','BusinessDelivery','Ready','WaitingForApproval','03:22','9:00'),('6987','50','Vivino','2025-10-06','2025-10-08','91','NoAddress','TakeAway','Procressing','WaitingForApproval','10:42','12:12'),('6987','51','Vivino,YasserFalfel','2025-10-06','2025-10-22','71','NoAddress','TakeAway','Procressing','WaitingForApproval','10:45','12:12'),('6987','52','Vivino,YasserFalfel','2025-10-06','2025-10-22','28','NoAddress','TakeAway','Procressing','WaitingForApproval','10:48','12:12'),('6987','53','Alena,Alena','2025-10-06','2025-09-30','146','NoAddress','TakeAway','Procressing','WaitingForApproval','10:50','12:12'),('6987','54','Vivino,Vivino','2025-10-06','2025-09-30','115','NoAddress','TakeAway','Procressing','WaitingForApproval','10:56','12:12'),('6987','55','Vivino','2025-10-06','2025-10-08','53','NoAddress','TakeAway','Procressing','WaitingForApproval','11:24','12:12'),('77777','554996','YasserFalfel','2024-08-08','2024-08-20','27','NoAddress','TakeAway','Ready','WaitingForApproval','01:30','17:00'),('6987','56','Alena','2025-10-06','2025-10-21','50','NoAddress','TakeAway','Procressing','WaitingForApproval','11:33','12:12'),('6987','57','Alena','2025-10-06','2025-10-16','50','NoAddress','TakeAway','Procressing','WaitingForApproval','11:49','12:12'),('6987','58','YasserFalfel','2025-10-06','2025-10-08','12','NoAddress','TakeAway','Procressing','WaitingForApproval','11:52','12:12'),('6987','59','Vivino','2025-10-06','2025-10-14','13','NoAddress','TakeAway','Procressing','WaitingForApproval','12:01','12:12'),('444444','6','YasserFalfel','2024-08-11','2024-08-25','39','BosmatTivon','RegularDelivery','Take It','WaitingForApproval','15:37','14:00'),('6987','60','Vivino','2025-10-06','2025-10-01','91','NoAddress','TakeAway','Procressing','WaitingForApproval','12:19','12:!2'),('6987','61','Vivino','2025-10-06','2025-10-07','13','NoAddress','TakeAway','Procressing','WaitingForApproval','12:22','12:12'),('6987','62','Vivino','2025-10-06','2025-10-08','13','NoAddress','TakeAway','Procressing','WaitingForApproval','12:24','12:12'),('6987','63','Vivino','2025-10-06','2025-10-01','14','NoAddress','TakeAway','Procressing','WaitingForApproval','12:28','11:11'),('6987','64','Vivino','2025-10-06','2025-10-02','13','NoAddress','TakeAway','Procressing','WaitingForApproval','20:24','12:12'),('6987','65','Vivino','2025-10-06','2025-10-09','91','NoAddress','TakeAway','Procressing','WaitingForApproval','20:29','12:12'),('6987','66','Vivino','2025-10-06','2025-10-10','13','NoAddress','TakeAway','Procressing','WaitingForApproval','20:31','12:12'),('6987','67','YasserFalfel','2025-10-06','2025-10-23','7','NoAddress','TakeAway','Procressing','WaitingForApproval','20:37','12:12'),('6987','68','Vivino','2025-10-06','2025-11-04','13','NoAddress','TakeAway','Procressing','WaitingForApproval','20:40','12:!2'),('6987','69','Vivino','2025-10-06','2025-10-23','91','NoAddress','TakeAway','Procressing','WaitingForApproval','20:42','12:12'),('444444','7','YasserFalfel','2024-08-11','2024-08-27','65','BosmatTivon','BusinessDelivery','Take It','WaitingForApproval','16:25','19:00'),('6987','70','YasserFalfel','2025-10-06','2025-10-07','20','NoAddress','TakeAway','Procressing','WaitingForApproval','20:48','12:12'),('6987','71','Disfrutar,Vivino','2025-10-06','2025-10-08','185','NoAddress','TakeAway','Procressing','WaitingForApproval','20:50','12:12'),('6987','72','Vivino,YasserFalfel','2025-10-06','2025-10-22','78','NoAddress','TakeAway','Procressing','WaitingForApproval','22:23','12:12'),('6987','73','Vivino','2025-10-06','2025-10-08','26','NoAddress','TakeAway','Ready','Approval','17:17','12:12'),('6987','74','Vivino','2025-10-07','2025-10-07','64','NoAddress','TakeAway','Procressing','WaitingForApproval','04:12','12:12'),('6987','75','YasserFalfel,YasserFalfel','2025-10-07','2025-10-22','20','BosmatTivon','BusinessDelivery','Procressing','WaitingForApproval','04:17','12:00'),('444444','8','YasserFalfel','2024-08-13','2024-08-25','58','NoAddress','TakeAway','Ready','WaitingForApproval','06:13','14:00'),('55555','833004','YasserFalfel','2024-08-08','2024-08-27','12','NoAddress','TakeAway','Ready','WaitingForApproval','01:59','12:00'),('77777','835101','YasserFalfel','2024-08-08','2024-08-17','22','NoAddress','TakeAway','Ready','WaitingForApproval','01:57','10:00'),('6987','9','Vivino','2025-09-15','2025-09-01','116','Haifa','RegularDelivery','Ready','Approval','12:00','12:00'),('66666','null','YasserFalfel','2024-08-09','2024-08-13','22','NoAddress','TakeAway','Ready','WaitingForApproval','02:45','11:20');
/*!40000 ALTER TABLE `order_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_reports`
--

DROP TABLE IF EXISTS `orders_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_reports` (
  `reportId` varchar(45) NOT NULL,
  `branchId` varchar(45) DEFAULT NULL,
  `month` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `restaurantName` varchar(45) DEFAULT NULL,
  `itemId` varchar(45) DEFAULT NULL,
  `itemName` varchar(45) DEFAULT NULL,
  `itemCategory` varchar(45) DEFAULT NULL,
  `quantity` varchar(45) DEFAULT NULL,
  `itemPrice` varchar(45) DEFAULT NULL,
  `totalRevenue` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`reportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_reports`
--

LOCK TABLES `orders_reports` WRITE;
/*!40000 ALTER TABLE `orders_reports` DISABLE KEYS */;
INSERT INTO `orders_reports` VALUES ('0','7','10','2025','YasserFalfel','5','Regular Falafel','Falafel','0','20','0.00'),('1','7','10','2025','Disfrutar','37','Beef Tartar','MainCourse','0','94','0.00'),('17','1','10','2025','Vivino','10','Pomodoro Spaghetti','MainCourse','0','64','0.00'),('18','2','10','2025','Alena','24','Sake-steamed chicken','MainCourse','0','84','0.00'),('26','2','10','2025','Alena','19','Water','Drinks','0','25','0.00'),('27','31','10','2025','Disfrutar','39','Chocolate Mousse Praline','Dessert','0','60','0.00'),('28','1','10','2025','Vivino','14','Tres Leches','Dessert','0','48','0.00'),('30','7','10','2025','Vivino','10','Pomodoro Spaghetti','MainCourse','0','64','0.00'),('31','7','10','2025','YasserFalfel','1','Cola','Drinks','0','7','0.00'),('32','7','10','2025','Vivino','7','Water','Drinks','0','13','0.00'),('33','1','10','2025','Vivino','10','Pomodoro Spaghetti','MainCourse','1','64','64.00'),('34','4','10','2025','YasserFalfel','3','Cola Zero','Drinks','1','7','7.00'),('35','4','10','2025','YasserFalfel','6','Cheese Falafel','Falafel','1','25','25.00');
/*!40000 ALTER TABLE `orders_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `performance_reports`
--

DROP TABLE IF EXISTS `performance_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `performance_reports` (
  `reportId` varchar(45) NOT NULL,
  `branchId` varchar(45) DEFAULT NULL,
  `month` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `totalOrders` varchar(45) DEFAULT NULL,
  `totalRevenue` varchar(45) DEFAULT NULL,
  `averageExpectedDeliveryTime` varchar(45) DEFAULT NULL,
  `averageActualDeliveryTime` varchar(45) DEFAULT NULL,
  `onTimeDeliveryRate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`reportId`),
  KEY `performance_reports_ibfk_1` (`branchId`),
  CONSTRAINT `performance_reports_ibfk_1` FOREIGN KEY (`branchId`) REFERENCES `branches` (`branchId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performance_reports`
--

LOCK TABLES `performance_reports` WRITE;
/*!40000 ALTER TABLE `performance_reports` DISABLE KEYS */;
INSERT INTO `performance_reports` VALUES ('101','1','1','2024','600','10500','45','50','0.9'),('102','1','1','2024','657','10600','30','30','1'),('103','1','1','2024','704','10700','21','30','0.7'),('104','1','4','2024','756','12560','26','30','0.86'),('105','1','5','2024','651','13520','23','32','0.71'),('106','1','6','2024','660','12500','23','34','0.67'),('107','1','7','2024','695','13200','21','35','0.6'),('108','1','8','2024','705','14500','24','36','0.66'),('109','1','9','2024','750','13000','25','36','0.69'),('110','1','10','2024','765','12600','25','35','0.69'),('111','1','11','2024','762','13700','21','32','0.65'),('112','1','12','2024','798','13200','25','34','0.73'),('201','2','1','2024','500','15000','26','39','0.66'),('202','2','2','2024','600','16000','21','36','0.58'),('203','2','3','2024','200','15000','31','39','0.79'),('204','2','4','2024','300','12000','32','39','0.82'),('205','2','5','2024','400','16000','36','45','0.8'),('206','2','6','2024','900','14500','32','40','0.8'),('207','2','7','2024','700','13500','31','45','0.68'),('208','2','8','2024','950','12000','36','40','0.9'),('209','2','9','2024','850','15000','30','42','0.71'),('210','2','10','2024','985','14000','40','42','0.95'),('211','2','11','2024','875','15000','45','52','0.86'),('212','2','12','2024','879','16500','46','50','0.92');
/*!40000 ALTER TABLE `performance_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarterly_reports`
--

DROP TABLE IF EXISTS `quarterly_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quarterly_reports` (
  `report_id` varchar(45) NOT NULL,
  `res_name` varchar(45) DEFAULT NULL,
  `q_year` varchar(45) DEFAULT NULL,
  `order_quantity` varchar(45) DEFAULT NULL,
  `income` varchar(45) DEFAULT NULL,
  `expected_income` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarterly_reports`
--

LOCK TABLES `quarterly_reports` WRITE;
/*!40000 ALTER TABLE `quarterly_reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `quarterly_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportnum`
--

DROP TABLE IF EXISTS `reportnum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reportnum` (
  `id` varchar(45) NOT NULL,
  `report_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reportnum`
--

LOCK TABLES `reportnum` WRITE;
/*!40000 ALTER TABLE `reportnum` DISABLE KEYS */;
INSERT INTO `reportnum` VALUES ('1','36');
/*!40000 ALTER TABLE `reportnum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `restaurant_name` varchar(45) NOT NULL,
  `branch_address` varchar(45) DEFAULT NULL,
  `branch_id` varchar(45) NOT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES ('Vivino','Haifa','1','0505555555'),('Alena','Tel Aviv','2','0506666666'),('Disfrutar','Madrid','31','0507777777'),('Disfrutar','Barcelona','32','0509999999'),('YasserFalfel','Bosmat Tivon','4','0508888888');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `selections`
--

DROP TABLE IF EXISTS `selections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `selections` (
  `selection_id` varchar(45) NOT NULL,
  `dishId` varchar(45) DEFAULT NULL,
  `selectionName` varchar(45) DEFAULT NULL,
  `selectionPrice` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`selection_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `selections`
--

LOCK TABLES `selections` WRITE;
/*!40000 ALTER TABLE `selections` DISABLE KEYS */;
INSERT INTO `selections` VALUES ('1','1','Big','5'),('10','5','Hot sauce','2'),('11','6','Homos','2'),('12','6','Hot sauce','2'),('13','7','Big','8'),('14','7','Medium','5'),('15','7','Small','3'),('16','7','Ice','5'),('17','8','Big','8'),('18','8','Medium','5'),('19','8','Small','3'),('2','1','Small','0'),('20','8','Ice','5'),('21','9','Big','8'),('22','9','Medium','5'),('23','9','Small','3'),('24','9','Ice','5'),('25','1','Ice','2'),('3','2','Big','5'),('4','2','Small','0'),('5','3','Big','5'),('6','3','Small','0'),('7','4','Big','3'),('8','4','Small','0'),('9','5','Homos','2');
/*!40000 ALTER TABLE `selections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userID` varchar(45) NOT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `creditCard` varchar(45) DEFAULT NULL,
  `userType` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `homeBranchId` varchar(45) DEFAULT NULL,
  `AccountType` enum('PRIVATE','BUSINESS') DEFAULT NULL,
  `homeBranchName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `homeId_fk_idx` (`homeBranchId`),
  CONSTRAINT `homeId_fk` FOREIGN KEY (`homeBranchId`) REFERENCES `branch_managers` (`branchId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('1','Fatmeh','Zoabi','f10@gmail.com','0523269954','987654321','Branch Manager','f10','f10','1','PRIVATE','Vivino'),('123','Lama','Razi','rz23@gmail.com','057936357','1598745632','Branch Manager','lam2','lam2','2','PRIVATE','Alena'),('124','Deema','Malik','dem12zw@gmail.com','0598963654','320450987','Branch Manager','deem3','deem3','31','PRIVATE','Disfrutar'),('125','Rami','Romi','ram@gmail.com','0548963655','159263874','Branch Manager','rom2','rom2','32','PRIVATE','Disfrutar'),('126','Danny','Bo','bo34@gmail.com','0547896933',NULL,'Branch Manager','dann7','dann7','4','BUSINESS','YasserFalfel'),('212212','Yasser','Saadi','Yasser@gmail.com','0509877888','','Qualified Worker','y7','y7','1','BUSINESS','Vivino'),('434343','M7','m7','m7@gmail.com','052009988','','Sponsor','m7','m7','1','BUSINESS','Vivino'),('6987','Jamal','Kamil','jamal@gmail.com','05462312199','','Client','j23','j23','1','BUSINESS','Vivino'),('dfg','dfg','dfgd','dfgdfg','fdgd','dfgdfg','CEO','z1','z1','1','PRIVATE','Vivino');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-07  2:55:58
