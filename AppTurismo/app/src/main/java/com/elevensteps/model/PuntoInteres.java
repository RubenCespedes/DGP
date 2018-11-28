package com.elevensteps.model;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/**
 * @author alejnp
 */
@Builder
public @Value class PuntoInteres {
    @NonNull String nombre;
    @NonNull @Default String url = "n/a";
    @NonNull @Default String texto = "n/a";
    @NonNull @Default String direccion = "n/a";
    @NonNull @Default String horario = "n/a";
    @NonNull @Default Double precio = 0.0;
    @NonNull @Default Double valoracion = 0.0;
    Double lng;
    Double lat;
    String audio;
    String imagen;
    String video;

    public boolean hasCoordinates() {
        return lat != null && lng != null;
    }
}