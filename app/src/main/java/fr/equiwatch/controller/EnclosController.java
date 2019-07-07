package fr.equiwatch.controller;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.ArraySortedMap;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.equiwatch.R;
import fr.equiwatch.model.EnclosClass;
import fr.equiwatch.model.PointsGpsClass;
import fr.equiwatch.view.EnclosActivity;
import fr.equiwatch.view.EnclosListAdapter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public final class EnclosController {

    private static EnclosController instance = null;
    private static EnclosClass enclos;
    private static Context context;
    private int lastInsertId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference firebaseRefEnclos = database.getReference("/equiwatch/equiwatch/enclos");
    private EnclosClass enclosUpdate;

    /**
     * constructeur private
     */
    private EnclosController(){
        super();
//        firebaseRefEnclos.child("1").child("label").setValue("Enclos1");
//        firebaseRefEnclos.child("2").child("label").setValue("Enclos2");
//        firebaseRefEnclos.child("3").child("label").setValue("Enclos3");
//        firebaseRefEnclos.child("4").child("label").setValue("Enclos4");
//        firebaseRefEnclos.child("5").child("label").setValue("Enclos5");
//        firebaseRefEnclos.child("6").child("label").setValue("Enclos6");
//        firebaseRefEnclos.child("7").child("label").setValue("Enclos7");
//        lesEnclos = new ArrayList<EnclosClass>();
//        setLastInsertId();
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

    public void creerEnclosFirestore(final EnclosClass enclos) {
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

    public void getAllEnclosForListAdapter(final Context context,final ListView lvListeEnclos, final TextView textVide) {
        db.collection("enclos")
            .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<EnclosClass> lesEnclos = new ArrayList<>();
                            lesEnclos.addAll(task.getResult().toObjects(EnclosClass.class));
                            if (lesEnclos.size() != 0) {

                                EnclosListAdapter adapter = new EnclosListAdapter(context, lesEnclos);
                                lvListeEnclos.setAdapter(adapter);
                            } else {
                                textVide.setText("Vous n'avez aucun enclos pour le moment, cliquez sur le + pour en ajouter.");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
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
                    List<EnclosClass> enclos = task.getResult().toObjects(EnclosClass.class);
                    ArrayList<LatLng> pointsEnclos = new ArrayList<LatLng>();
                    for (int i = 0; enclos.size() > i; i++) {
                        EnclosClass enclo = enclos.get(i);
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

    public int getLastInsertId() {
        return lastInsertId;
    }
}


