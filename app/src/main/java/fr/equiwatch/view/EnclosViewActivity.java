package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.CapteursController;
import fr.equiwatch.model.CapteursClass;
import fr.equiwatch.model.EnclosClass;

public class EnclosViewActivity extends MenuEquiwatch  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_enclos_view, dynamicContent, true);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if(intent != null && intent.getExtras() != null) {
            EnclosClass enclos =(EnclosClass) intent.getSerializableExtra("enclos");
            ArrayList<String> lesIdCapteurs = enclos.getListIdCapteur();
            CapteursController.getInstance(this).getAllCapteurs();
            ListView lvListeCapteur = findViewById(R.id.lvlEnclosViewCapteurs);
            
            ArrayList<CapteursClass> lesCapteurs = CapteursController.getInstance(this).getLesCapteurs();
            ArrayList<CapteursClass> listeCapteursEnclos = new ArrayList<CapteursClass>();
            for (CapteursClass unCapteurs: lesCapteurs) {
                if(lesIdCapteurs.contains(unCapteurs.getId()) == true){
                    listeCapteursEnclos.add(unCapteurs);
                }
            }
            TextView dynamicLabel = findViewById(R.id.dynamycLabel);
            dynamicLabel.setText(enclos.getLabel());
            if (!listeCapteursEnclos.isEmpty()) {
                EnclosViewCapteursListAdapter adapter = new EnclosViewCapteursListAdapter(this, listeCapteursEnclos);
                lvListeCapteur.setAdapter(adapter);
            }
        }
    }
}
