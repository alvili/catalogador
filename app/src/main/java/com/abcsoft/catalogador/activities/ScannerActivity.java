package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.model.BookAPI.BookOL;
import com.abcsoft.catalogador.model.BookLocal.BookLocal;
import com.abcsoft.catalogador.retrofit.BooksAPI;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    private BooksAPI jsonPlaceHolderApi_books;
    private BookOL bookinfo;
    private BookLocal book;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Button scan = findViewById(R.id.id_BtnScanCode);
        final TextView code = (TextView) findViewById(R.id.idTextCode);

        code.setText("9788408085614");
//        code.setText("9781101965481");
//        code.setText("8439596065");

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recupero les dades
                getRaw(code.getText().toString());

            }
        });

    }



    private void getRaw(final String bookIsbn){

        Map<String, String> params = new HashMap<String, String>();
        params.put("bibkeys", "ISBN:" + bookIsbn );
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

                if (!raw.equals("{}")) {
                    //Modifico el formato de los datos para poder leerlos con "fromJson"
                    raw = raw.replaceFirst(":" + bookIsbn, "")
                            .replaceFirst("ISBN", "isbn");

                    Gson gson = new Gson();
                    bookinfo = new Gson().fromJson(raw, BookOL.class); //Rellena el modelo con los datos del json
                    bookinfo.setIsbncode(bookIsbn); //isbn no esta entre los datos que vienen por json

                    //Transformo los datos al formato local
                    extraerDatos();
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

    public void extraerDatos() {
//        BookLocal book = new BookLocal();
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
        Intent intent = new Intent(ScannerActivity.this, DetailsScannedBookActivity.class);
        intent.putExtra("isbn", book.getIsbn());
        intent.putExtra("title", book.getTitle());
        intent.putExtra("author", book.getAuthor());
        intent.putExtra("publisher", book.getPublisher());
        intent.putExtra("year", book.getYear());
        intent.putExtra("place", book.getPublishPlace());
        intent.putExtra("cover", book.getCoverLink());
        intent.putExtra("pags", book.getNumPages());
        startActivity(intent);

    }

    private void mostrarNotFound() {
        Intent intent = new Intent(ScannerActivity.this, DetailsScannedBookActivity.class);
        intent.putExtra("isbn", "NOT FOUND");
        intent.putExtra("title", "");
        intent.putExtra("author", "");
        intent.putExtra("publisher", "");
        intent.putExtra("year", "");
        intent.putExtra("place", "");
        intent.putExtra("cover", "");
        intent.putExtra("pags", "");
        startActivity(intent);
    }

}
