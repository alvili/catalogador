package com.abcsoft.catalogador.modelo;

import java.util.List;

public class Book {

    private List<Publisher> publishers;
    private String title;
    private String url;
    private String identifiers; //**********
    private Cover cover;
    private List<Subject> subjects;
    private String publish_date;
    private String key;
    private List<Author> authors;
    private List<Publish_place> publish_places;

}
