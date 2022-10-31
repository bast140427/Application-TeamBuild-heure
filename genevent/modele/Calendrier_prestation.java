package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.DateException;
import fr.uga.iut2.genevent.Exception.HeureException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calendrier prestation est une classe association construite avec un événement
 * ainsi qu'une activité
 *
 * Calendrier prestation permet de déterminer la date et l'heure de début
 * ainsi que la date et l'heure de fin pour une prestation et un
 * événement.
 */
public class Calendrier_prestation implements Serializable {

    // Récupération du logger
    private static Logger LOGGER =  Logger.getLogger(Calendrier_prestation.class.getPackageName());

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Evenement evenement;
    private Prestation prestation;
    private float reduction;

    private float prix;

    public Calendrier_prestation(Evenement evenement, Prestation prestation) {
        this.evenement = evenement;
        this.prestation = prestation;
    }

    public Calendrier_prestation(Prestation prestation) {
        this.prestation = prestation;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public Prestation getPrestation() {
        return prestation;
    }
    
    public float getReduction(){
        return reduction;
    }

    /**
     * Calcul le prix d'une prestation
     * @return le prix de la prestation * le nombre de personne dans l'événement
     */
    public float getPrix() {
        return prestation.getPrix()*evenement.getNombre_personne();
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime localTime) {
        this.heureDebut=heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) throws HeureException {
        if(heureFin.isBefore(heureDebut)) {
            LOGGER.log(Level.WARNING, "Attention l'heure de fin ne peut pas être avant l'heure de début");
            throw new HeureException("L'heure de fin ne peut pas être avant l'heure de début");
        }else{
            this.heureFin=heureFin;
        }
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) throws DateException {
        if(dateFin.isBefore(dateDebut)) {
            LOGGER.log(Level.WARNING, "Attention la date de fin ne peut pas être avant la date de début");
            throw new DateException("La date de fin ne peut pas être avant la date de début");
        }else{
            this.dateFin=dateFin;
        }
    }



    @Override
    public String toString() {
        return "Evénement: " + evenement + "\n\tDate de début: " + dateDebut +
                "\n\tDate de fin: " + dateFin + "\n\tPrestation: " + prestation;
    }
}
