package fr.equiwatch.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.ChevauxController;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.ChevauxClass;
import fr.equiwatch.model.EnclosClass;

public class ChevauxListAdapter extends BaseAdapter {

    private ArrayList<ChevauxClass> lesChevaux;
    private LayoutInflater inflater;
    private ChevauxController chevauxController;
    private View uneView;


    public ChevauxListAdapter(Context context, ArrayList<ChevauxClass> lesChevaux){
        this.lesChevaux = lesChevaux;
        this.inflater = LayoutInflater.from(context);
        this.chevauxController = ChevauxController.getInstance(null);
    }

    /**
     * Retourne le nombre de lignes de la liste
     * @return
     */
    @Override
    public int getCount() {
        return lesChevaux.size();
    }

    /**
     * Retourne l'item de la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return lesChevaux.get(i);
    }

    /**
     * Retourne un indice par rapport à la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Retourne la ligne (view) formaté avec gestion des évennements
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // déclaration d'un holder
        ViewHolder holder;
        // si la ligne n'existe pas
        if(view == null){
            holder = new ViewHolder();

            // la ligne est construite avec un formatage (inflater) relié à layout_list_chevaux
            view = inflater.inflate(R.layout.layout_list_chevaux,null);

            //chaque propriété du holder est relié à une propriété graphique
            holder.txtLabelListeChevaux = (TextView)view.findViewById(R.id.txtLabelListeChevaux);
            holder.imgBtnViewChevaux = (ImageButton) view.findViewById(R.id.imgBtnViewChevaux);
            holder.imgBtnUpdateChevaux = (ImageButton) view.findViewById(R.id.imgBtnUpdateChevaux);
            holder.imgBtnDeleteChevaux = (ImageButton) view.findViewById(R.id.imgBtnDeleteChevaux);

            //affecter le holder à la vue
            view.setTag(holder);
        }else{
            //récupération du holder dans la ligne existante
            holder = (ViewHolder) view.getTag();
        }
        //valorisation du contenu du holder de la ligne
        holder.txtLabelListeChevaux.setText(lesChevaux.get(i).getNom());
        holder.imgBtnViewChevaux.setTag(i);
        holder.imgBtnUpdateChevaux.setTag(i);
        holder.imgBtnDeleteChevaux.setTag(i);

        // clic pour supprimer un cheval
        holder.imgBtnDeleteChevaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uneView = view;

                //Alterte avant suppression
                Dialog d = new AlertDialog.Builder(chevauxController.getContext())
                        .setTitle("Alerte avant suppression")
                        .setMessage("Voulez vous vraiment supprimer ?")
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int position = (int) uneView.getTag();
                                Snackbar snackbarSupr = Snackbar.make(uneView, "Suppression du cheval : " + lesChevaux.get(position).getNom(), Snackbar.LENGTH_LONG);

                                //demande de suppression au controller
                                chevauxController.deleteChevaux(lesChevaux.get(position));

                                View viewChevaux = snackbarSupr.getView();
                                viewChevaux.setBackgroundResource(R.color.colorPrimary);
                                snackbarSupr.show();

                                //rafraichir la liste
                                notifyDataSetChanged();
                            }
                        })
                        .create();
                d.show();
            }
        });

        // clic pour modifier un cheval
        holder.imgBtnUpdateChevaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                Snackbar snackbarSupr = Snackbar.make(view, "modifier " + lesChevaux.get(position).getId(), Snackbar.LENGTH_LONG);

                Intent nextAct = new Intent(chevauxController.getContext(), ChevauxUpdateActivity.class);
                chevauxController.getContext().startActivity(nextAct);
                //demande de suppression au controller
                chevauxController.setChevauxUpdate(lesChevaux.get(position));

                View viewChevaux = snackbarSupr.getView();

                viewChevaux.setBackgroundResource(R.color.colorPrimary);

                snackbarSupr.show();

                //rafraichir la liste
                notifyDataSetChanged();

            }
        });

        // clic pour voir un cheval
        holder.imgBtnViewChevaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();


                //demande de suppression au controller
//                enclosController.deleteEnclos(lesEnclos.get(position));

                Snackbar snackbarSupr = Snackbar.make(view, "voir " + lesChevaux.get(position).getId(), Snackbar.LENGTH_LONG);
                View viewChevaux = snackbarSupr.getView();
                viewChevaux.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();
            }
        });

        return view;
    }

    private class ViewHolder{
        TextView txtLabelListeChevaux;
        ImageButton imgBtnViewChevaux;
        ImageButton imgBtnUpdateChevaux;
        ImageButton imgBtnDeleteChevaux;


    }
}
