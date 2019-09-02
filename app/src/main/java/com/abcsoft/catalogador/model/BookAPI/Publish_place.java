package com.abcsoft.catalogador.model.BookAPI;

import com.abcsoft.catalogador.services.Utilidades;

public class Publish_place {
    private String name;

    public Publish_place() {
    }

    public String getName() {
        return Utilidades.ValidateBookStr(name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
