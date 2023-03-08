/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.paint.Color;
import tn.esprit.codefellaz.entities.Produit;
import tn.esprit.codefellaz.entities.Projets;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.AchatService;
import tn.esprit.codefellaz.services.ProjetsCRUD;
import tn.esprit.codefellaz.services.InterfaceTache;
import tn.esprit.codefellaz.services.ProduitService;
import tn.esprit.codefellaz.services.UserService;

/**
 *
 * @author myriam-PC
 */
public class AjouterProjetController implements Initializable {

    @FXML
    private TextField nom_projet;
    @FXML
    private TextField priorite_projet;
    @FXML
    private DatePicker dateL_projet;
    @FXML
    private TextArea description_projet;
    @FXML
    private Button enregistrer_projet;
    @FXML
    private Button projet_revenir;
    @FXML
    private Label nomprojetcontrol;
    @FXML
    private Label prioriteprojetcontrol;
    @FXML
    private Label desccontrol;
    @FXML
    private Label datelcontrol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        enregistrer_projet.setOnAction(e -> {

            LocalDate currentDate = LocalDate.now();
            ProjetsCRUD ajout = new ProjetsCRUD();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            ///control saisie
            String nomP = nom_projet.getText().trim();
            String prioP = priorite_projet.getText().trim();
            String descP = description_projet.getText().trim();
            LocalDate dateLP = dateL_projet.getValue();
            System.out.println(dateLP);

            if (dateLP == null || nomP.isEmpty() || prioP.isEmpty() || descP.isEmpty()) {

                datelcontrol.setVisible(true);
                datelcontrol.setText("Ce champ ne doit pas etre vide!");
                datelcontrol.setTextFill(Color.color(1, 0, 0));
                dateL_projet.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;-fx-border-width: 4px");

                nomprojetcontrol.setVisible(true);
                nomprojetcontrol.setText("Ce champ ne doit pas etre vide!");
                nomprojetcontrol.setTextFill(Color.color(1, 0, 0));
                nom_projet.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;-fx-border-width: 4px");

                prioriteprojetcontrol.setVisible(true);
                prioriteprojetcontrol.setText("Ce champ ne doit pas etre vide!");
                prioriteprojetcontrol.setTextFill(Color.color(1, 0, 0));
                priorite_projet.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;-fx-border-width: 4px");

                desccontrol.setVisible(true);
                desccontrol.setText("Ce champ ne doit pas etre vide!");
                desccontrol.setTextFill(Color.color(1, 0, 0));
                description_projet.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;-fx-border-width: 4px");

            } else {

                  Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
         

             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
             
            
           
           
                Projets p = new Projets(nom_projet.getText(), Integer.parseInt(priorite_projet.getText()), currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), dateL_projet.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), description_projet.getText(), 0, userconnected.getId());

                ajout.AjouterProjet(p);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information");

                alert.setHeaderText(null);

                alert.setContentText("Projet ajouté avec succés");

                alert.show();

                 } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                Stage stage = (Stage) enregistrer_projet.getScene().getWindow();

                //switch to tache
                try {
                    Parent root2 = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/AjoutTache.fxml"));
                    Scene scene = new Scene(root2);
                    stage.setTitle("Taches");
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }
        );
        projet_revenir.setOnAction(oo -> {

            //switch to affichage
            Stage stage = (Stage) projet_revenir.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/AfficherProjet.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Projets");
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
        );

        ///control saisie sur priorite du projet
        priorite_projet.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                priorite_projet.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Empêcher la saisie d'une date antérieure à la date actuelle
        dateL_projet.setDayCellFactory(dateL -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate currentDate = LocalDate.now();
                setDisable(empty || date.isBefore(currentDate));
                if (date.isBefore(currentDate)) {
                    setStyle("-fx-background-color: #ffc0cb;"); // changer la couleur des dates antérieures
                }
            }
        });

    }
}