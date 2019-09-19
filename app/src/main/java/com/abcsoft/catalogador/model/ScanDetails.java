package com.abcsoft.catalogador.model;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.abcsoft.catalogador.model.Book.Book;
import com.abcsoft.catalogador.model.Book.Cover;
import com.abcsoft.catalogador.model.BookAPI.BookOpenLibrary;

import java.util.Date;

public class ScanDetails {

    private long id;
    private Boolean found;
    private String type; //TODO Definir tipos
    private Date dateCreation;
    private Bitmap barcodePicture;
    private String barcode;
    private double price;
    private String notes;
    private double longitud;
    private double latitud;

    private Book book;

    public void importFromopenLibrary(BookOpenLibrary bookinfo) {
        //Extraigo algunos datos seleccionados del modelo json
        //TODO Mover a una clase dedicada a eso
        //TODO Validar emptys y nulls antes de leer campo

        this.book = new Book(
            -1, //id temporal. La definitiva cuando inserte a la bbdd
            bookinfo.getIsbncode(),
            bookinfo.getIsbn().getTitle(),
            bookinfo.getIsbn().getAuthors().get(0).getName(), //TODO Resolver cuando mas de un author con una tabla de authors
            bookinfo.getIsbn().getPublishers().get(0).getName(), //TODO Resolver cuando mas de un pulisher con una tabla de pulishers
            (bookinfo.getIsbn().getPublish_places() != null && !bookinfo.getIsbn().getPublish_places().isEmpty()) ? bookinfo.getIsbn().getPublish_places().get(0).getName() : "",
            bookinfo.getIsbn().getPublish_date(),
            bookinfo.getIsbn().getNumber_of_pages().intValue(),
            new Cover(
                -1, //id temporal. La definitiva cuando inserte a la bbdd
                bookinfo.getIsbn().getCover().getLarge(),
                null
            )
        );
        this.found = Boolean.TRUE;
    }

    public void importFromBundle(Bundle b) {
        //Si el bundle no es null recupero los datos
        if(b != null) {

            this.book = new Book();
            this.book.importFromBundle(b);

//            this.book = new Book (
//                -1,
//                b.getString("isbn"),
//                b.getString("title"),
//                b.getString("author"),
//                b.getString("publisher"),
//                b.getString("publishPlace"),
//                b.getString("publishDate"),
//                b.getInt("numPags"),
//                new Cover (-1,b.getString("coverLink"),null)
//            );
            this.setId(b.getLong("id"));
            this.setPrice(b.getDouble("price"));
            this.setNotes(b.getString("notes"));
            this.setFound(b.getBoolean("found"));
        }
    }

    public void exportToBundle(Bundle b) {
//        b.putString("isbn", this.book.getIsbn());
//        b.putString("title", this.book.getTitle());
//        b.putString("author", this.book.getAuthor());
//        b.putString("publisher", this.book.getPublisher());
//        b.putString("publishDate", this.book.getPublishDate());
//        b.putString("publishPlace", this.book.getPublishPlace());
//        b.putString("coverLink", this.book.getCover().getLink());
//        b.putInt("numPags", this.book.getNumPages());

        book.exportToBundle(b);

        b.putLong("id", this.getId()); //Necessario para delete y/o edit
        b.putDouble("price", this.getPrice());
        b.putString("notes", this.getNotes());
        b.putBoolean("found", this.getFound());
    }

    public ScanDetails() {
    }

    public ScanDetails(String barcode) {
        this.id = -1;
        this.found = Boolean.FALSE;
        this.type = null;
        this.dateCreation = new Date();
        this.barcodePicture = null;
        this.barcode = barcode;
        this.price = 0.0;
        this.notes = "";
        this.longitud = 0.0;
        this.latitud = 0.0;
        this.book = new Book(barcode);
    }

    public ScanDetails(long id, Boolean found, String type, Date dateCreation, Bitmap barcodePicture, String barcode, double price, String notes, double longitud, double latitud, Book book) {
        this.id = id;
        this.found = found;
        this.type = type;
        this.dateCreation = dateCreation;
        this.barcodePicture = barcodePicture;
        this.barcode = barcode;
        this.price = price;
        this.notes = notes;
        this.longitud = longitud;
        this.latitud = latitud;
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Bitmap getBarcodePicture() {
        return barcodePicture;
    }

    public void setBarcodePicture(Bitmap barcodePicture) {
        this.barcodePicture = barcodePicture;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
