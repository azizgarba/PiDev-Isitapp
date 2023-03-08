/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.entreprise;

/**
 *
 * @author Khouloud DAIRA
 */
public interface Interface_entreprise {
    public void ajouter_entreprise ();
    public void ajouter_entreprise2(entreprise ent);
    public List<entreprise> afficher_entreprise();
    public void Supprimer_entreprise_ById (int id_entreprise);
    public void modifier(entreprise ent);
}
