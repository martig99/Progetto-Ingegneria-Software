-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 06, 2022 alle 15:19
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
(1, 'Yacht1', 200, 1, 0),
(2, 'Crocera', 4000, 1, 9),
(3, 'Crocera', 10, 1, 0),
(4, 'Yacht', 1000, 4, 9),
(5, 'Crocera2', 100, 4, 9),
(6, 'Battello', 1500, 4, 0),
(10, 'Battello', 200, 4, 9),
(11, 'Battello', 100, 7, 9),
(12, 'Nave', 55, 7, 9),
(13, 'Nave', 5, 1, 0),
(14, 'Super nave', 10, 7, 0),
(15, 'Barca ilaria', 6, 1, 0),
(16, 'b', 10, 1, 9),
(17, 'Battello', 1, 6, 0);

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
(5, 0);

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
(6, 'Prova', 'Questo è un vero socio'),
(7, 'Test Registrazione', 'Via pero');

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
(1, '2022-03-04', 1, NULL, NULL, 1, '2022-03-04', '2023-03-04', 100, 2),
(2, '2022-03-05', 4, NULL, NULL, 1, '2022-03-05', '2023-03-05', 100, 2);

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
  `DateRegistration` datetime NOT NULL,
  `Race` int(11) NOT NULL,
  `Boat` int(11) NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `races`
--

DROP TABLE IF EXISTS `races`;
CREATE TABLE `races` (
  `IdRace` int(11) NOT NULL,
  `Name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Place` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `DateRace` datetime NOT NULL,
  `BoatsNumber` int(11) NOT NULL,
  `RegistrationFee` float NOT NULL,
  `StatusCode` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
(3, 'Sara', 'Verdi', 'verdisara@gmail.com', '0000', 0),
(4, 'Neri', 'Laura', 'neri.laura@gmail.com', '0000', 0),
(5, 'Prova', 'Socio', 'prova@socio.it', '1234', 0),
(6, 'Socio', 'Ciao', 'socio@ciao.it', 'aaa', 0),
(7, 'Nuovo', 'Utente', 'nuovo@utente.it', 'a', 0);

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
  ADD KEY `Race` (`Race`),
  ADD KEY `Boat` (`Boat`);

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
  MODIFY `IdBoat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT per la tabella `fees`
--
ALTER TABLE `fees`
  MODIFY `IdFee` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `payments`
--
ALTER TABLE `payments`
  MODIFY `IdPayment` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `paymentservices`
--
ALTER TABLE `paymentservices`
  MODIFY `IdPaymentService` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `raceregistrations`
--
ALTER TABLE `raceregistrations`
  MODIFY `IdRegistration` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `races`
--
ALTER TABLE `races`
  MODIFY `IdRace` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `IdUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

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
  ADD CONSTRAINT `raceregistrations_ibfk_1` FOREIGN KEY (`Race`) REFERENCES `races` (`IdRace`),
  ADD CONSTRAINT `raceregistrations_ibfk_2` FOREIGN KEY (`Boat`) REFERENCES `boats` (`IdBoat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
