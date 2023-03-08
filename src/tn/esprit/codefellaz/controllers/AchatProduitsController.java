
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import tn.esprit.codefellaz.entities.Achat;
import tn.esprit.codefellaz.entities.CategorieProduit;
import tn.esprit.codefellaz.entities.Produit;
import tn.esprit.codefellaz.entities.TwilioSMS;
import tn.esprit.codefellaz.services.AchatService;
import tn.esprit.codefellaz.services.CategorieProduitService;
import tn.esprit.codefellaz.services.ProduitService;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AchatProduitsController implements Initializable {

    @FXML
    private VBox chosenProduct;
    @FXML
    private Label productName;
    @FXML
    private Label productPrice;
    @FXML
    private ImageView productImg;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;
    private Image image;
    private MyListener myListener;

    private List<Produit> produits = new ArrayList<>();
    @FXML
    private Label productCat;
    @FXML
    private Label productOwner;
    @FXML
    private Button AjouterAuPanier;

    private int id_produit;
    private float prix_du_produit;

    @FXML
    private CheckBox myCheckBox;
    @FXML
    private ComboBox<String> combocategorie;
    @FXML
    private Button pdfbtn;

    private List<Produit> getData() {
        List<Produit> produits = new ArrayList();

        Produit produit;
        ProduitService prod = new ProduitService();
//                AchatService a = new AchatService();
//                        produits = prod.getProductsByIds(a.getAllProductsId()) ;

            produits = prod.afficherProduit();
        

        return produits;
    }
    
    private List<Produit> getDataThroughCategory(String Category) {
        List<Produit> produits = new ArrayList();

        Produit produit;
        ProduitService prod = new ProduitService();

            produits = prod.getProductsByCategory(Category);

        return produits;
    }
    
   public void exportToPdf(){
     try {
         FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
        document.open();
        PdfPTable table = new PdfPTable(2);
        PdfPCell c1 = new PdfPCell(new Phrase("Nom Produit"));
        table.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Prix"));
        table.addCell(c2);
       
        table.setHeaderRows(1);
        ProduitService pcrud = new ProduitService();
        List <Produit> listeProduits =  pcrud.afficherProduit();
        for (Produit p : listeProduits) {
            table.addCell(p.getNom_produit());
            table.addCell(String.format("%.2f", p.getPrix_produit())); 
           
        }
        document.add(table);
        document.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export PDF");
        alert.setHeaderText(null);
        alert.setContentText("Le fichier a été exporté avec succès !");
        alert.showAndWait();}
    } catch (FileNotFoundException | DocumentException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Export PDF");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de l'exportation du fichier PDF !");
        alert.showAndWait();
    }}



    private List<Produit> getDataSorted(){
        
        List<Produit> produits = new ArrayList();

        Produit produit;
        ProduitService prod = new ProduitService();

            produits = prod.getProductsSorted(prod.getAllProductsId());
      
        return produits;
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

    private void setChosenProduct(Produit produit) throws MalformedURLException {
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
        prix_du_produit = produit.getPrix_produit();

    }
//    public void change(ActionEvent event)
//        {
//          if (myCheckBox.isSelected()) {
//            produits.addAll(getDataSorted());
//        } else {
//            produits.addAll(getData());
//        }
//        }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        CategorieProduitService catg = new CategorieProduitService();
        List<String> cat = catg.ListeCategorie();
        ObservableList<String> list = FXCollections.observableArrayList(cat);
  produits.addAll(getData());
         if (produits.size() > 0) {
            try {
                setChosenProduct(produits.get(0));
            } catch (MalformedURLException ex) {
                Logger.getLogger(AchatProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            myListener = new MyListener() {
                @Override
                public void onClickListener(Produit produit) {
                    try {
                        setChosenProduct(produit);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(AchatProduitsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/tn/esprit/codefellaz/views/item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(produits.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
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

                GridPane.setMargin(anchorPane, new Insets(10));
               
//                
//                myCheckBox.setOnAction(event -> { 
//                   if (myCheckBox.isSelected())
//                   {
//                       
//                   }
//                   else{
//                       
//                   }
//                    
//                 });
                   
            }
        } catch (IOException ex) {
            Logger.getLogger(AchatProduitsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        combocategorie.setItems(list);
        combocategorie.setOnAction(event -> {

            String categorie_du_combo = combocategorie.getSelectionModel().getSelectedItem();
               produits.clear();
                grid.getChildren().clear();
                 produits.addAll(getDataThroughCategory(categorie_du_combo));
                 
                  if (produits.size() > 0) {
                try {
                    setChosenProduct(produits.get(0));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(AchatProduitsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            myListener = new MyListener() {
                @Override
                public void onClickListener(Produit produit) {
                    try {
                        setChosenProduct(produit);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(AchatProduitsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
        int column2 = 0;
        int row2 = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/tn/esprit/codefellaz/views/item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(produits.get(i), myListener);

                if (column2 == 3) {
                    column2 = 0;
                    row2++;
                }

                grid.add(anchorPane, column2++, row2);

                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
               
//                
//                myCheckBox.setOnAction(event -> { 
//                   if (myCheckBox.isSelected())
//                   {
//                       
//                   }
//                   else{
//                       
//                   }
//                    
//                 });
                   
            }
        } catch (IOException ex) {
            Logger.getLogger(AchatProduitsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
            
        });
        
           myCheckBox.setOnAction(event -> { 
              
               combocategorie.setValue("Categorie");
                       
                 if (myCheckBox.isSelected()){
                      produits.clear();
                           grid.getChildren().clear();
                     
                    //     produits.addAll(getData());
                             produits.addAll(getDataSorted());
                 }
                 else{
                     produits.clear();
                           grid.getChildren().clear();
                  
                       produits.addAll(getData());
                      
                 }
               
          
//         if (myCheckBox.isSelected()){
             
        
        // TODO
        
        
//                myCheckBox.setOnAction(event -> { 
//                   if (myCheckBox.isSelected())
//                   {
        
         //produits.addAll(getData());
        //produits.addAll(getData());
        if (produits.size() > 0) {
                   try {
                       setChosenProduct(produits.get(0));
                   } catch (MalformedURLException ex) {
                       Logger.getLogger(AchatProduitsController.class.getName()).log(Level.SEVERE, null, ex);
                   }
            myListener = new MyListener() {
                @Override
                public void onClickListener(Produit produit) {
                    try {
                        setChosenProduct(produit);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(AchatProduitsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
         int column3 = 0;
      int row3 = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/tn/esprit/codefellaz/views/item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(produits.get(i), myListener);

                if (column3 == 3) {
                    column3 = 0;
                    row3++;
                }

                grid.add(anchorPane, column3++, row3);

                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
               
//                
//                myCheckBox.setOnAction(event -> { 
//                   if (myCheckBox.isSelected())
//                   {
//                       
//                   }
//                   else{
//                       
//                   }
//                    
//                 });
                   
            }
        } catch (IOException ex) {
            Logger.getLogger(AchatProduitsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        

 });
        
        AjouterAuPanier.setOnAction(event -> {

            Achat c = new Achat(1, id_produit);
          
            AchatService ajout = new AchatService();
            ajout.ajouterAchat(c);

           
TwilioSMS.send("+21652525798","Salut Mr votre produit "+productName.getText()+" est sur commande. On vous contactera pour plus d'information le plus tot possible.");
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Information Dialog");
//            alert.setHeaderText("Indication :");
//            alert.setContentText("Votre achat a ete ajouté avec succés !");
//
//            alert.show();

  Notifications.create()
                    .title("Produit ajouté")
                    .text("Un produit a ete ajouté au panier!")
                    .showInformation();

            

        }
        );

        pdfbtn.setOnAction(event -> {

                exportToPdf();
        }
        );
    }
        
    }

// }

// }
