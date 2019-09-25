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
        switch (media.getType()){
            case BOOK:
                return myDB.createBook(media);
                break;
            case CD:
        }
    }

    @Override
    public Media read(Long id) {
        return myDB.readScan(id);
    }

    @Override
    public Media update(Media media) {
        return myDB.updateScan(media);
    }

    @Override
    public Boolean delete(Long id) {
        return myDB.deleteScan(id);
    }

    @Override
    public List<Media> getAll() {
        return myDB.getAll();
    }
}
