package com.abcsoft.catalogador.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.abcsoft.catalogador.model.Local.Book;
import com.abcsoft.catalogador.model.Local.Cover;
import com.abcsoft.catalogador.model.Local.Media;
import com.abcsoft.catalogador.model.Local.Type;
import com.abcsoft.catalogador.services.Utilidades;

import java.util.ArrayList;
import java.util.List;

//implementa las funcionalidades declaradas en el servicio BooksService a nivel sql
public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db; //referencia a la bbdd.

    //Nombre de la bbdd
    public static final String DATABASE_NAME = "catalogador.db";
    //path -> data/data/com.abcsoft.catalogador/databases/catalogador.db

    public static final String T_SCANS = "SCANS";
    public static final String[][] T_SCANS_FIELDS = {
            {"ID", "INTEGER", "PRIMARY KEY AUTOINCREMENT"},
            {"BARCODE", "TEXT", ""},
            {"BARCODEPICTURE", "BLOB", ""},
            {"CREATED", "TEXT", ""},
            {"MODIFIED", "TEXT", ""},
            {"FOUND", "INTEGER", ""},
            {"PRICE", "TEXT", ""},
            {"NOTES", "TEXT", ""},
            {"LONGITUD", "TEXT", ""},
            {"LATITUD", "TEXT", ""},
            {"MEDIATYPE", "TEXT", ""},
            {"MEDIAID", "INTEGER", ""}
    };

    public static final String T_BOOKS = "BOOKS";
    public static final String[][] T_BOOKS_FIELDS = {
            {"ID", "INTEGER", "PRIMARY KEY AUTOINCREMENT"},
            {"ISBN", "TEXT", ""},
            {"TITLE", "TEXT", ""},
            {"AUTHOR", "TEXT", ""},
            {"PUBLISHER", "TEXT", ""},
            {"PLACE", "TEXT", ""},
            {"YEAR", "TEXT", ""},
            {"PAGES", "INTEGER", ""},
            {"COVERID", "INTEGER", ""}
    };

    //Tabla carátulas
    public static final String T_COVERS = "COVERS";
    public static final String[][] T_COVERS_FIELDS = {
            {"ID", "INTEGER", "PRIMARY KEY AUTOINCREMENT"},
            {"LINK", "TEXT", ""},
            {"IMAGE", "BLOB", ""}
    };

    //Tabla relacional
    public static final String T_BOOKS_COVERS = "BOOKS_COVERS";
    public static final String[][] T_BOOKS_COVERS_FIELDS = {
            {"bookID", "INTEGER", "NOT NULL"},
            {"coverID", "INTEGER", "NOT NULL"}
    };
    public static final String[][] T_BOOKS_COVERS_KEYS = {
            {"bookID", T_BOOKS, T_BOOKS_FIELDS[0][0]},
            {"coverID", T_COVERS, T_COVERS_FIELDS[0][0]}
    };


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
        db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura.
    }

    @Override
    //Crea las tablas vacias
    public void onCreate(SQLiteDatabase db) {
        StringBuilder strSQL = new StringBuilder();
        strSQL.setLength(0);
        strSQL.append(sqlCreateTable(T_SCANS, T_SCANS_FIELDS));
        db.execSQL(strSQL.toString());
        strSQL.setLength(0);
        strSQL.append(sqlCreateTable(T_BOOKS, T_BOOKS_FIELDS));
        db.execSQL(strSQL.toString());
        strSQL.setLength(0);
        strSQL.append(sqlCreateTable(T_COVERS, T_COVERS_FIELDS));
        db.execSQL(strSQL.toString());
//        strSQL.setLength(0);
//        strSQL.append(sqlCreateTable(T_BOOKS_COVERS, T_BOOKS_COVERS_FIELDS, T_BOOKS_COVERS_KEYS));
//        db.execSQL(strSQL.toString());

    }

    @Override
    //Elimina las tablas actuales. Adios a los datos
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        StringBuilder strSQL = new StringBuilder();
        strSQL.setLength(0);
        strSQL.append(sqlDropTable(T_SCANS));
        db.execSQL(strSQL.toString());
        strSQL.setLength(0);
        strSQL.append(sqlDropTable(T_BOOKS));
        db.execSQL(strSQL.toString());
        strSQL.setLength(0);
        strSQL.append(sqlDropTable(T_COVERS));
        db.execSQL(strSQL.toString());
