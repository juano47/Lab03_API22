package padula.delaiglesia.dam.isi.frsf.lab03.dao;

/**
 * Created by npadula on 12/10/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import padula.delaiglesia.dam.isi.frsf.lab03.Categoria;
import padula.delaiglesia.dam.isi.frsf.lab03.Trabajo;

/**
 * Created by lbattistella on 10/10/2017.
 */

public class WorkFromHomeOpenHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_CATEGORIA = "" +
            "CREATE TABLE Categoria " +
            "(_ID integer NOT NULL PRIMARY KEY," +
            "descripcion text NOT NULL" +
            ");";

    private static final String SQL_CREATE_TRABAJO= "" +
            "CREATE TABLE Trabajo" +
            "(_ID integer NOT NULL PRIMARY KEY," +
            "descripcion text NOT NULL," +
            "horas integer NOT NULL," +
            "fechaEntrega DATE NOT NULL," +
            "precioHora real NOT NULL," +
            "monedaPago integer NOT NULL," +
            "requiereIngles integer NOT NULL," +
            "categoriaID integer NOT NULL," +
            "FOREIGN KEY (categoriaID) REFERENCES Categoria(_ID)" +
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

        seed(sqLiteDatabase);
    }

    private void seed(SQLiteDatabase db) {

        for (Categoria c: Categoria.CATEGORIAS_MOCK) {
            insertCategoria(db,c);
            Cursor z = db.rawQuery("SELECT * FROM Categoria",null);
            int x = z.getCount();
            int y = x;
        }


        for(Trabajo t: Trabajo.TRABAJOS_MOCK){
            insertTrabajo(db,t);
            Cursor c = db.rawQuery("SELECT * FROM Trabajo",null);
            int x = c.getCount();
            int y = x;
        }
    }

    public long insertCategoria(SQLiteDatabase db, Categoria c){
        ContentValues cv = new ContentValues();
        cv.put("descripcion",c.getDescripcion());

        return db.insert("Categoria",null,cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public long insertTrabajo(SQLiteDatabase db, Trabajo t) {
        try{
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put("descripcion",t.getDescripcion());
        cv.put("horas", t.getHorasPresupuestadas());
        cv.put("precioHora", t.getPrecioMaximoHora());
        cv.put("requiereIngles",t.getRequiereIngles());
        cv.put("monedaPago", t.getMonedaPago());
        cv.put("fechaEntrega",t.getFechaEntrega().toString());
        cv.put("categoriaID",t.getCategoria().getId());

            long result = db.insertOrThrow("Trabajo",null,cv);
            db.setTransactionSuccessful();
            return result;
        }
        catch (Exception ex){
            return -1;
        }
        finally{
            db.endTransaction();

        }
    }

    public void deleteTrabajo(SQLiteDatabase db,long id){
        try{
            db.beginTransaction();
            db.delete("Trabajo","_ID = " + String.valueOf(id),null);
            db.setTransactionSuccessful();
        }
        catch (Exception ex){
            //
        }
        finally {
            db.endTransaction();
        }
    }

}