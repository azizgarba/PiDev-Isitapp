/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.Avis;

/**
 *
 * @author user
 */
public interface InterfaceAvis {
    
    public void ajouterAvis(Avis avis) ; 
    public void modifierAvis (Avis avis) ;
    public void supprimerAvis (int idAvis) ;
    
    public List<Avis> afficherAvis() ; 
     public Float CalculeNote(int id) ;
    
}
