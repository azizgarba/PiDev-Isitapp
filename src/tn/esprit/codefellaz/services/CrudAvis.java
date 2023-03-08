/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.codefellaz.entities.Avis;
import tn.esprit.codefellaz.entities.OffreService;
import tn.esprit.codefellaz.utils.MyConnection;


/**
 *
 * @author user
 */
public class CrudAvis implements InterfaceAvis{
     Connection cnx ;
        public CrudAvis() {

        cnx = MyConnection.getInstance().getCnx();
    }

     
    @Override
    public void ajouterAvis(Avis avis) {

          try {
            String requete2 = "insert into avis (client,nb_etoiles,id_service) values (?,?,?)" ;
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setInt(1, avis.getClient());
            pst.setInt(2,avis.getNbEtoiles());
            pst.setInt(3, avis.getIdService());
            pst.executeUpdate();
            System.out.println("avis ajouter");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    
    }

    @Override
    public void modifierAvis(Avis avis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimerAvis(int idAvis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Avis> afficherAvis() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
      @Override
    public Float CalculeNote(int idOffreService) {
float note = 0 ; 
int i = 1 ;
        try {
               
           
           PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM avis WHERE id_service = ?");
            stmt.setInt(1, idOffreService);
              ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {    i ++ ;
                note = note +  rs.getInt(3);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    
        return (note/i) ;    }

   
    
    
    
}

    
    

