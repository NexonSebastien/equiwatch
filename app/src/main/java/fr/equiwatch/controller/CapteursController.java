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
import fr.equiwatch.model.CapteursClass;
import static androidx.constraintlayout.widget.Constraints.TAG;

public final class CapteursController {

    /**
     * Capteur controller
     */
    private static CapteursController instance = null;

    /**
     * Capteur class
     */
    private static CapteursClass capteurs;

    /**
     * context
     */
    private static Context context;

    /**
     * Lien vers la base de donnée firestore
     */
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * capteur update pour la modification
     */
    private CapteursClass capteursUpdate;

    /**
     * Capteur view pour la vue
     */
    private CapteursClass capteursView;

    /**
     * liste des capteurs
     */
    private ArrayList<CapteursClass> lesCapteurs = new ArrayList<>();

    private static final String COLLECTION_CAPTEURS = "capteurs";

    /**
     * constructeur private
     */
    private CapteursController(){
        super();
        getAllCapteurs();
    }

    /**
     * Permet de recupérer l'instance ou de la créer
     * @param context
     * @return
     */
    public static final CapteursController getInstance(Context context){
        if(context != null){
            CapteursController.context = context;
        }
        if(CapteursController.instance == null){
            CapteursController.instance = new CapteursController();
        }
        return CapteursController.instance;
    }

    /**
     * Permet de créer un capterus
     * @param label
     * @param type
     */
    public void creerCapteurs(final String label, final String type){

        // Add a new document with a generated ID
        db.collection(COLLECTION_CAPTEURS)
                .add(capteurs)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        CapteursClass unCapteurs = new CapteursClass(label,type);
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        String key = documentReference.getId();
                        capteurs.setId(key);
                        addUniqueIdToCapteurs(capteurs,key);
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
     * Permet d'ajouter une clée unique au capteur
     * @param capteurs
     * @param id
     */
    public void addUniqueIdToCapteurs(CapteursClass capteurs, String id) {
        capteurs.setId(id);
        db.collection(COLLECTION_CAPTEURS).document(id)
                .set(capteurs)
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
     * Permet de supprimer un capteur
     * @param capteurs
     */
    public void deleteCapteurs(CapteursClass capteurs){
        db.collection(COLLECTION_CAPTEURS).document(capteurs.getId())
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
        lesCapteurs.remove(capteurs);
    }

    /**
     * Permet de modifier un capteur
     * @param capteurs
     */
    public void updateCapteurs(CapteursClass capteurs){
        db.collection(COLLECTION_CAPTEURS).document(capteurs.getId())
                .set(capteurs)
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
    }

    /**
     * Permet de definir capteurs
     * @param capteurs
     */
    public static void setCapteurs(CapteursClass capteurs){
        CapteursController.capteurs = capteurs;
    }

    /**
     * Permet de réucperer la liste des capteurs
     * @return
     */
    public ArrayList<CapteursClass> getLesCapteurs() {
        return lesCapteurs;
    }

    /**
     * Permet de récupérer sur la base de donnée
     */
    public void getAllCapteurs() {
        db.collection(COLLECTION_CAPTEURS)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value,
                                    @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                lesCapteurs.clear();
                lesCapteurs.addAll(value.toObjects(CapteursClass.class));
                }
            });
    }


    /**
     * Permet de récuperer le capteur à modifier
     * @return
     */
    public CapteursClass getCapteursUpdate() {
        return capteursUpdate;
    }

    /**
     * Permet de définir le capteur à modifier
     * @param capteursUpdate
     */
    public void setCapteursUpdate(CapteursClass capteursUpdate) {
        this.capteursUpdate = capteursUpdate;
    }

    /**
     *Permet de récupérer le context
     * @return
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Permet de récupérer le capteursView
     * @return
     */
    public CapteursClass getCapteursView() {
        return capteursView;
    }

    /**
     * Permet de définir capteursView
     * @param capteursView
     */
    public void setCapteursView(CapteursClass capteursView) {
        this.capteursView = capteursView;
    }
}


