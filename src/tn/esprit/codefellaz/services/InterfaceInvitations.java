/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.Invitations;

/**
 *
 * @author MAG-PC
 */
public interface InterfaceInvitations {
     public void ajouter_Invitation(Invitations i);
    public void modifier_Invitation(Invitations i);
    public List<Invitations> afficher_Invitation();
    public void supprimer_Invitation(int id_event);
    
}
