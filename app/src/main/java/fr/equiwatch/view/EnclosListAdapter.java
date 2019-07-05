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
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.EnclosClass;

public class EnclosListAdapter extends BaseAdapter {

    private ArrayList<EnclosClass> lesEnclos;
    private LayoutInflater inflater;
    private EnclosController enclosController;
    private View uneView;


    public EnclosListAdapter(Context context, ArrayList<EnclosClass> lesEnclos){
        this.lesEnclos = lesEnclos;
        this.inflater = LayoutInflater.from(context);
        this.enclosController = EnclosController.getInstance(null);
    }

    /**
     * Retourne le nombre de lignes de la liste
     * @return
     */
    @Override
    public int getCount() {
        return lesEnclos.size();
    }

    /**
     * Retourne l'item de la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return lesEnclos.get(i);
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

            // la ligne est construite avec un formatage (inflater) relié à layout_liste_enclos
            view = inflater.inflate(R.layout.layout_list_enclos,null);

            //chaque propriété du holder est relié à une propriété graphique
            holder.txtLabelListeEnclos = (TextView)view.findViewById(R.id.txtLabelListeEnclos);
            holder.imgBtnViewEnclos = (ImageButton) view.findViewById(R.id.imgBtnViewEnclos);
            holder.imgBtnUpdateEnclos = (ImageButton) view.findViewById(R.id.imgBtnUpdateEnclos);
            holder.imgBtnDeleteEnclos = (ImageButton) view.findViewById(R.id.imgBtnDeleteEnclos);

            //affecter le holder à la vue
            view.setTag(holder);
        }else{
            //récupération du holder dans la ligne existante
            holder = (ViewHolder) view.getTag();
        }
        //valorisation du contenu du holder de la ligne
        holder.txtLabelListeEnclos.setText(lesEnclos.get(i).getLabel());
        holder.imgBtnViewEnclos.setTag(i);
        holder.imgBtnUpdateEnclos.setTag(i);
        holder.imgBtnDeleteEnclos.setTag(i);

        // clic pour supprimer un enclos
        holder.imgBtnDeleteEnclos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uneView = view;

                //Alterte avant suppression
                Dialog d = new AlertDialog.Builder(enclosController.getContext())
                        .setTitle("Alerte avant suppression")
                        .setMessage("Voulez vous vraiment supprimer ?")
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int position = (int) uneView.getTag();
                                Snackbar snackbarSupr = Snackbar.make(uneView, "Suppression de l'enclos : " + lesEnclos.get(position).getLabel(), Snackbar.LENGTH_LONG);

                                //demande de suppression au controller
                                enclosController.deleteEnclos(lesEnclos.get(position));

                                View viewEnclos = snackbarSupr.getView();
                                viewEnclos.setBackgroundResource(R.color.colorPrimary);
                                snackbarSupr.show();

                                //rafraichir la liste
                                notifyDataSetChanged();
                            }
                        })
                        .create();
                d.show();
            }
        });

        // clic pour modifier un enclos
        holder.imgBtnUpdateEnclos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                Snackbar snackbarSupr = Snackbar.make(view, "modifier " + lesEnclos.get(position).getId(), Snackbar.LENGTH_LONG);

                Intent nextAct = new Intent(enclosController.getContext(), EnclosUpdateActivity.class);
                enclosController.getContext().startActivity(nextAct);
                //demande de suppression au controller
                enclosController.setEnclosUpdate(lesEnclos.get(position));

                View viewEnclos = snackbarSupr.getView();

                viewEnclos.setBackgroundResource(R.color.colorPrimary);

                snackbarSupr.show();

                //rafraichir la liste
                notifyDataSetChanged();

            }
        });

        // clic pour voir un enclos
        holder.imgBtnViewEnclos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();


                //demande de suppression au controller
//                enclosController.deleteEnclos(lesEnclos.get(position));

                Snackbar snackbarSupr = Snackbar.make(view, "voir " + lesEnclos.get(position).getId(), Snackbar.LENGTH_LONG);
                View viewEnclos = snackbarSupr.getView();
                viewEnclos.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();
            }
        });

        return view;
    }

    private class ViewHolder{
        TextView txtLabelListeEnclos;
        ImageButton imgBtnViewEnclos;
        ImageButton imgBtnUpdateEnclos;
        ImageButton imgBtnDeleteEnclos;


    }
}
