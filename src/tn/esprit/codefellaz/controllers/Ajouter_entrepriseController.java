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
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.entities.entreprise;
import tn.esprit.codefellaz.services.UserService;
import tn.esprit.codefellaz.services.entreprise_CRUD;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class Ajouter_entrepriseController implements Initializable {

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
    private Text alertRole;
    @FXML
    private Text alertTel;
    @FXML
    private TextArea description_entreprise;
    @FXML
    private TextField id_representant;
    @FXML
    private TextField adresse;
    @FXML
    private TextField tel_entreprise;
private Label label;
    @FXML
    private Button Timage;
    @FXML
    private ImageView imgajoutincours;
    @FXML
    private Label imgpathttt;
    @FXML
    private Button back;
    @FXML
    private Button entreprise;
    Parent fxml;
    @FXML
    private AnchorPane root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         //get the current freelancer id
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
       
            
             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
                id_representant.setText(Integer.toString(userconnected.getId()));
        id_representant.setEditable(false);
                
     
            } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        entreprise.setOnAction(RETOUR -> {
            try {

                     fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/gerer_entreprise.fxml"));
                      root.getChildren().removeAll() ;
                      root.getChildren().setAll(fxml) ;

                   } catch (IOException ex) {
                       System.out.println(ex.getMessage());
                   }

       });
        
        
        
    }    

    @FXML
    private void handleButtonSubmitAction(ActionEvent event) throws IOException, SQLException {
        
        
         entreprise_CRUD productService = new entreprise_CRUD();

        if (id_representant.getText().equals("") || adresse.getText().equals("") || tel_entreprise.getText().equals("")
                || description_entreprise.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Veuillez saisir tous les champs ");
            a.setHeaderText(null);
            a.showAndWait();
        } 
    
        
        
        else {

  entreprise ccc = new entreprise(Integer.parseInt( id_representant.getText()), adresse.getText(),
                     Integer.parseInt(tel_entreprise.getText()), description_entreprise.getText(),imgpathttt.getText() );
                   
           

   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmer ");
      alert.setHeaderText("Confirmer");
      alert.setContentText(" ");
      
         Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
                 productService.Ajouterentreprise(ccc);
       TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.SLIDE;
            tray.setAnimationType(type);
            tray.setTitle("Ajouté avec succés");
            tray.setMessage("Ajoutéavec succés");
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
        Parent page1 = FXMLLoader.load(getClass().getResource("gerer_entreprise.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
}
