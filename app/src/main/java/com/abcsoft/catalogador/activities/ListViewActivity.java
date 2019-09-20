package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.adapters.BooksAdapter;
import com.abcsoft.catalogador.model.Local.Scan;
import com.abcsoft.catalogador.services.ScansServices;
import com.abcsoft.catalogador.services.ScansServicesSQLite;

import java.util.List;

public class ListViewActivity  extends AppCompatActivity {

    List<Scan> scans;
    ListView listScans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listScans = (ListView) findViewById(R.id.idListView);

        //Recupero la lista de todos los libros...
        final ScansServices scansServices = new ScansServicesSQLite(this);
        scans = scansServices.getAll();

        //...y los paso a un adaptador para que rellene la lista de libros...
        listScans.setAdapter(new BooksAdapter(this, scans));

        //...y muestre los datos de uno concreto cuando se seleccione
        listScans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Recupero los datos del libro de la bbdd local sqlite

                //Lanzo un intent y transfiero detalles del libro en un bundle
                Intent intent = new Intent(ListViewActivity.this, ScanDetailsActivity.class);
//                intent.putExtras(books.get(position).exportToBundle());
                Bundle b = new Bundle();
                //Exporto los datos del Item seleccionado en un bundle
                scansServices.read(scans.get(position).getId()).exportToBundle(b);
                intent.putExtras(b);
                intent.putExtra("ORIGIN","list");
                startActivity(intent);
            }
        });

    }

}
