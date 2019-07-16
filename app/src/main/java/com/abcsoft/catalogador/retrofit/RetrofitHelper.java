package com.abcsoft.catalogador.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final String REST_URL = "https://openlibrary.org/";
    private static final Retrofit retrofit;

    static {

        //Defino adaptadores de tipo
        GsonBuilder gsonbuilder = new GsonBuilder();

        gsonbuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                //Recupero el valor como long
                long milliseconds = json.getAsJsonPrimitive().getAsLong();
                //Lo convierto a date
                return new Date(milliseconds);
            }
        });

        gsonbuilder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getTime());
            }
        });

        Gson gson = gsonbuilder.create();

        retrofit = new Retrofit.Builder()
                .baseUrl(REST_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
//        RestAdapter retrofit = new RestAdapter.Builder()
//                .setEndpoint(REST_URL)
//                .build();

    }

    private RetrofitHelper(){

    }

    public static BooksAPI getBooksAPI() {
        return retrofit.create(BooksAPI.class);
    }


}
