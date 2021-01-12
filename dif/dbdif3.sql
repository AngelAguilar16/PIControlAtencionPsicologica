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

--
-- Table structure for table `cita`
--

CREATE TABLE `cita` (
  `id_cita` smallint(5) UNSIGNED NOT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `paciente` varchar(50) DEFAULT NULL,
  `usuario` tinyint(3) UNSIGNED DEFAULT NULL,
  `asistio` tinyint(1) UNSIGNED DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `consulta`
--

CREATE TABLE `consulta` (
  `id_consulta` smallint(5) UNSIGNED NOT NULL,
  `usuario` tinyint(3) UNSIGNED NOT NULL,
  `cita` smallint(5) UNSIGNED DEFAULT NULL,
  `caso` smallint(5) UNSIGNED NOT NULL,
  `paciente` smallint(5) UNSIGNED NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `motivo_atencion` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `notas_sesion` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_consulta` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `tratamiento` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `paciente`
--

CREATE TABLE `paciente` (
  `id_paciente` smallint(5) UNSIGNED NOT NULL,
  `fecha_registro` date NOT NULL,
  `nombres` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `ap` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `am` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `menor_de_edad` tinyint(2) NOT NULL,
  `telefono` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `estado` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `municipio` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `domicilio` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sexo` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `estado_civil` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `escolaridad` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ocupacion` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `paciente`
--

INSERT INTO `paciente` (`id_paciente`, `fecha_registro`, `nombres`, `ap`, `am`, `menor_de_edad`, `telefono`, `estado`, `municipio`, `domicilio`, `sexo`, `fecha_nacimiento`, `estado_civil`, `escolaridad`, `ocupacion`) VALUES
(1, '2021-01-04', 'Antonieta', 'Martinez', 'Torres', 0, '3141312334', 'Manzanillo', 'Colima', 'Barrio 5', 'Femenino', '1999-12-25', 'Soltera', 'Universidad', 'Estudiante');

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
  `tipo_usuario` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombres`,`ap`,`am`, `correo`, `password`, `tipo_usuario`) VALUES
(1, 'Luis', 'a', 'e', 'lmartinez@hotmail.com', '1234', 'Psicología'),
(2, 'hola', 'a', 'o', 'hola', '1234', 'Psicología');

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
  ADD KEY `fk_usuarioC_idx` (`usuario`);

--
-- Indexes for table `consulta`
--
ALTER TABLE `consulta`
  ADD PRIMARY KEY (`id_consulta`),
  ADD KEY `fk_usuario_idx` (`usuario`),
  ADD KEY `fk_caso_idx` (`caso`),
  ADD KEY `fk_paciente_idx` (`paciente`),
  ADD KEY `fk_cita_idx` (`cita`);

--
-- Indexes for table `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`id_paciente`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cita`
--
ALTER TABLE `cita`
  ADD CONSTRAINT `fk_usuarioC` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON UPDATE CASCADE;

--
-- Constraints for table `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `fk_caso` FOREIGN KEY (`caso`) REFERENCES `caso` (`id_caso`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_cita` FOREIGN KEY (`cita`) REFERENCES `cita` (`id_cita`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_paciente` FOREIGN KEY (`paciente`) REFERENCES `paciente` (`id_paciente`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
