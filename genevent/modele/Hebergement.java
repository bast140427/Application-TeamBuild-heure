package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.PrixException;
import fr.uga.iut2.genevent.util.Utilitaire;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe Hebergement permet de récupérer les informations des hébergements de notre application
 * Il est construit avec une libelle, le prix, la capacité de l'hotel, la description, le type d'hebergement
 * son adresse, son mail, son numéro de téléphone, ainsi que son nombre d'étoiles
 */
public class Hebergement implements Serializable {

    // Récupération du logger
    private static Logger LOGGER =  Logger.getLogger(Calendrier_prestation.class.getPackageName());

    private String libelle;
    private float prix;
    private int capacite;
    private String description;
    private Type_Hebergement type;
    private String adresse;
    private String mail;
    private String numero_telephone;
    private Ville ville;
    private int etoile;

    public Hebergement(String libelle, float prix, int capacite, String description, Type_Hebergement type, String adresse, String mail, String numero_telephone, Integer etoiles) throws PrixException {
        setLibelle(libelle);
        setPrix(prix);
        this.capacite = capacite;
        this.description = description;
        this.type = type;
        this.adresse = adresse;
        this.mail = mail;
        this.numero_telephone = numero_telephone;
        this.etoile=etoiles;
    }

    public String getLibelle() {
        return libelle;
    }


    public void setLibelle(String libelle) {
        this.libelle = Utilitaire.capitalize(libelle);
    }

    public float getPrix() {
        return prix;
    }

    /**
     * Le prix ne peut pas être inférieur ou égal à 0
     * @param prix
     */
    private void setPrix(float prix) throws PrixException {
        if(prix <= 0){
            LOGGER.log(Level.WARNING, "Attention, le prix ne peut pas être inférieur ou égal à 0");
            throw new PrixException("Le prix ne peut pas être inférieur ou égal à 0");
        } else {
            this.prix = prix;
        }
    }

    public int getCapacite() {
        return capacite;
    }

    public String getDescription() {
        return description;
    }

    public Type_Hebergement getType() {
        return type;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getMail() {
        return mail;
    }

    public String getNumero_telephone() {
        return numero_telephone;
    }

    public Ville getVille() {
        return ville;
    }

    public int getEtoile() {
        return etoile;
    }
}

