package fr.uga.iut2.genevent.vue;

import fr.uga.iut2.genevent.Exception.CodePostalException;
import fr.uga.iut2.genevent.Exception.PrixException;
import fr.uga.iut2.genevent.controleur.Controleur;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.uga.iut2.genevent.modele.*;
import fr.uga.iut2.genevent.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.commons.validator.routines.EmailValidator;

import static fr.uga.iut2.genevent.modele.Type_presta.ANIMATION;
import static java.lang.Math.round;

import javafx.scene.control.ListCell;


/**
 * La classe JavaFXGUI est responsable des interactions avec
 * l'utilisa·teur/trice en mode graphique.
 * <p>
 * Attention, pour pouvoir faire le lien avec le
 * {@link fr.uga.iut2.genevent.controleur.Controleur}, JavaFXGUI n'est pas une
 * sous-classe de {@link javafx.application.Application} !
 * <p>
 * Le démarrage de l'application diffère des exemples classiques trouvés dans
 * la documentation de JavaFX : l'interface est démarrée à l'initiative du
 * {@link fr.uga.iut2.genevent.controleur.Controleur} via l'appel de la méthode
 * {@link #demarrerInteraction()}.
 */
public class JavaFXGUI extends IHM implements Initializable {

    // Récupération du logger
    private static Logger LOGGER =  Logger.getLogger(Calendrier_prestation.class.getPackageName());

    private final Controleur controleur;
    private final CountDownLatch eolBarrier;  // /!\ ne pas supprimer /!\ : suivi de la durée de vie de l'interface
    private static ArrayList<Evenement> event = new ArrayList<>(); // permet de voir les evenements programmés sur la page principale (page_accueil)
    private static ObservableList<Evenement> evenements;
    private List<Hebergement> li = new ArrayList<>();
    private List<Hebergement> mes_hebergementsL = new ArrayList<>();
    private static ObservableList<Hebergement> hebergements;
    private List<Prestation> listeImplementation3 = new ArrayList<>();
    private List<Prestation> listeImplementation2 = new ArrayList<>();
    private List<Prestation> listeImplementation1 = new ArrayList<>();
    private List<Prestation> listeImplementation4 = new ArrayList<>();
    private static ObservableList<Prestation> activités;
    private static ObservableList<Prestation> mes_activités;
    private static ObservableList<Prestation> prestation;
    private static ObservableList<Prestation> mes_prestation;
    private List<Prestation> listeImplementation5 = new ArrayList<>();
    private  static ObservableList<Prestation> event_activite;
    private List<Prestation> listeImplementation6 = new ArrayList<>();
    private  static ObservableList<Prestation> event_prestation;
    private  static ObservableList<Reunion> reunions;
    private Hebergement hebergement_choisi =null;
    private List<Reunion> aaaaaa = new ArrayList<>();
    private int evenement_cree = 0; //1->team building  2->seminaire



    // éléments vue nouvel·le utilisa·teur/trice
    @FXML
    private TextField newUserForenameTextField;
    @FXML
    private TextField newUserSurnameTextField;
    @FXML
    private TextField newUserEmailTextField;
    @FXML
    private Button newUserOkButton;
    @FXML
    private Button newUserCancelButton;

    public JavaFXGUI(Controleur controleur) {
        this.controleur = controleur;

        this.eolBarrier = new CountDownLatch(1);  // /!\ ne pas supprimer /!\
    }

    /**
     * Point d'entrée principal pour le code de l'interface JavaFX.
     *
     * @param primaryStage stage principale de l'interface JavaFX, sur laquelle
     *                     définir des scenes.
     * @throws IOException si le chargement de la vue FXML échoue.
     * @see javafx.application.Application#start(Stage)
     */
    private void start(Stage primaryStage) throws IOException {
        ////////////////Initialisation/////////////////////////
        controleur.getGenevent().setUtilisateur_deconnecter();
        evenements = FXCollections.observableList(event);
        hebergements = FXCollections.observableList(li);
        activités = FXCollections.observableList(listeImplementation3);
        mes_activités = FXCollections.observableList(listeImplementation2);
        prestation = FXCollections.observableList(listeImplementation1);
        mes_prestation = FXCollections.observableList(listeImplementation4);
        event_activite = FXCollections.observableList(listeImplementation5);
        event_prestation = FXCollections.observableList(listeImplementation6);
        reunions = FXCollections.observableList(aaaaaa);
        try {
            controleur.getGenevent().clear();
            controleur.getGenevent().addActivites("Ekanim Securite", "EKANIM garantit votre sécurité et celle de vos biens", 10000, 1000, null, Type_presta.SECURITE, null, "42120", "594 rue de la Chantalouette");
            controleur.getGenevent().addActivites("API Restauration", "NOUS ÉPLUCHONS, NOUS ÉMINÇONS, NOUS TRANCHONS", 10000, 1300, null, Type_presta.RESTAURATION, null, "59370", "384 rue du Général de Gaulle");
            controleur.getGenevent().addActivites("Groupito Transport", "Location bus, autocar, minibus, voiture avec chauffeur", 10000, 500, null, Type_presta.TRANSPORT, null, "43270", "75 rue Victor Hugo");
            controleur.getGenevent().addActivites("Service Logistique .Inc", "Mise à disposition de chaise, table, micro pour vos événements", 10000, 200, null, Type_presta.SERVICE_LOGISTIQUE, null, "06650", "28 rue la papette");
            addPrestation(controleur.getGenevent().getPrestations());
            controleur.getGenevent().addActiviteGrenoble("Karting Echirolle", "Karting à échirolles proche de Grenoble.", 30, 25, null, ANIMATION, null, "38130", "5 rue Léon Fournier");
            controleur.getGenevent().addActiviteGrenoble("Tir à l'arc", "La passion du tir à l'arc dans le dauphiné. Situé à Gières", 50, 15, null, ANIMATION, null, "38610", "65 rue de la papeterie");
            controleur.getGenevent().addActiviteNice("To Be Locked - Escape Game", "Comprend 3 salles à deux pas de la place massena", 15, 20, null, Type_presta.ANIMATION, null, "06000", "12 rue de la liberté");
            controleur.getGenevent().addActiviteNice("Les Geckos Canyoning", "Canyoning dans le rivière du loup", 120, 55, null, Type_presta.ANIMATION, null, "06620", "1 passage des portiques");
            controleur.getGenevent().addActiviteBordeaux("Excursion gastronomique", "Excursion gastronomique et œnologique à Saint-Émilion en petit groupe avec dégustations", 36, 95, null, Type_presta.ANIMATION, null, "33000", "1 rue du bien");
            controleur.getGenevent().addActiviteBordeaux("Ballade à cheval", "Médoc sauvage à cheval Une découverte, d'une heure, de la faune et flore sauvage", 20, 35, null, Type_presta.ANIMATION, null, "35590", "12 route du port");
            controleur.getGenevent().addActiviteParis("Rallye en Bateau électrique", "Team Building Rallye en Bateau Electrique à Paris 19ème", 300, 85, null, Type_presta.ANIMATION, null, "75019", "'5 quai de seine");
            controleur.getGenevent().addActiviteParis("Olympiades Parisienne", "Team Building Sportif à Paris", 250, 45, null, Type_presta.ANIMATION, null, "75116", "Pavillon Royal");
            LOGGER.log(Level.INFO, "Initialisation de toutes les activités");
            controleur.getGenevent().addHebergementParis("Mercure Hotel porte versaille", 194, 260, " Parking Connexion ,Wi-Fi gratuite ,Chambres familiales" +
                " ,Chambres non-fumeurs ,Animaux domestiques admis ,Réception ouverte 24h/24, Plateau/bouilloire dans tous les hébergements Bar",
                Type_Hebergement.HOTEL, "69 Boulevard Victor Hugo", "mercure.hotel@gmail.com","0498657543", 4);
            controleur.getGenevent().addHebergementParis("Camping paris Maison Lafitte", 90, 300, "À 25 min des Champs-Élysées, notre camping vous accueille dans un écrin de verdure en bord de Seine",
                Type_Hebergement.CAMPING, "1 rue Johnson", "maison.lafitte@gmail.com", "0139122191", 4);

            controleur.getGenevent().addHebergementGrenoble("Gite de la Rochaline", 80, 6, "Gite au Coeur du Parc naturel " +
                "Régional de Chartreuse. 5-6 personnes. Terrasse + 800 m2 de terrain. Maison située au hameau de St Hugues de Chartreuse. " +
                "Vue panoramique.", Type_Hebergement.GITE, "21 Chemin de Marchandière", "gite.rochaline@gmail.com",
                "0673739877", 0);
            controleur.getGenevent().addHebergementGrenoble("Camping Bois Sigu", 65, 279, "Camping Bois Sigu 3* à " +
                "Lans-en-Vercors. Installé au cœur du Parc Naturel Régional du Vercors, à 800 mètres du village", Type_Hebergement.CAMPING,
                "315 vieille route", "bois.sigu@gmail.com", "0493456733", 3);
            controleur.getGenevent().addHebergementBordeaux("Radisson Blu Hotel Bordeaux", 122, 240, " Parking Spa et " +
                "centre de bien-être, Animaux domestiques admis, Connexion Wi-Fi gratuite, Chambres familiales, Restaurant, Bar", Type_Hebergement.HOTEL,
                "63 rue Lucien Faure", "radisson.bluhotel@gmail.com", "0576564666", 4);
            controleur.getGenevent().addHebergementBordeaux("Le Clos des Queyries", 78, 30, "Parking gratuit, Connexion " +
                "Wi-Fi gratuite, Restaurant, Chambres non-fumeurs, Service d'étage, Terrasse Plateau/bouilloire dans tous les hébergements, Bar",
                Type_Hebergement.HOTEL, "38 rue Andre Degain", "clos.queyries@gmail.com", "0588586757", 3);
            LOGGER.log(Level.INFO, "Initialisation de tous les hébergements");
        } catch (PrixException e) {
            System.out.println(e.getMessage());
        }

        controleur.getGenevent().addAllHebergementNice(Utilitaire.lectureXML_hotel("hotels_nice.xml"));

        ////////////////////////////////////////////////////PAGE D4ACCUEIL//////////////////////////////////////////////
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("page_accueil.fxml"));
        mainViewLoader.setController(this);
        Scene mainScene = new Scene(mainViewLoader.load());
        primaryStage.setTitle("GenEvent");
        String css = this.getClass().getResource("dashboard.css").toExternalForm();
        mainScene.getStylesheets().add(css);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

//-----  Éléments du dialogue  -------------------------------------------------

