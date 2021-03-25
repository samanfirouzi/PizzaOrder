-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pizza
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `discount` float NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(500) NOT NULL,
  `image` varchar(145) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
INSERT INTO `promotion` VALUES (1,10,'Family Deal','Enjoy with your family. tow pizza medium size are  in this package. Enjoy with your family. ','images/upload_cc6fcd8cffda0b800f9d2cb742527f4d.jpg'),(2,12,'Movie Deal','Do you like watching movies? we think you need some food so. Enjoy with your family on watching movie','images/upload_e6ec8d4183ff0f46c99fdd9037df8775.webp'),(3,15,'Simple dinner ','Simple dinner with good price, have a nice night. sweet dream. night night','images/upload_6357c5594ea3a77f784c5d67f9826f0e.webp'),(6,5,'Drink deal','Do you feel you are thirsty? are you are with your friends? what you want now? some delicious drinks? you are in the right place.','images/upload_1359694a1bec8e1a6e71edc427758aaf.jpg'),(9,20,'Good lunch - double','Are you hungry?  your friend too. so it is right chose. also you can enjoy with good side Rosted Buttremut Squash','images/upload_3e9afe14c4005d2ea563a8b61c2a8ccf.webp'),(10,10,'healthy food','healthy food is simple vegetarian food with fresh natural drink. it is so cheep but so delicious. enjoy it. ','images/upload_6a012769a0c5148ec9b4aad896d5c01b.jpg'),(11,15,'Discounts for certain products','Do you offer a product on the menu that you want to promote at a given moment? Would you like to increase its sales? Setting a discount for a particular product or product group in the online food ordering system is a great idea.','images/upload_f49dd020c6d0798979eff6769f13dff2.jpg'),(14,20,'Party day','Do you have a party? what about your food, we know, it can be so expensive. but no worry. we have good offer for you. just enjoy with your party.','images/upload_adf5c7b43f497cd571b7b625e37b9040.jpg'),(15,3,'test','test desc','images/upload_0bba88f4e932ab9db4dd8a87fde7c1a9.jpg'),(16,23,'asd','sdf s sdf sf sf ','images/upload_b7dc3013b4fc899cbc8afe28a57ce839.jpg');
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-23 11:59:11
