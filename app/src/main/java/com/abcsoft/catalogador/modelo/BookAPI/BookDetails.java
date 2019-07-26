package com.abcsoft.catalogador.modelo.BookAPI;

import java.util.List;

public class BookDetails {

    private List<Publisher> publishers;
    private String title;
    private String url;
//    private String identifiers; //**********
    private Cover cover;
    private List<Subject> subjects;
    private String publish_date;
    private String key;
    private List<Author> authors;
    private List<Publish_place> publish_places;

    public BookDetails() {
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Publish_place> getPublish_places() {
        return publish_places;
    }

    public void setPublish_places(List<Publish_place> publish_places) {
        this.publish_places = publish_places;
    }
}
