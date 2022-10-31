package fr.uga.iut2.genevent.util;

import fr.uga.iut2.genevent.Exception.PrixException;
import fr.uga.iut2.genevent.modele.Hebergement;
import fr.uga.iut2.genevent.modele.Prestation;
import fr.uga.iut2.genevent.modele.Type_Hebergement;
import fr.uga.iut2.genevent.modele.Type_presta;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Utilitaire {
    public static String capitalize(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1, s.length()).toLowerCase();
    }


    /**
     *
     * @param nomFichier
     * @return
     */
    public static ArrayList<Hebergement> lectureXML_hotel(String nomFichier){
        ArrayList<Hebergement> lexique = new ArrayList<>();
        Boolean entry=false;
        String libelle = null;
        float prix =0;
        int capacité=0;
        int nb_chambre=0;
        String description="";
        Type_Hebergement type= null;
        String adresse=null;
        String mail=null;
        String num_tel=null;
        int etoiles = 0;
        int nb_atribut=0;
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                if (ligne.compareTo("  <entry>")==0) {
                    entry = true;
                }else if (ligne.compareTo("  </entry>")==0) {
                    entry = false;
                    if (description.length()>3){
                        description=description.substring(0,description.length()-3);
                    }
                    if (capacité==0){
                        capacité=nb_chambre*2; //on suppose que le nombre de personne par chambre est de deux personnes si il n'est pas renseigné
                    }
                    //Definition du prix par personne d'un hotel
                    if (etoiles==0){
                        prix=10+nb_atribut*2;
                    }else if (etoiles==1){
                        prix=25+nb_atribut*2;
                    }else if (etoiles==2){
                        prix=50+nb_atribut*2;
                    }else if (etoiles==3){
                        prix=75+nb_atribut*2;
                    }else if (etoiles==4){
                        prix=100+nb_atribut*2;
                    }else if (etoiles==5){
                        prix=150+nb_atribut*2;
                    }
                    //System.out.println(description);
                    //System.out.println(capacité);
                    //System.out.println(prix+" €");
                    try {
                        lexique.add(new Hebergement(libelle,prix,capacité,description,Type_Hebergement.HOTEL,adresse,mail,num_tel,etoiles));
                    }catch (PrixException e) {
                        System.out.println(e.getMessage());
                    }
                    prix =0;
                    capacité=0;
                    nb_chambre=0;
                    description="";
                    type= null;
                    adresse=null;
                    mail=null;
                    num_tel=null;
                    etoiles = 0;
                    nb_atribut=0;
                }else if (entry==true) {
                    if (ligne.length()>14 && ligne.substring(0,13).compareTo("    <name_fr>")==0) {
                        libelle = ligne.substring(13,ligne.length()-10);
                        //System.out.println(libelle);
                    }else if (ligne.length()>24 && ligne.substring(0,21).compareTo("      <address_line1>")==0){
                        adresse = ligne.substring(21,ligne.length()-16);
                        //System.out.println(adresse);
                    }else if (ligne.length()>12 && ligne.substring(0,11).compareTo("      <zip>")==0){
                        //System.out.println(ligne.substring(11,ligne.length()-6));
                    }else if (ligne.length()>13 && ligne.substring(0,12).compareTo("      <city>")==0){
                        //System.out.println(ligne.substring(12,ligne.length()-7));
                    }else if (ligne.length()>13 && ligne.substring(0,11).compareTo("    <phone>")==0){
                        num_tel= ligne.substring(11,ligne.length()-8);
                        //System.out.println(num_tel);
                    }else if (ligne.length()>13 && ligne.substring(0,11).compareTo("    <email>")==0){
                        mail= ligne.substring(11,ligne.length()-8);
                        //System.out.println(mail);
                    }else if (ligne.length()>17 && ligne.substring(0,15).compareTo("      <amenity>")==0){
                        description=description+ligne.substring(15,ligne.length()-10)+" - ";
                        nb_atribut++;
                    }else if (ligne.length()>25 && ligne.substring(0,20).compareTo("      <single_count>")==0) {
                        capacité = capacité + Integer.parseInt(ligne.substring(20, ligne.length() - 15));
                    }else if (ligne.length()>25 && ligne.substring(0,20).compareTo("      <double_count>")==0){
                        capacité = capacité + Integer.parseInt(ligne.substring(20, ligne.length() - 15))*2;
                    }else if (ligne.length()>25 && ligne.substring(0,20).compareTo("      <triple_count>")==0){
                        capacité = capacité + Integer.parseInt(ligne.substring(20, ligne.length() - 15))*3;
                    }else if (ligne.length()>25 && ligne.substring(0,19).compareTo("      <twins_count>")==0){
                        capacité = capacité + Integer.parseInt(ligne.substring(19, ligne.length() - 14))*4;
                    }else if (ligne.length()>25 && ligne.substring(0,18).compareTo("      <room_count>")==0){
                        nb_chambre=Integer.parseInt(ligne.substring(18, ligne.length() - 13));
                    }else if (ligne.length()>25 && ligne.substring(0,23).compareTo("      <standings_level>")==0){
                        if (ligne.compareTo("      <standings_level>Non-classé</standings_level>")!=0 && scanner.nextLine().compareTo("      <standings_level>Non-classé</standings_level>")!=0){
                            etoiles=Integer.parseInt(ligne.substring(23,24));
                            //System.out.println("etoiles "+etoiles);
                            scanner.nextLine();
                        }else {
                            etoiles=0;
                            //System.out.println("etoiles "+etoiles);
                        }

                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(lexique.get(0));
        return lexique;
    }
}
