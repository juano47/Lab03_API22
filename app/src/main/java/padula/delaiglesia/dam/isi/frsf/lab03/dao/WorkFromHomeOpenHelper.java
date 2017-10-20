package padula.delaiglesia.dam.isi.frsf.lab03.dao;

/**
 * Created by npadula on 12/10/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lbattistella on 10/10/2017.
 */

public class WorkFromHomeOpenHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_CATEGORIA = "" +
            "CREATE TABLE Categoria " +
            "(id integer autoincrement NOT NULL PRIMARY KEY," +
            "descripcion text NOT NULL" +
            ");";

    private static final String SQL_CREATE_TRABAJO= "" +
            "CREATE TABLE Trabajo" +
            "(id integer autoincrement NOT NULL PRIMARY KEY," +
            "descripcion text NOT NULL," +
            "horas integer NOT NULL," +
            "fechaEntrega text NOT NULL," +
            "precioHora real NOT NULL," +
            "monedaPago integer NOT NULL," +
            "requiereIngles integer NOT NULL," +
            "categoriaID integer NOT NULL," +
            "FOREGEIN KEY (categoriaID) REFERENCES Categoria(id)" +
            ");";
    private static final java.lang.String SQL_INSERT_CATEGORIAS = "";
    private static final String SQL_INSERT_TRABAJOS = "";
    private static WorkFromHomeOpenHelper _INSTANCE;

    private WorkFromHomeOpenHelper(Context ctx){
        super(ctx,"WORK_FROM_HOME",null,1);
    }

    public static WorkFromHomeOpenHelper getInstance(Context ctx){
        if(_INSTANCE==null) _INSTANCE = new WorkFromHomeOpenHelper(ctx);
        return _INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORIA);
        sqLiteDatabase.execSQL(SQL_CREATE_TRABAJO);
        sqLiteDatabase.execSQL(SQL_INSERT_CATEGORIAS);
        sqLiteDatabase.execSQL(SQL_INSERT_TRABAJOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}