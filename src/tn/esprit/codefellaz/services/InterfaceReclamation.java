/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.Reclamation;


/**
 *
 * @author Meriem
 */
public interface InterfaceReclamation {
    
    public void ajouterReclamation(Reclamation reclamation);
    public void modifierReclamation(Reclamation reclamation);
    public void supprimerReclamation(Reclamation reclamation);
    public List<Reclamation> afficherReclamations();
    
}
