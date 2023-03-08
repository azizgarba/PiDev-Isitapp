/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import tn.esprit.codefellaz.entities.Reclamation;
import tn.esprit.codefellaz.entities.Reponse;
import tn.esprit.codefellaz.services.ReclamationCRUD;
import tn.esprit.codefellaz.services.ReponseCRUD;

/**
 * FXML Controller class
 *
 * @author Meriem
 */
public class ReponseReclamationController implements Initializable {

    @FXML
    private TextArea textReponse;
    @FXML
    private Button validerReponse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    
    {
    validerReponse.setOnAction( e -> {
        if (textReponse.getText().isEmpty() )
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Veuillez remplir tous les champs!");

                alert.show();
                }
            else {
            
        
        Reponse rep = new Reponse(12,textReponse.getText());
        ReponseCRUD crud1=new ReponseCRUD();
        crud1.ajouterReponse(rep); }
    
    } );
} 
}
    

