INSERT INTO ruta(nombre, categoria, nivel_coste, nivel_accesibilidad, imagen) 
     VALUES ('Visita a la Alhambra', 'cultura', 4.5, 4.0, NULL),
            ('Ruta del Vino', 'gastronomia', 4.35, 5.0, NULL),
            ('Tapeo de Granada',  'gastronomia', 1.55, 5.0, NULL),
            ('Cafeterías Hipsters', 'ocio', 3.99, 4.0, NULL),
            ('Intercambio de Idiomas', 'ocio', 0.5, 2.8, NULL),
            ('Pokémon Go', 'ocio', 0.0, 0.5, NULL);

-- geo:37.172627,-3.587386;crs=wgs84;u=0

INSERT INTO punto_interes(nombre, lat, lng, audio, imagen, video)
     VALUES ('Bar Paco', NULL, NULL, NULL, NULL, NULL),
            ('Parada 89', NULL, NULL, NULL, NULL, NULL),
            ('El Barbitas', NULL, NULL, NULL, NULL, NULL),
            ('Hipsty', NULL, NULL, NULL, NULL, NULL),
            ('Taberna Manuel', NULL, NULL, NULL, NULL, NULL),
            ('Generalife', 37.176990, -3.585268, NULL, NULL, NULL),
            ('Cármen de los Mártires', 37.172627, -3.587386, NULL, NULL, NULL),
            ('Palacio de Carlos V', 37.176760, -3.589876, NULL, NULL, NULL),
            ('Palacios Nazaríes', 37.177267, -3.589358, NULL, NULL, NULL),
            ('Alcazaba', 37.177069, -3.591796, NULL, NULL, NULL),
            ('Parador de San Francisco', 37.175895, -3.587269, NULL, NULL, NULL);

INSERT INTO contiene(ruta, punto_de_interes)
     VALUES ('Visita a la Alhambra', 'Generalife'),
            ('Visita a la Alhambra', 'Cármen de los Mártires'),
            ('Visita a la Alhambra', 'Palacio de Carlos V'),
            ('Visita a la Alhambra', 'Palacios Nazaríes'),
            ('Visita a la Alhambra', 'Alcazaba'),
            ('Visita a la Alhambra', 'Parador de San Francisco'),
            ('Cafeterías Hipsters', 'El Barbitas'),
            ('Cafeterías Hipsters', 'Hipsty'),
            ('Tapeo de Granada', 'Bar Paco'),
            ('Tapeo de Granada', 'Parada 89'),
            ('Tapeo de Granada', 'Taberna Manuel');