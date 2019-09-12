package com.abcsoft.catalogador.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.abcsoft.catalogador.model.Book.Book;
import com.abcsoft.catalogador.services.Utilidades;

import java.util.ArrayList;
import java.util.List;

//implementa las funcionalidades declaradas en el servicio BooksService a nivel sql
public class DatabaseHelper extends SQLiteOpenHelper {

    //Nombre de la bbdd
    public static final String DATABASE_NAME = "catalogador.db";
    //path -> data/data/com.abcsoft.catalogador/databases/catalogador.db

    //Nombre de la tabla
    public static final String TABLE_NAME = "BOOKS";

    //Columnas de la tabla
    public static final String COL_0_TAG ="ID";
    public static final String COL_1_TAG ="DATE";
    public static final String COL_2_TAG ="TITLE";
    public static final String COL_3_TAG ="AUTHOR";
    public static final String COL_4_TAG ="ISBN";
    public static final String COL_5_TAG ="PUBLISHER";
    public static final String COL_6_TAG ="YEAR";
    public static final String COL_7_TAG ="PRICE";
    public static final String COL_8_TAG ="LONGITUD";
    public static final String COL_9_TAG ="LATITUD";
    public static final String COL_10_TAG ="PLACE";
    public static final String COL_11_TAG ="PAGES";
    public static final String COL_12_TAG ="COVERLINK";
    public static final String COL_13_TAG ="FOUND";
    public static final String COL_14_TAG ="NOTES";

    public static final String COL_0_TYPE ="INTEGER";
    public static final String COL_1_TYPE ="TEXT";  // ??
    public static final String COL_2_TYPE ="TEXT";
    public static final String COL_3_TYPE ="TEXT";
    public static final String COL_4_TYPE ="TEXT";
    public static final String COL_5_TYPE ="TEXT";
    public static final String COL_6_TYPE ="TEXT";
    public static final String COL_7_TYPE ="REAL";
    public static final String COL_8_TYPE ="REAL";
    public static final String COL_9_TYPE ="REAL";
    public static final String COL_10_TYPE ="TEXT";
    public static final String COL_11_TYPE ="INTEGER";
    public static final String COL_12_TYPE ="TEXT";
    public static final String COL_13_TYPE ="INTEGER";
    public static final String COL_14_TYPE ="TEXT";

    public static final String TABLE2_NAME = "IMAGES";
    public static final String COL2_0_TAG ="ID";
    public static final String COL2_1_TAG ="LINK";
    public static final String COL2_2_TAG ="IMAGE";
    public static final String COL2_0_TYPE ="INTEGER";
    public static final String COL2_1_TYPE ="TEXT";
    public static final String COL2_2_TYPE ="BLOB";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder strSQL = new StringBuilder();
        //Tabla 1
        strSQL.append("CREATE TABLE ").append(TABLE_NAME).append(" (")
                .append(COL_0_TAG).append(" ").append(COL_0_TYPE).append(" PRIMARY KEY AUTOINCREMENT, ") //.append(" PRIMARY KEY AUTOINCREMENT, ")
                .append(COL_1_TAG).append(" ").append(COL_1_TYPE).append(", ") //.append(" NOT NULL, ")
                .append(COL_2_TAG).append(" ").append(COL_2_TYPE).append(", ")
                .append(COL_3_TAG).append(" ").append(COL_3_TYPE).append(", ")
                .append(COL_4_TAG).append(" ").append(COL_4_TYPE).append(", ") //.append(" NOT NULL, ")
                .append(COL_5_TAG).append(" ").append(COL_5_TYPE).append(", ")
                .append(COL_6_TAG).append(" ").append(COL_6_TYPE).append(", ")
                .append(COL_7_TAG).append(" ").append(COL_7_TYPE).append(", ")
                .append(COL_8_TAG).append(" ").append(COL_8_TYPE).append(", ")
                .append(COL_9_TAG).append(" ").append(COL_9_TYPE).append(", ")
                .append(COL_10_TAG).append(" ").append(COL_10_TYPE).append(", ")
                .append(COL_11_TAG).append(" ").append(COL_11_TYPE).append(", ")
                .append(COL_12_TAG).append(" ").append(COL_12_TYPE).append(", ")
                .append(COL_13_TAG).append(" ").append(COL_13_TYPE).append(", ")
                .append(COL_14_TAG).append(" ").append(COL_14_TYPE)
                .append(");");

        //Tabla 2
        strSQL.append("CREATE TABLE ").append(TABLE2_NAME).append(" (")
                .append(COL2_0_TAG).append(" ").append(COL2_0_TYPE).append(" PRIMARY KEY AUTOINCREMENT, ") //.append(" PRIMARY KEY AUTOINCREMENT, ")
                .append(COL2_1_TAG).append(" ").append(COL2_1_TYPE).append(", ")
                .append(COL2_2_TAG).append(" ").append(COL2_2_TYPE)
                .append(");");

        db.execSQL(strSQL.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //Elimina la tabla
        onCreate(db); //Reconstruye la tabla desde 0. Adios a los datos
    }

    public Book createBook(Book book){
        //Necesito una referencia de acceso a la bbdd
        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea

        //CON db.beginTransaction() no guarda datos a la bbdd
//        db.beginTransaction();//Inicia transaccion.Sirve para garantizar la consistencia de la bbdd en caso de problemas

        long id = db.insert(TABLE_NAME,null, book2contentvalues(book));
        //db.insert devulve un long correspondiente al número de registros. Equivale al codigo
        //nullColumnHack se utiliza cuando queremos insertar un registro con valores null

//        db.endTransaction(); //Cierra el beginTransaction
        db.close();

        //Si codigo = -1, indica que algo ha ido mal
        //Si codigo >= 0, indica el numero de registros afectados
//        if (id > 0) {
//            lectura.setCodigo((int) id);
//        }
        return id == -1 ? null : book;
    }

