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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.codefellaz.utils.MyConnection;
import tn.esprit.codefellaz.entities.Reclamation;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author Meriem
 */

public class ReclamationCRUD implements InterfaceReclamation{

    Connection cnx1;
    public ReclamationCRUD() {
        cnx1=MyConnection.getInstance().getCnx();
    }
    
  
    @Override
    public void ajouterReclamation(Reclamation Reclamation){
        try {
            
            String requete="INSERT INTO `reclamations`(`id_reclamation`, `id_utilisateur`, `object_reclamation`, `description_reclamation`,`categorie_reclamation`,`etat_reclamation`) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst2=cnx1.prepareStatement(requete);
            pst2.setInt(1, Reclamation.getId_Reclamation());
            pst2.setInt(2, Reclamation.getId_user());
            pst2.setString(3, Reclamation.getObjet_Reclamation());
            pst2.setString(4, Reclamation.getDescription_Reclamation());
            pst2.setString(5, Reclamation.getCategorie_Reclamation());
            pst2.setInt(6, Reclamation.getEtat_Reclamation());
            
           
            pst2.executeUpdate();
            System.out.println("Reclamation ajoutée avec succès");//}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    }
    
     @Override
    public void supprimerReclamation(Reclamation Reclamation){
        try {
            String requete="DELETE FROM `reclamations` WHERE `id_reclamation`=?";
            PreparedStatement pst=cnx1.prepareStatement(requete);
            pst.setInt(1, Reclamation.getId_Reclamation());
            pst.executeUpdate();
            System.out.println("Reclamation supprimée avec succès");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

     @Override
    public void modifierReclamation(Reclamation Reclamation) {
        try {
            String requete="UPDATE `reclamations` SET `id_reclamation`=?,`id_utilisateur`=?,`object_reclamation`=?,`description_reclamation`=?,`categorie_reclamation`=?,`etat_reclamation`=? WHERE `object_reclamation`=?";
            PreparedStatement pst=cnx1.prepareStatement(requete);
            pst.setInt(1, Reclamation.getId_Reclamation());
            pst.setInt(2, Reclamation.getId_user());
            pst.setString(3, Reclamation.getObjet_Reclamation());
            pst.setString(4, Reclamation.getDescription_Reclamation());
            pst.setString(5, Reclamation.getCategorie_Reclamation());
            pst.setInt(6, Reclamation.getEtat_Reclamation());
            pst.setString(7, Reclamation.getObjet_Reclamation());
            
            pst.executeUpdate();
            System.out.println("Reclamation modifiée avec succès");
        } 
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
        @Override
    public ObservableList<Reclamation> afficherReclamations(){
        ObservableList<Reclamation> ReclamationList=FXCollections.observableArrayList();
        try {
            String requete="SELECT * FROM reclamations";
            Statement st=cnx1.createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                Reclamation Reclamation=new Reclamation();
                Reclamation.setId_Reclamation(rs.getInt(1));
                Reclamation.setId_user(rs.getInt(2));
                Reclamation.setObjet_Reclamation(rs.getString(3));
                Reclamation.setDescription_Reclamation(rs.getString(4));
                Reclamation.setCategorie_Reclamation(rs.getString(5));
                Reclamation.setEtat_Reclamation(rs.getInt(6));
                
                ReclamationList.add(Reclamation);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage()); 
        }
        return ReclamationList;
    }
    
      public ObservableList<Reclamation> afficherReclamationsClient(int id){
        ObservableList<Reclamation> ReclamationList=FXCollections.observableArrayList();
        try {
            String requete="SELECT * FROM reclamations where id_utilisateur=?";
            PreparedStatement st=cnx1.prepareStatement(requete);
                        st.setInt(1,id);

            ResultSet rs=st.executeQuery();
            
            while(rs.next()){
                Reclamation Reclamation=new Reclamation();
                
                Reclamation.setObjet_Reclamation(rs.getString(3));
                Reclamation.setDescription_Reclamation(rs.getString(4));
                Reclamation.setCategorie_Reclamation(rs.getString(5));
                Reclamation.setEtat_Reclamation(rs.getInt(6));
                
                ReclamationList.add(Reclamation);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage()); 
        }
        return ReclamationList;
    }
      
    
    public void etat(Reclamation reclamation){
        try {
            String requete = "UPDATE `reclamations` SET `etat_reclamation`=? WHERE `id_reclamation`=?";
            PreparedStatement pst = cnx1.prepareStatement(requete);
            pst.setInt(1, 1);
            pst.setInt(2, reclamation.getId_Reclamation());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public 
        ObservableList<Reclamation> RechercherReclamation(String cat){
        ObservableList<Reclamation> reclamation=FXCollections.observableArrayList();
       
    try {
        String requete = "SELECT * from reclamations WHERE categorie_Reclamation= ?";
        PreparedStatement pst = cnx1.prepareStatement(requete);
        pst.setString(1, cat); // Modification de l'index de 0 à 1
        ResultSet result =pst.executeQuery();
        
        while (result.next()) {
            reclamation.add(new Reclamation(
                result.getInt("id_Reclamation"),
                result.getInt("id_utilisateur"),      
                result.getString("categorie_Reclamation"),
                    result.getString("object_Reclamation"),
                    result.getString("description_Reclamation"),
                result.getInt("etat_Reclamation")
            ));
        }
        
        System.out.println(reclamation);
    } catch(SQLException ex) {
        System.out.println(ex);
    }
    return reclamation;
}

        
    public List<Integer> getCountByCategory() throws SQLException {
    List<Integer> countList = new ArrayList<>();

    // Prepare a statement to query the count of each category
    String query = "SELECT COUNT(*) FROM reclamations WHERE categorie_reclamation = ?";
    PreparedStatement statement = cnx1.prepareStatement(query);

    // Loop through each category and retrieve its count
    for (String category : Arrays.asList("Freelancer", "Service", "Produit")) {
        statement.setString(1, category);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        countList.add(count);
    }

    // Close the database connection and return the count list
    cnx1.close();
    return countList;
}
   
}
