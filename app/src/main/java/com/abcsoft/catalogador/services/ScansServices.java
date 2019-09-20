package com.abcsoft.catalogador.services;

import com.abcsoft.catalogador.model.Local.Scan;

import java.util.List;

public interface ScansServices {

    public Scan create(Scan scan);
    public Scan read(Long id);
    public Scan update(Scan scan);
    public Boolean delete(Long id);

    public List<Scan> getAll();



}
