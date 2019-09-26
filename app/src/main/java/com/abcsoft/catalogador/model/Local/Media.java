package com.abcsoft.catalogador.model.Local;

import android.os.Bundle;

public class Media extends Scan {

    private long mediaId;
    private String title;
    private String author;
    private String publisher;
    private String publishDate;
    private String publishPlace;
    private Cover cover;
    private Type type;

    public void importFromBundle(Bundle b) {
        //Si el bundle no es null recupero los datos
        if (b != null) {
            this.setMediaId(b.getLong("mediaId"));
            this.setTitle(b.getString("title"));
            this.setAuthor(b.getString("author"));
            this.setPublisher(b.getString("publisher"));
            this.setPublishDate(b.getString("publishDate"));
            this.setPublishPlace(b.getString("publishPlace"));
            this.setType(Type.valueOf(b.getString("type")));
        }
        this.cover.importFromBundle(b);
    }

    public void exportToBundle(Bundle b) {
        super.exportToBundle(b);
        b.putLong("mediaId", this.getMediaId());
        b.putString("title", this.getTitle());
        b.putString("author", this.getAuthor());
        b.putString("publisher", this.getPublisher());
        b.putString("publishDate", this.getPublishDate());
        b.putString("publishPlace", this.getPublishPlace());
        b.putString("type", this.getType().toString());
        cover.exportToBundle(b);
    }


    public Media(){
        super();
        this.mediaId = -1;
        this.title = "";
        this.author = "";
        this.publisher = "";
        this.publishDate = "";
        this.publishPlace = "";
        this.cover = new Cover();
        this.type=null;
    }

    public Media(Type type){
        this();
        this.type=type;
    }

    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
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

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishPlace() {
        return publishPlace;
    }

    public void setPublishPlace(String publishPlace) {
        this.publishPlace = publishPlace;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
