package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.util.Utilitaire;

import java.io.Serializable;

public class Reunion implements Serializable {

    private String objet;
    private int duree;
    private int nb_personnes;
    private int heure_deb;
    private String lieu;

    public Reunion(String objet, int duree, int nb_personnes, int heure_deb, String lieu) {
        setObjet(objet);
        this.duree = duree;
        this.nb_personnes = nb_personnes;
        this.heure_deb = heure_deb;
        this.lieu = lieu;
    }



    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = Utilitaire.capitalize(objet);
    }

    public int getDuree() {
        return duree;
    }

    public int getNb_personnes() {
        return nb_personnes;
    }

    public int getHeure_deb() {
        return heure_deb;
    }

    public String getLieu() {
        return lieu;
    }
}
