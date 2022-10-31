package fr.uga.iut2.genevent.util;

import fr.uga.iut2.genevent.modele.Evenement;
import fr.uga.iut2.genevent.vue.ItemController;
import fr.uga.iut2.genevent.vue.JavaFXGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import java.io.IOException;

public class ItemEvenement extends ListCell<Evenement> {
    private Node rootNode;
    private ItemController controleur = new ItemController() ;
    private JavaFXGUI javaFXGUI;


    public void setJavaFXGUI(JavaFXGUI javaFXGUI) {
        this.javaFXGUI = javaFXGUI;
        controleur.setFXGUI(javaFXGUI);
    }

    public ItemEvenement() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(controleur.getClass().getResource("modèle_evenement_cree.fxml"));
        fxmlLoader.setController(controleur);
        try {
            this.rootNode = fxmlLoader.load();
        } catch (IllegalStateException | IOException e) {
            System.out.println("Erreur " + e);
        }
    }


    @Override
    protected void updateItem(Evenement e, boolean empty) {
        super.updateItem(e, empty);
        if (e != null) {
            controleur.setEvenement(e);
            setGraphic(rootNode);
        }else {
            setGraphic(null);
        }
    }
}

