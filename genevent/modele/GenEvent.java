package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.CodePostalException;
import fr.uga.iut2.genevent.Exception.PrixException;
import fr.uga.iut2.genevent.util.Utilitaire;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GenEvent implements Serializable {

    // Récupération du logger
    private static Logger LOGGER =  Logger.getLogger(Calendrier_prestation.class.getPackageName());

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private final Map<String, Utilisateur> utilisateurs;  // association qualifiée par l'email
    private final Map<String, Evenement> evenements;  // association qualifiée par le nom
    private Utilisateur utilisateur_connecter;

    private ArrayList<Calendrier_prestation> calendrier_activites = new ArrayList<>();
    private ArrayList<Calendrier_prestation> calendrier_prestations = new ArrayList<>();
    private Ville ville;

    private ArrayList<Prestation> activiteGrenoble = new ArrayList<>();
    private ArrayList<Prestation> activiteNice = new ArrayList<>();
    private ArrayList<Prestation> activiteBordeaux = new ArrayList<>();
    private ArrayList<Prestation> activiteParis = new ArrayList<>();

    private ArrayList<Hebergement> hebergementsNice = Utilitaire.lectureXML_hotel("hotels_nice.xml");
    private ArrayList<Hebergement> hebergementsParis = new ArrayList<>();
    private ArrayList<Hebergement> hebergementsGrenoble = new ArrayList<>();
    private ArrayList<Hebergement> hebergementsBordeaux = new ArrayList<>();

    ArrayList<Prestation> prestations = new ArrayList<>();


    public GenEvent() {
        this.utilisateurs = new HashMap<>();
        this.evenements = new HashMap<>();
    }

    public boolean ajouteUtilisateur(String email, String nom, String prenom) {
        if (this.utilisateurs.containsKey(email)||email==null||nom==null||prenom==null) {
            return false;
        } else {
            Utilisateur nouveau_utilisateur = new Utilisateur(email, nom, prenom);
            this.utilisateurs.put(email,nouveau_utilisateur);
            setUtilisateur_connecter(nouveau_utilisateur);
            return true;
        }
    }

    public Map<String, Evenement> getEvenements() {
        return this.evenements;
    }

    public void nouvelEvenement(String nom, LocalDate dateDebut, LocalDate dateFin ,String adminEmail, ArrayList<Calendrier_prestation> calendrier_activites, ArrayList<Calendrier_prestation> calendrier_prestations, Ville ville, int budget, int nbPers, float prix) {
        assert !this.evenements.containsKey(nom);
        assert this.utilisateurs.containsKey(adminEmail);
        Utilisateur admin = this.utilisateurs.get(adminEmail);
        Evenement evt = Evenement.initialiseEvenement(this, nom, dateDebut, dateFin, admin, calendrier_activites, calendrier_prestations, ville, budget, nbPers, prix);
        this.evenements.put(nom, evt);
    }

    public boolean estUtilisateur(String email){
        return utilisateurs.containsKey(email);
    }

    public void setUtilisateur_connecter(String email){
        this.utilisateur_connecter = utilisateurs.get(email);
    }

    public void setUtilisateur_connecter(Utilisateur utilisateur_connecter) {
        this.utilisateur_connecter = utilisateur_connecter;
    }

    public void setUtilisateur_deconnecter(){
        this.utilisateur_connecter=null;
    }

    public Utilisateur getUtilisateur_connecter() {
        return utilisateur_connecter;
    }

    public ArrayList<Calendrier_prestation> getCalendrier_activites() {
        return calendrier_activites;
    }

    public ArrayList<Calendrier_prestation> getCalendrier_prestations() {
        return calendrier_prestations;
    }

    public void suprimeUtilisateur(String email, String nom, String prenom) {
        this.utilisateurs.remove(email);
        System.out.println(utilisateurs.keySet());
    }

    public void creerEvenement(Evenement evenement, Ville ville, int budget, ObservableList<Prestation> mes_activites, ObservableList<Prestation> mes_prestations, int nombreDePersonnes){
        addActivites(mes_activites, evenement);
        addPrestations(mes_prestations, evenement);
        evenement.calculePrix();
        evenement.setBudget(budget);
        evenement.setNombre_personne(nombreDePersonnes);
        evenement.setVille(ville);
    }

    public void creerVille(EnumNomVille nomVille, String codePostal) throws CodePostalException {
        this.ville = new Ville(nomVille, codePostal);
    }

    public Ville getVille() {
        return ville;
    }

    public void addHebergement(){

    }

    public void addActivites(ObservableList<Prestation> mes_activites, Evenement evenement){
        for (Prestation activite : mes_activites){
            evenement.addCalendrierActivites(new Calendrier_prestation(evenement, activite));
            LOGGER.log(Level.INFO, "Ajout de l'activité : " + activite.getLibelle());
        }
    }

    public void addPrestations(ObservableList<Prestation> mes_prestations, Evenement evenement){
        for (Prestation prestation : mes_prestations){
            System.out.println(prestation.getLibelle());
            evenement.addCalendrierPrest(new Calendrier_prestation(evenement, prestation));
            LOGGER.log(Level.INFO, "Ajout de la prestation : " + prestation.getLibelle());
        }
    }

    public void addActiviteGrenoble(String libelle, String description, int personne_max, float prix, Prestataire prestataire, Type_presta type, Ville ville, String codePostal, String adresse) throws PrixException {
        activiteGrenoble.add(new Prestation(libelle, description, personne_max, prix, prestataire, type, ville, codePostal, adresse));
        LOGGER.log(Level.INFO, "Ajout d'une activité à Grenoble");
    }

    public void addActiviteBordeaux(String libelle, String description, int personne_max, float prix, Prestataire prestataire, Type_presta type, Ville ville, String codePostal, String adresse) throws PrixException{
        activiteBordeaux.add(new Prestation(libelle, description, personne_max, prix, prestataire, type, ville, codePostal, adresse));
        LOGGER.log(Level.INFO, "Ajout d'une activité à Bordeaux");
    }

    public void addActiviteParis(String libelle, String description, int personne_max, float prix, Prestataire prestataire, Type_presta type, Ville ville, String codePostal, String adresse) throws PrixException{
        activiteParis.add(new Prestation(libelle, description, personne_max, prix, prestataire, type, ville, codePostal, adresse));
        LOGGER.log(Level.INFO, "Ajout d'une activité à Paris");
    }

    public void addActiviteNice(String libelle, String description, int personne_max, float prix, Prestataire prestataire, Type_presta type, Ville ville, String codePostal, String adresse) throws PrixException{
        activiteNice.add(new Prestation(libelle, description, personne_max, prix, prestataire, type, ville, codePostal, adresse));
        LOGGER.log(Level.INFO, "Ajout d'une activité à Nice");
    }

    public void addAllHebergementNice(ArrayList<Hebergement> hebergements){
        hebergementsNice.addAll(hebergements);
        LOGGER.log(Level.INFO, "Ajout de tous les hébergements à Nice");
    }

    public void addHebergementGrenoble(String libelle, float prix, int capacite, String description, Type_Hebergement type, String adresse, String mail, String numero_telephone, Integer etoiles) throws PrixException{
        hebergementsGrenoble.add(new Hebergement(libelle, prix, capacite, description, type, adresse, mail, numero_telephone, etoiles));
        LOGGER.log(Level.INFO, "Ajout d'un hébergement à Grenoble");
    }

    public void addHebergementNice(String libelle, float prix, int capacite, String description, Type_Hebergement type, String adresse, String mail, String numero_telephone, Integer etoiles) throws PrixException{
        hebergementsNice.add(new Hebergement(libelle, prix, capacite, description, type, adresse, mail, numero_telephone, etoiles));
    }

    public void addHebergementBordeaux(String libelle, float prix, int capacite, String description, Type_Hebergement type, String adresse, String mail, String numero_telephone, Integer etoiles) throws PrixException{
        hebergementsBordeaux.add(new Hebergement(libelle, prix, capacite, description, type, adresse, mail, numero_telephone, etoiles));
        LOGGER.log(Level.INFO, "Ajout d'un hébergement à Bordeaux");
    }

    public void addHebergementParis(String libelle, float prix, int capacite, String description, Type_Hebergement type, String adresse, String mail, String numero_telephone, Integer etoiles) throws PrixException{
        hebergementsParis.add(new Hebergement(libelle, prix, capacite, description, type, adresse, mail, numero_telephone, etoiles));
        LOGGER.log(Level.INFO, "Ajout d'un hébergement à Paris");
    }

    public void addActivites(String libelle, String description, int personne_max, float prix, Prestataire prestataire, Type_presta type, Ville ville, String codePostal, String adresse) throws PrixException{
        prestations.add(new Prestation(libelle, description, personne_max, prix, prestataire, type, ville, codePostal, adresse));
    }

    public void clear(){
        hebergementsBordeaux.clear();
        hebergementsParis.clear();
        hebergementsGrenoble.clear();
        hebergementsNice.clear();
        activiteGrenoble.clear();
        activiteBordeaux.clear();
        activiteNice.clear();
        activiteParis.clear();
        prestations.clear();
        LOGGER.log(Level.INFO, "Toutes les array listes ont sont clear");
    }

    public void clearCalendrier(){
        calendrier_activites.clear();
        calendrier_prestations.clear();
        LOGGER.log(Level.INFO, "Toutes les listes de la classe Calendrier Prestation ont été clear");
    }

    public ArrayList<Hebergement> getHebergementsBordeaux() {
        return hebergementsBordeaux;
    }

    public ArrayList<Hebergement> getHebergementsGrenoble() {
        return hebergementsGrenoble;
    }

    public ArrayList<Hebergement> getHebergementsNice() {
        return hebergementsNice;
    }

    public ArrayList<Hebergement> getHebergementsParis() {
        return hebergementsParis;
    }

    public ArrayList<Prestation> getActiviteBordeaux() {
        return activiteBordeaux;
    }

    public ArrayList<Prestation> getActiviteGrenoble() {
        return activiteGrenoble;
    }

    public ArrayList<Prestation> getActiviteNice() {
        return activiteNice;
    }

    public ArrayList<Prestation> getActiviteParis() {
        return activiteParis;
    }

    public ArrayList<Prestation> getPrestations() {
        return prestations;
    }
}
