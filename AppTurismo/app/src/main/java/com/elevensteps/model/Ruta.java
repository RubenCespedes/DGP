package com.elevensteps.model;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/**
 * @author alejnp
 */
@Builder
public @Value class Ruta {
    @NonNull String nombre;
    @NonNull @Default String descripcion = "n/a";
    @NonNull @Default String categoria = "n/a";
    @NonNull @Default Double nivelCoste = 0.0;
    @NonNull @Default Double nivelAccesibilidad = 0.0;
    String imagen;
}
