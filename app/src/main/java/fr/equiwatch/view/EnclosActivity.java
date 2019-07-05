package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.EnclosClass;

public class EnclosActivity extends MenuEquiwatch  {

    // propriétés
    private EnclosController enclosController;
    private static EnclosActivity enclosActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_enclos, dynamicContent, true);
        super.onCreate(savedInstanceState);
        enclosActivity = this;
        this.enclosController = EnclosController.getInstance(this);
        ArrayList<EnclosClass> lesEnclos = enclosController.getLesEnclos();
        Log.d("lesEnclos", "**********" + lesEnclos.size());
        if (lesEnclos.size() != 0) {
            ListView lvListeEnclos = (ListView) findViewById(R.id.lvListeEnclos);
            EnclosListAdapter adapter = new EnclosListAdapter(this, lesEnclos);
            lvListeEnclos.setAdapter(adapter);
        } else {
            TextView textVide = (TextView) findViewById(R.id.txtVide);
            textVide.setText("Vous n'avez aucun enclos pour le moment cliqué sur le plus pour en ajouter.");
        }
        findViewById(R.id.imgBtnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextAct = new Intent(enclosActivity, EnclosCreateActivity.class);
                startActivity(nextAct);
                finish();
            }
        });
    }

    public static EnclosActivity getEnclosActivity() {
        return enclosActivity;
    }
}