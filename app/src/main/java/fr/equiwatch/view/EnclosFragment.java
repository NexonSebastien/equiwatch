package fr.equiwatch.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.EnclosClass;

public class EnclosFragment extends Fragment {

    // propriétés
    private EnclosController enclosController;

    public EnclosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        init();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enclos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.enclosController = EnclosController.getInstance(this.getContext());
        ArrayList<EnclosClass> lesEnclos = enclosController.getLesEnclos();
        Log.d("lesEnclos", "**********"+lesEnclos.size());
        if(lesEnclos.size() != 0){
            ListView lvListeEnclos = (ListView) getView().findViewById(R.id.lvListeEnclos);
            EnclosListAdapter adapter = new EnclosListAdapter(this.getContext(), lesEnclos);
            lvListeEnclos.setAdapter(adapter);
        }
        else{
            TextView textVide = (TextView) getView().findViewById(R.id.txtVide);
            textVide.setText("Vous n'avez aucun enclos pour le moment cliqué sur le plus pour en ajouter.");
        }
    }

    private void init(){

        //Log.d("EnclosController", "**********"+this.enclosController.toString());
        creerListe();

    }

    /**
     * Création de la liste des enclos
     */
    private void creerListe()
    {

    }
}