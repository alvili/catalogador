package com.abcsoft.catalogador.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.abcsoft.catalogador.model.Local.Cover;
import com.abcsoft.catalogador.model.Local.Scan;
import com.abcsoft.catalogador.services.Utilidades;

import java.util.ArrayList;
import java.util.List;

//implementa las funcionalidades declaradas en el servicio BooksService a nivel sql
public class DatabaseHelper extends SQLiteOpenHelper {

    //Nombre de la bbdd
    public static final String DATABASE_NAME = "catalogador.db";
    //path -> data/data/com.abcsoft.catalogador/databases/catalogador.db

    public static final String T_SCANS = "SCANS";
    public static final String[][] T_SCANS_FIELDS = {
            {"ID","INTEGER","PRIMARY KEY AUTOINCREMENT"},
            {"BARCODE","TEXT",""},
            {"CREATED","TEXT",""},
            {"MODIFIED","TEXT",""}
            {"FOUND","INTEGER",""},
            {"PRICE","TEXT",""},
            {"NOTES","TEXT",""}
            {"LONGITUD","TEXT",""},
            {"LATITUD","TEXT",""},
            {"ITEMTYPE","TEXT",""},
            {"ITEMID","INTEGER",""},
    };

    public static final String T_BOOKS = "BOOKS";
    public static final String[][] T_BOOKS_FIELDS = {
            {"ID","INTEGER","PRIMARY KEY AUTOINCREMENT"},
            {"ISBN","TEXT",""},
            {"TITLE","TEXT",""},
            {"AUTHOR","TEXT",""},
            {"PUBLISHER","TEXT",""},
            {"PLACE","TEXT",""},
            {"YEAR","TEXT",""},
            {"PAGES","INTEGER",""},
            {"COVERID","INTEGER",""},
    };

    //Tabla carátulas
    public static final String T_COVERS = "COVERS";
    public static final String[][] T_COVERS_FIELDS = {
            {"ID","INTEGER","PRIMARY KEY AUTOINCREMENT"},
            {"LINK","TEXT",""},
            {"IMAGE","BLOB",""}
    };

    //Tabla relacional
    public static final String T_BOOKS_COVERS = "BOOKS_COVERS";
    public static final String[][] T_BOOKS_COVERS_FIELDS = {
            {"bookID","INTEGER","NOT NULL"},
            {"coverID","INTEGER","NOT NULL"}
    };
    public static final String[][] T_BOOKS_COVERS_KEYS = {
            {"bookID",T_BOOKS,T_BOOKS_FIELDS[0][0]},
            {"coverID",T_COVERS,T_COVERS_FIELDS[0][0]}
    };


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crea las tablas vacias
        StringBuilder strSQL = new StringBuilder();

        strSQL.append(sqlCreateTable(T_SCANS, T_SCANS_FIELDS));
        strSQL.append(" ");
        strSQL.append(sqlCreateTable(T_BOOKS, T_BOOKS_FIELDS));
        strSQL.append(" ");
        strSQL.append(sqlCreateTable(T_COVERS, T_COVERS_FIELDS));
        strSQL.append(" ");
        strSQL.append(sqlCreateTable(T_BOOKS_COVERS, T_BOOKS_COVERS_FIELDS, T_BOOKS_COVERS_KEYS));

        db.execSQL(strSQL.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Elimina las tablas actuales. Adios a los datos
        StringBuilder strSQL = new StringBuilder();

        strSQL.append(sqlDropTable(T_SCANS));
        strSQL.append(" ");
        strSQL.append(sqlDropTable(T_BOOKS));
        strSQL.append(" ");
        strSQL.append(sqlDropTable(T_COVERS));
        strSQL.append(" ");
        strSQL.append(sqlDropTable(T_BOOKS_COVERS));

        db.execSQL(strSQL.toString());
        onCreate(db); //Reconstruye las tablas desde cero
    }

