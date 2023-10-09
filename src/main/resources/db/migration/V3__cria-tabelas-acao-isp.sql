CREATE TABLE `incentivos`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `nome` VARCHAR(255) NULL
);

CREATE TABLE `categorias`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `nome` VARCHAR(255) NULL
);

CREATE TABLE `areas`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `nome` VARCHAR(255) NULL
);

CREATE TABLE `acoes_isp`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `nome_acao` VARCHAR(255) NULL,
    `descricao` TEXT NULL,
    `abrangencia` VARCHAR(255) NULL,
    `tipo_investimento` VARCHAR(255) NULL,
    `qtde_pessoas_impactadas` INT NULL,
    `aporte_inicial` DECIMAL(10, 2) NULL,
    `status` VARCHAR(255) NULL,
    `categoria_id` INT UNSIGNED NULL,
    `area_id` INT UNSIGNED NULL,
    `organizacao_id` INT UNSIGNED NULL,
    `incentivo_id` INT UNSIGNED NULL,
    `contrato_id` INT UNSIGNED NULL,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(categoria_id) REFERENCES categorias(id),
    FOREIGN KEY(area_id) REFERENCES areas(id),
    FOREIGN KEY(incentivo_id) REFERENCES incentivos(id),
    FOREIGN KEY(contrato_id) REFERENCES contratos(id)
);

CREATE TABLE `locais_impactados`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `local` VARCHAR(255) NULL,
    `isp_id` INT UNSIGNED NULL,
    `voluntariado_id` INT UNSIGNED NULL,
    FOREIGN KEY(isp_id) REFERENCES acoes_isp(id),
    FOREIGN KEY(voluntariado_id) REFERENCES acoes_voluntariado(id)
);

CREATE TABLE `aportes`(
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `valor` DECIMAL(10,2) NULL,
    `isp_id` INT UNSIGNED NULL,
    FOREIGN KEY(isp_id) REFERENCES acoes_isp(id)
);