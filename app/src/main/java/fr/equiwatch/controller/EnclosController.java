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

    /**
     * Permet de récupérer l'instance de EnclosController
     *
     * @param context
     * @return
     */
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

    /**
     * Méthode asynchrone permettant de mettre à jour le marqueur passé en paramètre dans Firestore.
     *
     * @param enclos
     */
    public void updateEnclos(final EnclosClass enclos) {
        db.collection("enclos").document(enclos.getId())
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

    /**
     * Méthode asynchrone qui permet d'ajouter l'id unique généré par Firestore a l'enclos passé en paramètre.
     *
     * @param enclos
     * @param id
     */
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

    /**
     * Méthode asynchrone qui supprime l'enclos passé en parmètre de Firestore.
     *
     * @param unEnclos
     */
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

    /**
     * Méthode asynchrone qui permet de récupérer tout les enclos depuis firestore.
     * Le listener met à jour la liste a chaque modification en base de données.
     */
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

    /**
     * Méthode asynchrone permettant de créer les polygons représentant les enclos sur la google map.
     *
     * @param nmap
     */
    public void getAllEnclosWithPointsFirestore(final GoogleMap nmap) {
        db.collection("enclos")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    ArrayList<EnclosClass> listEnclos = (ArrayList<EnclosClass>) task.getResult().toObjects(EnclosClass.class);
                    ArrayList<LatLng> pointsEnclos = new ArrayList<>();
                    for (int i = 0; listEnclos.size() > i; i++) {
                        pointsEnclos.clear();
                        EnclosClass enclos = listEnclos.get(i);
                        ArrayList<PointsGpsClass> pointsGps = enclos.getPointsGps();
                        for (PointsGpsClass point: pointsGps) {
                            pointsEnclos.add(new LatLng(point.getLatitude(), point.getLongitude()));
                        }

                        PolygonOptions rectOptions = new PolygonOptions()
                                .addAll(pointsEnclos);
                        nmap.addPolygon(rectOptions);
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
                }
            });
    }

    /**
     * Getter de enclosUpdate
     *
     * @return
     */
    public EnclosClass getEnclosUpdate() {
        return enclosUpdate;
    }

    /**
     * Setter de enclosUpdate
     *
     * @param enclosUpdate
     */
    public void setEnclosUpdate(EnclosClass enclosUpdate) {
        this.enclosUpdate = enclosUpdate;
    }

    /**
     * Permet de récupérer le context ayant utilisant l'instance de EnclosController
     *
     * @return Context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Permet de récupérer lesEnclos
     *
     * @return ArrayList<EnclosClass>
     */
    public ArrayList<EnclosClass> getLesEnclos() {
        return lesEnclos;
    }
}


