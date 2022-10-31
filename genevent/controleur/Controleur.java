package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.vue.IHM;
import fr.uga.iut2.genevent.vue.JavaFXGUI;


public class Controleur {

    private final GenEvent genevent;
    private final IHM ihm;

    public Controleur(GenEvent genevent) {
        this.genevent = genevent;

        // choisir la classe CLI ou JavaFXGUI
//        this.ihm = new CLI(this);
        this.ihm = new JavaFXGUI(this);
    }

    public GenEvent getGenevent() {
        return genevent;
    }

    public void demarrer() {
        this.ihm.demarrerInteraction();
    }

    public void saisirUtilisateur() {
        this.ihm.saisirUtilisateur();
    }

    public void creerUtilisateur(IHM.InfosUtilisateur infos) {
        boolean nouvelUtilisateur = this.genevent.ajouteUtilisateur(
                infos.email,
                infos.nom,
                infos.prenom
        );
        if (nouvelUtilisateur) {
            this.ihm.informerUtilisateur(
                    "Nouvel·le utilisa·teur/trice : " + infos.prenom + " " + infos.nom + " <" + infos.email + ">",
                    true
            );
        } else {
            this.ihm.informerUtilisateur(
                    "L'utilisa·teur/trice " + infos.email + " est déjà connu·e de GenEvent.",
                    false
            );
        }
    }

    public void saisirEvenement() {
        this.ihm.saisirNouvelEvenement(this.genevent.getEvenements().keySet());
    }

    public void creerEvenement(IHM.InfosNouvelEvenement infos) {
        // création d'un Utilisateur si nécessaire
        boolean nouvelUtilisateur = this.genevent.ajouteUtilisateur(
                infos.admin.email,
                infos.admin.nom,
                infos.admin.prenom
        );
        if (nouvelUtilisateur) {
            this.ihm.informerUtilisateur("Nouvel·le utilisa·teur/trice : " + infos.admin.prenom + " " + infos.admin.nom + " <" + infos.admin.email + ">",
                    true
            );
        }

        this.genevent.nouvelEvenement(
                infos.nom,
                infos.dateDebut,
                infos.dateFin,
                infos.admin.email,
                infos.calendrier_activites,
                infos.calendrier_prestations,
                infos.ville,
                infos.budget,
                infos.nombre_personnes,
                infos.prix
        );
        this.ihm.informerUtilisateur(
                "Nouvel évènement : " + infos.nom + ", administré par " + infos.admin.email,
                true
        );
    }

    public void connection(String email){
        boolean nouvelUtilisateur = genevent.estUtilisateur(email);
        if (nouvelUtilisateur){
            genevent.setUtilisateur_connecter(email);

            this.ihm.informerUtilisateur("Vous êtes connecté sous l'adresse mail de " + email, true);
        }
        else {
            this.ihm.informerUtilisateur("Cette adresse mail n'est pas reconnue : " + email, false);
        }
    }

    public boolean estConnecte() {
        return genevent.getUtilisateur_connecter()!=null;
    }
}
