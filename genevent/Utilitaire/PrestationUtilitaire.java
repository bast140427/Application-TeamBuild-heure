package fr.uga.iut2.genevent.Utilitaire;

import java.time.DayOfWeek;
import java.util.HashMap;

public class PrestationUtilitaire {

    public static void afficherHoraire(HashMap<DayOfWeek, String> horaires){
        System.out.println("Horaires: ");
        for(DayOfWeek jours : horaires.keySet()){
            System.out.println("\t" + jours + ": " + horaires.get(jours));
        }
    }
}
