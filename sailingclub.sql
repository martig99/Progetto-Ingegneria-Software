-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 27, 2022 alle 11:26
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
(2, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `members`
--

CREATE TABLE IF NOT EXISTS `members` (
  `IdMember` int(11) NOT NULL,
  `MembershipFee` float NOT NULL,
  PRIMARY KEY (`IdMember`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `members`
--

INSERT INTO `members` (`IdMember`, `MembershipFee`) VALUES
(1, 100);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`IdUser`, `FiscalCode`, `FirstName`, `LastName`, `Email`, `Password`) VALUES
(1, 'RSSLRI95A41A944A', 'Ilaria', 'Rossi', 'ilaria.rossi@gmail.com', 'aaaaaaaaa'),
(2, 'MRCBCH92D07H223R', 'Marco', 'Bianchi', 'marco.bianchi@gmail.com', '12345');

--
-- Limiti per le tabelle scaricate
--

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
