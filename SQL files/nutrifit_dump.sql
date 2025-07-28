-- MySQL dump 10.13  Distrib 8.0.42, for macos15 (x86_64)
--
-- Host: localhost    Database: Nutrifit
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `MealLog`
--

DROP TABLE IF EXISTS `MealLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MealLog` (
  `LogID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) DEFAULT NULL,
  `FoodID` int DEFAULT NULL,
  `Quantity` double DEFAULT NULL,
  `MealTime` datetime DEFAULT NULL,
  `IngredientSummary` text,
  `mealName` varchar(100) DEFAULT NULL,
  `ingredients` text,
  `totalCalories` double DEFAULT NULL,
  `totalProtein` double DEFAULT NULL,
  `totalFat` double DEFAULT NULL,
  `totalCarbs` double DEFAULT NULL,
  PRIMARY KEY (`LogID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MealLog`
--

LOCK TABLES `MealLog` WRITE;
/*!40000 ALTER TABLE `MealLog` DISABLE KEYS */;
INSERT INTO `MealLog` VALUES (1,'',1,1,'2025-07-21 12:00:00',NULL,'MyMeal','Chicken',100,10,5,15),(2,NULL,NULL,NULL,'2025-07-21 21:20:14',NULL,'butter chicken',NULL,250,15,10,NULL),(3,NULL,NULL,NULL,'2025-07-22 18:38:36',NULL,'meal1',NULL,250,15,10,NULL),(4,NULL,NULL,NULL,'2025-07-22 19:01:47',NULL,'meal2',NULL,250,15,10,NULL),(5,NULL,NULL,NULL,'2025-07-22 19:08:55',NULL,'meal3 ',NULL,250,15,10,NULL),(6,NULL,NULL,NULL,'2025-07-22 19:10:19',NULL,'meal4',NULL,250,15,10,NULL),(7,NULL,NULL,NULL,'2025-07-22 19:12:39',NULL,'unhealthymealtest',NULL,250,15,10,NULL),(8,NULL,NULL,NULL,'2025-07-22 19:15:54',NULL,'hell',NULL,250,15,10,NULL),(9,NULL,NULL,NULL,'2025-07-22 19:18:17',NULL,'hello',NULL,0,0,0,NULL),(10,NULL,NULL,NULL,'2025-07-22 19:21:19',NULL,'hch',NULL,0,0,0,NULL),(11,NULL,NULL,NULL,'2025-07-22 19:26:17',NULL,'hekk',NULL,0,0,0,NULL),(12,NULL,NULL,NULL,'2025-07-22 19:39:20',NULL,'meal12',NULL,0,0,0,NULL),(13,NULL,NULL,NULL,'2025-07-22 19:44:41',NULL,'meal69',NULL,0,0,0,NULL),(14,NULL,NULL,NULL,'2025-07-22 19:49:51',NULL,'hello',NULL,0,0,0,NULL),(15,NULL,NULL,NULL,'2025-07-22 20:16:09',NULL,'yoo',NULL,0,0,0,NULL),(16,NULL,NULL,NULL,'2025-07-22 20:27:14',NULL,'ieie',NULL,0,0,0,NULL),(17,NULL,NULL,NULL,'2025-07-22 20:28:27',NULL,'djjd',NULL,0,0,0,NULL),(18,NULL,NULL,NULL,'2025-07-22 20:30:15',NULL,'hehe',NULL,0,0,0,NULL),(19,NULL,NULL,NULL,'2025-07-22 20:42:15',NULL,'dhe',NULL,0,0,0,NULL),(20,NULL,NULL,NULL,'2025-07-22 20:46:52',NULL,'de',NULL,0,0,0,NULL),(21,NULL,NULL,NULL,'2025-07-22 20:54:22',NULL,'meal22',NULL,0,0,0,NULL),(22,NULL,NULL,NULL,'2025-07-22 21:27:15','Cheese, cheddar','djdj',NULL,1888,141.64000000000001,137.74,19.75),(23,NULL,NULL,NULL,'2025-07-23 16:22:31','Chicken, broiler, meat only, roasted','meal2',NULL,380,57.86,14.82,0),(24,NULL,NULL,NULL,'2025-07-26 19:27:23','Cheese, cheddar','hehe',NULL,3776,283.28000000000003,275.48,39.5),(25,NULL,NULL,NULL,'2025-07-26 19:42:37','Cheese, cheddar','hdhd',NULL,5664,424.9200000000001,413.21999999999997,59.25),(26,NULL,NULL,NULL,'2025-07-26 19:52:51','Cheese, cheddar','ueue',NULL,944,70.82000000000001,68.87,9.875),(27,NULL,NULL,NULL,'2025-07-26 20:09:50','Cheese, cheddar, Chicken, broiler, meat only, roasted','hii',NULL,9820,766.0600000000001,703.5200000000001,98.75),(28,NULL,NULL,NULL,'2025-07-26 20:23:49','Cheese, cheddar','jeje',NULL,1888,141.64000000000001,137.74,19.75),(29,NULL,NULL,NULL,'2025-07-26 20:25:41','Cheese, cheddar','hee',NULL,3776,283.28000000000003,275.48,39.5),(30,NULL,NULL,NULL,'2025-07-26 20:29:54','Cheese, cheddar','new',NULL,3776,283.28000000000003,275.48,39.5),(31,NULL,NULL,NULL,'2025-07-26 20:31:45','Chicken, broiler, meat only, roasted','hw',NULL,190,28.93,7.41,0),(32,NULL,NULL,NULL,'2025-07-26 20:38:28','Carrot, raw, Cheese, cheddar, Banana, raw','updated',NULL,625,27.15,34.72,56.59),(33,NULL,NULL,NULL,'2025-07-26 20:54:26','Cheese, cheddar, Apple, raw, with skin','hel',NULL,510,24.56,34.160000000000004,28.950000000000003),(34,NULL,NULL,NULL,'2025-07-26 20:55:29','Cheese, cheddar, Chicken, broiler, meat only, roasted','jddj',NULL,1002,77.00999999999999,75.05,2.66),(35,NULL,NULL,NULL,'2025-07-26 21:15:31','Apple, raw, with skin','new ',NULL,104,0.52,0.34,27.62),(36,NULL,NULL,NULL,'2025-07-27 16:24:39','Cheese, cheddar, Chicken, broiler, meat only, roasted','meal1',NULL,583,69.88,31.73,0.665),(37,NULL,NULL,NULL,'2025-07-27 16:53:02','Cheese, cheddar, Chicken, broiler, meat only, roasted','meal2',NULL,583,69.88,31.73,0.665),(38,NULL,NULL,NULL,'2025-07-27 16:54:04','Banana, raw','meal3',NULL,445,5.45,1.65,114.2);
/*!40000 ALTER TABLE `MealLog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Profile`
--

DROP TABLE IF EXISTS `Profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Profile` (
  `user_id` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `height` double DEFAULT NULL,
  `activity_level` varchar(50) DEFAULT NULL,
  `goal` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Profile`
--

LOCK TABLES `Profile` WRITE;
/*!40000 ALTER TABLE `Profile` DISABLE KEYS */;
INSERT INTO `Profile` VALUES (2,'jasveen ',21,'male',85,183,'high','gain muscle'),(3,'jasveen',21,'male',85,182,'high','loss'),(4,'jasveen ',21,'male',85,183,'high','weight loss');
/*!40000 ALTER TABLE `Profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserGoals`
--

DROP TABLE IF EXISTS `UserGoals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UserGoals` (
  `goal_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `nutrient` varchar(100) NOT NULL,
  `direction` varchar(10) NOT NULL,
  `modifier` varchar(20) NOT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`goal_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `usergoals_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserGoals`
--

LOCK TABLES `UserGoals` WRITE;
/*!40000 ALTER TABLE `UserGoals` DISABLE KEYS */;
INSERT INTO `UserGoals` VALUES (11,1,'FAT (TOTAL LIPIDS)','Decrease','Slightly','5','2025-07-26 23:52:32'),(12,3,'SUGARS, TOTAL','Decrease','By a lot','15','2025-07-27 01:15:11'),(13,4,'FAT (TOTAL LIPIDS)','Increase','Slightly','5','2025-07-27 20:24:10');
/*!40000 ALTER TABLE `UserGoals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (1,'jasss','JAsveen@12345'),(2,'jsahani','jasvde'),(3,'yorku','Jasveen'),(4,'jsahani9','jasveen12345');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-27 17:07:35
