-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: travel_agency_db
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (44);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `password` varchar(100) NOT NULL,
  `userType` varchar(255) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','TRAVEL_AGENCY','admin'),(2,'Wanda@1','REGULAR_USER','wanda'),(3,'Hulk@1','REGULAR_USER','hulk'),(4,'Groot@1','REGULAR_USER','groot'),(5,'Marvel@1','REGULAR_USER','marvel');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_vacation_package`
--

DROP TABLE IF EXISTS `user_vacation_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_vacation_package` (
  `user_id` bigint NOT NULL,
  `vacation_package_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`vacation_package_id`),
  KEY `FK5it6wchdgnipxrg3jugf7hpbq` (`vacation_package_id`),
  CONSTRAINT `FK5it6wchdgnipxrg3jugf7hpbq` FOREIGN KEY (`vacation_package_id`) REFERENCES `vacation_package` (`id`),
  CONSTRAINT `FKq0yyxwiiajnfur7swlf0g7urp` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_vacation_package`
--

LOCK TABLES `user_vacation_package` WRITE;
/*!40000 ALTER TABLE `user_vacation_package` DISABLE KEYS */;
INSERT INTO `user_vacation_package` VALUES (2,5),(3,5),(4,5),(5,5),(2,34),(2,38),(2,43);
/*!40000 ALTER TABLE `user_vacation_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vacation_destination`
--

DROP TABLE IF EXISTS `vacation_destination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vacation_destination` (
  `id` bigint NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eamo5ybrucwlg8nfkmtucigqe` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacation_destination`
--

LOCK TABLES `vacation_destination` WRITE;
/*!40000 ALTER TABLE `vacation_destination` DISABLE KEYS */;
INSERT INTO `vacation_destination` VALUES (3,'Amsterdam'),(7,'Hawaii'),(8,'Kyoto'),(2,'London'),(4,'Plitvitze'),(9,'Tokyo'),(1,'Tuscany'),(5,'Venice');
/*!40000 ALTER TABLE `vacation_destination` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vacation_package`
--

DROP TABLE IF EXISTS `vacation_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vacation_package` (
  `id` bigint NOT NULL,
  `details` varchar(255) DEFAULT NULL,
  `endDate` date NOT NULL,
  `maxNrOfBookings` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `nrOfBookings` int DEFAULT NULL,
  `packageStatus` varchar(255) NOT NULL,
  `price` double DEFAULT NULL,
  `startDate` date NOT NULL,
  `vacationDestinationId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_n2rsnvkhj0htw07rn9mxn3j6` (`name`),
  KEY `FKg2lu214yevv1852yqd0ydcjoc` (`vacationDestinationId`),
  CONSTRAINT `FKg2lu214yevv1852yqd0ydcjoc` FOREIGN KEY (`vacationDestinationId`) REFERENCES `vacation_destination` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vacation_package`
--

LOCK TABLES `vacation_package` WRITE;
/*!40000 ALTER TABLE `vacation_package` DISABLE KEYS */;
INSERT INTO `vacation_package` VALUES (1,'Visit the \"floating city\"  of Venice, join the carnival, take a ride in one of the gondolas,\n or just enjoy a cup of Gelato in front of the Doge Palace.','2022-04-27',3,'Spring in Venice',2,'IN_PROGRESS',4500,'2022-04-14',5),(2,'Visit the old Italy, explore the city, italian cuisine, and enjoy the warm italian summer. Breakfast is served at the hotel.','2022-06-24',7,'Summer in Tuscany',2,'IN_PROGRESS',3450,'2022-06-15',1),(4,'Return for the summer to Tuscany, the perfect place for one who wants to get away from the madness of the city life.','2022-08-11',10,'Back to Italy for the summer',2,'IN_PROGRESS',1800,'2022-07-15',1),(5,'Spend Christmas Eve this year on the warm coasts of the Hawaiian islands. You won\'t regret it! ','2022-12-28',4,'Aloha and merry Christmas!',4,'BOOKED',2500,'2022-12-20',7),(34,'One of the most beautiful capitals, Amsterdam attracts tourists all around the world. \nIt is the perfect holiday destination for anyone fond of the Netherlands.','2022-04-29',3,'Travelling to Amsterdam',1,'IN_PROGRESS',2800,'2022-04-14',3),(37,'Plitvitze is one of the most beautiful natural parks in Croatia. \nThe several waterfalls, lakes and the colourful vegetation, won\'t fail to amaze any visitor.','2022-06-29',6,'Summer in Croatia',0,'NOT_BOOKED',3400,'2022-06-16',4),(38,'The nature park of Plitvitze should be on you bucket list no matter what! \nIt is the most beautiful place where you can really let your guard down. \nFood and accomodation are included in the price.','2022-03-27',3,'Plitvitze lakes',3,'BOOKED',345,'2022-03-23',4),(41,'Perfect honeymoon destination for young couples.','2022-04-08',3,'Plan your honeymoon in Hawaii',0,'NOT_BOOKED',3456,'2022-03-30',7),(43,'One of the most populated, still considered as the #1 safest capital of the world, \nTokyo nevertheless won\'t fail to impress anyone with its various attractions,\nentertainment facilities and colourful city life.','2022-03-24',3,'Visiting Tokyo',1,'IN_PROGRESS',456,'2022-03-16',9);
/*!40000 ALTER TABLE `vacation_package` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-14  6:02:26