    public Book readBook(Long id){
        //Modifica el libro con un id concreto
        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea
        //Mediante rawQuery
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_0_TAG + "=" + id, null);
        Book book = cursorToBook(cursor);
        db.close();
        return book;
    }

    public Book updateBook(Book book){
        //Modifica el libro con un id concreto
        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea
        db.update(TABLE_NAME, book2contentvalues(book), COL_0_TAG + "=" + book.getId(), null);
        db.close();
        //TODO return?
        return book;
    }

    public Boolean deleteBook(Long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_0_TAG + "=" + id, null);
//        //Mediante rawQuery
//        Cursor cursor = db.rawQuery("DELETE FROM " + TABLE_NAME + " WHERE " + COL_0_TAG + " ='" + id + "'", null);
        //TODO return?
        return Boolean.TRUE;
    }

    public List<Book> getAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        //Mediante rawQuery
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_1_TAG + " ASC", null);
        //TODO Pedir solo los campos basicos. pero afecta a cursor 2 book

        /*
        //Mediante query
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COL_0_TAG, COL_1_TAG, COL_2_TAG, COL_3_TAG, COL_4_TAG, COL_5_TAG, COL_6_TAG},
                null,
                null,
                null,
                null,
                COL_1_TAG,
                null
        );
        */
        return cursorToBookList(cursor);
    }

    public Long insertImage( String imageLink) throws SQLiteException {

        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap b = Utilidades.getBitmapFromURL(imageLink);
        byte[] image = Utilidades.getBytes(b);

        ContentValues cv = new  ContentValues();
        cv.put(COL2_1_TAG, imageLink);
        cv.put(COL2_2_TAG, image);

        long id = db.insert(TABLE2_NAME,null, cv);
        db.close();

        return id == -1 ? null : id;
    }





//***************************************************************************************
//************************                                     **************************
//************************          Métodos privados           **************************
//************************                                     **************************
//***************************************************************************************


    //Conveirte los tipos de java a sqlite y transfiere los campos de book a un contenedor de valores
    private ContentValues book2contentvalues(Book book){
        //Creo un contenedor de valores y transformo los campos de book a los tipos de sqlite
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_TAG, Utilidades.getStringFromDate(book.getDateCreation()));
        contentValues.put(COL_2_TAG, book.getTitle());
        contentValues.put(COL_3_TAG, book.getAuthor());
        contentValues.put(COL_4_TAG, book.getIsbn());
        contentValues.put(COL_5_TAG, book.getPublisher());
        contentValues.put(COL_6_TAG, book.getPublishDate());
        contentValues.put(COL_7_TAG, book.getPrice());
        contentValues.put(COL_8_TAG, book.getLongitud());
        contentValues.put(COL_9_TAG, book.getLatitud());
        contentValues.put(COL_10_TAG, book.getPublishPlace());
        contentValues.put(COL_11_TAG, book.getNumPages());
        contentValues.put(COL_12_TAG, book.getCoverLink());
        contentValues.put(COL_13_TAG, Utilidades.getIntegerFromBoolean(book.getFound()));
        contentValues.put(COL_14_TAG, book.getNotes());
        insertImage(book.getCoverLink()); //guardar la id que devuelve
        return contentValues;
    }

    //Convierte un cursor de la tabla lecturas a una List de Books
    private Book cursor2book(Cursor cursor){
        Book book = new Book(); //Añado un libro nuevo en cada iteración
        book.setId(cursor.getInt(0));
        book.setDateCreation(Utilidades.getDateFromString(cursor.getString(1)));
        book.setTitle(cursor.getString(2));
        book.setAuthor(cursor.getString(3));
        book.setIsbn(cursor.getString(4));
        book.setPublisher(cursor.getString(5));
        book.setPublishDate(cursor.getString(6));
        book.setPrice(cursor.getDouble(7));
        book.setLongitud(cursor.getDouble(8));
        book.setLatitud(cursor.getDouble(9));
        book.setPublishPlace(cursor.getString(10));
        book.setNumPages(cursor.getInt(11));
        book.setCoverLink(cursor.getString(12));
        book.setFound(Utilidades.getBooleanFromInteger(cursor.getInt(13)));
        book.setNotes(cursor.getString(14));
        return book;
    }

    private Book cursorToBook(Cursor cursor){
        Book book = new Book();
        //Verifico que el cursor no esté vacio
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            book = cursor2book(cursor);
        }
        cursor.close();
        return book;
    }

    //Convierte un cursor de la tabla lecturas a una List de Books
    private List<Book> cursorToBookList(Cursor cursor){
        List<Book> books = new ArrayList<>();
//        Book book;

        //Verifico que el cursor no esté vacio
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
//                book = new Book(); //Añado un libro nuevo en cada iteración
//                book.setId(cursor.getInt(0));
//                book.setDateCreation(Utilidades.getDateFromString(cursor.getString(1)));
//                book.setTitle(cursor.getString(2));
//                book.setAuthor(cursor.getString(3));
//                book.setIsbn(cursor.getString(4));
//                book.setPublisher(cursor.getString(5));
//                book.setPublishDate(cursor.getString(6));
//                book.setPrice(cursor.getDouble(7));
//                book.setLongitud(cursor.getDouble(8));
//                book.setLatitud(cursor.getDouble(9));
//                book.setPublishPlace(cursor.getString(10));
//                book.setNumPages(cursor.getInt(11));
//                book.setCoverLink(cursor.getString(12));
//                book.setFound(Utilidades.getBooleanFromInteger(cursor.getInt(13)));
                books.add(cursor2book(cursor));
            }
        }
        cursor.close();
        return books;
    }


}
