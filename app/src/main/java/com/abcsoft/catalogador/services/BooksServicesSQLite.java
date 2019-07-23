package com.abcsoft.catalogador.services;

import android.content.Context;

import com.abcsoft.catalogador.database.DatabaseHelper;
import com.abcsoft.catalogador.modelo.Book;

import java.util.List;

public class BooksServicesSQLite implements BooksServices {

    private DatabaseHelper myDB;

    public BooksServicesSQLite(Context contexto) {
        myDB = new DatabaseHelper(contexto);
    }


    @Override
    public Book create(Book book) {
        return myDB.createBook(book);
    }

    @Override
    public Book read(Long id) {
        return null;
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return myDB.getAll();
    }
}
