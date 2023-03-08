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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import tn.esprit.codefellaz.entities.Reclamation;
import tn.esprit.codefellaz.entities.Reponse;
import tn.esprit.codefellaz.services.ReclamationCRUD;
import tn.esprit.codefellaz.services.ReponseCRUD;

/**
 * FXML Controller class
 *
 * @author Meriem
 */
public class ConsulterReclamationAdminController implements Initializable {

    @FXML
    private TableColumn<?, ?> UtilisateurReclamation;
    @FXML
    private TableColumn<?, ?> id_reclamation;
    @FXML
    private TableColumn<?, ?> ObjetReclamation;
    @FXML
    private TableColumn<?, ?> categorieReclamation;
    @FXML
    private TableColumn<?, ?> etatReclamation;
    @FXML
    private TableColumn<?, ?> rec;
    @FXML
    private TableView<Reclamation> tablerec;
    @FXML
    private TextField idReclamationadmin;
    @FXML
    private Button btnr;
    @FXML
    private Button supprimerrec;
    @FXML
    private TextArea textReponse;
    @FXML
    private Button validerReponse;
    @FXML
    private Button rechercherec;
    @FXML
    private Button pdf;

    /**
     * Initializes the controller class.
     */
    
    public void exportToPdf(){
     try {
         FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
        document.open();
        PdfPTable table = new PdfPTable(2);
        PdfPCell c1 = new PdfPCell(new Phrase("Sujet"));
        table.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Description"));
        table.addCell(c2);
       
        table.setHeaderRows(1);
        ReclamationCRUD pcrud = new ReclamationCRUD();
        List <Reclamation> listeProduits =  pcrud.afficherReclamations();
        for (Reclamation p : listeProduits) {
            table.addCell(p.getObjet_Reclamation());
            table.addCell(String.format(p.getDescription_Reclamation()));
           
        }
        document.add(table);
        document.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export PDF");
        alert.setHeaderText(null);
        alert.setContentText("Le fichier a été exporté avec succès !");
        alert.showAndWait();}
    } catch (FileNotFoundException | DocumentException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Export PDF");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de l'exportation du fichier PDF !");
        alert.showAndWait();
    }}
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficherReclamations();
        selectReclamation();
        btnr.setOnAction(event -> {
            Reclamation reclamation = new Reclamation(Integer.parseInt(idReclamationadmin.getText()));
            ReclamationCRUD rec = new ReclamationCRUD();
            rec.etat(reclamation);
            afficherReclamations();
        });
        
        
        pdf.setOnAction( e -> {
            exportToPdf();
        });
        
        
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
            
        Reponse rep = new Reponse(Integer.parseInt(idReclamationadmin.getText()),textReponse.getText());
        ReponseCRUD crud1=new ReponseCRUD();
        crud1.ajouterReponse(rep);
        Notifications.create()
                    .title("REPONSE VALIDEE")
                    .text("VOTRE REPONSE A ETE AJOUTEE AVEC SUCCES")
                    .showInformation();
        }
    
    } );
        
         supprimerrec.setOnAction(event -> {
             Reclamation reclamation= new Reclamation(Integer.parseInt(idReclamationadmin.getText()));
             ReclamationCRUD rec = new ReclamationCRUD();
            rec.supprimerReclamation(reclamation);
            afficherReclamations();
            
            });
         rechercherec.setOnAction( e ->{ 
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/RechercheReclamation.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Filtrage reclamation");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ConsulterReclamationAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
});
         
         
         
         rechercherec.setOnAction( e ->{ 
             try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/RechercheReclamation.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Consultation des réclamations");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
         });

        
        
    }  
    
    public void afficherReclamations() {
       ReclamationCRUD reclamation = new ReclamationCRUD();
        ObservableList<Reclamation> ReclamationList = reclamation.afficherReclamations();

        UtilisateurReclamation.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        id_reclamation.setCellValueFactory(new PropertyValueFactory<>("id_Reclamation"));
        ObjetReclamation.setCellValueFactory(new PropertyValueFactory<>("objet_Reclamation"));
        categorieReclamation.setCellValueFactory(new PropertyValueFactory<>("categorie_Reclamation"));
        rec.setCellValueFactory(new PropertyValueFactory<>("description_Reclamation"));
        etatReclamation.setCellValueFactory(new PropertyValueFactory<>("etat_Reclamation"));
        tablerec.setItems(ReclamationList);
    }
    
    public void selectReclamation() {
        tablerec.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                
                idReclamationadmin.setText(Integer.toString(newValue.getId_Reclamation()));
                
            }
        });
        
        

    }
    
    
    
    
    
    
    
}
