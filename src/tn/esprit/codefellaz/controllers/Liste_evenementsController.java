/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import com.sun.javafx.text.TextLine;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JToolTip;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import tn.esprit.codefellaz.entities.Evenements;
import tn.esprit.codefellaz.services.Evenements_service;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author MAG-PC
 */
public class Liste_evenementsController implements Initializable {

    private AnchorPane root;

    TilePane parentContainer = new TilePane();

    @FXML
    private Button afficher;

    /**
     * Initializes the controller class.
     */
    Connection cnx;
    String city = ""; // Replace with the name of the city you want to display
    String json = null;

    public Liste_evenementsController() {
        cnx = MyConnection.getInstance().getCnx();
    }
    Evenements_service afficher1 = new Evenements_service();
    List<Evenements> list_event = new ArrayList<>();
// a fuction to get the path of the image and convert it to an ImageView

    public ImageView convert_path_to_image(String path) {
        //Creating the image view
        ImageView imageView = new ImageView();
        //creating the image object
        
        Image image = new Image("http://localhost"+path);
        imageView.setFitWidth(90);
        imageView.setFitHeight(90);
        //Setting image to the image view
        imageView.setImage(image);
        return imageView;

    }
// a function that gets the date as string and convert it to a DatePicker

    public DatePicker convert_string_to_date(String date) {
        DatePicker date_picker = new DatePicker();
        date_picker.setValue(date_picker.getConverter().fromString(date));
        return date_picker;
    }
// this fuctions returns the name of a user (from database) with his id 

