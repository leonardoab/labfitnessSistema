CREATE TABLE estado (
  id_estado INTEGER NOT NULL AUTO_INCREMENT,
  dsc_sigla VARCHAR(255) NULL,
  dsc_estado VARCHAR(255) NULL,
  PRIMARY KEY(id_estado)
);

CREATE TABLE met (
  id_met INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  dsc_met VARCHAR(255) NULL,
  PRIMARY KEY(id_met)
);

CREATE TABLE grupamento (
  id_grupamento INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  dsc_grupamento VARCHAR(255) NULL,
  PRIMARY KEY(id_grupamento)
);

CREATE TABLE estado_civil (
  id_estado_civil INTEGER NOT NULL AUTO_INCREMENT,
  dsc_estado_civil VARCHAR(255) NULL,
  PRIMARY KEY(id_estado_civil)
);

CREATE TABLE unidade (
  id_unidade INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  dsc_unidade INTEGER UNSIGNED NULL,
  PRIMARY KEY(id_unidade)
);

CREATE TABLE tipo_questionamento (
  id_tipo_questionamento INTEGER NOT NULL AUTO_INCREMENT,
  dsc_tipo_questionamento VARCHAR(255) NULL,
  PRIMARY KEY(id_tipo_questionamento)
);

CREATE TABLE tipo_medicao (
  id_tipo_medicao INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  dsc_tipo_medicao VARCHAR(255) NULL,
  PRIMARY KEY(id_tipo_medicao)
);

