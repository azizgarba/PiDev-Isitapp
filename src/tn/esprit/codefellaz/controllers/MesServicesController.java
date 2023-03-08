/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.codefellaz.entities.OffreService;
import tn.esprit.codefellaz.services.CrudOffreService;
import tn.esprit.codefellaz.utils.MyConnection;
import org.controlsfx.control.*;
import tn.esprit.codefellaz.entities.Avis;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.entities.commande;
import tn.esprit.codefellaz.entities.offre_de_travail;
import tn.esprit.codefellaz.services.CrudAvis;
import tn.esprit.codefellaz.services.CrudCommande;
import tn.esprit.codefellaz.services.UserService;
import tn.esprit.codefellaz.services.updateBase;

/**
 * FXML Controller class
 *
 * @author user
 */
public class MesServicesController implements Initializable {

    Connection cnx;
    private AnchorPane root;
    private AnchorPane view;

    TilePane parentContainer = new TilePane();
    TilePane newContainer = new TilePane();

    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    @FXML
    private AnchorPane leftAnchor;
    @FXML
    private AnchorPane modifierView;
    @FXML
    private TextField prix1;
    @FXML
    private TextArea description_offre;
    @FXML
    private TextField pays1;
    @FXML
    private Button ajouter_image;
    @FXML
    private ImageView imageOffre;
    @FXML
    private Button valider;
    @FXML
    private Button annuler;

    private String filePath;
    @FXML
    private Button retour;
    @FXML
    private AnchorPane bouttons;

    @FXML
    private AnchorPane serviceView;
    @FXML
    private Text desc;
    @FXML
    private Text prop;
    @FXML
    private Button returnB;
    @FXML
    private Text prix;
    @FXML
    private Button afficher;
    @FXML
    private Text note;
    @FXML
    private Text nb_jour;
    @FXML
    private Text pays;
    @FXML
    private Text nb_services;
    @FXML
    private Slider rating;
    @FXML
    private Button evaluer;
    @FXML
    private Button commander;
    @FXML
    private Text nb_jour1;
    @FXML
    private AnchorPane listeS;
    
    private Parent fxml ;
    
    public MesServicesController() {
        cnx = MyConnection.getInstance().getCnx();
    }

