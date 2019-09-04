package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.adapters.BooksAdapter;
import com.abcsoft.catalogador.model.BookAPI.BookDetails;
import com.abcsoft.catalogador.model.BookLocal.BookLocal;
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
        books = booksServices.getAll();
//
//        //Instancio adaptador con los datos recuperados
//        BooksAdapter booksAdapter = new BooksAdapter(this, books);
//
//        //Asigno el adaptador al ListView
//        listBooks.setAdapter(booksAdapter);

        //Asigno al ListView un adaptador con la lista de libros
        listBooks.setAdapter(new BooksAdapter(this, books));

        listBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Lanzo un intent a los detalles del libro
                Intent intent = new Intent(ListViewActivity.this, DetailsCatalogedBookActivity.class);
                //Necesito pasar como paramentr oel libro a mostrar
                intent.putExtra("bookIsbn", books.get(position).getIsbn());
                //Cambio la vista
                startActivity(intent);
            }
        });

    }


}
