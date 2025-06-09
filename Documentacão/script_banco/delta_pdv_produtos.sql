CREATE TABLE `produtos` (
  `ID_Produto` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) DEFAULT NULL,
  `Descricao` text,
  `Preco_Unitario` decimal(10,2) DEFAULT NULL,
  `Quantidade_Estoque` int DEFAULT NULL,
  `Categoria` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_Produto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
