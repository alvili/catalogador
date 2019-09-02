package com.abcsoft.catalogador.services;

import com.abcsoft.catalogador.model.BookLocal.BookLocal;

import java.util.List;

public interface BooksServices {

    public BookLocal create(BookLocal book);
    public BookLocal read(Long id);
    public BookLocal update(BookLocal book);
    public Boolean delete(Long id);

    public List<BookLocal> getAll();

}
