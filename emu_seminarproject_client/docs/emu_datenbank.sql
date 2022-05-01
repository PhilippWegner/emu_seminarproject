-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 26. Apr 2022 um 22:17
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

--
-- Daten für Tabelle `messreihe`
--

INSERT INTO `messreihe` (`MessreihenId`, `Zeitintervall`, `Verbraucher`, `Messgroesse`) VALUES
(1, 5, 'Lampe', 'Leistung'),
(2, 10, 'Computer', 'Arbeit'),
(3, 15, 'GPU', 'Leistung');

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
-- Daten für Tabelle `messung`
--

INSERT INTO `messung` (`LaufendeNummer`, `Wert`, `MessreihenId`) VALUES
(1, 4.6, 1),
(2, 4.6, 1),
(3, 4.5, 1),
(4, 4.6, 1),
(5, 4.5, 1),
(6, 4.5, 1),
(7, 4.6, 1),
(8, 4.6, 1),
(9, 4.6, 1),
(1, 4.6, 2),
(2, 4.6, 2),
(1, 4.5, 3),
(2, 4.6, 3);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
