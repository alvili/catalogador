package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.modelo.BookAPI.Book;
import com.abcsoft.catalogador.modelo.BookLocal.BookLocal;
import com.abcsoft.catalogador.retrofit.BooksAPI;
import com.abcsoft.catalogador.retrofit.RetrofitHelper;
import com.abcsoft.catalogador.services.BooksServicesSQLite;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsActivity extends AppCompatActivity {

    private BooksAPI jsonPlaceHolderApi_books;
    private BooksServicesSQLite bookServices;
    private Book bookinfo;

    private TextView title;
    private TextView isbn;
    private TextView year;
    private TextView publisher;
    private TextView author;
    private ImageView cover;
    private Button guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);

        //Referencio la vista
        title = (TextView) findViewById(R.id.idTitle);
        isbn = (TextView ) findViewById(R.id.idIsbn);
        year = (TextView ) findViewById(R.id.idYear);
        publisher = (TextView ) findViewById(R.id.idPublisher);
        author = (TextView) findViewById(R.id.idAuthor);
        cover = (ImageView) findViewById(R.id.idCover);
        guardar = (Button) findViewById(R.id.idBtnGuardarLibro);

        jsonPlaceHolderApi_books = RetrofitHelper.getBooksAPIsc();

        //Recogemos los datos enviados por el intent
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        //Solo si bundle no es null pido los datos
        if(b != null){
            getRaw(b.getString("isbn"));
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardo a la bbdd local
                guardarDatos();

                //Vuelvo a la vista principal
                Intent intent = new Intent(BookDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void getRaw(final String isbn){

        Map<String, String> params = new HashMap<String, String>();
        params.put("bibkeys", "ISBN:" + isbn );
        params.put("jscmd", "data");
        params.put("format", "json");

        Call<String> call = jsonPlaceHolderApi_books.getRAW(params);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (!response.isSuccessful()){
                    //???
                    return;
                }

                //https://openlibrary.org/api/books?format=json&bibkeys=ISBN%3A9788408085614&jscmd=data

                //Recupero los datos e inflo la vista
                String raw = response.body();
                raw=raw.replaceFirst(":"+isbn,"")
                        .replaceFirst("ISBN","isbn");

                Gson gson = new Gson();
                bookinfo = new Gson().fromJson(raw, Book.class);
                bookinfo.setIsbncode(isbn);

                //Muestro los datos
                rellenarCampos();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //???
                Log.d("**","Error!!!");
            }
        });

    }

    private void rellenarCampos(){
        title.setText(bookinfo.getIsbn().getTitle());
        isbn.setText(bookinfo.getIsbncode());
        year.setText(bookinfo.getIsbn().getPublish_date() + ", " + bookinfo.getIsbn().getPublish_places().get(0).getName());
        publisher.setText(bookinfo.getIsbn().getPublishers().get(0).getName());
        author.setText(bookinfo.getIsbn().getAuthors().get(0).getName());
        Picasso.get().load(bookinfo.getIsbn().getCover().getLarge()).into(cover);
    }

    public void guardarDatos(){
        BookLocal book = new BookLocal();
        book.setIsbn(String.valueOf(isbn.getText()));
        book.setTitle(String.valueOf(title.getText()));
        book.setAuthor(String.valueOf(author.getText()));
        book.setPublisher(String.valueOf(publisher.getText()));
        book.setYear(String.valueOf(year.getText()));
        book.setDate(new Date());
        book.setPrice(0.0);
        book.setLongitud(0.0);
        book.setLatitud(0.0);

        bookServices = new BooksServicesSQLite(getApplicationContext());
        bookServices.create(book);

    }

}