    public String get_nom_user(int id) {
        String name = "";
        try {
            Statement ste = cnx.createStatement();
            String req = "SELECT `nom_utilisateur`FROM utilisateurs WHERE `id_utilisateur` = '" + id + "'";
            ResultSet res = ste.executeQuery(req);
            if (res.next()) {
                name = res.getString("nom_utilisateur");
                return name;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return name;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ScrollPane scroll = new ScrollPane();
        Button ajouter2 = new Button();
        ajouter2.setText("ajouter");
        TextField search = new TextField();

       afficher.setOnAction(event
                -> {
            afficher.setVisible(false);
            root = (AnchorPane) afficher.getScene().getRoot();

            //constrains
            AnchorPane.setTopAnchor(Liste_evenementsController.this.parentContainer, 14.0);
            AnchorPane.setLeftAnchor(Liste_evenementsController.this.parentContainer, 14.0);
            AnchorPane.setRightAnchor(Liste_evenementsController.this.parentContainer, 14.0);
            AnchorPane.setBottomAnchor(Liste_evenementsController.this.parentContainer, 14.0);

            // Créer un conteneur parent pour les FlowPane
            this.parentContainer = new TilePane();
            scroll.setContent(this.parentContainer);
            root.getChildren().add(scroll);
            this.parentContainer.setStyle("-fx-background-color:linear-gradient(from 25% 25% to 100% 100%,#091F4E,#18b9dF);");
            //ajouter2.setStyle("-fx-background-color: #e24933");
            ajouter2.setStyle("-fx-background-radius: 10px");
            root.setOnKeyPressed(pressed->{
                if(pressed.getCode()==KeyCode.BACK_SPACE){
                      try {
                Parent annuler_modif_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/SignInUp.fxml"));
                Scene annuler_modif_scene = new Scene(annuler_modif_event_parent);
                Stage annuler_modif_stage = (Stage) ((Node) pressed.getSource()).getScene().getWindow();
                annuler_modif_stage.hide();
                annuler_modif_stage.setScene(annuler_modif_scene);
                annuler_modif_stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
                }
            });

            parentContainer.getChildren().addAll(ajouter2, search);

            // Parcourir la liste des evenements et créer un FlowPane pour chaque event
            list_event = afficher1.afficher_Evenement();
            for (Evenements evenement : list_event) {
                ImageView imageView = new ImageView();
                DatePicker date_picker = new DatePicker();
                Label type = new Label();
                // Créer un FlowPane pour le service
                TilePane tilePane = new TilePane(Orientation.VERTICAL, 20, 20);
                this.parentContainer.setHgap(10);
                this.parentContainer.setVgap(10);

                //tilePane.setStyle("-fx-background-color:linear-gradient(from 25% 25% to 100% 100%,#091F4E,#18b9dF) ;");
                tilePane.setStyle("-fx-background-color: #dddddd;");
                //tilePane.setStyle("-fx-background-color: #E24933;");
                tilePane.setHgap(100);

                Label nom = new Label(evenement.getName_event());
                imageView = convert_path_to_image(evenement.getLogo_event());
                Label description = new Label(evenement.getDesc_event());
                Label emplacement = new Label(evenement.getEmplacement_event());
                Label date = new Label(evenement.getDate_event());
                date_picker = convert_string_to_date(evenement.getDate_event());
                Label nombre_perso = new Label(Integer.toString(evenement.getNbr_perso_event()));
                if (evenement.getType_event() == 1) {
                    type.setText("sans invitation");
                }
                if (evenement.getType_event() == 0) {
                    type.setText("avec invitation");
                }
                Label responsable = new Label(get_nom_user(evenement.getId_representant()));
                Button bouton_ajouter = new Button("Ajouter");
                Button bouton_modifier = new Button("Modifier");
                Button bouton_supprimer = new Button("Supprimer");

                // Ajouter les éléments au FlowPane
                tilePane.getChildren().addAll(imageView, nom, description, emplacement, date_picker, nombre_perso, type, responsable, bouton_modifier, bouton_supprimer);
                tilePane.setPrefRows(4);

                // Ajouter le FlowPane créé au conteneur parent
                this.parentContainer.getChildren().add(tilePane);
                //when mouse enters a bouton , it changes color and when it exits it changes to the original color 
                bouton_ajouter.setOnMouseEntered(e -> bouton_ajouter.setStyle("-fx-border-color:#0000ff"));
                bouton_ajouter.setOnMouseExited(e -> bouton_ajouter.setStyle("-fx-border-color:#dddddd"));
                bouton_modifier.setOnMouseEntered(e -> bouton_modifier.setStyle("-fx-border-color:#0000ff"));
                bouton_modifier.setOnMouseExited(e -> bouton_modifier.setStyle("-fx-border-color:#dddddd"));
                bouton_supprimer.setOnMouseEntered(e -> bouton_supprimer.setStyle("-fx-border-color:#0000ff"));
                bouton_supprimer.setOnMouseExited(e -> bouton_supprimer.setStyle("-fx-border-color:#dddddd"));
                //event when mouse enters a tilepane it chages the tilepane color 
                tilePane.setOnMouseEntered(enter -> {
                    tilePane.setStyle("-fx-background-color: #B0B0B0;");
                    //tilePane.setStyle("-fx-background-color: #B43A28;");

                    //when bouton supprimer is clicked , we delete an event via a method  
                    bouton_supprimer.setOnAction(supprimer -> {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Are you sure you want to delete the event?");
                        alert.setContentText("Click yes to continue or no to cancel.");

                        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.YES);
                        ButtonType buttonTypeNo = new ButtonType("No", ButtonData.NO);
                        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == buttonTypeYes) {
                            // user clicked "Yes"
                            // handle "Yes" button event
                            afficher1.supprimer_Evenement(evenement.getId_event());
                            try {
                                Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                                Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                                Stage ajout_stage = (Stage) ((Node) supprimer.getSource()).getScene().getWindow();
                                ajout_stage.hide();
                                ajout_stage.setScene(ajouter_event_scene);
                                ajout_stage.show();
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }

                        } else {
                            try {
                                Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                                Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                                Stage ajout_stage = (Stage) ((Node) supprimer.getSource()).getScene().getWindow();
                                ajout_stage.hide();
                                ajout_stage.setScene(ajouter_event_scene);
                                ajout_stage.show();
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }

                    });
                    //when bouton modifier is clicked , we set data in the corresponding fields and load the modifier_evenements.fxml
                    bouton_modifier.setOnAction(modifier -> {

                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/modifier_evenements.fxml"));
                            Parent modif_event = loader.load();
                            Evenements e = new Evenements(evenement.getName_event(), evenement.getLogo_event(), evenement.getDesc_event(), evenement.getEmplacement_event(), evenement.getDate_event(), evenement.getNbr_perso_event(), evenement.getType_event(), evenement.getId_representant());
                            Modifier_evenementsController modif = loader.getController();
                            modif.set_data_in_the_view(evenement);
                            Scene modif_evenScene = new Scene(modif_event);
                            Stage modif_event_stage = (Stage) ((Node) modifier.getSource()).getScene().getWindow();
                            modif_event_stage.hide();
                            modif_event_stage.setScene(modif_evenScene);
                            modif_event_stage.show();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    });
                });
                //when tilepane is exited whe change its color
                tilePane.setOnMouseExited(exit -> {
                    //tilePane.setStyle("-fx-background-color: #E24933;");
                    tilePane.setStyle("-fx-background-color: #dddddd;");
                });
                tilePane.setOnMouseClicked(event1 -> {
                    city = evenement.getEmplacement_event();

                    String url1 = "https://nominatim.openstreetmap.org/search?q=" + city + "&format=json&addressdetails=1&limit=1";
                    try {
                        // Send a GET request to the Nominatim API to retrieve the latitude and longitude of the city
                        URL apiUrl = new URL(url1);
                        HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
                        conn.setRequestMethod("GET");

                        Scanner scanner = new Scanner(conn.getInputStream());
                        scanner.useDelimiter("\\A");
                        json = scanner.next();
                        scanner.close();
                        System.out.println(json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (json != null) {
                        // Parse the JSON response to retrieve the latitude and longitude of the city
                        JSONArray results = new JSONArray(json);
                        JSONObject result = results.getJSONObject(0);
                        //JSONObject latLon = result.getJSONObject("latlon");
                        double latitude = result.getDouble("lat");
                        double longitude = result.getDouble("lon");
                        System.out.println(latitude);
                        System.out.println(longitude);

                        final JXMapKit jXMapKit = new JXMapKit();
                        TileFactoryInfo info = new OSMTileFactoryInfo();
                        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
                        jXMapKit.setTileFactory(tileFactory);

                        //location of Java
                        final GeoPosition gp = new GeoPosition(latitude, longitude);

                        final JToolTip tooltip = new JToolTip();
                        tooltip.setTipText("Event");
                        tooltip.setComponent(jXMapKit.getMainMap());
                        jXMapKit.getMainMap().add(tooltip);

                        jXMapKit.setZoom(11);
                        jXMapKit.setAddressLocation(gp);

                        jXMapKit.getMainMap().addMouseMotionListener(new MouseMotionListener() {
                            @Override
                            public void mouseDragged(java.awt.event.MouseEvent e) {
                                // ignore
                            }

                            @Override
                            public void mouseMoved(java.awt.event.MouseEvent e) {
                                JXMapViewer map = jXMapKit.getMainMap();

                                // convert to world bitmap
                                Point2D worldPos = map.getTileFactory().geoToPixel(gp, map.getZoom());

                                // convert to screen
                                Rectangle rect = map.getViewportBounds();
                                int sx = (int) worldPos.getX() - rect.x;
                                int sy = (int) worldPos.getY() - rect.y;
                                Point screenPos = new Point(sx, sy);

                                // check if near the mouse
                                if (screenPos.distance(e.getPoint()) < 20) {
                                    screenPos.x -= tooltip.getWidth() / 2;

                                    tooltip.setLocation(screenPos);
                                    tooltip.setVisible(true);
                                } else {
                                    tooltip.setVisible(false);
                                }
                            }
                        });

                        // Display the viewer in a JFrame
                        JFrame frame = new JFrame("emplacement de l'evenement");
                        frame.getContentPane().add(jXMapKit);
                        frame.setSize(800, 600);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setVisible(true);

                    }

                });
                //when bouton ajouter is clicked we load ajouter_evenements.fxml
                ajouter2.setOnAction(ajouter -> {
                    try {
                        Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouter_evenements.fxml"));
                        Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                        Stage ajout_stage = (Stage) ((Node) ajouter.getSource()).getScene().getWindow();
                        ajout_stage.hide();
                        ajout_stage.setScene(ajouter_event_scene);
                        ajout_stage.show();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                });

            }
            search.textProperty().addListener((observable, oldValue, newValue) -> {
                //parentContainer.getChildren().clear();
                if (!oldValue.equals(newValue)) {
                    list_event.clear();
                    list_event = afficher1.search_event(newValue);
                    System.out.println(list_event);
                    if (!list_event.isEmpty()) {
                        parentContainer.getChildren().clear();
                        //root.getChildren().add(scroll);

                        parentContainer.getChildren().addAll(ajouter2, search);
                        for (Evenements evenement : list_event) {
                            ImageView imageView = new ImageView();
                            DatePicker date_picker = new DatePicker();
                            Label type = new Label();
                            // Créer un FlowPane pour le service
                            TilePane tilePane = new TilePane(Orientation.VERTICAL, 20, 20);
                            this.parentContainer.setHgap(10);
                            this.parentContainer.setVgap(10);

                            //tilePane.setStyle("-fx-background-color:linear-gradient(from 25% 25% to 100% 100%,#091F4E,#18b9dF) ;");
                            tilePane.setStyle("-fx-background-color: #dddddd;");
                            //tilePane.setStyle("-fx-background-color: #E24933;");
                            tilePane.setHgap(100);

                            Label nom = new Label(evenement.getName_event());
                            imageView = convert_path_to_image(evenement.getLogo_event());
                            Label description = new Label(evenement.getDesc_event());
                            Label emplacement = new Label(evenement.getEmplacement_event());
                            Label date = new Label(evenement.getDate_event());
                            date_picker = convert_string_to_date(evenement.getDate_event());
                            Label nombre_perso = new Label(Integer.toString(evenement.getNbr_perso_event()));
                            if (evenement.getType_event() == 1) {
                                type.setText("sans invitation");
                            }
                            if (evenement.getType_event() == 0) {
                                type.setText("avec invitation");
                            }
                            Label responsable = new Label(get_nom_user(evenement.getId_representant()));
                            Button bouton_ajouter = new Button("Ajouter");
                            Button bouton_modifier = new Button("Modifier");
                            Button bouton_supprimer = new Button("Supprimer");

                            // Ajouter les éléments au FlowPane
                            tilePane.getChildren().addAll(imageView, nom, description, emplacement, date_picker, nombre_perso, type, responsable, bouton_modifier, bouton_supprimer);
                            tilePane.setPrefRows(4);

                            // Ajouter le FlowPane créé au conteneur parent
                            this.parentContainer.getChildren().add(tilePane);
                            //when mouse enters a bouton , it changes color and when it exits it changes to the original color 
                            bouton_ajouter.setOnMouseEntered(e -> bouton_ajouter.setStyle("-fx-border-color:#0000ff"));
                            bouton_ajouter.setOnMouseExited(e -> bouton_ajouter.setStyle("-fx-border-color:#dddddd"));
                            bouton_modifier.setOnMouseEntered(e -> bouton_modifier.setStyle("-fx-border-color:#0000ff"));
                            bouton_modifier.setOnMouseExited(e -> bouton_modifier.setStyle("-fx-border-color:#dddddd"));
                            bouton_supprimer.setOnMouseEntered(e -> bouton_supprimer.setStyle("-fx-border-color:#0000ff"));
                            bouton_supprimer.setOnMouseExited(e -> bouton_supprimer.setStyle("-fx-border-color:#dddddd"));
                            //event when mouse enters a tilepane it chages the tilepane color 
                            tilePane.setOnMouseEntered(enter -> {
                                tilePane.setStyle("-fx-background-color: #B0B0B0;");
                                //tilePane.setStyle("-fx-background-color: #B43A28;");

                                //when bouton supprimer is clicked , we delete an event via a method  
                                bouton_supprimer.setOnAction(supprimer -> {
                                    Alert alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation Dialog");
                                    alert.setHeaderText("Are you sure you want to delete the event?");
                                    alert.setContentText("Click yes to continue or no to cancel.");

                                    ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.YES);
                                    ButtonType buttonTypeNo = new ButtonType("No", ButtonData.NO);
                                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.isPresent() && result.get() == buttonTypeYes) {
                                        // user clicked "Yes"
                                        // handle "Yes" button event
                                        afficher1.supprimer_Evenement(evenement.getId_event());
                                        try {
                                            Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                                            Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                                            Stage ajout_stage = (Stage) ((Node) supprimer.getSource()).getScene().getWindow();
                                            ajout_stage.hide();
                                            ajout_stage.setScene(ajouter_event_scene);
                                            ajout_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }

                                    } else {
                                        try {
                                            Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                                            Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                                            Stage ajout_stage = (Stage) ((Node) supprimer.getSource()).getScene().getWindow();
                                            ajout_stage.hide();
                                            ajout_stage.setScene(ajouter_event_scene);
                                            ajout_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    }

                                });
                                //when bouton modifier is clicked , we set data in the corresponding fields and load the modifier_evenements.fxml
                                bouton_modifier.setOnAction(modifier -> {

                                    try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/modifier_evenements.fxml"));
                                        Parent modif_event = loader.load();
                                        Evenements e = new Evenements(evenement.getName_event(), evenement.getLogo_event(), evenement.getDesc_event(), evenement.getEmplacement_event(), evenement.getDate_event(), evenement.getNbr_perso_event(), evenement.getType_event(), evenement.getId_representant());
                                        Modifier_evenementsController modif = loader.getController();
                                        modif.set_data_in_the_view(evenement);
                                        Scene modif_evenScene = new Scene(modif_event);
                                        Stage modif_event_stage = (Stage) ((Node) modifier.getSource()).getScene().getWindow();
                                        modif_event_stage.hide();
                                        modif_event_stage.setScene(modif_evenScene);
                                        modif_event_stage.show();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }

                                });
                            });
                            //when tilepane is exited whe change its color
                            tilePane.setOnMouseExited(exit -> {
                                //tilePane.setStyle("-fx-background-color: #E24933;");
                                tilePane.setStyle("-fx-background-color: #dddddd;");
                            });
                            tilePane.setOnMouseClicked(event1 -> {
                    city = evenement.getEmplacement_event();

                    String url1 = "https://nominatim.openstreetmap.org/search?q=" + city + "&format=json&addressdetails=1&limit=1";
                    try {
                        // Send a GET request to the Nominatim API to retrieve the latitude and longitude of the city
                        URL apiUrl = new URL(url1);
                        HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
                        conn.setRequestMethod("GET");

                        Scanner scanner = new Scanner(conn.getInputStream());
                        scanner.useDelimiter("\\A");
                        json = scanner.next();
                        scanner.close();
                        System.out.println(json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (json != null) {
                        // Parse the JSON response to retrieve the latitude and longitude of the city
                        JSONArray results = new JSONArray(json);
                        JSONObject result = results.getJSONObject(0);
                        //JSONObject latLon = result.getJSONObject("latlon");
                        double latitude = result.getDouble("lat");
                        double longitude = result.getDouble("lon");
                        System.out.println(latitude);
                        System.out.println(longitude);

                        final JXMapKit jXMapKit = new JXMapKit();
                        TileFactoryInfo info = new OSMTileFactoryInfo();
                        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
                        jXMapKit.setTileFactory(tileFactory);

                        //location of Java
                        final GeoPosition gp = new GeoPosition(latitude, longitude);

                        final JToolTip tooltip = new JToolTip();
                        tooltip.setTipText("Event");
                        tooltip.setComponent(jXMapKit.getMainMap());
                        jXMapKit.getMainMap().add(tooltip);

                        jXMapKit.setZoom(11);
                        jXMapKit.setAddressLocation(gp);

                        jXMapKit.getMainMap().addMouseMotionListener(new MouseMotionListener() {
                            @Override
                            public void mouseDragged(java.awt.event.MouseEvent e) {
                                // ignore
                            }

                            @Override
                            public void mouseMoved(java.awt.event.MouseEvent e) {
                                JXMapViewer map = jXMapKit.getMainMap();

                                // convert to world bitmap
                                Point2D worldPos = map.getTileFactory().geoToPixel(gp, map.getZoom());

                                // convert to screen
                                Rectangle rect = map.getViewportBounds();
                                int sx = (int) worldPos.getX() - rect.x;
                                int sy = (int) worldPos.getY() - rect.y;
                                Point screenPos = new Point(sx, sy);

                                // check if near the mouse
                                if (screenPos.distance(e.getPoint()) < 20) {
                                    screenPos.x -= tooltip.getWidth() / 2;

                                    tooltip.setLocation(screenPos);
                                    tooltip.setVisible(true);
                                } else {
                                    tooltip.setVisible(false);
                                }
                            }
                        });

                        // Display the viewer in a JFrame
                        JFrame frame = new JFrame("emplacement de l'evenement");
                        frame.getContentPane().add(jXMapKit);
                        frame.setSize(800, 600);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setVisible(true);

                    }

                });
                            //when bouton ajouter is clicked we load ajouter_evenements.fxml
                            ajouter2.setOnAction(ajouter -> {
                                try {
                                    Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouter_evenements.fxml"));
                                    Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                                    Stage ajout_stage = (Stage) ((Node) ajouter.getSource()).getScene().getWindow();
                                    ajout_stage.hide();
                                    ajout_stage.setScene(ajouter_event_scene);
                                    ajout_stage.show();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            });

                        }
                    } else {
                        list_event = afficher1.afficher_Evenement();
                        parentContainer.getChildren().clear();
                        //root.getChildren().add(scroll);

                        parentContainer.getChildren().addAll(ajouter2, search);
                        for (Evenements evenement : list_event) {
                            ImageView imageView = new ImageView();
                            DatePicker date_picker = new DatePicker();
                            Label type = new Label();
                            // Créer un FlowPane pour le service
                            TilePane tilePane = new TilePane(Orientation.VERTICAL, 20, 20);
                            this.parentContainer.setHgap(10);
                            this.parentContainer.setVgap(10);

                            //tilePane.setStyle("-fx-background-color:linear-gradient(from 25% 25% to 100% 100%,#091F4E,#18b9dF) ;");
                            tilePane.setStyle("-fx-background-color: #dddddd;");
                            //tilePane.setStyle("-fx-background-color: #E24933;");
                            tilePane.setHgap(100);

                            Label nom = new Label(evenement.getName_event());
                            imageView = convert_path_to_image(evenement.getLogo_event());
                            Label description = new Label(evenement.getDesc_event());
                            Label emplacement = new Label(evenement.getEmplacement_event());
                            Label date = new Label(evenement.getDate_event());
                            date_picker = convert_string_to_date(evenement.getDate_event());
                            Label nombre_perso = new Label(Integer.toString(evenement.getNbr_perso_event()));
                            if (evenement.getType_event() == 1) {
                                type.setText("sans invitation");
                            }
                            if (evenement.getType_event() == 0) {
                                type.setText("avec invitation");
                            }
                            Label responsable = new Label(get_nom_user(evenement.getId_representant()));
                            Button bouton_ajouter = new Button("Ajouter");
                            Button bouton_modifier = new Button("Modifier");
                            Button bouton_supprimer = new Button("Supprimer");

                            // Ajouter les éléments au FlowPane
                            tilePane.getChildren().addAll(imageView, nom, description, emplacement, date_picker, nombre_perso, type, responsable, bouton_modifier, bouton_supprimer);
                            tilePane.setPrefRows(4);

                            // Ajouter le FlowPane créé au conteneur parent
                            this.parentContainer.getChildren().add(tilePane);
                            //when mouse enters a bouton , it changes color and when it exits it changes to the original color 
                            bouton_ajouter.setOnMouseEntered(e -> bouton_ajouter.setStyle("-fx-border-color:#0000ff"));
                            bouton_ajouter.setOnMouseExited(e -> bouton_ajouter.setStyle("-fx-border-color:#dddddd"));
                            bouton_modifier.setOnMouseEntered(e -> bouton_modifier.setStyle("-fx-border-color:#0000ff"));
                            bouton_modifier.setOnMouseExited(e -> bouton_modifier.setStyle("-fx-border-color:#dddddd"));
                            bouton_supprimer.setOnMouseEntered(e -> bouton_supprimer.setStyle("-fx-border-color:#0000ff"));
                            bouton_supprimer.setOnMouseExited(e -> bouton_supprimer.setStyle("-fx-border-color:#dddddd"));
                            //event when mouse enters a tilepane it chages the tilepane color 
                            tilePane.setOnMouseEntered(enter -> {
                                tilePane.setStyle("-fx-background-color: #B0B0B0;");
                                //tilePane.setStyle("-fx-background-color: #B43A28;");

                                //when bouton supprimer is clicked , we delete an event via a method  
                                bouton_supprimer.setOnAction(supprimer -> {
                                    Alert alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation Dialog");
                                    alert.setHeaderText("Are you sure you want to delete the event?");
                                    alert.setContentText("Click yes to continue or no to cancel.");

                                    ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.YES);
                                    ButtonType buttonTypeNo = new ButtonType("No", ButtonData.NO);
                                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.isPresent() && result.get() == buttonTypeYes) {
                                        // user clicked "Yes"
                                        // handle "Yes" button event
                                        afficher1.supprimer_Evenement(evenement.getId_event());
                                        try {
                                            Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                                            Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                                            Stage ajout_stage = (Stage) ((Node) supprimer.getSource()).getScene().getWindow();
                                            ajout_stage.hide();
                                            ajout_stage.setScene(ajouter_event_scene);
                                            ajout_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }

                                    } else {
                                        try {
                                            Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                                            Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                                            Stage ajout_stage = (Stage) ((Node) supprimer.getSource()).getScene().getWindow();
                                            ajout_stage.hide();
                                            ajout_stage.setScene(ajouter_event_scene);
                                            ajout_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    }

                                });
                                //when bouton modifier is clicked , we set data in the corresponding fields and load the modifier_evenements.fxml
                                bouton_modifier.setOnAction(modifier -> {

                                    try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/modifier_evenements.fxml"));
                                        Parent modif_event = loader.load();
                                        Evenements e = new Evenements(evenement.getName_event(), evenement.getLogo_event(), evenement.getDesc_event(), evenement.getEmplacement_event(), evenement.getDate_event(), evenement.getNbr_perso_event(), evenement.getType_event(), evenement.getId_representant());
                                        Modifier_evenementsController modif = loader.getController();
                                        modif.set_data_in_the_view(evenement);
                                        Scene modif_evenScene = new Scene(modif_event);
                                        Stage modif_event_stage = (Stage) ((Node) modifier.getSource()).getScene().getWindow();
                                        modif_event_stage.hide();
                                        modif_event_stage.setScene(modif_evenScene);
                                        modif_event_stage.show();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }

                                });
                            });
                            //when tilepane is exited whe change its color
                            tilePane.setOnMouseExited(exit -> {
                                //tilePane.setStyle("-fx-background-color: #E24933;");
                                tilePane.setStyle("-fx-background-color: #dddddd;");
                            });
                            
                            tilePane.setOnMouseClicked(event1 -> {
                    city = evenement.getEmplacement_event();

                    String url1 = "https://nominatim.openstreetmap.org/search?q=" + city + "&format=json&addressdetails=1&limit=1";
                    try {
                        // Send a GET request to the Nominatim API to retrieve the latitude and longitude of the city
                        URL apiUrl = new URL(url1);
                        HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
                        conn.setRequestMethod("GET");

                        Scanner scanner = new Scanner(conn.getInputStream());
                        scanner.useDelimiter("\\A");
                        json = scanner.next();
                        scanner.close();
                        System.out.println(json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (json != null) {
                        // Parse the JSON response to retrieve the latitude and longitude of the city
                        JSONArray results = new JSONArray(json);
                        JSONObject result = results.getJSONObject(0);
                        //JSONObject latLon = result.getJSONObject("latlon");
                        double latitude = result.getDouble("lat");
                        double longitude = result.getDouble("lon");
                        System.out.println(latitude);
                        System.out.println(longitude);

                        final JXMapKit jXMapKit = new JXMapKit();
                        TileFactoryInfo info = new OSMTileFactoryInfo();
                        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
                        jXMapKit.setTileFactory(tileFactory);

                        //location of Java
                        final GeoPosition gp = new GeoPosition(latitude, longitude);

                        final JToolTip tooltip = new JToolTip();
                        tooltip.setTipText("Event");
                        tooltip.setComponent(jXMapKit.getMainMap());
                        jXMapKit.getMainMap().add(tooltip);

                        jXMapKit.setZoom(11);
                        jXMapKit.setAddressLocation(gp);

                        jXMapKit.getMainMap().addMouseMotionListener(new MouseMotionListener() {
                            @Override
                            public void mouseDragged(java.awt.event.MouseEvent e) {
                                // ignore
                            }

                            @Override
                            public void mouseMoved(java.awt.event.MouseEvent e) {
                                JXMapViewer map = jXMapKit.getMainMap();

                                // convert to world bitmap
                                Point2D worldPos = map.getTileFactory().geoToPixel(gp, map.getZoom());

                                // convert to screen
                                Rectangle rect = map.getViewportBounds();
                                int sx = (int) worldPos.getX() - rect.x;
                                int sy = (int) worldPos.getY() - rect.y;
                                Point screenPos = new Point(sx, sy);

                                // check if near the mouse
                                if (screenPos.distance(e.getPoint()) < 20) {
                                    screenPos.x -= tooltip.getWidth() / 2;

                                    tooltip.setLocation(screenPos);
                                    tooltip.setVisible(true);
                                } else {
                                    tooltip.setVisible(false);
                                }
                            }
                        });

                        // Display the viewer in a JFrame
                        JFrame frame = new JFrame("emplacement de l'evenement");
                        frame.getContentPane().add(jXMapKit);
                        frame.setSize(800, 600);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setVisible(true);

                    }

                });
                            //when bouton ajouter is clicked we load ajouter_evenements.fxml
                            ajouter2.setOnAction(ajouter -> {
                                try {
                                    Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouter_evenements.fxml"));
                                    Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                                    Stage ajout_stage = (Stage) ((Node) ajouter.getSource()).getScene().getWindow();
                                    ajout_stage.hide();
                                    ajout_stage.setScene(ajouter_event_scene);
                                    ajout_stage.show();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            });

                        }
                    }

                }

            });
            //when bouton ajouter is clicked we load ajouter_evenements.fxml
                            ajouter2.setOnAction(ajouter -> {
                                try {
                                    Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouter_evenements.fxml"));
                                    Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                                    Stage ajout_stage = (Stage) ((Node) ajouter.getSource()).getScene().getWindow();
                                    ajout_stage.hide();
                                    ajout_stage.setScene(ajouter_event_scene);
                                    ajout_stage.show();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            });

            this.parentContainer.prefHeightProperty().bind(root.heightProperty());
            this.parentContainer.prefWidthProperty().bind(root.widthProperty());

            // Ajouter le conteneur parent au root de la scène
            root.getChildren().add(this.parentContainer);

        });

    }

}
