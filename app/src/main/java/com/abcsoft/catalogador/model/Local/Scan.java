package com.abcsoft.catalogador.model.Local;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.abcsoft.catalogador.services.Utilidades;

import java.util.Date;

public class Scan {

    private long scanId;
    private Boolean found;
    private Date dateCreated;
    private Date dateModified;
    private String barcode;
    private Bitmap barcodePicture;
    private double price;
    private String notes;
    private double longitud;
    private double latitud;

    public void importFromBundle(Bundle b) {
        //Si el bundle no es null recupero los datos
        if (b != null) {
//            this.book = new Book();
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
            this.setFound(b.getBoolean("found"));
            this.setDateCreated(Utilidades.getDateFromString(b.getString("dateCreated")));
            this.setDateModified(Utilidades.getDateFromString(b.getString("dateModified")));
            this.setLongitud(b.getDouble("longitud"));
            this.setLatitud(b.getDouble("latitud"));

            this.setType(b.getString("type"));
            this.setBarcode(b.getString("barcode"));
            this.setBarcodePicture(Utilidades.getBitmap(b.getByteArray("barCodePicture")));
            this.setPrice(b.getDouble("price"));
            this.setNotes(b.getString("notes"));

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

        b.putLong("id", this.getScanid());
        b.putBoolean("found", this.getFound());
        b.putString("dateCreated", Utilidades.getStringFromDate(this.getDateCreated()));
        b.putString("dateModified", Utilidades.getStringFromDate(this.getDateModified()));
        b.putDouble("longitud", this.getLongitud());
        b.putDouble("latitud", this.getLatitud());

        b.putString("barcode", this.getBarcode());
        b.putByteArray("barCodePicture", Utilidades.getBytes(this.getBarcodePicture()));
        b.putDouble("price", this.getPrice());
        b.putString("notes", this.getNotes());
    }

    public Scan() {
        this.scanId = -1;
        this.found = Boolean.FALSE;
        this.dateCreated = new Date();
        this.dateModified = this.dateCreated;
        this.longitud = 0.0;
        this.latitud = 0.0;
        this.barcode = "";
        this.barcodePicture = null;
        this.price = 0.0;
        this.notes = "";

    }

    public long getScanId() {
        return scanId;
    }

    public void setScanId(long scanId) {
        this.scanId = scanId;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Bitmap getBarcodePicture() {
        return barcodePicture;
    }

    public void setBarcodePicture(Bitmap barcodePicture) {
        this.barcodePicture = barcodePicture;
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
}