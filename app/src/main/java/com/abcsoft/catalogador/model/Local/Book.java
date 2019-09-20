package com.abcsoft.catalogador.model.Local;

import android.os.Bundle;

import com.abcsoft.catalogador.model.BookAPI.BookOpenLibrary;

import java.io.Serializable;

public class Book extends Media implements Serializable {

    private long bookid;
    private String isbn;
    private String author;
    private String publisher;
    private String publishPlace;
    private int numPages;

    public void importFromOpenLibrary(BookOpenLibrary bookinfo) {
        //Extraigo algunos datos seleccionados del modelo json
        //TODO Mover a una clase dedicada a eso
        //TODO Validar emptys y nulls antes de leer campo

        setIsbn(bookinfo.getIsbncode());
        setTitle(bookinfo.getIsbn().getTitle());
        setAuthor(bookinfo.getIsbn().getAuthors().get(0).getName()); //TODO Resolver cuando mas de un author con una tabla de authors
        setPublisher(bookinfo.getIsbn().getPublishers().get(0).getName()); //TODO Resolver cuando mas de un pulisher con una tabla de pulishers
        setPublishPlace((bookinfo.getIsbn().getPublish_places() != null && !bookinfo.getIsbn().getPublish_places().isEmpty()) ? bookinfo.getIsbn().getPublish_places().get(0).getName() : "");

        setPublishDate(bookinfo.getIsbn().getPublish_date());
        setNumPages(bookinfo.getIsbn().getNumber_of_pages().intValue());

        getCover().setLink(bookinfo.getIsbn().getCover().getLarge());

        setFound(Boolean.TRUE);
    }

    public void importFromBundle(Bundle b) {
        //Si el bundle no es null recupero los datos
        if(b != null) {
            this.setId(b.getLong("bookId"));
            this.setIsbn(b.getString("isbn"));
            this.setTitle(b.getString("title"));
            this.setAuthor(b.getString("author"));
            this.setPublisher(b.getString("publisher"));
            this.setPublishPlace(b.getString("publishPlace"));
            this.setPublishDate(b.getString("publishDate"));
            this.setNumPages(b.getInt("numPags"));
            this.cover = new Cover (-1,b.getString("coverLink"),null);
        }
    }

    @Override
    public void exportToBundle(Bundle b) {
        super();
        b.putLong("bookId", this.getId());
        b.putString("isbn", this.getIsbn());
        b.putString("title", this.getTitle());
        b.putString("author", this.getAuthor());
        b.putString("publisher", this.getPublisher());
        b.putString("publishDate", this.getPublishDate());
        b.putString("publishPlace", this.getPublishPlace());
        b.putString("coverLink", this.getCover().getLink());
        b.putInt("numPags", this.getNumPages());
    }

    public Book() {
        super();
        this.isbn = "";
        this.author = "";
        this.publisher = "";
        this.publishPlace = "";
        this.numPages = 0;
    }

    public Book(String isbn) {
        this();
        this.isbn = isbn;
    }


    public long getBookid() {
        return bookid;
    }

    public void setBookid(long bookid) {
        this.bookid = bookid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishPlace() {
        return publishPlace;
    }

    public void setPublishPlace(String publishPlace) {
        this.publishPlace = publishPlace;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }
}

