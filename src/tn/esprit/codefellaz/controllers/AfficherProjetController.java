/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.awt.Image;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.Alert;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tn.esprit.codefellaz.entities.Taches;
import tn.esprit.codefellaz.services.TachesSERVICES;
import tn.esprit.codefellaz.services.ProjetsCRUD;
import tn.esprit.codefellaz.entities.Projets;
import tn.esprit.codefellaz.utils.MyConnection;

import org.controlsfx.control.Notifications;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.UserService;

/**
 * FXML Controller class
 *
 * @author myriam-PC
 */
public class AfficherProjetController implements Initializable {

    @FXML
    private ChoiceBox<String> choix_projet;

    @FXML
    private Button ajouter_tache;
    private ObservableList<Projets> items = FXCollections.observableArrayList();

    ObservableList<Projets> listp;
    ObservableList<Projets> datalist;
    @FXML
    private Button modifier_projet;
    @FXML
    private Button supprimer_projet;
    @FXML
    private Button ajouter_projett;
    @FXML
    private TextField priofield;
    @FXML
    private TextField dateLfield;
    @FXML
    private TextArea descfield;
    @FXML
    private Label choixaffichage;
    Statement ste;
    Connection conn = MyConnection.getInstance().getCnx();
    @FXML
    private TextField datePfield;
    @FXML
    private ListView<String> listafaire;
    @FXML
    private ListView<String> listeencours;
    @FXML
    private ListView<String> listetermine;
    @FXML
    private Button suppriafaire;
    @FXML
    private Button suppriencours;
    @FXML
    private Button suppritermine;
    @FXML
    private Label hiddenid;
    @FXML
    private Button modifier_tache;
    @FXML
    private Button reload;
    @FXML
    private Button deadline;
    @FXML
    private ProgressBar progressP;
    @FXML
    private ChoiceBox<String> trie_projet;
    @FXML
    private Label progresslabel;

    /**
     * Initializes the controller class.
     */
    ////// methode for api
    public void showdeadline(String projectname) {
        LocalDate projetdeadline = null;
        try {

            Connection conn = MyConnection.getInstance().getCnx();
            PreparedStatement sta = conn.prepareStatement("SELECT dateL_projet FROM Projets WHERE nom_projet=?");
            sta.setString(1, projectname);
            ResultSet rs = sta.executeQuery();
            if (rs.next()) {
                projetdeadline = rs.getDate("dateL_projet").toLocalDate();

            }

        } catch (SQLException ex) {

            System.err.println(ex.getMessage());
        }
        if (projetdeadline != null) {

            // Calculate the number of days until the project deadline
            long daysUntilDeadline = ChronoUnit.DAYS.between(LocalDate.now(), projetdeadline);

            Notifications.create()
                    .title("Deadline du projet " + projectname)
                    .text("vous avez " + daysUntilDeadline + " jours avant la date limite du projet  " + projectname + ".")
                    .darkStyle()
                    .showWarning();

        }

    }

