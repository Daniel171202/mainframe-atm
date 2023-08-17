/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : atm

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 16/08/2023 23:52:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for historico
-- ----------------------------
DROP TABLE IF EXISTS `historico`;
CREATE TABLE `historico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int DEFAULT NULL,
  `tipo_operacion` varchar(50) NOT NULL,
  `cantidad` double DEFAULT NULL,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `historico_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of historico
-- ----------------------------
BEGIN;
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (1, 1, 'Depósito', 1000, '2023-08-13 12:10:29');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (2, 2, 'depósito', 2500, '2023-08-13 12:10:29');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (3, 3, 'depósito', 500, '2023-08-13 12:10:29');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (4, 4, 'depósito', 750, '2023-08-13 12:10:29');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (5, 5, 'depósito', 3000, '2023-08-13 12:10:29');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (6, 1, 'Transferencia', -200, '2023-08-13 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (7, 1, 'Transferencia', -100, '2023-08-13 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (8, 1, 'Deposito', 1200, '2023-08-13 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (9, 1, 'Deposito', 100, '2023-08-13 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (10, 1, 'Retiro..', -100, '2023-08-13 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (11, 1, 'Transferencia', -600, '2023-08-13 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (12, 1, 'Deposito', 3000, '2023-08-13 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (15, 1, 'Depósito', 100, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (16, 1, 'Retiro', -100, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (17, 1, 'Retiro', -100, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (18, 1, 'Retiro', -1000, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (19, 1, 'Retiro', -1000, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (20, 1, 'Retiro', -100, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (21, 1, 'Depósito', 4000, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (22, 1, 'Retiro', -100, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (23, 1, 'Depósito', 100, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (24, 1, 'Retiro', -100, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (25, 1, 'Transferencia enviada', -1000, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (26, 1, 'Transferencia recibida', 1000, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (27, 1, 'Transferencia enviada', -1000, '2023-08-16 00:00:00');
INSERT INTO `historico` (`id`, `usuario_id`, `tipo_operacion`, `cantidad`, `fecha`) VALUES (28, 2, 'Transferencia recibida', 1000, '2023-08-16 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for usuarios
-- ----------------------------
DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `pin` int NOT NULL,
  `saldo` double NOT NULL,
  `ci` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of usuarios
-- ----------------------------
BEGIN;
INSERT INTO `usuarios` (`id`, `nombre`, `pin`, `saldo`, `ci`) VALUES (1, 'Daniel Aldazosa', 1234, 4900, '6959943');
INSERT INTO `usuarios` (`id`, `nombre`, `pin`, `saldo`, `ci`) VALUES (2, 'Ana Ramirez', 5678, 4700, '1234567');
INSERT INTO `usuarios` (`id`, `nombre`, `pin`, `saldo`, `ci`) VALUES (3, 'Carlos Gomez', 9012, 500, '7654321');
INSERT INTO `usuarios` (`id`, `nombre`, `pin`, `saldo`, `ci`) VALUES (4, 'Marta Torres', 3456, 750, '2345678');
INSERT INTO `usuarios` (`id`, `nombre`, `pin`, `saldo`, `ci`) VALUES (5, 'Luisa Fernandez', 7890, 3000, '8765432');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
