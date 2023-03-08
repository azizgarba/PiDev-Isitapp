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
import tn.esprit.codefellaz.entities.DemandeService;
import tn.esprit.codefellaz.entities.OffreService;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author user
 */
public class CrudOffreService implements InterfaceOffreService{

    
     Connection cnx;

    public CrudOffreService() {

        cnx = MyConnection.getInstance().getCnx();
    }

    
    
    @Override
    public void ajouterOffreService(OffreService offreService) {



        
        try {
            String requete2 = "insert into offres_services (categorie_service,freelancer_proprietaire,prix, description_offre_service,note,pays,derniere_commande,date_depuis_derniere_commande,image_offre_service, nb_commande_passee) values (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setInt(1, offreService.getCategorie());
            pst.setInt(2, offreService.getProprietaireOffre());
            pst.setFloat(3, offreService.getPrix());
            pst.setString(4, offreService.getDescriptionOffreService());
            pst.setFloat(5, 0);
            pst.setString(6, offreService.getPays());
            pst.setNull(7, java.sql.Types.DATE);
            pst.setInt(8, -1);
            pst.setString(9, offreService.getImagePaths());
            pst.setInt(10, 0);
            pst.executeUpdate();
            System.out.println("Categorie ajouter");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        
        
        
      }

    @Override
    public void modifierOffreService( int idOffreService, float prix, String descriptionOffreService, String pays, String imagePaths) {

  try {
            String requete = "UPDATE offres_services SET prix = ? ,description_offre_service = ? , pays= ? , image_offre_service = ? WHERE id_offre_service = ?";

            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setFloat(1,prix);
            pst.setString(2, descriptionOffreService);
            pst.setString(3, pays);
            pst.setString(4, imagePaths);
            pst.setInt(5, idOffreService );
            pst.executeUpdate();
            System.out.println("offre modifie");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
    
    
       @Override
    public void modifierNote( int idOffreService, float note,CrudAvis evaluer) {

   
  try {
            String requete = "UPDATE offres_services SET note = ?  WHERE id_offre_service = ?";

            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setFloat(1, evaluer.CalculeNote(idOffreService));
            pst.setFloat(2,idOffreService);
            
           
            pst.executeUpdate();
            System.out.println("offre modifie");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public void supprimerOffreService(int id) {
        
        
try {
            String requete = "DELETE from offres_services WHERE id_offre_service = ?" ;
            
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,id);
            
            pst.executeUpdate();
            System.out.println("offre supprime");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }   
        
        
    }

    @Override
    public List<OffreService> afficherOffreService() {

List<OffreService> myList = new  ArrayList<> ();

        try {
            
            
            
           
            
            
           
           String requete3 = "select * from offres_services " ;
            Statement st = cnx.createStatement() ;
            ResultSet rs = st.executeQuery(requete3);
            while(rs.next())
            {
                OffreService dm = new OffreService() ;
                //CategorieService categorieService = new CategorieService() ;
              
                
                
                
                dm.setIdOffreService(rs.getInt(1));
                dm.setCategorie(rs.getInt(2));
                dm.setProprietaireOffre(rs.getInt(3));
                dm.setPrix(rs.getFloat(4));
                dm.setDescriptionOffreService(rs.getString(5));
                dm.setNote(rs.getFloat(6));
                dm.setPays(rs.getString(7));
                dm.setDerniereCommande(rs.getString(8));
                dm.setDateDepuisDerniereCommande(rs.getInt(9));
                dm.setImagePaths(rs.getString(10));
                dm.setNbCommandePasse(rs.getInt(11));
                
                
                
              
                myList.add(dm) ; 
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    
        return myList ;        }
    
    
    
      @Override
    public List<OffreService> afficherOffreService(int id) {

List<OffreService> myList = new  ArrayList<> ();

        try {
            
            
            
           
           String requete3 = "select * from offres_services where freelancer_proprietaire=? " ;
           
           PreparedStatement pst = cnx.prepareStatement(requete3);
            pst.setInt(1, id);
            pst.executeQuery();
            ResultSet rs =  pst.executeQuery();
            while(rs.next())
            {
                OffreService dm = new OffreService() ;
                //CategorieService categorieService = new CategorieService() ;
              
                
                
                
                dm.setIdOffreService(rs.getInt(1));
                dm.setCategorie(rs.getInt(2));
                dm.setProprietaireOffre(rs.getInt(3));
                dm.setPrix(rs.getFloat(4));
                dm.setDescriptionOffreService(rs.getString(5));
                dm.setNote(rs.getFloat(6));
                dm.setPays(rs.getString(7));
                dm.setDerniereCommande(rs.getString(8));
                dm.setDateDepuisDerniereCommande(rs.getInt(9));
                dm.setImagePaths(rs.getString(10));
                dm.setNbCommandePasse(rs.getInt(11));
                
                
                
              
                myList.add(dm) ; 
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    
        return myList ;        }
    
    
    @Override
    public List<OffreService> afficherUnSeulOffredeServices(int id) {
List<OffreService> myList = new  ArrayList<> ();

        try {
               
           
           PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM offres_services WHERE id_offre_service = ?");
            stmt.setInt(1, id);
              ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                OffreService dm = new OffreService() ;
                //CategorieService categorieService = new CategorieService() ;
              
                
                
                
                dm.setIdOffreService(rs.getInt(1));
                dm.setCategorie(rs.getInt(2));
                dm.setProprietaireOffre(rs.getInt(3));
                dm.setPrix(rs.getFloat(4));
                dm.setDescriptionOffreService(rs.getString(5));
                dm.setNote(rs.getFloat(6));
                dm.setPays(rs.getString(7));
                dm.setDerniereCommande(rs.getString(8));
                dm.setDateDepuisDerniereCommande(rs.getInt(9));
                dm.setImagePaths(rs.getString(10));
                dm.setNbCommandePasse(rs.getInt(11));
                
                
                
              
                myList.add(dm) ; 
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    
        return myList ;    }
    
    
    
     public void modifierCommande( int idOffreService, float note,CrudAvis evaluer) {

   
  try {
            String requete = "UPDATE offres_services SET note = ?  WHERE id_offre_service = ?";

            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setFloat(1, evaluer.CalculeNote(idOffreService));
            pst.setFloat(2,idOffreService);
            
           
            pst.executeUpdate();
            System.out.println("offre modifie");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
    
    
     
     
       @Override
    public int  recupererNombreCommande(int idOffreService) {

        int nb = 0 ;
        try {
               
           
           PreparedStatement stmt = cnx.prepareStatement("SELECT nb_commande_passee FROM offres_services WHERE id_offre_service = ?");
            stmt.setInt(1, idOffreService);
              ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {  
                nb = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    
        return nb ;    }
    
    
   @Override
     public void modifierNombreCommandeDate( int idOffreService, int  nbCommande, String date) {

   
  try {
            String requete = "UPDATE offres_services SET nb_commande_passee = ? , derniere_commande = ?  WHERE id_offre_service = ?";

            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,nbCommande);
            pst.setString(2,date);
            pst.setInt(3,idOffreService);
            pst.executeUpdate();
            System.out.println("offre modifie");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    
}
