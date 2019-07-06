package fr.equiwatch.controller;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import fr.equiwatch.model.EnclosClass;
public final class EnclosController {

    private static EnclosController instance = null;
    private static EnclosClass enclos;
    private static ArrayList<EnclosClass> lesEnclos;
    private static Context context;
    private int lastInsertId;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebaseRefEnclos = database.getReference("/equiwatch/equiwatch/enclos");
    private EnclosClass enclosUpdate;

    /**
     * constructeur private
     */
    private EnclosController(){
        super();
        firebaseRefEnclos.child("1").child("label").setValue("Enclos1");
        firebaseRefEnclos.child("2").child("label").setValue("Enclos2");
        firebaseRefEnclos.child("3").child("label").setValue("Enclos3");
        firebaseRefEnclos.child("4").child("label").setValue("Enclos4");
        firebaseRefEnclos.child("5").child("label").setValue("Enclos5");
        firebaseRefEnclos.child("6").child("label").setValue("Enclos6");
        firebaseRefEnclos.child("7").child("label").setValue("Enclos7");
        lesEnclos = new ArrayList<EnclosClass>();
        getAllEnclos();
        setLastInsertId();
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

    public void creerEnclos(String label){
        int id = lastInsertId + 1;
        Log.v("lastInsertId1","*********** "+ lastInsertId);
        enclos = new EnclosClass(id, label);
        firebaseRefEnclos.child(Integer.toString(id)).child("label").setValue(enclos.getLabel());
        lesEnclos.add(enclos);
        Log.v("lastInsertId2","*********** "+ lastInsertId);
        Log.v("idForInsert","*********** "+ id);
    }

    public void deleteEnclos(EnclosClass unEnclos){
        firebaseRefEnclos.child(Integer.toString(unEnclos.getId())).removeValue();
        lesEnclos.remove(unEnclos);
    }

    public void updateEnclos(EnclosClass unEnclos){
        firebaseRefEnclos.child(Integer.toString(unEnclos.getId())).child("label").setValue(unEnclos.getLabel());
    }

    public void setEnclos(EnclosClass enclos){
        EnclosController.enclos = enclos;
    }

    public ArrayList<EnclosClass> getLesEnclos() {
        return lesEnclos;
    }

    public void setLesEnclos(ArrayList<EnclosClass> lesEnclos) {
        EnclosController.lesEnclos = lesEnclos;
    }

    public void setLastInsertId() {
        // FIREBASE  insertion enclos

        firebaseRefEnclos.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // access last enclos
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

    public void getAllEnclos(){
        firebaseRefEnclos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lesEnclos.clear();
                for (DataSnapshot unSnapshot : dataSnapshot.getChildren()) {
                    Log.d("label","***********" + unSnapshot.child("label").getValue().toString());
                    lesEnclos.add(new EnclosClass(((int)Integer.parseInt(unSnapshot.getKey().toString())),unSnapshot.child("label").getValue().toString()));
                }
//                for (EnclosClass unEnclos : lesEnclos){
//                    int id = unEnclos.getId();
//                    String label = unEnclos.getLabel();
//                    Log.d("label","***********" + label);
//                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("***********", "Failed to read value.", error.toException());
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
}


