create database pneu;

use pneu;


create table cliente(

    idCliente int primary key auto_increment,

    nomeCliente varchar(40),

    CNPJ varchar(14)

);

create table usuario(

    idLogin int primary key auto_increment,

    nomeUsuario varchar(30),

    CargoUsuario varchar(30),

    nickUsuario varchar(30),

    senha varchar (30),
    
    fkCliente int,
    foreign key (fkCliente) references cliente (idCliente)
);


create table rodovia(

    idRodovia int primary key auto_increment,

    nomeRodovia varchar (50),

   fkCliente int,
   foreign key(fkCliente) references cliente(idCliente)

);


create table Ponto(

    idPonto int primary key auto_increment,

    Sensor int,

    nomePonto varchar(10),

    Quilometro int,

    tipo char (1),
    check (char = "e" or char = "s"),

    fkRodovia int,
    foreign key(fkRodovia) references rodovia(idRodovia)
);

create table Registro(

     primary key (idRegistro, fkPonto),

    idRegistro int,
    

    QtdCarros int,

    dataRegistro date,

    horaRegistro time,

    fkPonto int,
    foreign key(fkPonto) references Registro (idRegistro)
);