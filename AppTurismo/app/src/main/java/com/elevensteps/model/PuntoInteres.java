package com.elevensteps.model;

/**
 * @author alejnp
 */
@lombok.Builder
public @lombok.Value class PuntoInteres {
    @lombok.NonNull String nombre;
    @lombok.NonNull String url;
    @lombok.NonNull String texto;
    @lombok.NonNull String direccion;
    @lombok.NonNull String horario;
    @lombok.NonNull Double precio;
    @lombok.NonNull Double valoracion;
    Double lng;
    Double lat;
    String audio;
    String imagen;
    String video;

    public boolean hasCoordinates() {
        return lat != null && lng != null;
    }
}