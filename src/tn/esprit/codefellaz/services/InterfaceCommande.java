/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.commande;

/**
 *
 * @author user
 */
public interface InterfaceCommande {
    
        public void ajouterCommande(commande commande) ; 
   //public void modifierNomCategorieService(int idCategorieService, String nomCategorieService);
    public void supprimerCommande (int idCommande) ;
    public  List<commande> afficherCategorieService() ;
    
    
    
}
