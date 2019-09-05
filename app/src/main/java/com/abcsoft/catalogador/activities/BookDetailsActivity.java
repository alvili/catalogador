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
import com.squareup.picasso.Picasso;

import java.util.Date;

public class BookDetailsActivity extends AppCompatActivity {

    private BooksServicesSQLite bookServices;
    private Book book = new Book();

    private TextView isbn;
    private TextView title;
    private TextView year;
    private TextView publisher;
    private TextView author;
    private TextView numPags;
    private TextView price;
    private TextView notes;
    private ImageView cover;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);

        //Referencio la vista
        title = (TextView) findViewById(R.id.idTitle);
        isbn = (TextView) findViewById(R.id.idIsbn);
        year = (TextView) findViewById(R.id.idYear);
        publisher = (TextView) findViewById(R.id.idPublisher);
        author = (TextView) findViewById(R.id.idAuthor);
        numPags = (TextView) findViewById(R.id.idNumPags);
        price = (TextView) findViewById(R.id.idPrice);
        notes = (TextView) findViewById(R.id.idNotes);
        cover = (ImageView) findViewById(R.id.idCover);
        guardar = (Button) findViewById(R.id.idBtnGuardarLibro);

        //Recogemos los datos enviados por el intent
        readBundle(getIntent().getExtras());

        //Traslado los datos a los campos
        mostrarCampos();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardo a la bbdd local
                guardarDatos();

                //Vuelvo a la vista principal
                Intent intent = new Intent(BookDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void readBundle(Bundle b){
        //Solo si bundle no es null pido los datos
        if(b != null) {
            book.setIsbn(b.getString("isbn"));
            book.setTitle(b.getString("title"));
            book.setAuthor(b.getString("author"));
            book.setPublisher(b.getString("publisher"));
            book.setYear(b.getString("year"));
            book.setPublishPlace(b.getString("place"));
            book.setCoverLink(b.getString("cover"));
            book.setNumPages(b.getInt("pags"));
        }
    }

    private void mostrarCampos(){
        if (validate(book.getTitle())) {title.setText(book.getTitle());}
        if (validate(book.getIsbn())) {isbn.setText(book.getIsbn());}
        if (validate(book.getYear())) {year.setText(book.getYear() + ", " + book.getPublishPlace());}
        if (validate(book.getPublisher())) {publisher.setText(book.getPublisher());}
        if (validate(book.getAuthor())) {author.setText(book.getAuthor());}
        numPags.setText(String.valueOf(book.getNumPages()));
        if (validate(book.getCoverLink())) {
            if (!book.getCoverLink().equals("")) {
                Picasso.get().load(book.getCoverLink()).into(cover);
            }
        }
    }

    private boolean validate (String str){
        return (str != null && !str.isEmpty());
    }

    public void guardarDatos(){
        //Actualizo book con los datos del formulario

        book.setIsbn(isbn.getText().toString());
        book.setTitle(title.getText().toString());
        book.setAuthor(author.getText().toString());
        book.setPublisher(publisher.getText().toString());
        book.setYear(year.getText().toString());
//        book.setPublishPlace(place.getText().toString());
        book.setNumPages(Integer.parseInt(numPags.getText().toString()));

        book.setDate(new Date());
        book.setNotes(notes.getText().toString());
        book.setPrice(Double.parseDouble(price.getText().toString()));
        book.setLongitud(0.0);
        book.setLatitud(0.0);

        bookServices = new BooksServicesSQLite(getApplicationContext());
        bookServices.create(book);

        //MODIFICAR SI YA EXISTE

    }

}
