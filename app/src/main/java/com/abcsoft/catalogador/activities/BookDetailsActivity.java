package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.modelo.Book;
import com.abcsoft.catalogador.retrofit.BooksAPI;
import com.abcsoft.catalogador.retrofit.RetrofitHelper;
import com.abcsoft.catalogador.services.BooksServicesSQLite;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsActivity extends AppCompatActivity {

    private BooksAPI jsonPlaceHolderApi_books;
    private BooksServicesSQLite bookServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);

        //Recogemos los datos enviados por el intent
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        //Solo si bundle no es null
        if(b != null){
            jsonPlaceHolderApi_books = RetrofitHelper.getBooksAPIsc();
//            getRaw(b.getString("isbn"));     // Los datos se pierden
            getRaw("9788408085614");

        }

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
                Book bookinfo = new Gson().fromJson(raw, Book.class);

                bookinfo.setIsbncode(isbn);

                //Preparo la vista
                TextView title = findViewById(R.id.idTitle);
                TextView year = findViewById(R.id.idYear);
                TextView publisher = findViewById(R.id.idPublisher);
                TextView author = findViewById(R.id.idAuthor);
                ImageView cover = findViewById(R.id.idCover);

                title.setText(bookinfo.getIsbn().getTitle());
                year.setText(bookinfo.getIsbn().getPublish_date() + ", " + bookinfo.getIsbn().getPublish_places().get(0).getName());
                publisher.setText(bookinfo.getIsbn().getPublishers().get(0).getName());
                author.setText(bookinfo.getIsbn().getAuthors().get(0).getName());
                Picasso.get().load(bookinfo.getIsbn().getCover().getLarge()).into(cover);

                //Añado a la bbdd
                bookServices = new BooksServicesSQLite(getApplicationContext());
                bookServices.create(bookinfo);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //???
                Log.d("**","Error!!!");
            }
        });

    }

}
