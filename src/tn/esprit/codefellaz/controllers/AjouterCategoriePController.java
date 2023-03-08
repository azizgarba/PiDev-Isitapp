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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.codefellaz.entities.CategorieProduit;
import tn.esprit.codefellaz.services.CategorieProduitService;


/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AjouterCategoriePController implements Initializable {

    @FXML
    private TextField nom_categorie;
    @FXML
    private Button Ajouter_Cat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Ajouter_Cat.setOnAction(event -> {

             if(nom_categorie.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs!");
                alert.show();
            }
            else {
                 
             
            CategorieProduit CP = new CategorieProduit(nom_categorie.getText());
            
            CategorieProduitService ajout = new CategorieProduitService();
            ajout.ajouterCategorieProduit(CP);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Indication :");
            alert.setContentText("Votre categorie a ete ajouté avec succés !");

            alert.show();
            
           Stage stage = (Stage) Ajouter_Cat.getScene().getWindow();
           stage.close();
}
        }
        );
    }    
    
}

