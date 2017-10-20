package padula.delaiglesia.dam.isi.frsf.lab03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by st on 19/09/2017.
 */
public class MiAdaptador extends BaseAdapter {

    LayoutInflater inflater;
    List<Trabajo> items;
    Context context;

    public MiAdaptador(Context context, List<Trabajo> items) {
        super();
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Trabajo getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View row = convertView;
        Trabajo t = getItem(i);

        if(row == null) {
            row = inflater.inflate(R.layout.content_ofertalaboral, parent, false);
        }

        ViewHolder holder = (ViewHolder) row.getTag();
        if(holder == null){
            holder = new ViewHolder(row,t);
            row.setTag(holder);
        }

        holder.nombreTrabajo = (TextView) row.findViewById(R.id.nombrePuesto);
        holder.nombreTrabajo.setText(this.getItem(i).getCategoria().getDescripcion());

        holder.nombreEmpresa = (TextView) row.findViewById(R.id.nombreEmpresa);
        holder.nombreEmpresa.setText(getItem(i).getDescripcion());

        holder.horas = (TextView) row.findViewById(R.id.horas);
        holder.horas.setText("Horas " + getItem(i).getHorasPresupuestadas().toString() + " Max");

        DecimalFormat df = new DecimalFormat("#.00");
        holder.precioHora = (TextView) row.findViewById(R.id.precioHora);
        holder.precioHora.setText(" $/Hora: " + df.format(getItem(i).getPrecioMaximoHora()));

        holder.fechaFin = (TextView) row.findViewById(R.id.fechaFin);
        holder.fechaFin.setText("Fecha fin: " + getItem(i).getFechaEntrega().toString());

        holder.requiereIngles = (CheckBox) row.findViewById(R.id.ingles);
        holder.requiereIngles.setChecked(getItem(i).getRequiereIngles());

        holder.bandera = (ImageView) row.findViewById(R.id.imageViewBandera);
        switch(t.getMonedaPago()){
            case 1:
                holder.bandera.setImageResource(R.drawable.us);
                break;
            case 2:
                holder.bandera.setImageResource(R.drawable.eu);
                break;
            case 3:
                holder.bandera.setImageResource(R.drawable.ar);
                break;
            case 4:
                holder.bandera.setImageResource(R.drawable.uk);
                break;
            case 5:
                holder.bandera.setImageResource(R.drawable.br);
                break;
        }

        return(row);
    }
}
