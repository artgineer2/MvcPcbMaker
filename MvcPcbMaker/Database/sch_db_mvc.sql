-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: localhost    Database: sch_db_mvc
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.16.04.2

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
-- Table structure for table `child_parts`
--

DROP TABLE IF EXISTS `child_parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `child_parts` (
  `name` varchar(20) DEFAULT NULL,
  `type` varchar(3) DEFAULT NULL,
  `component` varchar(20) DEFAULT NULL,
  `package` varchar(20) DEFAULT NULL,
  `x` float DEFAULT NULL,
  `y` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `child_parts`
--

LOCK TABLES `child_parts` WRITE;
/*!40000 ALTER TABLE `child_parts` DISABLE KEYS */;
/*!40000 ALTER TABLE `child_parts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `closest_parent`
--

DROP TABLE IF EXISTS `closest_parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `closest_parent` (
  `child_part` varchar(20) DEFAULT NULL,
  `child_package` varchar(20) DEFAULT NULL,
  `closest_parent_part` varchar(20) DEFAULT NULL,
  `closest_parent_package` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `closest_parent`
--

LOCK TABLES `closest_parent` WRITE;
/*!40000 ALTER TABLE `closest_parent` DISABLE KEYS */;
/*!40000 ALTER TABLE `closest_parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `net`
--

