-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 30 juin 2019 à 09:11
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `equiwatch`
--

-- --------------------------------------------------------

--
-- Structure de la table `capteurs`
--

DROP TABLE IF EXISTS `capteurs`;
CREATE TABLE IF NOT EXISTS `capteurs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) COLLATE utf8_bin NOT NULL,
  `idTypeCapteur` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idTypeCapteur` (`idTypeCapteur`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `capteurs`
--

INSERT INTO `capteurs` (`id`, `label`, `idTypeCapteur`) VALUES
(1, 'Hydro1', 1),
(2, 'Elec1', 2),
(3, 'temp1', 3);

-- --------------------------------------------------------

--
-- Structure de la table `capteurshassignal`
--

DROP TABLE IF EXISTS `capteurshassignal`;
CREATE TABLE IF NOT EXISTS `capteurshassignal` (
  `idCapteurs` int(11) NOT NULL,
  `dernierSignal` datetime NOT NULL,
  `valeur` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`idCapteurs`,`dernierSignal`) USING BTREE,
  KEY `idCapteurs` (`idCapteurs`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `chevaux`
--

DROP TABLE IF EXISTS `chevaux`;
CREATE TABLE IF NOT EXISTS `chevaux` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `enclos`
--

DROP TABLE IF EXISTS `enclos`;
CREATE TABLE IF NOT EXISTS `enclos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `enclos`
--

INSERT INTO `enclos` (`id`, `label`) VALUES
(1, 'Manege\r\n'),
(2, 'Carriere'),
(3, 'Pre');

-- --------------------------------------------------------

--
-- Structure de la table `encloshascapteurs`
--

DROP TABLE IF EXISTS `encloshascapteurs`;
CREATE TABLE IF NOT EXISTS `encloshascapteurs` (
  `idEnclos` int(11) NOT NULL,
  `idCpateurs` int(11) NOT NULL,
  PRIMARY KEY (`idEnclos`,`idCpateurs`),
  KEY `idCpateurs` (`idCpateurs`),
  KEY `idEnclos` (`idEnclos`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `encloshascapteurs`
--

INSERT INTO `encloshascapteurs` (`idEnclos`, `idCpateurs`) VALUES
(1, 1),
(1, 2),
(1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `pointgps`
--

DROP TABLE IF EXISTS `pointgps`;
CREATE TABLE IF NOT EXISTS `pointgps` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` varchar(255) COLLATE utf8_bin NOT NULL,
  `longitude` varchar(255) COLLATE utf8_bin NOT NULL,
  `ordre` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Structure de la table `typescapteurs`
--

DROP TABLE IF EXISTS `typescapteurs`;
CREATE TABLE IF NOT EXISTS `typescapteurs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Déchargement des données de la table `typescapteurs`
--

INSERT INTO `typescapteurs` (`id`, `label`) VALUES
(1, 'Hydraulique'),
(2, 'Electrique'),
(3, 'Thermique');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

DROP TABLE IF EXISTS `utilisateurs`;
CREATE TABLE IF NOT EXISTS `utilisateurs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) COLLATE utf8_bin NOT NULL,
  `prenom` varchar(255) COLLATE utf8_bin NOT NULL,
  `email` varchar(255) COLLATE utf8_bin NOT NULL,
  `telephone` varchar(255) COLLATE utf8_bin NOT NULL,
  `login` varchar(255) COLLATE utf8_bin NOT NULL,
  `mdp` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
