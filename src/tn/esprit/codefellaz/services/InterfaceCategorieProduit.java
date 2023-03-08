/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;
import java.util.List;
import tn.esprit.codefellaz.entities.CategorieProduit ;
/**
 *
 * @author ASUS
 */
public interface InterfaceCategorieProduit {
    public void ajouterCategorieProduit(CategorieProduit cp);
    public void ajouterCategorieProduit2(CategorieProduit cp);
    public void modifierCategorieProduit(CategorieProduit cp);
    public void supprimerCategorieProduit(int id);
    public List<CategorieProduit> afficherCategorieProduit();
}
