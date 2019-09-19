package com.abcsoft.catalogador.model.Book;

import android.graphics.Bitmap;

public class Cover {

    private long id;
    private String link;
    private Bitmap image;

    public Cover() {
    }

    public Cover(long id, String link, Bitmap image) {
        this.id = id;
        this.link = link;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
