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
            this.setScanId(b.getLong("scanId"));
            this.setFound(b.getBoolean("found"));
            this.setDateCreated(Utilidades.getDateFromString(b.getString("dateCreated")));
            this.setDateModified(Utilidades.getDateFromString(b.getString("dateModified")));
            this.setBarcode(b.getString("barcode"));
            this.setBarcodePicture(Utilidades.getBitmap(b.getByteArray("barcodePicture")));
            this.setPrice(b.getDouble("price"));
            this.setNotes(b.getString("notes"));
            this.setLongitud(b.getDouble("longitud"));
            this.setLatitud(b.getDouble("latitud"));
        }
    }

    public void exportToBundle(Bundle b) {
        b.putLong("scanId", this.getScanId());
        b.putBoolean("found", this.getFound());
        b.putString("dateCreated", Utilidades.getStringFromDate(this.getDateCreated()));
        b.putString("dateModified", Utilidades.getStringFromDate(this.getDateModified()));
        b.putString("barcode", this.getBarcode());
        b.putByteArray("barcodePicture", Utilidades.getBytes(this.getBarcodePicture()));
        b.putDouble("price", this.getPrice());
        b.putString("notes", this.getNotes());
        b.putDouble("longitud", this.getLongitud());
        b.putDouble("latitud", this.getLatitud());
    }

    public Scan() {
        this.scanId = -1;
        this.found = Boolean.FALSE;
        this.dateCreated = new Date();
        this.dateModified = this.dateCreated;
        this.longitud = 0.0;
        this.latitud = 0.0;
        this.barcode = "";
        this.barcodePicture = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
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