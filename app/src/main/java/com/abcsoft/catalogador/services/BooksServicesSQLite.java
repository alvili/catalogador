package com.abcsoft.catalogador.services;

import android.content.Context;

import com.abcsoft.catalogador.database.DatabaseHelper;
import com.abcsoft.catalogador.model.Book.Book;
import com.abcsoft.catalogador.model.ScanDetails;

import java.util.List;

public class BooksServicesSQLite implements BooksServices {

    private DatabaseHelper myDB;

    public BooksServicesSQLite(Context contexto) {
        myDB = new DatabaseHelper(contexto);
    }


    @Override
    public ScanDetails create(ScanDetails scan) {
        return myDB.createBook(scan);
    }

    @Override
    public ScanDetails read(Long id) {
        return myDB.readBook(id);
    }

    @Override
    public ScanDetails update(ScanDetails scan) {
        return myDB.updateBook(scan);
    }

    @Override
    public Boolean delete(Long id) {
        return myDB.deleteBook(id);
    }

    @Override
    public List<ScanDetails> getAll() {
        return myDB.getAll();
    }
}
