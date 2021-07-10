-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: 10-Jul-2021 às 15:07
-- Versão do servidor: 5.7.24
-- versão do PHP: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nshop`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apt` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `street_name` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `id_city` int(11) DEFAULT NULL,
  `id_client` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKxpbahketqa6aw9r9ico11xu9` (`id_city`),
  KEY `FKh3o13l4287gb6ok1fm7tdo55r` (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `address`
--

INSERT INTO `address` (`id`, `apt`, `district`, `number`, `street_name`, `zip`, `id_city`, `id_client`) VALUES
(1, 'Apto 303', 'Jardim', '300', 'Rua Flores', '38220834', 1, 1),
(2, 'Sala 800', 'Centro', '105', 'Avenida Matos', '38777012', 2, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `boleto_payment`
--

DROP TABLE IF EXISTS `boleto_payment`;
CREATE TABLE IF NOT EXISTS `boleto_payment` (
  `expiration_day` datetime(6) DEFAULT NULL,
  `payment_day` datetime(6) DEFAULT NULL,
  `id_order` int(11) NOT NULL,
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `boleto_payment`
--

INSERT INTO `boleto_payment` (`expiration_day`, `payment_day`, `id_order`) VALUES
('2017-10-20 00:00:00.000000', NULL, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `card_payment`
--

DROP TABLE IF EXISTS `card_payment`;
CREATE TABLE IF NOT EXISTS `card_payment` (
  `installments` int(11) DEFAULT NULL,
  `id_order` int(11) NOT NULL,
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `card_payment`
--

INSERT INTO `card_payment` (`installments`, `id_order`) VALUES
(6, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Informática'),
(2, 'Escritório'),
(3, 'Cama mesa e banho'),
(4, 'Eletrônicos'),
(5, 'Jardinagem'),
(6, 'Decoração'),
(7, 'Perfumaria');

-- --------------------------------------------------------

--
-- Estrutura da tabela `city`
--

DROP TABLE IF EXISTS `city`;
CREATE TABLE IF NOT EXISTS `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `id_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd0p47lqu885cst48arraojuqs` (`id_state`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `city`
--

INSERT INTO `city` (`id`, `name`, `id_state`) VALUES
(1, 'Uberlândia', 1),
(2, 'São Paulo', 2),
(3, 'Campinas', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `document_id` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bfgjs3fem0hmjhvih80158x29` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `client`
--

INSERT INTO `client` (`id`, `document_id`, `email`, `name`, `type`) VALUES
(1, '36378912377', 'maria@gmail.com', 'Maria Silva', 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `client_order`
--

DROP TABLE IF EXISTS `client_order`;
CREATE TABLE IF NOT EXISTS `client_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime(6) DEFAULT NULL,
  `id_client` int(11) DEFAULT NULL,
  `id_delivery_address` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6ni3ry5dd7dojygmaqebtod71` (`id_client`),
  KEY `FK6dg7r6dndvi4tasb9mwvwy17f` (`id_delivery_address`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `client_order`
--

INSERT INTO `client_order` (`id`, `date`, `id_client`, `id_delivery_address`) VALUES
(1, '2017-09-30 10:32:00.000000', 1, 1),
(2, '2017-10-10 19:35:00.000000', 1, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `client_order_item`
--

DROP TABLE IF EXISTS `client_order_item`;
CREATE TABLE IF NOT EXISTS `client_order_item` (
  `amount` int(11) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `id_order` int(11) NOT NULL,
  `id_product` int(11) NOT NULL,
  PRIMARY KEY (`id_order`,`id_product`),
  KEY `FK835bolboeu3h48i8e0cg2220o` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `client_order_item`
--

INSERT INTO `client_order_item` (`amount`, `discount`, `price`, `id_order`, `id_product`) VALUES
(1, 0, 2000, 1, 1),
(2, 0, 80, 1, 3),
(1, 100, 800, 2, 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `id_order` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `payment`
--

INSERT INTO `payment` (`id_order`, `status`) VALUES
(1, 2),
(2, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `phones`
--

DROP TABLE IF EXISTS `phones`;
CREATE TABLE IF NOT EXISTS `phones` (
  `client_id` int(11) NOT NULL,
  `phones` varchar(255) DEFAULT NULL,
  KEY `FKh4or0t7446ksr8839qxa2n8an` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `phones`
--

INSERT INTO `phones` (`client_id`, `phones`) VALUES
(1, '27363323'),
(1, '93838393');

-- --------------------------------------------------------

--
-- Estrutura da tabela `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `product`
--

INSERT INTO `product` (`id`, `name`, `price`) VALUES
(1, 'Computador', 2000),
(2, 'Impressora', 800),
(3, 'Mouse', 80),
(4, 'Mesa de escritório', 300),
(5, 'Toalha', 50),
(6, 'Colcha', 200),
(7, 'TV true color', 1200),
(8, 'Roçadeira', 800),
(9, 'Abajour', 100),
(10, 'Pendente', 180),
(11, 'Shampoo', 90);

-- --------------------------------------------------------

--
-- Estrutura da tabela `product_category`
--

DROP TABLE IF EXISTS `product_category`;
CREATE TABLE IF NOT EXISTS `product_category` (
  `id_product` int(11) NOT NULL,
  `id_category` int(11) NOT NULL,
  KEY `FK4y2ucymplqxycf58myn6abcv2` (`id_category`),
  KEY `FKt4sn9fs5ju7d8mcoporlyhfun` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `product_category`
--

INSERT INTO `product_category` (`id_product`, `id_category`) VALUES
(1, 1),
(1, 1),
(1, 4),
(2, 1),
(2, 2),
(2, 1),
(2, 2),
(2, 4),
(3, 1),
(3, 1),
(3, 4),
(4, 2),
(5, 3),
(6, 3),
(7, 4),
(8, 5),
(9, 6),
(10, 6),
(11, 7);

-- --------------------------------------------------------

--
-- Estrutura da tabela `state`
--

DROP TABLE IF EXISTS `state`;
CREATE TABLE IF NOT EXISTS `state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `state`
--

INSERT INTO `state` (`id`, `name`) VALUES
(1, 'Minas Gerais'),
(2, 'São Paulo');

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `FKh3o13l4287gb6ok1fm7tdo55r` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `FKxpbahketqa6aw9r9ico11xu9` FOREIGN KEY (`id_city`) REFERENCES `city` (`id`);

--
-- Limitadores para a tabela `boleto_payment`
--
ALTER TABLE `boleto_payment`
  ADD CONSTRAINT `FKdgbbuw5oy5g64vi53jtmiavnd` FOREIGN KEY (`id_order`) REFERENCES `payment` (`id_order`);

--
-- Limitadores para a tabela `card_payment`
--
ALTER TABLE `card_payment`
  ADD CONSTRAINT `FKoi60oek3oltjtej9ty3lmrrpt` FOREIGN KEY (`id_order`) REFERENCES `payment` (`id_order`);

--
-- Limitadores para a tabela `city`
--
ALTER TABLE `city`
  ADD CONSTRAINT `FKd0p47lqu885cst48arraojuqs` FOREIGN KEY (`id_state`) REFERENCES `state` (`id`);

--
-- Limitadores para a tabela `client_order`
--
ALTER TABLE `client_order`
  ADD CONSTRAINT `FK6dg7r6dndvi4tasb9mwvwy17f` FOREIGN KEY (`id_delivery_address`) REFERENCES `address` (`id`),
  ADD CONSTRAINT `FK6ni3ry5dd7dojygmaqebtod71` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`);

--
-- Limitadores para a tabela `client_order_item`
--
ALTER TABLE `client_order_item`
  ADD CONSTRAINT `FK4g634l46lbx47tys8ebp529x5` FOREIGN KEY (`id_order`) REFERENCES `client_order` (`id`),
  ADD CONSTRAINT `FK835bolboeu3h48i8e0cg2220o` FOREIGN KEY (`id_product`) REFERENCES `product` (`id`);

--
-- Limitadores para a tabela `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `FKddjhuhmjkhgmygqduxbdvbwiw` FOREIGN KEY (`id_order`) REFERENCES `client_order` (`id`);

--
-- Limitadores para a tabela `phones`
--
ALTER TABLE `phones`
  ADD CONSTRAINT `FKh4or0t7446ksr8839qxa2n8an` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`);

--
-- Limitadores para a tabela `product_category`
--
ALTER TABLE `product_category`
  ADD CONSTRAINT `FK4y2ucymplqxycf58myn6abcv2` FOREIGN KEY (`id_category`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `FKt4sn9fs5ju7d8mcoporlyhfun` FOREIGN KEY (`id_product`) REFERENCES `product` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
