package com.abcsoft.catalogador.model.Local;

import android.os.Bundle;

import com.abcsoft.catalogador.model.BookAPI.BookOpenLibrary;

import java.io.Serializable;

public class Book extends Media implements Serializable {

    private String isbn;
    private int numPages;

    public void importFromOpenLibrary(BookOpenLibrary bookinfo) {
        //Extraigo algunos datos seleccionados del modelo json
        //TODO Mover a una clase dedicada a eso
        //TODO Validar emptys y nulls antes de leer campo

        this.setIsbn(bookinfo.getIsbncode());
        this.setTitle(bookinfo.getIsbn().getTitle());
        this.setAuthor(bookinfo.getIsbn().getAuthors().get(0).getName()); //TODO Resolver cuando mas de un author con una tabla de authors
        this.setPublisher(bookinfo.getIsbn().getPublishers().get(0).getName()); //TODO Resolver cuando mas de un pulisher con una tabla de pulishers
        this.setPublishPlace((bookinfo.getIsbn().getPublish_places() != null && !bookinfo.getIsbn().getPublish_places().isEmpty()) ? bookinfo.getIsbn().getPublish_places().get(0).getName() : "");

        this.setPublishDate(bookinfo.getIsbn().getPublish_date());
        this.setNumPages(bookinfo.getIsbn().getNumber_of_pages().intValue());

        this.getCover().setLink(bookinfo.getIsbn().getCover().getLarge());

        this.setFound(Boolean.TRUE);
    }

    public void importFromBundle(Bundle b) {
        super.importFromBundle(b);
        //Si el bundle no es null recupero los datos
        if(b != null) {
            this.setIsbn(b.getString("isbn"));
            this.setNumPages(b.getInt("numPags"));
        }
    }

    @Override
    public void exportToBundle(Bundle b) {
        super.exportToBundle(b);
        b.putString("isbn", this.getIsbn());
        b.putInt("numPags", this.getNumPages());
    }

    public Book() {
        super(Type.BOOK);
        this.isbn = "";
        this.numPages = 0;
    }

    public Book(String isbn) {
        this();
        this.isbn = isbn;
        super.setBarcode(isbn);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

}