    private void exitAction() {
        // fermeture de l'interface JavaFX : on notifie sa fin de vie
        this.eolBarrier.countDown();
    }

    public int getEvenement_cree() {
        return evenement_cree;
    }

    // menu principal  -----

    @FXML
    private void newUserMenuItemAction(ActionEvent evenement) {
        this.controleur.saisirUtilisateur();
    }

    @FXML
    private void exitMenuItemAction() {
        Platform.exit();
        this.exitAction();
    }

//-----------------------------------------------
//-----Méthode ajouter/supprimer dans listView-----
//-----------------------------------------------

    @FXML
    private ListView<Evenement> listView_evenement; // page_accueil

    public void addEvenement(Evenement evenement) {
        this.evenements.add(evenement);
    }

    public void removeEvenement(Evenement evenement) {
        evenements.remove(evenement);
        this.controleur.getGenevent().getEvenements().remove(evenement.getNom());
    }

    @FXML
    private ListView<Hebergement> listView_hebergement; // page_hebergement

    public void addHebergement(Hebergement hebergement) {
        this.hebergements.add(hebergement);
    }

    public void addHebergement(ArrayList<Hebergement> hebergement) {
        hebergements.addAll(hebergement);
    }

    public void setHebergement(ArrayList<Hebergement> hebergement){
        hebergements.clear();
        addHebergement(hebergement);
    }

    @FXML
    private ListView<Prestation> listView_activite; // page_activité

    public static void addActivite(Prestation prestataire) {
        activités.add(prestataire);
    }

    public void addActivite(ArrayList<Prestation> prestations) {
        activités.addAll(prestations);
    }

    public static void removeActivite(Prestation prestation) {
        activités.remove(prestation);
    }

    @FXML
    private ListView<Prestation> listView_mes_activite; // page_mes_activité

    public static void addMes_Activite(Prestation prestataire) {
        mes_activités.add(prestataire);
    }

    public void addMes_Activite(ArrayList<Prestation> prestations) {
        mes_activités.addAll(prestations);
    }

    public static void removemes_Activite(Prestation prestation) {
        mes_activités.remove(prestation);
    }

     public void setActivite(ArrayList<Prestation> prestations){
        activités.clear();
        addActivite(prestations);
     }

     public void  clearMes_activite(){
        mes_activités.clear();
     }

    @FXML
    private ListView<Prestation> listView_prestation; // page_prestation

    public static void addPrestation(Prestation prestataire) {
        prestation.add(prestataire);
    }

    public void addPrestation(ArrayList<Prestation> prestations) {
        prestation.addAll(prestations);
    }

    public static void removepestation(Prestation prestations) {
        prestation.remove(prestations);
    }

    @FXML
    private ListView<Prestation> listView_mes_prestation; // page_prestation

    public static void addMes_Prestation(Prestation prestataire) {
        mes_prestation.add(prestataire);
    }

    public void addMes_Prestation(ArrayList<Prestation> prestations) {
        mes_prestation.addAll(prestations);
    }

    public static void removemes_pestation(Prestation prestations) {
        mes_prestation.remove(prestations);
    }

    @FXML
    private ListView<Prestation> listView_event_prestation;

    @FXML
    private ListView<Prestation> listView_event_activite;

    public void addevent_activite(Prestation activite){
        event_activite.add(activite);
    }

    public void setevent_activite(ArrayList<Prestation> activite){
        event_activite.setAll(activite);
    }

    @FXML
    private ListView<Prestation> ListView_event_prestation;

    public void addevent_prestation(Prestation activite){
        event_prestation.add(activite);
    }

    public void setevent_prestation(ArrayList<Prestation> activite){
        event_prestation.setAll(activite);
    }

    @FXML
    private TextField tf_budget;

    @FXML
    private TextField tf_nbPers;

    @FXML
    private ListView<Reunion> listView_salle;

