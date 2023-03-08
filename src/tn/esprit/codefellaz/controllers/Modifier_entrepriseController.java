/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.codefellaz.entities.entreprise;
import tn.esprit.codefellaz.services.entreprise_CRUD;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class Modifier_entrepriseController implements Initializable {

    @FXML
    private ImageView Photo_Input;
    @FXML
    private Button button_Submit;
    @FXML
    private Text alertNom;
    @FXML
    private Text alertCIN;
    @FXML
    private Text alertPrenom;
    @FXML
    private Text alertDatenaissance;
    @FXML
    private Text alertTel;
    @FXML
    private Button button_Submit1;
    @FXML
    private TextField id_representant;
    @FXML
    private TextField adresse;
    @FXML
    private TextField tel_entreprise;
    @FXML
    private TextField description_entreprise;
    @FXML
    private Label labelid;
private Label label;
    @FXML
    private Button Timage;
    @FXML
    private ImageView imgajoutincours;
    @FXML
    private Label imgpathttt;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          labelid.setText(Integer.toString(Gerer_entrepriseController.connectedEntreprise.getId_entreprise()));
        id_representant.setText(Integer.toString(Gerer_entrepriseController.connectedEntreprise.getId_representant()));
        adresse.setText(Gerer_entrepriseController.connectedEntreprise.getAdresse());
        tel_entreprise.setText(Integer.toString(Gerer_entrepriseController.connectedEntreprise.getTel_entreprise()));
        description_entreprise.setText(Gerer_entrepriseController.connectedEntreprise.getDescription_entreprise());
        imgpathttt.setText(Gerer_entrepriseController.connectedEntreprise.getImage());

        
    
    }    

    @FXML
    private void handleButtonSubmitAction(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException {
           entreprise_CRUD productService = new entreprise_CRUD();

        if (id_representant.getText().equals("") || adresse.getText().equals("") || tel_entreprise.getText().equals("")
                || description_entreprise.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Veuillez saisir tous les champs ");
            a.setHeaderText(null);
            a.showAndWait();
        } 
    
        
        
        else {

  entreprise ccc = new entreprise(Integer.parseInt(labelid.getText()), Integer.parseInt( id_representant.getText()), adresse.getText(),
                     Integer.parseInt(tel_entreprise.getText()), description_entreprise.getText(),imgpathttt.getText() );
                   
           

   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmer ");
      alert.setHeaderText("Confirmer");
      alert.setContentText(" ");
      
         Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
                 productService.modifierentreprise(ccc);
       TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.SLIDE;
            tray.setAnimationType(type);
            tray.setTitle("Modifé avec succés");
            tray.setMessage("Modifié avec succés");
            tray.setNotificationType(NotificationType.INFORMATION);
            tray.showAndDismiss(Duration.millis(3000));
  Parent page1 = FXMLLoader.load(getClass().getResource("gerer_entreprise.fxml"));
            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
          
      
      } else if (option.get() == ButtonType.CANCEL) {
      
      } else {
         this.label.setText("-");
      }
      

          

        };
    }

    @FXML
    private void addimgcours(ActionEvent event) {
    }
          @FXML
    private void back(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("gerer_entreprise.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
        
    }
    

