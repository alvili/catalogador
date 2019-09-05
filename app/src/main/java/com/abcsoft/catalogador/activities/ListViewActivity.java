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

    private List<Book> books;
    ListView listBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listBooks = (ListView) findViewById(R.id.idListView);

        //Recupero los datos
        BooksServices booksServices = new BooksServicesSQLite(this);
        books = booksServices.getAll();

        //Asigno al ListView un adaptador con la lista de libros
        listBooks.setAdapter(new BooksAdapter(this, books));

        listBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Recupero los datos del libro de la bbdd local sqlite

                //Lanzo un intent a los detalles del libro
                Intent intent = new Intent(ListViewActivity.this, BookDetailsActivity.class);
                Bundle b = new Bundle();
                b.putString("isbn", books.get(position).getIsbn());
                b.putString("title", books.get(position).getTitle());
                b.putString("author", books.get(position).getAuthor());
                b.putString("publisher", books.get(position).getPublisher());
                b.putString("year", books.get(position).getYear());
                b.putString("place", books.get(position).getPublishPlace());
                b.putString("cover", books.get(position).getCoverLink());
                b.putInt("pags", books.get(position).getNumPages());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }

}
