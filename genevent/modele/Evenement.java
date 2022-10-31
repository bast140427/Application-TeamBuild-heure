package fr.uga.iut2.genevent.modele;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


public class Evenement implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private final GenEvent genevent;
    private final String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private final Map<String, Utilisateur> administrateurs;  // association qualifiée par l'email
    private Ville ville;
    private int budget;
    private float prix;
    private int nombre_personne;
    private Hebergement hebergement;
    private ArrayList<Calendrier_prestation> calendrier_activites = new ArrayList<>();
    private ArrayList<Calendrier_prestation> calendrier_prestations = new ArrayList<>();

    private List<Prestation> a= new ArrayList<>();
    private List<Prestation> aa= new ArrayList<>();
    //private ObservableList<Prestation> prestations = FXCollections.observableList(a) ;
    //private ObservableList<Prestation> activite = FXCollections.observableList(aa) ;


    // Invariant de classe : !dateDebut.isAfter(dateFin)
    //     On utilise la négation ici pour exprimer (dateDebut <= dateFin), ce
    //     qui est équivalent à !(dateDebut > dateFin).

    public static Evenement initialiseEvenement(GenEvent genevent, String nom, LocalDate dateDebut, LocalDate dateFin, Utilisateur admin, ArrayList<Calendrier_prestation> calendrier_activites, ArrayList<Calendrier_prestation> calendrier_prestations, Ville ville, int budget, int nbPers, float prix) {
        Evenement evt = new Evenement(genevent, nom, dateDebut, dateFin, calendrier_activites, calendrier_prestations, ville, budget, nbPers, prix);
        evt.ajouteAdministrateur(admin);
        return evt;
    }

    public Evenement(GenEvent genevent, String nom, LocalDate dateDebut, LocalDate dateFin, ArrayList<Calendrier_prestation> calendrier_activites, ArrayList<Calendrier_prestation> calendrier_prestations, Ville ville, int budget, int nbPers, float prix) {
        assert !dateDebut.isAfter(dateFin);
        this.genevent = genevent;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.administrateurs = new HashMap<>();
        this.calendrier_activites = calendrier_activites;
        this.calendrier_prestations = calendrier_prestations;
        this.ville = ville;
        this.budget = budget;
        this.nombre_personne = nbPers;
        this.prix = prix;
    }

    public Evenement(GenEvent genevent, String nom) {
        this.genevent = genevent;
        this.nom = nom;
        this.administrateurs = new HashMap<>();
    }

    public String getNom() {
        return this.nom;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        assert !dateDebut.isAfter(this.dateFin);
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        assert !this.dateDebut.isAfter(dateFin);
        this.dateFin = dateFin;
    }

    public void ajouteAdministrateur(Utilisateur admin) {
        assert !this.administrateurs.containsKey(admin.getEmail());
        this.administrateurs.put(admin.getEmail(), admin);
        admin.ajouteEvenementAdministre(this);
    }

    public void voirPlanning(){
        System.out.println();
    }

    public void getCalendrier(){
        for (Calendrier_prestation calendrier : calendrier_activites){
            System.out.println(calendrier);
        }
    }

    public void ajoutAdmin(Utilisateur admin){
        administrateurs.put(admin.getEmail(), admin);
    }

    public void addCalendrierActivites(Calendrier_prestation calendrier_activite){
        calendrier_activites.add(calendrier_activite);
    }

    public void addCalendrierPrest(Calendrier_prestation calendrier_prestation){
        calendrier_prestations.add(calendrier_prestation);
    }

    //public ObservableList<Prestation> getPrestations() {
        //return prestations;
    //}

    /*
    public void setPrestations(ObservableList<Prestation> prestations) {
        this.prestations.clear();
        this.prestations.addAll(prestations);}


    private void addPrestataire(Prestation securite){
        prestations.add(securite);
    }


    public boolean existPrestataire() {
        return prestations.size() > 0;
    }

     */

    public ArrayList<Calendrier_prestation> getCalendrier_activites() {
        return calendrier_activites;
    }

    public Ville getVille() {
        return ville;
    }

    public int getBudget() {
        return budget;
    }

    public float getPrix() {
        return prix;
    }

    public float getPrixParPersonne() {
        return prix/nombre_personne;
    }

    public int getNombre_personne() {
        return nombre_personne;
    }

    public Hebergement getHebergement() {
        return hebergement;
    }

    public void calculePrix(){
        float prixCalcule = 0;
        for(Calendrier_prestation calendrier_prestation : calendrier_activites){
            prixCalcule += calendrier_prestation.getPrix();
        }
        this.prix = prixCalcule;
    }
    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setNombre_personne(int nombre_personne) {
        this.nombre_personne = nombre_personne;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    /*

    public ObservableList<Prestation> getActivite() {
        return activite;
    }

    public void setActivite(ObservableList<Prestation> activite) {
        this.activite.clear();
        this.activite.addAll(activite);
    }

    public void addActivite(Prestation activite){
        this.activite.add(activite);
    }

    public void removeActivite(Prestation activite){
        this.activite.remove(activite);
    }

     */

    public Map<String, Utilisateur> getAdministrateurs() {
        return administrateurs;
    }

    public ArrayList<Calendrier_prestation> getCalendrier_prestations() {
        return calendrier_prestations;
    }
}