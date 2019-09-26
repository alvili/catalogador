package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.adapters.MediasAdapter;
import com.abcsoft.catalogador.model.Local.Media;
import com.abcsoft.catalogador.services.MediaServices;
import com.abcsoft.catalogador.services.MediaServicesSQLite;

import java.util.List;

public class ListViewActivity  extends AppCompatActivity {

    List<Media> medias;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listView = (ListView) findViewById(R.id.idListView);

        //TODO Filtrar por tipos de media
        //Recupero la lista de todos los libros...
        final MediaServices mediaServices = new MediaServicesSQLite(this);
        medias = mediaServices.getAll();

        //...y los paso a un adaptador para que rellene la lista de libros...
        listView.setAdapter(new MediasAdapter(this, medias));

        //...y muestre los datos de uno concreto cuando se seleccione
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Recupero los datos del libro de la bbdd local sqlite

                //Lanzo un intent y transfiero detalles del libro en un bundle
                Intent intent = new Intent(ListViewActivity.this, BookDetailsActivity.class);
//                intent.putExtras(books.get(position).exportToBundle());
                Bundle b = new Bundle();
                //Exporto los datos del Item seleccionado en un bundle
                mediaServices.read(medias.get(position).getScanId()).exportToBundle(b);
                intent.putExtras(b);
                intent.putExtra("ORIGIN","list");
                startActivity(intent);
            }
        });

    }

}
