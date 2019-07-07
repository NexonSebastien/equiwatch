package fr.equiwatch.controller;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import fr.equiwatch.model.PointsGpsClass;

public final class PointsGpsController {

    private static PointsGpsController instance = null;
    private static PointsGpsClass point;
    private static ArrayList<PointsGpsClass> lesPoints;
    private static Context context;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EnclosController enclosController;

    /**
     * constructeur private
     */
    private PointsGpsController(){
        super();
        lesPoints = new ArrayList<PointsGpsClass>();
        this.enclosController = EnclosController.getInstance(null);
    }

    public static final PointsGpsController getInstance(Context context){
        if(context != null){
            PointsGpsController.context = context;
        }
        if(PointsGpsController.instance == null){
            PointsGpsController.instance = new PointsGpsController();
        }
        return PointsGpsController.instance;
    }
}
