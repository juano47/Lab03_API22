package padula.delaiglesia.dam.isi.frsf.lab03;


import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import static android.R.attr.id;


/**
 * Created by John on 21/10/2017.
 */

public class MiTareaAsincrona extends AsyncTask<Context,Integer,Integer> {
    @Override
    protected Integer doInBackground(Context... context) {
        NotificationManager nm = (NotificationManager) context[0].getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context[0])
                        .setSmallIcon(android.R.drawable.checkbox_on_background)
                        .setContentTitle("Solicitud aceptada")
                        .setTicker("Alerta!")
                        .setVibrate(new long[] {100, 250, 100, 500})
                        .setContentText("Usted se ha postulado con éxito!");
        nm.notify(id, mBuilder.build());
        return 0;
    }

}
