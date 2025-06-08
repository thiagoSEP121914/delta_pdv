-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: delta_pdv
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produtos` (
  `ID_Produto` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) DEFAULT NULL,
  `caminho_imagem` text DEFAULT NULL,
  `Descricao` text DEFAULT NULL,
  `Preco_Unitario` decimal(10,2) DEFAULT NULL,
  `Custo` decimal(10,2) DEFAULT 0.00,
  `lucro` decimal(10,2) GENERATED ALWAYS AS (`Preco_Unitario` - `Custo`) VIRTUAL,
  `Quantidade_Estoque` int(11) DEFAULT NULL,
  `id_categoria` int(11) DEFAULT NULL,
  `ativo` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`ID_Produto`),
  KEY `fk_categoria` (`id_categoria`),
  CONSTRAINT `fk_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`ID_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos`
--

LOCK TABLES `produtos` WRITE;
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
INSERT INTO `produtos` (`ID_Produto`, `Nome`, `caminho_imagem`, `Descricao`, `Preco_Unitario`, `Custo`, `Quantidade_Estoque`, `id_categoria`, `ativo`) VALUES (1,'Cachimbo','imgs/1748990638564_coca.jpg','Para usufruir de drgoa',50.00,30.00,15,1,0),(2,'Nathan','imgs/1748998557990_indian-man-7061278_640.jpg','Indiano',100.00,50.00,30,2,0),(3,'Nathan','imgs/1749061252873_indian-man-7061278_640.jpg','indiano',50.00,30.00,6,2,0),(4,'Naruto maconha','imgs/1749061761755_cachimbo.jpg','sdfs',100.00,50.00,7,1,0),(5,'india','imgs/1749061850714_coca.jpg','dsd',50.00,33.00,5,2,1),(6,'Sherek','imgs/1749094709319_shureki.jpg','Ogro',80.00,50.00,0,3,1),(7,'Nathan pedro veloso','imgs/1749100983977_indian-man-7061278_640.jpg','Indiano',100.00,50.00,23,4,1),(8,'cocaina','imgs/1749263151177_pcocaina.jpeg','Pinos de pó',10.00,5.00,81,5,1),(9,'Maconha','imgs/1749263265874_uv0twmotjid71.jpg','paranga',100.00,50.00,995,5,1);
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-08  1:07:44
