/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.Invitations_participation;

/**
 *
 * @author MAG-PC
 */
public interface InterfaceInvitations_parti {
     public void ajouter_Invitation(Invitations_participation i);
    public void modifier_Invitation(Invitations_participation i);
    public List<Invitations_participation> afficher_Invitation();
    public void supprimer_Invitation(int id_invi);
    
}
