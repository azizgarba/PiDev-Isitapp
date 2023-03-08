/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.scene.Node;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.paint.Color;
import tn.esprit.codefellaz.entities.Projets;
import tn.esprit.codefellaz.entities.Taches;
import tn.esprit.codefellaz.services.ProjetsCRUD;
import tn.esprit.codefellaz.services.TachesSERVICES;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author myriam-PC
 */
public class AjouterTacheController implements Initializable {

    @FXML
    private TextField nom_tache;
    @FXML
    private DatePicker dateL_tache;
    @FXML
    private TextArea description_tache;
    @FXML
    private Button enregistrer_tache;
    @FXML
    private TextField priorite_tache;
    @FXML
    private ChoiceBox<String> type_tachebox;
    @FXML
    private Button revenir_affichage;
    @FXML
    private Label nomtachecontrol;
    @FXML
    private Label prioritetachecontrol;
    @FXML
    private Label datetachecontrol;
    Statement ste;
    Connection conn = MyConnection.getInstance().getCnx();
    @FXML
    private ChoiceBox<String> id_project;
    @FXML
    private Label idhere;
    @FXML
    private ChoiceBox<String> choix_tache;
    @FXML
    private Button modifier_tache;
    @FXML
    private Label hiddenidtache;
    @FXML
    private Label desccontrol;

    //afficher ca fel supprimer tache ou modifier ou ajout 
    private void handeltache() {
        int selectedIndex = id_project.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            id_project.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("projet non selectionné");

            alert.setHeaderText(null);

            alert.setContentText("Veuillez selectionnez un projet");

            alert.show();
            alert.showAndWait();
        }
    }

    public int getidprojet(String nom) {

        List<Projets> proj = new ArrayList<>();
        int id = 0;
        try {

            ste = conn.createStatement();
            String req = "SELECT id_projet FROM `Projets`WHERE nom_projet='" + nom + "'";
            ResultSet result = ste.executeQuery(req);
            while (result.next()) {

                Projets proo = new Projets();

                proo.setId_projet(result.getInt(1));
                proj.add(proo);
                System.out.println("ceci marche");

            }

        } catch (SQLException ex) {

            System.err.println(ex.getMessage());
        }
        for (Projets projj : proj) {
            id = projj.getId_projet();
        }
        return id;
    }

