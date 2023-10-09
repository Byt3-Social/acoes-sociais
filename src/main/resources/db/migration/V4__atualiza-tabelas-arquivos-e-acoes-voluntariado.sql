ALTER TABLE arquivos RENAME COLUMN acao_id TO voluntariado_id;
ALTER TABLE arquivos ADD COLUMN isp_id INT UNSIGNED NULL, ADD FOREIGN KEY(isp_id) REFERENCES acoes_isp(id);

ALTER TABLE acoes_voluntariado ADD COLUMN multiplicador INT NULL;
ALTER TABLE acoes_voluntariado ADD COLUMN sobre_organizacao TEXT NULL;
ALTER TABLE acoes_voluntariado ADD COLUMN sobre_acao TEXT NULL;