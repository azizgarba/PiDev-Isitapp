/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.codefellaz.entities.DemandeService;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.CrudDemandeService;
import tn.esprit.codefellaz.services.CrudOffreService;
import tn.esprit.codefellaz.services.UserService;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AjouterDemandeServiceController implements Initializable {

    @FXML
    private TextArea description_demande;
    @FXML
    private TextField nom_demande;
    @FXML
    private TextField budget_demande;
    @FXML
    private DatePicker date_limite_demande;
    @FXML
    private Button enregistrer_demande;
    @FXML
    private Button retour;

    Connection cnx ;
    @FXML
    private AnchorPane ajout;
    
    private Parent fxml ;
    
    
     public AjouterDemandeServiceController() {
        cnx = MyConnection.getInstance().getCnx();
    }
     
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
   

        retour.setOnAction(retour -> {

              try {
                       
                      fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/HomeView.fxml"));
                       ajout.getChildren().removeAll() ;
                       ajout.getChildren().setAll(fxml) ;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }


        });

        enregistrer_demande.setOnAction(e -> {

            //controle de saisie champs manquant 
            if ("".equals(budget_demande.getText()) || "".equals(description_demande.getText()) || "".equals(nom_demande.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Veuillez remplir tous les champs coorectement!");

                alert.show();

            } else {
                //controle de saisie champs budget numerique 
                try {
                    float verifPrix = Float.parseFloat((budget_demande.getText()));
                    CrudOffreService ajout = new CrudOffreService();
                 // User user = new User(1, "dhia", "dhiaajmi7@gmail.com", "26247874", "30-03-2002", "male", "dhia", "0000", 0, "user", "fdf") ;
                 
             Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
       
            
             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
             
                
  
            
            
        

           
                    DemandeService ds = new DemandeService(nom_demande.getText(), Integer.parseInt(budget_demande.getText()), description_demande.getText(), date_limite_demande.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),userconnected.getId(), 10.0);

                    CrudDemandeService ajoutDemandeService = new CrudDemandeService();

                    //verification de l'unicite par le nom de la demande le budget la description et la date limite 
                    String sql = "SELECT * FROM demandes_services WHERE nom_demande = ? AND budget = ? AND description_demande = ? AND date_limite = ?";
                    PreparedStatement stmt = cnx.prepareStatement(sql);
                    stmt.setString(1, ds.getNomDemande());
                    stmt.setFloat(2, ds.getBudget());
                    stmt.setString(3, ds.getDescription());
                    stmt.setString(4, ds.getDateLimite());
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        
                       Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Ce service existe deja!");

                alert.show();
                        return; 
                    }

                    // si elle n'existe pas on procede a l ajout 
                    ajoutDemandeService.ajouterDemandeService(ds);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Information Dialog");

                    alert.setHeaderText(null);

                    alert.setContentText("Votre Demande a ete ajouté");

                    alert.show();

                    // fermer la fenetre apres l'ajout 
//           Stage stage = (Stage) enregistrer_demande.getScene().getWindow();
//        stage.close();
                    // retour a la fenetre principale
//                    try {
//                      fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/HomeView.fxml"));
//                       ajout.getChildren().removeAll() ;
//                       ajout.getChildren().setAll(fxml) ;
//
//                    } catch (IOException ex) {
//                        System.out.println(ex.getMessage());
//                    }

                } catch (NumberFormatException erreur) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Information Dialog");

                    alert.setHeaderText(null);

                    alert.setContentText("Le budget doit etre exprimé en chiffre !");

                    alert.show();

                     } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(AjouterDemandeServiceController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

                
                
        }
        );
        

    }

}
