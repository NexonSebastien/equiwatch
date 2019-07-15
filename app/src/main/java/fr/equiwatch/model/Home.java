package fr.equiwatch.model;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.equiwatch.controller.CapteursController;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.controller.EquidesController;
import fr.equiwatch.view.MainActivity;
import fr.equiwatch.view.MenuMapsActivity;
import fr.equiwatch.view.ProfileActivity;

public class Home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            EnclosController enclosController = EnclosController.getInstance(Home.this);
            EquidesController equidesController = EquidesController.getInstance(Home.this);
            CapteursController capteursController = CapteursController.getInstance(Home.this);
            startActivity(new Intent(Home.this, MenuMapsActivity.class));
        }

    }
}
