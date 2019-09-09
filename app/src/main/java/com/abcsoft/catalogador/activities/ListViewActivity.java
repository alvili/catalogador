package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.adapters.BooksAdapter;
import com.abcsoft.catalogador.model.Book.Book;
import com.abcsoft.catalogador.services.BooksServices;
import com.abcsoft.catalogador.services.BooksServicesSQLite;

import java.util.List;

public class ListViewActivity  extends AppCompatActivity {

    //private List<Book> books;
    List<Book> books;
    ListView listBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listBooks = (ListView) findViewById(R.id.idListView);

        //Recupero la lista de todos los libros...
        final BooksServices booksServices = new BooksServicesSQLite(this);
        books = booksServices.getAll();

        //...y los paso a un adaptador para que rellene la lista de libros...
        listBooks.setAdapter(new BooksAdapter(this, books));

        //...y muestre los datos de uno concreto cuando se seleccione
        listBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Recupero los datos del libro de la bbdd local sqlite

                //Lanzo un intent y transfiero detalles del libro en un bundle
                Intent intent = new Intent(ListViewActivity.this, BookDetailsActivity.class);
//                intent.putExtras(books.get(position).exportToBundle());
                intent.putExtras(booksServices.read(books.get(position).getId()).exportToBundle());
                startActivity(intent);
            }
        });

    }

}
