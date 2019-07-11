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
import fr.equiwatch.controller.CapteursController;
import fr.equiwatch.model.CapteursClass;

public class CapteursListAdapter extends BaseAdapter {

    /**
     * Liste des capteurs
     */
    private ArrayList<CapteursClass> lesCapteurs;

    /**
     * inflater
     */
    private LayoutInflater inflater;

    /**
     * Capteur controller
     */
    private CapteursController capteursController;

    /**
     * View
     */
    private View uneView;


    /**
     * Constructeur
     * @param context
     * @param lesCapteurs
     */
    public CapteursListAdapter(Context context, ArrayList<CapteursClass> lesCapteurs){
        this.lesCapteurs = lesCapteurs;
        this.inflater = LayoutInflater.from(context);
        this.capteursController = CapteursController.getInstance(null);
    }

    /**
     * Retourne le nombre de lignes de la liste
     * @return
     */
    @Override
    public int getCount() {
        return lesCapteurs.size();
    }

    /**
     * Retourne l'item de la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return lesCapteurs.get(i);
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

            // la ligne est construite avec un formatage (inflater) relié à layout_list_capteurs
            view = inflater.inflate(R.layout.layout_list_capteurs,null);

            //chaque propriété du holder est relié à une propriété graphique
            holder.txtLabelListeCapteurs = (TextView)view.findViewById(R.id.txtLabelListeCapteurs);
            holder.imgBtnViewCapteurs = (ImageButton) view.findViewById(R.id.imgBtnViewCapteurs);
            holder.imgBtnUpdateCapteurs = (ImageButton) view.findViewById(R.id.imgBtnUpdateCapteurs);
            holder.imgBtnDeleteCapteurs = (ImageButton) view.findViewById(R.id.imgBtnDeleteCapteurs);

            //affecter le holder à la vue
            view.setTag(holder);
        }else{
            //récupération du holder dans la ligne existante
            holder = (ViewHolder) view.getTag();
        }
        //valorisation du contenu du holder de la ligne
        holder.txtLabelListeCapteurs.setText(lesCapteurs.get(i).getLabel());
        holder.imgBtnViewCapteurs.setTag(i);
        holder.imgBtnUpdateCapteurs.setTag(i);
        holder.imgBtnDeleteCapteurs.setTag(i);

        // clic pour supprimer un capteurs
        holder.imgBtnDeleteCapteurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uneView = view;

                //Alterte avant suppression
                Dialog d = new AlertDialog.Builder(capteursController.getContext())
                        .setTitle(R.string.alerte_delete_title)
                        .setMessage(R.string.alerte_delete_message)
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int position = (int) uneView.getTag();
                                Snackbar snackbarSupr = Snackbar.make(uneView, R.string.capteur_information_delete + lesCapteurs.get(position).getLabel(), Snackbar.LENGTH_LONG);

                                //demande de suppression au controller
                                capteursController.deleteCapteurs(lesCapteurs.get(position));

                                View viewCapteurs = snackbarSupr.getView();
                                viewCapteurs.setBackgroundResource(R.color.colorPrimary);
                                snackbarSupr.show();

                                //rafraichir la liste
                                notifyDataSetChanged();
                            }
                        })
                        .create();
                d.show();
            }
        });

        // clic pour modifier un capteurs
        holder.imgBtnUpdateCapteurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                Snackbar snackbarSupr = Snackbar.make(view, R.string.capteur_information_update + lesCapteurs.get(position).getId(), Snackbar.LENGTH_LONG);

                //demande de modification au controller
                capteursController.setCapteursUpdate(lesCapteurs.get(position));

                View viewCapteurs = snackbarSupr.getView();

                viewCapteurs.setBackgroundResource(R.color.colorPrimary);

                snackbarSupr.show();

                //rafraichir la liste
//                notifyDataSetChanged();
                Intent nextAct = new Intent(CapteursController.getContext(), CapteursUpdateActivity.class);
                CapteursController.getContext().startActivity(nextAct);

            }
        });

        // clic pour voir un capteurs
        holder.imgBtnViewCapteurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();

                //demande de vue au controller
                capteursController.setCapteursView(lesCapteurs.get(position));

                Intent nextAct = new Intent(CapteursController.getContext(), CapteursViewActivity.class);
                CapteursController.getContext().startActivity(nextAct);
            }
        });

        return view;
    }

    /**
     * Défini les different champ et bouton du Holder
     */
    private class ViewHolder{
        TextView txtLabelListeCapteurs;
        ImageButton imgBtnViewCapteurs;
        ImageButton imgBtnUpdateCapteurs;
        ImageButton imgBtnDeleteCapteurs;


    }
}
