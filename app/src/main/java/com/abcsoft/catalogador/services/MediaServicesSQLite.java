package com.abcsoft.catalogador.services;

import android.content.Context;

import com.abcsoft.catalogador.database.DatabaseHelper;
import com.abcsoft.catalogador.model.Local.Media;

import java.util.List;

public class MediaServicesSQLite implements MediaServices {

    private DatabaseHelper myDB;

    public MediaServicesSQLite(Context contexto) {
        myDB = new DatabaseHelper(contexto);
    }


    @Override
    public Media create(Media media) {
        return myDB.createMedia(media);
    }

    @Override
    public Media read(Long id) {
        return myDB.readScan(id);
    }

    @Override
    public Media update(Media media) {
        return myDB.updateMedia(media);
    }

    @Override
    public Boolean delete(Long id) {
        return myDB.deleteMedia(id);
    }

    @Override
    public List<Media> getAll() {
        return myDB.getAll();
    }
}
