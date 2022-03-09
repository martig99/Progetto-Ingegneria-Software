-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 09, 2022 alle 11:08
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
CREATE TABLE `boats` (
  `IdBoat` int(11) NOT NULL,
  `Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Length` int(11) NOT NULL,
  `Owner` int(11) NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `boats`
--

INSERT INTO `boats` (`IdBoat`, `Name`, `Length`, `Owner`, `StatusCode`) VALUES
(1, 'yacht', 1, 1, 0),
(2, 'Crocera', 4000, 1, 9),
(3, 'Crocera', 10, 1, 0),
(4, 'Yacht', 1000, 4, 0),
(5, 'Crocera2', 100, 4, 0),
(6, 'Battello', 1500, 4, 0),
(10, 'Battello', 200, 4, 9),
(11, 'Battello', 100, 7, 0),
(12, 'Nave', 55, 7, 0),
(13, 'Nave', 5, 1, 0),
(14, 'Super nave', 10, 7, 0),
(15, 'Barca ilaria', 6, 1, 0),
(16, 'b', 10, 1, 9),
(17, 'Battello', 1, 6, 0),
(18, 'Yacht', 10, 8, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `employees`
--

DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
  `IdEmployee` int(11) NOT NULL,
  `Administrator` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `employees`
--

INSERT INTO `employees` (`IdEmployee`, `Administrator`) VALUES
(2, 1),
(5, 0),
(11, 0),
(12, 1),
(13, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `fees`
--

DROP TABLE IF EXISTS `fees`;
CREATE TABLE `fees` (
  `IdFee` int(11) NOT NULL,
  `Type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Amount` float DEFAULT NULL,
  `ValidityPeriod` int(11) DEFAULT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `fees`
--

INSERT INTO `fees` (`IdFee`, `Type`, `Amount`, `ValidityPeriod`, `StatusCode`) VALUES
(1, 'Membership', 100, 365, 0),
(2, 'Storage', 25, 365, 0),
(3, 'Race_Registration', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `members`
--

DROP TABLE IF EXISTS `members`;
CREATE TABLE `members` (
  `IdMember` int(11) NOT NULL,
  `FiscalCode` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Address` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `members`
--

INSERT INTO `members` (`IdMember`, `FiscalCode`, `Address`) VALUES
(1, 'RSSLRI95A41A944A', 'Via della Pace 11, Bologna'),
(3, 'VRDSRA91T47A944V', 'Via Galilei 2, Milano'),
(4, 'NRELRA99E43H501F', 'Via Roma 11, Bologna'),
(6, 'aaaa', 'Via Roma 6, Padova'),
(7, 'A', 'Via pero'),
(8, 'abc', 'c'),
(9, 'ciao', 'a'),
(10, 'Club member', 'Via dei pini 11');

-- --------------------------------------------------------

--
-- Struttura della tabella `payments`
--

DROP TABLE IF EXISTS `payments`;
CREATE TABLE `payments` (
  `IdPayment` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Member` int(11) NOT NULL,
  `Boat` int(11) DEFAULT NULL,
  `RaceRegistration` int(11) DEFAULT NULL,
  `Fee` int(11) NOT NULL,
  `ValidityStartDate` date NOT NULL,
  `ValidityEndDate` date NOT NULL,
  `Total` float NOT NULL,
  `PaymentService` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `payments`
--

INSERT INTO `payments` (`IdPayment`, `Date`, `Member`, `Boat`, `RaceRegistration`, `Fee`, `ValidityStartDate`, `ValidityEndDate`, `Total`, `PaymentService`) VALUES
(1, '2022-03-08', 1, NULL, 1, 3, '2022-03-08', '2022-03-08', 6, 2),
(2, '2022-03-08', 4, NULL, 2, 3, '2022-03-08', '2022-03-08', 6, 2),
(3, '2022-03-09', 1, NULL, NULL, 1, '2022-03-09', '2023-03-09', 100, 2),
(4, '2022-03-09', 6, NULL, 3, 3, '2022-03-09', '2022-03-09', 6, 2),
(5, '2022-03-09', 6, NULL, 4, 3, '2022-03-09', '2022-03-09', 12, 2),
(6, '2022-03-09', 1, NULL, 5, 3, '2022-03-09', '2022-03-09', 12, 2),
(7, '2022-03-09', 1, NULL, 6, 3, '2022-03-09', '2022-03-09', 50, 2),
(8, '2022-03-09', 4, NULL, 7, 3, '2022-03-09', '2022-03-09', 50, 2),
(9, '2022-03-09', 6, NULL, 8, 3, '2022-03-09', '2022-03-09', 50, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `paymentservices`
--

DROP TABLE IF EXISTS `paymentservices`;
CREATE TABLE `paymentservices` (
  `IdPaymentService` int(11) NOT NULL,
  `Description` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
CREATE TABLE `raceregistrations` (
  `IdRegistration` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Race` int(11) NOT NULL,
  `Boat` int(11) NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `raceregistrations`
--

INSERT INTO `raceregistrations` (`IdRegistration`, `Date`, `Race`, `Boat`, `StatusCode`) VALUES
(1, '2022-03-08', 2, 15, 0),
(2, '2022-03-08', 2, 6, 0),
(3, '2022-03-09', 2, 17, 0),
(4, '2022-03-09', 4, 17, 0),
(5, '2022-03-09', 4, 13, 0),
(6, '2022-03-09', 3, 13, 0),
(7, '2022-03-09', 3, 6, 0),
(8, '2022-03-09', 3, 17, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `races`
--

DROP TABLE IF EXISTS `races`;
CREATE TABLE `races` (
  `IdRace` int(11) NOT NULL,
  `Name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Place` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `Date` date NOT NULL,
  `BoatsNumber` int(11) NOT NULL,
  `RegistrationFee` float NOT NULL,
  `EndDateRegistration` date NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `races`
--

INSERT INTO `races` (`IdRace`, `Name`, `Place`, `Date`, `BoatsNumber`, `RegistrationFee`, `EndDateRegistration`, `StatusCode`) VALUES
(1, 'Gara 1', 'Roma', '2022-03-08', 3, 10, '2022-03-07', 0),
(2, 'Gara 2', 'Milano', '2022-03-10', 5, 6, '2022-03-09', 0),
(3, 'Gara 3', 'Torino', '2022-03-18', 3, 50, '2022-03-17', 0),
(4, 'A', 'Genova', '2022-03-12', 2, 12, '2022-03-11', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `IdUser` int(11) NOT NULL,
  `FirstName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `LastName` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`IdUser`, `FirstName`, `LastName`, `Email`, `Password`, `StatusCode`) VALUES
(1, 'Ilaria', 'Rossi', 'ilaria.rossi@gmail.com', 'aaaaaaaaa', 0),
(2, 'Marco', 'Bianchi', 'marco.bianchi@gmail.com', '12345', 0),
(3, 'Sara', 'Verdi', 'sara.verdi@gmail.com', '0000', 0),
(4, 'Neri', 'Laura', 'neri.laura@gmail.com', '0000', 0),
(5, 'A', 'Impiegato', 'prova@impiegato.it', '1234', 9),
(6, 'Socio', 'Ciao', 'socio@ciao.it', 'aaa', 0),
(7, 'Pippo', 'Utente', 'nuovo@utente.it', 'a', 9),
(8, 'a', 'b', 'aaa@bbb.it', '1', 9),
(9, 'a', 'b', 'ciao@ciao.it', 'd', 0),
(10, 'Club', 'Member', 'club@member.it', 'a', 0),
(11, 'Mario', 'Verdi', 'mario.verdi@gmail.com', '0000', 0),
(12, 'Nuovo', 'Admin', 'nuovo@admin.it', 'a', 0),
(13, 'Nuovo', 'Impiegato', 'nuovo@impiegato.it', 'a', 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `boats`
--
ALTER TABLE `boats`
  ADD PRIMARY KEY (`IdBoat`),
  ADD KEY `boats_ibfk_1` (`Owner`);

--
-- Indici per le tabelle `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`IdEmployee`);

--
-- Indici per le tabelle `fees`
--
ALTER TABLE `fees`
  ADD PRIMARY KEY (`IdFee`);

--
-- Indici per le tabelle `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`IdMember`),
  ADD UNIQUE KEY `FiscalCode` (`FiscalCode`);

--
-- Indici per le tabelle `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`IdPayment`),
  ADD KEY `Member` (`Member`),
  ADD KEY `Boat` (`Boat`),
  ADD KEY `RaceRegistration` (`RaceRegistration`),
  ADD KEY `PaymentService` (`PaymentService`),
  ADD KEY `Fee` (`Fee`);

--
-- Indici per le tabelle `paymentservices`
--
ALTER TABLE `paymentservices`
  ADD PRIMARY KEY (`IdPaymentService`) USING BTREE,
  ADD UNIQUE KEY `Description` (`Description`);

--
-- Indici per le tabelle `raceregistrations`
--
ALTER TABLE `raceregistrations`
  ADD PRIMARY KEY (`IdRegistration`),
  ADD KEY `raceregistrations_ibfk_1` (`Race`),
  ADD KEY `raceregistrations_ibfk_2` (`Boat`);

--
-- Indici per le tabelle `races`
--
ALTER TABLE `races`
  ADD PRIMARY KEY (`IdRace`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`IdUser`),
  ADD UNIQUE KEY `Email` (`Email`),
  ADD UNIQUE KEY `IdUser` (`IdUser`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `boats`
--
ALTER TABLE `boats`
  MODIFY `IdBoat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT per la tabella `fees`
--
ALTER TABLE `fees`
  MODIFY `IdFee` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `payments`
--
ALTER TABLE `payments`
  MODIFY `IdPayment` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la tabella `paymentservices`
--
ALTER TABLE `paymentservices`
  MODIFY `IdPaymentService` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `raceregistrations`
--
ALTER TABLE `raceregistrations`
  MODIFY `IdRegistration` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `races`
--
ALTER TABLE `races`
  MODIFY `IdRace` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `IdUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

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
