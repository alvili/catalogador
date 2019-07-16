package com.abcsoft.catalogador.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.modelo.Book;
import com.abcsoft.catalogador.retrofit.BooksAPI;
import com.abcsoft.catalogador.retrofit.RetrofitHelper;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetails  extends AppCompatActivity {

    private BooksAPI jsonPlaceHolderApi_books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);

        //lista = (ListView) findViewById(R.id.idMiLista);

        jsonPlaceHolderApi_books = RetrofitHelper.getBooksAPI();
        getBook("9788408085614");

    }

    private void getBook(String isbn){

        Map<String, String> params = new HashMap<String, String>();
        params.put("bibkeys", "ISBN:" + isbn );
        params.put("jscmd", "data");
        params.put("format", "json");

        Call<Book> call = jsonPlaceHolderApi_books.getBook(params);

        call.enqueue(new Callback<Book>() {

            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {

                if (!response.isSuccessful()){
                    //???
                    return;
                }

                //Recupero los datos e inflo la vista
                Book book = response.body();
//                lista.setAdapter(new CamareroListAdapter(getApplicationContext(), camareros));
                Log.d("**","Ok!!!!!");

            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                //???
                Log.d("**","Error!!!");
            }
        });

    }



}
