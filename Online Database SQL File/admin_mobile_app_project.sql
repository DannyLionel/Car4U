-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 25, 2019 at 08:01 PM
-- Server version: 10.0.38-MariaDB-0+deb8u1
-- PHP Version: 7.1.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `admin_mobile_app_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `id` int(11) NOT NULL,
  `carid` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `total_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

CREATE TABLE `cars` (
  `id` int(11) NOT NULL,
  `make_id` int(11) NOT NULL,
  `model_id` int(11) NOT NULL,
  `avaliable` tinyint(1) NOT NULL,
  `owner_id` int(11) NOT NULL,
  `price` double NOT NULL,
  `image_link` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cars`
--

INSERT INTO `cars` (`id`, `make_id`, `model_id`, `avaliable`, `owner_id`, `price`, `image_link`) VALUES
(1, 1, 1, 1, 4, 34.52, 'https://allansantosh.com/mobileappproject/images/camry.jpeg'),
(2, 1, 2, 1, 9, 45.5, 'https://allansantosh.com/mobileappproject/images/corolla.jpeg'),
(3, 3, 11, 1, 11, 65.98, 'https://allansantosh.com/mobileappproject/images/edge.jpeg'),
(4, 4, 16, 1, 14, 52.55, 'https://allansantosh.com/mobileappproject/images/pathfinder.jpeg'),
(5, 5, 18, 1, 16, 92.34, 'https://allansantosh.com/mobileappproject/images/challenger.jpeg'),
(6, 5, 17, 1, 18, 87.09, 'https://allansantosh.com/mobileappproject/images/charger.jpeg'),
(7, 5, 20, 1, 14, 67.54, 'https://allansantosh.com/mobileappproject/images/journey.jpeg'),
(8, 4, 16, 1, 13, 45.22, 'https://allansantosh.com/mobileappproject/images/pathfinder.jpeg'),
(9, 1, 3, 1, 10, 54.22, 'https://allansantosh.com/mobileappproject/images/avalon.jpeg'),
(10, 4, 13, 1, 18, 43.22, 'https://allansantosh.com/mobileappproject/images/altima.jpeg'),
(11, 5, 19, 1, 20, 65.33, 'https://allansantosh.com/mobileappproject/images/caravan.jpeg'),
(12, 4, 14, 1, 19, 63.22, 'https://allansantosh.com/mobileappproject/images/maxima.jpeg'),
(13, 3, 9, 1, 21, 32.55, 'https://allansantosh.com/mobileappproject/images/fusion.jpeg'),
(14, 2, 8, 1, 22, 65.09, 'https://allansantosh.com/mobileappproject/images/x6.jpeg'),
(15, 2, 6, 1, 24, 69.89, 'https://allansantosh.com/mobileappproject/images/m4.jpeg'),
(16, 2, 5, 1, 23, 76.32, 'https://allansantosh.com/mobileappproject/images/m3.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `make`
--

CREATE TABLE `make` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `make`
--

INSERT INTO `make` (`id`, `name`) VALUES
(1, 'Toyota'),
(2, 'BMW'),
(3, 'Ford'),
(4, 'Nissan'),
(5, 'Dodge'),
(6, 'Audi'),
(7, 'Hyundai');

-- --------------------------------------------------------

--
-- Table structure for table `model`
--

CREATE TABLE `model` (
  `id` int(11) NOT NULL,
  `make_id` int(11) NOT NULL,
  `model` varchar(255) NOT NULL,
  `num_seats` int(11) NOT NULL,
  `suv` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `model`
--

INSERT INTO `model` (`id`, `make_id`, `model`, `num_seats`, `suv`) VALUES
(1, 1, 'Camry', 5, 0),
(2, 1, 'Corolla', 5, 0),
(3, 1, 'Avalon', 5, 0),
(4, 1, 'Highlander', 8, 1),
(5, 2, 'M3', 5, 0),
(6, 2, 'M4', 5, 0),
(7, 2, 'M5', 5, 0),
(8, 2, 'X6', 5, 1),
(9, 3, 'Fusion', 5, 0),
(10, 3, 'Mustang', 5, 0),
(11, 3, 'Edge', 5, 1),
(12, 3, 'Escape', 7, 1),
(13, 4, 'Altima', 5, 0),
(14, 4, 'Maxima', 5, 0),
(15, 4, 'Rogue', 5, 1),
(16, 4, 'Pathfinder', 8, 1),
(17, 5, 'Charger', 5, 0),
(18, 5, 'Challenger', 5, 0),
(19, 5, 'Caravan', 7, 1),
(20, 5, 'Journey', 7, 1),
(21, 6, 'A4', 5, 0),
(22, 6, 'A5', 5, 0),
(23, 6, 'Q8', 8, 1),
(24, 6, 'Q7', 8, 1),
(25, 6, 'Q6', 5, 1),
(26, 7, 'Sonata', 5, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `username`, `password`, `email`) VALUES
(1, 'Allan', 'Santosh', 'allansantosh', 'pass1', 'allansantosh@gmail.com'),
(9, 'Luis', 'B. Toms', 'Goisuccaut', 'Ooy0aiLeet', 'LuisBToms@rhyta.com'),
(10, 'Roy', 'A. Campbell', 'Smarculd', 'pahCh1tho', 'RoyACampbell@jourrapide.com\r\n'),
(11, 'Steven', 'T. Shelton', 'Hiscor', 'neeH8ces', 'StevenTShelton@rhyta.com'),
(12, 'Kevin', 'D. Mitchell', 'Windepped1988', 'Aesh1jei', 'KevinDMitchell@armyspy.com'),
(13, 'Mark', 'M. Walker', 'Commed', 'soTh7ageijoh', 'MarkMWalker@armyspy.com'),
(14, 'Charlotte', 'J. Paz', 'Thartat', 'booch6Ai', 'CharlotteJPaz@armyspy.com'),
(15, 'Julia', 'F. Ward', 'Mereass1960', 'Iezagh6Uo', 'JuliaFWard@jourrapide.com'),
(16, 'Cleo', 'D. Quarles', 'Chismakey', 'ueth2VahSei', 'CleoDQuarles@jourrapide.com'),
(17, 'Paul', 'L. McMichael', 'Sperihat', 'Ohmie2Osaph', 'PaulLMcMichael@jourrapide.com'),
(18, 'Andrew', 'L. Chavez', 'Essfull', 'Aqueera7asai', 'AndrewLChavez@armyspy.com'),
(19, 'Ricardo', 'H. Mireles', 'Lilly1935', 'udahLoh4vio', 'RicardoHMireles@dayrep.com'),
(20, 'Edward', 'E. Driver', 'Embefors', 'chaethoog7Oo', 'EdwardEDriver@rhyta.com'),
(21, 'Fred', 'A. Bischoff', 'Priagenction71', 'OpieXa5riuz', 'FredABischoff@jourrapide.com'),
(22, 'Laura', 'P. Moore', 'Plimparthid', 'eb2ceegh2Ee', 'LauraPMoore@teleworm.us'),
(23, 'Charles', 'L. Handley', 'Nowent93', 'thahyee7eeG', 'CharlesLHandley@armyspy.com'),
(24, 'Alfred', 'D. Snell', 'Thaide72', 'eY6Tik3ohm', 'AlfredDSnell@dayrep.com'),
(25, 'ayey', 'far', 'ayefar', 'mypassword', 'ayesha.farkhundah@ontariotechu.net'),
(26, 'Mathew', 'play', 'mplay', '123', 'mplay@gmail.com'),
(27, 'aye', 'far', 'ayefar2', 'mypassword', 'ayesha.farkhundah@Ontariotechu.net'),
(28, 'danny', 'lionel', 'dhanush13', 'dhanush13', 'dhanushga.lionel@ontariotechu.net');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `make`
--
ALTER TABLE `make`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `model`
--
ALTER TABLE `model`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cars`
--
ALTER TABLE `cars`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1000;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
