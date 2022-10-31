package fr.uga.iut2.genevent.util;

import fr.uga.iut2.genevent.modele.Evenement;
import fr.uga.iut2.genevent.modele.Prestation;
import fr.uga.iut2.genevent.vue.ItemController;
import fr.uga.iut2.genevent.vue.JavaFXGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import java.io.IOException;

public class ItemMesPrestation extends ListCell<Prestation> {
    private Node rootNode;
    private ItemController controleur = new ItemController() ;

    public ItemMesPrestation() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(controleur.getClass().getResource("modele_mes_prestataire.fxml"));
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
            controleur.setMes_prestation(e);
            setGraphic(rootNode);
        }else {
            setGraphic(null);
        }
    }

}
