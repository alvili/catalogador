package com.abcsoft.catalogador.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.adapters.BooksAdapter;
import com.abcsoft.catalogador.modelo.BookLocal.BookLocal;
import com.abcsoft.catalogador.services.BooksServices;
import com.abcsoft.catalogador.services.BooksServicesSQLite;

import java.util.List;

public class ListViewActivity  extends AppCompatActivity {

    private List<BookLocal> books;
    ListView listBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listBooks = (ListView) findViewById(R.id.idListView);

        //Recupero los datos
        BooksServices booksServices = new BooksServicesSQLite(this);
//        listBooks.setAdapter(new BooksAdapter(this, booksServices.getAll()));
        books = booksServices.getAll();

        //Instancio adaptador
        BooksAdapter booksAdapter = new BooksAdapter(this, books);

        //asigno el adaptador al ListView
        listBooks.setAdapter(booksAdapter);


    }


}
