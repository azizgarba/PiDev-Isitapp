/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.DemandeService;

/**
 *
 * @author user
 */
public interface InterfaceDemandeService {
    
    public void ajouterDemandeService(DemandeService demandeService) ;
    public void modifierDemandeService(DemandeService demandeService) ;
    public void modifierDemandeService(int id, float budget , String description , String date ) ;
    public void supprimerDemandeService(int idDemandeService) ; 
    public List<DemandeService> afficherDemandeService() ;
    public List<DemandeService> afficherUneSeuleDemandeServices(int id);
    
}
