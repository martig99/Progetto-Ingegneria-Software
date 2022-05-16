SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `sailingclub` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `sailingclub`;

DROP TABLE IF EXISTS `boats`;
CREATE TABLE IF NOT EXISTS `boats` (
  `IdBoat` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Length` int(11) NOT NULL,
  `Owner` int(11) NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdBoat`),
  KEY `boats_ibfk_1` (`Owner`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `boats` (`IdBoat`, `Name`, `Length`, `Owner`, `StatusCode`) VALUES
(1, 'Classica', 1, 1, 0),
(5, 'aaa', 10, 3, 0),
(6, 'bb', 2, 3, 9),
(7, 'aaa', 10, 1, 0),
(8, 'a', 19, 1, 0),
(9, 'bb', 12, 3, 0),
(10, 'a12', 11, 1, 9),
(11, 'b12', 112, 1, 0),
(12, 'cc', 45, 1, 0),
(13, 'zz', 1, 3, 0),
(14, 'pp', 10, 1, 9),
(15, 'a1234', 12, 1, 0),
(16, 'a12345', 12, 3, 0),
(17, 'aaaaaaa', 12, 1, 9),
(18, 'a123456', 12, 1, 9),
(19, 'Classica', 7, 1, 9),
(20, 'a503', 5, 1, 0),
(21, 'a', 12, 3, 0),
(22, 'aaaaaaaa', 40, 3, 0);

DROP TABLE IF EXISTS `employees`;
CREATE TABLE IF NOT EXISTS `employees` (
  `IdEmployee` int(11) NOT NULL,
  `Administrator` tinyint(1) NOT NULL,
  PRIMARY KEY (`IdEmployee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `employees` (`IdEmployee`, `Administrator`) VALUES
(2, 1),
(7, 0),
(8, 1),
(9, 1),
(10, 0),
(13, 0);

DROP TABLE IF EXISTS `fees`;
CREATE TABLE IF NOT EXISTS `fees` (
  `IdFee` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Amount` float DEFAULT NULL,
  `ValidityPeriod` int(11) DEFAULT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdFee`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `fees` (`IdFee`, `Type`, `Amount`, `ValidityPeriod`, `StatusCode`) VALUES
(1, 'MEMBERSHIP', 20, 365, 9),
(2, 'STORAGE', 10, 365, 0),
(3, 'RACE_REGISTRATION', NULL, NULL, 0),
(4, 'MEMBERSHIP', 15, 365, 9),
(5, 'MEMBERSHIP', 70, 365, 0);

DROP TABLE IF EXISTS `members`;
CREATE TABLE IF NOT EXISTS `members` (
  `IdMember` int(11) NOT NULL,
  `FiscalCode` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdMember`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `members` (`IdMember`, `FiscalCode`, `Address`) VALUES
(1, 'RSSLRI95A41A944A', 'Via Roma 1, Reggio Emilia'),
(3, 'CRVCST00S29F463B', 'Via Alcide de Gasperi, Reggio Emilia'),
(4, 'RSSMRC83C17F205Y', 'Via Alcide de Gasperi, Reggio Emilia'),
(5, 'RSSMRC83C17F205Y', 'Via Alcide de Gasperi, Reggio Emilia'),
(6, 'RSSMRC83C17F205Y', 'Via Alcide de Gasperi, Reggio Emilia'),
(10, 'AAABBB99T65H223B', 'strada melograno 11'),
(11, 'AAABBB99T63H223A', 'aaa'),
(12, 'AAABBB99T63H223B', 'c');

DROP TABLE IF EXISTS `notifications`;
CREATE TABLE IF NOT EXISTS `notifications` (
  `IdNotification` int(11) NOT NULL AUTO_INCREMENT,
  `Member` int(11) NOT NULL,
  `Boat` int(11) DEFAULT NULL,
  `Fee` int(11) NOT NULL,
  `StatusCode` int(1) NOT NULL,
  PRIMARY KEY (`IdNotification`),
  KEY `notifications_ibfk_1` (`Boat`),
  KEY `notifications_ibfk_2` (`Member`),
  KEY `notifications_ibfk_3` (`Fee`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `notifications` (`IdNotification`, `Member`, `Boat`, `Fee`, `StatusCode`) VALUES
(1, 1, NULL, 4, 9),
(2, 1, NULL, 4, 9),
(3, 1, NULL, 4, 9),
(4, 1, NULL, 4, 9),
(5, 3, NULL, 4, 0),
(6, 1, 1, 2, 9),
(7, 1, 7, 2, 0),
(8, 3, 13, 2, 0);

DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `IdPayment` int(11) NOT NULL AUTO_INCREMENT,
  `Date` date NOT NULL,
  `Member` int(11) NOT NULL,
  `Boat` int(11) DEFAULT NULL,
  `RaceRegistration` int(11) DEFAULT NULL,
  `Fee` int(11) NOT NULL,
  `ValidityStartDate` date NOT NULL,
  `ValidityEndDate` date NOT NULL,
  `Total` float NOT NULL,
  `PaymentService` int(11) NOT NULL,
  PRIMARY KEY (`IdPayment`),
  KEY `Member` (`Member`),
  KEY `Boat` (`Boat`),
  KEY `RaceRegistration` (`RaceRegistration`),
  KEY `PaymentService` (`PaymentService`),
  KEY `Fee` (`Fee`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `payments` (`IdPayment`, `Date`, `Member`, `Boat`, `RaceRegistration`, `Fee`, `ValidityStartDate`, `ValidityEndDate`, `Total`, `PaymentService`) VALUES
(1, '2022-03-27', 1, NULL, NULL, 4, '2022-03-27', '2023-03-27', 15, 2),
(3, '2022-04-21', 3, NULL, NULL, 4, '2022-04-21', '2023-04-21', 15, 1),
(4, '2022-04-21', 1, 1, NULL, 2, '2022-04-21', '2023-04-21', 70, 1),
(5, '2022-04-21', 1, 1, 4, 3, '2022-04-21', '2022-04-21', 12, 1),
(6, '2022-04-21', 1, 7, NULL, 2, '2022-04-21', '2023-04-21', 100, 1),
(7, '2022-04-21', 1, 7, 4, 3, '2022-04-21', '2022-04-21', -12, 1),
(8, '2022-04-21', 1, 1, 5, 3, '2022-04-21', '2022-04-21', 12, 1),
(9, '2022-04-21', 1, 7, 5, 3, '2022-04-21', '2022-04-21', -12, 1),
(10, '2022-04-29', 1, NULL, NULL, 4, '2023-03-28', '2024-03-27', 15, 2),
(11, '2022-04-29', 1, NULL, NULL, 4, '2024-03-28', '2025-03-28', 15, 1),
(12, '2022-04-29', 1, 10, NULL, 2, '2022-04-29', '2023-04-29', 110, 2),
(13, '2022-05-02', 1, 1, 6, 3, '2022-05-02', '2022-05-02', 23, 1),
(14, '2022-05-04', 1, NULL, NULL, 4, '2025-03-29', '2026-03-29', 15, 1),
(15, '2022-05-13', 1, 1, 6, 3, '2022-05-13', '2022-05-13', -23, 1),
(16, '2022-05-13', 1, 10, 7, 3, '2022-05-13', '2022-05-13', 23, 1),
(17, '2022-05-13', 1, 10, 7, 3, '2022-05-13', '2022-05-13', -23, 1),
(18, '2022-05-13', 1, 11, NULL, 2, '2022-05-13', '2023-05-13', 1120, 2),
(19, '2022-05-13', 1, 1, NULL, 2, '2023-04-22', '2024-04-21', 10, 1),
(20, '2022-05-13', 1, 11, 8, 3, '2022-05-13', '2022-05-13', 23, 1),
(21, '2022-05-13', 1, 1, 8, 3, '2022-05-13', '2022-05-13', -23, 1),
(22, '2022-05-13', 1, 7, 9, 3, '2022-05-13', '2022-05-13', 100, 2),
(23, '2022-05-13', 1, 7, 9, 3, '2022-05-13', '2022-05-13', -100, 2),
(24, '2022-05-13', 1, 1, 10, 3, '2022-05-13', '2022-05-13', 100, 2),
(25, '2022-05-13', 3, 16, NULL, 2, '2022-05-13', '2023-05-13', 120, 2),
(26, '2022-05-13', 3, 16, 11, 3, '2022-05-13', '2022-05-13', 100, 1),
(27, '2022-05-13', 3, 9, NULL, 2, '2022-05-13', '2023-05-13', 120, 2),
(28, '2022-05-13', 3, 16, 11, 3, '2022-05-13', '2022-05-13', -100, 1),
(29, '2022-05-13', 3, 9, 12, 3, '2022-05-13', '2022-05-13', 100, 2),
(30, '2022-05-13', 10, NULL, NULL, 5, '2022-05-13', '2023-05-13', 70, 1);

DROP TABLE IF EXISTS `paymentservices`;
CREATE TABLE IF NOT EXISTS `paymentservices` (
  `IdPaymentService` int(11) NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdPaymentService`) USING BTREE,
  UNIQUE KEY `Description` (`Description`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `paymentservices` (`IdPaymentService`, `Description`) VALUES
(1, 'Credit Card'),
(2, 'Transfer Receipt');

DROP TABLE IF EXISTS `raceregistrations`;
CREATE TABLE IF NOT EXISTS `raceregistrations` (
  `IdRegistration` int(11) NOT NULL AUTO_INCREMENT,
  `Date` date NOT NULL,
  `Race` int(11) NOT NULL,
  `Boat` int(11) NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdRegistration`),
  KEY `raceregistrations_ibfk_1` (`Race`),
  KEY `raceregistrations_ibfk_2` (`Boat`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `raceregistrations` (`IdRegistration`, `Date`, `Race`, `Boat`, `StatusCode`) VALUES
(4, '2022-04-21', 4, 7, 9),
(5, '2022-04-21', 4, 7, 9),
(6, '2022-05-02', 6, 1, 9),
(7, '2022-05-13', 6, 10, 9),
(8, '2022-05-13', 6, 1, 9),
(9, '2022-05-13', 7, 7, 9),
(10, '2022-05-13', 7, 1, 0),
(11, '2022-05-13', 7, 16, 9),
(12, '2022-05-13', 7, 9, 0);

DROP TABLE IF EXISTS `races`;
CREATE TABLE IF NOT EXISTS `races` (
  `IdRace` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Place` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `Date` date NOT NULL,
  `BoatsNumber` int(11) NOT NULL,
  `RegistrationFee` float NOT NULL,
  `EndDateRegistration` date NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdRace`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `races` (`IdRace`, `Name`, `Place`, `Date`, `BoatsNumber`, `RegistrationFee`, `EndDateRegistration`, `StatusCode`) VALUES
(4, 'a', 'Rimini', '2022-04-25', 2, 12, '2022-04-23', 0),
(5, 'aaaaa', 'bbbbb', '2022-04-30', 2, 2, '2022-04-29', 9),
(6, 'a', 'b', '2022-05-21', 2, 23, '2022-05-13', 9),
(7, 'Super gara', 'Rimini', '2022-05-14', 2, 100, '2022-05-13', 0);

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `IdUser` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `LastName` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdUser`),
  UNIQUE KEY `IdUser` (`IdUser`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `users` (`IdUser`, `FirstName`, `LastName`, `Email`, `Password`, `StatusCode`) VALUES
(1, 'Ilaria', 'Rossi', 'ilaria.rossi@gmail.com', 'aaaaaaaaa', 0),
(2, 'Marco', 'Bianchi', 'marco.bianchi@gmail.com', '12345', 0),
(3, 'Marco', 'Rossi', 'marco.rossi@gmail.com', '1234', 0),
(4, 'Marco', 'Rossi', 'marco.rossi@gmail.com', '1234', 9),
(5, 'Marco', 'Rossi', 'marco.rossi@gmail.com', '1234', 9),
(6, 'Marco', 'Rossi', 'marco.rossi@gmail.com', '1234', 9),
(7, 'Francesca', 'Rossi', 'fra.rossi@gmail.com', '1111', 9),
(8, 'a', 'b', 'ciao@ciao.it', 'aaa', 0),
(9, 'aa', 'aa', 'aa@aa.it', 'aa', 9),
(10, 'a', 'b', 'ab@ab.it', 'aa', 0),
(11, 'aaa', 'aaa', 'aa@bb.it', 'aaa', 0),
(12, 'a', 'b', 'aaa@aaa.it', 'abc', 0),
(13, 'A', 'B', 'admin@aa.it', 'abab', 0);


ALTER TABLE `boats`
  ADD CONSTRAINT `boats_ibfk_1` FOREIGN KEY (`Owner`) REFERENCES `members` (`IdMember`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `employees`
  ADD CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`IdEmployee`) REFERENCES `users` (`IdUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `members`
  ADD CONSTRAINT `members_ibfk_1` FOREIGN KEY (`IdMember`) REFERENCES `users` (`IdUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`Boat`) REFERENCES `boats` (`IdBoat`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `notifications_ibfk_2` FOREIGN KEY (`Member`) REFERENCES `members` (`IdMember`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `notifications_ibfk_3` FOREIGN KEY (`Fee`) REFERENCES `fees` (`IdFee`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`Member`) REFERENCES `members` (`IdMember`),
  ADD CONSTRAINT `payments_ibfk_2` FOREIGN KEY (`Boat`) REFERENCES `boats` (`IdBoat`),
  ADD CONSTRAINT `payments_ibfk_3` FOREIGN KEY (`RaceRegistration`) REFERENCES `raceregistrations` (`IdRegistration`),
  ADD CONSTRAINT `payments_ibfk_4` FOREIGN KEY (`PaymentService`) REFERENCES `paymentservices` (`IdPaymentService`),
  ADD CONSTRAINT `payments_ibfk_5` FOREIGN KEY (`Fee`) REFERENCES `fees` (`IdFee`);

ALTER TABLE `raceregistrations`
  ADD CONSTRAINT `raceregistrations_ibfk_1` FOREIGN KEY (`Race`) REFERENCES `races` (`IdRace`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `raceregistrations_ibfk_2` FOREIGN KEY (`Boat`) REFERENCES `boats` (`IdBoat`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
