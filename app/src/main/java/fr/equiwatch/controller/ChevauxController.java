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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fr.equiwatch.model.ChevauxClass;
import fr.equiwatch.view.ChevauxListAdapter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public final class ChevauxController {

    private static ChevauxController instance = null;
    private static ChevauxClass chevaux;
    private static ArrayList<ChevauxClass> lesChevaux;
    private static Context context;
    private int lastInsertId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference firebaseRefChevaux = database.getReference("/equiwatch/equiwatch/chevaux");
    private ChevauxClass chevauxUpdate;


    /**
     * constructeur private
     */
    private ChevauxController(){
        super();
//        firebaseRefChevaux.child("1").child("nom").setValue("Tornado");
//        firebaseRefChevaux.child("1").child("idEnclos").setValue("1");
//        firebaseRefChevaux.child("1").child("idCapteur").setValue("1");
//
//        firebaseRefChevaux.child("2").child("nom").setValue("Eclair");
//        firebaseRefChevaux.child("2").child("idEnclos").setValue("2");
//        firebaseRefChevaux.child("2").child("idCapteur").setValue("2");
//
//        firebaseRefChevaux.child("3").child("nom").setValue("Tonnerre");
//        firebaseRefChevaux.child("3").child("idEnclos").setValue("3");
//        firebaseRefChevaux.child("3").child("idCapteur").setValue("3");
//
//        firebaseRefChevaux.child("4").child("nom").setValue("Pegase");
//        firebaseRefChevaux.child("4").child("idEnclos").setValue("4");
//        firebaseRefChevaux.child("4").child("idCapteur").setValue("4");

        lesChevaux = new ArrayList<ChevauxClass>();
//        getAllChevaux();
        setLastInsertId();
    }

    public static final ChevauxController getInstance(Context context){
        if(context != null){
            ChevauxController.context = context;
        }
        if(ChevauxController.instance == null){
            ChevauxController.instance = new ChevauxController();
//            @todo recuperer chevaux
        }
        return ChevauxController.instance;
    }

    public void creerChevaux(String nom, int idEnclos, int idCapteur){
        ChevauxClass chevaux = new ChevauxClass(lastInsertId,nom,idEnclos,idCapteur);
        // Add a new document with a generated ID
        db.collection("chevaux")
                .add(chevaux)
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

    public void deleteChevaux(ChevauxClass chevaux){
        firebaseRefChevaux.child(Integer.toString(chevaux.getId())).removeValue();
        lesChevaux.remove(chevaux);
    }

    public void updateChevaux(ChevauxClass chevaux){
        firebaseRefChevaux.child(Integer.toString(chevaux.getId())).child("nom").setValue(chevaux.getNom());
        firebaseRefChevaux.child(Integer.toString(chevaux.getId())).child("idEnclos").setValue(chevaux.getIdEnclos());
        firebaseRefChevaux.child(Integer.toString(chevaux.getId())).child("idCapteur").setValue(chevaux.getIdCapteur());
    }

    public void setChevaux(ChevauxClass chevaux){
        ChevauxController.chevaux = chevaux;
    }

    public ArrayList<ChevauxClass> getLesChevaux() {
        return lesChevaux;
    }

    public void setLesEnclos(ArrayList<ChevauxClass> lesChevaux) {
        ChevauxController.lesChevaux = lesChevaux;
    }

    public void setLastInsertId() {
        // FIREBASE  insertion chevaux

        firebaseRefChevaux.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // access last chevaux
                    DataSnapshot messageSnapShot= dataSnapshot.getChildren().iterator().next();
                    lastInsertId = Integer.parseInt(messageSnapShot.getKey());
                    Log.v("lastInsertId","*********** "+ Integer.toString(lastInsertId));
                }
                else{
                    lastInsertId = 0;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
//                Log.w("***********", "Failed to read value.", error.toException());
            }
        });

        this.lastInsertId = lastInsertId;
    }

    public void getAllChevaux(final Context context, final ListView lvListeChevaux, final TextView textVide){

        db.collection("chevaux")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            lesChevaux.clear();
                            lesChevaux.addAll(task.getResult().toObjects(ChevauxClass.class));
                            if (lesChevaux.size() != 0) {

                                ChevauxListAdapter adapter = new ChevauxListAdapter(context, lesChevaux);
                                lvListeChevaux.setAdapter(adapter);
                            } else {
                                textVide.setText("Vous n'avez aucun cheval pour le moment, cliquez sur le + pour en ajouter.");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public ChevauxClass getChevauxUpdate() {
        return chevauxUpdate;
    }

    public void setChevauxUpdate(ChevauxClass chevauxUpdate) {
        this.chevauxUpdate = chevauxUpdate;
    }

    public static Context getContext() {
        return context;
    }
}


