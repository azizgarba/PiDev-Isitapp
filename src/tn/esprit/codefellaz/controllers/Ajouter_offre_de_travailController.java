/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.entities.entreprise;
import tn.esprit.codefellaz.entities.offre_de_travail;
import tn.esprit.codefellaz.services.UserService;
import tn.esprit.codefellaz.services.entreprise_CRUD;
import tn.esprit.codefellaz.services.offre_de_travail_CRUD;
import tn.esprit.codefellaz.utils.MyConnection;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class Ajouter_offre_de_travailController implements Initializable {
private Label label;
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
    private Text alertAdresse;
    @FXML
    private Text alertDatenaissance;
    @FXML
    private Text alertEmail;
    @FXML
    private Text alertRole;
    @FXML
    private Text alertTel;
    @FXML
    private TextField type_offre_de_travail;
    @FXML
    private TextField specialite_offre_de_travail;
    @FXML
    private TextField description_offre_de_travail;
    @FXML
    private TextField titre_offre_de_travail;
    @FXML
    private TextField salaire_offre_de_travail;
    @FXML
    private ComboBox<Integer> entreprisecombobox;
  Connection connexion;   
    /**
     * Initializes the controller class.
     */
  public Ajouter_offre_de_travailController() {
        connexion = MyConnection.getInstance().getCnx();
    }
  
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
           try {
            String req = "select * from entreprises";
            Statement stm = connexion.createStatement();
            ResultSet rst = stm.executeQuery(req);
            
            while (rst.next()) {
                
                Integer xx = rst.getInt("id_entreprise");
                entreprisecombobox.getItems().add(xx);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
           offre_de_travail_CRUD offreservice = new offre_de_travail_CRUD();
   
           
       
        List <offre_de_travail>  listoffre =  new  ArrayList<>();
 List <String>  listspecialité =  new  ArrayList<>();
    try {
        listoffre = offreservice.afficheroffre_de_travail();
        

        
    } catch (SQLException ex) {
    }
    for (offre_de_travail of : listoffre){
      listspecialité.add(of.getSpecialite_offre_de_travail());
    }
    
Map<String, Integer> occurences = new HashMap<>();
        
        for (String element : listspecialité) {
            if (occurences.containsKey(element)) {
                int count = occurences.get(element);
                occurences.put(element, count + 1);
            } else {
                occurences.put(element, 1);
            }
        }
        
        System.out.println("Nombre d'occurrences des éléments : ");
       
        for (Map.Entry<String, Integer> entry : occurences.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }  
         
           
    }    

    @FXML
    private void handleButtonSubmitAction(ActionEvent event) throws IOException, SQLException {
            offre_de_travail_CRUD productService = new offre_de_travail_CRUD();

        if (type_offre_de_travail.getText().equals("") || specialite_offre_de_travail.getText().equals("") 
                || description_offre_de_travail.getText().equals("") || salaire_offre_de_travail.getText().equals("")
                 || titre_offre_de_travail.getText().equals("")
                ) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Veuillez saisir tous les champs ");
            a.setHeaderText(null);
            a.showAndWait();
        } 
    
       

        
        else {

            offre_de_travail ccc = new offre_de_travail( titre_offre_de_travail.getText(), type_offre_de_travail.getText(),
                     Float.parseFloat(salaire_offre_de_travail.getText()), specialite_offre_de_travail.getText()
            , entreprisecombobox.getValue(), description_offre_de_travail.getText() 
            );
                   
           

   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmer ");
      alert.setHeaderText("Confirmer");
      alert.setContentText(" ");
      
         Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
                 productService.Ajouteroffre_de_travail(ccc);
          TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.SLIDE;
            tray.setAnimationType(type);
            tray.setTitle("Ajouté avec succés");
            tray.setMessage("Ajoutéavec succés");
            tray.setNotificationType(NotificationType.INFORMATION);
            tray.showAndDismiss(Duration.millis(3000));
  Parent page1 = FXMLLoader.load(getClass().getResource("gerer_offre_de_travail.fxml"));
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
    private void back(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("gerer_offre_de_travail.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
        
    }
    

