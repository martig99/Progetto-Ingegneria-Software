CREATE DATABASE IF NOT EXISTS `sailingclub` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `sailingclub`;

GRANT USAGE ON *.* TO `adminclub`@`%` IDENTIFIED BY PASSWORD '*BCEC6129C9732BDE6308BAEA150B1B53672064FE';
GRANT ALL PRIVILEGES ON `sailingclub`.* TO `adminclub`@`%`;

DROP TABLE IF EXISTS `boats`;
CREATE TABLE IF NOT EXISTS `boats` (
  `IdBoat` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Length` int(11) NOT NULL,
  `Owner` int(11) NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdBoat`),
  KEY `boats_ibfk_1` (`Owner`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

TRUNCATE TABLE `boats`;
INSERT INTO `boats` (`IdBoat`, `Name`, `Length`, `Owner`, `StatusCode`) VALUES
(1, 'Sea Ray', 10, 3, 0),
(2, 'Yacht', 15, 3, 0),
(3, 'Yacht', 17, 4, 0),
(4, 'Yacht a vela', 12, 5, 0);

DROP TABLE IF EXISTS `employees`;
CREATE TABLE IF NOT EXISTS `employees` (
  `IdEmployee` int(11) NOT NULL,
  `Administrator` tinyint(1) NOT NULL,
  PRIMARY KEY (`IdEmployee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

TRUNCATE TABLE `employees`;
INSERT INTO `employees` (`IdEmployee`, `Administrator`) VALUES
(1, 1),
(2, 0);

DROP TABLE IF EXISTS `fees`;
CREATE TABLE IF NOT EXISTS `fees` (
  `IdFee` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Amount` float DEFAULT NULL,
  `ValidityPeriod` int(11) DEFAULT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdFee`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

TRUNCATE TABLE `fees`;
INSERT INTO `fees` (`IdFee`, `Type`, `Amount`, `ValidityPeriod`, `StatusCode`) VALUES
(1, 'MEMBERSHIP', 200, 365, 0),
(2, 'STORAGE', 10, 365, 0),
(3, 'RACE_REGISTRATION', NULL, NULL, 0);

DROP TABLE IF EXISTS `members`;
CREATE TABLE IF NOT EXISTS `members` (
  `IdMember` int(11) NOT NULL,
  `FiscalCode` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdMember`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

TRUNCATE TABLE `members`;
INSERT INTO `members` (`IdMember`, `FiscalCode`, `Address`) VALUES
(3, 'RSSLRI97H52H223L', 'Via dei melograni, 11'),
(4, 'BNCMRC99S04A944V', 'Via della Repubblica, 1'),
(5, 'VRDSRA00B55F205Y', 'Via Puecher, 11');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

TRUNCATE TABLE `notifications`;
INSERT INTO `notifications` (`IdNotification`, `Member`, `Boat`, `Fee`, `StatusCode`) VALUES
(1, 4, 3, 2, 0);

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

TRUNCATE TABLE `payments`;
INSERT INTO `payments` (`IdPayment`, `Date`, `Member`, `Boat`, `RaceRegistration`, `Fee`, `ValidityStartDate`, `ValidityEndDate`, `Total`, `PaymentService`) VALUES
(1, '2022-06-08', 3, NULL, NULL, 1, '2022-06-08', '2023-06-08', 200, 2),
(2, '2022-06-08', 3, 2, NULL, 2, '2022-06-08', '2023-06-08', 150, 1),
(3, '2022-06-08', 4, NULL, NULL, 1, '2022-06-08', '2023-06-08', 200, 1),
(4, '2022-06-08', 5, NULL, NULL, 1, '2022-06-08', '2023-06-08', 200, 2),
(5, '2022-06-08', 3, 2, 1, 3, '2022-06-08', '2022-06-08', 14, 2);

DROP TABLE IF EXISTS `paymentservices`;
CREATE TABLE IF NOT EXISTS `paymentservices` (
  `IdPaymentService` int(11) NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdPaymentService`) USING BTREE,
  UNIQUE KEY `Description` (`Description`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

TRUNCATE TABLE `paymentservices`;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

TRUNCATE TABLE `raceregistrations`;
INSERT INTO `raceregistrations` (`IdRegistration`, `Date`, `Race`, `Boat`, `StatusCode`) VALUES
(1, '2022-06-08', 1, 2, 0);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

TRUNCATE TABLE `races`;
INSERT INTO `races` (`IdRace`, `Name`, `Place`, `Date`, `BoatsNumber`, `RegistrationFee`, `EndDateRegistration`, `StatusCode`) VALUES
(1, 'Caribbean 600', 'Antigua', '2022-06-30', 3, 14, '2022-06-27', 0),
(2, 'Regata Heineken', 'Monaco', '2022-07-11', 5, 25, '2022-07-10', 0);

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

TRUNCATE TABLE `users`;
INSERT INTO `users` (`IdUser`, `FirstName`, `LastName`, `Email`, `Password`, `StatusCode`) VALUES
(1, 'Martina', 'Gualtieri', 'martina.gualtieri@studenti.unipr.it', 'admin', 0),
(2, 'Cristian', 'Cervellera', 'cristian.cervellera@studenti.unipr.it', 'aaaa', 0),
(3, 'Ilaria', 'Rossi', 'ilaria.rossi@gmail.com', '12345', 0),
(4, 'Marco', 'Bianchi', 'marco.bianchi@gmail.com', '0000', 0),
(5, 'Sara', 'Verdi', 'sara.verdi@gmail.com', '1111', 0);


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