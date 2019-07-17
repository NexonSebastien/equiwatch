package fr.equiwatch.notifications;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.CapteursController;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.controller.EquidesController;
import fr.equiwatch.model.CapteursClass;
import fr.equiwatch.model.EnclosClass;
import fr.equiwatch.model.EquidesClass;
import fr.equiwatch.model.PointsGpsClass;
import fr.equiwatch.view.MainActivity;


public class NotificationService extends IntentService {
    private static final int NOTIFICATION_ID = 2;
    private static final String CHANNEL_ID = "1";
    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_START = "ACTION_START";
    private static ArrayList<EnclosClass> listEnclos;
    private static ArrayList<CapteursClass> listCapteurs;
    private EnclosController enclosController;
    private CapteursController capteursController;
    private final double LIMIT_TEMP = 30;

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_START);
        return intent;
    }


    public NotificationService() {
        super("NotificationService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(getClass().getSimpleName(), "onStartCommand");
        enclosController = EnclosController.getInstance(this);
        capteursController = CapteursController.getInstance(this);
        return START_NOT_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(getClass().getSimpleName(), "onStartHandle");
        if (enclosController == null && capteursController == null) {
            return;
        }
        Log.i(getClass().getSimpleName(), "tentative de condition");
        enclosController.getAllEnclos();
        capteursController.getAllCapteurs();
        listEnclos = enclosController.getLesEnclos();
        listCapteurs = capteursController.getLesCapteurs();

        for (int n = 0; listCapteurs.size() > n;n++) {
            CapteursClass capteur = listCapteurs.get(n);
            if (!capteur.getType().equals("GPS") && !capteur.getType().equals("VIDE")) {
                for (int i = 0; listEnclos.size() > i; i++) {
                    EnclosClass enclos = listEnclos.get(i);
                    if (enclos.getListIdCapteur().contains(capteur.getId())) {
                        if (capteur.getType().equals("Thermique")) {
                            Log.i(getClass().getSimpleName(), "tentative notif thermique");
                            checkTemperature(capteur, enclos);
                        }
                    }
                }
            }
        }
    }

    /**
     * Affiche une notification
     */
    private void showNotification(String type, Object sujet) {
        createNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String notificationDescriptif = "";
        if (type.equals("Thermique")) {
            EnclosClass enclos = (EnclosClass) sujet;
            notificationDescriptif = String.format(getString(R.string.notif_alerte_temperature_descriptif), enclos.getLabel(), Double.toString(LIMIT_TEMP));
        } else if (type.equals("Electrique")) {

        } else if (type.equals("Hydraulique")) {

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.horse)
                .setContentTitle("Equiwatch: Alerte")
                .setContentText(notificationDescriptif)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Log.i(getClass().getSimpleName(), "show notification");
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * Créer un channel pour les notifications de l'applications
     */
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.nom_channel);
            String description = getString(R.string.description_channel);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void checkTemperature(CapteursClass capteur, EnclosClass enclos) {
        if (LIMIT_TEMP < Double.valueOf(capteur.getDonnee())) {
            showNotification("Thermique", enclos);
        }
    }
}
