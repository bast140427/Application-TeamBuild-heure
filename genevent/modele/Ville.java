package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.CodePostalException;
import fr.uga.iut2.genevent.util.Utilitaire;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe Ville permet de connaître la ville que l'utilisateur
 * choisit pour son événement et ses prestations
 * Elle est construite avec son nom de la ville de type EnumNomVille et son codePostal
 */
public class Ville implements Serializable {

    // Récupération du logger
    private static Logger LOGGER =  Logger.getLogger(Calendrier_prestation.class.getPackageName());

    private EnumNomVille nom;
    private String codePostal;
    private ArrayList<Evenement> lieuEvenements = new ArrayList<>();
    private ArrayList<Prestation> lieuPrestations = new ArrayList<>();

    public Ville(EnumNomVille nom, String codePostal) throws CodePostalException {
        this.nom = nom;
        setCodePostal(codePostal);
    }

    /**
     * Le code postal doit avoir une taille de 5 sinon on lève
     * CodePostalException
     * @param codePostal
     * @throws CodePostalException
     */
    public void setCodePostal(String codePostal) throws CodePostalException {
        if(codePostal.length()!=5) {
            LOGGER.log(Level.WARNING, "Attention un code postal doit forcément être composé de 5 chiffres");
            throw new CodePostalException("Un code postal doit forcément être composé de 5 chiffres");
        }else{
            this.codePostal=codePostal;
        }
    }

    private void addEvenement(Evenement evenement){
        lieuEvenements.add(evenement);
    }

    private void addPrestation(Prestation prestation){
        lieuPrestations.add(prestation);
    }

    public ArrayList<Evenement> getLieuEvenements() {
        return lieuEvenements;
    }

    public ArrayList<Prestation> getLieuPrestations() {
        return lieuPrestations;
    }

    public EnumNomVille getNom() {
        return nom;
    }

    public String getCodePostal() {
        return codePostal;
    }
}
