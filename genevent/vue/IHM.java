package fr.uga.iut2.genevent.vue;

import fr.uga.iut2.genevent.modele.Calendrier_prestation;
import fr.uga.iut2.genevent.modele.Ville;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;


public abstract class IHM {
    /**
     * Classe conteneur pour les informations saisies à propos d'un
     * {@link fr.uga.iut2.genevent.modele.Utilisateur}.
     *
     * <ul>
     * <li>Tous les attributs sont `public` par commodité d'accès.</li>
     * <li>Tous les attributs sont `final` pour ne pas être modifiables.</li>
     * </ul>
     */
    public static class InfosUtilisateur {
        public final String email;
        public final String nom;
        public final String prenom;

        public InfosUtilisateur(final String email, final String nom, final String prenom) {
            this.email = email;
            this.nom = nom;
            this.prenom = prenom;
        }
    }

    /**
     * Classe conteneur pour les informations saisies pour un nouvel
     * {@link fr.uga.iut2.genevent.modele.Evenement}.
     *
     * <ul>
     * <li>Tous les attributs sont `public` par commodité d'accès.</li>
     * <li>Tous les attributs sont `final` pour ne pas être modifiables.</li>
     * </ul>
     */
    public static class InfosNouvelEvenement {
        public final String nom;
        public final LocalDate dateDebut;
        public final LocalDate dateFin;
        public final InfosUtilisateur admin;
        public final ArrayList<Calendrier_prestation> calendrier_activites;
        public final ArrayList<Calendrier_prestation> calendrier_prestations;
        public final Ville ville;

        public final int budget;

        public final int nombre_personnes;

        public final float prix;

        public InfosNouvelEvenement(final String nom, final LocalDate dateDebut, final LocalDate dateFin, final InfosUtilisateur admin, final ArrayList<Calendrier_prestation> calendrier_activites, final ArrayList<Calendrier_prestation> calendrier_prestations, final Ville ville, final int budget, final int nombre_personnes, final float prix) {
            assert !dateDebut.isAfter(dateFin);
            this.nom = nom;
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
            this.admin = admin;
            this.calendrier_activites = calendrier_activites;
            this.calendrier_prestations = calendrier_prestations;
            this.ville = ville;
            this.budget = budget;
            this.nombre_personnes = nombre_personnes;
            this.prix = prix;
        }
    }

    public abstract void initialize(URL url, ResourceBundle resourceBundle);

    /**
     * Rend actif l'interface Humain-machine.
     *
     * L'appel est bloquant : le contrôle est rendu à l'appelant une fois que
     * l'IHM est fermée.
     *
     */
    public abstract void demarrerInteraction();

    /**
     * Affiche un message d'information à l'attention de l'utilisa·teur/trice.
     *
     * @param msg Le message à afficher.
     *
     * @param succes true si le message informe d'une opération réussie, false
     *     sinon.
     */
    public abstract void informerUtilisateur(final String msg, final boolean succes);

    /**
     * Récupère les informations à propos d'un
     * {@link fr.uga.iut2.genevent.modele.Utilisateur}.
     *
     */
    public abstract void saisirUtilisateur();

    /**
     * Récupère les informations nécessaires à la création d'un nouvel
     * {@link fr.uga.iut2.genevent.modele.Evenement}.
     *
     * @param nomsExistants L'ensemble des noms d'évenements qui ne sont plus
     *     disponibles.
     *
     */
    public abstract void saisirNouvelEvenement(final Set<String> nomsExistants);
}
