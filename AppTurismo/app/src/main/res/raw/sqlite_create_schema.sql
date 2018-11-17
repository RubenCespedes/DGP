-- Capa de Usuarios
--drop table if exists usuario;
--create table usuario(
--    nombre_usuario varchar primary key,
--    contraseña varchar not null,
--    nombre varchar not null,
--    apellido varchar not null,
--    correo varchar not null
--);
--
--drop table if exists gestor;
--create table gestor(
--    nombre_usuario varchar primary key,
--
--    foreign key(nombre_usuario)
--        references usuario(nombre_usuario)
--);
--
--drop table if exists turista;
--create table turista(
--    nombre_turista varchar not null primary key,
--    idioma varchar not null,
--    formato_informacion varchar not null default 'audio' check(
--        formato_informacion in ('texto', 'audio', 'video')
--    ),
--
--    foreign key(nombre_turista)
--        references usuario(nombre_usuario)
--);
--
--drop table if exists sugerencias;
--create table sugerencias(
--    nombre varchar primary key,
--    descripcion varchar not null default 'n/a',
--    direccion varchar not null default 'n/a',
--    tipo varchar not null
--);
--
--drop table if exists sugiere;
--create table sugiere(
--    nombre_punto_de_interes varchar primary key,
--    nombre_usuario varchar,
--
--    foreign key(nombre_punto_de_interes)
--        references punto_de_interes_sugerido(nombre_punto_de_interes),
--    foreign key(nombre_usuario)
--        references usuario(nombre_usuario)
--);

-- Capa de Rutas
drop table if exists ruta;
create table ruta(
    nombre varchar primary key,
    categoria varchar not null default 'n/a' check(
        categoria in ('cultura', 'gastronomia', 'ocio', 'n/a')
    ),
    nivel_coste real not null default 0.0 check(
        nivel_coste >= 0.0
    ),
    nivel_accesibilidad real not null default 0.0 check(
        nivel_accesibilidad between 0.0 and 5.0
    ),
    imagen varchar
);

drop table if exists punto_interes;
create table punto_interes(
    nombre varchar primary key,
    lng real,
    lat real,
    url varchar not null default 'n/a',
    texto varchar not null default 'n/a',
    direccion varchar not null default 'n/a',
    horario varchar not null default 'n/a',
    precio real not null default 0.0 check(
        precio >= 0.0
    ),
    valoracion real not null default 0.0 check(
        valoracion between 0.0 and 5.0
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
        references punto_interes(nombre)
);

--drop table if exists conecta_con;
--create table conecta_con(
--    punto_de_interesA varchar not null,
--    punto_de_interesB varchar not null,
--    grado_accesibilidad real not null default 0.0 check(
--        grado_accesibilidad between 0.0 and 5.0
--    ),
--    trayecto varchar not null,
--
--    primary key (punto_de_interesA, punto_de_interesB),
--    check(
--        punto_de_interesA != punto_de_interesB
--    ),
--    foreign key(punto_de_interesA)
--        references punto_de_interes(nombre),
--    foreign key(punto_de_interesB)
--        references punto_de_interes(nombre)
--);
