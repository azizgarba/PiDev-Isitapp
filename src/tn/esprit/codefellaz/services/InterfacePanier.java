/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;
import java.util.List;
import tn.esprit.codefellaz.entities.Panier ;
/**
 *
 * @author ASUS
 */
public interface InterfacePanier {
    public void ajouterPanier(Panier p);
    public void ajouterPanier2(Panier p);
    public void modifierPanier(Panier p);
    public void supprimerPanier(int id);
    public List<Panier> afficherPanier();
}
