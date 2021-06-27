package com.example.marieange;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.marieange.event.Evenement;

public class DetailActivity extends AppCompatActivity {
    public static final String EVENEMENT_EXTRA = "EVENEMENT_EXTRA";

    private TextView tv_titre;
    private ImageView iv;
    private Evenement evenement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tv_titre = findViewById(R.id.tv_titre);
        iv = findViewById(R.id.iv);

        //on récupère l'événement
        evenement = (Evenement) getIntent().getSerializableExtra(EVENEMENT_EXTRA);

        // Remplir le textView
        tv_titre.setText(evenement.getDescription());
        // Mes icones images into (rajouté) dans mon imageView
        Glide.with(this).load(evenement.getUrl()).error(R.drawable.error).into(iv);
    }
}
