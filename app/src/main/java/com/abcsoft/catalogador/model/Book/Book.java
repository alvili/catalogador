package com.abcsoft.catalogador.model.Book;

import android.os.Bundle;

import com.abcsoft.catalogador.model.BookAPI.BookOpenLibrary;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {

    //Book details
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String publishPlace;
    private String publishDate;
    private int numPages;
    private String coverLink;

    //personal info
    private double price;
    private String notes;

    //bbdd details
    private long id;
    private Boolean found;
    private Date dateCreation;
    private double longitud;
    private double latitud;

    public Book() {
        this.found = Boolean.FALSE; //opcion por defecto hasta que no tenga datos
    }

    public void importFromopenLibrary(BookOpenLibrary bookinfo) {
        //Extraigo algunos datos seleccionados del modelo json
        //TODO Mover a una clase dedicada a eso
        //TODO Validar emptys y nulls antes de leer campo
        this.setIsbn(bookinfo.getIsbncode());
        this.setTitle(bookinfo.getIsbn().getTitle());
        this.setAuthor(bookinfo.getIsbn().getAuthors().get(0).getName()); //TODO Resolver cuando mas de un author con una tabla de authors
        this.setPublisher(bookinfo.getIsbn().getPublishers().get(0).getName()); //TODO Resolver cuando mas de un pulisher con una tabla de pulishers
        this.setPublishDate(bookinfo.getIsbn().getPublish_date());
        if (bookinfo.getIsbn().getPublish_places() != null && !bookinfo.getIsbn().getPublish_places().isEmpty()) {
            this.setPublishPlace(bookinfo.getIsbn().getPublish_places().get(0).getName());
        } else {
            this.setPublishPlace("");
        }
        this.setCoverLink(bookinfo.getIsbn().getCover().getLarge());
        this.setNumPages(bookinfo.getIsbn().getNumber_of_pages().intValue());
        this.found = Boolean.TRUE;
    }

    public void importFromBundle(Bundle b) {
        //Si el bundle no es null recupero los datos
        if(b != null) {
            this.setId(b.getLong("id"));
            this.setIsbn(b.getString("isbn"));
            this.setTitle(b.getString("title"));
            this.setAuthor(b.getString("author"));
            this.setPublisher(b.getString("publisher"));
            this.setPublishDate(b.getString("publishDate"));
            this.setPublishPlace(b.getString("publishPlace"));
            this.setCoverLink(b.getString("coverLink"));
            this.setNumPages(b.getInt("numPags"));
            this.setPrice(b.getDouble("price"));
            this.setNotes(b.getString("notes"));
            this.setFound(b.getBoolean("found"));
        }
    }

    public Bundle exportToBundle() {
        Bundle b = new Bundle();
        b.putLong("id", this.getId()); //Necessario para delete y/o edit
        b.putString("isbn", this.getIsbn());
        b.putString("title", this.getTitle());
        b.putString("author", this.getAuthor());
        b.putString("publisher", this.getPublisher());
        b.putString("publishDate", this.getPublishDate());
        b.putString("publishPlace", this.getPublishPlace());
        b.putString("coverLink", this.getCoverLink());
        b.putInt("numPags", this.getNumPages());
        b.putDouble("price", this.getPrice());
        b.putString("notes", this.getNotes());
        b.putBoolean("found", this.getFound());
        return b;
    }

    public Bundle exportToBundleNotFound() {
        Bundle b = new Bundle();
        b.putLong("id", -1); //?? Cuando grabe en bbdd sera ella quien determinara el id para que sea único
        b.putString("isbn", "");
        b.putString("title", "");
        b.putString("author", "");
        b.putString("publisher", "");
        b.putString("publishDate", "");
        b.putString("publishPlace", "");
        b.putString("coverLink", "");
        b.putInt("numPags", 0);
        b.putDouble("price", 0.0);
        b.putString("notes", "");
        b.putBoolean("found", Boolean.FALSE);
        return b;
    }



    //Getters & Setters

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }
}