    /**
     * Initialise les list view
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//            listView_evenement.getItems().setAll();
//            listView_evenement.getItems().addAll(evenements);
//            listView_evenement.setCellFactory(new Callback<ListView<Evenement>, ListCell<Evenement>>() {
//                @Override
//                public ListCell<Evenement> call(ListView<Evenement> evenementListView) {
//                    ItemEvenement item = new ItemEvenement();
//                    item.setJavaFXGUI(JavaFXGUI.this);
//                    return item;}});
//        listView_evenement.getItems().setAll();
//        listView_evenement.getItems().addAll(hebergements.get(0));
//        listView_evenement.setCellFactory(new Callback<ListView<Hebergement>, ListCell<Hebergement>>() {
//            @Override
//            public ListCell<Hebergement> call(ListView<Hebergement> hebergementListView) {
//                ItemHebergement item_hebergement = new ItemHebergement();
//                //item.setJavaFXGUI(JavaFXGUI.this);
//                return item_hebergement;}});
        if (etape_team == 0) {
            listView_evenement.setItems(evenements);
            listView_evenement.setItems(evenements);
            listView_evenement.setCellFactory(new Callback<ListView<Evenement>, ListCell<Evenement>>() {
                @Override
                public ListCell<Evenement> call(ListView<Evenement> evenementListView) {
                    ItemEvenement item = new ItemEvenement();
                    item.setJavaFXGUI(JavaFXGUI.this);
                    LOGGER.log(Level.INFO, "Initialisation de la listeView d'événement");
                    return item;
                }
            });
        } else if (etape_team == 3) {
            listView_hebergement.setItems(hebergements);
            listView_hebergement.setCellFactory(new Callback<ListView<Hebergement>, ListCell<Hebergement>>() {
                @Override
                public ListCell<Hebergement> call(ListView<Hebergement> hebergementListView) {
                    ItemHebergement item_hebergement = new ItemHebergement();
                    item_hebergement.setJavaFXGUI(JavaFXGUI.this);
                    LOGGER.log(Level.INFO, "Initialisation de la listeView d'hébergement");
                    return item_hebergement;
                }
            });
        } else if (etape_team == 4 && evenement_cree==1) {
            listView_activite.setItems(activités);
            listView_activite.setCellFactory(new Callback<ListView<Prestation>, ListCell<Prestation>>() {
                @Override
                public ListCell<Prestation> call(ListView<Prestation> activitéListView) {
                    ItemActivite item_activite = new ItemActivite();
                    //item.setJavaFXGUI(JavaFXGUI.this);
                    LOGGER.log(Level.INFO, "Initialisation de la listView d'activité");
                    return item_activite;
                }
            });
        }else if (etape_team == 4 && evenement_cree==2) {
            listView_salle.setItems(reunions);
            listView_salle.setCellFactory(new Callback<ListView<Reunion>, ListCell<Reunion>>() {
                @Override
                public ListCell<Reunion> call(ListView<Reunion> salleListView) {
                    ItemReunion item_reunion = new ItemReunion();
                    item_reunion.setJavaFXGUI(JavaFXGUI.this);
                    LOGGER.log(Level.INFO, "Initialisation de la listView de salle");
                    return item_reunion;
                }
            });
        } else if (etape_team == 5) {
            listView_mes_activite.setItems(mes_activités);

            listView_mes_activite.setCellFactory(new Callback<ListView<Prestation>, ListCell<Prestation>>() {
                @Override
                public ListCell<Prestation> call(ListView<Prestation> activitéListView) {
                    ItemMes_Activite item_mes_activité = new ItemMes_Activite();
                    //item.setJavaFXGUI(JavaFXGUI.this);
                    LOGGER.log(Level.INFO, "Initialisation de la listView de mes activités");
                    return item_mes_activité;
                }
            });
        } else if (etape_team == 6) {
            listView_prestation.setItems(prestation);
            listView_prestation.setCellFactory(new Callback<ListView<Prestation>, ListCell<Prestation>>() {
                @Override
                public ListCell<Prestation> call(ListView<Prestation> activitéListView) {
                    ItemPrestation item_prestataire = new ItemPrestation();
                    //item.setJavaFXGUI(JavaFXGUI.this);
                    LOGGER.log(Level.INFO, "Initialisation de la listView : liste des prestataires");
                    return item_prestataire;
                }
            });
            listView_mes_prestation.setItems(mes_prestation);
            listView_mes_prestation.setCellFactory(new Callback<ListView<Prestation>, ListCell<Prestation>>() {
                @Override
                public ListCell<Prestation> call(ListView<Prestation> activitéListView) {
                    ItemMesPrestation item_mes_prestataire = new ItemMesPrestation();
                    //item.setJavaFXGUI(JavaFXGUI.this);
                    LOGGER.log(Level.INFO, "Initialisation de la listView de nos prestataires");
                    return item_mes_prestataire;
                }
            });

        }else if (etape_team == 7) {
//            for (Prestation prestation : mes_prestation) {
//            System.out.println(prestation.getLibelle());
//        }
            System.out.println(evenement_consulter);
            event_activite.clear();
            for (int i = 0; i < evenement_consulter.getCalendrier_activites().size(); i++) {
                addevent_activite(evenement_consulter.getCalendrier_activites().get(i).getPrestation());
            }
            listView_event_activite.setItems(event_activite);
            listView_event_activite.setCellFactory(new Callback<ListView<Prestation>, ListCell<Prestation>>() {
                @Override
                public ListCell<Prestation> call(ListView<Prestation> evenementListView) {
                    ItemEvenement_activite item_act = new ItemEvenement_activite();
                    item_act.setJavaFXGUI(JavaFXGUI.this);
                    LOGGER.log(Level.INFO, "Initialisation de la listView des activités pour un événement consulté");
                    return item_act;
                }
            });
        } else if (etape_team == 8) {
            System.out.println(evenement_consulter);
            event_prestation.clear();
            System.out.println(evenement_consulter.getCalendrier_prestations().size());
            for (int i = 0; i < evenement_consulter.getCalendrier_prestations().size(); i++) {
//                System.out.println(evenement_consulter.getCalendrier_prestations().get(i).getPrestation().getLibelle());
                addevent_prestation(evenement_consulter.getCalendrier_prestations().get(i).getPrestation());
            }
            listView_event_prestation.setItems(event_prestation);
            listView_event_prestation.setCellFactory(new Callback<ListView<Prestation>, ListCell<Prestation>>() {
                @Override
                public ListCell<Prestation> call(ListView<Prestation> evenement_ListView) {
                    ItemEvenement_prestataire item_event_presta = new ItemEvenement_prestataire();
                    item_event_presta.setJavaFXGUI(JavaFXGUI.this);
                    LOGGER.log(Level.INFO, "Initialisation de la listView des prestataires pour un événement consulté");
                    return item_event_presta;
                }
            });
        }
    }

    public void setEtape(int etape){
        etape_team=etape;
    }

    @FXML
    private Button button_seconnecter;


    @FXML
    private Button label_connexion;

    @FXML
    private Button btn_connexion;

    @FXML
    private TextField connectionUserEmailTextField;

    //@FXML
    //private TextField connectionUserEmailTextField;

    @FXML
    private Button button_creerTB;

    @FXML
    private Button button_creerS;

    @FXML
    private void connection() throws IOException {
        if (connectionUserEmailTextField.getText().isEmpty()==false) {
            this.controleur.connection(connectionUserEmailTextField.getText());
            button_seconnecter.setText(this.controleur.getGenevent().getUtilisateur_connecter().getNom() + " " + this.controleur.getGenevent().getUtilisateur_connecter().getPrenom());
            button_seconnecter.setDisable(true);
            button_creerTB.setDisable(false);
            button_creerS.setDisable(false);
            this.connectionUserOkButton.getScene().getWindow().hide();
            chercherEvenement();
        }
    }

    @FXML
    private void se_deconnecter(ActionEvent event) throws IOException {
        try {
            if(etape_team>0) {
                FXMLLoader erreurPopUp = new FXMLLoader(getClass().getResource("popErreur/popUp_erreur_se_deconnecter.fxml"));
                erreurPopUp.setController(this);
                Scene newUserScene = new Scene(erreurPopUp.load());
                Stage newUserWindow = new Stage();
                newUserWindow.setTitle("Attention");
                newUserWindow.initModality(Modality.APPLICATION_MODAL);
                newUserWindow.setScene(newUserScene);
                newUserWindow.showAndWait();
                newUserWindow.setResizable(false);
                viderEvenements();
            }else {
                this.controleur.getGenevent().setUtilisateur_deconnecter();
                button_seconnecter.setText("Se connecter");
                button_seconnecter.setDisable(false);
                button_creerTB.setDisable(true);
                button_creerS.setDisable(true);
                viderEvenements();
            }
        }catch (IOException exc){
            throw new RuntimeException(exc);
        }
    }

    @FXML
    private void Continuer_Se_Deco(ActionEvent event) throws IOException {
        if(etape_team>0) {
            etape_team = 0;
            FXMLLoader accueil = new FXMLLoader(getClass().getResource("page_accueil.fxml"));
            accueil.setController(this);
            Stage fenetre = (Stage) btn_retour.getScene().getWindow();
            Scene scene = new Scene(accueil.load());
            fenetre.setScene(scene);
            fenetre.show();
            this.btn_continuer.getScene().getWindow().hide();
        }
            this.controleur.getGenevent().setUtilisateur_deconnecter();
            button_seconnecter.setText("Se connecter");
            button_seconnecter.setDisable(false);
            button_creerTB.setDisable(true);
            button_creerS.setDisable(true);
            viderEvenements();
        LOGGER.log(Level.INFO, "Utilisateur déconnecté");
    }





    @FXML
    private void se_deco(ActionEvent event) throws IOException {
        onBoutonContinuerLogo(event);
        this.controleur.getGenevent().setUtilisateur_deconnecter();
        button_seconnecter.setText("Se connecter");
        button_seconnecter.setDisable(false);
        button_creerTB.setDisable(true);
        button_creerS.setDisable(true);
        viderEvenements();
    }

    //connection.fxml
    @FXML
    private TextField email1;

    @FXML
    private void connection1() {
        this.controleur.connection(email1.getText());
    }

    protected void utilisateurConnecte() {
        if (controleur.estConnecte()) {
            button_seconnecter.setText(this.controleur.getGenevent().getUtilisateur_connecter().getNom() + " " + this.controleur.getGenevent().getUtilisateur_connecter().getPrenom());
            button_seconnecter.setDisable(true);
            button_creerTB.setDisable(false);
            button_creerS.setDisable(false);
        }
    }


    // vue nouvel·le utilisa·teur/trice  -----
    @FXML private CheckBox cge;

    @FXML
    private void createNewUserAction(ActionEvent evenement) {
        if (newUserEmailTextField.getText().isEmpty()==false && cge.isSelected()) {
            try {
                IHM.InfosUtilisateur data = new IHM.InfosUtilisateur(
                        this.newUserEmailTextField.getText().strip().toLowerCase(),
                        this.newUserSurnameTextField.getText().strip(),
                        this.newUserForenameTextField.getText().strip()
                );
                LOGGER.log(Level.INFO, "Création de l'utilisateur : " + data.email);
                this.controleur.creerUtilisateur(data);
                this.newUserOkButton.getScene().getWindow().hide();
                button_seconnecter.setText(this.controleur.getGenevent().getUtilisateur_connecter().getNom() + " " + this.controleur.getGenevent().getUtilisateur_connecter().getPrenom());
                button_seconnecter.setDisable(true);
                button_creerTB.setDisable(false);
                button_creerS.setDisable(false);
            }catch (NullPointerException e){

                System.out.println(e.getMessage());
            }
//        }else if (cge.isSelected()==false && newUserEmailTextField.getText().isEmpty()==false){
//            newUserEmailTextField.text
//        }
        }
    }


    @FXML
    private void cancelNewUserAction() {
        this.newUserCancelButton.getScene().getWindow().hide();
    }

    @FXML
    private Button connectionUserOkButton;

    @FXML
    private void validateTextFields1() {
        boolean isValid = true;
        isValid &= validateEmailTextField(this.connectionUserEmailTextField);
        this.connectionUserOkButton.setDisable(!isValid);
    }

    @FXML
    private void validateTextFields() {
        boolean isValid = true;

        isValid &= validateNonEmptyTextField(this.newUserForenameTextField);
        isValid &= validateNonEmptyTextField(this.newUserSurnameTextField);
        isValid &= validateEmailTextField(this.newUserEmailTextField);


        this.newUserOkButton.setDisable(!isValid);
    }

    private static void markTextFieldErrorStatus(TextField textField, boolean isValid) {
        if (isValid) {
            textField.setStyle(null);
        } else {
            textField.setStyle("-fx-control-inner-background: f8d7da");
        }
    }

    private static boolean validateNonEmptyTextField(TextField textField) {
        boolean isValid = textField.getText().strip().length() > 0 && textField.getText()!=null;

        markTextFieldErrorStatus(textField, isValid);

        return isValid;
    }

    private static boolean validateEmailTextField(TextField textField) {
        EmailValidator validator = EmailValidator.getInstance(false, false);
        boolean isValid = validator.isValid(textField.getText().strip().toLowerCase());

        markTextFieldErrorStatus(textField, isValid);

        return isValid;
    }



    //-------- Créer Team Building --------------------------------------------------
    //Passage page Etape 1
    @FXML
    private Button btn_nomInfo;

    @FXML
    private Button button_tb;

    @FXML
    private Button button_semi;

    private boolean creation_team = false;
    private boolean creation_semi = false;

    @FXML
    private void creer_TB(ActionEvent event) throws IOException {
        if (etape_team == 0 && controleur.estConnecte()) {
            etape_team = 1;
            evenement_cree = 1;
            FXMLLoader informations = new FXMLLoader(getClass().getResource("page_informations_evenement.fxml"));
            informations.setController(this);
            Stage fenetre = (Stage) button_creerTB.getScene().getWindow();
            Scene scene = new Scene(informations.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
            try {
                btn_nomInfo.setDisable(true);
                btn_nomInfo.setText(this.controleur.getGenevent().getUtilisateur_connecter().getNom() + " " + this.controleur.getGenevent().getUtilisateur_connecter().getPrenom());
                dpk_dateFin.setDisable(true);
            }catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            Image icon2 = new Image(getClass().getResourceAsStream("image/info-circle-regular-2.png"));
            ttbudget.setGraphic(new ImageView(icon2));
            tf_budget.setTooltip(ttbudget);

            ttpers.setGraphic(new ImageView(icon2));
            tf_nbPers.setTooltip(ttpers);

            ttpers.setShowDelay(Duration.seconds(1));
            ttbudget.setShowDelay(Duration.seconds(1));
        } else if (controleur.estConnecte()) {
            creation_team=true;
            onButtonRetourLogo(event);
        }
    }

    @FXML
    private void creer_S(ActionEvent event) throws IOException {
        if (controleur.estConnecte()) {
            if (etape_team==0) {
                etape_team = 1;
                evenement_cree = 2;
                FXMLLoader informations = new FXMLLoader(getClass().getResource("page_informations_evenement.fxml"));
                informations.setController(this);
                Stage fenetre = (Stage) button_creerTB.getScene().getWindow();
                Scene scene = new Scene(informations.load());
                fenetre.setScene(scene);
                fenetre.show();
                utilisateurConnecte();
                // initialisation des tooltips
                try{
                    btn_nomInfo.setDisable(true);
                    btn_nomInfo.setText(this.controleur.getGenevent().getUtilisateur_connecter().getNom() + " " + this.controleur.getGenevent().getUtilisateur_connecter().getPrenom());
                    dpk_dateFin.setDisable(true);
                }catch (NullPointerException e){
                    System.out.println(e.getMessage());
                }
                Image icon2 = new Image(getClass().getResourceAsStream("image/info-circle-regular-2.png"));
                ttbudget.setGraphic(new ImageView(icon2));
                tf_budget.setTooltip(ttbudget);

                ttpers.setGraphic(new ImageView(icon2));
                tf_nbPers.setTooltip(ttpers);
            }else {
                creation_semi = true;
                onButtonRetourLogo(event);
            }
        }


    }

    //Page Etape 1
    //déclaration de tous les éléments du FXML
    @FXML
    private TextField tf_nomEvent;

    @FXML
    private TextField tf_budgetParPersonne;

    @FXML
    private Button btn_suivant;
    @FXML
    private Button btn_retour;
    @FXML
    private Label label_nomEvent;
    @FXML
    private Label label_budget;
    @FXML
    private Label label_budgetParPersonne;
    @FXML
    private Label label_nbPers;
    @FXML
    private DatePicker dpk_dateDebut;
    @FXML
    private DatePicker dpk_dateFin;
    @FXML
    private Button button_suivant1;
    @FXML
    private Label label_periode;

    //a supprimer
    private int nbPers;
    private int budget;
    private String nomEvent;
    LocalDate dateDebut;
    LocalDate dateFin;

    Evenement evenement;

    /**
     * @param event
     * @throws IOException
     */
    @FXML
    private void etape_suivante(ActionEvent event) throws IOException {
        if (tf_nomEvent == null || tf_nomEvent.getText().isEmpty()) {
            label_nomEvent.setTextFill(Color.RED);
        } else {
            label_nomEvent.setTextFill(Color.BLACK);
        }
        if (tf_budget == null || tf_budget.getText().isEmpty()) {
            label_budget.setTextFill(Color.RED);
        } else {
            label_budget.setTextFill(Color.BLACK);
        }
        if (tf_nbPers == null || tf_nbPers.getText().isEmpty()) {
            label_nbPers.setTextFill(Color.RED);
        } else {
            label_nbPers.setTextFill(Color.BLACK);
        }
        if (dpk_dateDebut.getValue() == null || dpk_dateFin.getValue() == null) {
            label_periode.setTextFill(Color.RED);
        } else {
            label_periode.setTextFill(Color.BLACK);
        }

        if (!tf_budgetParPersonne.getText().isEmpty() && !tf_nomEvent.getText().isEmpty() && dpk_dateDebut.getValue() != null && dpk_dateFin.getValue() != null) {
            etape_team = 2;
            FXMLLoader rename = new FXMLLoader(getClass().getResource("page_ville2.fxml"));
            rename.setController(this);
            Stage fenetre = (Stage) button_suivant1.getScene().getWindow();
            Scene scene = new Scene(rename.load());
            fenetre.setScene(scene);
            fenetre.show();
            etape_team = 2;
            utilisateurConnecte();
            LOGGER.log(Level.INFO, "Aller page choix ville");
        }
    }

