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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
public class MesproduitController implements Initializable {

    @FXML
    private VBox chosenProduct;
    @FXML
    private TextField productName;
    @FXML
    private TextField productPrice;
    @FXML
    private Label id_panier;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    @FXML
    private Button modifierPanier;
    @FXML
    private Text nomuser;
    @FXML
    private ImageView productImg;

     private Image image ;
     private int id_produit;
     private String path ;
     private List<Produit> produits = new ArrayList<>();
     private MyListener myListener ;
    private TextField categoryproduct;
    @FXML
    private ComboBox<String> combocategorie;
    
     private List<Produit> getData(){
            List<Produit> produits = new ArrayList();
            
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
       private void setChosenProduct(Produit produit)throws MalformedURLException {
        CategorieProduitService cate = new CategorieProduitService();
        CategorieProduit cc = new CategorieProduit();
        ProduitService CP = new ProduitService();
        productName.setText(produit.getNom_produit());
        productPrice.setText(Float.toString(produit.getPrix_produit()));
        image = new Image(new URL("http://localhost"+CP.ReturnImg(produit)).toString());
        productImg.setImage(image);
            productImg.setImage(image);
            chosenProduct.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #D91F4E,#FF854C);" + /*produit.getColor() + */";\n" +
                "    -fx-background-radius: 30;");
            //productCat.setText(Int.toString(produit.getId_categorie()); 
            cc = cate.findById(produit.getId_categorie());
            //productCat.setText(cc.getNom_categorie_produit());
            //nomuser.setText(getNomUser(produit.getId_freelancer()));
            id_produit = produit.getId_produit();
            path = produit.getImage_produit();
           
            combocategorie.setValue(cc.getNom_categorie_produit());
    //        categoryproduct.setText(produit.getId_categorie());

            
        } 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
                           

// TODO
        CategorieProduitService catg = new CategorieProduitService();
        List<String> cat = catg.ListeCategorie();
        ObservableList<String> list = FXCollections.observableArrayList(cat);

         
        combocategorie.setItems(list);
       
        combocategorie.setOnAction(event -> {

            String categorie_du_combo = combocategorie.getSelectionModel().getSelectedItem();
     
        });
        
        
         produits.addAll(getData());
        if(produits.size() > 0)
        {
            try {
                setChosenProduct(produits.get(0));
            } catch (MalformedURLException ex) {
                Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
            myListener = new MyListener(){
            @Override
            public void onClickListener(Produit produit){
                try {
                    setChosenProduct(produit);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        modifierPanier.setOnAction(event -> {

            String Nom = nomuser.getText();
             UserService US = new UserService();
                User userconnected = US.userconnected(Nom);
                
                System.out.println(userconnected.getId());
          ProduitService ajout = new ProduitService();
            CategorieProduitService CP = new CategorieProduitService();
                
                 CategorieProduit CategorySelected = CP.returnCatwithname(combocategorie.getSelectionModel().getSelectedItem());
            int rep = CP.RetourID(combocategorie.getSelectionModel().getSelectedItem());
                System.out.println(rep);
            // System.out.println(rep);
            Produit pp = new Produit(id_produit,productName.getText(), Float.parseFloat(productPrice.getText()), CategorySelected.getid_categorie_produit(), userconnected.getId(),path);
            System.out.println(pp);
            //PersonneDao pdao = PersonneDao.getInstance();
            ajout.modifierProduit(pp);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Information Dialog");
            alert.setHeaderText("Indication :");
            alert.setContentText("Votre Produit a ete modifié");
            alert.show();

            // fermer la fenetre apres l'ajout 
//            Stage stage = (Stage) modifierPanier.getScene().getWindow();
//            stage.close();

           
    }     ); 
    }
}
