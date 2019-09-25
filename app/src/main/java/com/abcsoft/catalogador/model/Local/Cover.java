package com.abcsoft.catalogador.model.Local;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.abcsoft.catalogador.services.Utilidades;

public class Cover {

    private long coverId;
    private String link;
    private Bitmap image;

    public void importFromBundle(Bundle b) {
        //Si el bundle no es null recupero los datos
        if(b != null) {
            this.setCoverId(b.getLong("coverId"));
            this.setLink(b.getString("coverLink"));
            this.setImage(Utilidades.getBitmap(b.getByteArray("coverPicture")));
        }
    }

    public void exportToBundle(Bundle b) {
        b.putLong("coverId", this.getCoverId());
        b.putString("coverLink", this.getLink());
        b.putByteArray("coverPicture", Utilidades.getBytes(this.getImage()));
    }


    public Cover() {
        this.coverId=-1;
        this.link="";
        this.image=null;
    }

    public Cover(long id, String link, Bitmap image) {
        this.coverId = id;
        this.link = link;
        this.image = image;
    }

    public long getCoverId() {
        return coverId;
    }

    public void setCoverId(long coverId) {
        this.coverId = coverId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