    @FXML
    private Tooltip ttbudget;

    @FXML
    private Tooltip ttpers;




//    @FXML
//    private void NumberOnly(KeyEvent event){
//        char c;
//        int i=0;
//        while(event.getText().length()<i) {
//            c = event.getCharacter().charAt(i);
//            if (Character.isLetter(c)) {
//                tf_budget.setEditable(false);
//            } else {
//                tf_budget.setEditable(true);
//            }
//            i++;
//        }
//    }


    /**
     * @param event
     */
    @FXML
    private void entrerNom(KeyEvent event) {
        if (tf_nomEvent.getText().length() > 30) {
            tf_nomEvent.setText(tf_nbPers.getText(0, 30));
        }
        nomEvent = tf_nomEvent.getText();
        LOGGER.log(Level.INFO, "Nom de l'événement : " + nomEvent);
    }

    @FXML
    private void budgetParPers(KeyEvent event) {
        tf_budget.setText(tf_budget.getText().replaceAll("[^0-9]|^0", ""));
        tf_budget.positionCaret(tf_budget.getText().length());
        tf_nbPers.setText(tf_nbPers.getText().replaceAll("[^0-9]|^0", ""));
        tf_nbPers.positionCaret(tf_nbPers.getText().length());
        if (tf_nbPers.getText().length() > 0) {
            if (tf_nbPers.getText().length() > 5) {
                tf_nbPers.setText(tf_nbPers.getText(0, 5));
                tf_nbPers.positionCaret(5);
                nbPers = Integer.parseInt(tf_nbPers.getText());
            } else {
                nbPers = Integer.parseInt(tf_nbPers.getText());
            }
        } else {
            nbPers = 0;
        }
        if (tf_budget.getText().length() > 0) {
            if (tf_budget.getText().length() > 7) {
                tf_budget.setText(tf_budget.getText(0, 7));
                tf_budget.positionCaret(7);
                budget = Integer.parseInt(tf_budget.getText());
            } else {
                budget = Integer.parseInt(tf_budget.getText());
            }
        } else {
            budget = 0;
        }
        if (budget > 0 && nbPers > 0) {
            tf_budgetParPersonne.setText("" + (float) budget / nbPers);
        } else {
            tf_budgetParPersonne.setText("");
        }
    }

