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
import tn.esprit.codefellaz.entities.Evenements;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author MAG-PC
 */
public class Evenements_service implements interfaceEvenements {

    Connection cnx1;

    public Evenements_service() {
        cnx1 = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter_Evenement(Evenements e) {
        try {
            Statement ste = cnx1.createStatement();
            String req = "INSERT INTO `evenements`(`id_evenement`, `nom_evenement`, `logo_evenement`, `desc_evenement`, `emplacement_evenement`, `date_evenement`, `nbr_perso_evenement`, `type_evenement`, `id_representant`) VALUES ('" + e.getId_event() + "','" + e.getName_event() + "','" + e.getLogo_event() + "','" + e.getDesc_event() + "','" + e.getEmplacement_event() + "','" + e.getDate_event() + "','" + e.getNbr_perso_event() + "','" + e.getType_event() + "','" + e.getId_representant() + "')";
            ste.executeUpdate(req);
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier_Evenement(Evenements e) {
        try {
            Statement ste = cnx1.createStatement();
            String req = "UPDATE `evenements` SET `nom_evenement`='"+e.getName_event()+"',`logo_evenement`='"+e.getLogo_event()+"',`desc_evenement`='"+e.getDesc_event()+"',`emplacement_evenement`='"+e.getEmplacement_event()+"',`date_evenement`='"+e.getDate_event()+"',`nbr_perso_evenement`='"+e.getNbr_perso_event()+"',`type_evenement`='"+e.getType_event()+"',`id_representant`='"+e.getId_representant()+"' WHERE `id_evenement` = '"+e.getId_event()+"'";
            ste.executeUpdate(req);
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Evenements> afficher_Evenement() {
        List<Evenements> event_list = new ArrayList<>();
        try {
            Statement ste = cnx1.createStatement();
            String req = "SELECT * FROM `evenements`";
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                Evenements event = new Evenements();
                event.setId_event(res.getInt(1));
                event.setName_event(res.getString(2));
                event.setLogo_event(res.getString(3));
                event.setDesc_event(res.getString(4));
                event.setEmplacement_event(res.getString(5));
                event.setDate_event(res.getString(6));
                event.setNbr_perso_event(res.getInt(7));
                event.setType_event(res.getInt(8));
                event.setId_representant(res.getInt(9));
                event_list.add(event);
            }
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return event_list;

    }

    @Override
    public void supprimer_Evenement(int id_event) {
         try {
            Statement ste = cnx1.createStatement();
            String req = "DELETE FROM `evenements` WHERE `id_evenement`= '"+id_event+"'";
            ste.executeUpdate(req);
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void ajouter_Evenement2(Evenements e) {
        try {
            Statement ste = cnx1.createStatement();
            String req = "INSERT INTO `evenements`(`nom_evenement`, `logo_evenement`, `desc_evenement`, `emplacement_evenement`, `date_evenement`, `nbr_perso_evenement`, `type_evenement`, `id_representant`) VALUES ('" + e.getName_event() + "','" + e.getLogo_event() + "','" + e.getDesc_event() + "','" + e.getEmplacement_event() + "','" + e.getDate_event() + "','" + e.getNbr_perso_event() + "','" + e.getType_event() + "','" + e.getId_representant() + "')";
            ste.executeUpdate(req);
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Evenements> afficher_Evenement2(String nom) {
         List<Evenements> event_list = new ArrayList<>();
        try {
            Statement ste = cnx1.createStatement();
            String req = "SELECT `id_utilisateur` FROM `utilisateurs` WHERE `nom_utilisateur` = '"+nom+"'";
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                Evenements event = new Evenements();
                event.setId_representant(res.getInt("id_utilisateur"));
                event_list.add(event);
            }
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return event_list;
    }

    @Override
    public List<Evenements> search_event(String critere) {
        List<Evenements> event_list = new ArrayList<>();
        try {
            Statement ste = cnx1.createStatement();
            String req = "SELECT * FROM `evenements` WHERE `emplacement_evenement` = '"+critere+"' OR `nom_evenement` = '"+critere+"'";
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                Evenements event = new Evenements();
                event.setId_event(res.getInt(1));
                event.setName_event(res.getString(2));
                event.setLogo_event(res.getString(3));
                event.setDesc_event(res.getString(4));
                event.setEmplacement_event(res.getString(5));
                event.setDate_event(res.getString(6));
                event.setNbr_perso_event(res.getInt(7));
                event.setType_event(res.getInt(8));
                event.setId_representant(res.getInt(9));
                event_list.add(event);
            }
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return event_list;
    }

    @Override
    public List<Evenements> sort_event(int critere) {
        List<Evenements> event_list = new ArrayList<>();
        try {
            Statement ste = cnx1.createStatement();
            String req = "SELECT * FROM `evenements` WHERE `type_evenement` = '"+critere+"'";
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                Evenements event = new Evenements();
                event.setId_event(res.getInt(1));
                event.setName_event(res.getString(2));
                event.setLogo_event(res.getString(3));
                event.setDesc_event(res.getString(4));
                event.setEmplacement_event(res.getString(5));
                event.setDate_event(res.getString(6));
                event.setNbr_perso_event(res.getInt(7));
                event.setType_event(res.getInt(8));
                event.setId_representant(res.getInt(9));
                event_list.add(event);
            }
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return event_list;
    }

}
