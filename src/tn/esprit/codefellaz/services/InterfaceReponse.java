/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;
import java.util.List;
import tn.esprit.codefellaz.entities.Reponse;

/**
 *
 * @author Meriem
 */
public interface InterfaceReponse {
     public void ajouterReponse(Reponse reponse);
    public void modifierReponse(Reponse reponse);
    public void supprimerReponse(Reponse reponse);
    public List<Reponse> afficherReponses();
    

}
