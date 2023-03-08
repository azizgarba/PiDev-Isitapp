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
import tn.esprit.codefellaz.entities.cv;
import tn.esprit.codefellaz.entities.offre_de_travail;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author Khouloud DAIRA
 */
public class offre_de_travail_CRUD {
     Connection connexion;  
    
     public offre_de_travail_CRUD() {
        connexion = MyConnection.getInstance().getCnx();
    } 
    public void Ajouteroffre_de_travail(offre_de_travail u) throws SQLException {
        String req = "INSERT INTO `offres_de_travail` (`titre_offre_de_travail`,`type_offre_de_travail`,`salaire_offre_de_travail`,`specialite_offre_de_travail`,`entreprise_offre_de_travail`,`description_offre_de_travail`) "
                + "VALUES (?,?,?,?,?,?) ";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setString(1, u.getTitre_offre_de_travail());
         ps.setString(2,  u.getType_offre_de_travail());
        ps.setFloat(3, u.getSalaire_offre_de_travail());
  
    ps.setString(4, u.getSpecialite_offre_de_travail());
     ps.setInt(5, u.getEntreprise_offre_de_travail());
      ps.setString(6, u.getDescription_offre_de_travail());
   
   
        ps.executeUpdate();
    }

    
    public List<offre_de_travail> afficheroffre_de_travail() throws SQLException {

        List<offre_de_travail> listoffre_de_travail = new ArrayList<>();
         
       
        String req = "select * from offres_de_travail ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            offre_de_travail u = new offre_de_travail(rst.getInt("id_offre_de_travail")
                    , rst.getString("titre_offre_de_travail")
                    , rst.getString("type_offre_de_travail")
                    , rst.getFloat("salaire_offre_de_travail")
                    , rst.getString("specialite_offre_de_travail")
                     , rst.getInt("entreprise_offre_de_travail")
                     , rst.getString("description_offre_de_travail")
                 
            
            );
            listoffre_de_travail.add(u);
        }
        return listoffre_de_travail;
    }  
    
    
//    public List<offre_de_travail> afficher_entreprise1(offre_de_travail e) throws SQLException {
//
//        List<offre_de_travail> listentreprise = new ArrayList<>();
//         
//       
//        String req = "select * from offres_de_travail where id_representant=? ";
//   
//         PreparedStatement ps = connexion.prepareStatement(req);
//            ps.setInt(1, e.getId_offre_de_travail());
//            ResultSet rst = ps.executeQuery();
//
//        while (rst.next()) {
//            offre_de_travail u = new offre_de_travail(rst.getInt("id_entreprise")
//                    , rst.getInt("id_representant")
//                    , rst.getString("adresse")
//                    , rst.getInt("tel_entreprise")
//                    , rst.getString("description_entreprise")
//              , rst.getString("image")
//                 
//            
//            );
//            listentreprise.add(u);
//        }
//        return listentreprise;
//    } 
    
    
    
      public List<offre_de_travail> afficheroffre_de_travailBySpecialité() throws SQLException {

        List<offre_de_travail> listoffre_de_travail = new ArrayList<>();
         
       
        String req = "select * from offres_de_travail order by specialite_offre_de_travail ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            offre_de_travail u = new offre_de_travail(rst.getInt("id_offre_de_travail")
                    , rst.getString("titre_offre_de_travail")
                    , rst.getString("type_offre_de_travail")
                    , rst.getFloat("salaire_offre_de_travail")
                    , rst.getString("specialite_offre_de_travail")
                     , rst.getInt("entreprise_offre_de_travail")
                     , rst.getString("description_offre_de_travail")
                 
            
            );
            listoffre_de_travail.add(u);
        }
        return listoffre_de_travail;
    }  
    
    public void modifieroffre_de_travail(offre_de_travail u) throws SQLException, NoSuchAlgorithmException {
        String req = "UPDATE offres_de_travail SET "
                + "titre_offre_de_travail='"   +   u.getTitre_offre_de_travail()+"'"
                + ", type_offre_de_travail='"+  u.getType_offre_de_travail()+"'"
                + ", salaire_offre_de_travail='"+u.getSalaire_offre_de_travail()+"'"
                + ", specialite_offre_de_travail='"+u.getSpecialite_offre_de_travail()+"'"
                + ", entreprise_offre_de_travail='"+u.getEntreprise_offre_de_travail()+"'"
      
                + ", description_offre_de_travail='"+   u.getDescription_offre_de_travail()+"' where id_offre_de_travail  = "+u.getId_offre_de_travail()+"";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    }   
    
           public void Supprimeroffre_de_travail(offre_de_travail u) throws SQLException {

        String req = "DELETE FROM offres_de_travail WHERE id_offre_de_travail =?";
        try {
            PreparedStatement ps = connexion.prepareStatement(req);
            ps.setInt(1, u.getId_offre_de_travail());
            ps.executeUpdate();
        } catch (SQLException ex) {
        }
    }
    
    


    }




