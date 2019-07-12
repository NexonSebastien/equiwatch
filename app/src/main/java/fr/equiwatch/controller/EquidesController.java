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
import static androidx.constraintlayout.widget.Constraints.TAG;

public final class EquidesController {

    private static EquidesController instance = null;
    private static EquidesClass equides;
    private static Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EquidesClass equidesUpdate;
    private EquidesClass equidesView;
    private ArrayList<EquidesClass> lesEquides = new ArrayList<>();
    private static final String COLLECTION_EQUIDES = "equides";


    /**
     * constructeur private
     */
    private EquidesController(){
        super();
        getAllEquides();
    }

    /**
     * constructeur public
     * @param context
     * @return
     */
    public static final EquidesController getInstance(Context context){
        if(context != null){
            EquidesController.context = context;
        }
        if(EquidesController.instance == null){
            EquidesController.instance = new EquidesController();
        }
        return EquidesController.instance;
    }

    /**
     * Créer un equides
     * @param nom
     * @param idEnclos
     * @param idCapteur
     */
    public void creerEquides(final String nom, final String idEnclos, final String idCapteur){
        // Add a new document with a generated ID
        db.collection(COLLECTION_EQUIDES)
            .add(equides)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    EquidesClass unEquides = new EquidesClass(nom,idEnclos,idCapteur);
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    String key = documentReference.getId();
                    unEquides.setId(key);
                    addUniqueIdToEquides(unEquides,key);
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
     * Ajouter la clée primaire à l'équides
     * @param equides
     * @param id
     */
    public void addUniqueIdToEquides(EquidesClass equides, String id) {
        equides.setId(id);
        db.collection(COLLECTION_EQUIDES).document(id)
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

    /**
     * supprimer un equides
     * @param equides
     */
    public void deleteEquides(EquidesClass equides){
        db.collection(COLLECTION_EQUIDES).document(equides.getId())
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

    /**
     * Modifier un equides
     * @param equides
     */
    public void updateEquides(EquidesClass equides){
        db.collection(COLLECTION_EQUIDES).document(equides.getId())
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

    /**
     * Définir un equides
     * @param equides
     */
    public static void setEquides(EquidesClass equides){
        EquidesController.equides = equides;
    }

    /**
     * Récupérer la liste des equides
     * @return
     */
    public ArrayList<EquidesClass> getLesEquides() {
        return lesEquides;
    }

    /**
     * Récupérer tout les equidé de la base de donnée firestore
     */
    public void getAllEquides() {
        db.collection(COLLECTION_EQUIDES)
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

    /**
     * Récupérer l'equides à modifier
     * @return
     */
    public EquidesClass getEquidesUpdate() {
        return equidesUpdate;
    }

    /**
     * Définir l'equides à modifier
     * @param equidesUpdate
     */
    public void setEquidesUpdate(EquidesClass equidesUpdate) {
        this.equidesUpdate = equidesUpdate;
    }

    /**
     * Récupérer le context
     * @return
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Récupérer l'equides à voir
     * @return
     */
    public EquidesClass getEquidesView() {
        return equidesView;
    }

    /**
     * Définir l'equides à voir
     * @param equidesView
     */
    public void setEquidesView(EquidesClass equidesView) {
        this.equidesView = equidesView;
    }
}


