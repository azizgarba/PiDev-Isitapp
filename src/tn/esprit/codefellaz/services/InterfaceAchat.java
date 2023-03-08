/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;
import java.util.List;
import tn.esprit.codefellaz.entities.Achat ;
/**
 *
 * @author ASUS
 */
public interface InterfaceAchat {
    public void ajouterAchat(Achat a);
    public void ajouterAchat2(Achat a);
    public void modifierAchat(Achat a);
    public void supprimerAchat(int id);
    public List<Achat> afficherAchat();
    
}
