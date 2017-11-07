package padula.delaiglesia.dam.isi.frsf.lab03;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import padula.delaiglesia.dam.isi.frsf.lab03.dao.TrabajoDAOSQLite;
import padula.delaiglesia.dam.isi.frsf.lab03.dao.TrabajoDaoJSON;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private MiAdaptador adaptador;
    private FloatingActionButton floatingButton;
    private Intent intentAltaOferta;
    private ArrayList<Trabajo> trabajosMockList;
    int ALTA_OFERTA=1;
    private MiTareaAsincrona tareaAsincrona;
    private TrabajoDaoJSON dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trabajosMockList = new ArrayList<Trabajo>(Arrays.asList(Trabajo.TRABAJOS_MOCK));

        dao = new TrabajoDaoJSON(MainActivity.this);
        floatingButton = (FloatingActionButton) findViewById(R.id.fab);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        adaptador = new MiAdaptador(this,dao );
        listView.setAdapter(adaptador);
        registerForContextMenu(listView);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentAltaOferta = new Intent(MainActivity.this,AltaOferta.class);
                startActivityForResult(intentAltaOferta,ALTA_OFERTA);
            }
        });

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                AdapterView.AdapterContextMenuInfo contextInfo = (AdapterView.AdapterContextMenuInfo)contextMenuInfo;
                Trabajo trabajoSeleccionado = adaptador.getItem(contextInfo.position);

                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu, contextMenu);

//                Toast.makeText(getApplicationContext(),trabajoSeleccionado.getDescripcion(),Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.descartarOferta:
                Trabajo t = adaptador.getItem(info.position);
                dao.deleteTrabajo((long)t.getId());
                adaptador.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"La oferta ha sido descartada",Toast.LENGTH_LONG).show();
                return true;
            case R.id.postularseOferta:

                tareaAsincrona= new MiTareaAsincrona();
                tareaAsincrona.execute(this);

                return true;
            default:
                return super.onContextItemSelected(item);
    }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        if(resultCode == RESULT_OK){
            Trabajo nuevoTrabajo = (Trabajo) data.getParcelableExtra("OFERTA");
            dao.crearOferta(nuevoTrabajo);
            adaptador.notifyDataSetChanged();
        }
        else{
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
        }
    }
}
