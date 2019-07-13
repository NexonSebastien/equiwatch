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
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.EnclosClass;
import fr.equiwatch.model.PointsGpsClass;
import fr.equiwatch.view.MainActivity;

public class NotificationService extends IntentService {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "0";
    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_START = "ACTION_START";
    private static ArrayList<EnclosClass> listEnclos;
    private static EnclosController enclosController;

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
        return START_NOT_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        enclosController.getAllEnclos();
        listEnclos = enclosController.getLesEnclos();
    }

    private void showForegroundNotification() {
        createNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        String notificationDescriptif = String.format(getString(R.string.notif_alerte_cheval_descriptif), "test cheval", "test enclos");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.horse)
                .setContentTitle("Test notification")
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

    private void geoFencing(LatLng chevalPos) {
        ArrayList<LatLng> pointsEnclos = new ArrayList<>();
        ArrayList<Polygon> listPolygon = new ArrayList<>();
        for (int i = 0; listEnclos.size() > i; i++) {
            pointsEnclos.clear();
            EnclosClass enclos = listEnclos.get(i);
            ArrayList<PointsGpsClass> pointsGps = enclos.getPointsGps();
            for (PointsGpsClass point: pointsGps) {
                pointsEnclos.add(new LatLng(point.getLatitude(), point.getLongitude()));
            }

            if (!pointsEnclos.isEmpty()) {
                PolygonOptions rectOptions = new PolygonOptions()
                        .addAll(pointsEnclos);
                if(!PolyUtil.containsLocation(chevalPos, rectOptions.getPoints(), false)) {
                    showForegroundNotification();
                }
            }
        }
    }
}
