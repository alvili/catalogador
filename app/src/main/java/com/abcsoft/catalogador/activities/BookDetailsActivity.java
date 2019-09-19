package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.model.Book.Book;
import com.abcsoft.catalogador.model.ScanDetails;
import com.abcsoft.catalogador.services.BooksServicesSQLite;
import com.abcsoft.catalogador.services.ImageDownloadTask;
import com.abcsoft.catalogador.services.Utilidades;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class BookDetailsActivity extends AppCompatActivity {

    private BooksServicesSQLite bookServices;
    //private Book book = new Book();
    private ScanDetails scan = new ScanDetails();

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
    private Button guardar;
    private Button borrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);

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
        guardar = (Button) findViewById(R.id.idBtnGuardarLibro);
        borrar = (Button) findViewById(R.id.idBtnBorrarLibro);

        bookServices = new BooksServicesSQLite(getApplicationContext());

        //Recogemos los datos enviados por el intent
        scan.importFromBundle(getIntent().getExtras());

        //Traslado los datos a los campos
        scanDetailsToForm(getIntent().getExtras().getString("ORIGIN"));

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardo a la bbdd local
                formToScanDetails();
                bookToBBDD();

                //Vuelvo a la vista principal
                gotoPrincipal();
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //borra el elemento
                bookServices.delete(scan.getBook().getId());

                //Vuelvo a la lista
                gotoList();
            }
        });
    }

    private void scanDetailsToForm(String ORIGEN) {

        found.setText("NOT FOUND");
        found.setVisibility((scan.getFound()) ? View.INVISIBLE : View.VISIBLE);

        //TODO contemplar scan repetidos
        switch(ORIGEN) {
            case "scan":
                guardar.setText("SAVE");
                borrar.setVisibility(View.INVISIBLE);
                break;
            case "list":
                guardar.setText("UPDATE");
                borrar.setVisibility(View.VISIBLE);
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
        cover.setImageBitmap(scan.getBook().getCover().getImage());
        price.setText(String.valueOf(scan.getPrice()));
        notes.setText(scan.getNotes());

//        if (validate(book.getTitle())) {
//            title.setText(book.getTitle());
//        }
//        if (validate(book.getIsbn())) {
//            isbn.setText(book.getIsbn());
//        }
//        if (validate(book.getPublishDate())) {
//            publishDate.setText(book.getPublishDate());
//        }
//        if (validate(book.getPublishPlace())) {
//            publishPlace.setText(book.getPublishPlace());
//        }
//
//        if (validate(book.getPublisher())) {
//            publisher.setText(book.getPublisher());
//        }
//        if (validate(book.getAuthor())) {
//            author.setText(book.getAuthor());
//        }
//        numPags.setText(String.valueOf(book.getNumPages()));
//        if (validate(book.getCoverLink())) {
//            if (!book.getCoverLink().equals("")) {
//                //Picasso.get().load(book.getCoverLink()).into(cover);
//
////                ImageDownloadTask miAsyncTask = new ImageDownloadTask(cover, book);
////                miAsyncTask.execute(book.getCoverLink());
//                cover.setImageBitmap(book.getCover().getImage());
//            }
//        }
//        price.setText(String.valueOf(book.getPrice()));
//        notes.setText(book.getNotes());

    }

//    private boolean validate(String str) {
//        return (str != null && !str.isEmpty());
//    }

    public void formToScanDetails() {
        //Actualizo book con los datos del formulario

        scan.getBook().setIsbn(isbn.getText().toString());
        scan.getBook().setTitle(title.getText().toString());
        scan.getBook().setAuthor(author.getText().toString());
        scan.getBook().setPublisher(publisher.getText().toString());
        scan.getBook().setPublishDate(publishDate.getText().toString());
        scan.getBook().setPublishPlace(publishPlace.getText().toString());
        scan.getBook().setNumPages(Integer.parseInt(numPags.getText().toString()));

        scan.setDateCreation(new Date());
        scan.setNotes(notes.getText().toString());
        scan.setPrice(Double.parseDouble(price.getText().toString()));
        scan.setLongitud(0.0);
        scan.setLatitud(0.0);
        //MODIFICAR SI YA EXISTE
    }

    public void bookToBBDD() {
//        bookServices = new BooksServicesSQLite(getApplicationContext());
        bookServices.create(scan.getBook());
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
