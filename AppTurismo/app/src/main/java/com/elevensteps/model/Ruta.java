package com.elevensteps.model;

/**
 * @author alejnp
 */
@lombok.Builder
public @lombok.Value class Ruta {
    @lombok.NonNull String nombre;
    @lombok.NonNull String categoria;
    @lombok.NonNull Double nivelCoste;
    @lombok.NonNull Double nivelAccesibilidad;
    String imagen;
}
