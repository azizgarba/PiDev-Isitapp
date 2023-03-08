/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.controlsfx.control.Notifications;
import tn.esprit.codefellaz.entities.Reclamation;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.ReclamationCRUD;
import tn.esprit.codefellaz.services.UserService;

/**
 * FXML Controller class
 *
 * @author Meriem
 */
public class AjoutReclamationController implements Initializable {

    @FXML
    private ComboBox<String> comboCategorie;
    @FXML
    private TextField textObjet;
    @FXML
    private TextArea textReclamation;
    @FXML
    private Button validerReclamation;

    private String combo ;
    

    /**
     * Initializes the controller class.
     */
     

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        comboCategorie.getItems().addAll("Freelancer","Produit","Service");
        comboCategorie.setOnAction(event->{ 
        
                combo = comboCategorie.getSelectionModel().getSelectedItem();
                System.out.println(combo);
               
} );
        
      
        
//            public Reclamation(int id_user, String objet_Reclamation, 
//                    String description_Reclamation, String categorie_Reclamation, 
//                    int etat_Reclamation) {
        validerReclamation.setOnAction( e -> {
            if (textObjet.getText().isEmpty() || textReclamation.getText().isEmpty()||comboCategorie.getValue().isEmpty() )
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Veuillez remplir tous les champs!");

                alert.show();
                }
            else {
                      
                 
             Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
       
            
             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
          
                
                
        Reclamation rec = new Reclamation(userconnected.getId(),textObjet.getText(),textReclamation.getText(),combo,0);
        ReclamationCRUD crud1=new ReclamationCRUD();
        crud1.ajouterReclamation(rec);
        Notifications.create()
                    .title("NOUVELLE RECLAMATION")
                    .text("UNE NOUVELLE RECLAMATION A ETE AJOUTEE !")
                    .showInformation();
        
                           } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        }
        
        
      
        } );
        

    }    
    
    
    
}
