-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : mer. 18 mai 2022 à 07:07
-- Version du serveur : 10.6.5-MariaDB
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `pizza`
--

-- --------------------------------------------------------

--
-- Structure de la table `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
CREATE TABLE IF NOT EXISTS `ingredients`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `type`       varchar(255) NOT NULL,
    `label`      varchar(255) NOT NULL,
    `nbCalories` int(11)      NOT NULL,
    `prix`       int(11)      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM
  AUTO_INCREMENT = 27
  DEFAULT CHARSET = utf8mb3;

--
-- Déchargement des données de la table `ingredients`
--

INSERT INTO `ingredients` (`id`, `type`, `label`, `nbCalories`, `prix`)
VALUES (1, 'pate', 'pate fine', 100, 3),
       (2, 'pate', 'pate epaisse', 120, 4),
       (3, 'sauce', 'tomate', 50, 1),
       (4, 'sauce', 'crème fraîche', 80, 2),
       (5, 'sauce', 'barbecue', 70, 2),
       (6, 'sauce', 'spicy tomato', 60, 5),
       (7, 'garniture', 'jambon', 40, 3),
       (8, 'garniture', 'emmental', 75, 2),
       (9, 'garniture', 'boeuf', 75, 3),
       (10, 'garniture', 'olives', 15, 1),
       (11, 'garniture', 'poulet', 80, 3),
       (12, 'garniture', 'mozzarella', 60, 1),
       (13, 'garniture', 'merguez', 75, 2),
       (14, 'garniture', 'poivrons', 30, 1),
       (15, 'garniture', 'roquefort', 85, 2),
       (16, 'garniture', 'chèvre', 75, 2),
       (17, 'garniture', 'chorizo', 60, 1),
       (18, 'garniture', 'merguez', 75, 2),
       (19, 'garniture', 'poivrons', 30, 1),
       (20, 'garniture', 'roquefort', 85, 2),
       (21, 'garniture', 'chèvre', 75, 2),
       (22, 'garniture', 'chorizo', 60, 1),
       (23, 'garniture', 'bacon', 60, 2),
       (24, 'garniture', 'ananas', 25, 1),
       (25, 'pate', 'mozza-crust', 125, 4),
       (26, 'garniture', 'saumon', 40, 3);

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

DROP TABLE IF EXISTS `panier`;
CREATE TABLE IF NOT EXISTS `panier`
(
    `id`   int(11)      NOT NULL AUTO_INCREMENT,
    `date` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb3;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`id`, `date`)
VALUES (1, '2022-05-12T12:33:39.6661632'),
       (2, '2022-05-12T12:34:29.4816416'),
       (3, '2022-05-12T12:35:55.2679305'),
       (4, '2022-05-12T14:10:42.1090185');

-- --------------------------------------------------------

--
-- Structure de la table `panier_pizza`
--

DROP TABLE IF EXISTS `panier_pizza`;
CREATE TABLE IF NOT EXISTS `panier_pizza`
(
    `panierid` int(11) NOT NULL,
    `pizzaid`  int(11) NOT NULL
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb3;

--
-- Déchargement des données de la table `panier_pizza`
--

INSERT INTO `panier_pizza` (`panierid`, `pizzaid`)
VALUES (2, 2),
       (4, 4),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 4),
       (4, 0),
       (4, 3),
       (4, 3),
       (4, 3),
       (4, 2),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 2),
       (4, 2),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0),
       (4, 0);

-- --------------------------------------------------------

--
-- Structure de la table `pizza`
--

DROP TABLE IF EXISTS `pizza`;
CREATE TABLE IF NOT EXISTS `pizza`
(
    `id`    int(11)      NOT NULL AUTO_INCREMENT,
    `label` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb3;

--
-- Déchargement des données de la table `pizza`
--

INSERT INTO `pizza` (`id`, `label`)
VALUES (5, '4 Fromaggi'),
       (2, 'Margherita'),
       (3, 'Maranira'),
       (4, 'BBQ'),
       (6, 'Mexico'),
       (7, 'Bacon BBQ'),
       (8, 'Atlantica'),
       (9, 'Demona'),
       (10, 'Chevrina');

-- --------------------------------------------------------

--
-- Structure de la table `pizza_ingredients`
--

DROP TABLE IF EXISTS `pizza_ingredients`;
CREATE TABLE IF NOT EXISTS `pizza_ingredients`
(
    `pizzaid`      int(11) NOT NULL,
    `ingredientid` int(11) NOT NULL
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb3;

--
-- Déchargement des données de la table `pizza_ingredients`
--

INSERT INTO `pizza_ingredients` (`pizzaid`, `ingredientid`)
VALUES (10, 10),
       (10, 16),
       (10, 4),
       (10, 2),
       (2, 1),
       (2, 3),
       (2, 8),
       (2, 10),
       (3, 1),
       (3, 3),
       (3, 8),
       (3, 10),
       (4, 2),
       (4, 5),
       (4, 6),
       (4, 7),
       (4, 9),
       (4, 8),
       (5, 25),
       (5, 3),
       (5, 8),
       (5, 15),
       (5, 12),
       (5, 16),
       (5, 10),
       (6, 2),
       (6, 3),
       (6, 6),
       (6, 11),
       (6, 12),
       (6, 19),
       (7, 2),
       (7, 5),
       (7, 11),
       (7, 23),
       (7, 12),
       (8, 1),
       (8, 4),
       (8, 12),
       (8, 26),
       (8, 10),
       (9, 2),
       (9, 3),
       (9, 7),
       (9, 12),
       (9, 24),
       (10, 11);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`
(
    `id`       int(11)      NOT NULL AUTO_INCREMENT,
    `mail`     varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `role`     varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb3;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `mail`, `password`, `role`)
VALUES (2, 'Orel', '$2a$10$lb.KVV.KGJ7s58xubcLjI.UZ71hc2h7iY5TFqZtKIMWLFApEffMIe', 'ROLE_ADMIN');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
