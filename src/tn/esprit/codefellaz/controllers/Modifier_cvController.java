/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import tn.esprit.codefellaz.entities.cv;
import tn.esprit.codefellaz.entities.entreprise;
import tn.esprit.codefellaz.services.CV_CRUD;
import tn.esprit.codefellaz.services.entreprise_CRUD;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class Modifier_cvController implements Initializable {

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
    private Text alertTel;
    @FXML
    private Label labelid;
    @FXML
    private TextField annee_diplome;
    @FXML
    private TextField specialite;
    @FXML
    private TextField diplome;
    @FXML
    private TextField institut;
private Label label;
    @FXML
    private TextArea bio;
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
 labelid.setText(Integer.toString(Gerer_cvController.connected_cv.getId_cv()));
        annee_diplome.setText(Integer.toString(Gerer_cvController.connected_cv.getAnnee_diplome()));
        specialite.setText(Gerer_cvController.connected_cv.getSpecialite());
        bio.setText(Gerer_cvController.connected_cv.getBio_cv());
        diplome.setText(Gerer_cvController.connected_cv.getDiplome());
        
        institut.setText(Gerer_cvController.connected_cv.getInstitut());
        
    }    

    @FXML
    private void handleButtonSubmitAction(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException {
           
         CV_CRUD productService = new CV_CRUD();

        if (annee_diplome.getText().equals("") || specialite.getText().equals("") || bio.getText().equals("")
                || institut.getText().equals("")|| diplome.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Veuillez saisir tous les champs ");
            a.setHeaderText(null);
            a.showAndWait();
        } 
    
        
        
        else {

  cv ccc = new cv(Integer.parseInt(labelid.getText()), bio.getText(), diplome.getText(),
                     Integer.parseInt(annee_diplome.getText()), institut.getText(),specialite.getText(),imgpathttt.getText() );
                   
           

   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmer ");
      alert.setHeaderText("Confirmer");
      alert.setContentText(" ");
      
         Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
                 productService.modifiercv(ccc);
      TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.SLIDE;
            tray.setAnimationType(type);
            tray.setTitle("Modifé avec succés");
            tray.setMessage("Modifié avec succés");
            tray.setNotificationType(NotificationType.INFORMATION);
            tray.showAndDismiss(Duration.millis(3000));
  Parent page1 = FXMLLoader.load(getClass().getResource("gerer_cv.fxml"));
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
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgajoutincours.setImage(image);
            imgajoutincours.setFitWidth(200);
            imgajoutincours.setFitHeight(200);
            imgajoutincours.scaleXProperty();
            imgajoutincours.scaleYProperty();
            imgajoutincours.setSmooth(true);
            imgajoutincours.setCache(true);
            FileInputStream fin = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fin.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            byte[] person_image = bos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger("ss");
        }
        imgpathttt.setText(file.getAbsolutePath());
    }
    
    @FXML
    private void back(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("gerer_cv.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
}   

