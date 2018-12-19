package com.elevensteps;

import android.graphics.Color;

public enum Filtro {
    Cultura(Color.parseColor("#ff8608")), Gastronomia(Color.parseColor("#b2ff80")), Ocio(Color.parseColor("#4cb5ff"));

    private Filtro(int color) {
        this.color = color;
    }

    private final int color;

    public int getColor() {
        return color;
    }


}
