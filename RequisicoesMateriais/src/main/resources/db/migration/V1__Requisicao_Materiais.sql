CREATE TABLE colaborador(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    ativo BOOLEAN NOT NULL,
    data_cadastro DATETIME
);

CREATE TABLE requisicao(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    colaborador_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    data_criacao DATETIME NOT NULL,

    FOREIGN KEY (colaborador_id) REFERENCES colaborador(id)
);

CREATE TABLE material(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(300),
    preco_unitario DECIMAL(10,2) NOT NULL,
    quantidade_estoque INT NOT NULL,
    estoque_minimo INT,
    ativo BOOLEAN
);

CREATE TABLE item_requisicao(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    requisicao_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    preco_unitario DECIMAL(10,2),
    preco_total DECIMAL(10,2),
    quantidade INT NOT NULL,

    FOREIGN KEY (requisicao_id) REFERENCES requisicao(id),
    FOREIGN KEY (material_id) REFERENCES material(id)
);
