package padula.delaiglesia.dam.isi.frsf.lab03.dao;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import padula.delaiglesia.dam.isi.frsf.lab03.Categoria;
import padula.delaiglesia.dam.isi.frsf.lab03.MainActivity;
import padula.delaiglesia.dam.isi.frsf.lab03.R;
import padula.delaiglesia.dam.isi.frsf.lab03.Trabajo;

import static padula.delaiglesia.dam.isi.frsf.lab03.MainActivity.*;

/**
 * Created by John on 02/11/2017.
 */

public class TrabajoDaoJSON implements ITrabajoDAO {

    Context context;
    private ArrayList<Trabajo> trabajosMockList;

    private static final String FILENAME_CATEGORIAS = "archivo_categorias";
    private static final String FILENAME_TRABAJOS = "archivo_trabajos";
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public TrabajoDaoJSON(Context contexto) {
        String sbString=null;
        context = contexto;
        trabajosMockList = new ArrayList<Trabajo>(Arrays.asList(Trabajo.TRABAJOS_MOCK));

        //si el fichero nunca fue creado lo creo con las categorias por defecto
        try {
            FileInputStream mInput = contexto.openFileInput(FILENAME_CATEGORIAS);
            byte[] data = new byte[128];
            StringBuilder sb = new StringBuilder();
            while (mInput.read(data) != -1) {
                sb.append(new String(data));
            }
            sbString = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (sbString==null) {
            try {
                FileOutputStream outputStream = contexto.openFileOutput(FILENAME_CATEGORIAS, Context.MODE_PRIVATE);

                JSONArray jsonArrayCategorias = new JSONArray();

                JSONObject jo1 = new JSONObject();
                jo1.put("id",1);
                jo1.put("descripcion","arquitecto");
                JSONObject jo2 = new JSONObject();
                jo2.put("id",2);
                jo2.put("descripcion","desarrollador");
                JSONObject jo3 = new JSONObject();
                jo3.put("id",3);
                jo3.put("descripcion","tester");
                JSONObject jo4 = new JSONObject();
                jo4.put("id",4);
                jo4.put("descripcion","analista");
                JSONObject jo5 = new JSONObject();
                jo5.put("id",5);
                jo5.put("descripcion","mobile developer");

                jsonArrayCategorias.put(jo1);
                jsonArrayCategorias.put(jo2);
                jsonArrayCategorias.put(jo3);
                jsonArrayCategorias.put(jo4);
                jsonArrayCategorias.put(jo5);

                outputStream.write(jsonArrayCategorias.toString().getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Categoria> listaCategoria() {

        ArrayList<Categoria> categorias = new ArrayList<>();

        try {
            FileInputStream mInput = context.openFileInput(FILENAME_CATEGORIAS);
            byte[] data = new byte[128];
            StringBuilder sb = new StringBuilder();
            while (mInput.read(data) != -1) {
                sb.append(new String(data));
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String descripcion = jsonObject.getString("descripcion");

                Categoria categoria= new Categoria(id,descripcion);
                categorias.add(categoria);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    @Override
    public void crearOferta(Trabajo p) {

        List<Trabajo> trabajos = this.listaTrabajos();
        trabajos.add(p);

        JSONArray jsonArray_trabajos = new JSONArray();
        for (int i = 0; i < trabajos.size(); i++) {
            JSONObject trabajo = new JSONObject();
            try {
                trabajo.put("id", trabajos.get(i).getId());
                trabajo.put("descripcion", trabajos.get(i).getDescripcion());
                trabajo.put("horasPresupuestadas", trabajos.get(i).getHorasPresupuestadas());
                trabajo.put("precioMaximoHora", trabajos.get(i).getPrecioMaximoHora());
                SimpleDateFormat sdf = new SimpleDateFormat(TrabajoDaoJSON.DATE_FORMAT);
                trabajo.put("fechaEntrega", sdf.format(trabajos.get(i).getFechaEntrega()));
                trabajo.put("monedaPago", trabajos.get(i).getMonedaPago());
                trabajo.put("requiereIngles", trabajos.get(i).getRequiereIngles());

                Categoria categoria = trabajos.get(i).getCategoria();
                JSONObject categoriaJsonObject = new JSONObject();
                categoriaJsonObject.put("id", categoria.getId());
                categoriaJsonObject.put("descripcion", categoria.getDescripcion());

                trabajo.put("categoria", categoriaJsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray_trabajos.put(trabajo);
        }

        try {
            FileOutputStream outputStream = context.openFileOutput(FILENAME_TRABAJOS, Context.MODE_PRIVATE);
            outputStream.write(jsonArray_trabajos.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Trabajo> listaTrabajos() {
        ArrayList<Trabajo> trabajos = new ArrayList<>();
        String sbString=null;
        //si el fichero nunca fue creado lo creo con la lista de trabajos por defecto
        try {
            FileInputStream mInput = context.openFileInput(FILENAME_TRABAJOS);
            byte[] data = new byte[128];
            StringBuilder sb = new StringBuilder();
            while (mInput.read(data) != -1) {
                sb.append(new String(data));
            }
            sbString = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (sbString==null) {
            //creo la lista de trabajos por defecto
            trabajosMockList = new ArrayList<Trabajo>(Arrays.asList(Trabajo.TRABAJOS_MOCK));

            //creo y cargo un JSONObject por cada  y lo guardo en un JSONArray

            JSONArray jsonArray_trabajos = new JSONArray();
            for (int i = 0; i < trabajosMockList.size(); i++) {
                JSONObject trabajo = new JSONObject();
                try {
                    trabajo.put("id", trabajosMockList.get(i).getId());
                    trabajo.put("descripcion", trabajosMockList.get(i).getDescripcion());
                    trabajo.put("horasPresupuestadas", trabajosMockList.get(i).getHorasPresupuestadas());
                    trabajo.put("precioMaximoHora", trabajosMockList.get(i).getPrecioMaximoHora());
                    SimpleDateFormat sdf = new SimpleDateFormat(TrabajoDaoJSON.DATE_FORMAT);
                    trabajo.put("fechaEntrega", sdf.format(trabajosMockList.get(i).getFechaEntrega()));
                    trabajo.put("monedaPago", trabajosMockList.get(i).getMonedaPago());
                    trabajo.put("requiereIngles", trabajosMockList.get(i).getRequiereIngles());

                    Categoria categoria = trabajosMockList.get(i).getCategoria();
                    JSONObject categoriaJsonObject = new JSONObject();
                    categoriaJsonObject.put("id", categoria.getId());
                    categoriaJsonObject.put("descripcion", categoria.getDescripcion());

                    trabajo.put("categoria", categoriaJsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray_trabajos.put(trabajo);
            }

            try {
                FileOutputStream outputStream = context.openFileOutput(FILENAME_TRABAJOS, Context.MODE_PRIVATE);
                outputStream.write(jsonArray_trabajos.toString().getBytes());
                outputStream.flush();
                outputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //cuando el fichero ya existe obtengo el JSON con los trabajos
        // Obtengo el JSON como string
        try {
            FileInputStream mInput = context.openFileInput(FILENAME_TRABAJOS);
            byte[] data = new byte[128];
            StringBuilder sb = new StringBuilder();
            while (mInput.read(data) != -1) {
                sb.append(new String(data));
            }

            JSONArray jsonArray = new JSONArray(sb.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id= jsonObject.getInt("id");
                String descripcion = jsonObject.getString("descripcion");
                int horasPresupuestadas = jsonObject.getInt("horasPresupuestadas");
                Double precioMaximoHora = jsonObject.getDouble("precioMaximoHora");
                SimpleDateFormat sdf = new SimpleDateFormat(TrabajoDaoJSON.DATE_FORMAT);
                String fechaEntregaString = jsonObject.getString("fechaEntrega");
                Date fechaEntrega = sdf.parse(fechaEntregaString);
                int monedaPago = jsonObject.getInt("monedaPago");
                Boolean requiereIngles = jsonObject.getBoolean("requiereIngles");

                //tengo que obtener la categoria como un JSONObject, crear un objeto categoria y setearle los atributos

                JSONObject jsonCategoria = jsonObject.getJSONObject("categoria");
                int idCategoria = jsonCategoria.getInt("id");
                String descripcionCategoria = jsonCategoria.getString("descripcion");

                Categoria categoria = new Categoria(idCategoria,descripcionCategoria);

                Trabajo trabajo = new Trabajo();
                trabajo.setId(id);
                trabajo.setDescripcion(descripcion);
                trabajo.setHorasPresupuestadas(horasPresupuestadas);
                trabajo.setCategoria(categoria);
                trabajo.setPrecioMaximoHora(precioMaximoHora);
                trabajo.setFechaEntrega(fechaEntrega);
                trabajo.setMonedaPago(monedaPago);
                trabajo.setRequiereIngles(requiereIngles);

                trabajos.add(trabajo);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return trabajos;
    }

    public void deleteTrabajo(long id){
        List<Trabajo> trabajos = this.listaTrabajos();

        for(int i=0; i<trabajos.size(); i++){
            if(trabajos.get(i).getId()==id){
                trabajos.remove(i);
                break;
            }
        }

        JSONArray jsonArray_trabajos = new JSONArray();
        for (int i = 0; i < trabajos.size(); i++) {
            JSONObject trabajo = new JSONObject();
            try {
                trabajo.put("id", trabajos.get(i).getId());
                trabajo.put("descripcion", trabajos.get(i).getDescripcion());
                trabajo.put("horasPresupuestadas", trabajos.get(i).getHorasPresupuestadas());
                trabajo.put("precioMaximoHora", trabajos.get(i).getPrecioMaximoHora());
                SimpleDateFormat sdf = new SimpleDateFormat(TrabajoDaoJSON.DATE_FORMAT);
                trabajo.put("fechaEntrega", sdf.format(trabajos.get(i).getFechaEntrega()));
                trabajo.put("monedaPago", trabajos.get(i).getMonedaPago());
                trabajo.put("requiereIngles", trabajos.get(i).getRequiereIngles());

                Categoria categoria = trabajos.get(i).getCategoria();
                JSONObject categoriaJsonObject = new JSONObject();
                categoriaJsonObject.put("id", categoria.getId());
                categoriaJsonObject.put("descripcion", categoria.getDescripcion());

                trabajo.put("categoria", categoriaJsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray_trabajos.put(trabajo);
        }

        try {
            FileOutputStream outputStream = context.openFileOutput(FILENAME_TRABAJOS, Context.MODE_PRIVATE);
            outputStream.write(jsonArray_trabajos.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
