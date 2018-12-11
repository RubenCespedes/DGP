INSERT INTO ruta(nombre, descripcion, categoria, nivel_coste, nivel_accesibilidad, imagen)
     VALUES
     ('El legado árabe',
     'Recorrido por todas las construcciones y monumentos que los árabes (ziríes y nazaríes) dejaron en su asentamiento en Granada',
     'Cultura', 3.0, 3.0, 'legado_arabe'),
     ('Granada cristiana y judía',
     'Recorrido por los principales monasterios, catedrales y monumentos relacionados con el cristianismo en Granada',
     'Cultura', 1.0, 4.5, 'cristianos_judios'),
     ('Museos',
     'Recorrido por todos los museos de la ciudad. Desde el Parque de las Ciencias hasta el Centro Federico Garcia Lorca',
     'Cultura', 3.5, 3.0, 'parque_ciencias');

INSERT INTO punto_interes(nombre, lng, lat, texto, direccion, precio, audio, imagen, video)
     VALUES
     ('Alhambra', -3.588068, 37.176434,
     'Ciudad palatina medieval con salón del trono cubierto de yeserías y patios interiores porticados con fuentes',
     'Calle Real de la Alhambra, s/n, 18009 Granada', 59.00, NULL, 'alhambra', NULL),
     ('Patio de los leones', -3.589206, 37.177358,
     'Palacio morisco de la Alhambra y Patio de los Leones, con complejo hidráulico y la famosa fuente decorativa',
     'Calle Real de la Alhambra, s/n, 18009 Granada', 0.0, NULL, 'patio_leones', NULL),
     ('Palacios Nazaríes', -3.589740, 37.177440,
     'Palacios nazaríes de la Alhambra con patios, salas de audiencia y estancias realdes de intrincados disenios',
     'Calle Real de la Alhambra, s/n, 18009 Granada', 0.0, NULL, 'palacios_nazaries', NULL),
     ('Palacio de Carlos V', -3.589991, 37.176919,
     'Residencia del emperador, de estilo renacentista, con un gran patio interior y museos dentro de la Alhambra',
     'Calle Real de la Alhambra, s/n, 18009 Granada', 0.0, NULL, 'carlos_v', NULL),
     ('Generalife', -3.584605, 37.178704,
     'Antiguo palacio musulmán con elegantes jardines ornamentales, estanques, patios y flora exuberante',
     'Paseo del Generalife, 1A, 18009 Granada', 0.0, NULL, 'generalife', NULL);

INSERT INTO contiene(ruta, punto_de_interes)
    VALUES ('El legado árabe', 'Alhambra'),
    ('El legado árabe', 'Patio de los leones'),
    ('El legado árabe', 'Palacios Nazaríes'),
    ('El legado árabe', 'Palacio de Carlos V'),
    ('El legado árabe', 'Generalife');

INSERT INTO punto_interes(nombre, lng, lat, texto, direccion, precio, audio, imagen, video)
	VALUES
	('Cartuja de Granada', -3.599514, 37.192291,
	'Monasterio del siglo XVI de exterior ocre e interiores dorados de estilo barroco con tallas de mármol',
	'Paseo de Cartuja, s/n, 18011 Granada', 0.0, NULL, 'cartuja_granada', NULL),
	('Catedral de Granada', -3.599094, 37.176820,
	'Espectacular catedral renacentista con interior blanco y dorado, famosa por su capilla y cúpula con vidrieras',
	'Calle Gran Vía de Colón, 5, 18001 Granada', 0.0, NULL, 'catedral_granada', NULL),
	('Plaza Isabel La Católica', -3.597466, 37.175668,
	'Monumental plaza urbana con una escultura de bronce de la reina Isabel con Cristóbal Colón',
	'18009 Granada', 0.0, NULL, 'plaza_isabel', NULL),
	('Museo Sefardí', -3.594917, 37.175291,
	'Este museo privado recoge la historia de los judíos en Granada por medio de objetos y una visita guiada',
	'5 Placeta Berrocal, 18009 Granada', 0.0, NULL, 'museo_sefardi', NULL),
	('El Palacio de los Olvidados', -3.593786, 37.178511,
	'Museo en una monumental casa aristocrática con eventos, historia, artefactos y obras de arte judías',
	'Cuesta de Sta. Inés, 6, 18010 Granada', 0.0, NULL, 'palacio_olvidados', NULL);