    private long getCoverID(String coverLink){
        long coverID = -1;
        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura.
        String sqlQuery = "SELECT " + T_COVERS_FIELDS[0][0] + " FROM " + T_COVERS + " WHERE " + T_COVERS_FIELDS[0][1] + " = \"" + coverLink +"\"";
        Cursor c = db.rawQuery(sqlQuery, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            coverID = c.getLong(0);
        }
        c.close();
        db.close();
        return coverID;
    }

    private long insertCover(Cover cover){
        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura.

        //Inserto la caratula a la bbdd
        byte[] image = Utilidades.getBytes(cover.getImage());
        ContentValues cv = new  ContentValues();
        cv.put(T_COVERS_FIELDS[1][0], cover.getLink());
        cv.put(T_COVERS_FIELDS[2][0], image);

        long coverId = db.insert(T_COVERS,null, cv);
        db.close();
        cover.setId(coverId);
        return coverId;
    }

    public Scan createScan(Scan scan){
        //Necesito una referencia de acceso a la bbdd
        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea

        //CON db.beginTransaction() no guarda datos a la bbdd
//        db.beginTransaction();//Inicia transaccion.Sirve para garantizar la consistencia de la bbdd en caso de problemas

        //Obtener id de la imagen
        long coverId = getCoverID(scan.getBook().getCover().getLink());

        long id = db.insert(T_BOOKS,null, book2contentvalues(scan));
        //db.insert devulve un long correspondiente al número de registros. Equivale al codigo
        //nullColumnHack se utiliza cuando queremos insertar un registro con valores null

        byte[] image = Utilidades.getBytes(scan.getBook().getCover().getImage());

        ContentValues cv = new  ContentValues();
        cv.put(T_COVERS_FIELDS[0][0], scan.getBook().getCover().getLink());
        cv.put(T_COVERS_FIELDS[1][0], image);

        long id2 = db.insert(T_COVERS,null, cv);

        //TODO LIMPIAR ESTO!!

//        db.endTransaction(); //Cierra el beginTransaction
        db.close();

        //Si codigo = -1, indica que algo ha ido mal
        //Si codigo >= 0, indica el numero de registros afectados
//        if (id > 0) {
//            lectura.setCodigo((int) id);
//        }
        return id == -1 ? null : scan;
    }

    public Scan readScan(Long id){
        //Modifica el libro con un id concreto
        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea
        //Mediante rawQuery
        Cursor cursor = db.rawQuery("SELECT * FROM " + T_BOOKS + " WHERE " + T_BOOKS_FIELDS[0][0] + "=" + id, null);
//        Cursor cursor = db.rawQuery("SELECT * FROM " + T_BOOKS + " WHERE " + T_BOOKS_PARAMS[0][0]COL_0_TAG + "=" + id, null);
        Scan scan = cursorToScanDetails(cursor);
        db.close();
        return scan;
    }

    public Scan updateScan(Scan scan){
        //Modifica el libro con un id concreto
        SQLiteDatabase db = this.getWritableDatabase(); //Devuelve una referencia a la bbdd en modo escritura. Si la bbdd no existe, la crea
        db.update(T_BOOKS, book2contentvalues(scan), T_BOOKS_FIELDS[0][0] + "=" + scan.getId(), null);
        db.close();
        //TODO return?
        return scan;
    }

    public Boolean deleteScan(Long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(T_BOOKS, T_BOOKS_FIELDS[0][0] + "=" + id, null);
//        //Mediante rawQuery
//        Cursor cursor = db.rawQuery("DELETE FROM " + TABLE_NAME + " WHERE " + COL_0_TAG + " ='" + id + "'", null);
        //TODO return?
        return Boolean.TRUE;
    }

    public List<Scan> getAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        //Mediante rawQuery
        Cursor cursor = db.rawQuery("SELECT * FROM " + T_BOOKS + " ORDER BY " + T_BOOKS_FIELDS[1][0] + " ASC", null);
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
        return cursorToScanDetailsList(cursor);
    }

//    public Long insertImage( String imageLink) throws SQLiteException {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//
//        Bitmap b = Utilidades.getBitmapFromURL(imageLink);
//        byte[] image = Utilidades.getBytes(b);
//
//        ContentValues cv = new  ContentValues();
//        cv.put(T_COVERS_FIELDS[1][0], imageLink);
//        cv.put(T_COVERS_FIELDS[2][0], image);
//
//        long id = db.insert(T_COVERS,null, cv);
//        db.close();
//
//        return id == -1 ? null : id;
//    }





