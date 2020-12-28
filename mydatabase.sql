CREATE DATABASE  IF NOT EXISTS `users` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `users`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: users
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `billingdata`
--

DROP TABLE IF EXISTS `billingdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `billingdata` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `UserId` int DEFAULT NULL,
  `Money` int DEFAULT NULL,
  `Sdate` date DEFAULT NULL,
  `Edate` date DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billingdata`
--

LOCK TABLES `billingdata` WRITE;
/*!40000 ALTER TABLE `billingdata` DISABLE KEYS */;
INSERT INTO `billingdata` VALUES (1,9,1000,'2020-12-24','2021-01-24'),(2,10,1000,'2020-12-24','2021-01-24'),(3,14,1000,'2020-12-26','2021-01-26');
/*!40000 ALTER TABLE `billingdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coachdata`
--

DROP TABLE IF EXISTS `coachdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coachdata` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Age` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Password_UNIQUE` (`Password`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coachdata`
--

LOCK TABLES `coachdata` WRITE;
/*!40000 ALTER TABLE `coachdata` DISABLE KEYS */;
INSERT INTO `coachdata` VALUES (1,'Hello','HHHHH',1),(2,'c','c',1);
/*!40000 ALTER TABLE `coachdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercisedata`
--

DROP TABLE IF EXISTS `exercisedata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercisedata` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `exercise` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercisedata`
--

LOCK TABLES `exercisedata` WRITE;
/*!40000 ALTER TABLE `exercisedata` DISABLE KEYS */;
INSERT INTO `exercisedata` VALUES (1,'Chest'),(2,'Arm'),(3,'Leg');
/*!40000 ALTER TABLE `exercisedata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagedata`
--

DROP TABLE IF EXISTS `messagedata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messagedata` (
  `UserId` int NOT NULL,
  `Message` varchar(200) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `ExerciseId` int DEFAULT NULL,
  `IsReaded` tinyint(1) DEFAULT NULL,
  KEY `fk_userIdMess` (`UserId`),
  KEY `fk_exercise` (`ExerciseId`),
  CONSTRAINT `fk_exercise` FOREIGN KEY (`ExerciseId`) REFERENCES `coachdata` (`Id`) ON DELETE SET NULL,
  CONSTRAINT `fk_userIdMess` FOREIGN KEY (`UserId`) REFERENCES `usersdata` (`Id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messagedata`
--

LOCK TABLES `messagedata` WRITE;
/*!40000 ALTER TABLE `messagedata` DISABLE KEYS */;
INSERT INTO `messagedata` VALUES (13,'Hello Boys!!','2020-12-26',2,0),(14,'Hello Boys!!','2020-12-26',2,0),(14,'!!!','2020-12-27',2,0),(14,'Man!','2020-12-29',2,0),(14,'Max!','2020-12-20',2,0);
/*!40000 ALTER TABLE `messagedata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersdata`
--

DROP TABLE IF EXISTS `usersdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usersdata` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Age` int DEFAULT NULL,
  `Report` varchar(200) DEFAULT NULL,
  `SubDate` date DEFAULT NULL,
  `CoachId` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Password_UNIQUE` (`Password`),
  KEY `fk_cid` (`CoachId`),
  KEY `fk_subdate` (`SubDate`),
  CONSTRAINT `fk_cid` FOREIGN KEY (`CoachId`) REFERENCES `coachdata` (`Id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersdata`
--

LOCK TABLES `usersdata` WRITE;
/*!40000 ALTER TABLE `usersdata` DISABLE KEYS */;
INSERT INTO `usersdata` VALUES (9,'tqwed','424',111,NULL,'2021-01-24',1),(13,'Ahmed','rx13123',19,'Bad','2021-01-24',2),(14,'Zaki','rx12323',19,'Shit','2021-01-26',2),(15,'Mohamed','rx123123',19,NULL,'2021-01-26',2);
/*!40000 ALTER TABLE `usersdata` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-28 11:59:27
