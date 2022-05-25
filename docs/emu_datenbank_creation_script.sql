-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 25. Mai 2022 um 15:39
-- Server-Version: 10.4.18-MariaDB
-- PHP-Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `emu_datenbank`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `messreihe`
--

CREATE TABLE `messreihe` (
  `MessreihenId` int(11) NOT NULL,
  `Zeitintervall` int(11) NOT NULL,
  `Verbraucher` text NOT NULL,
  `Messgroesse` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `messung`
--

CREATE TABLE `messung` (
  `LaufendeNummer` int(11) NOT NULL,
  `Wert` double NOT NULL,
  `MessreihenId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `messreihe`
--
ALTER TABLE `messreihe`
  ADD PRIMARY KEY (`MessreihenId`);

--
-- Indizes für die Tabelle `messung`
--
ALTER TABLE `messung`
  ADD UNIQUE KEY `LaufendeNummer` (`LaufendeNummer`,`MessreihenId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
