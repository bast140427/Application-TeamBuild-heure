package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.util.Utilitaire;

import java.io.Serializable;

/**
 * Définit les types de prestation
 * Une prestation peut être soit de la sécurité, une aide de service logistique,
 * de la restauration, des animations (les activités), de la restauration ou du transport
 */
public enum Type_presta implements Serializable {

    SECURITE("sécurité"),
    SERVICE_LOGISTIQUE("service logistique"),
    ANIMATION("animation"),
    RESTAURATION("restaurateur"),
    TRANSPORT("transport");

    private String libelle;

    private Type_presta(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = Utilitaire.capitalize(libelle);
    }
}