INSERT INTO ruta(nombre, descripcion, categoria, nivel_coste, nivel_accesibilidad, imagen)
     VALUES ('Visita a la Alhambra', 'La Alhambra es una ciudad palatina andalusí situada en Granada. Consiste en un conjunto de palacios, jardines y fortaleza (alcázar o al-qasr القصر) que albergaba una verdadera ciudadela dentro de la propia ciudad de Granada, que servía como alojamiento al monarca y a la corte del Reino nazarí de Granada. Su verdadero atractivo, como en otras obras musulmanas de la época, no solo radica en los interiores, cuya decoración está entre las cumbres del arte andalusí, sino también en su localización y adaptación, generando un paisaje nuevo pero totalmente integrado con la naturaleza preexistente. En 2016 fue el segundo monumento más visitado de España.', 'Cultura', 4.5, 4.0, NULL);

INSERT INTO punto_interes(nombre, lat, lng, audio, imagen, video)
     VALUES ('Generalife', 37.176990, -3.585268, NULL, NULL, NULL),
            ('Cármen de los Mártires', 37.172627, -3.587386, NULL, NULL, NULL),
            ('Palacio de Carlos V', 37.176760, -3.589876, NULL, NULL, NULL),
            ('Palacios Nazaríes', 37.177267, -3.589358, NULL, NULL, NULL),
            ('Alcazaba', 37.177069, -3.591796, NULL, NULL, NULL),
            ('Parador de San Francisco', 37.175895, -3.587269, NULL, NULL, NULL);

INSERT INTO ruta(nombre, categoria, nivel_coste, nivel_accesibilidad)
     VALUES ('Ruta del Vino', 'Gastronomia', 4.35, 5.0),
            ('Tapeo de Granada',  'Gastronomia', 1.55, 5.0),
            ('Cafeterías Hipsters', 'Ocio', 3.99, 4.0),
            ('Intercambio de Idiomas', 'Ocio', 0.5, 2.8),
            ('Pokémon Go', 'Ocio', 0.0, 0.5);

INSERT INTO punto_interes(nombre)
     VALUES ('Bar Paco'),
            ('Parada 89'),
            ('El Barbitas'),
            ('Hipsty'),
            ('Taberna Manuel');

INSERT INTO contiene(ruta, punto_de_interes)
     VALUES ('Visita a la Alhambra', 'Generalife'),
            ('Visita a la Alhambra', 'Cármen de los Mártires'),
            ('Visita a la Alhambra', 'Palacio de Carlos V'),
            ('Visita a la Alhambra', 'Palacios Nazaríes'),
            ('Visita a la Alhambra', 'Alcazaba'),
            ('Visita a la Alhambra', 'Parador de San Francisco'),
            ('Visita a la Alhambra', 'Hipsty'),
            ('Cafeterías Hipsters', 'El Barbitas'),
            ('Cafeterías Hipsters', 'Hipsty'),
            ('Tapeo de Granada', 'Bar Paco'),
            ('Tapeo de Granada', 'Parada 89'),
            ('Tapeo de Granada', 'Taberna Manuel');