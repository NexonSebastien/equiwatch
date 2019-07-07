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
import fr.equiwatch.controller.ChevauxController;
import fr.equiwatch.model.ChevauxClass;

public class ChevauxActivity extends MenuEquiwatch  {

    // propriétés
    private ChevauxController chevauxController;
    private static ChevauxActivity chevauxActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_chevaux, dynamicContent, true);
        super.onCreate(savedInstanceState);

        chevauxActivity = this;
        this.chevauxController = ChevauxController.getInstance(this);
        ArrayList<ChevauxClass> lesChevaux = chevauxController.getLesChevaux();
        Log.d("lesChevaux", "**********" + lesChevaux.size());
        ListView lvListeChevaux = (ListView) findViewById(R.id.lvListeChevaux);
        TextView textVide = (TextView) findViewById(R.id.txtVide);
        chevauxController.getAllChevaux(this, lvListeChevaux, textVide);
        findViewById(R.id.imgBtnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextAct = new Intent(chevauxActivity, ChevauxCreateActivity.class);
                startActivity(nextAct);
                finish();
            }
        });
    }

    public static ChevauxActivity getChevauxActivity() {
        return chevauxActivity;
    }
}