CREATE TABLE exercicio (
  id_exercicio INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  id_grupamento INTEGER UNSIGNED NULL,
  dsc_exercicio VARCHAR(255) NULL,
  PRIMARY KEY(id_exercicio),
  INDEX exercicio_FKIndex1(id_grupamento),
  FOREIGN KEY(id_grupamento)
    REFERENCES grupamento(id_grupamento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE questionamento (
  id_questionamento INTEGER NOT NULL AUTO_INCREMENT,
  id_tipo_questionamento INTEGER NOT NULL,
  dsc_questionamento VARCHAR(255) NULL,
  PRIMARY KEY(id_questionamento),
  INDEX questionamento_FKIndex1(id_tipo_questionamento),
  FOREIGN KEY(id_tipo_questionamento)
    REFERENCES tipo_questionamento(id_tipo_questionamento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE cidade (
  id_cidade INTEGER NOT NULL AUTO_INCREMENT,
  id_estado INTEGER NOT NULL,
  dsc_cidade VARCHAR(255) NULL,
  PRIMARY KEY(id_cidade),
  INDEX cidade_FKIndex1(id_estado),
  FOREIGN KEY(id_estado)
    REFERENCES estado(id_estado)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE medicao (
  id_medicao INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  id_tipo_medicao INTEGER UNSIGNED NOT NULL,
  dsc_medicao VARCHAR(255) NULL,
  id_unidade INTEGER UNSIGNED NULL,
  PRIMARY KEY(id_medicao),
  INDEX medicao_FKIndex1(id_tipo_medicao),
  INDEX medicao_FKIndex2(id_unidade),
  FOREIGN KEY(id_tipo_medicao)
    REFERENCES tipo_medicao(id_tipo_medicao)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(id_unidade)
    REFERENCES unidade(id_unidade)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE bairro (
  id_bairro INTEGER NOT NULL AUTO_INCREMENT,
  id_cidade INTEGER NOT NULL,
  dsc_bairro VARCHAR(255) NULL,
  PRIMARY KEY(id_bairro),
  INDEX bairro_FKIndex1(id_cidade),
  FOREIGN KEY(id_cidade)
    REFERENCES cidade(id_cidade)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE endereco (
  id_endereco INTEGER NOT NULL AUTO_INCREMENT,
  id_bairro INTEGER NOT NULL,
  dsc_endereco VARCHAR(255) NULL,
  PRIMARY KEY(id_endereco),
  INDEX endereco_FKIndex1(id_bairro),
  FOREIGN KEY(id_bairro)
    REFERENCES bairro(id_bairro)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE aluno (
  id_aluno INTEGER NOT NULL AUTO_INCREMENT,
  dt_cadastro DATE NOT NULL,
  dsc_nome VARCHAR(255) NULL,
  dt_nascimento DATE NULL,  
  dsc_email VARCHAR(255) NULL,
  flg_sexo BOOL NULL,
    PRIMARY KEY(id_aluno)  
);

CREATE TABLE telefone (
  id_telefone INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  dsc_telefone VARCHAR(255) NULL,
  id_aluno INTEGER NULL,
  PRIMARY KEY(id_telefone),
  INDEX telefone_FKIndex1(id_aluno),
  FOREIGN KEY(id_aluno)
    REFERENCES aluno(id_aluno)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE antropometria (
  id_antropometria INTEGER NOT NULL AUTO_INCREMENT,
  id_aluno INTEGER NULL,
  dt_realizacao DATE NULL,
  PRIMARY KEY(id_antropometria),
  INDEX antropometria_FKIndex1(id_aluno),
  FOREIGN KEY(id_aluno)
    REFERENCES aluno(id_aluno)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE anamnese (
  id_anamnese INTEGER NOT NULL AUTO_INCREMENT,
  id_aluno INTEGER NULL,
  dt_realizacao DATE NULL,
  PRIMARY KEY(id_anamnese),
  INDEX anamnese_FKIndex1(id_aluno),
  FOREIGN KEY(id_aluno)
    REFERENCES aluno(id_aluno)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE anamnese_questionamento (
  id_anamnese_questionamento INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  id_questionamento INTEGER NOT NULL,
  id_anamnese INTEGER NOT NULL,
  dsc_resposta VARCHAR(255) NULL,
  PRIMARY KEY(id_anamnese_questionamento),
  INDEX anamnese_historico_FKIndex1(id_anamnese),
  INDEX anamnese_questionamento_FKIndex2(id_questionamento),
  FOREIGN KEY(id_anamnese)
    REFERENCES anamnese(id_anamnese)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(id_questionamento)
    REFERENCES questionamento(id_questionamento)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE vodois (
  id_vodois INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  id_anamnese INTEGER NULL,
  dt_realizacao DATE NULL,
  num_freq_cardiaca INTEGER UNSIGNED NULL,
  num_pa_sistolica INTEGER UNSIGNED NULL,
  num_pa_diastolica INTEGER UNSIGNED NULL,
  num_zona_treinamento INTEGER UNSIGNED NULL,
  id_met INTEGER UNSIGNED NULL,
  vl_met FLOAT NULL,
  dsc_conclusao VARCHAR(255) NULL,
  PRIMARY KEY(id_vodois),
  INDEX vodois_FKIndex1(id_anamnese),
  INDEX vodois_FKIndex2(id_met),
  FOREIGN KEY(id_anamnese)
    REFERENCES anamnese(id_anamnese)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(id_met)
    REFERENCES met(id_met)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE ficha (
  id_ficha INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  id_aluno INTEGER NULL,
  dt_teste_carga DATE NULL,
  dt_prox_teste_carga DATE NULL,
  num_descanso INTEGER UNSIGNED NULL,
  num_duracao INTEGER UNSIGNED NULL,
  id_antropometria INTEGER NULL,
  dt_prox_antropometria DATE NULL,
  PRIMARY KEY(id_ficha),
  INDEX ficha_FKIndex1(id_aluno),
  INDEX ficha_FKIndex2(id_antropometria),
  FOREIGN KEY(id_aluno)
    REFERENCES aluno(id_aluno)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(id_antropometria)
    REFERENCES antropometria(id_antropometria)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE ficha_exercicio (
  id_ficha_exercicio INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  id_ficha INTEGER UNSIGNED NULL,
  num_aparelho INTEGER UNSIGNED NULL,
  id_exercicio INTEGER UNSIGNED NULL,
  dsc_execucao VARCHAR(255) NULL,
  num_serie INTEGER UNSIGNED NULL,
  num_repeticoes INTEGER UNSIGNED NULL,
  dsc_velocidade_execucao VARCHAR(255) NULL,
  PRIMARY KEY(id_ficha_exercicio),
  INDEX ficha_exercicio_FKIndex1(id_ficha),
  INDEX ficha_exercicio_FKIndex2(id_exercicio),
  FOREIGN KEY(id_ficha)
    REFERENCES ficha(id_ficha)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(id_exercicio)
    REFERENCES exercicio(id_exercicio)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE antropometria_unidade (
  id_antropometria_unidade INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  id_antropometria INTEGER NOT NULL,
  id_medicao INTEGER UNSIGNED NULL,
  vl_medicao FLOAT NULL,
  PRIMARY KEY(id_antropometria_unidade),
  INDEX antropometria_unidade_FKIndex1(id_antropometria),
  INDEX antropometria_unidade_FKIndex2(id_medicao),
  FOREIGN KEY(id_antropometria)
    REFERENCES antropometria(id_antropometria)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(id_medicao)
    REFERENCES medicao(id_medicao)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);


