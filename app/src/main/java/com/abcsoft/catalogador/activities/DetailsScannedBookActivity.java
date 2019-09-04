package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.model.BookAPI.BookOL;
import com.abcsoft.catalogador.model.BookLocal.BookLocal;
import com.abcsoft.catalogador.retrofit.BooksAPI;
import com.abcsoft.catalogador.retrofit.RetrofitHelper;
import com.abcsoft.catalogador.services.BooksServicesSQLite;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsScannedBookActivity extends AppCompatActivity {

    private BooksAPI jsonPlaceHolderApi_books;
    private BooksServicesSQLite bookServices;
    private BookOL bookinfo;
    private BookLocal book = new BookLocal();

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

        jsonPlaceHolderApi_books = RetrofitHelper.getBooksAPIsc();

        //Recogemos los datos enviados por el intent
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        //Solo si bundle no es null pido los datos
        if(b != null){

            book.setIsbn(String.valueOf(b.getString("isbn")));
            book.setTitle(String.valueOf(b.getString("title")));
            book.setAuthor(String.valueOf(b.getString("author")));
            book.setPublisher(String.valueOf(b.getString("publisher")));
            book.setYear(String.valueOf(b.getString("year")));
            book.setPublishPlace(String.valueOf(b.getString("place")));
            book.setCoverLink(String.valueOf(b.getString("cover")));
            book.setNumPages(Integer.valueOf(b.getString("pags")));
            mostrarCampos();

        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardo a la bbdd local
                guardarDatosAdicionales(); //Hauria de guardar-ho tot

                //Vuelvo a la vista principal
                Intent intent = new Intent(DetailsScannedBookActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


//    private void getRaw(final String bookIsbn){
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("bibkeys", "ISBN:" + bookIsbn );
//        params.put("jscmd", "data");
//        params.put("format", "json");
//
//        Call<String> call = jsonPlaceHolderApi_books.getRAW(params);
//
//        call.enqueue(new Callback<String>() {
//
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if (!response.isSuccessful()){
//                    //???
//                    return;
//                }
//
//                //https://openlibrary.org/api/books?format=json&bibkeys=ISBN%3A9788408085614&jscmd=data
//
//                //Recupero los datos e inflo la vista
//                String raw = response.body();
//
//                if (!raw.equals("{}")) {
//                    //Modifico el formato de los datos para poder leerlos con "fromJson"
//                    raw = raw.replaceFirst(":" + bookIsbn, "")
//                            .replaceFirst("ISBN", "isbn");
//
//                    Gson gson = new Gson();
//                    bookinfo = new Gson().fromJson(raw, Book.class); //Rellena el modelo con los datos del json
//                    bookinfo.setIsbncode(bookIsbn); //isbn no esta entre los datos que vienen por json
//
//                    //Muestro los datos
//                    extraerDatos();
//                    mostrarCampos();
//                } else { //No arriba res
//                    //Toast("")
//                    isbn.setText("NOT FOUND");
//                    reiniciarDatos();
////                    ocultarcampos();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                //???
//                Log.d("**","Error!!!");
//            }
//        });
//
//    }

    private void mostrarCampos(){
        title.setText(book.getTitle());
        isbn.setText(book.getIsbn());
        year.setText(book.getYear() + ", " + book.getPublishPlace());
        publisher.setText(book.getPublisher());
        author.setText(book.getAuthor());
        numPags.setText(String.valueOf(book.getNumPages()));
        if (!book.getCoverLink().equals("")) {
            Picasso.get().load(book.getCoverLink()).into(cover);
        }
    }

//    public void reiniciarDatos() {
//        book.setIsbn("");
//        book.setTitle("");
//        book.setAuthor("");
//        book.setPublisher("");
//        book.setYear("");
//        book.setPublishPlace("");
//        book.setNumPages(0);
//        book.setCoverLink("");
//    }

//    public void extraerDatos() {
////        BookLocal book = new BookLocal();
//        //Extraigo algunos datos seleccionados del modelo json
//        //Mover a una clase dedicada a eso
//        book.setIsbn(bookinfo.getIsbncode());
//        book.setTitle(bookinfo.getIsbn().getTitle());
//        book.setAuthor(bookinfo.getIsbn().getAuthors().get(0).getName());
//        book.setPublisher(bookinfo.getIsbn().getPublishers().get(0).getName());
//        book.setYear(bookinfo.getIsbn().getPublish_date());
//        if (bookinfo.getIsbn().getPublish_places() != null && !bookinfo.getIsbn().getPublish_places().isEmpty()) {
//            book.setPublishPlace(bookinfo.getIsbn().getPublish_places().get(0).getName());
//        } else {
//            book.setPublishPlace("");
//        }
//        book.setCoverLink(bookinfo.getIsbn().getCover().getLarge());
//        book.setNumPages(bookinfo.getIsbn().getNumber_of_pages().intValue());
//    }

    public void guardarDatosAdicionales(){
        book.setDate(new Date());
        book.setNotes("");
        book.setPrice(0.0);
        book.setLongitud(0.0);
        book.setLatitud(0.0);

        bookServices = new BooksServicesSQLite(getApplicationContext());
        bookServices.create(book);

        //MODIFICAR SI YA EXISTE

    }

}
