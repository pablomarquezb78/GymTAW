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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignacion_cliente_dietista`
--

LOCK TABLES `asignacion_cliente_dietista` WRITE;
/*!40000 ALTER TABLE `asignacion_cliente_dietista` DISABLE KEYS */;
INSERT INTO `asignacion_cliente_dietista` VALUES (1,1,5);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignacion_cliente_entrenador`
--

LOCK TABLES `asignacion_cliente_entrenador` WRITE;
/*!40000 ALTER TABLE `asignacion_cliente_entrenador` DISABLE KEYS */;
INSERT INTO `asignacion_cliente_entrenador` VALUES (1,1,3);
/*!40000 ALTER TABLE `asignacion_cliente_entrenador` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cantidad_ingrediente-plato-comida`
--

LOCK TABLES `cantidad_ingrediente-plato-comida` WRITE;
/*!40000 ALTER TABLE `cantidad_ingrediente-plato-comida` DISABLE KEYS */;
INSERT INTO `cantidad_ingrediente-plato-comida` VALUES (13,100,1,0,1,1,1),(14,20,1,0,2,1,1),(15,20,1,0,3,2,1),(16,100,1,0,1,2,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comida`
--

LOCK TABLES `comida` WRITE;
/*!40000 ALTER TABLE `comida` DISABLE KEYS */;
INSERT INTO `comida` VALUES (1,1,1,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dia_dieta`
--

LOCK TABLES `dia_dieta` WRITE;
/*!40000 ALTER TABLE `dia_dieta` DISABLE KEYS */;
INSERT INTO `dia_dieta` VALUES (1,'2000-01-01 00:00:00','Este dia de dieta esta hecho para testear',1,5);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dia_entrenamiento`
--

LOCK TABLES `dia_entrenamiento` WRITE;
/*!40000 ALTER TABLE `dia_entrenamiento` DISABLE KEYS */;
INSERT INTO `dia_entrenamiento` VALUES (1,'2000-01-01 00:00:00',NULL,1,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejercicio`
--

LOCK TABLES `ejercicio` WRITE;
/*!40000 ALTER TABLE `ejercicio` DISABLE KEYS */;
INSERT INTO `ejercicio` VALUES (1,'Press de Banca',1,'Ejercicio orientado a el pectoral mayor','https://videosample.com'),(2,'Press Inclinado',1,'Ejercicio orientado a el pectoral superior','https://videosample.com');
/*!40000 ALTER TABLE `ejercicio` ENABLE KEYS */;
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
  `seguimiento_sets_done` varchar(45) DEFAULT NULL,
  `seguimiento_repeticiones_done` varchar(45) DEFAULT NULL,
  `seguimiento_peso_done` varchar(45) DEFAULT NULL,
  `seguimiento_tiempo_done` varchar(45) DEFAULT NULL,
  `seguimiento_kilocalorias_done` varchar(45) DEFAULT NULL,
  `seguimiento_metros_done` varchar(45) DEFAULT NULL,
  `realizado` tinyint DEFAULT NULL,
  PRIMARY KEY (`idimplementacion_ejercicio-rutina`),
  KEY `implementacion-ejercicio_idx` (`ejercicio`),
  KEY `implementacion-rutina_fk_idx` (`rutina`),
  CONSTRAINT `implementacion-ejercicio_fk` FOREIGN KEY (`ejercicio`) REFERENCES `ejercicio` (`idejercicio`),
  CONSTRAINT `implementacion-rutina_fk` FOREIGN KEY (`rutina`) REFERENCES `rutina` (`idrutina`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `implementacion_ejercicio-rutina`
--

LOCK TABLES `implementacion_ejercicio-rutina` WRITE;
/*!40000 ALTER TABLE `implementacion_ejercicio-rutina` DISABLE KEYS */;
INSERT INTO `implementacion_ejercicio-rutina` VALUES (1,1,1,'4','8','60',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,2,1,'4','8','45',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingrediente`
--

LOCK TABLES `ingrediente` WRITE;
/*!40000 ALTER TABLE `ingrediente` DISABLE KEYS */;
INSERT INTO `ingrediente` VALUES (1,'Pasta','5','5','5','5','5'),(2,'Tomate','1','1','1','1','1'),(3,'Nata','2','2','2','2','2');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plato`
--

LOCK TABLES `plato` WRITE;
/*!40000 ALTER TABLE `plato` DISABLE KEYS */;
INSERT INTO `plato` VALUES (1,'Pasta con tomate','15 minutos','Esto es un test de receta','https://www.youtube.com/watch?v=dQw4w9WgXcQ'),(2,'Pasta con nata','20 minutos','Esto es otra receta','https://www.youtube.com/watch?v=dQw4w9WgXcQ');
/*!40000 ALTER TABLE `plato` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutina`
--

LOCK TABLES `rutina` WRITE;
/*!40000 ALTER TABLE `rutina` DISABLE KEYS */;
INSERT INTO `rutina` VALUES (1,'2000-01-01 00:00:00','rutina1',3);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_cantidad`
--

LOCK TABLES `tipo_cantidad` WRITE;
/*!40000 ALTER TABLE `tipo_cantidad` DISABLE KEYS */;
INSERT INTO `tipo_cantidad` VALUES (1,'cantidad_implementacion');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_comida`
--

LOCK TABLES `tipo_comida` WRITE;
/*!40000 ALTER TABLE `tipo_comida` DISABLE KEYS */;
INSERT INTO `tipo_comida` VALUES (1,'tipo_implementacion');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'cliente_test','cliente','cliente','clientado',999,80,180,'2000-04-20 00:00:00','soy una cuenta de testeo de cliente',2),(2,'admin_test','admin','admin','administrador',111,100,200,'2000-01-01 00:00:00','soy una cuenta de testeo de administrador',1),(3,'bodybuilder_test','bodybuilder','bodybuilder','bodybuilding',222,120,150,'2003-03-02 00:00:00','soy una cuenta de testeo',3),(4,'crosstrainer_test','crosstrainer','crosstrainer','crosstraining',333,72,185,'1990-05-27 00:00:00','soy una cuenta de testeo',4),(5,'dietista_test','dietista','dietista','dieta',444,92,175,'1995-10-10 00:00:00','soy una cuenta de testeo',5);
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

-- Dump completed on 2024-05-04 20:25:23
