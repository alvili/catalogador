package com.abcsoft.catalogador.model.Local;

public class Media extends Scan {

    private String title;
    private String publishDate;
    private Cover cover;

    public Media(){
        super();
        this.title = "";
        this.publishDate = "";
        this.cover = new Cover();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}
