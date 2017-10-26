package padula.delaiglesia.dam.isi.frsf.lab03.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import padula.delaiglesia.dam.isi.frsf.lab03.Categoria;
import padula.delaiglesia.dam.isi.frsf.lab03.Trabajo;

/**
 * Created by npadula on 12/10/2017.
 */

public class TrabajoDAOSQLite implements ITrabajoDAO {
    private  WorkFromHomeOpenHelper helper;
    private SQLiteDatabase db;

    public TrabajoDAOSQLite(Context c){
        helper = WorkFromHomeOpenHelper.getInstance(c);

        initDB();
    }

    private void initDB() {
        startSession(true);

    }


    @Override
    public void crearOferta(Trabajo p) {
        helper.insertTrabajo(db,p);
    }

    @Override
    public List<Trabajo> listaTrabajos() {

        Cursor c = db.rawQuery("SELECT * FROM Trabajo",null);
        ArrayList<Trabajo> trabajos = new ArrayList<>();


        while(c.moveToNext()){
            Trabajo t = new Trabajo();

            t.setDescripcion( c.getString(c.getColumnIndex("descripcion")));

            t.setId((int)c.getLong(c.getColumnIndex("_ID")));

            t.setHorasPresupuestadas(c.getInt(c.getColumnIndex("horas")));

            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

            try {

                t.setFechaEntrega(df.parse(c.getString(c.getColumnIndex("fechaEntrega"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            t.setPrecioMaximoHora((double)c.getFloat(c.getColumnIndex("precioHora")));

            t.setMonedaPago(c.getInt(c.getColumnIndex("monedaPago")));

            t.setRequiereIngles(c.getInt(c.getColumnIndex("requiereIngles")) == 1);

            long categoriaID = c.getLong(c.getColumnIndex("categoriaID"));
            Categoria cat = findByID(categoriaID);

            t.setCategoria(cat);

            trabajos.add(t);

        }

        c.close();

        return trabajos;
    }


    public void startSession(){
        startSession(false);


    }

    public void startSession(boolean write){
        if(write)
            db = helper.getWritableDatabase();
        else
            db = helper.getReadableDatabase();

    }

    public long insertCategoria(Categoria c){
        return helper.insertCategoria(db,c);
    }

    @Override
    public List<Categoria> listaCategoria(){
        Cursor c = db.rawQuery("SELECT * FROM Categoria",null);
        ArrayList<Categoria> categorias = new ArrayList<>();


        while(c.moveToNext()){
            String desc = c.getString(c.getColumnIndex("descripcion"));
            long id = c.getLong(c.getColumnIndex("_ID"));

            categorias.add(new Categoria((int)id,desc));

        }

        c.close();

        return categorias;

    }

    public Categoria findByID(long _id){
        Cursor c = db.rawQuery("SELECT * FROM Categoria WHERE _ID = ?",new String[]{Long.toString(_id)});

        if(c.moveToFirst()){
            long id = c.getLong(c.getColumnIndex("_ID"));
            String descripcion = c.getString(c.getColumnIndex("descripcion"));


            Categoria cat = new Categoria((int)id,descripcion);

            return cat;
        }
        else
            return  null;



    }
}
