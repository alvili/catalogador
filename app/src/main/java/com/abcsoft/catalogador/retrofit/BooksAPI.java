package com.abcsoft.catalogador.retrofit;

import com.abcsoft.catalogador.modelo.BookAPI.Book;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface BooksAPI {

    @GET("api/books")
    Call<Book> getBook(@QueryMap Map<String, String> params);

    @GET("api/books")
    Call<String> getRAW(@QueryMap Map<String, String> params);


}


//    @GET("api/books")
//    Call<Book> getLibro();
//    void getMyThing(@QueryMap Map<String, String> params, new Callback<String> callback);
