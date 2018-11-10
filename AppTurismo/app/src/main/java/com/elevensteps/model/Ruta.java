package com.elevensteps.model;

import lombok.Value;
import lombok.NonNull;

/**
 * @author alejnp
 */
public @Value class Ruta {
    @NonNull String nombre;
    @NonNull int nivelCoste;
    @NonNull int nivelAccesibilidad;
    String imagen;
}
