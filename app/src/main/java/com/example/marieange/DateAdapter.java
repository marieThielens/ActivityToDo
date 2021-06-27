package com.example.marieange;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marieange.event.Evenement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

// etends le recyclerView
public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    private ArrayList<Evenement> dates;
    private SimpleDateFormat format; // formater la date pour un affichage correct
    private DateAdapterCB dateAdapterCB; // Un pointeur qui alimente l'interface

    public DateAdapter(ArrayList<Evenement> dates, DateAdapterCB dateAdapterCB) {
        this.dates = dates;
        this.dateAdapterCB = dateAdapterCB;
        format = new SimpleDateFormat("dd/MM/yyyy hh'h'mm");
    }

    /* ---------------------------------
    // @Override
    // -------------------------------- */

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Je transmet mon layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_date, parent, false);
        return new DateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Je récupère la position de mon element
        final Evenement evenement = dates.get(position);

        holder.tv_description.setText(evenement.getDescription()); // la description de mon evenement
        //Mise en forme de la date
        holder.tv_date.setText(format.format(evenement.getDate())); // convertir ma date dans le bon format

        // holder.iv.setImageResource(evenement.getCheminImage());
        // images externe. si il n'y a pas de connexion une image d'erreur
        Glide.with(holder.tv_date.getContext()).load(evenement.getUrl()).error(R.drawable.error).placeholder(R.drawable.run).into(holder.iv);

        // Intercepte les clics
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateAdapterCB != null) { // risque de null pointer exeption
                    //  au clic je transmet mon evenement
                    dateAdapterCB.onEvenementClick(evenement, holder);
                }
            }
        });
    }

    @Override
    // Pour le nombre de date à afficher
    public int getItemCount() {
        return dates.size();
    }


    // ViewHolder (element graphique de ma ligne ) ..................................
    protected class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_date, tv_description;
        public View root; // Pointeur vers le linearLayout
        public ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            // Mes composants graphiques
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_description = itemView.findViewById(R.id.tv_description);
            root = itemView.findViewById(R.id.root);
            iv = itemView.findViewById(R.id.iv);
        }
    }

    // que dateAdapter puisse transmettre l'évènement cliqué à main activity
    public interface DateAdapterCB {
        // A une methode qui au clic renvoie un evenement
        void onEvenementClick(Evenement evenement, ViewHolder viewHolder);

    }
}
