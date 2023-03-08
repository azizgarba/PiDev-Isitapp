/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import tn.esprit.codefellaz.entities.Projets;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author myriam-PC
 */
public interface InterfaceProjet {
    public void AjouterProjet(Projets projet);
    public void ModifierProjet(Projets projet);
    public void SupprimerProjet(int id_projet);
    public void SupprimerProjet2(String nom_projet);
    public List<Projets> AfficherProjets();
    public ObservableList<Projets> afficherProjets2(); // a ajouter fel main

}
