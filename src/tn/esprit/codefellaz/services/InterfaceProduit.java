/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.Produit ;

/**
 *
 * @author ASUS
 */
public interface InterfaceProduit {
    
    public void ajouterProduit(Produit p);
    public void ajouterProduit2 (Produit p);
    public void modifierProduit(Produit p);
    public void supprimerProduit(int id);
    public List<Produit> afficherProduit();
}
