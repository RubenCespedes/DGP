pragma foreign_keys=on;

drop table if exists usuario;
drop table if exists gestor;
drop table if exists turista;
drop table if exists sugerencias;
drop table if exists sugiere;
drop table if exists ruta;
drop table if exists punto_interes;
drop table if exists contiene;
drop table if exists conecta_con;

create table usuario(
   nombre_usuario varchar not null primary key,
   contraseña varchar not null,
   nombre varchar not null,
   apellido varchar not null,
   correo varchar not null
);

create table gestor(
   nombre_usuario varchar not null primary key,

   foreign key(nombre_usuario)
       references usuario(nom_bre_usuario)
);

create table turista(
   nombre_turista varchar not null not null primary key,
   idioma varchar not null,
   formato_informacion varchar not null default 'audio' check(
       formato_informacion in ('texto', 'audio', 'video')
   ),

   foreign key(nombre_turista)
       references usuario(nombre_usuario)
);



create table punto_interes(
    nombre varchar not null primary key,
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


create table sugerencias(
   nombre varchar not null primary key,
   descripcion varchar not null default 'n/a',
   direccion varchar not null default 'n/a',
   tipo varchar not null
);

create table sugiere(
   nombre_punto_de_interes varchar not null primary key,
   nombre_usuario varchar,

   foreign key(nombre_punto_de_interes)
       references punto_de_interes_sugerido(nombre_punto_de_interes),
   foreign key(nombre_usuario)
       references usuario(nombre_usuario)
);

create table ruta(
    nombre varchar not null primary key,
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



create table contiene(
    ruta varchar not null,
    punto_de_interes varchar not null,

    primary key(ruta, punto_de_interes),
    foreign key(ruta)
        references ruta(nombre),
    foreign key(punto_de_interes)
        references punto_interes(nombre)
);

create table conecta_con(
   punto_de_interesA varchar not null,
   punto_de_interesB varchar not null,
   grado_accesibilidad real not null default 0.0 check(
       grado_accesibilidad between 0.0 and 5.0
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
