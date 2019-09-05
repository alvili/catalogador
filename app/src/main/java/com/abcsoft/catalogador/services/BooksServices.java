package com.abcsoft.catalogador.services;

import com.abcsoft.catalogador.model.Book.Book;

import java.util.List;

public interface BooksServices {

    public Book create(Book book);
    public Book read(Long id);
    public Book update(Book book);
    public Boolean delete(Long id);

    public List<Book> getAll();

}