//***************************************************************************************
//************************                                     **************************
//************************          Métodos privados           **************************
//************************                                     **************************
//***************************************************************************************


    //Conveirte los tipos de java a sqlite y transfiere los campos de book a un contenedor de valores
    private ContentValues book2contentvalues(Scan scan){
        //Creo un contenedor de valores y transformo los campos de book a los tipos de sqlite
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_BOOKS_FIELDS[1][0], Utilidades.getStringFromDate(scan.getDateCreated()));
        contentValues.put(T_BOOKS_FIELDS[2][0], scan.getBook().getTitle());
        contentValues.put(T_BOOKS_FIELDS[3][0], scan.getBook().getAuthor());
        contentValues.put(T_BOOKS_FIELDS[4][0], scan.getBook().getIsbn());
        contentValues.put(T_BOOKS_FIELDS[5][0], scan.getBook().getPublisher());
        contentValues.put(T_BOOKS_FIELDS[6][0], scan.getBook().getPublishDate());
        contentValues.put(T_BOOKS_FIELDS[7][0], scan.getPrice());
        contentValues.put(T_BOOKS_FIELDS[8][0], scan.getLongitud());
        contentValues.put(T_BOOKS_FIELDS[9][0], scan.getLatitud());
        contentValues.put(T_BOOKS_FIELDS[10][0], scan.getBook().getPublishPlace());
        contentValues.put(T_BOOKS_FIELDS[11][0], scan.getBook().getNumPages());
        contentValues.put(T_BOOKS_FIELDS[12][0], scan.getBook().getCover().getLink());
        contentValues.put(T_BOOKS_FIELDS[13][0], Utilidades.getIntegerFromBoolean(scan.getFound()));
        contentValues.put(T_BOOKS_FIELDS[14][0], scan.getNotes());
        contentValues.put(T_BOOKS_FIELDS[15][0], Utilidades.getStringFromDate(scan.getDateModified()));
//        insertImage(book.getCoverLink()); //guardar la id que devuelve
        return contentValues;
    }

    //Convierte un cursor de la tabla lecturas a una List de Books
    private Scan cursor2ScanDetails(Cursor cursor){
        Scan scan = new Scan(); //Añado un libro nuevo en cada iteración
        scan.setId(cursor.getInt(0));
        scan.setDateCreated(Utilidades.getDateFromString(cursor.getString(1)));
        scan.getBook().setTitle(cursor.getString(2));
        scan.getBook().setAuthor(cursor.getString(3));
        scan.getBook().setIsbn(cursor.getString(4));
        scan.getBook().setPublisher(cursor.getString(5));
        scan.getBook().setPublishDate(cursor.getString(6));
        scan.setPrice(cursor.getDouble(7));
        scan.setLongitud(cursor.getDouble(8));
        scan.setLatitud(cursor.getDouble(9));
        scan.getBook().setPublishPlace(cursor.getString(10));
        scan.getBook().setNumPages(cursor.getInt(11));
        scan.getBook().getCover().setLink(cursor.getString(12));
        scan.setFound(Utilidades.getBooleanFromInteger(cursor.getInt(13)));
        scan.setNotes(cursor.getString(14));
        scan.setDateModified(Utilidades.getDateFromString(cursor.getString(15)));
        return scan;
    }

    private Scan cursorToScanDetails(Cursor cursor){
        Scan scan = new Scan();
        //Verifico que el cursor no esté vacio
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            scan = cursor2ScanDetails(cursor);
        }
        cursor.close();
        return scan;
    }

    //Convierte un cursor de la tabla lecturas a una List de Books
    private List<Scan> cursorToScanDetailsList(Cursor cursor){
        List<Scan> scans = new ArrayList<>();
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
                scans.add(cursor2ScanDetails(cursor));
            }
        }
        cursor.close();
        return scans;
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
