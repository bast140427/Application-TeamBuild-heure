package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.util.Utilitaire;

import java.io.Serializable;

public enum EnumNomVille implements Serializable {

    GRENOBLE("Grenoble"),
    PARIS("Paris"),
    BORDEAUX("Bordeaux"),
    NICE("Nice");

    private String libelle;

    EnumNomVille(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = Utilitaire.capitalize(libelle);
    }
}
