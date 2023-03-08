/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.UserService;
import tn.esprit.codefellaz.utils.MyConnection;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField nameins;
    @FXML
    private TextField mailins;
    @FXML
    private TextField telins;
    @FXML
    private DatePicker naisins;
    @FXML
    private ComboBox<String> genrins;
    @FXML
    private PasswordField passins;
    @FXML
    private PasswordField vpassins;
    @FXML
    private ComboBox<String> rolins;
    @FXML
    private Button photins;
    @FXML
    private Button valins;
    private String filePath;
    Connection cnx1;
    public String fileName;
    public Path source;
    public Path destination;
    public String imagePath;
    @FXML
    private ImageView photplace;
    ObservableList<String> genrelist = FXCollections.observableArrayList("Homme", "femme");
    ObservableList<String> rolelist = FXCollections.observableArrayList("Client", "Freelancer", "Représentant d'entreprise");

    /**
     * Initializes the controller class.
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        genrins.setItems(genrelist);
        rolins.setItems(rolelist);
        photins.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose an image file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.jfif")
            );
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                try {
                    Image image = new Image(selectedFile.toURI().toString());
                    photplace.setImage(image);
                    fileName = selectedFile.getName();
                    source = selectedFile.toPath();
                    destination = Paths.get("C:/xampp/htdocs/img/" + fileName);

                    System.out.println("image" + destination);
                    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

                    imagePath = "/img/" + fileName;
                    
        byte[] fileContent = Files.readAllBytes(destination);

        // Encode the byte array as Base64
        String base64Encoded = Base64.getEncoder().encodeToString(fileContent);

        // Upload the Base64 encoded data to the server
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost/img/upload.php");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("photo", base64Encoded));
        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            
            System.out.println("image uploaded");
        } else {
            
            System.out.println("image not uploded");
        }

                } catch (Exception e) {
                    System.out.println("Failed to load image file.");
                    e.printStackTrace();
                }
            }
        });
        valins.setOnAction(event -> {

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (nameins.getText().isEmpty() || mailins.getText().isEmpty() || telins.getText().isEmpty() || naisins.getValue().format(dateFormatter).isEmpty() || genrins.getValue().isEmpty() || passins.getText().isEmpty() || vpassins.getText().isEmpty() || rolins.getValue().isEmpty() || imagePath.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Veuillez remplir tous les champs!");

                alert.show();
            } else if (isNumeric(telins.getText()) == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Veuillez saisir seulement des chifres pour votre numéro de téléphone!");

                alert.show();
            } else if (ValidationEmail() == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Veuillez respecter le format d'un courriel(exemple@domaine.com)!");

                alert.show();
            } else if (!passins.getText().equals(vpassins.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Les mots de passe ne sont pas conforme");

                alert.show();
            } else {

                UserService userserv = new UserService();
                User user = new User(nameins.getText(), mailins.getText(), telins.getText(), naisins.getValue().format(dateFormatter), genrins.getValue(), passins.getText(), Integer.toString((int) (Math.random() * 10000)), rolins.getValue(), imagePath);

                userserv.ajouterUser(user);
                cnx1 = MyConnection.getInstance().getCnx();

                String query2 = "select * from utilisateurs where email=? ";
                String code = "empty";
                try {

                    PreparedStatement smt = cnx1.prepareStatement(query2);
                    smt.setString(1, mailins.getText());
                    ResultSet rs = smt.executeQuery();
                    if (rs.next()) {
                        code = rs.getString("code_verification");

                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                sendMail(code);
            }

        });

    }

    public boolean ValidationEmail() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
        Matcher match = pattern.matcher(mailins.getText());

        if (match.find() && match.group().equals(mailins.getText())) {
            return true;
        } else {

            return false;
        }
    }

    public void sendMail(String recepient) {

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
        Message message = preparedMessage(session, myAccountEmail, recepient);
        try {
            Transport.send(message);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("IsItApp :: Boite Mail");
            alert.setHeaderText(null);
            alert.setContentText("Un code de vérification sera envoyé par mail !!");
            alert.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    private Message preparedMessage(Session session, String myAccountEmail, String recepient) {
        String query2 = "select * from utilisateurs where email=?";
        String userEmail = "null";
        String pass = "empty";
        recepient = mailins.getText();
        try {
            cnx1 = MyConnection.getInstance().getCnx();
            PreparedStatement smt = cnx1.prepareStatement(query2);
            smt.setString(1, mailins.getText());
            ResultSet rs = smt.executeQuery();
            System.out.println(rs);
            if (rs.next()) {
                pass = rs.getString("code_verification");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        String text = "Votre code de vérification est :" + pass + "";
        String object = "Vérification de compte";
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(object);
            String htmlcode = "<h1> " + text + " </h1> <h2> <b> </b2> </h2> ";
            message.setContent(htmlcode, "text/html");

            return message;

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
