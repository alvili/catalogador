package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.services.Utilidades;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Utilidades.getBitmapFromURL("https://covers.openlibrary.org/b/id/8738451-L.jpg");
//        Utilidades.getBitmapFromURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3jbh9qdb7M7z8fHRDtVv8wqze-ECJnlX7BP_Qafh9iJAqfiOW");
        Button escanear = findViewById(R.id.idBtnEscanear);
        Button catalogo = findViewById(R.id.idBtnCatalogo);
        Button sync = findViewById(R.id.idBtnSync);


        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });

        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO sincronizar bbdd sqlite con la bbdd del servidor REST
            }
        });

    }
}
