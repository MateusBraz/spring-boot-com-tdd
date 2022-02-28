INSERT INTO pessoa (nome, cpf) VALUES ('João', '93095361017');
INSERT INTO pessoa (nome, cpf) VALUES ('Marcos', '47855639071');
INSERT INTO pessoa (nome, cpf) VALUES ('Pedro', '93131180099');
INSERT INTO pessoa (nome, cpf) VALUES ('Maurício', '87247434023');
INSERT INTO pessoa (nome, cpf) VALUES ('Bernardo', '84900228010');

INSERT INTO telefone (ddd, numero, id_pessoa) VALUES ('91', '985305340', (SELECT id FROM pessoa WHERE cpf = '93095361017'));
INSERT INTO telefone (ddd, numero, id_pessoa) VALUES ('91', '982431444', (SELECT id FROM pessoa WHERE cpf = '47855639071'));
INSERT INTO telefone (ddd, numero, id_pessoa) VALUES ('91', '988657657', (SELECT id FROM pessoa WHERE cpf = '93131180099'));
INSERT INTO telefone (ddd, numero, id_pessoa) VALUES ('91', '975923309', (SELECT id FROM pessoa WHERE cpf = '87247434023'));
INSERT INTO telefone (ddd, numero, id_pessoa) VALUES ('91', '979751356', (SELECT id FROM pessoa WHERE cpf = '84900228010'));

INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, id_pessoa) VALUES ('Rua 1', 156, '----', 'Castanheira', 'Belém', 'PA', (SELECT id FROM pessoa WHERE cpf = '93095361017'));
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, id_pessoa) VALUES ('Rua 2', 284, '----', 'Guamá', 'Belém', 'PA', (SELECT id FROM pessoa WHERE cpf = '47855639071'));
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, id_pessoa) VALUES ('Rua 3', 874, '----', 'Tapanã', 'Belém', 'PA', (SELECT id FROM pessoa WHERE cpf = '93131180099'));
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, id_pessoa) VALUES ('Rua 4', 345, '----', 'Cabanagem', 'Belém', 'PA', (SELECT id FROM pessoa WHERE cpf = '87247434023'));
INSERT INTO endereco (logradouro, numero, complemento, bairro, cidade, estado, id_pessoa) VALUES ('Rua 5', 48, '----', 'Mangueirão', 'Belém', 'PA', (SELECT id FROM pessoa WHERE cpf = '84900228010'));
