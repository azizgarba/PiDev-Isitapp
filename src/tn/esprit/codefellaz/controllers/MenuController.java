/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Meriem
 */
public class MenuController implements Initializable {

    @FXML
    private Button consulter;
    @FXML
    private Button recadmin;
    @FXML
    private Button ajout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         consulter.setOnAction( e ->{ 
             try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ConsulterReclamationAdmin.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Consultation des réclamations");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
         });
         
          recadmin.setOnAction( e ->{ 
             try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ConsulterReclamationClient.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Consultation des réclamations");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
         });
         
           ajout.setOnAction( e ->{ 
             try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/AjoutReclamation.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle(" Ajouter Reclamation");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
         });
          
          
          
          
    }    
        
}  
