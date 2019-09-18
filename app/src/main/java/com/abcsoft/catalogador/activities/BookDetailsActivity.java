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
import com.abcsoft.catalogador.services.BooksServicesSQLite;
import com.abcsoft.catalogador.services.ImageDownloadTask;
import com.abcsoft.catalogador.services.Utilidades;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class BookDetailsActivity extends AppCompatActivity {

    private BooksServicesSQLite bookServices;
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
        book.importFromBundle(getIntent().getExtras());

        //Traslado los datos a los campos
        bookToForm(getIntent().getExtras().getString("ORIGIN"));

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardo a la bbdd local
                formToBook();
                bookToBBDD();

                //Vuelvo a la vista principal
                gotoPrincipal();
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //borra el elemento
                bookServices.delete(book.getId());

                //Vuelvo a la lista
                gotoList();
            }
        });
    }

    private void bookToForm(String ORIGEN) {

        found.setText("NOT FOUND");
        found.setVisibility((book.getFound()) ? View.INVISIBLE : View.VISIBLE);

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



        if (validate(book.getTitle())) {
            title.setText(book.getTitle());
        }
        if (validate(book.getIsbn())) {
            isbn.setText(book.getIsbn());
        }
        if (validate(book.getPublishDate())) {
            publishDate.setText(book.getPublishDate());
        }
        if (validate(book.getPublishPlace())) {
            publishPlace.setText(book.getPublishPlace());
        }

        if (validate(book.getPublisher())) {
            publisher.setText(book.getPublisher());
        }
        if (validate(book.getAuthor())) {
            author.setText(book.getAuthor());
        }
        numPags.setText(String.valueOf(book.getNumPages()));
        if (validate(book.getCoverLink())) {
            if (!book.getCoverLink().equals("")) {
                //Picasso.get().load(book.getCoverLink()).into(cover);


                ImageDownloadTask miAsyncTask = new ImageDownloadTask(cover, book);
                miAsyncTask.execute(book.getCoverLink());
            }
        }
        price.setText(String.valueOf(book.getPrice()));
        notes.setText(book.getNotes());

    }

    private boolean validate(String str) {
        return (str != null && !str.isEmpty());
    }

    public void formToBook() {
        //Actualizo book con los datos del formulario

        book.setIsbn(isbn.getText().toString());
        book.setTitle(title.getText().toString());
        book.setAuthor(author.getText().toString());
        book.setPublisher(publisher.getText().toString());
        book.setPublishDate(publishDate.getText().toString());
        book.setPublishPlace(publishPlace.getText().toString());
        book.setNumPages(Integer.parseInt(numPags.getText().toString()));

        book.setDateCreation(new Date());
        book.setNotes(notes.getText().toString());
        book.setPrice(Double.parseDouble(price.getText().toString()));
        book.setLongitud(0.0);
        book.setLatitud(0.0);
        //MODIFICAR SI YA EXISTE
    }

    public void bookToBBDD() {
//        bookServices = new BooksServicesSQLite(getApplicationContext());
        bookServices.create(book);
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