    // code pour afficher le nom de la categorie car c'est une clé etranger
    public String getCategorie(int id) {

        String nomCategorie;

        try {
            String requete2 = "SELECT nom_categorie_service FROM categories_services WHERE id_categorie_service = ?";
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            nomCategorie = "";
            if (rs.next()) {
                nomCategorie = rs.getString("nom_categorie_service");
                return nomCategorie;
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }

        return "nom";
    }
    
    
    public ImageView convert_path_to_image(String path) {
        //Creating the image view
        ImageView imageView = new ImageView();
        //creating the image object
        
        Image image = new Image("http://localhost"+path);
        imageView.setFitWidth(90);
        imageView.setFitHeight(90);
        //Setting image to the image view
        imageView.setImage(image);
        return imageView;

    }
    
    

    // code pour afficher le nom de l'utilisateur car c'est une clé etranger
    public String getNomUser(int id) {

        String nomUtilisateur;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       // root.resize(825, 589);
            ScrollPane scroll = new ScrollPane();
        //boutton retour a l'acceuil
        retour.setOnAction(retour -> {

              try {
                       
                      fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/HomeView.fxml"));
                       root.getChildren().removeAll() ;
                       root.getChildren().setAll(fxml) ;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

        });

        //afficher les les demandes et creer les flowPane
        afficher.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //craation du scroll bar
            
                
                
                
                afficher.setVisible(false);
                bouttons.setVisible(false);
                root = (AnchorPane) afficher.getScene().getRoot();
                CrudOffreService afficher = new CrudOffreService();
                
                  try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
         

             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
                      System.out.println(userconnected.getId());
                
                List<OffreService> services = afficher.afficherOffreService(userconnected.getId());
                Label titre = new Label("liste des service");
               
                

                
                
                // Créer un conteneur parent pour les FlowPane et fixier les contrainte et creer le bouton retour 
                MesServicesController.this.parentContainer = new TilePane();

                parentContainer.setStyle("-fx-background-color:linear-gradient(from 100% 100% to 60% 60%,#091F4E,#18b9dF) ; ");
                scroll.setContent(parentContainer);
                //root.setStyle("-fx-background-color:linear-gradient(from 25% 25% to 100% 100%,#091F4E,#18b9dF) ; ");
                //root.getChildren().add(scroll);
//                AnchorPane.setTopAnchor(ServicesViewController.this.parentContainer, 14.0);
//                AnchorPane.setLeftAnchor(ServicesViewController.this.parentContainer, 14.0);
//              
//                AnchorPane.setRightAnchor(ServicesViewController.this.parentContainer, 14.0);
//                AnchorPane.setBottomAnchor(ServicesViewController.this.parentContainer, 14.0);

                Button retourAcceuil = new Button();
                retourAcceuil.setText("Retour vers ecran d'acceuil");
                 //parentContainer.getChildren().add(retourAcceuil);
                 
                 
                 Label principale = new Label("Listes des Offres de services");
                 principale.setStyle(" -fx-text-fill: white;");
                 
                 
                  HBox h = new HBox() ; 
                AnchorPane b = new AnchorPane() ;
                VBox v = new VBox() ; 
                v.setAlignment(Pos.CENTER);
                b.getChildren().add(v) ; 
                v.setSpacing(120);
            v.getChildren().add(principale) ;
                v.getChildren().add(retourAcceuil) ;
                h.getChildren().add(v) ;
               h.getChildren().add(scroll) ;
               retourAcceuil.setVisible(false);
              
               
               
               
               
                 retourAcceuil.setStyle(" -fx-background-color: #091F4E;\n" +
"    -fx-background-radius: 15px;\n" +
"    -fx-border-color: #cccccc;\n" +
"    -fx-border-radius: 15px;\n" +
"    -fx-border-width: 1px;\n" +
"    -fx-text-fill: whitesmoke;");
               
               v.setStyle(" -fx-background-color: #091F4E;\n" +
"    -fx-background-radius: 15px;\n" +
"    -fx-border-color: #cccccc;\n" +
"    -fx-border-radius: 15px;\n" +
"    -fx-border-width: 1px;\n" +
"    -fx-text-fill: whitesmoke;");
               
                //ajout du scroll bar 
//               scroll.setContent(parentContainer);
//               root.getChildren().add(scroll);
                //definir l'action du bouton retour du flowPane creer 
                retourAcceuil.setOnAction(retour -> {
                                 try {
                       
                      fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/HomeView.fxml"));
                       listeS.getChildren().removeAll() ;
                       listeS.getChildren().setAll(fxml) ;
                                h.setVisible(false);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                });

                // Parcourir la liste des services et créer un FlowPane pour chaque service
                for (OffreService service : services) {
                    // Créer un FlowPane pour le service
                    TilePane tilePane = new TilePane(Orientation.VERTICAL, 20, 20);
                    MesServicesController.this.parentContainer.setHgap(10);
                    MesServicesController.this.parentContainer.setVgap(10);
                    tilePane.setStyle("fx-background-radius: 10;");
                    tilePane.setHgap(100);

                    // Ajouter les éléments au FlowPane (labels, boutons, etc.)
                    Label categorie = new Label("categori: " + getCategorie(service.getCategorie()));
                    Label description = new Label(service.getDescriptionOffreService());
                    Label nb_derniere_commande = new Label();
                    Label nb_commande = new Label("nombre de commande passe : " + Integer.toString(service.getNbCommandePasse()) + "commandes");
                    Label proprietaire = new Label("freelencer : " + getNomUser(service.getProprietaireOffre()));
                    ImageView imageview=new ImageView();
                    imageview = convert_path_to_image(service.getImagePaths());
                    Label prix = new Label("prix : " + Double.toString(service.getPrix()));
                    Label note = new Label("la note du service : " + Float.toString(service.getNote()));
                     if (service.getDateDepuisDerniereCommande() == -1) {
                                        nb_derniere_commande.setText("aucune commande passée");
                                       
                                    } else if (service.getDateDepuisDerniereCommande() == 0) {
                                        nb_derniere_commande.setText("derniere commande passée aujourd'hui");
                                        
                                    } else 
                                    nb_derniere_commande.setText("derniere comande passee il y a"+Integer.toString(service.getDateDepuisDerniereCommande())+"jours");
                    tilePane.setId(Integer.toString(service.getIdOffreService()));

                    // Ajouter les éléments au FlowPane
                    tilePane.getChildren().addAll(imageview,categorie, nb_derniere_commande, nb_commande, proprietaire, prix, note);
                    tilePane.setPrefRows(4);

                    //definir les action sur le boutton
                    MesServicesController.this.parentContainer.getChildren().add(tilePane);
                    for (Node node : MesServicesController.this.parentContainer.getChildren()) {
                        if (node instanceof TilePane) {
                            TilePane tile = (TilePane) node;
                         
                            tile.setOnMouseEntered(e -> {
                                tile.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #D91F4E,#FF854C);"); // Changer la couleur du fond lorsqu'on survole la TilePane
                            });
                            tile.setOnMouseExited(e -> {
                                tile.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 90% 90%,#18b9dF,#2988ad) ; "); // Changer la couleur du fond lorsque la souris quitte la TilePane
                            });

                            // afficher un seule service lorsque on clique
                            tile.setOnMouseClicked((e) -> {
                                System.out.println(tile.getId());
                                h.setVisible(false);
                                List<OffreService> serviceX = afficher.afficherUnSeulOffredeServices(Integer.parseInt(tile.getId()));
                                for (OffreService s : serviceX) {
                                    serviceView.setVisible(true);
                                    desc.setText(s.getDescriptionOffreService());
                                    prop.setText(getNomUser(s.getProprietaireOffre()));
                                    MesServicesController.this.prix.setText(Float.toString(s.getPrix()));
                                    MesServicesController.this.note.setText(Float.toString(s.getNote()));
                                    pays.setText(s.getPays());
                                    nb_services.setText(Integer.toString(s.getNbCommandePasse()));
                                    if (s.getDateDepuisDerniereCommande() == -1) {
                                        nb_jour1.setText("aucune commande passée");
                                        nb_jour.setText("");
                                    } else if (s.getDateDepuisDerniereCommande() == 0) {
                                        nb_jour1.setText("derniere commande passée aujourd'hui");
                                        nb_jour.setText("");
                                    } else 
                                    nb_jour.setText(Integer.toString(s.getDateDepuisDerniereCommande())+"jours");
                                    
                                    serviceView.resize(825, 589);
                                    returnB.setOnAction((retour) -> {
                                        serviceView.setVisible(false);
                                        h.setVisible(true);
                                    });

                                    evaluer.setOnAction(evaluer -> {

                                        System.out.format("%.0f", rating.getValue());
                                        int rate = (int) Math.round(rating.getValue()) ;

                                        Avis avis = new Avis(1, rate, Integer.parseInt(tile.getId()));
            
                                        System.out.println(avis);
                                        CrudAvis crud = new CrudAvis();
                                        crud.ajouterAvis(avis);
                                        CrudOffreService modif = new CrudOffreService() ;
                                        modif.modifierNote(Integer.parseInt(tile.getId()), rate, crud);
                                        

                                    });

                                    //supprimer
                                    supprimer.setOnAction((supprimer) -> {
                                        CrudOffreService update = new CrudOffreService();

                                        List<OffreService> newServices = afficher.afficherOffreService();

                                        update.supprimerOffreService(Integer.parseInt(tile.getId()));

                                        //reafficher la nouvelle liste apres une mise a jour 
                                        int idOffreServiceSupprime = Integer.parseInt(tile.getId()); // L'ID de l'offre de service que vous venez de supprimer
                                        Iterator<Node> iterator = parentContainer.getChildren().iterator();
                                        while (iterator.hasNext()) {
                                            Node nodeS = iterator.next();
                                            if (nodeS instanceof TilePane && Integer.parseInt(nodeS.getId()) == idOffreServiceSupprime) {
                                                iterator.remove();
                                                
                                                break;
                                            }
                                        }
                                        serviceView.setVisible(false);
                                        h.setVisible(true);

                                    });
                                       
                                     commander.setOnAction(commander -> {
                                            CrudCommande ajout = new CrudCommande() ;   
                                            CrudOffreService rec = new CrudOffreService() ;
                                            commande commande = new commande(1, Integer.parseInt(tile.getId()),"test") ;
                                            System.out.println(commande);
                                            ajout.ajouterCommande(commande); 
                                          int  nb = rec.recupererNombreCommande(Integer.parseInt(tile.getId())) ;
                                          nb ++ ; 
                                          Date date = new Date() ; 
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
         String dateStr = formatter.format(date);
                                          System.out.println(nb);
                                          rec.modifierNombreCommandeDate(Integer.parseInt(tile.getId()), nb,dateStr);
                                          updateBase.updateJour();
                                          
                                        
                                        });
                                    //mofifier 
                                    modifier.setOnAction(modifer -> {

                                        //ajouter l'image
                                        ajouter_image.setOnAction(image -> {
                                            FileChooser fileChooser = new FileChooser();
                                            File selectedFile = fileChooser.showOpenDialog(ajouter_image.getScene().getWindow());
                                            if (selectedFile != null) {
                                                Image modifImage = new Image(selectedFile.toURI().toString());
                                                imageOffre.setImage(modifImage);
                                                filePath = selectedFile.getAbsolutePath();
                                                System.out.println("Chemin du fichier : " + filePath);
                                            }
                                        });

                                        //definir les valeur par defaut des champs
                                        h.setVisible(false);
                                        serviceView.setVisible(false);
                                        modifierView.setVisible(true);
                                        prix1.setPromptText(Float.toString(s.getPrix()));
                                        description_offre.setPromptText(s.getDescriptionOffreService());
                                        pays1.setPromptText(s.getPays());
                                        filePath = s.getImagePaths();

                                        //valider les modification
                                        valider.setOnAction(valider -> {
                                            CrudOffreService update = new CrudOffreService();
                                            update.modifierOffreService(Integer.parseInt(tile.getId()),Float.parseFloat(prix1.getText()),description_offre.getText(),pays1.getText(),filePath);
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Information Dialog");
                                            alert.setHeaderText(null);
                                            alert.setContentText("Publication modifié avec succés!");
                                            alert.show();

                                        });

                                        //annuler les modifications 
                                        annuler.setOnAction(annuler -> {
                                            modifierView.setVisible(false);
                                            serviceView.setVisible(true);

                                        });
                                        
                                        
                                       
                                        

                                    });

                                }
                            });
                        }
                    }
                }

                MesServicesController.this.parentContainer.prefHeightProperty().bind(root.heightProperty());
                MesServicesController.this.parentContainer.prefWidthProperty().bind(root.widthProperty());

                // Ajouter le conteneur parent au root de la scène
               
                root.getChildren().add(h) ;
                //root.getChildren().add(ServicesViewController.this.parentContainer);
  } catch (IOException ex) {
                    Logger.getLogger(AjouterOffreServiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    @FXML
    private void handleDragOver(DragEvent event) {
    }

    @FXML
    private void handleDrop(DragEvent event) {
    }

}
