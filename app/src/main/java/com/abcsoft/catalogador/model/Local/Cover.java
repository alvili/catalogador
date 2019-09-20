package com.abcsoft.catalogador.model.Local;

import android.graphics.Bitmap;

public class Cover {

    private long id;
    private String link;
    private Bitmap image;

//    public void importFromBundle(Bundle b) {
//        //Si el bundle no es null recupero los datos
//        if(b != null) {
//            this.setId(b.getLong("bookId"));
//            this.setIsbn(b.getString("isbn"));
//            this.setTitle(b.getString("title"));
//            this.setAuthor(b.getString("author"));
//            this.setPublisher(b.getString("publisher"));
//            this.setPublishPlace(b.getString("publishPlace"));
//            this.setPublishDate(b.getString("publishDate"));
//            this.setNumPages(b.getInt("numPags"));
//            this.cover = new Cover (-1,b.getString("coverLink"),null);
//        }
//    }
//
//    public void exportToBundle(Bundle b) {
//        b.putLong("bookId", this.getId());
//        b.putString("isbn", this.getIsbn());
//        b.putString("title", this.getTitle());
//        b.putString("author", this.getAuthor());
//        b.putString("publisher", this.getPublisher());
//        b.putString("publishDate", this.getPublishDate());
//        b.putString("publishPlace", this.getPublishPlace());
//        b.putString("coverLink", this.getCover().getLink());
//        b.putInt("numPags", this.getNumPages());
//    }


    public Cover() {
        id=-1;
        link="";
        image=null;
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
