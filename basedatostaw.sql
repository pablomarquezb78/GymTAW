CREATE DATABASE  IF NOT EXISTS `basedatostaw` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `basedatostaw`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: basedatostaw
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `asignacion_cliente_dietista`
--

DROP TABLE IF EXISTS `asignacion_cliente_dietista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asignacion_cliente_dietista` (
  `idasignacion_cliente_dietista` int NOT NULL AUTO_INCREMENT,
  `cliente` int DEFAULT NULL,
  `dietista` int DEFAULT NULL,
  PRIMARY KEY (`idasignacion_cliente_dietista`),
  KEY `asignacion-cliente_fk_idx` (`cliente`),
  KEY `asignacion-dietista_fk_idx` (`dietista`),
  CONSTRAINT `asignacion-cliente_fk` FOREIGN KEY (`cliente`) REFERENCES `user` (`iduser`),
  CONSTRAINT `asignacion-dietista_fk` FOREIGN KEY (`dietista`) REFERENCES `user` (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignacion_cliente_dietista`
--

LOCK TABLES `asignacion_cliente_dietista` WRITE;
/*!40000 ALTER TABLE `asignacion_cliente_dietista` DISABLE KEYS */;
INSERT INTO `asignacion_cliente_dietista` VALUES (1,1,5),(2,7,6),(3,8,6);
/*!40000 ALTER TABLE `asignacion_cliente_dietista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asignacion_cliente_entrenador`
--

DROP TABLE IF EXISTS `asignacion_cliente_entrenador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asignacion_cliente_entrenador` (
  `idasignacion_cliente_entrenador` int NOT NULL AUTO_INCREMENT,
  `cliente` int DEFAULT NULL,
  `entrenador` int DEFAULT NULL,
  PRIMARY KEY (`idasignacion_cliente_entrenador`),
  KEY `asignacion_cliente_fk_idx` (`cliente`),
  KEY `asignacion-entrenador_fk_idx` (`entrenador`),
  CONSTRAINT `asignacion-entrenador_fk` FOREIGN KEY (`entrenador`) REFERENCES `user` (`iduser`),
  CONSTRAINT `asignacion_cliente_fk` FOREIGN KEY (`cliente`) REFERENCES `user` (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignacion_cliente_entrenador`
--

LOCK TABLES `asignacion_cliente_entrenador` WRITE;
/*!40000 ALTER TABLE `asignacion_cliente_entrenador` DISABLE KEYS */;
INSERT INTO `asignacion_cliente_entrenador` VALUES (1,1,3),(2,1,4);
/*!40000 ALTER TABLE `asignacion_cliente_entrenador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asignacion_plato_ingrediente_dietista_creador`
--

DROP TABLE IF EXISTS `asignacion_plato_ingrediente_dietista_creador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asignacion_plato_ingrediente_dietista_creador` (
  `idasignacion_plato_ingrediente_dietista_creador` int NOT NULL AUTO_INCREMENT,
  `plato` int DEFAULT NULL,
  `ingrediente` int DEFAULT NULL,
  `dietista` int DEFAULT NULL,
  PRIMARY KEY (`idasignacion_plato_ingrediente_dietista_creador`),
  KEY `asignacion_plato_con_plato_idx` (`plato`),
  KEY `asignacion_con_ingrediente_idx` (`ingrediente`),
  KEY `asignacion_con_dietista_idx` (`dietista`),
  CONSTRAINT `asignacion_con_dietista` FOREIGN KEY (`dietista`) REFERENCES `user` (`iduser`),
  CONSTRAINT `asignacion_con_ingrediente` FOREIGN KEY (`ingrediente`) REFERENCES `ingrediente` (`idingrediente`),
  CONSTRAINT `asignacion_plato_con_plato` FOREIGN KEY (`plato`) REFERENCES `plato` (`idplato`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignacion_plato_ingrediente_dietista_creador`
--

LOCK TABLES `asignacion_plato_ingrediente_dietista_creador` WRITE;
/*!40000 ALTER TABLE `asignacion_plato_ingrediente_dietista_creador` DISABLE KEYS */;
INSERT INTO `asignacion_plato_ingrediente_dietista_creador` VALUES (7,9,1,5),(8,9,2,5),(9,9,2,5),(13,11,1,5),(14,11,3,5),(15,11,9,5),(16,12,10,5),(17,12,8,5),(27,15,1,6),(28,15,3,6),(29,15,9,6),(30,16,11,6),(31,16,12,6),(32,16,14,6),(33,17,8,6),(34,17,10,6),(35,17,15,6),(36,17,16,6);
/*!40000 ALTER TABLE `asignacion_plato_ingrediente_dietista_creador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cantidad_ingrediente-plato-comida`
--

DROP TABLE IF EXISTS `cantidad_ingrediente-plato-comida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cantidad_ingrediente-plato-comida` (
  `idcantidad_ingrediente-plato` int NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `tipo_cantidad` int DEFAULT NULL,
  `cantidad_consumida` int DEFAULT NULL,
  `ingrediente` int DEFAULT NULL,
  `plato` int DEFAULT NULL,
  `comida` int DEFAULT NULL,
  PRIMARY KEY (`idcantidad_ingrediente-plato`),
  KEY `cantidad-tipo_cantidad_fk_idx` (`tipo_cantidad`),
  KEY `cantidad-ingrediente_fk_idx` (`ingrediente`),
  KEY `cantidad-plato_fk_idx` (`plato`),
  KEY `cantidad-comida_fk_idx` (`comida`),
  CONSTRAINT `cantidad-comida_fk` FOREIGN KEY (`comida`) REFERENCES `comida` (`idcomida`),
  CONSTRAINT `cantidad-ingrediente_fk` FOREIGN KEY (`ingrediente`) REFERENCES `ingrediente` (`idingrediente`),
  CONSTRAINT `cantidad-plato_fk` FOREIGN KEY (`plato`) REFERENCES `plato` (`idplato`),
  CONSTRAINT `cantidad-tipo_cantidad_fk` FOREIGN KEY (`tipo_cantidad`) REFERENCES `tipo_cantidad` (`idtipo_cantidad`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cantidad_ingrediente-plato-comida`
--

LOCK TABLES `cantidad_ingrediente-plato-comida` WRITE;
/*!40000 ALTER TABLE `cantidad_ingrediente-plato-comida` DISABLE KEYS */;
INSERT INTO `cantidad_ingrediente-plato-comida` VALUES (25,0,1,NULL,1,11,2),(26,0,1,NULL,3,11,2),(27,0,1,NULL,9,11,2),(53,200,2,NULL,1,9,3),(54,100,2,NULL,2,9,3),(71,150,2,NULL,8,17,5),(72,50,2,NULL,10,17,5),(73,100,2,NULL,15,17,5),(74,100,2,NULL,16,17,5),(75,200,2,NULL,1,15,6),(76,100,3,NULL,3,15,6),(77,100,2,NULL,9,15,6),(78,100,2,NULL,11,16,7),(79,50,2,NULL,12,16,7),(80,2,4,NULL,14,16,7),(81,50,2,NULL,8,17,8),(82,20,2,NULL,10,17,8),(83,20,2,NULL,15,17,8),(84,20,2,NULL,16,17,8);
/*!40000 ALTER TABLE `cantidad_ingrediente-plato-comida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comida`
--

DROP TABLE IF EXISTS `comida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comida` (
  `idcomida` int NOT NULL AUTO_INCREMENT,
  `realizado` tinyint DEFAULT NULL,
  `tipo_comida` int DEFAULT NULL,
  `dia_dieta` int DEFAULT NULL,
  PRIMARY KEY (`idcomida`),
  KEY `comida-tipo_comida_fk_idx` (`tipo_comida`),
  KEY `comida-dia_dieta_fk_idx` (`dia_dieta`),
  CONSTRAINT `comida-dia_dieta_fk` FOREIGN KEY (`dia_dieta`) REFERENCES `dia_dieta` (`iddia_dieta`),
  CONSTRAINT `comida-tipo_comida_fk` FOREIGN KEY (`tipo_comida`) REFERENCES `tipo_comida` (`idtipo_comida`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comida`
--

LOCK TABLES `comida` WRITE;
/*!40000 ALTER TABLE `comida` DISABLE KEYS */;
INSERT INTO `comida` VALUES (2,NULL,3,2),(3,NULL,1,2),(4,NULL,5,2),(5,NULL,2,3),(6,NULL,3,4),(7,NULL,5,4),(8,NULL,2,5);
/*!40000 ALTER TABLE `comida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dia_dieta`
--

DROP TABLE IF EXISTS `dia_dieta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dia_dieta` (
  `iddia_dieta` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `seguimiento` varchar(1500) DEFAULT NULL,
  `cliente` int DEFAULT NULL,
  `dietista` int DEFAULT NULL,
  PRIMARY KEY (`iddia_dieta`),
  KEY `dia_dieta-cliente_fk_idx` (`cliente`),
  KEY `dia_dieta-dietista_fk_idx` (`dietista`),
  CONSTRAINT `dia_dieta-cliente_fk` FOREIGN KEY (`cliente`) REFERENCES `user` (`iduser`),
  CONSTRAINT `dia_dieta-dietista_fk` FOREIGN KEY (`dietista`) REFERENCES `user` (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dia_dieta`
--

LOCK TABLES `dia_dieta` WRITE;
/*!40000 ALTER TABLE `dia_dieta` DISABLE KEYS */;
INSERT INTO `dia_dieta` VALUES (2,'2024-06-26 10:48:56',NULL,1,5),(3,'2024-06-26 00:00:00',NULL,7,6),(4,'2024-06-26 00:00:00',NULL,8,6),(5,'2024-06-27 00:00:00',NULL,8,6);
/*!40000 ALTER TABLE `dia_dieta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dia_entrenamiento`
--

DROP TABLE IF EXISTS `dia_entrenamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dia_entrenamiento` (
  `iddia_entrenamiento` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `seguimiento` varchar(1500) DEFAULT NULL,
  `cliente` int DEFAULT NULL,
  `rutina` int DEFAULT NULL,
  PRIMARY KEY (`iddia_entrenamiento`),
  KEY `dia_entrenamiento-cliente_idx` (`cliente`),
  KEY `dia_entrenamiento-rutina_fk_idx` (`rutina`),
  CONSTRAINT `dia_entrenamiento-cliente` FOREIGN KEY (`cliente`) REFERENCES `user` (`iduser`),
  CONSTRAINT `dia_entrenamiento-rutina_fk` FOREIGN KEY (`rutina`) REFERENCES `rutina` (`idrutina`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dia_entrenamiento`
--

LOCK TABLES `dia_entrenamiento` WRITE;
/*!40000 ALTER TABLE `dia_entrenamiento` DISABLE KEYS */;
INSERT INTO `dia_entrenamiento` VALUES (1,'2000-01-01 00:00:00',NULL,1,1),(4,'2024-05-20 00:00:00',NULL,1,NULL);
/*!40000 ALTER TABLE `dia_entrenamiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejercicio`
--

DROP TABLE IF EXISTS `ejercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ejercicio` (
  `idejercicio` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `tipo` int DEFAULT NULL,
  `descripcion` varchar(350) DEFAULT NULL,
  `enlace_video` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`idejercicio`),
  KEY `ejercicio-tipo_ejercicio_fk_idx` (`tipo`),
  CONSTRAINT `ejercicio-tipo_ejercicio_fk` FOREIGN KEY (`tipo`) REFERENCES `tipo_ejercicio` (`idtipo_ejercicio`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejercicio`
--

LOCK TABLES `ejercicio` WRITE;
/*!40000 ALTER TABLE `ejercicio` DISABLE KEYS */;
INSERT INTO `ejercicio` VALUES (1,'Press de Banca',1,'Ejercicio orientado a el pectoral mayor','https://videosample.com'),(2,'Press Inclinado',1,'Ejercicio orientado a el pectoral superior','https://videosample.com'),(3,'Dominadas',2,'Descripion dominada','https://videosample.com'),(4,'Sprints',4,'Descripcion sprints','https://videosample.com'),(5,'Bicicleta estática',3,'Descripcion Bicicleta estática','https://videosample.com');
/*!40000 ALTER TABLE `ejercicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_ejercicio`
--

DROP TABLE IF EXISTS `feedback_ejercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback_ejercicio` (
  `idfeedback_ejercicio` int NOT NULL AUTO_INCREMENT,
  `implementacion` int DEFAULT NULL,
  `dia_entrenamiento` int DEFAULT NULL,
  `realizado` tinyint DEFAULT NULL,
  `seguimiento_sets_done` varchar(45) DEFAULT NULL,
  `seguimiento_tiempo_done` varchar(45) DEFAULT NULL,
  `seguimiento_kilocalorias_done` varchar(45) DEFAULT NULL,
  `seguimiento_metros__done` varchar(45) DEFAULT NULL,
  `seguimiento_peso_done` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idfeedback_ejercicio`),
  KEY `implementacion_idx` (`implementacion`),
  KEY `diaEntrenamiento_idx` (`dia_entrenamiento`),
  CONSTRAINT `dia_entrenamiento` FOREIGN KEY (`dia_entrenamiento`) REFERENCES `dia_entrenamiento` (`iddia_entrenamiento`),
  CONSTRAINT `implementacion` FOREIGN KEY (`implementacion`) REFERENCES `implementacion_ejercicio-rutina` (`idimplementacion_ejercicio-rutina`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_ejercicio`
--

LOCK TABLES `feedback_ejercicio` WRITE;
/*!40000 ALTER TABLE `feedback_ejercicio` DISABLE KEYS */;
INSERT INTO `feedback_ejercicio` VALUES (1,2,1,1,'3',NULL,NULL,NULL,NULL),(2,4,1,NULL,NULL,NULL,NULL,NULL,NULL),(3,5,1,1,'5',NULL,NULL,NULL,NULL),(4,6,1,1,NULL,'900',NULL,NULL,NULL);
/*!40000 ALTER TABLE `feedback_ejercicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_ejercicioserie`
--

DROP TABLE IF EXISTS `feedback_ejercicioserie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback_ejercicioserie` (
  `idfeedback_ejercicioserie` int NOT NULL AUTO_INCREMENT,
  `Serie` varchar(45) DEFAULT NULL,
  `Repeticiones_realizadas` varchar(45) DEFAULT NULL,
  `Peso_realizado` varchar(45) DEFAULT NULL,
  `Tiempo_realizado` varchar(45) DEFAULT NULL,
  `Kilocalorias_realizado` varchar(45) DEFAULT NULL,
  `Metros_realizado` varchar(45) DEFAULT NULL,
  `feedback_ejercicio` int DEFAULT NULL,
  PRIMARY KEY (`idfeedback_ejercicioserie`),
  KEY `feedback_ejercicio_idx` (`feedback_ejercicio`),
  CONSTRAINT `feedback_ejercicio` FOREIGN KEY (`feedback_ejercicio`) REFERENCES `feedback_ejercicio` (`idfeedback_ejercicio`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_ejercicioserie`
--

LOCK TABLES `feedback_ejercicioserie` WRITE;
/*!40000 ALTER TABLE `feedback_ejercicioserie` DISABLE KEYS */;
INSERT INTO `feedback_ejercicioserie` VALUES (64,'1',NULL,NULL,NULL,NULL,NULL,NULL),(85,'1',NULL,NULL,NULL,NULL,NULL,NULL),(86,'2',NULL,NULL,NULL,NULL,NULL,NULL),(87,'3',NULL,NULL,NULL,NULL,NULL,NULL),(88,'4',NULL,NULL,NULL,NULL,NULL,NULL),(89,'1',NULL,NULL,NULL,NULL,NULL,NULL),(90,'2',NULL,NULL,NULL,NULL,NULL,NULL),(91,'3',NULL,NULL,NULL,NULL,NULL,NULL),(92,'4',NULL,NULL,NULL,NULL,NULL,NULL),(93,'1','8','45',NULL,NULL,NULL,1),(94,'2','8','40',NULL,NULL,NULL,1),(95,'3',NULL,NULL,NULL,NULL,NULL,1),(96,'1',NULL,NULL,'30',NULL,NULL,3),(97,'2',NULL,NULL,'25',NULL,NULL,3),(98,'3',NULL,NULL,NULL,NULL,NULL,3),(99,'4',NULL,NULL,NULL,NULL,NULL,3),(100,'5',NULL,NULL,NULL,NULL,NULL,3);
/*!40000 ALTER TABLE `feedback_ejercicioserie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `implementacion_ejercicio-rutina`
--

DROP TABLE IF EXISTS `implementacion_ejercicio-rutina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `implementacion_ejercicio-rutina` (
  `idimplementacion_ejercicio-rutina` int NOT NULL AUTO_INCREMENT,
  `ejercicio` int DEFAULT NULL,
  `rutina` int DEFAULT NULL,
  `sets` varchar(45) DEFAULT NULL,
  `repeticiones` varchar(45) DEFAULT NULL,
  `peso` varchar(45) DEFAULT NULL,
  `tiempo` varchar(45) DEFAULT NULL,
  `kilocalorias` varchar(45) DEFAULT NULL,
  `metros` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idimplementacion_ejercicio-rutina`),
  KEY `implementacion-ejercicio_idx` (`ejercicio`),
  KEY `implementacion-rutina_fk_idx` (`rutina`),
  CONSTRAINT `implementacion-ejercicio_fk` FOREIGN KEY (`ejercicio`) REFERENCES `ejercicio` (`idejercicio`),
  CONSTRAINT `implementacion-rutina_fk` FOREIGN KEY (`rutina`) REFERENCES `rutina` (`idrutina`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `implementacion_ejercicio-rutina`
--

LOCK TABLES `implementacion_ejercicio-rutina` WRITE;
/*!40000 ALTER TABLE `implementacion_ejercicio-rutina` DISABLE KEYS */;
INSERT INTO `implementacion_ejercicio-rutina` VALUES (2,2,1,'4','8','45','','',''),(4,1,1,'4','8','60','','',''),(5,4,1,'5','','','30','',''),(6,5,1,'','','','900','','');
/*!40000 ALTER TABLE `implementacion_ejercicio-rutina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingrediente`
--

DROP TABLE IF EXISTS `ingrediente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingrediente` (
  `idingrediente` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `kilocalorias` varchar(30) DEFAULT NULL,
  `proteinas` varchar(30) DEFAULT NULL,
  `grasas` varchar(30) DEFAULT NULL,
  `azucares` varchar(30) DEFAULT NULL,
  `hidratos_de_carbono` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`idingrediente`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingrediente`
--

LOCK TABLES `ingrediente` WRITE;
/*!40000 ALTER TABLE `ingrediente` DISABLE KEYS */;
INSERT INTO `ingrediente` VALUES (1,'Pasta','5','5','5','5','5'),(2,'Tomate','1','1','1','1','1'),(3,'Nata','2','2','2','2','2'),(8,'Pan','30','5','5','5','50'),(9,'Bacon','50','30','30','1','5'),(10,'Jamón','100','10','10','10','10'),(11,'Lechuga','30','3','0','1','10'),(12,'Tomate','10','5','2','2','10'),(13,'Mayonesa','50','30','30','10','10'),(14,'Aceite','30','10','20','5','5'),(15,'Pimiento','50','6','10','10','15'),(16,'Lomo','50','50','30','1','10');
/*!40000 ALTER TABLE `ingrediente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plato`
--

DROP TABLE IF EXISTS `plato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plato` (
  `idplato` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `tiempo_de_preparacion` varchar(30) DEFAULT NULL,
  `receta` varchar(1500) DEFAULT NULL,
  `enlace_receta` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idplato`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plato`
--

LOCK TABLES `plato` WRITE;
/*!40000 ALTER TABLE `plato` DISABLE KEYS */;
INSERT INTO `plato` VALUES (9,'Pasta tomate','10','Cocer pasta y añadir tomate','https://www.youtube.com/watch?v=jNQXAC9IVRw'),(11,'Pasta con nata y bacon','10','Pasta y nata y bacon','https://www.youtube.com/watch?v=jNQXAC9IVRw'),(12,'Bocadillo de jamón','5','Cortas el pan y metes jamón','https://www.youtube.com/watch?v=vAOzFvVz718'),(15,'Pasta con nata y bacon','20','Hervir la pasta y añadir nata junto con el bacon calentado','https://www.youtube.com/watch?v=SUjTxy5_NNY'),(16,'Ensalada pobre','10','Lavar la lechuga, cortar el tomate, mezclar todo con un chorrito de aceite','https://www.youtube.com/watch?v=UYyHci_ZzxY'),(17,'Serranito','12','Cocinar el lomo y los pimientos. Abrir el pan. Montar el bocadillo con cariño','https://www.youtube.com/watch?v=z5zMs9WztJc');
/*!40000 ALTER TABLE `plato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro`
--

DROP TABLE IF EXISTS `registro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro` (
  `idregistro` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(70) DEFAULT NULL,
  `nombre` varchar(70) DEFAULT NULL,
  `apellidos` varchar(150) DEFAULT NULL,
  `telefono` int DEFAULT NULL,
  `fecha_nacimiento` datetime DEFAULT NULL,
  `rol` int DEFAULT NULL,
  PRIMARY KEY (`idregistro`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro`
--

LOCK TABLES `registro` WRITE;
/*!40000 ALTER TABLE `registro` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutina`
--

DROP TABLE IF EXISTS `rutina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rutina` (
  `idrutina` int NOT NULL AUTO_INCREMENT,
  `fecha_creacion` datetime DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `entrenador` int DEFAULT NULL,
  PRIMARY KEY (`idrutina`),
  KEY `rutina-entrenador_fk_idx` (`entrenador`),
  CONSTRAINT `rutina-entrenador_fk` FOREIGN KEY (`entrenador`) REFERENCES `user` (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutina`
--

LOCK TABLES `rutina` WRITE;
/*!40000 ALTER TABLE `rutina` DISABLE KEYS */;
INSERT INTO `rutina` VALUES (1,'2000-01-01 00:00:00','rutina1',3),(2,'2000-01-01 00:00:00','rutina2',4),(3,'2024-05-20 11:39:27',NULL,4);
/*!40000 ALTER TABLE `rutina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_cantidad`
--

DROP TABLE IF EXISTS `tipo_cantidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_cantidad` (
  `idtipo_cantidad` int NOT NULL AUTO_INCREMENT,
  `tipo_cantidad_medida` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idtipo_cantidad`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_cantidad`
--

LOCK TABLES `tipo_cantidad` WRITE;
/*!40000 ALTER TABLE `tipo_cantidad` DISABLE KEYS */;
INSERT INTO `tipo_cantidad` VALUES (1,'Cantidad sin asignar'),(2,'gramos'),(3,'mililitros'),(4,'cucharada(s)');
/*!40000 ALTER TABLE `tipo_cantidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_comida`
--

DROP TABLE IF EXISTS `tipo_comida`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_comida` (
  `idtipo_comida` int NOT NULL AUTO_INCREMENT,
  `comida_del_dia` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idtipo_comida`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_comida`
--

LOCK TABLES `tipo_comida` WRITE;
/*!40000 ALTER TABLE `tipo_comida` DISABLE KEYS */;
INSERT INTO `tipo_comida` VALUES (1,'Desayuno'),(2,'Medio dia'),(3,'Almuerzo'),(4,'Merienda'),(5,'Cena');
/*!40000 ALTER TABLE `tipo_comida` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_ejercicio`
--

DROP TABLE IF EXISTS `tipo_ejercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_ejercicio` (
  `idtipo_ejercicio` int NOT NULL AUTO_INCREMENT,
  `tipo_de_ejercicio` varchar(45) DEFAULT NULL COMMENT 'guarda si es cardio, fuerza, etc...',
  PRIMARY KEY (`idtipo_ejercicio`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_ejercicio`
--

LOCK TABLES `tipo_ejercicio` WRITE;
/*!40000 ALTER TABLE `tipo_ejercicio` DISABLE KEYS */;
INSERT INTO `tipo_ejercicio` VALUES (1,'BodyBuilding'),(2,'Fuerza/Resistencia'),(3,'Capacidad aeróbica'),(4,'Velocidad/Potencia'),(5,'Estabilidad'),(6,'Movilidad');
/*!40000 ALTER TABLE `tipo_ejercicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `iduser` int NOT NULL AUTO_INCREMENT,
  `username` varchar(40) DEFAULT NULL,
  `password` varchar(70) DEFAULT NULL,
  `nombre` varchar(70) DEFAULT NULL,
  `apellidos` varchar(150) DEFAULT NULL,
  `telefono` int DEFAULT NULL,
  `peso` int DEFAULT NULL,
  `altura` int DEFAULT NULL,
  `fecha_nacimiento` datetime DEFAULT NULL,
  `descripcion_personal` varchar(750) DEFAULT NULL,
  `rol` int DEFAULT NULL,
  PRIMARY KEY (`iduser`),
  KEY `user-rol_fk_idx` (`rol`),
  CONSTRAINT `user-rol_fk` FOREIGN KEY (`rol`) REFERENCES `user_rol` (`iduser_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'cliente_test','cliente','cliente','clientado',999,80,180,'2000-04-20 00:00:00','soy una cuenta de testeo de cliente',2),(2,'admin_test','admin','admin','administrador',111,100,200,'2000-01-01 00:00:00','soy una cuenta de testeo de administrador',1),(3,'bodybuilder_test','bodybuilder','bodybuilder','bodybuilding',222,120,150,'2003-03-02 00:00:00','soy una cuenta de testeo',3),(4,'crosstrainer_test','crosstrainer','crosstrainer','crosstraining',333,72,185,'1990-05-27 00:00:00','soy una cuenta de testeo',4),(5,'dietista_test','dietista','dietista','dieta dietada',444,90,175,'1995-10-10 00:00:00','soy una cuenta de testeo',5),(6,'jaime_dietista','jaime','Jaime Ezequiel','Rodriguez Rodriguez',666555444,75,187,'2003-10-03 00:00:00','Delgado y sano',5),(7,'dani_cliente','dani','Daniel','El cliente',777888999,NULL,NULL,'2003-07-19 00:00:00',NULL,2),(8,'ana_cliente','ana','Ana','La clienta',111222333,NULL,NULL,'2003-09-17 00:00:00',NULL,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_rol`
--

DROP TABLE IF EXISTS `user_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_rol` (
  `iduser_rol` int NOT NULL AUTO_INCREMENT,
  `rol_usuario` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`iduser_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_rol`
--

LOCK TABLES `user_rol` WRITE;
/*!40000 ALTER TABLE `user_rol` DISABLE KEYS */;
INSERT INTO `user_rol` VALUES (1,'admin'),(2,'cliente'),(3,'bodybuilder'),(4,'crosstrainer'),(5,'dietista');
/*!40000 ALTER TABLE `user_rol` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-23 12:36:53
