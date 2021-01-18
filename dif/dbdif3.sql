-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 04, 2021 at 07:27 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbdif`
--

-- --------------------------------------------------------

--
-- Table structure for table `caso`
--

CREATE TABLE `caso` (
  `id_caso` smallint(5) UNSIGNED NOT NULL,
  `fecha_apertura` date DEFAULT NULL,
  `descripcion_general` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `estado` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
-- --------------------------------------------------------
INSERT INTO `caso` VALUES (1, '2021-01-15', 'a', 1);
--
-- Table structure for table `cita`
--

CREATE TABLE `cita` (
  `id_cita` smallint(5) UNSIGNED NOT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `paciente` smallint(5) UNSIGNED NOT NULL,
  `usuario` tinyint(3) UNSIGNED NOT NULL,
  `visible` tinyint(1) UNSIGNED DEFAULT 1,
  `asistio` tinyint(1) UNSIGNED DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------
INSERT INTO `cita` VALUES (1, '2021-01-06', '12:00:00', 1, 2, 1, 0);
--
-- Table structure for table `consulta`
--

CREATE TABLE `consulta` (
  `id_consulta` smallint(5) UNSIGNED NOT NULL,
  `usuario` tinyint(3) UNSIGNED NOT NULL,
  `cita` smallint(5) UNSIGNED DEFAULT NULL,
  `paciente` smallint(5) UNSIGNED NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `motivo_atencion` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `notas_sesion` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_consulta` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `tratamiento` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------
INSERT INTO `consulta` VALUES (1, 2, 1, 1, '2021-01-06', '12:30:00', 'Depresión', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse mattis, ante vel pretium fringilla, nunc libero porta eros, et porttitor ipsum urna vitae metus. Proin ullamcorper, risus interdum dignissim fringilla, turpis urna gravida mi, et malesuada arcu arcu sed enim. Curabitur gravida vestibulum tellus, sit amet sodales est bibendum quis. Sed vel erat ut magna tristique congue. Mauris malesuada luctus nisi, ac viverra purus finibus ut. Ut tincidunt maximus lacus, sed malesuada odio lobortis id. Aliquam aliquet imperdiet mi, vitae venenatis magna eleifend vel. Vestibulum at posuere sem. Nulla facilisi. Praesent lacus ante, vulputate eget augue nec, ullamcorper tincidunt est.'
,'Psicología', 'Decir me amo 3 veces a las 3:00 a.m. con las luces apagadas');
--
-- Table structure for table `paciente`
--

CREATE TABLE `paciente` (
  `id_paciente` smallint(5) UNSIGNED NOT NULL,
  `usuario` tinyint(3) UNSIGNED NOT NULL,
  `fecha_registro` date NOT NULL,
  `nombres` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `ap` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `am` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `menor_de_edad` tinyint(1) NOT NULL,
  `telefono` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `estado` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `municipio` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `domicilio` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sexo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `estado_civil` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `escolaridad` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ocupacion` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `caso` smallint(5) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `paciente`
--

INSERT INTO `paciente` VALUES (1, 2, '2021-01-04', 'Antonieta', 'Martinez', 'Torres', 0, '3141312334', 'Manzanillo', 'Colima', 'Barrio 5', 'Femenino', '1999-12-25', 'Soltera', 'Universidad', 'Estudiante', 1);

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` tinyint(3) UNSIGNED NOT NULL,
  `nombres` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `ap` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `am` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `correo` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_usuario` varchar(11) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombres`,`ap`,`am`, `correo`, `password`, `tipo_usuario`) VALUES
(1, 'Luis', 'a', 'e', 'lmartinez@hotmail.com', '1234', 'Psicología'),
(2, 'hola', 'a', 'o', 'hola', '1234', 'Psicología'),
(3, 'Pepe', 'e', 'a', 'elpepe', '1234', 'Peritaje');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `caso`
--
ALTER TABLE `caso`
  ADD PRIMARY KEY (`id_caso`);

--
-- Indexes for table `cita`
--
ALTER TABLE `cita`
  ADD PRIMARY KEY (`id_cita`),
  ADD KEY `fk_usuarioC_idx` (`usuario`),
  ADD KEY `fk_pacienteC_idx` (`paciente`);

--
-- Indexes for table `consulta`
--
ALTER TABLE `consulta`
  ADD PRIMARY KEY (`id_consulta`),
  ADD KEY `fk_usuario_idx` (`usuario`),
  ADD KEY `fk_paciente_idx` (`paciente`),
  ADD KEY `fk_cita_idx` (`cita`);

--
-- Indexes for table `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`id_paciente`),
  ADD KEY `fk_usuarioP_idx` (`usuario`),
  ADD KEY `fk_caso_idx` (`caso`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`);

--
-- Constraints for dumped tables
--
ALTER TABLE `paciente`
  ADD CONSTRAINT `fk_usuarioP` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_caso` FOREIGN KEY (`caso`) REFERENCES `caso` (`id_caso`) ON UPDATE CASCADE;
--
-- Constraints for table `cita`
--
ALTER TABLE `cita`
  ADD CONSTRAINT `fk_pacienteC` FOREIGN KEY (`paciente`) REFERENCES `paciente` (`id_paciente`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_usuarioC` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON UPDATE CASCADE;

--
-- Constraints for table `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `fk_cita` FOREIGN KEY (`cita`) REFERENCES `cita` (`id_cita`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_paciente` FOREIGN KEY (`paciente`) REFERENCES `paciente` (`id_paciente`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
