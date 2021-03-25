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
-- Table structure for table `user_data`
--

DROP TABLE IF EXISTS `user_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(70) NOT NULL,
  `ugrant` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data`
--

LOCK TABLES `user_data` WRITE;
/*!40000 ALTER TABLE `user_data` DISABLE KEYS */;
INSERT INTO `user_data` VALUES (1,'Saman Firouzi','saman','$2a$08$fL7u5xcvsZl78su29x1ti.dxI.9rYO8t0q5wk2ROJ.1cdR53bmaVG','ADMIN'),(2,'Saman','test','$2a$10$esBEVmSiA/cUuBvuy7I69utD9EunDIBBsiHOHwCjxkAF9q7W48Ubu','CUSTOMER'),(3,'Saman','test2','$2a$10$xfrGMwpkUhFNITT6y6MmZeE7eycXF3i6uIJ2WWibxGeGAVTuuUL02','CUSTOMER'),(4,'Saman22','test100','$2a$10$pvcJkycOwa6cbt52sA5XueiNFoM686vRLxBBfCs3wpWm3bu0gVZAm','CUSTOMER'),(5,'sa','sa','$2a$10$HjpbDpaLqZiRjac4.llp9eSZYk0RbRetFuTBfU17VX8SbEXRbzqUq','CUSTOMER'),(6,'baran','baran','$2a$10$NKfY5yVGtVq85R1FehVaiOm.675xerYGDhiviFFonHUt/OY091qSS','CUSTOMER'),(7,'Admin','admin','$2a$10$QdK.ezxY7.eQHsAGrOIWj.rCvV.e9kp3erBPNYCfOLW1XjLNBV1TC','ADMIN'),(8,'ali','ali','$2a$10$XhlcZYCRE6X8v1YiPkVGpOKlyJtUkRRu3rV.Da/HeZS.2CNageX9u','CUSTOMER'),(10,'sati','sati','$2a$10$QPBFC2kyzjaHbxCTgGCajePk0Q0KxA/hiMJVmDz0GIENXTOm8BnOq','CUSTOMER'),(11,'demo','demo','$2a$10$SRIQRGIIRlj8HNVgJlsSduN5r2gFQJ72BoKpFtyCrlk8o3DjChdbC','CUSTOMER'),(12,'kaveh','kaveh','$2a$10$iBY/NWYjryOGiG6IoZAYFu20FmuGWY9UVN1t0yOvxkCKOZY18AW0O','CUSTOMER');
/*!40000 ALTER TABLE `user_data` ENABLE KEYS */;
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
