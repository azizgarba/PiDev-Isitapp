/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.codefellaz.entities.Invitations_participation;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author MAG-PC
 */
public class InvitationParti_service implements InterfaceInvitations_parti{
    Connection cnx1;
    public InvitationParti_service(){
        cnx1=MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter_Invitation(Invitations_participation i) {
        try {
            Statement ste = cnx1.createStatement();
            String req = "INSERT INTO `invitations_participation`(`id_invitation_parti`, `id_evenement`, `id_freelancer`, `type_invi_parti` ) VALUES ('"+i.getId_invi()+"','"+i.getId_event()+"','"+i.getId_freelancer()+"','"+i.getType()+"')";
            ste.executeUpdate(req);
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier_Invitation(Invitations_participation i) {
         try {
            Statement ste = cnx1.createStatement();
            String req = "UPDATE `invitations_participation` SET `id_evenement`='"+i.getId_event()+"',`id_freelancer`='"+i.getId_freelancer()+"',`type_invi_parti`='"+i.getType()+"' WHERE `id_invitation_parti` = '"+i.getId_invi()+"'";
            ste.executeUpdate(req);
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Invitations_participation> afficher_Invitation() {
        List<Invitations_participation> invi_list = new ArrayList<>();
        try {
            Statement ste = cnx1.createStatement();
            String req = "SELECT * FROM `invitations_participation`";
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                Invitations_participation invi = new Invitations_participation();
                invi.setId_invi(res.getInt(1));
                invi.setId_event(res.getInt(2));
                invi.setId_freelancer(res.getInt(3));
                invi.setType(res.getString(4));
                invi_list.add(invi);
            }
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return invi_list;
    }

    @Override
    public void supprimer_Invitation(int id_invi) {
         try {
            Statement ste = cnx1.createStatement();
            String req = "DELETE FROM `invitations_participation` WHERE `id_invitation_parti`= '"+id_invi+"'";
            ste.executeUpdate(req);
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
