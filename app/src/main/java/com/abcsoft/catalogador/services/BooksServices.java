package com.abcsoft.catalogador.services;

import com.abcsoft.catalogador.model.Book.Book;
import com.abcsoft.catalogador.model.ScanDetails;

import java.util.List;

public interface BooksServices {

    public ScanDetails create(ScanDetails book);
    public ScanDetails read(Long id);
    public ScanDetails update(ScanDetails book);
    public Boolean delete(Long id);

    public List<ScanDetails> getAll();




}
