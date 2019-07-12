package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import fr.equiwatch.R;
import fr.equiwatch.controller.CapteursController;
import fr.equiwatch.controller.EquidesController;
import fr.equiwatch.controller.EnclosController;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);

        Button btMaps = findViewById(R.id.bt_maps);
        btMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps();
            }
        });

//        Permet d'ajouter dans les differentes liste les enclos, equides et capteurs existant en base de donnée.
        EnclosController enclosController = EnclosController.getInstance(this);
        EquidesController equidesController = EquidesController.getInstance(this);
        CapteursController capteursController = CapteursController.getInstance(this);
    }

    public void openMaps() {
        Intent intent = new Intent(this, MenuMapsActivity.class);
        startActivity(intent);
    }
}
