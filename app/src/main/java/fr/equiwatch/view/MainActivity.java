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
    private Button bt_maps;
    private EnclosController enclosController;
    private EquidesController equidesController;
    private CapteursController capteursController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);

        bt_maps = findViewById(R.id.bt_maps);
        bt_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps();
            }
        });

//        Permet d'ajouter dans les differentes liste les enclos, equides et capteurs existant en base de donnée.
        enclosController = EnclosController.getInstance(this);
        equidesController = EquidesController.getInstance(this);
        capteursController = CapteursController.getInstance(this);
    }

    public void openMaps() {
        Intent intent = new Intent(this, MenuMapsActivity.class);
        startActivity(intent);
    }
}
