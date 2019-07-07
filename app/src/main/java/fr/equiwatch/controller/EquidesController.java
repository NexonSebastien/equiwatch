package fr.equiwatch.controller;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import fr.equiwatch.model.EquidesClass;
import fr.equiwatch.view.EquidesListAdapter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public final class EquidesController {

    private static EquidesController instance = null;
    private static EquidesClass equides;
    private static Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EquidesClass equidesUpdate;
    private EquidesClass equidesView;
    private ArrayList<EquidesClass> lesEquides = new ArrayList<>();


    /**
     * constructeur private
     */
    private EquidesController(){
        super();
        getAllEquides();
    }

    public static final EquidesController getInstance(Context context){
        if(context != null){
            EquidesController.context = context;
        }
        if(EquidesController.instance == null){
            EquidesController.instance = new EquidesController();
        }
        return EquidesController.instance;
    }

    public void creerEquides(String nom, int idEnclos, int idCapteur){
        final EquidesClass equides = new EquidesClass(nom,idEnclos,idCapteur);
        // Add a new document with a generated ID
        db.collection("equides")
                .add(equides)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        String key = documentReference.getId();
                        equides.setId(key);
                        addUniqueIdToEquides(equides,key);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void addUniqueIdToEquides(EquidesClass equides, String id) {
        equides.setId(id);
        db.collection("equides").document(id)
                .set(equides)
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

    public void deleteEquides(EquidesClass equides){
        db.collection("equides").document(equides.getId())
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
        lesEquides.remove(equides);
    }

    public void updateEquides(EquidesClass equides){
        db.collection("equides").document(equides.getId())
                .set(equides)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully update!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
        lesEquides.remove(equides);
    }

    public void setEquides(EquidesClass equides){
        EquidesController.equides = equides;
    }

    public ArrayList<EquidesClass> getLesEquides() {
        return lesEquides;
    }

    public void getAllEquides() {
        db.collection("equides")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        lesEquides.clear();
                        lesEquides.addAll(value.toObjects(EquidesClass.class));
                    }
                });
    }


    public EquidesClass getEquidesUpdate() {
        return equidesUpdate;
    }

    public void setEquidesUpdate(EquidesClass equidesUpdate) {
        this.equidesUpdate = equidesUpdate;
    }

    public static Context getContext() {
        return context;
    }

    public EquidesClass getEquidesView() {
        return equidesView;
    }

    public void setEquidesView(EquidesClass equidesView) {
        this.equidesView = equidesView;
    }
}


