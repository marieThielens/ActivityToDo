package com.example.marieange.event;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.example.marieange.R;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Evenement implements Serializable {

    private Date date;
    private String description;
    private String url; // chemin vers les images

    private static final String[] images = new String[]{
        "https://cdn.pixabay.com/photo/2017/03/07/11/33/cup-2123710_960_720.jpg",
        "https://cdn.pixabay.com/photo/2014/06/18/13/23/time-371226_960_720.jpg",
        "https://cdn.pixabay.com/photo/2015/01/25/18/11/clock-611619__340.jpg",
        "https://cdn.pixabay.com/photo/2012/04/14/14/04/hourglass-34048__340.png"
    };

    public Evenement(Date date, String description) {
        this.date = date;
        this.description = description;

        // Une image aléatoire du tableau
        int aleatoire = new Random().nextInt(images.length);
        url = images[aleatoire];
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "date=" + date +
                ", description='" + description + '\'' +
                '}';
    }

    // Le chemin vers mon image
    public String getUrl() {
        return url;
    }

//    public int getCheminImage() {
//        return cheminImage;
//    }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
