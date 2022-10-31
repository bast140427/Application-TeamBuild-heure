package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.util.Utilitaire;

import java.io.Serializable;
import java.util.ArrayList;

public class Prestataire implements Serializable {

    private String nom;
    private String numero_siret;
    private String adresse;
    private String mail;
    private String numero_telephone;
    private ArrayList<Prestation> prestations = new ArrayList<>();

    public Prestataire(String nom, String numero_siret, String adresse, String mail, String numero_telephone) {
        setNom(nom);
        this.numero_siret = numero_siret;
        this.adresse = adresse;
        this.mail = mail;
        this.numero_telephone = numero_telephone;
    }

    public void ajoutPrestation(Prestation prestation){
        prestations.add(prestation);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = Utilitaire.capitalize(nom);
    }

    public String getNumero_siret() {
        return numero_siret;
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

    public ArrayList<Prestation> getPrestations() {
        return prestations;
    }
}
