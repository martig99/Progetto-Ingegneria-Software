-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 01, 2022 alle 09:49
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

CREATE TABLE IF NOT EXISTS `boats` (
  `IdBoat` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Length` int(11) NOT NULL,
  `StorageFee` int(11) NOT NULL,
  `Owner` int(11) NOT NULL,
  PRIMARY KEY (`IdBoat`),
  KEY `Owner` (`Owner`),
  KEY `StorageFee` (`StorageFee`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `boats`
--

INSERT INTO `boats` (`IdBoat`, `Name`, `Length`, `StorageFee`, `Owner`) VALUES
(1, 'Yacht', 1020, 2, 1),
(2, 'Crocera', 4000, 2, 1),
(3, 'Crocera2', 4000, 2, 1),
(5, 'w', 2000, 2, 1),
(6, 'A', 10, 2, 3),
(8, 'Yacht', 2000, 2, 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `employees`
--

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
(7, 0),
(10, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `fees`
--

CREATE TABLE IF NOT EXISTS `fees` (
  `IdFee` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Amount` float NOT NULL,
  `ValidityPeriod` int(11) NOT NULL,
  PRIMARY KEY (`IdFee`),
  UNIQUE KEY `Type` (`Type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `fees`
--

INSERT INTO `fees` (`IdFee`, `Type`, `Amount`, `ValidityPeriod`) VALUES
(1, 'Membership', 100, 365),
(2, 'Storage', 25, 365);

-- --------------------------------------------------------

--
-- Struttura della tabella `members`
--

CREATE TABLE IF NOT EXISTS `members` (
  `IdMember` int(11) NOT NULL,
  `MembershipFee` int(11) NOT NULL,
  PRIMARY KEY (`IdMember`),
  KEY `MembershipFee` (`MembershipFee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `members`
--

INSERT INTO `members` (`IdMember`, `MembershipFee`) VALUES
(1, 1),
(3, 1),
(4, 1),
(5, 1),
(8, 1),
(9, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `paymentservices`
--

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
-- Struttura della tabella `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `IdUser` int(11) NOT NULL AUTO_INCREMENT,
  `FiscalCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `FirstName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `LastName` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`IdUser`),
  UNIQUE KEY `FiscalCode` (`FiscalCode`),
  UNIQUE KEY `Email` (`Email`),
  UNIQUE KEY `IdUser` (`IdUser`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`IdUser`, `FiscalCode`, `FirstName`, `LastName`, `Email`, `Password`) VALUES
(1, 'RSSLRI95A41A944A', 'Ilaria', 'Rossi', 'ilaria.rossi@gmail.com', 'aaaaaaaaa'),
(2, 'MRCBCH92D07H223R', 'Marco', 'Bianchi', 'marco.bianchi@gmail.com', '12345'),
(3, 'VRDSRA93M55F205N', 'Sara', 'Verdi', 'verdisara@gmail.com', '0000'),
(4, 'Prova', 'Registrazione', 'Test', 'abc@bee.it', 'b'),
(5, 'Prova2', 'Nuovo', 'utente', 'nuovo@utente.it', 'a'),
(7, 'Impiegato', 'Impiegato', 'Semplice', 'impiegato@semplice.it', '1111'),
(8, 'Nuovo Utente', 'Nuovo', 'Utente', 'nuovo2@utente.it', '2222'),
(9, 'Prova Registrazione', 'Prova', 'Registrazione', 'prova@registrazione.it', '000'),
(10, 'Nuovo Admin', 'Nuovo', 'Admin', 'admin@gmail.com', '0987');

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `boats`
--
ALTER TABLE `boats`
  ADD CONSTRAINT `boats_ibfk_1` FOREIGN KEY (`Owner`) REFERENCES `members` (`IdMember`),
  ADD CONSTRAINT `boats_ibfk_2` FOREIGN KEY (`StorageFee`) REFERENCES `fees` (`IdFee`);

--
-- Limiti per la tabella `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`IdEmployee`) REFERENCES `users` (`IdUser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `members`
--
ALTER TABLE `members`
  ADD CONSTRAINT `members_ibfk_1` FOREIGN KEY (`IdMember`) REFERENCES `users` (`IdUser`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `members_ibfk_2` FOREIGN KEY (`MembershipFee`) REFERENCES `fees` (`IdFee`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
