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
import org.controlsfx.control.Notifications;
import tn.esprit.codefellaz.entities.CategorieService;
import tn.esprit.codefellaz.services.CrudCategoriService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AjouterCategorieController implements Initializable {

    @FXML
    private Button valider_categorie;
    @FXML
    private TextField nom_categorie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        valider_categorie.setOnAction(e -> {

            CategorieService cs = new CategorieService(nom_categorie.getText());

            CrudCategoriService ajout = new CrudCategoriService();

         
            ajout.ajouterCategorieService(cs);

//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//
//            alert.setTitle("Information Dialog");
//
//            alert.setHeaderText(null);
//
//            alert.setContentText("Votre categorie a ete ajouter");
//
//            alert.show();

            Notifications.create()
                    .title("Categorie ajoutée")
                    .text("Une categorie a ete ajoutée !")
                    .showInformation();

            // fermer la fenetre apres l'ajout 
            Stage stage = (Stage) valider_categorie.getScene().getWindow();
            stage.close();

        }
        );

    }

}
