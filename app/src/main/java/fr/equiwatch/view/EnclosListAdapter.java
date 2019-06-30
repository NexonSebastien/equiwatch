package fr.equiwatch.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.model.EnclosClass;

public class EnclosListAdapter extends BaseAdapter {

    private ArrayList<EnclosClass> lesEnclos;
    private LayoutInflater inflater;

    public EnclosListAdapter(Context context, ArrayList<EnclosClass> lesEnclos){
        this.lesEnclos = lesEnclos;
        this.inflater = LayoutInflater.from(context);
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

            // la ligne est construite avec un formatage (inflater) remié à layout_liste_enclos
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


        return view;
    }

    private class ViewHolder{
        TextView txtLabelListeEnclos;
        ImageButton imgBtnViewEnclos;
        ImageButton imgBtnUpdateEnclos;
        ImageButton imgBtnDeleteEnclos;


    }
}
