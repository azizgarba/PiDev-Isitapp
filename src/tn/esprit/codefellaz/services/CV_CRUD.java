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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.esprit.codefellaz.entities.cv;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author Khouloud DAIRA
 */
public class CV_CRUD {
    Connection connexion;  
    
     public CV_CRUD() {
        connexion = MyConnection.getInstance().getCnx();
    } 
    
 public void Ajoutercv(cv u) throws SQLException {
        String req = "INSERT INTO `cvs` (`bio_cv`,`diplome`,`annee_diplome`,`institut`,`specialite`,`id_freelancer`,`image`) "
            + "VALUES (?,?,?,?,?,?,?) ";

        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setString(1, u.getBio_cv());
         ps.setString(2,  u.getDiplome());
        ps.setInt(3, u.getAnnee_diplome());
  
    ps.setString(4, u.getInstitut());
     ps.setString(5, u.getSpecialite());
   ps.setInt(6, u.getId_freelance());
    ps.setString(7, u.getImage());
        ps.executeUpdate();
    }

    
    public List<cv> afficher_CV() throws SQLException {

        List<cv> listcv = new ArrayList<>();
         
       
        String req = "select * from cvs ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            cv u = new cv(rst.getInt("id_cv")
                    , rst.getString("bio_cv")
                    , rst.getString("diplome")
                    , rst.getInt("annee_diplome")
                    , rst.getString("institut")
                     , rst.getString("specialite")
                     , rst.getInt("id_freelancer")
                     ,rst.getString("image")
                 
            
            );
            listcv.add(u);
        }
        return listcv;
    }  
       public List<cv> afficher_CVByInstitut() throws SQLException {

        List<cv> listcv = new ArrayList<>();
         
       
        String req = "select * from cvs order by institut ";
        Statement stm = connexion.createStatement();
        ResultSet rst = stm.executeQuery(req);

        while (rst.next()) {
            cv u = new cv(rst.getInt("id_cv")
                    , rst.getString("bio_cv")
                    , rst.getString("diplome")
                    , rst.getInt("annee_diplome")
                    , rst.getString("institut")
                     , rst.getString("specialite")
                     , rst.getInt("id_freelancer")
                     ,rst.getString("image")
                 
            
            );
            listcv.add(u);
        }
        return listcv;
    }  
    
    public void modifiercv(cv u) throws SQLException, NoSuchAlgorithmException {
        String req = "UPDATE cvs SET "
                + "bio_cv ='"   +   u.getBio_cv()+"'"
                + ", diplome='"+  u.getDiplome()+"'"
                + ", annee_diplome='"+u.getAnnee_diplome()+"'"
                + ", institut='"+u.getInstitut()+"'"
      + ", specialite='"+u.getSpecialite()+"'"
                + ", image ='"+   u.getImage()+"' where id_cv  = "+u.getId_cv()+"";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    }   
    
           public void Supprimercv(cv u) throws SQLException {

        String req = "DELETE FROM cvs WHERE id_cv =?";
        try {
            PreparedStatement ps = connexion.prepareStatement(req);
            ps.setInt(1, u.getId_cv());
            ps.executeUpdate();
        } catch (SQLException ex) {
        }
    }
    
    


    }





