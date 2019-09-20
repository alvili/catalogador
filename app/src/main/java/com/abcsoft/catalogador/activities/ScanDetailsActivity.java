package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.model.Local.Scan;
import com.abcsoft.catalogador.services.ScansServicesSQLite;
import com.abcsoft.catalogador.services.ImageDownloadTask;

import java.util.Date;

public class ScanDetailsActivity extends AppCompatActivity {

    private ScansServicesSQLite scansServices;
    private Scan scan = new Scan();

    private TextView found;
    private TextView isbn;
    private TextView title;
    private TextView publishDate;
    private TextView publishPlace;
    private TextView publisher;
    private TextView author;
    private TextView numPags;
    private TextView price;
    private TextView notes;
    private ImageView cover;
    private Button create;
    private Button update;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scandetails);

        //Referencio la vista
        found = (TextView) findViewById(R.id.idFound);
        title = (TextView) findViewById(R.id.idTitle);
        isbn = (TextView) findViewById(R.id.idIsbn);
        publishDate = (TextView) findViewById(R.id.idDate);
        publishPlace = (TextView) findViewById(R.id.idPlace);
        publisher = (TextView) findViewById(R.id.idPublisher);
        author = (TextView) findViewById(R.id.idAuthor);
        numPags = (TextView) findViewById(R.id.idNumPags);
        price = (TextView) findViewById(R.id.idPrice);
        notes = (TextView) findViewById(R.id.idNotes);
        cover = (ImageView) findViewById(R.id.idCover);
        create = (Button) findViewById(R.id.idBtnCreate);
        update = (Button) findViewById(R.id.idBtnUpdate);
        delete = (Button) findViewById(R.id.idBtnDelete);

        scansServices = new ScansServicesSQLite(getApplicationContext());

        //Recogemos los datos enviados por el intent
        scan.importFromBundle(getIntent().getExtras()); //Los campos no editables toman su valor aquí

        //Transfiero los datos a los campos
        scanToForm(getIntent().getExtras().getString("ORIGIN"));

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardo los datos a la bbdd local como un nuevo elemento
                formToScan();
                scansServices.create(scan);

                //Vuelvo a la vista principal
                gotoPrincipal();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //borra el elemento de la bbdd local
                scansServices.update(scan);

                //Vuelvo a la lista
                gotoList();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //borra el elemento de la bbdd local
                scansServices.delete(scan.getBook().getId()); //TODO boorar scan o borrar libro?

                //Vuelvo a la lista
                gotoList();
            }
        });
    }

    private void scanToForm(String ORIGEN) {
        //Preparo la vista en función de si se viene de un nuevo escaneo o de la lista de escaneos

        found.setText(R.string.notfound);
        found.setVisibility((scan.getFound()) ? View.INVISIBLE : View.VISIBLE);

        switch(ORIGEN) {
            case "scan":
                create.setVisibility(View.VISIBLE);
                update.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);

                //TODO contemplar scan repetidos
                //si ya existe
//        found.setText(R.string.alreadyscanned);

                //Descargo la carátula
                ImageDownloadTask miAsyncTask = new ImageDownloadTask(scan,cover);
                miAsyncTask.execute(scan.getBook().getCover().getLink());
                break;
            case "list":
                create.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                cover.setImageBitmap(scan.getBook().getCover().getImage());
                break;
            default:
                break;
        }
        title.setText(scan.getBook().getTitle());
        isbn.setText(scan.getBook().getIsbn());
        publishDate.setText(scan.getBook().getPublishDate());
        publishPlace.setText(scan.getBook().getPublishPlace());
        publisher.setText(scan.getBook().getPublisher());
        author.setText(scan.getBook().getAuthor());
        numPags.setText(String.valueOf(scan.getBook().getNumPages()));
        price.setText(String.valueOf(scan.getPrice()));
        notes.setText(scan.getNotes());
    }

    public void formToScan() {
        scan.getBook().setIsbn(isbn.getText().toString());
        scan.getBook().setTitle(title.getText().toString());
        scan.getBook().setAuthor(author.getText().toString());
        scan.getBook().setPublisher(publisher.getText().toString());
        scan.getBook().setPublishDate(publishDate.getText().toString());
        scan.getBook().setPublishPlace(publishPlace.getText().toString());
        scan.getBook().setNumPages(Integer.parseInt(numPags.getText().toString()));

        scan.setNotes(notes.getText().toString());
        scan.setPrice(Double.parseDouble(price.getText().toString()));
        scan.setDateModified(new Date()); //Actualizo fecha de modificación
    }

    public void gotoPrincipal(){
        Intent intent = new Intent(ScanDetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void gotoList(){
        Intent intent = new Intent(ScanDetailsActivity.this, ListViewActivity.class);
        startActivity(intent);
    }

}
