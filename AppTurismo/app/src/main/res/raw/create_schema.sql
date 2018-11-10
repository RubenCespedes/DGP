pragma foreign_keys=on;

begin transaction;

drop table if exists usuario;
create table usuario(
    nombre_usuario varchar primary key,
    contraseña varchar not null,
    nombre varchar not null,
    apellido varchar not null,
    correo varchar not null
) without rowid;

drop table if exists gestor;
create table gestor(
    nombre_usuario varchar primary key,

    foreign key(nombre_usuario)
        references usuario(nombre_usuario)
) without rowid;

drop table if exists turista;
create table turista(
    nombre_turista varchar not null primary key,
    idioma varchar not null,
    formato_informacion varchar not null check(
        formato_informacion in ('texto', 'audio', 'video')
    ),

    foreign key(nombre_turista)
        references usuario(nombre_usuario)
);

drop table if exists sugerencias;
create table sugerencias(
    nombre varchar primary key,
    descripcion varchar not null,
    direccion varchar not null,
    tipo varchar not null
);

drop table if exists sugiere;
create table sugiere(
    nombre_punto_de_interes varchar primary key,
    nombre_usuario varchar,

    foreign key(nombre_punto_de_interes)
        references punto_de_interes_sugerido(nombre_punto_de_interes),
    foreign key(nombre_usuario)
        references usuario(nombre_usuario)
);

drop table if exists ruta;
create table ruta(
    nombre varchar primary key,
    imagen varchar,
    nivel_coste integer not null check(
        nivel_coste >= 0
    ),
    nivel_accesibilidad integer not null check(
        nivel_accesibilidad between 0 and 5
    )
);

drop table if exists punto_interes;
create table punto_interes(
    nombre varchar primary key,
    horario varchar not null,
    precio integer not null check(
        precio >= 0
    ),
    url varchar not null,
    texto varchar not null,
    direccion varchar not null,
    valoracion integer not null check(
        valoracion between 0 and 5
    ),
    audio varchar,
    imagen varchar,
    video varchar
);

drop table if exists contiene;
create table contiene(
    ruta varchar not null,
    punto_de_interes varchar not null,

    primary key(ruta, punto_de_interes),
    foreign key(ruta)
        references ruta(nombre),
    foreign key(punto_de_interes)
        references punto_de_interes(nombre)
);

drop table if exists conecta_con;
create table conecta_con(
    punto_de_interesA varchar not null,
    punto_de_interesB varchar not null,
    grado_accesibilidad integer not null check(
        grado_accesibilidad between 0 and 5
    ),
    trayecto varchar not null,

    primary key (punto_de_interesA, punto_de_interesB),
    check(
        punto_de_interesA != punto_de_interesB
    ),
    foreign key(punto_de_interesA) 
        references punto_de_interes(nombre),
    foreign key(punto_de_interesB)
        references punto_de_interes(nombre)
);

commit;