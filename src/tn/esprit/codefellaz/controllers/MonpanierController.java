/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.codefellaz.entities.Achat;
import tn.esprit.codefellaz.entities.CategorieProduit;
import tn.esprit.codefellaz.entities.Produit;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.AchatService;
import tn.esprit.codefellaz.services.CategorieProduitService;
import tn.esprit.codefellaz.services.ProduitService;
import tn.esprit.codefellaz.services.UserService;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MonpanierController implements Initializable {

    @FXML
    private VBox chosenProduct;
    @FXML
    private Label productName;
    @FXML
    private Label productPrice;
    @FXML
    private Label productCat;
    @FXML
    private Label productOwner;
    @FXML
    private Button Supprimerpanier;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    
    
     private Image image ;
     private int id_produit;
     private List<Produit> produits = new ArrayList<>();
     private MyListener myListener ;
    @FXML
    private ImageView productImg;
    @FXML
    private Label id_panier;
    @FXML
    private Text nomuser;


    
     private List<Produit> getData(){
            List<Produit> produits = new ArrayList();
            
            AchatService a = new AchatService();
            ProduitService p = new ProduitService();
           // System.out.println(p.getProductsByIds(a.getAllProductsId()));
            
            Produit produit ;
            ProduitService prod = new ProduitService();
            
            produits = p.getProductsByIds(a.getAllProductsId()) ;
            // produits = p.getProductsByIds(p.getIdsByFreelancerId(1));
            
           // for(int i = 0 ; i < 20 ; i++) produits.add(produit);
            return produits ;
        }
     public String getNomUser(int id) {

        String nomUtilisateur;
        Connection cnx = MyConnection.getInstance().getCnx();
        try {
            String requete2 = "SELECT nom_utilisateur FROM utilisateurs WHERE id_utilisateur = ?";
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            nomUtilisateur = "";
            if (rs.next()) {
                nomUtilisateur = rs.getString("nom_utilisateur");
                return nomUtilisateur;
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }

        return "nom";
    }
        
        
        private void setChosenProduct(Produit produit) throws MalformedURLException{
            CategorieProduitService cat = new CategorieProduitService();
            CategorieProduit cc = new CategorieProduit();
            ProduitService CP = new ProduitService();
            productName.setText(produit.getNom_produit());
            productPrice.setText(Float.toString(produit.getPrix_produit()));
           image = new Image(new URL("http://localhost"+CP.ReturnImg(produit)).toString());
            
            productImg.setImage(image);
            chosenProduct.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #D91F4E,#FF854C);" + /*produit.getColor() + */";\n" +
                "    -fx-background-radius: 30;");
            //productCat.setText(Int.toString(produit.getId_categorie()); 
            cc = cat.findById(produit.getId_categorie());
            productCat.setText(cc.getNom_categorie_produit());
            productOwner.setText(getNomUser(produit.getId_freelancer()));
            id_produit = produit.getId_produit();

            
        } 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
             nomuser.setText(labelValue);

             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
             
            AchatService a = new AchatService();
            ProduitService p = new ProduitService();
           
            
            Produit produit ;
            ProduitService prod = new ProduitService();
            
            produits = p.getProductsByIds(p.getIdsByFreelancerId(userconnected.getId()));

            } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // TODO
        //id_panier.setText("1");
         produits.addAll(getData());
        if(produits.size() > 0)
        {
             try {
                 setChosenProduct(produits.get(0));
             } catch (MalformedURLException ex) {
                 Logger.getLogger(MonpanierController.class.getName()).log(Level.SEVERE, null, ex);
             }
            myListener = new MyListener(){
            @Override
            public void onClickListener(Produit produit){
                try {
                    setChosenProduct(produit);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(MonpanierController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            };
        }
        int column = 0 ;
        int row = 1 ;
        try { 
        for(int i = 0 ; i < produits.size() ; i++)
        {
          FXMLLoader fxmlLoader = new FXMLLoader();
          fxmlLoader.setLocation(getClass().getResource("/tn/esprit/codefellaz/views/item.fxml"));
            
                AnchorPane anchorPane = fxmlLoader.load();
          
          
          ItemController itemController = fxmlLoader.getController();
          itemController.setData(produits.get(i),myListener);
          
          
          if(column == 3){
              column = 0 ;
              row++ ;
          }
          
          grid.add(anchorPane, column++, row);
          
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                
          GridPane.setMargin(anchorPane,new Insets(10));
          
        }
          } catch (IOException ex) {
                Logger.getLogger(AchatProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            } 
     
        Supprimerpanier.setOnAction(event -> {

            Achat c = new Achat(1,id_produit);
            
            AchatService ajout = new AchatService();
            c = ajout.findById(id_produit);
            
            System.out.println(c.getId_achat());
            ajout.supprimerAchat(c.getId_achat());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Indication :");
            alert.setContentText("Votre achat a ete supprimé avec succés !");

            alert.show();
            
           
            
        } 
            ); 
        
    }    
    
}

//            produit = new Produit("paint",12 ,3,1,"/tn/esprit/codefellaz/img/paint.png"); 
//            produits.add(produit);
//            produit = new Produit("accessoire",15 ,3,1,"/tn/esprit/codefellaz/img/acchomme.png"); 
//            produits.add(produit);
//            produit = new Produit("accessoire",18,3,1,"/tn/esprit/codefellaz/img/accfemme.png"); 
//            produits.add(produit);
//            produit = new Produit("food",001 ,3,1,"/tn/esprit/codefellaz/img/food.png"); 
//            produits.add(produit);
//            produit = new Produit("sucreries",112 ,3,1,"/tn/esprit/codefellaz/img/sucreries.png"); 
//            produits.add(produit);