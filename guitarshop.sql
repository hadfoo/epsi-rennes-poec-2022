-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: May 25, 2022 at 01:49 PM
-- Server version: 10.2.8-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `guitarshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `order_all`
--

DROP TABLE IF EXISTS `order_all`;
CREATE TABLE IF NOT EXISTS `order_all` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` timestamp(4) NULL DEFAULT NULL,
  `customer` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `total_price` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `order_all`
--

INSERT INTO `order_all` (`id`, `date`, `customer`, `total_price`) VALUES
(1, '2022-05-23 22:00:00.0000', 'bob', 1000),
(2, '2022-05-15 22:00:00.0000', 'gibson', 120);

-- --------------------------------------------------------

--
-- Table structure for table `order_has_product`
--

DROP TABLE IF EXISTS `order_has_product`;
CREATE TABLE IF NOT EXISTS `order_has_product` (
  `id_order` int(11) NOT NULL,
  `id_product` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `order_has_product`
--

INSERT INTO `order_has_product` (`id_order`, `id_product`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 3),
(2, 5),
(2, 6);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brand` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `category` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `brand`, `model`, `price`, `category`) VALUES
(1, 'Gibson', 'SG', 1250, '1'),
(2, 'Fender', 'Telecaster', 1200, '1'),
(3, 'Gibson', 'LesPaul', 1300, '1'),
(4, 'Fender', 'Stratocaster', 1100, '1'),
(5, 'Boss', 'Looper RC-1', 120, '2'),
(6, 'Peavey', 'Vypyr30', 200, '3'),
(7, 'Marshall', 'JCM2203', 1600, '3'),
(8, 'Fender', 'Hot Rod Deluxe', 450, '3'),
(9, 'Roland', 'Cube', 150, '3');

-- --------------------------------------------------------

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
CREATE TABLE IF NOT EXISTS `product_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product_category`
--

INSERT INTO `product_category` (`id`, `name`) VALUES
(1, 'Guitar'),
(2, 'Pedal'),
(3, 'Guitar Amps');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL,
  `role` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
