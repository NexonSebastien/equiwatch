package fr.equiwatch.controller;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fr.equiwatch.model.EquidesClass;
import fr.equiwatch.view.EquidesListAdapter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public final class EquidesController {

    private static EquidesController instance = null;
    private static EquidesClass equides;
    private static ArrayList<EquidesClass> lesEquides;
    private static Context context;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference firebaseRefEquides = database.getReference("/equiwatch/equiwatch/equides");
    private EquidesClass equidesUpdate;


    /**
     * constructeur private
     */
    private EquidesController(){
        super();
        lesEquides = new ArrayList<EquidesClass>();
    }

    public static final EquidesController getInstance(Context context){
        if(context != null){
            EquidesController.context = context;
        }
        if(EquidesController.instance == null){
            EquidesController.instance = new EquidesController();
//            @todo recuperer equides
        }
        return EquidesController.instance;
    }

    public void creerEquides(String nom, int idEnclos, int idCapteur){
        EquidesClass equides = new EquidesClass(nom,idEnclos,idCapteur);
        // Add a new document with a generated ID
        db.collection("equides")
                .add(equides)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void deleteEquides(EquidesClass equides){
//        firebaseRefEquides.child(Integer.toString(equides.getId())).removeValue();
//        lesEquides.remove(equides);
    }

    public void updateEquides(EquidesClass equides){
//        firebaseRefEquides.child(Integer.toString(equides.getId())).child("nom").setValue(equides.getNom());
//        firebaseRefEquides.child(Integer.toString(equides.getId())).child("idEnclos").setValue(equides.getIdEnclos());
//        firebaseRefEquides.child(Integer.toString(equides.getId())).child("idCapteur").setValue(equides.getIdCapteur());
    }

    public void setEquides(EquidesClass equides){
        EquidesController.equides = equides;
    }

    public ArrayList<EquidesClass> getLesEquides() {
        return lesEquides;
    }

    public void setLesEnclos(ArrayList<EquidesClass> lesEquides) {
        EquidesController.lesEquides = lesEquides;
    }

    public void getAllEquides(final Context context, final ListView lvListeEquides, final TextView textVide){

        db.collection("equides")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            lesEquides.clear();
                            lesEquides.addAll(task.getResult().toObjects(EquidesClass.class));
                            if (lesEquides.size() != 0) {

                                EquidesListAdapter adapter = new EquidesListAdapter(context, lesEquides);
                                lvListeEquides.setAdapter(adapter);
                            } else {
                                textVide.setText("Vous n'avez aucun équidés pour le moment, cliquez sur le + pour en ajouter.");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
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
}


