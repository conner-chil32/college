-- MySQL dump 10.13  Distrib 5.7.44, for Linux (x86_64)
--
-- Host: localhost    Database: company_manager
-- ------------------------------------------------------
-- Server version	5.7.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Companies`
--

DROP TABLE IF EXISTS `Companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Companies` (
  `company_id` int(11) NOT NULL,
  `company_name` varchar(255) NOT NULL,
  `industry` varchar(100) NOT NULL,
  `website_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `UQ_name` (`company_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Companies`
--

LOCK TABLES `Companies` WRITE;
/*!40000 ALTER TABLE `Companies` DISABLE KEYS */;
INSERT INTO `Companies` VALUES (1,'Chevron U.S.A Inc.','Oil & Gas','https://www.chevron.com/'),(2,'Signal Hill Petroleum','Oil & Gas','https://www.shpi.net/'),(3,'Berry Petroleum Company LLC.','Oil & Gas','https://bry.com/'),(4,'Sentinel Peak Resources California LLC.','Oil & Gas','https://sentinelpeakresources.com/'),(5,'Grapevine Energy LLC.','Oil & Gas',NULL),(6,'California Resources Elk Hills LLC.','Oil & Gas','https://www.crc.com/about-crc/default.aspx'),(7,'Cat Canyon Resources LLC.','Oil & Gas','https://catcanyonresources.com/'),(8,'Crimson Resource Management Corp.','Oil & Gas','http://www.crimsonrm.com/'),(9,'Aera Energy LLC.','Oil & Gas','https://www.aeraenergy.com/'),(10,'CalNRG Operating LLC.','Oil & Gas','https://calnrg.com/');
/*!40000 ALTER TABLE `Companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Financials`
--

DROP TABLE IF EXISTS `Financials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Financials` (
  `revenue` decimal(20,2) NOT NULL,
  `expenses` decimal(20,2) NOT NULL,
  `company_id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `financial_id` int(11) NOT NULL,
  PRIMARY KEY (`year`,`financial_id`),
  KEY `FK_FinancialCompanies` (`company_id`),
  CONSTRAINT `FK_FinancialCompanies` FOREIGN KEY (`company_id`) REFERENCES `Companies` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Financials`
--

LOCK TABLES `Financials` WRITE;
/*!40000 ALTER TABLE `Financials` DISABLE KEYS */;
INSERT INTO `Financials` VALUES (200949000000.00,171365000000.00,1,2023,1),(19500000.00,12700000.00,2,2023,3),(120000000.00,75000000.00,3,2023,5),(345000000.00,6700000.00,4,2023,7),(675000000.00,57300000.00,5,2023,10),(690000000.00,372000000.00,6,2023,11),(3275000.00,2831000.00,7,2023,13),(20800000.00,16000000.00,8,2023,16),(2000000000.00,1540000000.00,9,2023,17),(298000000.00,234000000.00,10,2023,20),(246252000000.00,196578000000.00,1,2024,2),(26700000.00,22300000.00,2,2024,4),(250000000.00,160000000.00,3,2024,6),(742000000.00,824000000.00,4,2024,8),(2750000000.00,93400000.00,5,2024,9),(740000000.00,403000000.00,6,2024,12),(4534000.00,3929000.00,7,2024,14),(19300000.00,16300000.00,8,2024,15),(23000000.00,17000000.00,9,2024,18),(320000000.00,220000000.00,10,2024,19);
/*!40000 ALTER TABLE `Financials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MarketTrends`
--

DROP TABLE IF EXISTS `MarketTrends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MarketTrends` (
  `trend_id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `oil_price` decimal(20,2) NOT NULL,
  `geopolitical_event` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`year`,`trend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MarketTrends`
--

LOCK TABLES `MarketTrends` WRITE;
/*!40000 ALTER TABLE `MarketTrends` DISABLE KEYS */;
INSERT INTO `MarketTrends` VALUES (2,2022,101.00,NULL),(1,2023,83.00,NULL),(3,2023,83.21,NULL),(6,2023,106.45,'Severe storm that escalated into a Tsunami and damaged several oil rigs'),(4,2024,78.52,NULL),(5,2024,85.49,'Tensions between China, Isreal, Russia, and Venezuela');
/*!40000 ALTER TABLE `MarketTrends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Production`
--

DROP TABLE IF EXISTS `Production`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Production` (
  `production_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `barrel_produced` int(11) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`year`,`production_id`),
  KEY `FK_ProductionCompanies` (`company_id`),
  CONSTRAINT `FK_ProductionCompanies` FOREIGN KEY (`company_id`) REFERENCES `Companies` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Production`
--

LOCK TABLES `Production` WRITE;
/*!40000 ALTER TABLE `Production` DISABLE KEYS */;
INSERT INTO `Production` VALUES (1,1,2023,8350,'Cymric'),(6,3,2023,9320,'Midway-Sunset'),(9,2,2023,11931,'Long Beach'),(14,4,2023,10356,'Cymric'),(15,4,2023,9473,'Cymric'),(17,5,2023,10933,'Rio Viejo'),(18,5,2023,9887,'Rio Viejo'),(19,6,2023,10848,'Elk Hills'),(22,7,2023,6506,'Cat Canyon'),(24,8,2023,6728,'Midway-Sunset'),(27,9,2023,7372,'San Ardo'),(29,10,2023,6200,'Bardsdale'),(2,1,2024,11720,'San Ardo'),(3,1,2024,8921,'Midway-Sunset'),(5,3,2024,8868,'Midway-Sunset'),(8,3,2024,11498,'San-Ardo'),(10,2,2024,12861,'Long Beach'),(12,2,2024,20786,'Long Beach'),(13,4,2024,11027,'Cymric'),(20,6,2024,5953,'Elk Hills'),(21,7,2024,6419,'Cat Canyon'),(23,7,2024,6744,'Cat Canyon'),(26,8,2024,7028,'Midway-Sunset'),(28,9,2024,8587,'San Ardo'),(4,1,2025,9273,'McKittrick'),(7,3,2025,10030,'Midway-Sunset'),(11,2,2025,12857,'Long Beach'),(16,4,2025,8840,'Cymric'),(25,8,2025,6909,'Midway-Sunset'),(30,10,2025,5528,'Bardsdale');
/*!40000 ALTER TABLE `Production` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProfitPrediction`
--

DROP TABLE IF EXISTS `ProfitPrediction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProfitPrediction` (
  `prediction_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `predicted_profit` decimal(20,2) NOT NULL,
  `avg_oil_price` decimal(20,2) NOT NULL,
  `sum_barrels_produced` int(11) NOT NULL,
  PRIMARY KEY (`prediction_id`),
  KEY `FK_CompanyID` (`company_id`),
  KEY `FK_ProfitPrediction_Financials` (`year`),
  CONSTRAINT `FK_CompanyID` FOREIGN KEY (`company_id`) REFERENCES `Companies` (`company_id`),
  CONSTRAINT `FK_ProfitPrediction_Financials` FOREIGN KEY (`year`) REFERENCES `Financials` (`year`),
  CONSTRAINT `FK_ProfitPrediction_MarketTrends` FOREIGN KEY (`year`) REFERENCES `MarketTrends` (`year`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProfitPrediction`
--

LOCK TABLES `ProfitPrediction` WRITE;
/*!40000 ALTER TABLE `ProfitPrediction` DISABLE KEYS */;
INSERT INTO `ProfitPrediction` VALUES (1,1,2023,29585000000.00,90.89,8350),(2,1,2024,49676000000.00,82.01,20641),(3,2,2023,7884000.00,90.89,11931),(4,2,2024,7159000.00,82.01,33647),(5,3,2023,45847000.00,90.89,9320),(6,3,2024,91670000.00,82.01,20366),(7,4,2023,340102000.00,90.89,19829),(8,4,2024,-81096000.00,82.01,11027),(9,5,2023,619592000.00,90.89,20820),(10,5,2024,2657000000.00,82.01,0),(11,6,2023,318986000.00,90.89,10848),(12,6,2024,337488000.00,82.01,5953),(13,7,2023,1035000.00,90.89,6506),(14,7,2024,1684000.00,82.01,13163),(15,8,2023,5412000.00,90.89,6728),(16,8,2024,3576000.00,82.01,7028),(17,9,2023,460670000.00,90.89,7372),(18,9,2024,113704000.00,82.01,8587),(19,10,2023,64564000.00,90.89,6200),(20,10,2024,1000000000.00,82.01,0);
/*!40000 ALTER TABLE `ProfitPrediction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-28  8:33:44
