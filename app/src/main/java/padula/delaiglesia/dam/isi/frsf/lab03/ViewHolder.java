package padula.delaiglesia.dam.isi.frsf.lab03;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by st on 19/09/2017.
 */
public class ViewHolder {

    public  TextView nombreEmpresa;
    public  TextView horas;
    public  TextView nombreTrabajo;
    public  TextView precioHora;
    public  TextView fechaFin;
    public  CheckBox requiereIngles;
    public ImageView bandera;

    ViewHolder(View row, Trabajo t){
        nombreTrabajo = (TextView) row.findViewById(R.id.nombrePuesto);
        nombreTrabajo.setText(t.getCategoria().getDescripcion());

        nombreEmpresa = (TextView) row.findViewById(R.id.nombreEmpresa);
        nombreEmpresa.setText(t.getDescripcion());

        horas = (TextView) row.findViewById(R.id.horas);
        horas.setText("Horas " + t.getHorasPresupuestadas().toString() + " Max");

        DecimalFormat df = new DecimalFormat("#.00");

        precioHora = (TextView) row.findViewById(R.id.precioHora);
        precioHora.setText("$/Hora: " + df.format(t.getPrecioMaximoHora()));

        fechaFin = (TextView) row.findViewById(R.id.fechaFin);
        fechaFin.setText("Fecha fin: " + t.getFechaEntrega().toString());

        requiereIngles = (CheckBox) row.findViewById(R.id.ingles);
        requiereIngles.setChecked(t.getRequiereIngles());


        bandera = (ImageView) row.findViewById(R.id.imageViewBandera);
        switch(t.getMonedaPago()){
            case 1:
                bandera.setImageResource(R.drawable.us);
                break;
            case 2:
                bandera.setImageResource(R.drawable.eu);
                break;
            case 3:
                bandera.setImageResource(R.drawable.ar);
                break;
            case 4:
                bandera.setImageResource(R.drawable.uk);
                break;
            case 5:
                bandera.setImageResource(R.drawable.br);
                break;
        }
    }
}