//        strSQL.setLength(0);
//        strSQL.append(sqlDropTable(T_BOOKS_COVERS));
//        db.execSQL(strSQL.toString());
        onCreate(db); //Reconstruye las tablas desde cero
    }

    //Debvuelve ID de una cubierta a partir del link, considerado único
    private long getCoverID(String coverLink) {
        long coverID = -1;
//        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura.
        String sqlQuery = "SELECT " + T_COVERS_FIELDS[0][0] + " FROM " + T_COVERS + " WHERE " + T_COVERS_FIELDS[1][0] + " = \"" + coverLink + "\"";
        Cursor c = db.rawQuery(sqlQuery, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            coverID = c.getLong(0);
        }
        c.close();
//        db.close();
        return coverID;
    }

    //Inserta la cubierta a la bbdd
    private long insertCover(Cover cover) {
//        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura.
        long id = getCoverID(cover.getLink());
        //Lo inserto solo si no existe
        if (id < 0) {
            ContentValues cv = new ContentValues();
            cv.put(T_COVERS_FIELDS[1][0], cover.getLink());
            cv.put(T_COVERS_FIELDS[2][0], Utilidades.getBytes(cover.getImage()));

            id = db.insert(T_COVERS, null, cv);
//            db.close();
            cover.setCoverId(id);
        }
        return id;
    }

    //Recupera una cubierta a partir del id
    private Cover readCover(Long id) {
        Cover cover = new Cover();
//        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea
        //Mediante rawQuery
        Cursor cursor = db.rawQuery("SELECT * FROM " + T_COVERS + " WHERE " + T_COVERS_FIELDS[0][0] + "=" + id, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            cover.setCoverId(cursor.getInt(0));
            cover.setLink(cursor.getString(1));
            cover.setImage(Utilidades.getBitmap(cursor.getBlob(2)));
        }
//        db.close();
        return cover;
    }

    //Devuelve el ID de un book a partir de su isbn (considerado único)
    private long getBookID(String isbn) {
        long bookID = -1;
//        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura.
        String sqlQuery = "SELECT " + T_BOOKS_FIELDS[0][0] + " FROM " + T_BOOKS + " WHERE " + T_BOOKS_FIELDS[1][0] + " = \"" + isbn + "\"";
        Cursor c = db.rawQuery(sqlQuery, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            bookID = c.getLong(0);
        }
        c.close();
//        db.close();
        return bookID;
    }

    private Long insertBook(Book book) {
//        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea
        long id = getBookID(book.getIsbn());
        //Lo inserto solo si no existe
        if (id < 0) {
           //Inserto libro a la bbdd si ya existe devuelve id
            ContentValues cv = new ContentValues();
            cv.put(T_BOOKS_FIELDS[1][0], book.getIsbn());
            cv.put(T_BOOKS_FIELDS[2][0], book.getTitle());
            cv.put(T_BOOKS_FIELDS[3][0], book.getAuthor());
            cv.put(T_BOOKS_FIELDS[4][0], book.getPublisher());
            cv.put(T_BOOKS_FIELDS[5][0], book.getPublishPlace());
            cv.put(T_BOOKS_FIELDS[6][0], book.getPublishDate());
            cv.put(T_BOOKS_FIELDS[7][0], book.getNumPages());
            cv.put(T_BOOKS_FIELDS[8][0], insertCover(book.getCover())); //Devuelve id de la carátula
            id = db.insert(T_BOOKS, null, cv);
//            db.close();
            book.setMediaId(id);
        }
        return id;
    }

    //Recupera un libro a partir de su ID en la tabla books
    public Book readBook(Long id, Book book) {
//        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea
        //Mediante rawQuery
        Cursor cursor = db.rawQuery("SELECT * FROM " + T_BOOKS + " WHERE " + T_BOOKS_FIELDS[0][0] + "=" + book.getMediaId(), null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            book.setIsbn(cursor.getString(1));
            book.setTitle(cursor.getString(2));
            book.setAuthor(cursor.getString(3));
            book.setPublisher(cursor.getString(4));
            book.setPublishPlace(cursor.getString(5));
            book.setPublishDate(cursor.getString(6));
            book.setNumPages(cursor.getInt(7));
            book.setCover(readCover(cursor.getLong(8)));
        }
//        db.close();
        return book;
    }


    public Media insertScan(Media media) {
        //Necesito una referencia de acceso a la bbdd
//        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea

        //CON db.beginTransaction() no guarda datos a la bbdd
//        db.beginTransaction();//Inicia transaccion.Sirve para garantizar la consistencia de la bbdd en caso de problemas

        ContentValues cv = new ContentValues();
        cv.put(T_SCANS_FIELDS[1][0], media.getBarcode());
        cv.put(T_SCANS_FIELDS[2][0], Utilidades.getBytes(media.getBarcodePicture()));
        cv.put(T_SCANS_FIELDS[3][0], Utilidades.getStringFromDate(media.getDateCreated()));
        cv.put(T_SCANS_FIELDS[4][0], Utilidades.getStringFromDate(media.getDateModified()));
        cv.put(T_SCANS_FIELDS[5][0], Utilidades.getIntegerFromBoolean(media.getFound()));
        cv.put(T_SCANS_FIELDS[6][0], media.getPrice());
        cv.put(T_SCANS_FIELDS[7][0], media.getNotes());
        cv.put(T_SCANS_FIELDS[8][0], media.getLongitud());
        cv.put(T_SCANS_FIELDS[9][0], media.getLatitud());
        cv.put(T_SCANS_FIELDS[10][0], media.getType().toString());
        switch (media.getType()) {
            case BOOK:
                cv.put(T_SCANS_FIELDS[11][0], insertBook((Book) media )); //Devuelve id del libro
                break;
            case CD:
                break;
            default:
                break;
        }
        long id = db.insert(T_SCANS, null, cv);
        //db.insert devulve un long correspondiente al número de registros. Equivale al codigo
        //nullColumnHack se utiliza cuando queremos insertar un registro con valores null
        media.setScanId(id);

//        db.endTransaction(); //Cierra el beginTransaction
//        db.close();

        //Si codigo = -1, indica que algo ha ido mal
        //Si codigo >= 0, indica el numero de registros afectados
//        if (id > 0) {
//            lectura.setCodigo((int) id);
//        }
        return id == -1 ? null : media;
    }


    //Devuelve un media a partir del scanId
    public Media readScan(Long id) {
        //Recupera un media a partir del id del scan
//        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea
        //Mediante rawQuery
        Cursor cursor = db.rawQuery("SELECT * FROM " + T_SCANS + " WHERE " + T_SCANS_FIELDS[0][0] + "=" + id, null);
        Media media = new Media();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            switch (Type.valueOf(cursor.getString(10))){
                case BOOK:
                    media = new Book();
                    break;
                case CD:
                    break;
                case VIDEOGAME:
                    break;
            }
            media.setScanId(cursor.getInt(0));
            media.setBarcode(cursor.getString(1));
            media.setBarcodePicture(Utilidades.getBitmap(cursor.getBlob(2)));
            media.setDateCreated(Utilidades.getDateFromString(cursor.getString(3)));
            media.setDateModified(Utilidades.getDateFromString(cursor.getString(4)));
            media.setFound(Utilidades.getBooleanFromInteger(cursor.getInt(5)));
            media.setPrice(cursor.getDouble(6));
            media.setNotes(cursor.getString(7));
            media.setLongitud(cursor.getDouble(8));
            media.setLatitud(cursor.getDouble(9));
            media.setType(Type.valueOf(cursor.getString(10)));
            media.setMediaId(cursor.getLong(11));
        }
        switch (media.getType()){
            case BOOK:
                readBook(media.getMediaId(), (Book) media);
                break;
            case CD:
                break;
            case VIDEOGAME:
                break;
        }
        return media;
    }

    public Media createMedia(Media media) {
        return insertScan(media);
    }


    public Media updateMedia(Media media) {
        //Modifica el libro con un id concreto
        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea
//        db.update(T_BOOKS, book2contentvalues(media), T_BOOKS_FIELDS[0][0] + "=" + media.getMediaId(), null);
//        db.close();
        //TODO return?
        return media;
    }

    public Boolean deleteMedia(Long id) {

        //TODO id tiene que ser la de la bbdd, no la posición de la lista
        SQLiteDatabase db = this.getWritableDatabase();
        //Solo borro los datos de scan. El libro i la cubierta se queda en la bbdd
        long res = db.delete(T_SCANS, T_SCANS_FIELDS[0][0] + "=" + id, null);
//        //Mediante rawQuery
//        Cursor cursor = db.rawQuery("DELETE FROM " + TABLE_NAME + " WHERE " + COL_0_TAG + " ='" + id + "'", null);
        return Boolean.TRUE;
    }

    public List<Media> getAll() {
//        SQLiteDatabase db = this.getWritableDatabase();

        //Mediante rawQuery
        Cursor cursor = db.rawQuery("SELECT " + T_SCANS_FIELDS[0][0] + " FROM " + T_SCANS + " ORDER BY " + T_SCANS_FIELDS[4][0] + " ASC", null);

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

        //Convierto el cursor de la tabla lecturas a una List de Media
        List<Media> medias = new ArrayList<>();

        //Verifico que el cursor no esté vacio
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Media media = readScan(cursor.getLong(0));
            medias.add(media);
            while (cursor.moveToNext()) {
                media = readScan(cursor.getLong(0));
                medias.add(media);
            }
        }
        cursor.close();
        return medias;
    }

    private String sqlCreateTable(String table_name, String[][] table_fields){
        StringBuilder strSQL = new StringBuilder();
        strSQL.append("CREATE TABLE IF NOT EXISTS ").append(table_name).append(" (");
        for (int i=0;i<table_fields.length;i++){
            strSQL.append(table_fields[i][0]).append(" ").append(table_fields[i][1]).append(" ").append(table_fields[i][2]);
            strSQL.append(( i < table_fields.length-1 ) ? ", " : "");
        }
        strSQL.append(");");
        return strSQL.toString();
    }

    private String sqlCreateTable(String table_name, String[][] table_fields, String[][] table_keys){
        StringBuilder strSQL = new StringBuilder();
        strSQL.append("CREATE TABLE IF NOT EXISTS ").append(table_name).append(" (");
        for (int i=0;i<table_fields.length;i++){
            strSQL.append(table_fields[i][0]).append(" ").append(table_fields[i][1]).append(" ").append(table_fields[i][2]);
            strSQL.append(", ");
        }
        for (int i=0;i<table_keys.length;i++){
            strSQL.append("FOREIGN KEY(").append(table_keys[i][0]).append(") REFERENCES ").append(table_keys[i][1]).append("(").append(table_keys[i][2]).append(")");
            strSQL.append(( i < table_keys.length-1 ) ? ", " : "");
        }
        strSQL.append(");");
        return strSQL.toString();
    }

    private String sqlDropTable(String table_name){
        return "DROP TABLE IF EXISTS " + table_name; //Elimina la tabla
    }

}
