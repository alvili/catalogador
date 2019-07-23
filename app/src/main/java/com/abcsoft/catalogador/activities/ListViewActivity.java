package com.abcsoft.catalogador.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.adapters.BooksAdapter;
import com.abcsoft.catalogador.modelo.Book;

import java.util.List;

public class ListViewActivity  extends AppCompatActivity {

    private List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ListView listView = (ListView) findViewById(R.id.idListView);

        //Recupero los datos
//        books = getbooks();

        //Instancio adaptador
        BooksAdapter booksAdapter = new BooksAdapter(this, books);

        //asigno el adaptador al ListView
        listView.setAdapter(booksAdapter);


    }


}
