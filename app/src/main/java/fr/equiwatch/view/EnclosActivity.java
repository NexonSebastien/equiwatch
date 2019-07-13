package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
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
    private static EnclosActivity enclosActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_enclos, dynamicContent, true);
        super.onCreate(savedInstanceState);
        EnclosController enclosController = EnclosController.getInstance(this);
        ArrayList<EnclosClass> lesEnclos;
        enclosActivity = this;
        enclosController = EnclosController.getInstance(this);
        lesEnclos = enclosController.getLesEnclos();
        ListView lvListeEnclos = findViewById(R.id.lvListeEnclos);
        TextView textVide = findViewById(R.id.txtVide);
        if (!lesEnclos.isEmpty()) {
            EnclosListAdapter adapter = new EnclosListAdapter(this, lesEnclos);
            lvListeEnclos.setAdapter(adapter);
        } else {
            textVide.setText(R.string.enclos_information_vide);
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