    @FXML
    private void onActionDateDebut(ActionEvent event) {
        dateDebut = dpk_dateDebut.getValue();
        dpk_dateFin.setDisable(false);
        if (dpk_dateFin.getValue() != null && dpk_dateDebut.getValue().compareTo(dpk_dateFin.getValue()) > 0 || dpk_dateDebut.getValue().isBefore(LocalDate.now())) {
            dpk_dateDebut.setValue(null);
            LOGGER.log(Level.WARNING, "La date de fin ne peut pas être avant la date de début");
        }
    }

    @FXML
    private void onActionDateFin(ActionEvent event) {
        dateFin = dpk_dateFin.getValue();
        dateFin=dpk_dateFin.getValue();
        if (dpk_dateDebut.getValue()==null || dpk_dateDebut.getValue().compareTo(dpk_dateFin.getValue()) > 0 ) {
            dpk_dateFin.setValue(null);
            LOGGER.log(Level.WARNING, "La date de fin ne peut pas être avant la date de début");
        }
    }


//    private void NumberOnly(TextField tf) {
//        tf.addEventFilter(KeyEvent.ANY, event -> {
//            if (!event.getCharacter().trim().matches("\\d?")) {
//                event.consume();
//            }
//        });
//    }


    /////////////////////////////////////////////////////////////////
    ////////////////////////// LOGO /////////////////////////////////
    /////////////////////////////////////////////////////////////////
    @FXML
    private void onButtonRetourLogo(ActionEvent event) {
        try {
            if (etape_team > 0) {
                FXMLLoader erreurPopUp = new FXMLLoader(getClass().getResource("popErreur/popUp_erreurLogo.fxml"));
                erreurPopUp.setController(this);
                Scene newUserScene = new Scene(erreurPopUp.load());
                Stage newUserWindow = new Stage();
                newUserWindow.setTitle("Attention");
                newUserWindow.initModality(Modality.APPLICATION_MODAL);
                newUserWindow.setScene(newUserScene);
                newUserWindow.showAndWait();
                newUserWindow.setResizable(false);
            }
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    private void onBoutonContinuerLogo(ActionEvent event) throws IOException {
        if (creation_semi==false && creation_team==false && etape_team>0) {
            etape_team = 0;
            this.btn_continuer.getScene().getWindow().hide();
            FXMLLoader accueil = new FXMLLoader(getClass().getResource("page_accueil.fxml"));
            accueil.setController(this);
            Stage fenetre = (Stage) btn_retour.getScene().getWindow();
            Scene scene = new Scene(accueil.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
        }else if (creation_team){
            etape_team = 1;
            evenement_cree = 1;
            this.button_tb.getScene().getWindow().hide();
            FXMLLoader informations = new FXMLLoader(getClass().getResource("page_informations_evenement.fxml"));
            informations.setController(this);
            Stage fenetre = (Stage) button_tb.getScene().getWindow();
            Scene scene = new Scene(informations.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
        }else {
            etape_team = 1;
            evenement_cree = 2;
            this.button_tb.getScene().getWindow().hide();
            FXMLLoader informations = new FXMLLoader(getClass().getResource("page_informations_evenement.fxml"));
            informations.setController(this);
            Stage fenetre = (Stage) button_tb.getScene().getWindow();
            Scene scene = new Scene(informations.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
        }
        creation_team=false;
        creation_semi=false;
    }
    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////

    @FXML
    private void onButtonRetour(ActionEvent event) {
        try {
            if (etape_team == 5) {
                etape_team--;
                FXMLLoader accueil2 = new FXMLLoader(getClass().getResource("page_activite.fxml"));
                accueil2.setController(this);
                Stage fenetre5 = (Stage) btn_retour.getScene().getWindow();
                Scene scene = new Scene(accueil2.load());
                fenetre5.setScene(scene);
                fenetre5.show();
                this.btn_continuer.getScene().getWindow().hide();
                utilisateurConnecte();
            }else {
                FXMLLoader erreurPopUp = new FXMLLoader(getClass().getResource("popErreur/popUp_erreur.fxml"));
                erreurPopUp.setController(this);
                Scene newUserScene = new Scene(erreurPopUp.load());
                Stage newUserWindow = new Stage();
                newUserWindow.setTitle("Attention");
                newUserWindow.initModality(Modality.APPLICATION_MODAL);
                newUserWindow.setScene(newUserScene);
                newUserWindow.showAndWait();
                newUserWindow.setResizable(false);
            }
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }


//----- Page Ville ------------------------------------------------------

    @FXML
    private Button button_SuivantVille;

    @FXML
    private void pageSuivanteVille(ActionEvent event) throws IOException {
        if (dateDebut.isEqual(dateFin)==false){
            etape_team=3;
            FXMLLoader VilleS = new FXMLLoader(getClass().getResource("page_hebergement.fxml"));
            VilleS.setController(this);
            Stage fenetre = (Stage) button_SuivantVille.getScene().getWindow();
            Scene scene = new Scene(VilleS.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
            LOGGER.log(Level.INFO, "Aller page choix hébergement");
        }else if (evenement_cree==1){
            etape_team = 4;
            FXMLLoader Hebergement = new FXMLLoader(getClass().getResource("page_activite.fxml"));
            Hebergement.setController(this);
            Stage fenetre = (Stage) button_SuivantVille.getScene().getWindow();
            Scene scene = new Scene(Hebergement.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
            LOGGER.log(Level.INFO, "Aller page choix activités");
        }else {
            FXMLLoader Hebergement = new FXMLLoader(getClass().getResource("page_salle.fxml"));
            Hebergement.setController(this);
            Stage fenetre = (Stage) button_SuivantVille.getScene().getWindow();
            Scene scene = new Scene(Hebergement.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
            LOGGER.log(Level.INFO, "Aller page choix salle");
        }

    }

    @FXML
    private void onActionGrenoble(ActionEvent event) throws IOException {
        try {
            controleur.getGenevent().creerVille(EnumNomVille.GRENOBLE, "38000");
            pageSuivanteVille(event);
            setHebergement(controleur.getGenevent().getHebergementsGrenoble());
            setActivite(controleur.getGenevent().getActiviteGrenoble());
            clearMes_activite();
        } catch (CodePostalException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onActionParis(ActionEvent event) throws IOException {
        try {
            controleur.getGenevent().creerVille(EnumNomVille.PARIS, "75000");
            pageSuivanteVille(event);
            setHebergement(controleur.getGenevent().getHebergementsParis());
            setActivite(controleur.getGenevent().getActiviteParis());
            clearMes_activite();
        } catch (CodePostalException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onActionNice(ActionEvent event) throws IOException {
        try {
            controleur.getGenevent().creerVille(EnumNomVille.NICE, "06000");
            pageSuivanteVille(event);
            setHebergement(controleur.getGenevent().getHebergementsNice());
            setActivite(controleur.getGenevent().getActiviteNice());
            clearMes_activite();
        } catch (CodePostalException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onActionBordeaux(ActionEvent event) throws IOException {
        try {
            controleur.getGenevent().creerVille(EnumNomVille.BORDEAUX, "33000");
            pageSuivanteVille(event);
            setHebergement(controleur.getGenevent().getHebergementsBordeaux());
            setActivite(controleur.getGenevent().getActiviteBordeaux());
            clearMes_activite();
        } catch (CodePostalException e) {
            System.out.println(e.getMessage());
        }
    }

    //----- ---------------------------------------------------------------------------
//----- Page Hebergement ----------------------------------------------------------
//----- ---------------------------------------------------------------------------
    private int etape_team = 0;//-> accueil

    @FXML
    private Button button_RetourHebergement;
    @FXML
    private Button button_SuivantHebergement;
    @FXML
    private Button btn_retour_info;
    @FXML
    private CheckBox infCent;
    @FXML
    private CheckBox centCentCinquante;
    @FXML
    private CheckBox centCinquanteDeuxCent;
    @FXML
    private CheckBox supDeuxCent;
    ArrayList<Hebergement> hebergementEnleve = new ArrayList<>();

    @FXML
    private void pageSuivanteHebergement(ActionEvent event) throws IOException {
        if (evenement_cree==1) {
            etape_team = 4;
            FXMLLoader Hebergement = new FXMLLoader(getClass().getResource("page_activite.fxml"));
            Hebergement.setController(this);
            Stage fenetre = (Stage) button_SuivantHebergement.getScene().getWindow();
            Scene scene = new Scene(Hebergement.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
            LOGGER.log(Level.INFO, "Aller page choix activité");
        }else if (evenement_cree==2){
            etape_team = 4;
            FXMLLoader Hebergement = new FXMLLoader(getClass().getResource("page_salle.fxml"));
            Hebergement.setController(this);
            Stage fenetre = (Stage) button_SuivantHebergement.getScene().getWindow();
            Scene scene = new Scene(Hebergement.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
            LOGGER.log(Level.INFO, "Aller page choix salle");
        }
    }



    @FXML
    private void isInfCentSelected(ActionEvent event) {
        if(infCent.isSelected()) {
            if (!(centCentCinquante.isSelected() || centCinquanteDeuxCent.isSelected() || supDeuxCent.isSelected())){
                hebergementEnleve.addAll(hebergements);
                hebergements.clear();
            }
            Iterator<Hebergement> it = hebergementEnleve.iterator();
            while (it.hasNext()) {
                Hebergement hebergement = it.next();
                if(hebergement.getPrix() < 100) {
                    hebergements.add(hebergement);
                    it.remove();
                    LOGGER.log(Level.INFO, "filtre inférieur à 100€ sélectionné");
                }
            }
        } else if (centCentCinquante.isSelected() || centCinquanteDeuxCent.isSelected() || supDeuxCent.isSelected()) {
            Iterator<Hebergement> it = hebergements.iterator();
            while (it.hasNext()) {
                Hebergement hebergement = it.next();
                if(hebergement.getPrix() < 100) {
                    hebergementEnleve.add(hebergement);
                    it.remove();
                }
            }
        } else {
            hebergements.addAll(hebergementEnleve);
            hebergementEnleve.clear();
        }
    }

    @FXML
    private void isCentCentCinquanteSelected(ActionEvent event) {
        if(centCentCinquante.isSelected()) {
            if (!(infCent.isSelected() || centCinquanteDeuxCent.isSelected() || supDeuxCent.isSelected())){
                hebergementEnleve.addAll(hebergements);
                hebergements.clear();
            }
            Iterator<Hebergement> it = hebergementEnleve.iterator();
            while (it.hasNext()) {
                Hebergement hebergement = it.next();
                if(hebergement.getPrix() >= 100 && hebergement.getPrix() < 150) {
                    hebergements.add(hebergement);
                    it.remove();
                    LOGGER.log(Level.INFO, "filtre entre cent et cent cinquante euros sélectionné");
                }
            }
        } else if (infCent.isSelected() || centCinquanteDeuxCent.isSelected() || supDeuxCent.isSelected()) {
            Iterator<Hebergement> it = hebergements.iterator();
            while (it.hasNext()) {
                Hebergement hebergement = it.next();
                if(hebergement.getPrix() >= 100 && hebergement.getPrix() < 150) {
                    hebergementEnleve.add(hebergement);
                    it.remove();
                }
            }
        } else {
            hebergements.addAll(hebergementEnleve);
            hebergementEnleve.clear();
        }
    }

    @FXML
    private void isCentCinquanteDeuxCentSelected(ActionEvent event) {
        if(centCinquanteDeuxCent.isSelected()) {
            if (!(centCentCinquante.isSelected() || infCent.isSelected() || supDeuxCent.isSelected())){
                hebergementEnleve.addAll(hebergements);
                hebergements.clear();
            }
            Iterator<Hebergement> it = hebergementEnleve.iterator();
            while (it.hasNext()) {
                Hebergement hebergement = it.next();
                if(hebergement.getPrix() >= 150 && hebergement.getPrix() < 200) {
                    hebergements.add(hebergement);
                    it.remove();
                    LOGGER.log(Level.INFO, "filtre entre cent cinquante et deux cents euros sélectionné");
                }
            }
        } else if (centCentCinquante.isSelected() || infCent.isSelected() || supDeuxCent.isSelected()) {
            Iterator<Hebergement> it = hebergements.iterator();
            while (it.hasNext()) {
                Hebergement hebergement = it.next();
                if(hebergement.getPrix() >= 150 && hebergement.getPrix() < 200) {
                    hebergementEnleve.add(hebergement);
                    it.remove();
                }
            }
        } else {
            hebergements.addAll(hebergementEnleve);
            hebergementEnleve.clear();
        }
    }

    @FXML
    private void isSupDeuxCentSelected(ActionEvent event) {
        if(supDeuxCent.isSelected()) {
            if (!(centCentCinquante.isSelected() || centCinquanteDeuxCent.isSelected() || infCent.isSelected())){
                hebergementEnleve.addAll(hebergements);
                hebergements.clear();
            }
            Iterator<Hebergement> it = hebergementEnleve.iterator();
            while (it.hasNext()) {
                Hebergement hebergement = it.next();
                if(hebergement.getPrix() >= 200) {
                    hebergements.add(hebergement);
                    it.remove();
                    LOGGER.log(Level.INFO, "filtre supérieur à deux cents euros sélectionné");
                }
            }
        } else if (centCentCinquante.isSelected() || centCinquanteDeuxCent.isSelected() || infCent.isSelected()) {
            Iterator<Hebergement> it = hebergements.iterator();
            while (it.hasNext()) {
                Hebergement hebergement = it.next();
                if(hebergement.getPrix() >= 200) {
                    hebergementEnleve.add(hebergement);
                    it.remove();
                }
            }
        } else {
            hebergements.addAll(hebergementEnleve);
            hebergementEnleve.clear();
        }
    }

    @FXML
    private void onActionRetourHebergement(ActionEvent event){
        onButtonRetour(event);
    }

//----- ---------------------------------------------------------------------------
//----- Page Activité ----------------------------------------------------------
//----- ---------------------------------------------------------------------------


    @FXML
    private Button button_mesactivites;

    @FXML
    private void pageMesActivites(ActionEvent event) throws IOException {
        etape_team = 5;
        FXMLLoader mesActivites = new FXMLLoader(getClass().getResource("page_mes_activite.fxml"));
        mesActivites.setController(this);
        Stage fenetre = (Stage) button_mesactivites.getScene().getWindow();
        Scene scene = new Scene(mesActivites.load());
        fenetre.setScene(scene);
        fenetre.show();
        utilisateurConnecte();
        LOGGER.log(Level.INFO, "Aller choix page mes activités");
    }

    @FXML
    private CheckBox infDix;
    @FXML
    private CheckBox dixVingt;
    @FXML
    private CheckBox vingtTrente;
    @FXML
    private CheckBox supTrente;
    ArrayList<Prestation> prestationsEnleves = new ArrayList<>();

    @FXML
    private void isInf10eSelected(ActionEvent event) {
        if(infDix.isSelected()) {
            if (!(dixVingt.isSelected() || vingtTrente.isSelected() || supTrente.isSelected())){
                prestationsEnleves.addAll(activités);
                activités.clear();
            }
            Iterator<Prestation> it = prestationsEnleves.iterator();
            while (it.hasNext()) {
                Prestation prestation = it.next();
                if(prestation.getPrix()<10) {
                    activités.add(prestation);
                    it.remove();
                    LOGGER.log(Level.INFO, "filtre inférieur à 10 euros sélectionné");
                }
            }
        } else if (dixVingt.isSelected() || vingtTrente.isSelected() || supTrente.isSelected()){
            Iterator<Prestation> it = activités.iterator();
            while (it.hasNext()) {
                Prestation prestation = it.next();
                if(prestation.getPrix() < 10) {
                    prestationsEnleves.add(prestation);
                    it.remove();
                }
            }
        } else {
            activités.addAll(prestationsEnleves);
            prestationsEnleves.clear();
        }
    }

    @FXML
    private void is10A20Selected(ActionEvent event) {
        if(dixVingt.isSelected()) {
            if (!(infDix.isSelected() || vingtTrente.isSelected() || supTrente.isSelected())){
                prestationsEnleves.addAll(activités);
                activités.clear();
            }
            Iterator<Prestation> it = prestationsEnleves.iterator();
            while (it.hasNext()) {
                Prestation prestation = it.next();
                if(prestation.getPrix()>=10 && prestation.getPrix() < 20) {
                    activités.add(prestation);
                    it.remove();
                    LOGGER.log(Level.INFO, "filtre entre 10 et 20 euros sélectionné");
                }
            }
        } else if (infDix.isSelected() || vingtTrente.isSelected() || supTrente.isSelected()){
            Iterator<Prestation> it = activités.iterator();
            while (it.hasNext()) {
                Prestation prestation = it.next();
                if(prestation.getPrix() >= 10 && prestation.getPrix() < 20) {
                    prestationsEnleves.add(prestation);
                    it.remove();
                }
            }
        } else {
            activités.addAll(prestationsEnleves);
            prestationsEnleves.clear();
        }
    }

    @FXML
    private void is20A30Selected(ActionEvent event) {
        if(vingtTrente.isSelected()) {
            if (!(infDix.isSelected() || dixVingt.isSelected() || supTrente.isSelected())){
                prestationsEnleves.addAll(activités);
                activités.clear();
            }
            Iterator<Prestation> it = prestationsEnleves.iterator();
            while (it.hasNext()) {
                Prestation prestation = it.next();
                if(prestation.getPrix()>=20 && prestation.getPrix() < 30) {
                    activités.add(prestation);
                    it.remove();
                    LOGGER.log(Level.INFO, "filtre entre 20 euros et 30 euros sélectionné");
                }
            }
        } else if(infDix.isSelected() || dixVingt.isSelected() || supTrente.isSelected()) {
            Iterator<Prestation> it = activités.iterator();
            while (it.hasNext()) {
                Prestation prestation = it.next();
                if(prestation.getPrix() >= 20 && prestation.getPrix() < 30) {
                    prestationsEnleves.add(prestation);
                    it.remove();
                }
                System.out.println(prestationsEnleves.size());
            }
        } else {
            activités.addAll(prestationsEnleves);
            prestationsEnleves.clear();
        }
    }

    @FXML
    private void isSup30(ActionEvent event) {
        if(supTrente.isSelected()) {
            if (!(infDix.isSelected() || vingtTrente.isSelected() || dixVingt.isSelected())){
                prestationsEnleves.addAll(activités);
                activités.clear();
            }
            Iterator<Prestation> it = prestationsEnleves.iterator();
            while (it.hasNext()) {
                Prestation prestation = it.next();
                if(prestation.getPrix() >= 30) {
                    activités.add(prestation);
                    it.remove();
                    LOGGER.log(Level.INFO, "filtre supérieur à 30 euros sélectionné");
                }
            }
        } else if (infDix.isSelected() || vingtTrente.isSelected() || dixVingt.isSelected()) {
            Iterator<Prestation> it = activités.iterator();
            while (it.hasNext()) {
                Prestation prestation = it.next();
                if(prestation.getPrix() >= 30) {
                    prestationsEnleves.add(prestation);
                    it.remove();
                }
            }
        } else {
            activités.addAll(prestationsEnleves);
            prestationsEnleves.clear();
        }
    }


    //----- ---------------------------------------------------------------------------
//----- Page Mes Activités ----------------------------------------------------------
//----- ---------------------------------------------------------------------------
    @FXML
    private Button suivant_mesActivites;

    @FXML
    private void pageSuivantMesActivites(ActionEvent event) throws IOException {
        etape_team = 6;
        FXMLLoader prestations = new FXMLLoader(getClass().getResource("page_prestation.fxml"));
        prestations.setController(this);
        Stage fenetre = (Stage) suivant_mesActivites.getScene().getWindow();
        Scene scene = new Scene(prestations.load());
        fenetre.setScene(scene);
        fenetre.show();
        utilisateurConnecte();
        LOGGER.log(Level.INFO, "Aller page choix prestataires");
    }

    @FXML
    private void onRetourActivite(ActionEvent event) throws IOException {
        if (evenement_cree ==1) {
            etape_team = 4;
            FXMLLoader activite = new FXMLLoader(getClass().getResource("page_activite.fxml"));
            activite.setController(this);
            Stage fenetre = (Stage) suivant_mesActivites.getScene().getWindow();
            Scene scene = new Scene(activite.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
        }else if (evenement_cree==2){
            FXMLLoader activite = new FXMLLoader(getClass().getResource("page_salle.fxml"));
            activite.setController(this);
            Stage fenetre = (Stage) suivant_mesActivites.getScene().getWindow();
            Scene scene = new Scene(activite.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
        }
    }


//----- ---------------------------------------------------------------------------
//----- Page Prestations ----------------------------------------------------------
//----- ---------------------------------------------------------------------------

    @FXML
    private Button creerEv;

    @FXML
    private void onRetourPrestation(ActionEvent event) throws IOException {
        if (evenement_cree ==1) {
            etape_team = 4;
            FXMLLoader activite = new FXMLLoader(getClass().getResource("page_activite.fxml"));
            activite.setController(this);
            Stage fenetre = (Stage) creerEv.getScene().getWindow();
            Scene scene = new Scene(activite.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
        }else if (evenement_cree==2){
            etape_team = 4;
            FXMLLoader activite = new FXMLLoader(getClass().getResource("page_salle.fxml"));
            activite.setController(this);
            Stage fenetre = (Stage) creerEv.getScene().getWindow();
            Scene scene = new Scene(activite.load());
            fenetre.setScene(scene);
            fenetre.show();
            utilisateurConnecte();
        }
    }

    @FXML
    private Button log;

//----- ------------------------------------------------------
//----- Page prestations ------------------------------------------------------
// ----- ------------------------------------------------------

    @FXML
    private void onActionCreerEv(ActionEvent event) throws IOException {
        LOGGER.log(Level.INFO, "Début du processus de création de l'événement");
        //récupération de l'utilisateur connecté
        Utilisateur utilisateur = controleur.getGenevent().getUtilisateur_connecter();
        //Récupération des informations utilisateurs
        IHM.InfosUtilisateur data = new InfosUtilisateur(
                utilisateur.getEmail(),
                utilisateur.getPrenom(),
                utilisateur.getNom()
        );
        LOGGER.log(Level.INFO, "Récupération des données de l'utilisateur : " + data.email);
        controleur.getGenevent().clearCalendrier();
        //Récupération des informations de l'événement
        IHM.InfosNouvelEvenement eventData = new InfosNouvelEvenement(
                tf_nomEvent.getText(),
                dpk_dateDebut.getValue(),
                dpk_dateFin.getValue(),
                data,
                controleur.getGenevent().getCalendrier_activites(),
                controleur.getGenevent().getCalendrier_prestations(),
                controleur.getGenevent().getVille(),
                Integer.parseInt(tf_budget.getText()),
                Integer.parseInt(tf_nbPers.getText()),
                0.0f
        );
        LOGGER.log(Level.INFO, "Récupérations des informations de l'événement : " + eventData.nom);

        //Créer événement
        controleur.creerEvenement(eventData);
        LOGGER.log(Level.INFO, "Création de l'événement : " + eventData.nom + ", administré par l'utilisateur" +
                " : " + eventData.admin.email);

        //load page accueil
        etape_team = 0;
        FXMLLoader CreerE = new FXMLLoader(getClass().getResource("page_accueil.fxml"));
        evenement = Evenement.initialiseEvenement(this.controleur.getGenevent(), tf_nomEvent.getText(), dpk_dateDebut.getValue(), dpk_dateFin.getValue(), controleur.getGenevent().getUtilisateur_connecter(), this.controleur.getGenevent().getCalendrier_activites(), controleur.getGenevent().getCalendrier_prestations(), controleur.getGenevent().getVille(), Integer.parseInt(tf_budget.getText()), Integer.parseInt(tf_nbPers.getText()), 0.0f);
        controleur.getGenevent().creerEvenement(evenement, controleur.getGenevent().getVille(), Integer.parseInt(tf_budget.getText()), mes_activités, mes_prestation, Integer.parseInt(tf_nbPers.getText()));
        addEvenement(evenement);
        CreerE.setController(this);
        Stage fenetre = (Stage) creerEv.getScene().getWindow();
        Scene scene = new Scene(CreerE.load());
        fenetre.setScene(scene);
        fenetre.show();
        utilisateurConnecte();
        LOGGER.log(Level.INFO, "Retour page d'accueil");
    }

//----- ------------------------------------------------------
//----- Page pop Up error ------------------------------------------------------
// ----- ------------------------------------------------------

    @FXML
    private Button btn_continuer;
    @FXML
    private Button btn_annuler;

    @FXML
    private void onBoutonAnnuler(ActionEvent event) {
        this.btn_annuler.getScene().getWindow().hide();
    }

    /**
     * @param event
     * @throws IOException
     */
    @FXML
    private void onBoutonContinuer(ActionEvent event) throws IOException {
        if (etape_team == 1) {
            etape_team = 0;
            FXMLLoader accueil = new FXMLLoader(getClass().getResource("page_accueil.fxml"));
            accueil.setController(this);
            Stage fenetre = (Stage) btn_retour.getScene().getWindow();
            Scene scene = new Scene(accueil.load());
            fenetre.setScene(scene);
            fenetre.show();
            this.btn_continuer.getScene().getWindow().hide();
            utilisateurConnecte();
        } else if (etape_team == 2) {
            etape_team--;
            FXMLLoader accueil2 = new FXMLLoader(getClass().getResource("page_informations_evenement.fxml"));
            accueil2.setController(this);
            Stage fenetre2 = (Stage) btn_retour.getScene().getWindow();
            Scene scene = new Scene(accueil2.load());
            fenetre2.setScene(scene);
            fenetre2.show();
            this.btn_continuer.getScene().getWindow().hide();
            utilisateurConnecte();
        } else if (etape_team == 3) {
            etape_team--;
            FXMLLoader accueil3 = new FXMLLoader(getClass().getResource("page_ville2.fxml"));
            accueil3.setController(this);
            Stage fenetre3 = (Stage) btn_retour.getScene().getWindow();
            Scene scene = new Scene(accueil3.load());
            fenetre3.setScene(scene);
            fenetre3.show();
            this.btn_continuer.getScene().getWindow().hide();
            utilisateurConnecte();
        } else if (etape_team == 4 && dateDebut.isEqual(dateFin)==false) {
            etape_team--;
            FXMLLoader accueil = new FXMLLoader(getClass().getResource("page_hebergement.fxml"));
            accueil.setController(this);
            Stage fenetre4 = (Stage) btn_retour.getScene().getWindow();
            Scene scene = new Scene(accueil.load());
            fenetre4.setScene(scene);
            fenetre4.show();
            this.btn_continuer.getScene().getWindow().hide();
            utilisateurConnecte();
        }else if (etape_team == 4 && dateDebut.isEqual(dateFin)==true){
            etape_team=2;
            FXMLLoader accueil3 = new FXMLLoader(getClass().getResource("page_ville2.fxml"));
            accueil3.setController(this);
            Stage fenetre3 = (Stage) btn_retour.getScene().getWindow();
            Scene scene = new Scene(accueil3.load());
            fenetre3.setScene(scene);
            fenetre3.show();
            this.btn_continuer.getScene().getWindow().hide();
            utilisateurConnecte();
        }
    }
    //-----------------------Page recap Activité ----------------

    private Evenement evenement_consulter;

    public void setEvenement_consulter(Evenement evenement_consulter) {
        this.evenement_consulter = evenement_consulter;
    }

    @FXML
    private void onRetourEvenement(ActionEvent event) throws IOException {
        this.btn_retour_info.getScene().getWindow().hide();
    }

    //---------------------Page hebergement ----------------------------

    public void setHebergement_choisi(Hebergement hebergement_choisi) {
        this.hebergement_choisi = hebergement_choisi;
    }


    //-----  Implémentation des méthodes abstraites  -------------------------------

    @Override
    public void demarrerInteraction() {
        // démarrage de l'interface JavaFX
        Platform.startup(() -> {
            Stage primaryStage = new Stage();
            primaryStage.setOnCloseRequest((WindowEvent t) -> this.exitAction());
            try {
                this.start(primaryStage);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        });

        // attente de la fin de vie de l'interface JavaFX
        try {
            this.eolBarrier.await();
        } catch (InterruptedException exc) {
            System.err.println("Erreur d'exécution de l'interface.");
            System.err.flush();
        }
    }

    @Override
    public void informerUtilisateur(String msg, boolean succes) {
        final Alert alert = new Alert(
                succes ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING
        );
        alert.setTitle("GenEvent");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void saisirUtilisateur() {
        try {
            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("new-user-view.fxml"));
            newUserViewLoader.setController(this);
            Scene newUserScene = new Scene(newUserViewLoader.load());

            Stage newUserWindow = new Stage();
            newUserWindow.setTitle("Créer un·e utilisa·teur/trice");
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.showAndWait();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void saisirNouvelEvenement(Set<String> nomsExistants) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//--------------------------------------------------
//-------------- Test Sérialisation-----------------
//--------------------------------------------------

    @FXML
    private void creerEvenement(ActionEvent event) throws CodePostalException {
        //récupération de l'utilisateur connecté
        Utilisateur utilisateur = controleur.getGenevent().getUtilisateur_connecter();
        //Récupération des informations utilisateurs
        IHM.InfosUtilisateur data = new InfosUtilisateur(
                utilisateur.getEmail(),
                utilisateur.getPrenom(),
                utilisateur.getNom()
        );
        //Récupération des informations de l'événement
        IHM.InfosNouvelEvenement eventData = new InfosNouvelEvenement("salut", null, null, data, controleur.getGenevent().getCalendrier_activites(), controleur.getGenevent().getCalendrier_prestations(), controleur.getGenevent().getVille(), 0, 0 , 0.0f);

        //Créer événement
        controleur.creerEvenement(eventData);

        addMes_Activite(controleur.getGenevent().getActiviteBordeaux());
        mes_activités.clear();
        //Ajout de l'événement à la l'observable liste
        addEvenement(controleur.getGenevent().getEvenements().get("salut"));
    }

    private void chercherEvenement(){
        Utilisateur utilisateur = controleur.getGenevent().getUtilisateur_connecter();
        for(Evenement evenement : controleur.getGenevent().getEvenements().values()){
            if(evenement.getAdministrateurs().containsKey(utilisateur.getEmail())){
                evenements.add(evenement);
            }
        }
    }
    private void viderEvenements(){
        evenements.clear();
    }
}
