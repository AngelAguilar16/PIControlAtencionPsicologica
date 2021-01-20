-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-01-2021 a las 21:29:52
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `dbdif`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caso`
--

CREATE TABLE `caso` (
  `id_caso` smallint(5) UNSIGNED NOT NULL,
  `fecha_apertura` date DEFAULT NULL,
  `descripcion_general` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `caso`
--

INSERT INTO `caso` (`id_caso`, `fecha_apertura`, `descripcion_general`, `estado`) VALUES
(1, '2021-01-15', 'a', 1),
(2, '2021-01-20', 'dadadaa', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cita`
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

--
-- Volcado de datos para la tabla `cita`
--

INSERT INTO `cita` (`id_cita`, `fecha`, `hora`, `paciente`, `usuario`, `visible`, `asistio`) VALUES
(1, '2021-01-06', '12:00:00', 1, 2, 1, 0),
(2, '0000-00-00', '16:30:00', 2, 3, 1, 0),
(3, '0000-00-00', '17:23:00', 3, 4, 1, 0),
(4, '0000-00-00', '19:15:00', 3, 4, 1, 0),
(5, '0000-00-00', '19:16:00', 4, 4, 1, 0),
(6, '0000-00-00', '19:17:00', 5, 4, 1, 0),
(7, '0000-00-00', '19:17:00', 3, 4, 1, 0),
(8, '0000-00-00', '19:31:00', 5, 4, 1, 0),
(9, '0000-00-00', '19:31:00', 4, 4, 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consulta`
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

--
-- Volcado de datos para la tabla `consulta`
--

INSERT INTO `consulta` (`id_consulta`, `usuario`, `cita`, `paciente`, `fecha`, `hora`, `motivo_atencion`, `notas_sesion`, `tipo_consulta`, `tratamiento`) VALUES
(1, 2, 1, 1, '2021-01-06', '12:30:00', 'Depresión', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse mattis, ante vel pretium fringilla, nunc libero porta eros, et porttitor ipsum urna vitae metus. Proin ullamcorper, risus interdum dignissim fringilla, turpis urna gravida mi, et malesuada arcu arcu sed enim. Curabitur gravida vestibulum tellus, sit amet sodales est bibendum quis. Sed vel erat ut magna tristique congue. Mauris malesuada luctus nisi, ac viverra purus finibus ut. Ut tincidunt maximus lacus, sed malesuada odio lob', 'Psicología', 'Decir me amo 3 veces a las 3:00 a.m. con las luces apagadas'),
(2, 3, 2, 2, '2021-01-18', '17:15:06', 'aaaaaaaaaaaa', 'sdasdasdas', 'Peritaje', NULL),
(3, 4, 3, 3, '2021-01-19', '15:24:09', 'aaaaaaaaaaaa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'Peritaje', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paciente`
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
  `caso` smallint(5) UNSIGNED NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `paciente`
--

INSERT INTO `paciente` (`id_paciente`, `usuario`, `fecha_registro`, `nombres`, `ap`, `am`, `menor_de_edad`, `telefono`, `estado`, `municipio`, `domicilio`, `sexo`, `fecha_nacimiento`, `estado_civil`, `escolaridad`, `ocupacion`, `caso`) VALUES
(1, 2, '2021-01-04', 'Antonieta', 'Martinez', 'Torres', 0, '3141312334', 'Manzanillo', 'Colima', 'Barrio 5', 'Femenino', '1999-12-25', 'Soltera', 'Universidad', 'Estudiante', 1),
(2, 3, '2021-01-18', 'adada', 'sdasdasd', 'asdasdas', 0, 'dasdasdas', 'Colima', 'Manzanillo', 'asdasdas', 'Masculino', '0000-00-00', '', '', '', 1),
(3, 4, '2021-01-19', 'a', 'ad', 'afd', 0, '12354221', 'Colima', 'Manzanillo', '', 'Masculino', '0000-00-00', '', '', '', 1),
(4, 4, '2021-01-19', 'DAniel', 'sadasd', 'lalala', 0, '12312512', 'Colima', 'Manzanillo', '', 'Masculino', '0000-00-00', '', '', '', 2),
(5, 4, '2021-01-19', 'dddddddddd', 'aaaaada', 'dada', 0, '123123', 'Colima', 'Manzanillo', '', 'Masculino', '0000-00-00', '', '', '', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
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
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombres`, `ap`, `am`, `correo`, `password`, `tipo_usuario`) VALUES
(1, 'Luis', 'a', 'e', 'lmartinez@hotmail.com', '1234', 'Psicología'),
(2, 'hola', 'a', 'o', 'hola', '1234', 'Psicología'),
(3, 'Pepe', 'e', 'a', 'elpepe', '1234', 'Peritaje'),
(4, 'dododo', 'dadada', 'daede', 'dada', '123', 'Peritaje');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `caso`
--
ALTER TABLE `caso`
  ADD PRIMARY KEY (`id_caso`);

--
-- Indices de la tabla `cita`
--
ALTER TABLE `cita`
  ADD PRIMARY KEY (`id_cita`),
  ADD KEY `fk_usuarioC_idx` (`usuario`),
  ADD KEY `fk_pacienteC_idx` (`paciente`);

--
-- Indices de la tabla `consulta`
--
ALTER TABLE `consulta`
  ADD PRIMARY KEY (`id_consulta`),
  ADD KEY `fk_usuario_idx` (`usuario`),
  ADD KEY `fk_paciente_idx` (`paciente`),
  ADD KEY `fk_cita_idx` (`cita`);

--
-- Indices de la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`id_paciente`),
  ADD KEY `fk_usuarioP_idx` (`usuario`),
  ADD KEY `fk_caso_idx` (`caso`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cita`
--
ALTER TABLE `cita`
  ADD CONSTRAINT `fk_pacienteC` FOREIGN KEY (`paciente`) REFERENCES `paciente` (`id_paciente`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_usuarioC` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `fk_cita` FOREIGN KEY (`cita`) REFERENCES `cita` (`id_cita`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_paciente` FOREIGN KEY (`paciente`) REFERENCES `paciente` (`id_paciente`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD CONSTRAINT `fk_caso` FOREIGN KEY (`caso`) REFERENCES `caso` (`id_caso`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_usuarioP` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
