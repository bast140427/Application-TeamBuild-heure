package fr.uga.iut2.genevent.util;

import fr.uga.iut2.genevent.modele.Prestation;
import fr.uga.iut2.genevent.vue.ItemController;
import fr.uga.iut2.genevent.vue.JavaFXGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ItemActivite extends ListCell<Prestation>{
    private Node rootNode;
    private ItemController controleur = new ItemController() ;
    private JavaFXGUI javaFXGUI;


    public void setJavaFXGUI(JavaFXGUI javaFXGUI) {
        this.javaFXGUI = javaFXGUI;
        controleur.setFXGUI(javaFXGUI);
    }

    public ItemActivite() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(controleur.getClass().getResource("modele_activite.fxml"));
        fxmlLoader.setController(controleur);
        try {
            this.rootNode = fxmlLoader.load();
        } catch (IllegalStateException | IOException e) {
            System.out.println("Erreur " + e);
        }
    }


    @Override
    protected void updateItem(Prestation e, boolean empty) {
        super.updateItem(e, empty);
        if (e != null) {
            controleur.setActivite(e);
            setGraphic(rootNode);
        }else {
            setGraphic(null);
        }
    }
}
