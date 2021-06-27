package com.example.marieange;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.marieange.event.Evenement;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DateAdapter.DateAdapterCB {

    private static final int ID_ITEM1 = 1;
    private static final int ID_ITEM_AJOUTER = 2;

    // Composant graphique. Pointeur vers mon recycler
    private RecyclerView rv;

    // Données
    ArrayList<Evenement> evenementArrayList;

    // Outils
    private DateAdapter dateAdapter;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupère un objet calendar avec l'heure
        calendar = Calendar.getInstance();
        evenementArrayList = new ArrayList<>(); // Je crée mon ArrayList que je transmet à mon dateAdapter
        // on lui transmet (le tableau, le mainActivity)
        dateAdapter = new DateAdapter(evenementArrayList, this);

        rv = (RecyclerView) findViewById(R.id.rv);
        // Elements affichés les un en dessous des autres sur deux colonnes
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(dateAdapter);

        //Jeu de données
        calendar.add(Calendar.MONTH, -51);
        evenementArrayList.add(new Evenement(calendar.getTime(), "Toto"));
        calendar.add(Calendar.MONTH, 48);
        evenementArrayList.add(new Evenement(calendar.getTime(), "Tata"));
        calendar.add(Calendar.MONTH, 12);
        evenementArrayList.add(new Evenement(calendar.getTime(), "Titi"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            // groupId, id de l'item, je ne gère pas l'ordre, titre .
            // rajouter l'image à mon menu (choisir l'icone dans drawable) .
            // .setShowAsAction() : i ndiquer que notre icone apparrait sur notre barre
            menu.add(0,ID_ITEM1,0, "Menu 1").setIcon(R.drawable.horloge).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            // Il est impossible de mettre une image dans le menu déroulant
            menu.add(0,ID_ITEM_AJOUTER,0, "Ajouter");
            return super.onCreateOptionsMenu(menu);
    }
    @Override
    // Un callback sur mon menu
    public boolean onOptionsItemSelected(MenuItem item) {
        // savoir sur quel item on a pressé
        if(item.getItemId() == 1) {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        else {
            // (le contexte, listener un callback (quand l'utilisateur clique sur oui), annee, mois, jour
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH + 1), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show(); // Afficher la dialog
        }
        return  super.onOptionsItemSelected(item);
    }

    // Méthode qui sera appellée quand on appuie ok sur l'agenda
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        // true = affichage 24 heures ( et non pas 12 heures)
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,   this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show(); // Montrer le picker des heures
    }

    // Le resultat des heures
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // heure et minutes recues
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        demanderDescription();
    }
    // Alert dialogue . Caque méthode pointent vers elle meme
    public void demanderDescription() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Description"); // Titre de la dialogue

        final EditText input = new EditText(this); // Je crée un deitText je lui transmet un contexte
        builder.setView(input); // Je transmet le texte à mon builder

        // Sur le bouton ajouter
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Ajout d'un evenement
                // Récupère le contenu de l'alertDialog et le rajoute à mon evenement
                Evenement evenement = new Evenement(calendar.getTime(), input.getText().toString());

                evenementArrayList.add(0,evenement); // Je donne l'évenement à mon arrayList
                dateAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Annuler", null);

        builder.show(); // Afficher
    }

    @Override
    // Effet pour enlever l'evenement avec une glissade
    // Au moment d'un cliq sur une ligne de mon recyclerView dateAdaptert appellera .onEvenementClick de l'objet qu'on lui a transmis
    public void onEvenementClick(Evenement evenement, DateAdapter.ViewHolder viewHolder) {

        // Transition ........................
        Intent intent = new Intent(this, DetailActivity.class);
        // transmettre l'evenement avec ma clé
        intent.putExtra(DetailActivity.EVENEMENT_EXTRA, evenement);
        // Transmettre les elements graphique avec Pair

        startActivity(intent);

        // Suppression ........................
        // this : le contexte qui est mainActivity
       //  Toast.makeText(this, evenement.getDescription(), Toast.LENGTH_SHORT).show();
        // Connaitre la position de mon element dans la liste
        // int positionElement = evenementArrayList.indexOf(evenement);
        // Supprimer l'evenement qui a été cliqué
        // evenementArrayList.remove(evenement);
        // prévenir l'adapter qu'on remove un element
        // dateAdapter.notifyItemRemoved(positionElement); // Si je veux voir une animation de disparition
    }
}