DROP TABLE IF EXISTS `net`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `net` (
  `name` varchar(30) DEFAULT NULL,
  `part` varchar(20) DEFAULT NULL,
  `pin_name` varchar(30) DEFAULT NULL,
  `part_pin_x` float DEFAULT NULL,
  `part_pin_y` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `net`
--

LOCK TABLES `net` WRITE;
/*!40000 ALTER TABLE `net` DISABLE KEYS */;
/*!40000 ALTER TABLE `net` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package` (
  `name` varchar(20) DEFAULT NULL,
  `height` float(7,3) DEFAULT NULL,
  `width` float(7,3) DEFAULT NULL,
  `pin_count` int(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parent_child_connections`
--

DROP TABLE IF EXISTS `parent_child_connections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parent_child_connections` (
  `parent_part` varchar(20) DEFAULT NULL,
  `parent_pin` varchar(30) DEFAULT NULL,
  `child_part` varchar(20) DEFAULT NULL,
  `child_pin` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent_child_connections`
--

LOCK TABLES `parent_child_connections` WRITE;
/*!40000 ALTER TABLE `parent_child_connections` DISABLE KEYS */;
/*!40000 ALTER TABLE `parent_child_connections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parent_parts`
--

DROP TABLE IF EXISTS `parent_parts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parent_parts` (
  `name` varchar(20) DEFAULT NULL,
  `type` varchar(3) DEFAULT NULL,
  `component` varchar(20) DEFAULT NULL,
  `package` varchar(20) DEFAULT NULL,
  `x` float DEFAULT NULL,
  `y` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent_parts`
--

LOCK TABLES `parent_parts` WRITE;
/*!40000 ALTER TABLE `parent_parts` DISABLE KEYS */;
/*!40000 ALTER TABLE `parent_parts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `part`
--

DROP TABLE IF EXISTS `part`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `part` (
  `name` varchar(20) DEFAULT NULL,
  `type` varchar(3) DEFAULT NULL,
  `component` varchar(20) DEFAULT NULL,
  `package` varchar(20) DEFAULT NULL,
  `x` float DEFAULT NULL,
  `y` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `part`
--

LOCK TABLES `part` WRITE;
/*!40000 ALTER TABLE `part` DISABLE KEYS */;
/*!40000 ALTER TABLE `part` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'sch_db_mvc'
--
/*!50003 DROP PROCEDURE IF EXISTS `closest_parent` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `closest_parent`(child_part varchar(20))
BEGIN
  DECLARE done INT DEFAULT FALSE;
  #DECLARE part CHAR(16);
  DECLARE child_x float;
  DECLARE child_y float;
  DECLARE child_package varchar(20);
  declare parent_part varchar(20);
  DECLARE parent_x float;
  DECLARE parent_y float;
  DECLARE parent_package varchar(20);
  declare parent_distance float;
  declare min_distance float;
  declare closest_parent_part varchar(20);
  DECLARE closest_parent_package varchar(20);
  
  DECLARE cur1 CURSOR FOR SELECT name,x,y,package FROM sch_db_mvc.parent_parts;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  #set part = child_part;
  set child_x = (select x from sch_db_mvc.child_parts where name=child_part);
  set child_y = (select y from sch_db_mvc.child_parts where name=child_part);
  set child_package = (select package from sch_db_mvc.child_parts where name=child_part);
	set min_distance = 1000.0;
  OPEN cur1;
  read_loop: LOOP
    FETCH cur1 INTO parent_part,parent_x,parent_y, parent_package;
    SET parent_distance = SQRT(pow((parent_x-child_x),2)+pow((parent_y-child_y),2));
    if parent_distance < min_distance then
		set min_distance = parent_distance;
        set closest_parent_part = parent_part;
        set closest_parent_package = parent_package;
	end if;
 	IF done THEN
#		insert into closest_parent_results values (part,child_x,child_y,parent_part,parent_x,parent_y,parent_distance,closest_parent,min_distance);
		insert into sch_db_mvc.closest_parent values (child_part,child_package,closest_parent_part,closest_parent_package);
        commit;
      LEAVE read_loop;
    END IF;
  END LOOP;
  CLOSE cur1;
  

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_closest_parent` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `find_closest_parent`(child_part varchar(20))
BEGIN
  DECLARE done INT DEFAULT FALSE;
  #DECLARE part CHAR(16);
  DECLARE child_x float;
  DECLARE child_y float;
  DECLARE child_package varchar(20);
  declare parent_part varchar(20);
  DECLARE parent_x float;
  DECLARE parent_y float;
  DECLARE parent_package varchar(20);
  declare parent_distance float;
  declare min_distance float;
  declare closest_parent_part varchar(20);
  DECLARE closest_parent_package varchar(20);
  
  DECLARE cur1 CURSOR FOR SELECT name,x,y,package FROM sch_db_mvc.parent_parts;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  #set part = child_part;
  set child_x = (select x from sch_db_mvc.child_parts where name=child_part);
  set child_y = (select y from sch_db_mvc.child_parts where name=child_part);
  set child_package = (select package from sch_db_mvc.child_parts where name=child_part);
	set min_distance = 1000.0;
  OPEN cur1;
  read_loop: LOOP
    FETCH cur1 INTO parent_part,parent_x,parent_y, parent_package;
    SET parent_distance = SQRT(pow((parent_x-child_x),2)+pow((parent_y-child_y),2));
    if parent_distance < min_distance then
		set min_distance = parent_distance;
        set closest_parent_part = parent_part;
        set closest_parent_package = parent_package;
	end if;
 	IF done THEN
#		insert into closest_parent_results values (part,child_x,child_y,parent_part,parent_x,parent_y,parent_distance,closest_parent,min_distance);
		insert into sch_db_mvc.closest_parent values (child_part,child_package,closest_parent_part,closest_parent_package);
        commit;
      LEAVE read_loop;
    END IF;
  END LOOP;
  CLOSE cur1;
  

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `set_parent_child_connections_table` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `set_parent_child_connections_table`()
BEGIN
truncate table parent_child_connections;
drop table if exists parent_part_nets;
drop table if exists  child_part_nets;
create temporary table parent_part_nets as select * from net where(part in (select name from parent_parts));
create temporary table child_part_nets as select * from net where(part in (select name from child_parts));

insert into parent_child_connections 
select distinct parent_part_nets.part as parent_part, 
parent_part_nets.pin_name as parent_pin, 
child_part_nets.part as child_part ,
child_part_nets.pin_name as child_pin
from parent_part_nets inner join child_part_nets
where parent_part_nets.name = child_part_nets.name 
and parent_part_nets.part != child_part_nets.part;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-26 22:59:44
