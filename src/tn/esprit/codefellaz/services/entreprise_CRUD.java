/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.entities.cv;
import tn.esprit.codefellaz.entities.entreprise;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author Khouloud DAIRA
 */
public class entreprise_CRUD  {
    
    
  Connection connexion;  
    
     public entreprise_CRUD() {
        connexion = MyConnection.getInstance().getCnx();
    } 
       
     public void Ajouterentreprise(entreprise u) throws SQLException {
        String req = "INSERT INTO `entreprises` (`id_representant`,`adresse`,`tel_entreprise`,`description_entreprise`,`image`) "
                + "VALUES (?,?,?,?,?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setInt(1, u.getId_representant());
         ps.setString(2,  u.getAdresse());
        ps.setInt(3, u.getTel_entreprise());
  
    ps.setString(4, u.getDescription_entreprise());
   ps.setString(5, u.getImage());
   
   
        ps.executeUpdate();
    }

    
    public List<entreprise> afficher_entreprise() throws SQLException {

        List<entreprise> listentreprise = new ArrayList<>();
         
       
        String req = "select * from entreprises ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            entreprise u = new entreprise(rst.getInt("id_entreprise")
                    , rst.getInt("id_representant")
                    , rst.getString("adresse")
                    , rst.getInt("tel_entreprise")
                    , rst.getString("description_entreprise")
              , rst.getString("image")
                 
            
            );
            listentreprise.add(u);
        }
        return listentreprise;
    }  
    
      public List<entreprise> afficher_entreprise1(int id) throws SQLException {

        List<entreprise> listentreprise = new ArrayList<>();
         
       
        String req = "select * from entreprises where id_representant=? ";
        PreparedStatement ps = connexion.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();

        while (rst.next()) {
            entreprise u = new entreprise(rst.getInt("id_entreprise")
                    , rst.getInt("id_representant")
                    , rst.getString("adresse")
                    , rst.getInt("tel_entreprise")
                    , rst.getString("description_entreprise")
              , rst.getString("image")
                 
            
            );
            listentreprise.add(u);
        }
        return listentreprise;
    } 
    
       public List<entreprise> afficher_entrepriseByadresse() throws SQLException {

        List<entreprise> listentreprise = new ArrayList<>();
         
       
        String req = "select * from entreprises order by adresse ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            entreprise u = new entreprise(rst.getInt("id_entreprise")
                    , rst.getInt("id_representant")
                    , rst.getString("adresse")
                    , rst.getInt("tel_entreprise")
                    , rst.getString("description_entreprise")
              , rst.getString("image")
                 
            
            );
            listentreprise.add(u);
        }
        return listentreprise;
    }  
    public void modifierentreprise(entreprise u) throws SQLException, NoSuchAlgorithmException {
        String req = "UPDATE entreprises SET "
                + "id_representant ='"   +   u.getId_entreprise()+"'"
                + ", adresse='"+  u.getAdresse()+"'"
                + ", tel_entreprise='"+u.getTel_entreprise()+"'"
              + ", description_entreprise='"+u.getDescription_entreprise()+"'"
      
                + ", image ='"+   u.getImage()+"' where id_entreprise  = "+u.getId_entreprise()+"";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    }   
    
           public void Supprimerentreprise(entreprise u) throws SQLException {

        String req = "DELETE FROM entreprises WHERE id_entreprise=?";
        try {
            PreparedStatement ps = connexion.prepareStatement(req);
            ps.setInt(1, u.getId_entreprise());
            ps.executeUpdate();
        } catch (SQLException ex) {
        }
    }
    
       
     
    }