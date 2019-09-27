package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.model.Local.Book;
import com.abcsoft.catalogador.services.MediaServicesSQLite;
import com.abcsoft.catalogador.services.ImageDownloadTask;

import java.util.Date;

public class BookDetailsActivity extends AppCompatActivity {

    private MediaServicesSQLite scansServices;
    private Book book = new Book();

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

        scansServices = new MediaServicesSQLite(getApplicationContext());

        //Recogemos los datos enviados por el intent
        Bundle b = getIntent().getExtras();

//        book.importFromBundle(getIntent().getExtras()); //Los campos no editables toman su valor aquí
        book.importFromBundle(b); //Los campos no editables toman su valor aquí

        //Transfiero los datos a los campos
        scanToForm(getIntent().getExtras().getString("ORIGIN"));

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardo los datos a la bbdd local como un nuevo elemento
                formToScan();
                scansServices.create(book);

                //Vuelvo a la vista principal
                gotoPrincipal();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //borra el elemento de la bbdd local
                scansServices.update(book);

                //Vuelvo a la lista
                gotoList();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //borra el elemento de la bbdd local
                scansServices.delete(book.getMediaId()); //TODO boorar scan o borrar libro?

                //Vuelvo a la lista
                gotoList();
            }
        });
    }

    private void scanToForm(String ORIGEN) {
        //Preparo la vista en función de si se viene de un nuevo escaneo o de la lista de escaneos

        found.setText(R.string.notfound);
        found.setVisibility((book.getFound()) ? View.INVISIBLE : View.VISIBLE);

        switch(ORIGEN) {
            case "scan":
                create.setVisibility(View.VISIBLE);
                update.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);

                //TODO contemplar scan repetidos
                //si ya existe
//        found.setText(R.string.alreadyscanned);

                //Descargo la carátula
                ImageDownloadTask miAsyncTask = new ImageDownloadTask(book,cover);
                miAsyncTask.execute(book.getCover().getLink());
                break;
            case "list":
                create.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                cover.setImageBitmap(book.getCover().getImage());
                break;
            default:
                break;
        }
        isbn.setText(book.getIsbn());
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
        publishDate.setText(book.getPublishDate());
        publishPlace.setText(book.getPublishPlace());
        numPags.setText(String.valueOf(book.getNumPages()));
        notes.setText(book.getNotes());
        price.setText(String.valueOf(book.getPrice()));
    }

    public void formToScan() {
        book.setIsbn(isbn.getText().toString());
        book.setTitle(title.getText().toString());
        book.setAuthor(author.getText().toString());
        book.setPublisher(publisher.getText().toString());
        book.setPublishDate(publishDate.getText().toString());
        book.setPublishPlace(publishPlace.getText().toString());
        book.setNumPages(Integer.parseInt(numPags.getText().toString()));
        book.setNotes(notes.getText().toString());
        book.setPrice(Double.parseDouble(price.getText().toString()));
        book.setDateModified(new Date()); //Actualizo fecha de modificación
    }

    public void gotoPrincipal(){
        Intent intent = new Intent(BookDetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void gotoList(){
        Intent intent = new Intent(BookDetailsActivity.this, ListViewActivity.class);
        startActivity(intent);
    }

}
