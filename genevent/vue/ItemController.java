package fr.uga.iut2.genevent.vue;

import fr.uga.iut2.genevent.controleur.Controleur;
import fr.uga.iut2.genevent.modele.Evenement;
import fr.uga.iut2.genevent.modele.Hebergement;
import fr.uga.iut2.genevent.modele.Prestation;
import fr.uga.iut2.genevent.modele.Reunion;
import fr.uga.iut2.genevent.util.ItemEvenement;
import fr.uga.iut2.genevent.util.ItemEvenement_activite;
import fr.uga.iut2.genevent.util.ItemEvenement_prestataire;
import fr.uga.iut2.genevent.util.Persisteur;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class ItemController implements Initializable {
    private Evenement evenement;
    private JavaFXGUI javaFXGUI;
    private Hebergement hebergement;
    private Prestation activité;
    private Prestation mes_activite;
    private Prestation prestation;
    private Prestation mes_prestation;
    private Prestation event_prestation;
    private Prestation event_act;
    private Reunion reunion;

    public void setFXGUI(JavaFXGUI javaFXGUI) {
        this.javaFXGUI = javaFXGUI;
    }

    //label pour tous les modèles
    @FXML
    private Label name;
    @FXML private Label desc;
    @FXML private Label pers;
    @FXML private Label prix;

    @FXML
    private Button se_connecter;

    //button page hébergement
    @FXML private Button button_modifier;
    @FXML private Button button_SuivantHebergement;
    @FXML private Button btn_retour_info;
    //label page hébergement info
    @FXML private Label hebNom;
    @FXML private Label hebPrix;
    @FXML private Label hebDesc;
    @FXML private Label hebCapacite;
    @FXML private Label hebAdresseMail;
    @FXML private Label hebTel;
    @FXML private Label hebAdresse;
    @FXML private Label hebNbEtoile;
    //button page evenement
    @FXML private Label evnom;
    @FXML private Label evperiode;
    @FXML private Label evlieu;
    @FXML private Label evpersonne;
    @FXML private Label evbudget;
    //button page événement
    @FXML private Label dateEvenement;

    /**
     *
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    private void supprime_evenement(ActionEvent event) throws IOException, ClassNotFoundException {
        //long startTime = System.currentTimeMillis();
        javaFXGUI.removeEvenement(evenement);
    }

    public void setEvenement(Evenement e) {
        this.evenement = e;
        name.setText( evenement.getNom());
        //desc.setText(evenement.get);
        dateEvenement.setText(evenement.getDateDebut()+" / "+evenement.getDateFin());
    }

    public void setHebergement(Hebergement h) {
        this.hebergement =h;
        name.setText(hebergement.getLibelle());
        desc.setText(hebergement.getDescription());
        pers.setText(hebergement.getCapacite() + " personnes max");
        prix.setText(hebergement.getPrix() + "€/pers");
    }

    public void setPrestation(Prestation p) {
        this.prestation =p;
        name.setText(prestation.getLibelle());
        desc.setText(prestation.getDescription());
        prix.setText("" + prestation.getPrix());
    }

    public void setMes_prestation(Prestation p) {
        this.mes_prestation =p;
        name.setText(mes_prestation.getLibelle());
        desc.setText(mes_prestation.getDescription());
        prix.setText("" + mes_prestation.getPrix());
    }

    @FXML
    private void ajout_mesActivite(ActionEvent event){
        JavaFXGUI.addMes_Activite(activité);
        JavaFXGUI.removeActivite(activité);
    }

    @FXML
    private void supprimer_mesActivité(ActionEvent event){
        JavaFXGUI.addActivite(mes_activite);
        JavaFXGUI.removemes_Activite(mes_activite);
    }

    @FXML
    private void ajout_mesPrestataire(ActionEvent event){
        JavaFXGUI.addMes_Prestation(prestation);
        JavaFXGUI.removepestation(prestation);
    }

    @FXML
    private void ajout_Prestataire(ActionEvent event){
        JavaFXGUI.addPrestation(mes_prestation);
        JavaFXGUI.removemes_pestation(mes_prestation);
    }

    public void setActivite(Prestation e) {
        this.activité =e;
        name.setText(activité.getLibelle());
        desc.setText(activité.getDescription());
        pers.setText("" + activité.getPersonne_max());
        prix.setText("" + activité.getPrix());
    }

    public void setMes_activite(Prestation e) {
        this.mes_activite =e;
        name.setText(mes_activite.getLibelle());
        desc.setText(mes_activite.getDescription());
        //pers.setText("" + mes_activite.getPersonne_max());
        prix.setText("" + mes_activite.getPrix());
    }

//-----------------------------------------------
//---------- Page hebergement -------------------
//-----------------------------------------------

    //ouvre une pop up sur les informations de l'hébergement
    @FXML
    private void pageHebergementInfo(ActionEvent event) throws IOException {
        try {
            FXMLLoader hotelInfo = new FXMLLoader(getClass().getResource("page_hebergement_info.fxml"));
            hotelInfo.setController(this);
            Scene newUserScene = new Scene(hotelInfo.load());
            Stage newUserWindow = new Stage();
            getInfoHebergement2();
            newUserWindow.setTitle("Information hébergement");
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.showAndWait();
            newUserWindow.setResizable(false);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    private void onRetourHebergement(ActionEvent event) throws IOException {
        this.btn_retour_info.getScene().getWindow().hide();
    }

    private void getInfoHebergement2() {
        hebNom.setText(hebergement.getLibelle());
        hebAdresse.setText("Adresse : " + hebergement.getAdresse());
        hebCapacite.setText("Capacité : " + hebergement.getCapacite() + " personnes maximum");
        hebDesc.setText(hebergement.getDescription());
        hebAdresseMail.setText("Adresse Mail : " + hebergement.getMail());
        hebPrix.setText("Prix : " + hebergement.getPrix() + "€");
        hebTel.setText("Numéro de téléphone : " + hebergement.getNumero_telephone());
        hebNbEtoile.setText("Nombre d'étoile : "+hebergement.getEtoile());
    }

//-----------------------------------------------
//---------- Page Evenement -------------------
//-----------------------------------------------

    //ouvre une pop up sur les informations de l'hébergement
    @FXML
    private void pageEvenementInfo(ActionEvent event) throws IOException {
        try {
            FXMLLoader hotelInfo = new FXMLLoader(getClass().getResource("page_consultation_evenement2.fxml"));
            hotelInfo.setController(this);
            Scene newUserScene = new Scene(hotelInfo.load());
            Stage newUserWindow = new Stage();
            setEvenement(evenement);
            getInfoEvenement();
            newUserWindow.setTitle("Information evenement");
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.showAndWait();
            newUserWindow.setResizable(false);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    private void onRetourEvenement(ActionEvent event) throws IOException {
        this.btn_retour_info.getScene().getWindow().hide();
    }

    private void getInfoEvenement() {
        evlieu.setText(evenement.getVille().getNom().getLibelle());
        evperiode.setText(evenement.getDateDebut()+" / "+evenement.getDateFin());
        evnom.setText(evenement.getNom());
        evpersonne.setText(String.valueOf(evenement.getNombre_personne()));
        evbudget.setText(String.valueOf(evenement.getBudget()));
    }


    @FXML
    private void act_event(ActionEvent event) throws IOException {
        try {
            FXMLLoader hotelInfo = new FXMLLoader(getClass().getResource("page_recapActivités.fxml"));
            hotelInfo.setController(javaFXGUI);
            javaFXGUI.setEvenement_consulter(evenement);
            javaFXGUI.setEtape(7);
            Scene newUserScene = new Scene(hotelInfo.load());
            Stage newUserWindow = new Stage();
            newUserWindow.setTitle("Information evenement activités");
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.showAndWait();
            newUserWindow.setResizable(false);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    private void prest_event(ActionEvent event) throws IOException {
        try {
            FXMLLoader hotelInfo = new FXMLLoader(getClass().getResource("page_recapPresta.fxml"));
            hotelInfo.setController(javaFXGUI);
            javaFXGUI.setEvenement_consulter(evenement);
            javaFXGUI.setEtape(8);
            Scene newUserScene = new Scene(hotelInfo.load());
            Stage newUserWindow = new Stage();
            newUserWindow.setTitle("Information evenement prestataire");
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.showAndWait();
            newUserWindow.setResizable(false);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void setEvent_Activite(Prestation e) {
        this.event_act =e;
        name.setText(event_act.getLibelle());
        desc.setText(event_act.getDescription());
        pers.setText("" + event_act.getPersonne_max());
        prix.setText("" + event_act.getPrix());
    }

    public void setReunion(Reunion reunion){
        this.reunion=reunion;
        name.setText(reunion.getObjet());
        desc.setText(reunion.getLieu());
        pers.setText(reunion.getNb_personnes() + " personnes max");
        prix.setText(reunion.getDuree() + "€/pers");
    }

    public void setEvent_Prestation(Prestation e) {
        this.event_prestation=e;
        name.setText(event_prestation.getLibelle());
        desc.setText(event_prestation.getDescription());
        prix.setText("" + event_prestation.getPrix());
    }

    @FXML
    private Button btn_hebchoisi;

    @FXML
    private void hebchoisi(ActionEvent event) throws IOException {
        if (javaFXGUI.getEvenement_cree() == 1) {
            try {
                javaFXGUI.setEtape(4);
                FXMLLoader activite = new FXMLLoader(getClass().getResource("page_activite.fxml"));
                activite.setController(javaFXGUI);
                Stage fenetre = (Stage) btn_hebchoisi.getScene().getWindow();
                Scene scene = new Scene(activite.load());
                fenetre.setScene(scene);
                fenetre.show();
                javaFXGUI.utilisateurConnecte();
                javaFXGUI.setHebergement_choisi(hebergement);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }else {
            try {
                javaFXGUI.setEtape(4);
                FXMLLoader activite = new FXMLLoader(getClass().getResource("page_salle.fxml"));
                activite.setController(javaFXGUI);
                Stage fenetre = (Stage) btn_hebchoisi.getScene().getWindow();
                Scene scene = new Scene(activite.load());
                fenetre.setScene(scene);
                fenetre.show();
                javaFXGUI.utilisateurConnecte();
                javaFXGUI.setHebergement_choisi(hebergement);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }
    }

    @FXML
    private void reunionchoisi(ActionEvent event) throws IOException {
            try {
                javaFXGUI.setEtape(6);
                FXMLLoader activite = new FXMLLoader(getClass().getResource("page_prestation.fxml"));
                activite.setController(javaFXGUI);
                Stage fenetre = (Stage) btn_hebchoisi.getScene().getWindow();
                Scene scene = new Scene(activite.load());
                fenetre.setScene(scene);
                fenetre.show();
                javaFXGUI.utilisateurConnecte();
                javaFXGUI.setHebergement_choisi(hebergement);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }
}