//get nom des projets depuis la base de données
    public List<String> getNomProjDatabase() throws SQLException {
        List<String> itemss = new ArrayList<>();

        Connection conn = MyConnection.getInstance().getCnx();
        PreparedStatement req = conn.prepareStatement("SELECT `nom_projet` FROM `Projets`");
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            String nom_projet = rs.getString("nom_projet");
            itemss.add(nom_projet);
        }

        rs.close();
        req.close();

        return itemss;
    }

    //get nom des taches depuis la base de données
    public List<String> getNomTacheDatabase(int id_projet) throws SQLException {
        List<String> NomTache = new ArrayList<>();

        Connection conn = MyConnection.getInstance().getCnx();
        PreparedStatement req = conn.prepareStatement("SELECT `nom_tache` FROM `Taches` WHERE id_projet = '" + id_projet + "'");
        ResultSet rs = req.executeQuery();

        while (rs.next()) {
            String nom_tache = rs.getString("nom_tache");
            NomTache.add(nom_tache);
        }

        rs.close();
        req.close();

        return NomTache;
    }

    public void initialize(URL location, ResourceBundle resources) {

        try {
            List<String> items = getNomProjDatabase();
            id_project.getItems().addAll(items);
            System.out.println(items.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Ajouter un ChangeListener pour mettre à jour le texte du Label en fonction de la sélection de la ChoiceBox
        id_project.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            Connection conn = MyConnection.getInstance().getCnx();
            try {

                PreparedStatement prep = conn.prepareStatement("SELECT id_projet FROM Projets WHERE nom_projet =?");
                prep.setString(1, newValue);
                ResultSet rs = prep.executeQuery();
                if (rs.next()) {

                    //afficher sur les textfields
                    System.out.println(rs.getString("id_projet"));
                    idhere.setText(rs.getString("id_projet"));

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println("projet selectionné :" + newValue);

            getidprojet(newValue);
            try {
                List<String> nomtaches = getNomTacheDatabase(getidprojet(newValue));
                choix_tache.getItems().clear();
                choix_tache.getItems().addAll(nomtaches);

                System.out.println(nomtaches.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        );

        //tache choicebox listener
        choix_tache.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue1) -> {

            Connection conn = MyConnection.getInstance().getCnx();
            try {

                PreparedStatement prep1 = conn.prepareStatement("SELECT id_tache, nom_tache,priorite_tache, dateL_tache, description_tache, type_tache FROM Taches WHERE nom_tache =?");
                prep1.setString(1, newValue1);
                ResultSet rs = prep1.executeQuery();
                if (rs.next()) {

                    //afficher sur le hidden textfield
                    System.out.println(rs.getString("id_tache"));
                    hiddenidtache.setText(rs.getString("id_tache"));
                    //afficher sur les textfields
                    nom_tache.setText(rs.getString("nom_tache"));
                    priorite_tache.setText(rs.getString("priorite_tache"));
                    description_tache.setText(rs.getString("description_tache"));
                    dateL_tache.setValue(LocalDate.parse(rs.getString("dateL_tache"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    if (rs.getInt("type_tache") == 0) {

                        type_tachebox.getSelectionModel().select("A faire");

                    } else if (rs.getInt("type_tache") == 1) {

                        type_tachebox.getSelectionModel().select("En cour");

                    } else {

                        type_tachebox.getSelectionModel().select("Terminé");

                    }

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

        type_tachebox.getItems().add("A faire");
        type_tachebox.getItems().add("En cour");
        type_tachebox.getItems().add("Terminé");

        type_tachebox.getSelectionModel().select("A faire");

        enregistrer_tache.setOnAction(e -> {

            LocalDate currentDate = LocalDate.now();
            TachesSERVICES aj = new TachesSERVICES();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println(priorite_tache.getText());
            handeltache();

            //control saisie
            String nomT = nom_tache.getText().trim();
            String prioT = priorite_tache.getText().trim();
            String descT = description_tache.getText().trim();
            LocalDate dateLT = dateL_tache.getValue();

            if (dateLT == null || nomT.isEmpty() || prioT.isEmpty() || descT.isEmpty()) {

                datetachecontrol.setVisible(true);
                datetachecontrol.setText("Ce champ ne doit pas etre vide!");
                datetachecontrol.setTextFill(Color.color(1, 0, 0));
                dateL_tache.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;-fx-border-width: 4px");

                nomtachecontrol.setVisible(true);
                nomtachecontrol.setText("Ce champ ne doit pas etre vide!");
                nomtachecontrol.setTextFill(Color.color(1, 0, 0));
                nom_tache.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;-fx-border-width: 4px");

                prioritetachecontrol.setVisible(true);
                prioritetachecontrol.setText("Ce champ ne doit pas etre vide!");
                prioritetachecontrol.setTextFill(Color.color(1, 0, 0));
                priorite_tache.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;-fx-border-width: 4px");

                desccontrol.setVisible(true);
                desccontrol.setText("Ce champ ne doit pas etre vide!");
                desccontrol.setTextFill(Color.color(1, 0, 0));
                description_tache.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;-fx-border-width: 4px");

            } else {

                int p = Integer.parseInt(idhere.getText());
                Taches t = new Taches(nom_tache.getText(), Integer.parseInt(priorite_tache.getText()), currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), dateL_tache.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), description_tache.getText(), type_tachebox.getSelectionModel().getSelectedIndex(), p);
                aj.AjouterTache(t);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                try {

                    Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajoutTache.fxml"));
                    Scene s = new Scene(root);
                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.hide();
                    stage.setScene(s);
                    stage.show();

                    alert.setTitle("Information");

                    alert.setHeaderText(null);

                    alert.setContentText("Tache ajouté avec succés");

                    alert.show();

                    Stage info = (Stage) enregistrer_tache.getScene().getWindow();

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            }
            nom_tache.setText("");
            priorite_tache.setText("");
            dateL_tache.setValue(currentDate);
            description_tache.setText("");

        }
        );
        ///control saisie 
        priorite_tache.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                priorite_tache.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        // Empêcher la saisie d'une date antérieure à la date actuelle
        dateL_tache.setDayCellFactory(picker -> new DateCell() {
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

        //////////////////////////////////////////////////////////////////////////////////    
        revenir_affichage.setOnAction(ra -> {

            Stage stage = (Stage) revenir_affichage.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/AfficherProjet.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Vos Projets");
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
        );
        modifier_tache.setOnAction(ra -> {

            LocalDate currentDate = LocalDate.now();

            Taches mofit = new Taches(Integer.parseInt(hiddenidtache.getText()), nom_tache.getText(), Integer.parseInt(priorite_tache.getText()), dateL_tache.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), description_tache.getText(), type_tachebox.getSelectionModel().getSelectedIndex(), Integer.parseInt(idhere.getText()));
            TachesSERVICES modif = new TachesSERVICES();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Modification");
            alert.setHeaderText("Voulez-vous modifier cette tache ?");
            alert.setContentText("Cliquez sur Oui pour la modifier, ou sur Non pour abandonner.");

            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                // modifier la tache

                modif.ModifierTache(mofit);
                Stage info = (Stage) modifier_tache.getScene().getWindow();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajoutTache.fxml"));
                    Scene s = new Scene(root);
                    Stage stage = (Stage) ((Node) ra.getSource()).getScene().getWindow();
                    stage.hide();
                    stage.setScene(s);
                    stage.show();
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

                    alert2.setTitle("Information");

                    alert2.setHeaderText(null);

                    alert2.setContentText("Tache modifiée avec succés");

                    alert2.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }
        );

    }
}
