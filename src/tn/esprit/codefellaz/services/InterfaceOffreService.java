/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.OffreService;

/**
 *
 * @author user
 */
public interface InterfaceOffreService {

    public void ajouterOffreService(OffreService offreService);

    public void modifierOffreService(int idOffreService, float prix, String descriptionOffreService, String pays, String imagePaths);

    public void supprimerOffreService(int id);

    public List<OffreService> afficherOffreService();

    public List<OffreService> afficherOffreService(int id);

    public List<OffreService> afficherUnSeulOffredeServices(int id);

    public void modifierNote(int idOffreService, float note, CrudAvis evaluer);

    public int recupererNombreCommande(int idOffreService);

    public void modifierNombreCommandeDate(int idOffreService, int nbCommande, String date);

}
