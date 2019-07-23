package com.abcsoft.catalogador.modelo;

public class Book {

    private BookDetails isbn;
    private String isbncode;

    public BookDetails getIsbn() {
        return isbn;
    }

    public void setIsbn(BookDetails isbn) {
        this.isbn = isbn;
    }

    public String getIsbncode() {
        return isbncode;
    }

    public void setIsbncode(String isbncode) {
        this.isbncode = isbncode;
    }
}
