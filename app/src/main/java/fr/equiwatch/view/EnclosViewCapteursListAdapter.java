package fr.equiwatch.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.CapteursController;
import fr.equiwatch.model.CapteursClass;

public class EnclosViewCapteursListAdapter extends BaseAdapter {

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
     * Constructeur
     * @param context
     * @param lesCapteurs
     */
    public EnclosViewCapteursListAdapter(Context context, ArrayList<CapteursClass> lesCapteurs){
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

            // la ligne est construite avec un formatage (inflater) relié à layout_enclosview_capteurs
            view = inflater.inflate(R.layout.layout_enclosview_capteurs,null);

            //chaque propriété du holder est relié à une propriété graphique
            holder.txtLabelCapteurs = (TextView)view.findViewById(R.id.txtLabelCapteurs);
            holder.txtDonneeCapteur = (TextView)view.findViewById(R.id.txtDonneeCapteur);

            //affecter le holder à la vue
            view.setTag(holder);
        }else{
            //récupération du holder dans la ligne existante
            holder = (ViewHolder) view.getTag();
        }
        //valorisation du contenu du holder de la ligne
        holder.txtLabelCapteurs.setText(lesCapteurs.get(i).getLabel());
        holder.txtDonneeCapteur.setText(lesCapteurs.get(i).getDonnee());

        return view;
    }

    /**
     * Défini les different champ et bouton du Holder
     */
    private class ViewHolder{
        TextView txtLabelCapteurs;
        TextView txtDonneeCapteur;
    }
}
