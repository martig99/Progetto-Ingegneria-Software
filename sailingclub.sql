-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 03, 2022 alle 10:15
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `boats`
--

INSERT INTO `boats` (`IdBoat`, `Name`, `Length`, `Owner`, `StatusCode`) VALUES
(1, 'Yacht', 200, 1, 0),
(2, 'Crocera', 4000, 1, 9),
(3, 'Crocera', 150, 1, 0),
(4, 'Yacht', 1000, 4, 9),
(5, 'Crocera2', 100, 4, 9),
(6, 'Battello', 1500, 4, 9),
(10, 'Battello', 200, 4, 0),
(11, 'Battello', 100, 7, 9),
(12, 'Battello', 11, 7, 0);

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
-- Dump dei dati per la tabella `employees`
--

INSERT INTO `employees` (`IdEmployee`, `Administrator`) VALUES
(2, 1),
(5, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `fees`
--

DROP TABLE IF EXISTS `fees`;
CREATE TABLE IF NOT EXISTS `fees` (
  `IdFee` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Amount` float NOT NULL,
  `ValidityPeriod` int(11) NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdFee`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `fees`
--

INSERT INTO `fees` (`IdFee`, `Type`, `Amount`, `ValidityPeriod`, `StatusCode`) VALUES
(1, 'Membership', 100, 365, 0),
(2, 'Storage', 25, 365, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `members`
--

DROP TABLE IF EXISTS `members`;
CREATE TABLE IF NOT EXISTS `members` (
  `IdMember` int(11) NOT NULL,
  `FiscalCode` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdMember`),
  UNIQUE KEY `FiscalCode` (`FiscalCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `members`
--

INSERT INTO `members` (`IdMember`, `FiscalCode`, `Address`) VALUES
(1, 'RSSLRI95A41A944A', 'Via della Pace 11, Bologna'),
(3, 'VRDSRA91T47A944V', 'Via Galilei 2, Milano'),
(4, 'NRELRA99E43H501F', 'Via Roma 11, Bologna'),
(6, 'Prova', 'Questo è un vero socio'),
(7, 'Test Registrazione', 'Via pero');

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
  `Fee` int(11) DEFAULT NULL,
  `Total` float NOT NULL,
  `PaymentService` int(11) NOT NULL,
  PRIMARY KEY (`IdPayment`),
  KEY `payments_ibfk_1` (`Member`),
  KEY `payments_ibfk_2` (`Boat`),
  KEY `payments_ibfk_3` (`RaceRegistration`),
  KEY `payments_ibfk_4` (`PaymentService`),
  KEY `payments_ibfx_5` (`Fee`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `payments`
--

INSERT INTO `payments` (`IdPayment`, `Date`, `Member`, `Boat`, `RaceRegistration`, `Fee`, `Total`, `PaymentService`) VALUES
(1, '2021-03-10', 1, NULL, NULL, 1, 100, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `paymentservices`
--

DROP TABLE IF EXISTS `paymentservices`;
CREATE TABLE IF NOT EXISTS `paymentservices` (
  `IdService` int(11) NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdService`),
  UNIQUE KEY `Description` (`Description`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `paymentservices`
--

INSERT INTO `paymentservices` (`IdService`, `Description`) VALUES
(1, 'Credit Card'),
(2, 'Transfer Receipt');

-- --------------------------------------------------------

--
-- Struttura della tabella `raceregistrations`
--

DROP TABLE IF EXISTS `raceregistrations`;
CREATE TABLE IF NOT EXISTS `raceregistrations` (
  `IdRegistration` int(11) NOT NULL AUTO_INCREMENT,
  `DateRegistration` datetime NOT NULL,
  `Race` int(11) NOT NULL,
  `Boat` int(11) NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdRegistration`),
  KEY `Race` (`Race`),
  KEY `Boat` (`Boat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `races`
--

DROP TABLE IF EXISTS `races`;
CREATE TABLE IF NOT EXISTS `races` (
  `IdRace` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Place` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `DateRace` datetime NOT NULL,
  `BoatsNumber` int(11) NOT NULL,
  `RegistrationFee` float NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`IdRace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
  UNIQUE KEY `Email` (`Email`),
  UNIQUE KEY `IdUser` (`IdUser`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`IdUser`, `FirstName`, `LastName`, `Email`, `Password`, `StatusCode`) VALUES
(1, 'Ilaria', 'Rossi', 'ilaria.rossi@gmail.com', 'aaaaaaaaa', 0),
(2, 'Marco', 'Bianchi', 'marco.bianchi@gmail.com', '12345', 0),
(3, 'Sara', 'Verdi', 'verdisara@gmail.com', '0000', 0),
(4, 'Neri', 'Laura', 'neri.laura@gmail.com', '0000', 0),
(5, 'Prova', 'Socio', 'prova@socio.it', '1234', 0),
(6, 'Socio', 'Ciao', 'socio@ciao.it', 'aaa', 0),
(7, 'Nuovo', 'Utente', 'nuovo@utente.it', 'a', 0);

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
-- Limiti per la tabella `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`Member`) REFERENCES `members` (`IdMember`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `payments_ibfk_2` FOREIGN KEY (`Boat`) REFERENCES `boats` (`IdBoat`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `payments_ibfk_3` FOREIGN KEY (`RaceRegistration`) REFERENCES `raceregistrations` (`IdRegistration`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `payments_ibfk_4` FOREIGN KEY (`PaymentService`) REFERENCES `paymentservices` (`IdService`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `payments_ibfx_5` FOREIGN KEY (`Fee`) REFERENCES `fees` (`IdFee`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `raceregistrations`
--
ALTER TABLE `raceregistrations`
  ADD CONSTRAINT `raceregistrations_ibfk_1` FOREIGN KEY (`Race`) REFERENCES `races` (`IdRace`),
  ADD CONSTRAINT `raceregistrations_ibfk_2` FOREIGN KEY (`Boat`) REFERENCES `boats` (`IdBoat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
