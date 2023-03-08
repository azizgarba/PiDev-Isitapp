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
import java.util.List;
import tn.esprit.codefellaz.entities.commande;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author user
 */
public class CrudCommande implements InterfaceCommande{

    
       Connection cnx ; 
    
    
    public CrudCommande(){
         cnx = MyConnection.getInstance().getCnx();
    }
    
    @Override
    public void ajouterCommande(commande commande) {
        
        
        
          try {
            String requete2 = "insert into commandes (id_client,id_service,date_commande) values (?,?,?)" ;
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setInt(1, commande.getIdClient());
            pst.setInt(2, commande.getIdService());
            pst.setString(3,commande.getDateCommande());
            pst.executeUpdate();
            System.out.println("commmande ajoute");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }


    }

    @Override
    public void supprimerCommande(int idCommande) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<commande> afficherCategorieService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
//      @Override
//    public Float CalculeNote(int idOffreService) {
//float nb_commande =  ; 
//int i = 1 ;
//        try {
//               
//           
//           PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM avis WHERE id_service = ?");
//            stmt.setInt(1, idOffreService);
//              ResultSet rs = stmt.executeQuery();
//            while(rs.next())
//            {    i ++ ;
//                note = note +  rs.getInt(3);
//            }
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());        }
//    
//        return (note/i) ;    }

    
    
    
    
}
