package fr.equiwatch.view;

import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.EnclosClass;

public class EnclosFragment extends Fragment {

    // propriétés
    private EnclosController enclosController;
    private static EnclosFragment enclosFragment;


    public EnclosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.enclosFragment = new EnclosFragment();
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
        getView().findViewById(R.id.imgBtnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextAct = new Intent(getContext(),EnclosActivity.class);
                startActivity(nextAct);
//                MenuEquiwatch.getMenuEquiwatch().showFragment(new EnclosCreateFragment());

            }
        });
    }

    public static EnclosFragment getEnclosFragment() {
        return enclosFragment;
    }
}