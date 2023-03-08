/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.codefellaz.entities.DemandeService;
import tn.esprit.codefellaz.services.CrudDemandeService;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author user
 */
public class OffreServiceHomeController implements Initializable {

    private AnchorPane root;

    TilePane parentContainer = new TilePane();
    ScrollPane scroll = new ScrollPane();


    @FXML
    private Button afficher;

    /**
     * Initializes the controller class.
     */
    Connection cnx;
    private Text Description;
    @FXML
    private Text desc;
    @FXML
    private Pane serviceView;
    @FXML
    private Button returnB;
    @FXML
    private Text prop;
    @FXML
    private Text date;
    @FXML
    private Text budget;
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    @FXML
    private Button valider;
    @FXML
    private Button annuler;

    private String filePath;
    @FXML
    private AnchorPane modifierView;
    @FXML
    private TextField budget1;
    @FXML
    private TextArea description_demande;
    @FXML
    private DatePicker date1;
    @FXML
    private Button retour;
    @FXML
    private AnchorPane bouttons;
    @FXML
    private AnchorPane listeS;
    
    private Parent fxml ;

    public OffreServiceHomeController() {
        cnx = MyConnection.getInstance().getCnx();
    }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        retour.setOnAction(retour -> {

                    try {
                       
                      fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/HomeView.fxml"));
                       listeS.getChildren().removeAll() ;
                       listeS.getChildren().setAll(fxml) ;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }


        });

