/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;

import tn.esprit.codefellaz.entities.offre_de_travail;

/**
 *
 * @author Khouloud DAIRA
 */
public interface interface_offre_de_travail {
    
    public void ajouter_offre_de_travail ();
    public void ajouter_offre_de_travail2(offre_de_travail off) ;
    public List<offre_de_travail> afficher_offre_de_travail();
    public void SupprimerOffreById (int id_offre);
    public void modifier(offre_de_travail off);
    
    
    
    
    
   
    
    
    
    
    
    
    
    
}
