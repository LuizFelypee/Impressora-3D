/**
* Impressora 3D
* @author Luiz Felipe
*/

create database dbimpressora3D;

show databases;

use dbimpressora3D;

select dbimpressora3D;

drop database dbimpressora3D;

create table usuarios ( /* Tabela com campos*/
	iduser int primary key auto_increment,
	nome varchar(30) not null, /*nome com 30 caracter e obrigatorio*/
	login varchar(20) not null unique, /*unique = nao aceita valores duplicados*/
	senha varchar(250) not null,
    perfil varchar(5) not null
);

-- md5() gera um hash (criptografia)
insert into usuarios(nome,login,senha,perfil) values ('Administrador','admin',md5('admin'),'admin');

describe usuarios;

select * from Clientes; /*Ver tabela do usuario*/
describe Clientes;
select * from usuarios;

-- busca avançada por nome
select * from usuarios where nome like 'a%' order by nome;

-- autenticar um usuario
select * from usuarios where login = 'admin' and senha = md5('admin');

-- fazendo alteração de usuario para perfil
update usuarios set nome='Administrador', login='admin', senha= md5('admin'), perfil='admin' where iduser=1;
update usuarios set perfil='admin' where iduser='1';


create table Clientes (
	idcli int primary key auto_increment,
	nome varchar(30) not null, /*nome com 30 caracter e obrigatorio*/
    fone varchar (11) not null,
    email varchar(200) not null,
    cep varchar(8) not null,
	endereco varchar(200) not null,
    numeroendereco varchar(5) not null,
    complemento varchar(50),
    bairro varchar(50) not null,
	cidade varchar(50) not null,
    uf varchar(11) not null

	
    
);

drop table usuarios;
describe Clientes;
show tables;
select * from Clientes ;


create table fornecedores (
	idcli int primary key auto_increment,
	nome varchar(30) not null, /*nome com 30 caracter e obrigatorio*/
    fone varchar (11) not null,
    email varchar(200) not null,
    cep varchar(8) not null,
	endereco varchar(200) not null,
    numeroendereco varchar(5) not null,
    complemento varchar(50),
    bairro varchar(50) not null,
	cidade varchar(50) not null,
    cnpj varchar(50) not null unique,
    uf varchar(11) not null

	
    
);

describe fornecedores;
show tables;
select * from fornecedores ;




/* Relacionamento de Tabelas 1 - N (um para muitos)*/
-- decimal (10,2) tipo de dados (números não inteiros)
-- (10,2) (digitos, formação de casas decimais)
-- (timestamp default current_timestamp) (data e hora automático)
-- date (tipo de dados para trabalhar com datas)
-- foreign key(idcli) 
-- references clientes(idcli)
create table servicos(
	os int primary key auto_increment,
    idcli int not null,
    defeito varchar(200) not null,
    diagnostico varchar(200),
    statusOS varchar(40) not null,
    valor decimal(10,2),
    dataOS timestamp default current_timestamp,
    dataOSsaida date,
    marca varchar(150) not null,
    modelo varchar(200) not null,
    idtec int,
    usuario varchar (30) not null,
    foreign key(idcli) references Clientes(idcli),
    foreign key(idtec) references tecnicos(idtec)
);
drop table servicos;
describe servicos;
select * from servicos ;

update servicos set defeito='desliga sozinho', diagnostico='bateria', statusOS='AGUARDANDO PEÇAS', valor=500.00 , dataOSsaida=20230612 , marca='Ender 3' , modelo='V2 Creality' , idtec=1 , usuario='adiministrador' where os= 10;

 
 insert into servicos (idcli,defeito,diagnostico,statusOS,valor,dataOSsaida,marca,modelo,idtec,usuario)
 values (2,'Não liga','fonte','PRONTO',100,20230522,'Ender','V2 Creality',1,'luiz');
 
 
 
 
-- formatando a data (Brasil)
-- %d/%m/%Y  dd/mm/aaaa || %d/%m/%y dd/mm/aa || %H %i HH:mm
select os, defeito, statusOS, idcli, marca, modelo, date_format(dataOS, '%d/%m/%Y - %H:%i') as data_entrada, date_format(dataOSsaida,'%d/%m/%Y') as data_saida, diagnostico, valor, idtec, usuario from servicos;

delete from servicos where os = 2;

create table tecnicos ( /* Tabela com campos*/
	idtec int primary key auto_increment,
	nome varchar(30) not null, /*nome com 30 caracter e obrigatorio*/
	fone varchar (15) unique not null
);

select * from tecnicos;
insert into tecnicos (nome,fone) values ('zezinho', '111111111');
insert into tecnicos (nome,fone) values ('juca', '2222222222');

/************* Relatórios (select) **************/

-- Faturamento
select sum(valor) as faturamento from servicos;

-- Clientes
select nome,fone,CEP,email from Clientes order by nome;

-- Tecnicos
select nome,fone from tecnicos order by nome;

/** pendências **/

-- formato 1 (aguardando mecânico / técnico)
select * from servicos where statusOS = 'Aguardando tecnico';

-- formato 2 (aguardando mecânico / técnico)
select os,modelo,defeito,
date_format(dataOS,'%d/%m/%Y - %H:%i') as data_entrada,
idcli from servicos
where statusOS = 'Aguardando tecnico';

-- formato 3 final (aguardando mecânico / técnico)
select servicos.os,servicos.modelo,servicos.defeito,
date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada,
Clientes.nome as Cliente, Clientes.fone
from servicos
inner join Clientes
on servicos.idcli = Clientes.idcli
where statusOS = 'Aguardando tecnico';

-- relatório de serviços entregues
select servicos.os,servicos.modelo,servicos.defeito,
servicos.diagnostico,tecnicos.nome as tecnico,
servicos.valor,
date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada,
date_format(servicos.dataOSsaida,'%d/%m/%Y') as data_saida,
Clientes.nome as Cliente, Clientes.fone
from servicos
inner join Clientes
on servicos.idcli = Clientes.idcli
inner join tecnicos
on servicos.idtec = tecnicos.idtec
where statusOS = 'Entregue';

/** Impressão da OS **/
select servicos.os,date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada,
servicos.usuario as usuário,
servicos.defeito,servicos.statusOS as status_OS,servicos.diagnostico,
servicos.valor,
tecnicos.nome as tecnicos, tecnicos.fone,
date_format(servicos.dataOSsaida,'%d/%m/%Y') as data_saida,
Clientes.nome as Cliente, Clientes.fone
from servicos
inner join Clientes
on servicos.idcli = Clientes.idcli
inner join tecnicos
on servicos.idtec = tecnicos.idtec
where os = 6;

-- Obtendo o valor do PK(id) do último registro adicionado
select max(os) from servicos;
select max(idcli) from Clientes;