        afficher.setOnAction(event -> {
                //creation du scroll bar
              ScrollPane scroll = new ScrollPane();
            afficher.setVisible(false);
            bouttons.setVisible(false);

            root = (AnchorPane) afficher.getScene().getRoot();

            CrudDemandeService afficher = new CrudDemandeService();
            List<DemandeService> services = afficher.afficherDemandeService();
 
            // Créer un conteneur parent pour les FlowPane
            this.parentContainer = new TilePane();
            parentContainer.setStyle("-fx-background-color:linear-gradient(from 100% 100% to 60% 60%,#091F4E,#18b9dF) ; ");
            //Contrainte 
            AnchorPane.setTopAnchor(OffreServiceHomeController.this.parentContainer, 14.0);
            AnchorPane.setLeftAnchor(OffreServiceHomeController.this.parentContainer, 14.0);
            AnchorPane.setRightAnchor(OffreServiceHomeController.this.parentContainer, 14.0);
            AnchorPane.setBottomAnchor(OffreServiceHomeController.this.parentContainer, 14.0);
                scroll.setContent(parentContainer);
                root.setStyle("-fx-background-color:linear-gradient(from 25% 25% to 100% 100%,#091F4E,#18b9dF) ; ");
                //root.getChildren().add(scroll);

            //ajout bouttons
            Button retourAcceuil = new Button();
            retourAcceuil.setText("Retour vers ecran d'acceuil");
           // this.parentContainer.getChildren().add(retourAcceuil
                    
                     
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
            //evenement boutons retour
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
            for (DemandeService service : services) {
                
                // Créer un FlowPane pour le service
                TilePane tilePane = new TilePane(Orientation.VERTICAL, 20, 20);
                this.parentContainer.setHgap(10);
                this.parentContainer.setVgap(10);
          

                tilePane.setHgap(100);
                

             
                // Creer  les éléments a ajouter au FlowPane 
                Label nom = new Label(service.getNomDemande());
                Label description = new Label(service.getDescription());
                Label budget = new Label(Float.toString(service.getBudget()));
                Label dateLimite = new Label("date limite:  " + service.getDateLimite());
                Label client = new Label(getNomUser(service.getClientProprietaire()));
                tilePane.setId(Integer.toString(service.getIdDemande()));
                                                        tilePane.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #D91F4E,#FF854C);"); // Changer la couleur du fond lorsqu'on survole la TilePane

               
                // Ajouter les éléments au FlowPane
                tilePane.getChildren().addAll(nom, description, budget, client, dateLimite);
                tilePane.setPrefRows(4);

                //Ajouter le FlowPane créé au conteneur parent
                this.parentContainer.getChildren().add(tilePane);
                
                // definir des evenement 
                for (Node node : this.parentContainer.getChildren()) {
                    if (node instanceof TilePane) {
                        TilePane tile = (TilePane) node;
                        
                        //souris survole le flow pane 
                        tile.setOnMouseEntered(e -> {
                                tile.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 90% 90%,#18b9dF,#2988ad) ; "); // Changer la couleur du fond lorsque la souris quitte la TilePane
                        });
                        tile.setOnMouseExited(e -> {
                                                        tile.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #D91F4E,#FF854C);"); // Changer la couleur du fond lorsqu'on survole la TilePane

                        });
                        
                        //cliquer sur le flowPane
                        tile.setOnMouseClicked(e -> {

//                            System.out.println(tile.getId());

                           h.setVisible(false);
                            //creer une liste avec l'element du flowPane 
                            List<DemandeService> serviceX = afficher.afficherUneSeuleDemandeServices(Integer.parseInt(tile.getId()));
                            for (DemandeService s : serviceX) {
                                //recuperer les valeur et les afficher 
                                serviceView.setVisible(true);
                                desc.setText(s.getDescription());
                                prop.setText(getNomUser(s.getClientProprietaire()));
                                this.budget.setText(Float.toString(s.getBudget()));
                                date.setText(s.getDateLimite());
                                prop.setText(Double.toString(s.getPriorite()));

                                //bouttons retour
                                returnB.setOnAction(retour -> {
                                    serviceView.setVisible(false);
                                    h.setVisible(true);

                                });
                                
                               //boutton supprimer
                                supprimer.setOnAction((supprimer) -> {
                                    CrudDemandeService update = new CrudDemandeService();
                                    List<DemandeService> newServices = afficher.afficherDemandeService();
                                    update.supprimerDemandeService(Integer.parseInt(tile.getId()));
                                    
                                    
                                    //mise a jour sur le flowPane qui affiche tous les services
                                    int idDemandeServiceSupprime = Integer.parseInt(tile.getId()); // L'ID de l'offre de service que vous venez de supprimer
                                    Iterator<Node> iterator = parentContainer.getChildren().iterator();
                                    while (iterator.hasNext()) {
                                        Node nodeS = iterator.next();
                                        if (nodeS instanceof TilePane && Integer.parseInt(nodeS.getId()) == idDemandeServiceSupprime) {
                                            iterator.remove();
                                            break;
                                        }
                                    }
                                    h.setVisible(false);
                                    OffreServiceHomeController.this.parentContainer.setVisible(true);

                                });

                                //modifier boutton
                                modifier.setOnAction(modifer -> {
                                    
                                    //recuperer les valeur est les mettre par defaut dans les champs
                                    h.setVisible(false);
                                    serviceView.setVisible(false);
                                    modifierView.setVisible(true);
                                    budget1.setPromptText(Float.toString(s.getBudget()));
                                    description_demande.setPromptText(s.getDescription());
                                    date1.setPromptText(s.getDateLimite());
                                    date1.setValue(LocalDate.parse(s.getDateLimite(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                                    
                                    valider.setOnAction(valider -> {

                                        
                                        System.out.println(Integer.parseInt(tile.getId()));
                                        System.out.println(budget1.getText());
                                        System.out.println(description_demande.getText());
                                    
                                        
                                        
                                        CrudDemandeService update = new CrudDemandeService();
                                        update.modifierDemandeService(Integer.parseInt(tile.getId()),Float.parseFloat(budget1.getText()),description_demande.getText(),date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Information Dialog");
                                        alert.setHeaderText(null);
                                        alert.setContentText("service modifié avec succés!");
                                        alert.show();

                                    });
                                    
                                    //annulation 
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

            this.parentContainer.prefHeightProperty().bind(root.heightProperty());
            this.parentContainer.prefWidthProperty().bind(root.widthProperty());

            // Ajouter le conteneur parent au root de la scène
            //root.getChildren().add(this.parentContainer);
root.getChildren().add(h) ;
        });

    }

}
