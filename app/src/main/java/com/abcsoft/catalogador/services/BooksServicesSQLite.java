package com.abcsoft.catalogador.services;

import android.content.Context;

import com.abcsoft.catalogador.database.DatabaseHelper;
import com.abcsoft.catalogador.model.BookLocal.BookLocal;

import java.util.List;

public class BooksServicesSQLite implements BooksServices {

    private DatabaseHelper myDB;

    public BooksServicesSQLite(Context contexto) {
        myDB = new DatabaseHelper(contexto);
    }


    @Override
    public BookLocal create(BookLocal book) {
        return myDB.createBook(book);
    }

    @Override
    public BookLocal read(Long id) {
        return null;
    }

    @Override
    public BookLocal update(BookLocal book) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public List<BookLocal> getAll() {
        return myDB.getAll();
    }
}
