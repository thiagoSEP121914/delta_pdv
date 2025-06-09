CREATE TABLE `clientes` (
  `ID_Cliente` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) DEFAULT NULL,
  `Cpf` varchar(11) DEFAULT NULL,
  `Telefone` varchar(11) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Data_Criacao` date DEFAULT NULL,
  `Data_Atualizacao` date DEFAULT NULL,
  PRIMARY KEY (`ID_Cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;