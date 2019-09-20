package com.abcsoft.catalogador.services;

import android.content.Context;

import com.abcsoft.catalogador.database.DatabaseHelper;
import com.abcsoft.catalogador.model.Local.Scan;

import java.util.List;

public class ScansServicesSQLite implements ScansServices {

    private DatabaseHelper myDB;

    public ScansServicesSQLite(Context contexto) {
        myDB = new DatabaseHelper(contexto);
    }


    @Override
    public Scan create(Scan scan) {
        return myDB.createScan(scan);
    }

    @Override
    public Scan read(Long id) {
        return myDB.readScan(id);
    }

    @Override
    public Scan update(Scan scan) {
        return myDB.updateScan(scan);
    }

    @Override
    public Boolean delete(Long id) {
        return myDB.deleteScan(id);
    }

    @Override
    public List<Scan> getAll() {
        return myDB.getAll();
    }
}
