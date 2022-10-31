package fr.uga.iut2.genevent.modele;

import java.io.Serializable;

/**
 * Définit les types d'hébergement
 * Un hébergement peut être soit un hotel, un camping, une auberge ou un gite
 */
public enum Type_Hebergement implements Serializable {

    HOTEL("hotel"),
    CAMPING("camping"),
    AUBERGE("auberge"),
    GITE("gite");

    private String libelle;

    private Type_Hebergement(String libelle){
        this.libelle = libelle;
    }
}
