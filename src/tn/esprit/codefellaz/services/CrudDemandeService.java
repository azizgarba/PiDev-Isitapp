
package tn.esprit.codefellaz.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.codefellaz.entities.CategorieService;
import tn.esprit.codefellaz.entities.DemandeService;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author user
 *
 *
 * //////////////////////////ajout avec metier
 * /////////////////////////////////////////
 *
 */
public class CrudDemandeService implements InterfaceDemandeService {

    Connection cnx;

    public CrudDemandeService() {

        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouterDemandeService(DemandeService demandeService) {

        try {
            String requete2 = "insert into demandes_services (nom_demande,budget,description_demande,date_limite,client_proprietaire,priorite) values (?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setString(1, demandeService.getNomDemande());
            pst.setFloat(2, demandeService.getBudget());
            pst.setString(3, demandeService.getDescription());
            pst.setString(4, demandeService.getDateLimite());
            pst.setInt(5, demandeService.getClientProprietaire());
            pst.setDouble(6, demandeService.calculatePriorite(demandeService.getDateLimite(), demandeService.getBudget()));

            pst.executeUpdate();
            System.out.println("Categorie ajouter");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public void modifierDemandeService(DemandeService demandeService) {

        try {
            String requete = "UPDATE demandes_services SET budget = ? , description_demande = ? , date_limite = ? , priorite = ? WHERE id_demande = ?";

            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setFloat(1, demandeService.getBudget());
            pst.setString(2, demandeService.getDescription());
            pst.setString(3, demandeService.getDateLimite());
            pst.setDouble(4, demandeService.calculatePriorite(demandeService.getDateLimite(), demandeService.getBudget()));
            pst.setInt(5, demandeService.getIdDemande());
            pst.executeUpdate();
            System.out.println("demande modifie");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
    
    @Override
    public void modifierDemandeService(int id, float budget , String description , String date ){

        try {
            String requete = "UPDATE demandes_services SET budget = ? , description_demande = ? , date_limite = ?  WHERE id_demande = ?";

            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setFloat(1, budget);
            pst.setString(2, description);
            pst.setString(3,date);
            pst.setInt(4, id);
            pst.executeUpdate();
            System.out.println("demande modifie");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public void supprimerDemandeService(int idDemandeService) {
try {
            String requete = "DELETE from demandes_services WHERE id_demande = ?" ;
            
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,idDemandeService);
            
            pst.executeUpdate();
            System.out.println("demande supprime");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }    }

    @Override
    public List<DemandeService> afficherDemandeService(){
  List<DemandeService> myList = new  ArrayList<> ();

        try {
            String requete3 = "select * from demandes_services " ;
            Statement st = cnx.createStatement() ;
            ResultSet rs = st.executeQuery(requete3);
            while(rs.next())
            {
                DemandeService dm = new DemandeService() ;
                //CategorieService categorieService = new CategorieService() ;
                dm.setIdDemande(rs.getInt(1));
                dm.setNomDemande(rs.getString(2));
                dm.setBudget(rs.getFloat(3));
                dm.setDescription(rs.getString(4));
                dm.setDateLimite(rs.getString(5));
                dm.setClientProprietaire(rs.getInt(6));
                dm.setPriorite(rs.getString(5), rs.getFloat(3));
                
                
              
                myList.add(dm) ; 
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    
        return myList ;
    }    

    @Override
    public List<DemandeService> afficherUneSeuleDemandeServices(int id) {
List<DemandeService> myList = new  ArrayList<> ();

        try {
            
            
            
           
            
            
           
           PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM demandes_services WHERE id_demande = ?");
            stmt.setInt(1, id);
              ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                DemandeService dm = new DemandeService() ;
                //CategorieService categorieService = new CategorieService() ;
                dm.setIdDemande(rs.getInt(1));
                dm.setNomDemande(rs.getString(2));
                dm.setBudget(rs.getFloat(3));
                dm.setDescription(rs.getString(4));
                dm.setDateLimite(rs.getString(5));
                dm.setClientProprietaire(rs.getInt(6));
                dm.setPriorite(rs.getString(5), rs.getFloat(3));
                
                
              
                myList.add(dm) ; 
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    
        return myList ;    }
    
}