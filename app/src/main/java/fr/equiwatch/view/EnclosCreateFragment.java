package fr.equiwatch.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;

public class EnclosCreateFragment extends Fragment {

    // propriétés
    private EnclosController enclosController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.fragment_enclos_create);
        this.enclosController = EnclosController.getInstance(getContext());
        getActivity().findViewById(R.id.btnEnclosCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inpNomEnclos = (EditText) getActivity().findViewById(R.id.iptNomEnclos);
                String nomEnclos = inpNomEnclos.getText().toString();
//                Log.v("***********",nomEnclos);
                enclosController.creerEnclos(nomEnclos);


//              @todo refrech le fragment

//              @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                Snackbar snackbarSupr = Snackbar.make(view, "Vous venez de créer l'enclos : " + nomEnclos, Snackbar.LENGTH_LONG);
                View viewEnclos = snackbarSupr.getView();
                viewEnclos.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();
            }
        });
    }
}
