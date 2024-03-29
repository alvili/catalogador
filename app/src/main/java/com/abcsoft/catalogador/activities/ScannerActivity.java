package com.abcsoft.catalogador.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.model.BookAPI.BookOpenLibrary;
import com.abcsoft.catalogador.model.Local.Book;
import com.abcsoft.catalogador.retrofit.BooksAPI;
import com.abcsoft.catalogador.retrofit.RetrofitHelper;
import com.abcsoft.catalogador.services.Utilidades;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    private Book book;
    private LocationManager locationManager;
    private String providerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        //Referencio la vista
        Button scan = findViewById(R.id.id_BtnScanCode);
        final TextView code = (TextView) findViewById(R.id.idTextCode);

        //Recupero la imagen
        Bitmap barcodePicture = Utilidades.getBitmap(getIntent().getExtras().getByteArray("barcodePicture"));

        code.setText("9788408085614");
//        code.setText("9781101965481");
//        code.setText("8439596065");

        //Creo un nuevo libro
        //TODO Adaptar a otros tipos de media
        book = new Book(code.getText().toString());
        book.setBarcodePicture(barcodePicture);

        //Determino la posicion
        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        providerName = locationManager.getBestProvider(criteria, false);

        if (providerName != null && !providerName.equals("")) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.d("**","No hay permiso");
                return;
            }
            Location location = locationManager.getLastKnownLocation(providerName);

            if (location != null) {
                Log.d("**","Provider: " + providerName);
                Log.d("**","Longitud: " + location.getLongitude());
                Log.d("**","Latitud: " + location.getLatitude());
                book.setLongitud(location.getLongitude());
                book.setLatitud(location.getLatitude());
            } else {
                Log.d("**","No hay provider");
            }

        }

        //TODO:
        //1) Obtener ISBN
        //2) Comprobar si ya existe
        //3) Si nuevo, cargar datos de OpenLibrary
        //4) Si ya existia, cargar datos de la bbdd
        //5) (preguntar? dar opcion?)

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            ScannerActivity.this.book = new Book(code.getText().toString());
            ScannerActivity.this.book.setBarcode(code.getText().toString());
            //Pido los datos al servicio REST de OpenLibrary
            getRawOpenLibraryData(ScannerActivity.this.book.getIsbn());
            }
        });

    }

    private void getRawOpenLibraryData(final String bookIsbn){

        Map<String, String> params = new HashMap<String, String>();
        params.put("bibkeys", "ISBN:" + bookIsbn );
        params.put("jscmd", "data");
        params.put("format", "json");

        BooksAPI jsonPlaceHolderApi_books = RetrofitHelper.getBooksAPIsc();
        Call<String> call = jsonPlaceHolderApi_books.getRAW(params);
        //https://openlibrary.org/api/books?format=json&bibkeys=ISBN%3A9788408085614&jscmd=data

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (!response.isSuccessful()){
                    //???
                    return;
                }

                //Recupero los datos
                String raw = response.body();

                if (!raw.equals("{}")) {
                    //Modifico el formato de los datos para poder leerlos con "fromJson"
                    raw = raw.replaceFirst(":" + bookIsbn, "")
                             .replaceFirst("ISBN", "isbn");

                    Gson gson = new Gson();
                    BookOpenLibrary bookinfo = new Gson().fromJson(raw, BookOpenLibrary.class); //Rellena el modelo con los datos del json
                    bookinfo.setIsbncode(bookIsbn); //isbn no esta entre los datos que vienen por json

                    //Transformo los datos al formato local
                    book.importFromOpenLibrary(bookinfo);
                }
                //Cambio de activity
                mostrarInformacion();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //???
                Log.d("**","Error!!!");
            }
        });

    }

    private void mostrarInformacion(){
        //llamo a BookDetailsActivity pasandole los datos del libro en un intent
        Intent intent = new Intent(ScannerActivity.this, BookDetailsActivity.class);
        Bundle b = new Bundle();
        book.exportToBundle(b);
        intent.putExtras(b);
        intent.putExtra("ORIGIN","scan");
        startActivity(intent);
    }

}
