/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage; 
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
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
import tn.esprit.codefellaz.entities.Produit;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.entities.cv;
import tn.esprit.codefellaz.entities.entreprise;
import tn.esprit.codefellaz.services.AchatService;
import tn.esprit.codefellaz.services.CV_CRUD;
import tn.esprit.codefellaz.services.ProduitService;
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
public class Ajouter_cvController implements Initializable {
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
    private Text alertTel;
    @FXML
    private TextField annee_diplome;
    @FXML
    private TextField specialite;
    @FXML
    private TextField diplome;
    @FXML
    private TextField institut;
    @FXML
    private TextArea bio;
    @FXML
    private Button Timage;
    @FXML
    private ImageView imgajoutincours;
    @FXML
    private Label imgpathttt;
    @FXML
    private Button back;
    @FXML
    private Button but_entre;
    @FXML
    private Button but_offre;
    
    private Parent fxml; 
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         but_entre.setOnAction(RETOUR -> {
            try {

                     fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/gerer_entreprise.fxml"));
                      root.getChildren().removeAll() ;
                      root.getChildren().setAll(fxml) ;

                   } catch (IOException ex) {
                       System.out.println(ex.getMessage());
                   }

       });
          but_offre.setOnAction(RETOUR -> {
            try {

                     fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/gerer_offre_de_travail.fxml"));
                      root.getChildren().removeAll() ;
                      root.getChildren().setAll(fxml) ;

                   } catch (IOException ex) {
                       System.out.println(ex.getMessage());
                   }

       });
        
        
        
        
    }    

    @FXML
    private void handleButtonSubmitAction(ActionEvent event) throws SQLException, IOException, MessagingException {
             
        
         CV_CRUD productService = new CV_CRUD();

        if (annee_diplome.getText().equals("") || specialite.getText().equals("") || bio.getText().equals("")
                || institut.getText().equals("")|| diplome.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Veuillez saisir tous les champs ");
            a.setHeaderText(null);
            a.showAndWait();
        } 
    
        
        
        else {

//  cv c1 = new cv( bio.getText(), diplome.getText(),
//                     Integer.parseInt(annee_diplome.getText()), institut.getText(),specialite.getText(),3,"aaa" );
                   
           

   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmer ");
      alert.setHeaderText("Confirmer");
      alert.setContentText(" ");
      
         Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
            
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
            

             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
            

            
          cv c1 = new cv( bio.getText(), diplome.getText(),
                     Integer.parseInt(annee_diplome.getText()), institut.getText(),specialite.getText(),userconnected.getId(),imgpathttt.getText() );
          System.out.println(c1);
                 productService.Ajoutercv(c1);
                   TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.SLIDE;
            tray.setAnimationType(type);
            tray.setTitle("Ajouté avec succés");
            tray.setMessage("Ajoutéavec succés");
            tray.setNotificationType(NotificationType.INFORMATION);
            tray.showAndDismiss(Duration.millis(3000));
     
              sendMail("khouloud.bendaira@esprit.tn", "Cv ajouté avec succées", 
                    "Ajouté avec succées");
                 
            
            
//  Parent page1 = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/gerer_cv.fxml"));
//            Scene scene = new Scene(page1);
//
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();
          } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
      } else if (option.get() == ButtonType.CANCEL) {
      
      } else {
         this.label.setText("-");
      }
      

          

        };
    }
    public static void sendMail(String recipient,String Subject,String Text) throws MessagingException {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.port", "587");
        String myAccountEmail = "isitappofficial@gmail.com";
        String passwordd = "xfjoknbttjpurezw";
        Session session = Session.getInstance(properties, new Authenticator() {
             @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(myAccountEmail, passwordd);
            }
        });
            
        Message message = prepareMessage(session, myAccountEmail, recipient,Subject,Text);

        javax.mail.Transport.send(message);
        System.out.println("Message sent successfully");
    }  
   
    
    private static Message prepareMessage(Session session, String myAccountEmail, String recipient,String Subject,String Text) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(Subject);
            message.setText(Text);
            return message;
        } catch (MessagingException ex) {
          
        }
        return null;} 

@FXML
    private void addimgcours(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
       
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

