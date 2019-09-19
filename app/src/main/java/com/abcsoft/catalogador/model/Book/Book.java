package com.abcsoft.catalogador.model.Book;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.abcsoft.catalogador.model.BookAPI.BookOpenLibrary;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {

    private long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String publishPlace;
    private String publishDate;
    private int numPages;
    private Cover cover;



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

    public void exportToBundle(Bundle b) {
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
    }

    public Book(String isbn) {
        this.id = -1;
        this.isbn = isbn;
        this.title = "";
        this.author = "";
        this.publisher = "";
        this.publishPlace = "";
        this.publishDate = "";
        this.numPages = 0;
        this.cover = null;
    }

    public Book(long id, String isbn, String title, String author, String publisher, String publishPlace, String publishDate, int numPages, Cover cover) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishPlace = publishPlace;
        this.publishDate = publishDate;
        this.numPages = numPages;
        this.cover = cover;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}

//    //aditional info
//    private double price;
//    private String notes;
//
//    //bbdd details
//    private long id;
//    private Boolean found;
//    private Date dateCreation;
//    private double longitud;
//    private double latitud;

//    public Book() {
//        this.found = Boolean.FALSE; //opción por defecto hasta que no tenga datos
//    }
//
//    public Book(String isbn) {
//        this.id = -1;
//        this.isbn = isbn;
//        this.title = "";
//        this.author = "";
//        this.publisher = "";
//        this.publishDate = "";
//        this.publishPlace = "";
//        this.coverLink = "";
//        this.cover = null;
//        this.numPages = 0;
//        this.price = 0.0;
//        this.notes = "";
//        this.found = Boolean.FALSE; //opción por defecto hasta que no tenga datos
//        this.dateCreation = new Date();
//        this.longitud = 0;
//        this.latitud = 0;
//    }
//
//
//}
//
//    public void importFromopenLibrary(BookOpenLibrary bookinfo) {
//        //Extraigo algunos datos seleccionados del modelo json
//        //TODO Mover a una clase dedicada a eso
//        //TODO Validar emptys y nulls antes de leer campo
//        this.setIsbn(bookinfo.getIsbncode());
//        this.setTitle(bookinfo.getIsbn().getTitle());
//        this.setAuthor(bookinfo.getIsbn().getAuthors().get(0).getName()); //TODO Resolver cuando mas de un author con una tabla de authors
//        this.setPublisher(bookinfo.getIsbn().getPublishers().get(0).getName()); //TODO Resolver cuando mas de un pulisher con una tabla de pulishers
//        this.setPublishDate(bookinfo.getIsbn().getPublish_date());
//        if (bookinfo.getIsbn().getPublish_places() != null && !bookinfo.getIsbn().getPublish_places().isEmpty()) {
//            this.setPublishPlace(bookinfo.getIsbn().getPublish_places().get(0).getName());
//        } else {
//            this.setPublishPlace("");
//        }
//        this.setCoverLink(bookinfo.getIsbn().getImage().getLarge());
//        this.setNumPages(bookinfo.getIsbn().getNumber_of_pages().intValue());
//        this.found = Boolean.TRUE;
//    }
//
//    public void importFromBundle(Bundle b) {
//        //Si el bundle no es null recupero los datos
//        if(b != null) {
//            this.setId(b.getLong("id"));
//            this.setIsbn(b.getString("isbn"));
//            this.setTitle(b.getString("title"));
//            this.setAuthor(b.getString("author"));
//            this.setPublisher(b.getString("publisher"));
//            this.setPublishDate(b.getString("publishDate"));
//            this.setPublishPlace(b.getString("publishPlace"));
//            this.setCoverLink(b.getString("coverLink"));
//            this.setNumPages(b.getInt("numPags"));
//            this.setPrice(b.getDouble("price"));
//            this.setNotes(b.getString("notes"));
//            this.setFound(b.getBoolean("found"));
//        }
//    }
//
//    public Bundle exportToBundle() {
//        Bundle b = new Bundle();
//        b.putLong("id", this.getId()); //Necessario para delete y/o edit
//        b.putString("isbn", this.getIsbn());
//        b.putString("title", this.getTitle());
//        b.putString("author", this.getAuthor());
//        b.putString("publisher", this.getPublisher());
//        b.putString("publishDate", this.getPublishDate());
//        b.putString("publishPlace", this.getPublishPlace());
//        b.putString("coverLink", this.getCoverLink());
//        b.putInt("numPags", this.getNumPages());
//        b.putDouble("price", this.getPrice());
//        b.putString("notes", this.getNotes());
//        b.putBoolean("found", this.getFound());
//        return b;
//    }
//


//
//    //Getters & Setters
//
//    public String getIsbn() {
//        return isbn;
//    }
//
//    public void setIsbn(String isbn) {
//        this.isbn = isbn;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public String getPublisher() {
//        return publisher;
//    }
//
//    public void setPublisher(String publisher) {
//        this.publisher = publisher;
//    }
//
//    public String getPublishPlace() {
//        return publishPlace;
//    }
//
//    public void setPublishPlace(String publishPlace) {
//        this.publishPlace = publishPlace;
//    }
//
//    public String getPublishDate() {
//        return publishDate;
//    }
//
//    public void setPublishDate(String publishDate) {
//        this.publishDate = publishDate;
//    }
//
//    public int getNumPages() {
//        return numPages;
//    }
//
//    public void setNumPages(int numPages) {
//        this.numPages = numPages;
//    }
//
//    public String getCoverLink() {
//        return coverLink;
//    }
//
//    public void setCoverLink(String coverLink) {
//        this.coverLink = coverLink;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public String getNotes() {
//        return notes;
//    }
//
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public Date getDateCreation() {
//        return dateCreation;
//    }
//
//    public void setDateCreation(Date dateCreation) {
//        this.dateCreation = dateCreation;
//    }
//
//    public double getLongitud() {
//        return longitud;
//    }
//
//    public void setLongitud(double longitud) {
//        this.longitud = longitud;
//    }
//
//    public double getLatitud() {
//        return latitud;
//    }
//
//    public void setLatitud(double latitud) {
//        this.latitud = latitud;
//    }
//
//    public Boolean getFound() {
//        return found;
//    }
//
//    public void setFound(Boolean found) {
//        this.found = found;
//    }
//
//    public Bitmap getImage() {
//        return cover;
//    }
//
//    public void setImage(Bitmap cover) {
//        this.cover = cover;
//    }


