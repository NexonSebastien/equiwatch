package fr.equiwatch.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EquidesController;
import fr.equiwatch.model.EquidesClass;

public class EquidesListAdapter extends BaseAdapter {

    private ArrayList<EquidesClass> lesEquides;
    private LayoutInflater inflater;
    private EquidesController equidesController;
    private View uneView;


    public EquidesListAdapter(Context context, ArrayList<EquidesClass> lesEquides){
        this.lesEquides = lesEquides;
        this.inflater = LayoutInflater.from(context);
        this.equidesController = EquidesController.getInstance(null);
    }

    /**
     * Retourne le nombre de lignes de la liste
     * @return
     */
    @Override
    public int getCount() {
        return lesEquides.size();
    }

    /**
     * Retourne l'item de la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return lesEquides.get(i);
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

            // la ligne est construite avec un formatage (inflater) relié à layout_list_equides
            view = inflater.inflate(R.layout.layout_list_equides,null);

            //chaque propriété du holder est relié à une propriété graphique
            holder.txtLabelListeEquides = (TextView)view.findViewById(R.id.txtLabelListeEquides);
            holder.imgBtnViewEquides = (ImageButton) view.findViewById(R.id.imgBtnViewEquides);
            holder.imgBtnUpdateEquides = (ImageButton) view.findViewById(R.id.imgBtnUpdateEquides);
            holder.imgBtnDeleteEquides = (ImageButton) view.findViewById(R.id.imgBtnDeleteEquides);

            //affecter le holder à la vue
            view.setTag(holder);
        }else{
            //récupération du holder dans la ligne existante
            holder = (ViewHolder) view.getTag();
        }
        //valorisation du contenu du holder de la ligne
        holder.txtLabelListeEquides.setText(lesEquides.get(i).getNom());
        holder.imgBtnViewEquides.setTag(i);
        holder.imgBtnUpdateEquides.setTag(i);
        holder.imgBtnDeleteEquides.setTag(i);

        // clic pour supprimer un equides
        holder.imgBtnDeleteEquides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uneView = view;

                //Alterte avant suppression
                Dialog d = new AlertDialog.Builder(equidesController.getContext())
                        .setTitle("Alerte avant suppression")
                        .setMessage("Voulez vous vraiment supprimer ?")
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int position = (int) uneView.getTag();
                                Snackbar snackbarSupr = Snackbar.make(uneView, R.string.equide_information_delete  + lesEquides.get(position).getNom(), Snackbar.LENGTH_LONG);

                                //demande de suppression au controller
                                equidesController.deleteEquides(lesEquides.get(position));

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

        // clic pour modifier un equides
        holder.imgBtnUpdateEquides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                Snackbar snackbarSupr = Snackbar.make(view, R.string.equide_information_update + lesEquides.get(position).getId(), Snackbar.LENGTH_LONG);

                //demande de modification au controller
                equidesController.setEquidesUpdate(lesEquides.get(position));

                View viewEquides = snackbarSupr.getView();

                viewEquides.setBackgroundResource(R.color.colorPrimary);

                snackbarSupr.show();

                //rafraichir la liste
//                notifyDataSetChanged();
                Intent nextAct = new Intent(EquidesController.getContext(), EquidesUpdateActivity.class);
                EquidesController.getContext().startActivity(nextAct);

            }
        });

        // clic pour voir un equides
        holder.imgBtnViewEquides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();

                //demande de vue au controller
                equidesController.setEquidesView(lesEquides.get(position));

                Intent nextAct = new Intent(EquidesController.getContext(), EquidesViewActivity.class);
                EquidesController.getContext().startActivity(nextAct);
            }
        });

        return view;
    }

    private class ViewHolder{
        TextView txtLabelListeEquides;
        ImageButton imgBtnViewEquides;
        ImageButton imgBtnUpdateEquides;
        ImageButton imgBtnDeleteEquides;


    }
}