INSERT INTO contiene(ruta, punto_de_interes)
    VALUES ('Granada cristiana y judía', 'Cartuja de Granada'),
    ('Granada cristiana y judía', 'Catedral de Granada'),
    ('Granada cristiana y judía', 'Plaza Isabel La Católica'),
    ('Granada cristiana y judía', 'Museo Sefardí'),
    ('Granada cristiana y judía', 'El Palacio de los Olvidados');

INSERT INTO punto_interes(nombre, lng, lat, texto, direccion, precio, audio, imagen, video)
	VALUES
	('Museo Arqueológico y Etnológico de Granada', -3.591550, 37.178693,
	'Museo situado en la Casa de Castril, considerada uno de los mejores palacios renacentistas de Granada, y que contiene hallazgos arqueológicos del paleolítico y neolítico asi como piezas íberas, fenicias, romanas y árabes.',
	'Carrera del Darro, 41, 43, 18010 Granada', 0.0, NULL, 'museo_etnologico', NULL),
	('Parque de las Ciencias', -3.605659, 37.162634,
	'Gran parque de las Ciencias con planetario, museo interactivo, mariposario y torre de observación',
	'Av. de la Ciencia, s/n, 18006 Granada', 0.0, NULL, 'parque_ciencias', NULL),
	('Museo de la Memoria de Andalucía', -3.607848, 37.162173,
	'Centro cultural polivalente con espacio para actuaciones, archivos, talleres y un museo sobre Andalucía',
	'Av. de la Ciencia, 2, 18006 Granada', 0.0, NULL, 'museo_andalucia', NULL),
	('Jardín Botánico de la Universidad de Granada', -3.602338, 37.177501,
	'Apacible jardín botánico histórico, inaugurado en el siglo XIX, con flora variada',
	'Calle Duquesa, 13, 18001 Granada', 0.0, NULL, 'jardin_universidad', NULL),
	('Museo de Bellas Artes de Granada', -3.589560, 37.176730,
	'Museo con sede en el Palacio de Carlos V que posee pinturas y esculturas, desde el siglo XV hasta el XX',
	'Palacio de Carlos V, Calle Real de la Alhambra, S/N, 18009 Granada', 0.0, NULL, 'museo_bellas_artes', NULL);

INSERT INTO contiene(ruta, punto_de_interes)
    VALUES ('Museos', 'Museo Arqueológico y Etnológico de Granada'),
    ('Museos', 'Parque de las Ciencias'),
    ('Museos', 'Museo de la Memoria de Andalucía'),
    ('Museos', 'Jardín Botánico de la Universidad de Granada'),
    ('Museos', 'Museo de Bellas Artes de Granada');

INSERT INTO ruta(nombre, descripcion, categoria, nivel_coste, nivel_accesibilidad, imagen)
     VALUES
     ('Restaurantes: Ayuntamiento - Navas',
     'Selección de restaurantes donde se puede respirar la auténtica esencia del tapeo en Granada',
     'Gastronomia', 4.0, 3.0, 'tapas_ayun_navas'),
     ('Restaurantes: Pedro Antonio',
     'Recorrido por la Plaza de Toros, donde encontrará un verdadero eje gastronómico compuesto por multitud de bares y restaurantes',
     'Gastronomia', 2.5, 3.0, 'tapas_toros');

INSERT INTO punto_interes(nombre, lng, lat, texto, direccion, precio, audio, imagen, video)
     VALUES
     ('Bar Los Diamantes', -3.598220, 37.173684,
     'Especialidad en tapas de pescado como almejas o boquerones en acogedor bar y restaurante con terraza',
     'Calle Navas, 28, 18009 Granada', 0.00, NULL, 'diamantes', NULL),
     ('Restaurante Tinta Fina', -3.598598, 37.173085,
     'Comedor de estilo moderno con toques futuristas para vinos, cocina fusión japonesa y pescados a la espalda',
     'Calle Ángel Ganivet, 18009 Granada', 0.0, NULL, 'tinta_fina', NULL),
     ('Restaurante EntreBrasas', -3.598408, 37.173700,
     'Restaurante situado en calle navas especializado en carne a la brasa y churrascos',
     'Calle Navas, 27, 18009 Granada', 0.0, NULL, 'entre_brasas', NULL);

INSERT INTO contiene(ruta, punto_de_interes)
    VALUES ('Restaurantes: Ayuntamiento - Navas', 'Bar Los Diamantes'),
    ('Restaurantes: Ayuntamiento - Navas', 'Restaurante Tinta Fina'),
    ('Restaurantes: Ayuntamiento - Navas', 'Restaurante EntreBrasas');

