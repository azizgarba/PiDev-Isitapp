/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import javax.mail.MessagingException;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;
import tn.esprit.codefellaz.utils.MyConnection;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import tn.esprit.codefellaz.services.UserService;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class ForgotPassController implements Initializable {

    @FXML
    private Button btnforgpass;
    @FXML
    private TextField mailforgpass;
    Connection cnx1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnforgpass.setOnAction(e -> {
            cnx1 = MyConnection.getInstance().getCnx();

            String query2 = "select * from utilisateurs where email=? ";
            String code = "empty";
            try {

                PreparedStatement smt = cnx1.prepareStatement(query2);
                smt.setString(1, mailforgpass.getText());
                ResultSet rs = smt.executeQuery();
                if (rs.next()) {
                    code = rs.getString("code_verification");
                    
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Information Dialog");

                    alert.setHeaderText(null);

                    alert.setContentText("Adresse introuvable!");

                    alert.show();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            UserService userser = new UserService();
            userser.updatePassmail(code);
            sendMail(code);

        });

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
            alert.setContentText("Consulter votre boite mail !!");
            alert.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    private Message preparedMessage(Session session, String myAccountEmail, String recepient) {
        String query2 = "select * from utilisateurs where email=?";
        String userEmail = "null";
        String pass = "empty";
        recepient = mailforgpass.getText();
        try {
            cnx1 = MyConnection.getInstance().getCnx();
            PreparedStatement smt = cnx1.prepareStatement(query2);
            smt.setString(1, mailforgpass.getText());
            ResultSet rs = smt.executeQuery();
            System.out.println(rs);
            if (rs.next()) {
                pass = rs.getString("code_verification");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        String text = "Votre mot de passe est :" + pass + "";
        String object = "Recupération de mot de passe";
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
