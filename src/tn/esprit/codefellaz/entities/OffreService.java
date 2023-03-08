/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.esprit.codefellaz.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import static jdk.nashorn.internal.runtime.Debug.id;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author dhiaajmi
 */
public class OffreService {
    
    
    Connection cnx;

    
     public OffreService() {

        cnx = MyConnection.getInstance().getCnx();
    }
    
    private int idOffreService ;
    private int categorie ;
    private int proprietaireOffre ;
    private float prix ;
    private String descriptionOffreService ;
    private float note ;
    private String pays ; 
    private String derniereCommande; //lz dztee de la derniere commande passe 
    private int dateDepuisDerniereCommande;
    private String  imagePaths;
    private int nbCommandePasse ; 

    public OffreService( int categorie, int proprietaireOffre, float prix, String descriptionOffreService, String pays, String imagePaths) {
        this.categorie = categorie;
        this.proprietaireOffre = proprietaireOffre;
        this.prix = prix;
        this.descriptionOffreService = descriptionOffreService;
        this.pays = pays;
        this.imagePaths = imagePaths;
    }

    @Override
    public String toString() {
        return "OffreService{" + "idOffreService=" + idOffreService + ",  categorie=" + categorie + ", proprietaireOffre=" + proprietaireOffre + ", prix=" + prix + ", descriptionOffreService=" + descriptionOffreService + ", note=" + note + ", pays=" + pays + ", derniereCommande=" + derniereCommande + ", dateDepuisDerniereCommande=" + dateDepuisDerniereCommande + ", imagePaths=" + imagePaths + ", nbCommandePasse=" + nbCommandePasse + '}';
    }

    public OffreService(int idOffreService, float prix, String descriptionOffreService, String pays, String imagePaths) {
        this.idOffreService = idOffreService;
        this.prix = prix;
        this.descriptionOffreService = descriptionOffreService;
        this.pays = pays;
        this.imagePaths = imagePaths;
    }

   
    
    
    
    
    public OffreService(int idOffreService, int categorie, int proprietaireOffre, float prix, String description, float note, String pays, String derniereCommande, int dateDepuisDerniereCommande, String imagePaths,  int nbCommandePasse) {
        this.idOffreService = idOffreService;
        this.categorie = categorie;
        this.proprietaireOffre = proprietaireOffre;
        this.prix = prix;
        this.descriptionOffreService = description;
        this.note = note;
        this.pays = pays;
        this.derniereCommande = derniereCommande;
        this.dateDepuisDerniereCommande = dateDepuisDerniereCommande;
        this.imagePaths = imagePaths;
        this.nbCommandePasse = nbCommandePasse;
    }
    
     public OffreService(int categorie,  int proprietaireOffre, float prix, String description, float note, String pays, String derniereCommande, int dateDepuisDerniereCommande, String imagePaths, int nbCommandePasse) {
        this.categorie = categorie;
        this.proprietaireOffre = proprietaireOffre;
        this.prix = prix;
        this.descriptionOffreService = description;
        this.note = note;
        this.pays = pays;
        this.derniereCommande = derniereCommande;
        this.dateDepuisDerniereCommande = dateDepuisDerniereCommande;
        this.imagePaths = imagePaths;
        this.nbCommandePasse = nbCommandePasse;
    }

    public int getIdOffreService() {
        return idOffreService;
    }

    public void setIdOffreService(int idOffreService) {
        this.idOffreService = idOffreService;
    }



    
    
    
    
    
    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public int getProprietaireOffre() {
        return proprietaireOffre;
    }

    public void setProprietaireOffre(int id) {
        this.proprietaireOffre = id ;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescriptionOffreService() {
        return descriptionOffreService;
    }

    public void setDescriptionOffreService(String descriptionOffreService) {
        this.descriptionOffreService = descriptionOffreService;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getDerniereCommande() {
        return derniereCommande;
    }

    public void setDerniereCommande(String derniereCommande) {
        this.derniereCommande = derniereCommande;
    }

    public int getDateDepuisDerniereCommande() {
        return dateDepuisDerniereCommande;
    }

    public void setDateDepuisDerniereCommande(int dateDepuisDerniereCommande) {
        this.dateDepuisDerniereCommande = dateDepuisDerniereCommande;
    }

    public String getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(String imagePaths) {
        this.imagePaths = imagePaths;
    }

    

    public int getNbCommandePasse() {
        return nbCommandePasse;
    }

    public void setNbCommandePasse(int nbCommandePasse) {
        this.nbCommandePasse = nbCommandePasse;
    }

  
    /*
    public void updateDaysSinceLastOrder() {
    dateDepuisDerniereCommande = Duration.between(derniereCommande, LocalDateTime.now());
  }
*/
    

    
    
    
    public Float calculerNote() throws SQLException {
        float sommeEtoiles = 0;
        int nombreAvis = 0;
        float note = 3 ;

        
        try (
             Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT SUM(nb_etoiles) as somme, COUNT(*) as nombre FROM avis WHERE id_service = " + idOffreService)) {
            if (resultSet.next()) {
                sommeEtoiles = resultSet.getFloat("somme");
                nombreAvis = resultSet.getInt("nombre");
            }
        } catch (SQLException e) {
        }

        if (nombreAvis > 0) {
            note = sommeEtoiles / nombreAvis;
        }
        
        return  note ; 
    }
    
}
    
