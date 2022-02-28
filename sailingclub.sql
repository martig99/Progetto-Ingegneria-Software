-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 28, 2022 alle 19:52
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

CREATE TABLE `boats` (
  `IdBoat` int(11) NOT NULL,
  `Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Length` int(11) NOT NULL,
  `StorageFee` int(11) NOT NULL,
  `Owner` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `boats`
--

TRUNCATE TABLE `boats`;
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

CREATE TABLE `employees` (
  `IdEmployee` int(11) NOT NULL,
  `Administrator` tinyint(1) NOT NULL
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

CREATE TABLE `fees` (
  `IdFee` int(11) NOT NULL,
  `Type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Amount` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `fees`
--

TRUNCATE TABLE `fees`;
--
-- Dump dei dati per la tabella `fees`
--

INSERT INTO `fees` (`IdFee`, `Type`, `Amount`) VALUES
(1, 'Membership', 100),
(2, 'Storage', 25);

-- --------------------------------------------------------

--
-- Struttura della tabella `members`
--

CREATE TABLE `members` (
  `IdMember` int(11) NOT NULL,
  `MembershipFee` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `members`
--

TRUNCATE TABLE `members`;
--
-- Dump dei dati per la tabella `members`
--

INSERT INTO `members` (`IdMember`, `MembershipFee`) VALUES
(1, 1),
(3, 1),
(4, 1),
(5, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `IdUser` int(11) NOT NULL,
  `FiscalCode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `FirstName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `LastName` varchar(70) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Svuota la tabella prima dell'inserimento `users`
--

TRUNCATE TABLE `users`;
--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`IdUser`, `FiscalCode`, `FirstName`, `LastName`, `Email`, `Password`) VALUES
(1, 'RSSLRI95A41A944A', 'Ilaria', 'Rossi', 'ilaria.rossi@gmail.com', 'aaaaaaaaa'),
(2, 'MRCBCH92D07H223R', 'Marco', 'Bianchi', 'marco.bianchi@gmail.com', '12345'),
(3, 'VRDSRA93M55F205N', 'Sara', 'Verdi', 'verdisara@gmail.com', '0000'),
(4, 'Prova', 'Registrazione', 'Test', 'abc@bee.it', 'b'),
(5, 'Prova2', 'Nuovo', 'utente', 'nuovo@utente.it', 'a');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `boats`
--
ALTER TABLE `boats`
  ADD PRIMARY KEY (`IdBoat`),
  ADD KEY `Owner` (`Owner`),
  ADD KEY `StorageFee` (`StorageFee`);

--
-- Indici per le tabelle `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`IdEmployee`);

--
-- Indici per le tabelle `fees`
--
ALTER TABLE `fees`
  ADD PRIMARY KEY (`IdFee`),
  ADD UNIQUE KEY `Type` (`Type`);

--
-- Indici per le tabelle `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`IdMember`),
  ADD KEY `MembershipFee` (`MembershipFee`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`IdUser`),
  ADD UNIQUE KEY `FiscalCode` (`FiscalCode`),
  ADD UNIQUE KEY `Email` (`Email`),
  ADD UNIQUE KEY `IdUser` (`IdUser`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `boats`
--
ALTER TABLE `boats`
  MODIFY `IdBoat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la tabella `fees`
--
ALTER TABLE `fees`
  MODIFY `IdFee` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `IdUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

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
