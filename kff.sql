-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 21, 2022 at 04:47 AM
-- Server version: 5.7.33
-- PHP Version: 7.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kff`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` char(11) NOT NULL,
  `username` varchar(10) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `username`, `email`, `password`) VALUES
('KFFA0001', 'khenkun', 'khenkun@gmail.com', '123'),
('KFFA0003', 'billy', 'billy@gmail.com', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `app`
--

CREATE TABLE `app` (
  `id` int(11) NOT NULL,
  `name` varchar(11) NOT NULL,
  `version` varchar(11) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `app`
--

INSERT INTO `app` (`id`, `name`, `version`, `description`) VALUES
(1, 'KFF', '1.0 RELEASE', 'This application is build using java language.');

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `comp_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` text NOT NULL,
  `description` text NOT NULL,
  `trademark` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`comp_id`, `name`, `address`, `description`, `trademark`) VALUES
(1, 'KFF', 'chicken st', 'KFF (Khenkun French Fries) is serving foods and drinks.', '®KFF');

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE `food` (
  `food_id` char(11) NOT NULL,
  `categories` varchar(25) NOT NULL,
  `food_name` varchar(50) NOT NULL,
  `price` varchar(10) NOT NULL,
  `qty` int(11) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `food`
--

INSERT INTO `food` (`food_id`, `categories`, `food_name`, `price`, `qty`, `description`) VALUES
('KFF0001', 'Food', 'French Fries Melt', '12000', 8, 'French fries with mozarella melt.'),
('KFF0002', 'Food', 'French Fries Beef', '20000', 0, 'French fries with smoked beef.'),
('KFF0003', 'Food', 'Chicken Nugget Melt', '12000', -2, 'Chicken nugget with melt'),
('KFF0004', 'Food', 'Chicken Nugget Original', '12000', 1, 'Original chicken nugget'),
('KFF0005', 'Food', 'Fried Chicken Cheese', '12000', 6, 'Fried chicken with cheese'),
('KFF0006', 'Food', 'Fried Chicken Original', '12000', 6, 'Original Fried Chicken'),
('KFF0007', 'Food', 'Salad', '8000', 6, 'Salad'),
('KFF0008', 'Food', 'Burger Chicken Cheese', '20000', 2, 'Burger chicken with cheese'),
('KFF0009', 'Food', 'Burger Original', '20000', 5, 'Burger with original taste'),
('KFF0010', 'Food', 'Burger Double Cheese', '24000', 4, 'Burger with double cheese'),
('KFF0011', 'Drink', 'Soda Tea', '10000', 10, 'Soda with tea'),
('KFF0012', 'Food', 'Coca Cola', '9000', -4, 'Fresh coca cola'),
('KFF0013', 'Drink', 'Lychee Tea', '10000', 11, 'Fresh lychee tea');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `sales_id` char(11) NOT NULL,
  `sales_date` varchar(100) NOT NULL,
  `food_id` char(11) NOT NULL,
  `food_name` varchar(50) NOT NULL,
  `price` varchar(13) NOT NULL,
  `qty` int(11) NOT NULL,
  `total` int(13) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`sales_id`, `sales_date`, `food_id`, `food_name`, `price`, `qty`, `total`, `description`) VALUES
('S0001', '2009-02-11', 'KFF0001', 'French Fries Melt', '12000', 6, 72000, ''),
('S0002', '2006-05-09', 'KFF0002', 'French Fries Beef', '20000', 4, 80000, ''),
('S0003', '2012-07-04', 'KFF0003', 'Chicken Nugget Melt', '12000', 9, 108000, ''),
('S0004', '2013-09-25', 'KFF0004', 'Chicken Nugget Original', '12000', 3, 36000, ''),
('S0005', '2017-02-02', 'KFF0005', 'Fried Chicken Cheese', '12000', 9, 108000, ''),
('S0006', '2013-10-01', 'KFF0006', 'Fried Chicken Original', '12000', 7, 84000, ''),
('S0007', '2016-06-02', 'KFF0007', 'Salad', '8000', 2, 16000, ''),
('S0008', '2019-02-07', 'KFF0008', 'Burger Chicken Cheese', '20000', 6, 120000, ''),
('S0009', '2015-01-23', 'KFF0009', 'Burger Original', '20000', 4, 80000, ''),
('S0010', '2014-05-15', 'KFF0010', 'Burger Double Cheese', '24000', 5, 120000, ''),
('S0011', '2016-06-23', 'KFF0011', 'Soda Tea', '10000', 7, 70000, ''),
('S0012', '2021-06-10', 'KFF0012', 'Coca Cola', '9000', 10, 90000, ''),
('S0013', '2022-02-20', 'KFF0013', 'Lychee Tea', '10000', 4, 40000, '');

--
-- Triggers `sales`
--
DELIMITER $$
CREATE TRIGGER `delete` AFTER DELETE ON `sales` FOR EACH ROW BEGIN 
	UPDATE food SET qty = qty + OLD.qty
    WHERE food_id = OLD.food_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `f_out` AFTER INSERT ON `sales` FOR EACH ROW BEGIN
	UPDATE food SET qty = qty - NEW.qty
    WHERE food_id = NEW.food_id;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `app`
--
ALTER TABLE `app`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`comp_id`);

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`food_id`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`sales_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `app`
--
ALTER TABLE `app`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `comp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
