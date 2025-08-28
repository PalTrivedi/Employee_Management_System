-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 24, 2024 at 09:59 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `employee_management_system`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `increasePoints` (IN `idin` INT)   BEGIN
UPDATE EMPLOYEE SET POINTS=POINTS+1 WHERE ID=idin;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `turnInTask` (IN `idin` INT, IN `statusin` BOOLEAN)   BEGIN
UPDATE TASKS SET STATUS=statusin WHERE TID=idin;
END$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `loginEmployee` (`dt` DATE, `id` INT) RETURNS TINYINT(1)  BEGIN
DECLARE 
idout INT(11) DEFAULT 0;
SELECT ID INTO idout FROM EMPLOYEE WHERE DOB=dt;
RETURN idout;
IF id=idout THEN
	return true;
 ELSE
 	return false;
END IF;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `number` bigint(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `dept` varchar(20) NOT NULL,
  `points` double NOT NULL,
  `salary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `name`, `number`, `email`, `dob`, `dept`, `points`, `salary`) VALUES
(7, 'Raju Rastogi', 9898985656, 'rajurastogi@mail.com', '1979-01-09', 'Administration', 0, 95000);

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `tid` int(11) NOT NULL,
  `eid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `priority` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`tid`, `eid`, `name`, `priority`, `status`) VALUES
(1, 7, 'see turned in tasks', 5, 0),
(3, 7, 'checkTasks', 5, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`tid`),
  ADD KEY `f_key` (`eid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `tid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `f_key` FOREIGN KEY (`eid`) REFERENCES `employee` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
