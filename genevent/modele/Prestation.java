package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.PrixException;
import fr.uga.iut2.genevent.util.Utilitaire;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe Prestation permet de récupérer toutes les informations sur les prestations
 * de l'application
 * Une prestation est construite avec un libelle, une description, les personnes maximum pour une prestation,
 * le prestataire qui la gère, le type de la prestation, la ville, le code postal et l'adresse
 */
public class Prestation implements Serializable {

    // Récupération du logger
    private static Logger LOGGER =  Logger.getLogger(Calendrier_prestation.class.getPackageName());

    private String libelle;
    private String description;
    private int personne_max;
    private float prix;
    private float reduction;
    private Prestataire prestataire;
    private Type_presta type;
    private ArrayList<Calendrier_prestation> calendrier_prestations = new ArrayList<>();
    private final Ville ville;
    private final String adresse;
    private String codePostal;
    private String horaire;

    public Prestation(String libelle, String description, int personne_max, float prix, Prestataire prestataire, Type_presta type, Ville ville, String codePostal, String adresse) throws PrixException {
        setLibelle(libelle);
        this.description = description;
        this.personne_max = personne_max;
        setPrix(prix);
        this.prestataire = prestataire;
        this.type = type;
        this.ville = ville;
        this.codePostal=codePostal;
        this.adresse = adresse;
    }


    /**
     * Le prix ne doit pas être inférieur ou égal à 0
     * @param prix
     * @throws PrixException
     */
    private void setPrix(float prix) throws PrixException {
        if(prix <= 0){
            LOGGER.log(Level.WARNING, "Attention, le prix d'un prestation ne peut pas être inférieur ou égal à 0");
            throw new PrixException("Le prix ne peut pas être inférieur ou égal à 0");
        } else {
            this.prix = prix;
        }
    }

    public float getPrix() {
        return prix;
    }

    public int getPersonne_max() {
        return personne_max;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public String getDescription() {
        return description;
    }

    public String getLibelle() {
        return libelle;
    }

    private void setLibelle(String libelle) {
        this.libelle = Utilitaire.capitalize(libelle);
    }

    public Type_presta getType() {
        return type;
    }

    private void addCalendrierPresta(Calendrier_prestation calendrier_prestation){
        calendrier_prestations.add(calendrier_prestation);
    }

    public ArrayList<Calendrier_prestation> getCalendrier_prestations() {
        return calendrier_prestations;
    }

    /**
     * Concatène l'horaire à laquelle commence la prestation ainsi que l'heure
     * à laquelle se finit la prestation.
     * @param heureDebut
     * @param heureFin
     */
    private void calculHoraire(LocalTime heureDebut, LocalTime heureFin){
        horaire = heureDebut.toString() + " - " + heureFin.toString();
    }

    /**
     * ajoute dans une ArrayList toutes les prestations de type ANIMATION qui sont
     * des activités.
     * @param prestations
     * @return
     * @throws PrixException
     */
    public ArrayList<Prestation> addActivite(ArrayList<Prestation> prestations) throws PrixException {
        ArrayList<Prestation> activites = new ArrayList<>();
        for (Prestation prestation: prestations) {
            if(prestation.getType()==Type_presta.ANIMATION) {
                activites.add(prestation);
                LOGGER.log(Level.INFO, "Ajout de l'activité : " + prestation.getLibelle());
            }
        }
        return prestations;
    }


    /**
     * Remove la prestation p d'une ArrayList de prestations
     * @param prestations
     * @param p
     */
    public void removePrestation(ArrayList<Prestation> prestations, Prestation p) {
        if(prestations.size()!=0 && prestations.contains(p)) {
            prestations.remove(p);
            LOGGER.log(Level.INFO, "L'activité " + p.getLibelle() + "a été enlevé");
        }
    }
}
