-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 26, 2022 alle 15:54
-- Versione del server: 10.4.21-MariaDB
-- Versione PHP: 7.3.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sailingclub`
--
CREATE DATABASE IF NOT EXISTS `sailingclub` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `sailingclub`;

-- --------------------------------------------------------

--
-- Struttura della tabella `boats`
--

DROP TABLE IF EXISTS `boats`;
CREATE TABLE IF NOT EXISTS `boats` (
  `IdBoat` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Length` int(11) NOT NULL,
  `Owner` int(11) NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdBoat`),
  KEY `boats_ibfk_1` (`Owner`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `boats`
--

TRUNCATE TABLE `boats`;
--
-- Dump dei dati per la tabella `boats`
--

INSERT INTO `boats` (`IdBoat`, `Name`, `Length`, `Owner`, `StatusCode`) VALUES
(1, 'Classica', 7, 1, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `employees`
--

DROP TABLE IF EXISTS `employees`;
CREATE TABLE IF NOT EXISTS `employees` (
  `IdEmployee` int(11) NOT NULL,
  `Administrator` tinyint(1) NOT NULL,
  PRIMARY KEY (`IdEmployee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `employees`
--

TRUNCATE TABLE `employees`;
--
-- Dump dei dati per la tabella `employees`
--

INSERT INTO `employees` (`IdEmployee`, `Administrator`) VALUES
(2, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `fees`
--

DROP TABLE IF EXISTS `fees`;
CREATE TABLE IF NOT EXISTS `fees` (
  `IdFee` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Amount` float DEFAULT NULL,
  `ValidityPeriod` int(11) DEFAULT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdFee`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `fees`
--

TRUNCATE TABLE `fees`;
--
-- Dump dei dati per la tabella `fees`
--

INSERT INTO `fees` (`IdFee`, `Type`, `Amount`, `ValidityPeriod`, `StatusCode`) VALUES
(1, 'MEMBERSHIP', 20, 365, 9),
(2, 'STORAGE', 10, 365, 0),
(3, 'RACE_REGISTRATION', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `members`
--

DROP TABLE IF EXISTS `members`;
CREATE TABLE IF NOT EXISTS `members` (
  `IdMember` int(11) NOT NULL,
  `FiscalCode` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdMember`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `members`
--

TRUNCATE TABLE `members`;
--
-- Dump dei dati per la tabella `members`
--

INSERT INTO `members` (`IdMember`, `FiscalCode`, `Address`) VALUES
(1, 'RSSLRI95A41A944A', 'Via Roma 1, Reggio Emilia'),
(3, 'CRVCST00S29F463B', 'Via Alcide de Gasperi, Reggio Emilia');

-- --------------------------------------------------------

--
-- Struttura della tabella `notifications`
--

DROP TABLE IF EXISTS `notifications`;
CREATE TABLE IF NOT EXISTS `notifications` (
  `IdNotification` int(11) NOT NULL AUTO_INCREMENT,
  `Member` int(11) NOT NULL,
  `Boat` int(11) DEFAULT NULL,
  `Fee` int(11) NOT NULL,
  `ReadStatus` tinyint(1) NOT NULL,
  PRIMARY KEY (`IdNotification`),
  KEY `notifications_ibfk_1` (`Boat`),
  KEY `notifications_ibfk_2` (`Member`),
  KEY `notifications_ibfk_3` (`Fee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `notifications`
--

TRUNCATE TABLE `notifications`;
-- --------------------------------------------------------

--
-- Struttura della tabella `payments`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `payments`
--

TRUNCATE TABLE `payments`;
-- --------------------------------------------------------

--
-- Struttura della tabella `paymentservices`
--

DROP TABLE IF EXISTS `paymentservices`;
CREATE TABLE IF NOT EXISTS `paymentservices` (
  `IdPaymentService` int(11) NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdPaymentService`) USING BTREE,
  UNIQUE KEY `Description` (`Description`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `paymentservices`
--

TRUNCATE TABLE `paymentservices`;
--
-- Dump dei dati per la tabella `paymentservices`
--

INSERT INTO `paymentservices` (`IdPaymentService`, `Description`) VALUES
(1, 'Credit Card'),
(2, 'Transfer Receipt');

-- --------------------------------------------------------

--
-- Struttura della tabella `raceregistrations`
--

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

--
-- Svuota la tabella prima dell'inserimento `raceregistrations`
--

TRUNCATE TABLE `raceregistrations`;
--
-- Dump dei dati per la tabella `raceregistrations`
--

INSERT INTO `raceregistrations` (`IdRegistration`, `Date`, `Race`, `Boat`, `StatusCode`) VALUES
(1, '2022-03-26', 1, 1, 9);

-- --------------------------------------------------------

--
-- Struttura della tabella `races`
--

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `races`
--

TRUNCATE TABLE `races`;
--
-- Dump dei dati per la tabella `races`
--

INSERT INTO `races` (`IdRace`, `Name`, `Place`, `Date`, `BoatsNumber`, `RegistrationFee`, `EndDateRegistration`, `StatusCode`) VALUES
(1, 'Nettuno', 'Rimini', '2022-03-20', 7, 15, '2022-03-15', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `users`
--

TRUNCATE TABLE `users`;
--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`IdUser`, `FirstName`, `LastName`, `Email`, `Password`, `StatusCode`) VALUES
(1, 'Ilaria', 'Rossi', 'ilaria.rossi@gmail.com', 'aaaaaaaaa', 0),
(2, 'Marco', 'Bianchi', 'marco.bianchi@gmail.com', '12345', 0),
(3, 'Marco', 'Rossi', 'marco.rossi@gmail.com', '1234', 0);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `boats`
--
ALTER TABLE `boats`
  ADD CONSTRAINT `boats_ibfk_1` FOREIGN KEY (`Owner`) REFERENCES `members` (`IdMember`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`IdEmployee`) REFERENCES `users` (`IdUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `members`
--
ALTER TABLE `members`
  ADD CONSTRAINT `members_ibfk_1` FOREIGN KEY (`IdMember`) REFERENCES `users` (`IdUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`Boat`) REFERENCES `boats` (`IdBoat`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `notifications_ibfk_2` FOREIGN KEY (`Member`) REFERENCES `members` (`IdMember`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `notifications_ibfk_3` FOREIGN KEY (`Fee`) REFERENCES `fees` (`IdFee`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`Member`) REFERENCES `members` (`IdMember`),
  ADD CONSTRAINT `payments_ibfk_2` FOREIGN KEY (`Boat`) REFERENCES `boats` (`IdBoat`),
  ADD CONSTRAINT `payments_ibfk_3` FOREIGN KEY (`RaceRegistration`) REFERENCES `raceregistrations` (`IdRegistration`),
  ADD CONSTRAINT `payments_ibfk_4` FOREIGN KEY (`PaymentService`) REFERENCES `paymentservices` (`IdPaymentService`),
  ADD CONSTRAINT `payments_ibfk_5` FOREIGN KEY (`Fee`) REFERENCES `fees` (`IdFee`);

--
-- Limiti per la tabella `raceregistrations`
--
ALTER TABLE `raceregistrations`
  ADD CONSTRAINT `raceregistrations_ibfk_1` FOREIGN KEY (`Race`) REFERENCES `races` (`IdRace`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `raceregistrations_ibfk_2` FOREIGN KEY (`Boat`) REFERENCES `boats` (`IdBoat`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
