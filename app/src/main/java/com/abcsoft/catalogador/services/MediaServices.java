package com.abcsoft.catalogador.services;

import com.abcsoft.catalogador.model.Local.Media;
import com.abcsoft.catalogador.model.Local.Scan;

import java.util.List;

public interface MediaServices {

    public Media create(Media media);
    public Media read(Long id);
    public Media update(Media media);
    public Boolean delete(Long id);

    public List<Media> getAll();



}
