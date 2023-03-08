/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import tn.esprit.codefellaz.entities.Taches;
import java.util.List;

/**
 *
 * @author myriam-PC
 */
public interface InterfaceTache {
    public void AjouterTache(Taches T);
    public void ModifierTache(Taches T);
    public void SupprimerTache(int id_tache);
    public List<Taches> AfficherTaches();
}
