package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.model.BookAPI.BookOpenLibrary;
import com.abcsoft.catalogador.model.Book.Book;
import com.abcsoft.catalogador.retrofit.BooksAPI;
import com.abcsoft.catalogador.retrofit.RetrofitHelper;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Button scan = findViewById(R.id.id_BtnScanCode);
        final TextView code = (TextView) findViewById(R.id.idTextCode);

//        code.setText("9788408085614");
        code.setText("9781101965481");
//        code.setText("8439596065");

        book = new Book();

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pido los datos al servicio REST de OpenLibrary
                getRaw(code.getText().toString());
            }
        });

    }

    private void getRaw(final String bookIsbn){

        Map<String, String> params = new HashMap<String, String>();
        params.put("bibkeys", "ISBN:" + bookIsbn );
        params.put("jscmd", "data");
        params.put("format", "json");

        BooksAPI jsonPlaceHolderApi_books = RetrofitHelper.getBooksAPIsc();
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

                if (!raw.equals("{}")) {
                    //Modifico el formato de los datos para poder leerlos con "fromJson"
                    raw = raw.replaceFirst(":" + bookIsbn, "")
                             .replaceFirst("ISBN", "isbn");

                    Gson gson = new Gson();
                    BookOpenLibrary bookinfo = new Gson().fromJson(raw, BookOpenLibrary.class); //Rellena el modelo con los datos del json
                    bookinfo.setIsbncode(bookIsbn); //isbn no esta entre los datos que vienen por json

                    //Transformo los datos al formato local
                    extraerDatos(bookinfo);
                    mostrarDetalles();
                } else { //No arriba res
                    mostrarNotFound();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //???
                Log.d("**","Error!!!");
            }
        });

    }

    public void extraerDatos(BookOpenLibrary bookinfo) {
        //Extraigo algunos datos seleccionados del modelo json
        //Mover a una clase dedicada a eso
        book.setIsbn(bookinfo.getIsbncode());
        book.setTitle(bookinfo.getIsbn().getTitle());
        book.setAuthor(bookinfo.getIsbn().getAuthors().get(0).getName());
        book.setPublisher(bookinfo.getIsbn().getPublishers().get(0).getName());
        book.setYear(bookinfo.getIsbn().getPublish_date());
        if (bookinfo.getIsbn().getPublish_places() != null && !bookinfo.getIsbn().getPublish_places().isEmpty()) {
            book.setPublishPlace(bookinfo.getIsbn().getPublish_places().get(0).getName());
        } else {
            book.setPublishPlace("");
        }
        book.setCoverLink(bookinfo.getIsbn().getCover().getLarge());
        book.setNumPages(bookinfo.getIsbn().getNumber_of_pages().intValue());
    }

    private void mostrarDetalles(){
        Intent intent = new Intent(ScannerActivity.this, BookDetailsActivity.class);
        Bundle b = new Bundle();
        b.putString("isbn", book.getIsbn());
        b.putString("title", book.getTitle());
        b.putString("author", book.getAuthor());
        b.putString("publisher", book.getPublisher());
        b.putString("year", book.getYear());
        b.putString("place", book.getPublishPlace());
        b.putString("cover", book.getCoverLink());
        b.putInt("pags", book.getNumPages());
        intent.putExtras(b);
        startActivity(intent);
    }

    private void mostrarNotFound() {
        Intent intent = new Intent(ScannerActivity.this, BookDetailsActivity.class);
        Bundle b = new Bundle();
        b.putString("isbn", "NOT FOUND");
        b.putString("title", "");
        b.putString("author", "");
        b.putString("publisher", "");
        b.putString("year", "");
        b.putString("place", "");
        b.putString("cover", "");
        b.putInt("pags", 0);
        intent.putExtras(b);
        startActivity(intent);
    }

}
