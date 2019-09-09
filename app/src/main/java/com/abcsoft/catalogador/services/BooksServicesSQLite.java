package com.abcsoft.catalogador.services;

import android.content.Context;

import com.abcsoft.catalogador.database.DatabaseHelper;
import com.abcsoft.catalogador.model.Book.Book;

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
        return myDB.readBook(id);
    }

    @Override
    public Book update(Book book) {
        return myDB.updateBook(book);
    }

    @Override
    public Boolean delete(Long id) {
        return myDB.deleteBook(id);
    }

    @Override
    public List<Book> getAll() {
        return myDB.getAll();
    }
}
