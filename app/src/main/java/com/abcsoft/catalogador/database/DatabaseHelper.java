    package com.abcsoft.catalogador.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.abcsoft.catalogador.model.BookLocal.BookLocal;
import com.abcsoft.catalogador.services.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Nombre de la bbdd
    public static final String DATABASE_NAME = "catalogador.db";
    //data/data/com.abcsoft.catalogador/databases/catalogador.db

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


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder strSQL = new StringBuilder();
        //Revisar la estructura
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
                .append(COL_9_TAG).append(" ").append(COL_9_TYPE).append(");");
        db.execSQL(strSQL.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //Elimina la tabla
        onCreate(db); //Reconstruye la tabla desde 0. Adios a los datos

    }

    public BookLocal createBook(BookLocal book){
        //Necesito una referencia de acceso a la bbdd
        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea

        //Necesito un contenedor de valores
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_TAG, Utilidades.getStringFromDate(book.getDate()));
        contentValues.put(COL_2_TAG, book.getTitle());
        contentValues.put(COL_3_TAG, book.getAuthor());
        contentValues.put(COL_4_TAG, book.getIsbn());
        contentValues.put(COL_5_TAG, book.getPublisher());
        contentValues.put(COL_6_TAG, book.getYear());
        contentValues.put(COL_7_TAG, book.getPrice());
        contentValues.put(COL_8_TAG, book.getLongitud());
        contentValues.put(COL_9_TAG, book.getLatitud());

        //CON db.beginTransaction() no guarda datos a la bbdd
//        db.beginTransaction();//Inicia transaccion.Sirve para garantizar la consistencia de la bbdd en caso de problemas

        long id = db.insert(TABLE_NAME,null, contentValues);
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

    public List<BookLocal> getAll(){

            //Conexión a la bbdd
            SQLiteDatabase db = this.getWritableDatabase();

            //Mediante rawQuery
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_2_TAG + " DESC", null);
//            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
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
            return cursorBookToList(cursor);
    }


//***************************************************************************************
//************************                                     **************************
//************************          Métodos privados           **************************
//************************                                     **************************
//***************************************************************************************

        //Convierte un cursor de la tabla lecturas a una List
        private List<BookLocal> cursorBookToList(Cursor cursor){
            List<BookLocal> books = new ArrayList<>();
            BookLocal book;

            //Verifico que el cursor no esté vacio
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    book = new BookLocal();
                    book.setDate(Utilidades.getDateFromString(cursor.getString(1)));
                    book.setTitle(cursor.getString(2));
                    book.setAuthor(cursor.getString(3));
                    book.setIsbn(cursor.getString(4));
                    book.setPublisher(cursor.getString(5));
                    book.setYear(cursor.getString(6));
                    book.setPrice(cursor.getDouble(7));
                    book.setLongitud(cursor.getDouble(8));
                    book.setLatitud(cursor.getDouble(9));
                    books.add(book);
                }
            }
            cursor.close();
            return books;
        }

    }
