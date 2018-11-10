package com.elevensteps.model;

import lombok.Value;
import lombok.NonNull;

public @Value class PuntoInteres {
    @NonNull String id;
    @NonNull String descripcion;
    @NonNull String horario;
    @NonNull String url;
    @NonNull String texto;
    @NonNull String direcciom;
    @NonNull int valoracion;
    String audio;
    String imagen;
    String video;
}