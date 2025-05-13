#🧾 Delta PDV
Sistema de Ponto de Venda (PDV) moderno e intuitivo desenvolvido em JavaFX com arquitetura MVC. Ideal para gerenciamento de vendas, produtos e clientes em comércios de pequeno a médio porte.

✨ Funcionalidades
📦 Gerenciamento de produtos e categorias

🧍 Cadastro de clientes

🛒 Tela de vendas com:

Adição de produtos via clique

Cálculo automático de subtotal, desconto e total

Seleção de forma de pagamento (Cartão, Dinheiro, Pix)

📊 Interface gráfica moderna com CSS personalizado

🧠 Navegação entre telas via utilitário ScreenLoader

📷 Capturas de tela
<!-- Substitua pelo caminho correto ou adicione ao projeto -->

📁 Estrutura do Projeto
bash
Copiar
Editar
src/
├── main/
│   ├── java/
│   │   └── org/example/delta_pdv/
│   │       ├── gui/
│   │       │   ├── controllers/
│   │       │   │   ├── PdvController.java
│   │       │   │   ├── DashboardController.java
│   │       │   │   └── ...
│   │       │   └── utils/
│   │       │       └── ScreenLoader.java
│   ├── resources/
│   │   └── org/example/delta_pdv/
│   │       ├── pdv.fxml
│   │       ├── produtoCard.fxml
│   │       ├── style/
│   │       │   ├── pdv.css
│   │       │   └── card.css
🛠 Tecnologias Utilizadas
Java 17+

JavaFX

FXML

CSS (para estilização personalizada)

Scene Builder (opcional para layout visual)

✅ Como Executar
Clone este repositório:

bash
Copiar
Editar
git clone https://github.com/seu-usuario/delta-pdv.git
Importe o projeto em sua IDE (IntelliJ, Eclipse, etc.)

Execute a classe principal Main.java

📌 TODO
Integração com banco de dados (SQLite/PostgreSQL)

Autenticação de usuários

Relatórios de vendas

Geração de recibos

🧑‍💻 Autor
Desenvolvido com  por Thiago
