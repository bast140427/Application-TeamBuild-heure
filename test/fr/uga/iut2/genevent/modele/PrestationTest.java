package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.PrixException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrestationTest {

    @Test
    void getLibelle() throws PrixException {
        Prestation prestation = new Prestation("minuscule","",5, 12, null,Type_presta.SECURITE, null,"83390","l'autre");
        assertEquals("Minuscule", prestation.getLibelle(), "Le nom de la prestation doit avoir la première lettre en " +
                "majuscule et le reste en minuscule");
    }

    @Test
    void getPrix() throws PrixException {
        assertThrows(PrixException.class, () -> {
            new Prestation("Nom", "", 5, 0, null, Type_presta.SECURITE, null,"83390","l'autre");
        }, "Le prix ne peut pas être égal à 0");
    }

}