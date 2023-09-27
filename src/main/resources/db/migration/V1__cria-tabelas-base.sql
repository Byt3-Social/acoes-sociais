CREATE TABLE `usuarios`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `segmentos`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(255) NOT NULL
);

CREATE TABLE `interesses`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `usuario_id` INT UNSIGNED NOT NULL,
    `segmento_id` INT UNSIGNED NOT NULL,
    FOREIGN KEY(`usuario_id`) REFERENCES `usuarios`(`id`),
    FOREIGN KEY(`segmento_id`) REFERENCES `segmentos`(`id`)
);

CREATE TABLE `campanhas`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `meta` INT NULL,
    `meta_descricao` VARCHAR(255) NULL,
    `url` VARCHAR(255) NOT NULL
);

CREATE TABLE `contratos`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome_arquivo` VARCHAR(255) NOT NULL,
    `assinatura` VARCHAR(255) NULL,
    `campanha_id` INT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(`campanha_id`) REFERENCES `campanhas`(`id`)
);

CREATE TABLE `organizacoes`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `cnpj` VARCHAR(255) NOT NULL,
    `nome_empresarial` VARCHAR(255) NOT NULL,
    `cadastro_id` INT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `acoes_voluntariado`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome_acao` VARCHAR(255) NULL,
    `nivel` VARCHAR(255) NULL,
    `fase` VARCHAR(255) NULL,
    `formato` VARCHAR(255) NULL,
    `data_inicio` DATE NULL,
    `data_termino` DATE NULL,
    `tipo` VARCHAR(255) NULL,
    `horario` TIMESTAMP NULL,
    `local` VARCHAR(255) NULL,
    `informacoes_adicionais` TEXT NULL,
    `imagem` VARCHAR(255) NULL,
    `vagas` INT NULL,
    `campanha_id` INT UNSIGNED NULL,
    `segmento_id` INT UNSIGNED NULL,
    `usuario_id` INT UNSIGNED NOT NULL,
    `organizacao_id` INT UNSIGNED NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(`campanha_id`) REFERENCES `campanhas`(`id`),
    FOREIGN KEY(`segmento_id`) REFERENCES `segmentos`(`id`),
    FOREIGN KEY(`usuario_id`) REFERENCES `usuarios`(`id`),
    FOREIGN KEY(`organizacao_id`) REFERENCES `organizacoes`(`id`)
);

CREATE TABLE `arquivos`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome_arquivo` VARCHAR(255) NOT NULL,
    `acao_id` INT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(`acao_id`) REFERENCES `acoes_voluntariado`(`id`)
);

CREATE TABLE `inscricoes`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `assinatura_digital` VARCHAR(255) NULL,
    `status` VARCHAR(255) NULL,
    `usuario_id` INT UNSIGNED NOT NULL,
    `acao_id` INT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(`usuario_id`) REFERENCES `usuarios`(`id`),
    FOREIGN KEY(`acao_id`) REFERENCES `acoes_voluntariado`(`id`)
);

CREATE TABLE `checkins`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `inicio` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `termino` TIMESTAMP NULL,
    `duracao` INT NULL,
    `tipo_checkin` VARCHAR(255) NOT NULL,
    `inscricao_id` INT UNSIGNED NOT NULL,
    FOREIGN KEY(`inscricao_id`) REFERENCES `inscricoes`(`id`)
);