    //methode efficace en cas de non selection de projet 
    private void handeldeleteprojet() {
        int selectedIndex = choix_projet.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            choix_projet.getItems().remove(selectedIndex);
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("projet non selectionné");

            alert.setHeaderText(null);

            alert.setContentText("Veuillez selectionnez un projet afin de le supprimer");

            alert.show();
            alert.showAndWait();
        }
    }

    //get names of projects and displays it on choicebox 
    public List<String> getItemsFromDatabase(int id) throws SQLException {
        List<String> itemss = new ArrayList<>();
        //// Créer un Map pour stocker la priorité de chaque projet

        //Map<String, Integer> projectPriorities = new HashMap<>();
        Connection conn = MyConnection.getInstance().getCnx();
        PreparedStatement req = conn.prepareStatement("SELECT `nom_projet` FROM `Projets` where id_freelancer = ?");
          req.setInt(1, id);
        ResultSet rs = req.executeQuery();
            
        while (rs.next()) {
            String nomProjet = rs.getString("nom_projet");
            itemss.add(nomProjet);
        }

        rs.close();
        req.close();

        return itemss;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        trie_projet.getItems().addAll("Nbr taches", "Priorité");
//style////////////////////////////////////////////////////////////////////////
        listafaire.setStyle("-fx-background-color:  linear-gradient(from 10% 25% to 100% 100%,#e24933,#f7cec8); -fx-padding: 10px;");
        listeencours.setStyle("-fx-background-color:  linear-gradient(from 10% 25% to 100% 100%,#e24933,#f7cec8); -fx-padding: 10px;");
        listetermine.setStyle("-fx-background-color:  linear-gradient(from 10% 25% to 100% 100%,#e24933,#f7cec8); -fx-padding: 10px;");

          try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
         

             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
             
        try {
            List<String> items = getItemsFromDatabase(userconnected.getId());
            choix_projet.getItems().addAll(items);
            System.out.println(items.toString());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Taches> listtache = new ArrayList<>();
        List<Taches> listtache2 = new ArrayList<>();
        List<Taches> listtache3 = new ArrayList<>();

        //////////////////////////////tri////////////////////////////////////////
        trie_projet.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == "Nbr taches") {

                try {
                    String query = "SELECT Projets.*, COUNT(t.id_tache) AS taches_count "
                            + "FROM Projets "
                            + "LEFT JOIN Taches t ON Projets.id_projet = t.id_projet "
                            + "GROUP BY Projets.id_projet "
                            + "ORDER BY taches_count DESC";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet Set = statement.executeQuery();
                    choix_projet.getItems().clear();
                    while (Set.next()) {
                        String projetName = Set.getString("nom_projet");
                        choix_projet.getItems().add(projetName);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (newValue == "Priorité") {

                try {
                    String query = "SELECT Projets.* "
                            + "FROM Projets  "
                            + "ORDER BY Projets.priorite_projet ASC";
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();
                    choix_projet.getItems().clear();
                    while (resultSet.next()) {
                        String projetName = resultSet.getString("nom_projet");
                        choix_projet.getItems().add(projetName);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            progresslabel.setText("");
            progressP.setProgress(0);
            choixaffichage.setText("");
            priofield.setText("");
            dateLfield.setText("");
            descfield.setText("");
            datePfield.setText("");
            listafaire.getItems().clear();
            listeencours.getItems().clear();
            listetermine.getItems().clear();
        });

        // Ajouter un ChangeListener pour mettre à jour le texte du Label en fonction de la sélection de la ChoiceBox
        choix_projet.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            ObservableList<String> data = FXCollections.observableArrayList();
            ObservableList<String> data2 = FXCollections.observableArrayList();
            ObservableList<String> data3 = FXCollections.observableArrayList();

            Connection conn = MyConnection.getInstance().getCnx();
            try {

                ////////////////////////////////////////////////////////////////
                String querytermine = "SELECT COUNT(*) AS totalTachesAchevees FROM Taches WHERE type_tache = 2 AND id_projet = (SELECT id_projet FROM Projets WHERE nom_projet = ?)";
                PreparedStatement stat = conn.prepareStatement(querytermine);
                stat.setString(1, newValue);
                ResultSet Set = stat.executeQuery();
                int totalTachesAchevees = 0;
                ////////////////////////////////////////////////////////////////
                String querycours = "SELECT COUNT(*) AS totalTachesinprogress FROM Taches WHERE type_tache = 1 AND id_projet = (SELECT id_projet FROM Projets WHERE nom_projet = ?)";
                PreparedStatement state = conn.prepareStatement(querycours);
                state.setString(1, newValue);
                ResultSet Ssset = state.executeQuery();
                int totalTachesinprogress = 0;
                ////////////////////////////////////////////////////////////////
                String querytotal = "SELECT COUNT(*) AS totalTaches FROM taches WHERE id_projet = (SELECT id_projet FROM Projets WHERE nom_projet = ?)";
                PreparedStatement prepper = conn.prepareStatement(querytotal);
                prepper.setString(1, newValue);
                ResultSet Sset = prepper.executeQuery();
                int totalTaches = 0;
                ////////////////////////////////////////////////////////////////
                PreparedStatement prep = conn.prepareStatement("SELECT priorite_projet,dateP_projet,dateL_projet,description_projet FROM Projets WHERE nom_projet =?");
                prep.setString(1, newValue);
                ResultSet rs = prep.executeQuery();
                //pour laffichage listeafaire
                String queryTache0 = "SELECT id_tache ,nom_tache, priorite_tache,dateL_tache,description_tache FROM Taches WHERE type_tache=0 AND id_projet = (SELECT id_projet FROM Projets WHERE nom_projet = '" + newValue + "')";
                ste = conn.createStatement();
                ResultSet rs1 = ste.executeQuery(queryTache0);
                //pour l'afficher liste en cours
                String queryTache1 = "SELECT id_tache ,nom_tache, priorite_tache,dateL_tache,description_tache FROM Taches WHERE type_tache=1 AND id_projet = (SELECT id_projet FROM Projets WHERE nom_projet = '" + newValue + "')";
                ste = conn.createStatement();
                ResultSet rs2 = ste.executeQuery(queryTache1);
                //pour la liste terminé
                String queryTache2 = "SELECT id_tache ,nom_tache, priorite_tache,dateL_tache,description_tache FROM Taches WHERE type_tache=2 AND id_projet = (SELECT id_projet FROM Projets WHERE nom_projet = '" + newValue + "')";
                ste = conn.createStatement();
                ResultSet rs3 = ste.executeQuery(queryTache2);

                //lel liste terminé
                while (rs3.next()) {
                    int idtache3 = rs3.getInt(1);
                    String nomTache3 = rs3.getString("nom_tache");
                    int prioriteTache3 = rs3.getInt("priorite_tache");
                    String dateLtache3 = rs3.getString("dateL_tache");
                    String descripTache3 = rs3.getString("description_tache");
                    Taches r3 = new Taches(idtache3, nomTache3, prioriteTache3, dateLtache3, descripTache3, 2, 400);
                    listtache3.add(r3);
                    data3.add("Tâche : " + nomTache3 + ", de Priorité : " + prioriteTache3 + "\n doit etre terminé avant le : " + dateLtache3 + " \n Description : " + descripTache3);
                }
                //lel liste en cours
                while (rs2.next()) {
                    int idtache2 = rs2.getInt(1);
                    String nomTache2 = rs2.getString("nom_tache");
                    int prioriteTache2 = rs2.getInt("priorite_tache");
                    String dateLtache2 = rs2.getString("dateL_tache");
                    String descripTache2 = rs2.getString("description_tache");
                    Taches r2 = new Taches(idtache2, nomTache2, prioriteTache2, dateLtache2, descripTache2, 1, 400);
                    listtache2.add(r2);
                    data2.add("Tâche : " + nomTache2 + ", de Priorité : " + prioriteTache2 + "\n doit etre terminé avant le : " + dateLtache2 + " \n Description : " + descripTache2);
                }
                //lel liste afaire
                while (rs1.next()) {
                    int idtache = rs1.getInt(1);
                    String nomTache = rs1.getString("nom_tache");
                    int prioriteTache = rs1.getInt("priorite_tache");
                    String dateLtache = rs1.getString("dateL_tache");
                    String descripTache = rs1.getString("description_tache");

                    Taches r = new Taches(idtache, nomTache, prioriteTache, dateLtache, descripTache, 0, 400);
                    listtache.add(r);
                    data.add("Tâche : " + nomTache + ", de Priorité : " + prioriteTache + "\n doit etre terminé avant le : " + dateLtache + " \n Description : " + descripTache);
                }
                while (Set.next()) {
                    totalTachesAchevees = Set.getInt("totalTachesAchevees");
                }
                while (Ssset.next()) {
                    totalTachesinprogress = Ssset.getInt("totalTachesinprogress");
                }
                while (Sset.next()) {
                    totalTaches = Sset.getInt("totalTaches");
                }

                double ratio = (((double) totalTachesAchevees * 1) + ((double) totalTachesinprogress * 0.5)) / (double) totalTaches;
                int rat = 0;
                rat = (int) (ratio * 100);
                progressP.setProgress(ratio);
                progresslabel.setText("Projet " + newValue + "\n" + Integer.toString(rat) + "% terminé.");

                // progresslabel.setText(+ratio "Done");
                if (rs.next()) {

                    //afficher sur les textfields
                    priofield.setText(rs.getString("priorite_projet"));
                    dateLfield.setText(rs.getString("dateL_projet"));
                    datePfield.setText(rs.getString("dateP_projet"));
                    descfield.setText(rs.getString("description_projet"));

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("projet selectionné :" + newValue);
            //affichage sur le label
            choixaffichage.setText("Vous etes dans " + newValue);
            hiddenid.setText(newValue);

            listafaire.setItems(data);
            listeencours.setItems(data2);
            listetermine.setItems(data3);

        }
        );

////////////////////appel  methode api
        deadline.setOnAction(mm -> {

            String projectname = choix_projet.getValue();
            if (projectname != null) {

                showdeadline(projectname);

            }

        }
        );

        ////////////////////////////////////
        reload.setOnAction(load -> {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/AfficherProjet.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) load.getSource()).getScene().getWindow();
                stage.hide();
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        );

        ajouter_tache.setOnAction(mm -> {

            //switch to tache
            Stage stage = (Stage) ajouter_tache.getScene().getWindow();
            try {

                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/AjoutTache.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Taches");
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
        );

        ajouter_projett.setOnAction(i -> {

            //switch to ajout projet
            Stage stage = (Stage) ajouter_projett.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouterProjet.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Projets");
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
        );

        modifier_projet.setOnAction(mp -> {

            handeldeleteprojet();
            String selectedProjet = choix_projet.getValue();

            choix_projet.setValue(selectedProjet);

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Enregistrer les modifications?");
            alert.setHeaderText("Voulez-vous enregistrer les modifications ?");
            alert.setContentText("Cliquez sur Oui pour enregistrer les modifications, ou sur Non pour les abandonner.");

            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                // Enregistrer les modifications
                try {

                    String priorite_projet = priofield.getText();
                    String dateL_projet = dateLfield.getText();
                    String description_projet = descfield.getText();

                    Connection conn = MyConnection.getInstance().getCnx();

                    PreparedStatement prepp = conn.prepareStatement("UPDATE Projets SET priorite_projet=?,dateL_projet=?,description_projet=? WHERE nom_projet=?");
                    prepp.setString(1, priorite_projet);
                    prepp.setString(2, dateL_projet);
                    prepp.setString(3, description_projet);
                    prepp.setString(4, selectedProjet);

                    prepp.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            } else {

                String newValue = choix_projet.getSelectionModel().selectedItemProperty().getValue();
                Connection conn = MyConnection.getInstance().getCnx();
                try {

                    PreparedStatement prep = conn.prepareStatement("SELECT priorite_projet,dateP_projet,dateL_projet,description_projet FROM Projets WHERE nom_projet =?");
                    prep.setString(1, newValue);
                    ResultSet rs = prep.executeQuery();

                    if (rs.next()) {

                        //afficher sur les textfields
                        priofield.setText(rs.getString("priorite_projet"));
                        dateLfield.setText(rs.getString("dateL_projet"));
                        datePfield.setText(rs.getString("dateP_projet"));
                        descfield.setText(rs.getString("description_projet"));

                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.out.println("projet selectionné :" + newValue);
                //affichage sur le label
                choixaffichage.setText("Vous etes dans " + newValue);

                items.contains(newValue);
                choix_projet.getSelectionModel().select(newValue);
            }
        }
        );

        supprimer_projet.setOnAction(i -> {

            handeldeleteprojet();
            String selectedProjet = choix_projet.getValue();
            int p = getidprojet(selectedProjet);
            String priorite_projet = priofield.getText();
            String dateL_projet = dateLfield.getText();
            String description_projet = descfield.getText();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("Voulez-vous supprimer ce projet ?");
            alert.setContentText("Cliquez sur Oui pour le supprimer, ou sur Non pour abandonner.");

            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                // supprimer le projet

                Projets r = new Projets();
                Taches t = new Taches();
                TachesSERVICES tachto = new TachesSERVICES();
                ProjetsCRUD prop = new ProjetsCRUD();
                prop.SupprimerProjet2(selectedProjet);
                tachto.SupprimerTache(p);
                //  prop.SupprimerProjet(test.getId_projet());
                priofield.setText("");
                dateLfield.setText("");
                descfield.setText("");
                datePfield.setText("");

                listafaire.getItems().clear();
                listeencours.getItems().clear();
                listetermine.getItems().clear();
                progresslabel.setText("");
                progressP.setProgress(0.0);
                choixaffichage.setText("");

            }

        }
        );

        // tache selection suppresion tache a faire
        listafaire.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                System.out.println(newValue);
                System.out.println(listtache);
                for (Taches er : listtache) {
                    //  System.out.println(er.getId_projet());
                    if (newValue.contains(er.getNom_tache()) && newValue.contains(Integer.toString(er.getPriorite_tache())) && newValue.contains(er.getDateL_date()) && newValue.contains(er.getDescription_tache())) {

                        suppriafaire.setOnAction(sf -> {

                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Suppression");
                            alert.setHeaderText("Voulez-vous supprimer cette tache ?");
                            alert.setContentText("Cliquez sur Oui pour la supprimer, ou sur Non pour abandonner.");

                            ButtonType buttonTypeYes = new ButtonType("Oui");
                            ButtonType buttonTypeNo = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

                            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == buttonTypeYes) {
                                // supprimer tache

                                Taches t = new Taches();
                                String selectedtache = listafaire.getSelectionModel().getSelectedItem();

                                TachesSERVICES azerty = new TachesSERVICES();
                                azerty.SupprimerTache(er.getId_tache());
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

                                alert2.setTitle("Information");

                                alert2.setHeaderText(null);

                                alert2.setContentText("Tache supprimée avec succés");

                                alert2.show();

                            } else if (result.get() == buttonTypeNo) {
                                listafaire.getSelectionModel().clearSelection();

                            }
                        }
                        );

                    }
                }
            }
        });

        // tache selection suppression taches en cours
        listeencours.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                System.out.println(newValue);
                System.out.println(listtache2);
                for (Taches er : listtache2) {
                    if (newValue.contains(er.getNom_tache()) && newValue.contains(Integer.toString(er.getPriorite_tache())) && newValue.contains(er.getDateL_date()) && newValue.contains(er.getDescription_tache())) {

                        suppriencours.setOnAction(ssf -> {

                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Suppression");
                            alert.setHeaderText("Voulez-vous supprimer cette tache ?");
                            alert.setContentText("Cliquez sur Oui pour la supprimer, ou sur Non pour abandonner.");

                            ButtonType buttonTypeYes = new ButtonType("Oui");
                            ButtonType buttonTypeNo = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

                            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == buttonTypeYes) {
                                Taches tt = new Taches();
                                String selectedtache2 = listeencours.getSelectionModel().getSelectedItem();

                                TachesSERVICES azerty = new TachesSERVICES();
                                azerty.SupprimerTache(er.getId_tache());
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

                                alert2.setTitle("Information");

                                alert2.setHeaderText(null);

                                alert2.setContentText("Tache supprimée avec succés");
                                alert2.show();

                            } else if (result.get() == buttonTypeNo) {
                                listeencours.getSelectionModel().clearSelection();

                            }
                        }
                        );

                    }
                }
            }

        });

        // tache selection suppression taches termines
        listetermine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                System.out.println(newValue);
                System.out.println(listtache3);
                for (Taches er : listtache3) {

                    if (newValue.contains(er.getNom_tache()) && newValue.contains(Integer.toString(er.getPriorite_tache())) && newValue.contains(er.getDateL_date()) && newValue.contains(er.getDescription_tache())) {

                        suppritermine.setOnAction(sssf -> {

                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Suppression");
                            alert.setHeaderText("Voulez-vous supprimer cette tache ?");
                            alert.setContentText("Cliquez sur Oui pour la supprimer, ou sur Non pour abandonner.");

                            ButtonType buttonTypeYes = new ButtonType("Oui");
                            ButtonType buttonTypeNo = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

                            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == buttonTypeYes) {
                                Taches ttt = new Taches();
                                String selectedtache3 = listetermine.getSelectionModel().getSelectedItem();

                                TachesSERVICES azerty = new TachesSERVICES();
                                azerty.SupprimerTache(er.getId_tache());
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

                                alert2.setTitle("Information");

                                alert2.setHeaderText(null);

                                alert2.setContentText("Tache supprimée avec succés");

                                alert2.show();

                            } else if (result.get() == buttonTypeNo) {
                                listetermine.getSelectionModel().clearSelection();
                            }
                        }
                        );

                    }
                }
            }
        });

        modifier_tache.setOnAction(i -> {

            //switch to taches
            Stage stage = (Stage) modifier_tache.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/AjoutTache.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Manipulation des taches");
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
        );
        ///control saisie 
        priofield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                priofield.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
