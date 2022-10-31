package fr.uga.iut2.genevent.util;

import fr.uga.iut2.genevent.modele.Evenement;
import fr.uga.iut2.genevent.modele.Hebergement;
import fr.uga.iut2.genevent.vue.ItemController;
import fr.uga.iut2.genevent.vue.JavaFXGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ItemHebergement extends ListCell<Hebergement> {
    private Node rootNode;
    private ItemController controleur = new ItemController() ;
    private JavaFXGUI javaFXGUI;


    public void setJavaFXGUI(JavaFXGUI javaFXGUI) {
        this.javaFXGUI = javaFXGUI;
        controleur.setFXGUI(javaFXGUI);
    }

    public ItemHebergement() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(controleur.getClass().getResource("modele_hebergement.fxml"));
        fxmlLoader.setController(controleur);
        try {
            this.rootNode = fxmlLoader.load();
        } catch (IllegalStateException | IOException e) {
            System.out.println("Erreur " + e);
        }
    }

    @Override
    protected void updateItem(Hebergement h, boolean b) {
        super.updateItem(h, b);
        if (h != null) {
            controleur.setHebergement(h);
            setGraphic(rootNode);
        }else {
            setGraphic(null);
        }
    }
}