INSERT INTO punto_interes(nombre, lng, lat, texto, direccion, precio, audio, imagen, video)
     VALUES
     ('Restaurante Poetas Andaluces II', -3.606550, 37.173032,
     'Recetario español especializado en carnes en espacio con techos abovedados de madera y barra de azulejos',
     'Calle Pedro Antonio de Alarcón, 43, 18002 Granada', 0.00, NULL, 'poetas', NULL),
     ('Bar bubion', -3.609592, 37.174479,
     'Bar frecuentado por los estudiantes de Granada donde se sirve un fantástico pollo frito',
     'Glorieta de Arabial, 8, 18003 Granada', 0.0, NULL, 'bubion', NULL),
     ('Restaurante Kudamm Edel', -3.606619, 37.173470,
     'Acogedor restaurante con barra de picoteo y terraza exterior que sirve recetas de cocina española-alemana',
     'Calle Pedro Antonio de Alarcón, 36, 18002 Granada', 0.0, NULL, 'kudamm', NULL);

INSERT INTO contiene(ruta, punto_de_interes)
    VALUES ('Restaurantes: Pedro Antonio', 'Restaurante Poetas Andaluces II'),
    ('Restaurantes: Pedro Antonio', 'Bar bubion'),
    ('Restaurantes: Pedro Antonio', 'Restaurante Kudamm Edel');

INSERT INTO ruta(nombre, descripcion, categoria, nivel_coste, nivel_accesibilidad, imagen)
     VALUES
     ('Pubs y discotecas',
     'Recorrido por los principales pubs y discotecas de la noche granadina',
     'Ocio', 3.5, 3.0, 'noche'),
     ('Cines',
     'Recorrido por las salas de cine de Granada',
     'Ocio', 2.5, 3.5, 'cine');

INSERT INTO punto_interes(nombre, lng, lat, texto, direccion, precio, audio, imagen, video)
     VALUES
     ('Mae West Granada', -3.607883, 37.168316,
     'Discoteca, fiestas, espectáculos de cómicos y música en vivo en tres ambientes distintos con un público joven',
     'Centro Comercial Neptuno, C/ Arabial, s/n, 18004 Granada', 0.00, NULL, 'mae', NULL),
     ('Pub La Marisma', -3.608898, 37.176870,
     'Pub frecuentado por estudiantes en plena zona de Pedro Antonio',
     'Calle Pedro Antonio de Alarcón, 87, 18003 Granada', 0.0, NULL, 'marisma', NULL),
     ('Pub Wall Street', -3.606926, 37.174015,
     'Pub en plena zona de Pedro Antonio con bebidas cuyo precio sube y baja emulando las acciones de la bolsa',
     'Calle Pedro Antonio de Alarcón, 52, 18002 Granada', 0.0, NULL, 'wall_street', NULL);

INSERT INTO contiene(ruta, punto_de_interes)
    VALUES ('Pubs y discotecas', 'Mae West Granada'),
    ('Pubs y discotecas', 'Pub La Marisma'),
    ('Pubs y discotecas', 'Pub Wall Street');

INSERT INTO punto_interes(nombre, lng, lat, texto, direccion, precio, audio, imagen, video)
     VALUES
     ('Megarama Granada', -3.607752, 37.167816,
     'Estrenos de cartelera y pases únicos de films clásicos en V.O.S. en ocho salas digitales con sistema 3D',
     'Centro Comercial Neptuno, Calle Arabial, s/n, 18004 Granada', 6.00, NULL, 'megarama', NULL),
     ('Kinepolis', -3.615251, 37.14524,
     'Sala de cine con ocho salas de proyección equipadas con tecnología láser. Ubicado en centro comercial Nevada',
     'Nevada Shopping Center, Calle Hipócrates, 53, 18100 Armilla, Granada', 0.0, NULL, 'kinepolis', NULL),
     ('Cinema Serrallo', -3.583902, 37.157752,
     'Sala de cine ubicada en el centro comercial Serrallo',
     'Paseo Lagunas de Cameros, 1 Centro comercial Serrallo Plaza, 18008 Granada', 0.0, NULL, 'serrallo', NULL);

INSERT INTO contiene(ruta, punto_de_interes)
    VALUES ('Cines', 'Megarama Granada'),
    ('Cines', 'Kinepolis'),
    ('Cines', 'Cinema Serrallo');