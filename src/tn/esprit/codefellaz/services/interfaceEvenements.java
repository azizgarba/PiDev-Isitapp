/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.Evenements;

/**
 *
 * @author MAG-PC
 */
public interface interfaceEvenements {
     public void ajouter_Evenement(Evenements e);
    public void modifier_Evenement(Evenements e);
    public List<Evenements> afficher_Evenement();
    public void supprimer_Evenement(int id_event);
    public void ajouter_Evenement2(Evenements e);
    public List<Evenements> afficher_Evenement2(String nom);
    public List<Evenements> search_event(String critere);
    public List<Evenements> sort_event(int critere);
}
