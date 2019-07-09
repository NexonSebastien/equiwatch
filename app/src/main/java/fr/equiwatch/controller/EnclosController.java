package fr.equiwatch.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fr.equiwatch.model.EnclosClass;
import fr.equiwatch.model.PointsGpsClass;

import static androidx.constraintlayout.widget.Constraints.TAG;

public final class EnclosController {

    private static EnclosController instance = null;
    private static Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EnclosClass enclosUpdate;
    private ArrayList<EnclosClass> lesEnclos = new ArrayList<>();

    /**
     * constructeur private
     */
    private EnclosController(){
        super();
        getAllEnclos();
    }

    public static final EnclosController getInstance(Context context){
        if(context != null){
            EnclosController.context = context;
        }
        if(EnclosController.instance == null){
            EnclosController.instance = new EnclosController();
//            @todo recuperer enclos
        }
        return EnclosController.instance;
    }

    /**
     * Méthode asynchrone qui permet de créer l'enclos passer en paramètre dans la base de données.
     *
     * @param enclos
     */
    public void creerEnclos(final EnclosClass enclos) {
        // Add a new document with a generated ID
        db.collection("enclos")
            .add(enclos)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    String key = documentReference.getId();
                    enclos.setId(key);
                    addUniqueIdToEnclos(enclos, key);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                }
            });
    }

    public void addUniqueIdToEnclos(EnclosClass enclos, String id) {
        enclos.setId(id);
        db.collection("enclos").document(id)
            .set(enclos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void deleteEnclos(EnclosClass unEnclos){
        db.collection("enclos").document(unEnclos.getId())
            .delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error deleting document", e);
                }
            });
    }

    public void setEnclos(EnclosClass enclos){
        EnclosController.enclos = enclos;
    }

    public void getAllEnclos() {
        db.collection("enclos")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    lesEnclos.clear();
                    lesEnclos.addAll(value.toObjects(EnclosClass.class));
                }
            });
    }

    public void getAllEnclosWithPointsFirestore(final GoogleMap nmap) {
        db.collection("enclos")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    lesEnclos.clear();
                    lesEnclos.addAll(task.getResult().toObjects(EnclosClass.class));
                    ArrayList<LatLng> pointsEnclos = new ArrayList<LatLng>();
                    for (int i = 0; lesEnclos.size() > i; i++) {
                        EnclosClass enclo = lesEnclos.get(i);
                        ArrayList<PointsGpsClass> pointsGps = enclo.getPointsGps();
                        for (PointsGpsClass point: pointsGps) {
                            pointsEnclos.add(new LatLng(point.getLatitude(), point.getLongitude()));
                        }
                    }
                    PolygonOptions rectOptions = new PolygonOptions()
                            .addAll(pointsEnclos);
                    nmap.addPolygon(rectOptions);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
                }
            });
    }

    public EnclosClass getEnclosUpdate() {
        return enclosUpdate;
    }

    public void setEnclosUpdate(EnclosClass enclosUpdate) {
        this.enclosUpdate = enclosUpdate;
    }

    public static Context getContext() {
        return context;
    }

    public ArrayList<EnclosClass> getLesEnclos() {
        return lesEnclos;
    }
}


