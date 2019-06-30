package fr.equiwatch.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.EnclosClass;

public class EnclosActivity extends AppCompatActivity {

    // propriétés
    private EnclosController enclosController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enclos);
        init();
    }

    private void init(){
        this.enclosController = EnclosController.getInstance(this);
        //Log.d("EnclosController", "**********"+this.enclosController.toString());
        creerListe();

    }

    /**
     * Création de la liste des enclos
     */
    private void creerListe()
    {
        ArrayList<EnclosClass> lesEnclos = enclosController.getLesEnclos();
        if(lesEnclos != null){
            ListView lvListeEnclos = (ListView) findViewById(R.id.lvListeEnclos);
            EnclosListAdapter adapter = new EnclosListAdapter(this, lesEnclos);
            lvListeEnclos.setAdapter(adapter);
        }
    }

    public void recupEnclos(){

    }
}
