/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
import tn.esprit.codefellaz.entities.Invitations_participation;
import tn.esprit.codefellaz.services.Evenements_service;
import tn.esprit.codefellaz.services.InvitationParti_service;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author MAG-PC
 */
public class Liste_evenements_freelancerController implements Initializable {

    private AnchorPane root;

    TilePane parentContainer = new TilePane();
    List<Evenements> list_event = new ArrayList<>();
    List<Invitations_participation> list_invi = new ArrayList<>();
    String city = ""; // Replace with the name of the city you want to display
    String json = null;

    @FXML
    private Button afficher;
    /**
     * Initializes the controller class.
     */
    Connection cnx;

    public Liste_evenements_freelancerController() {
        cnx = MyConnection.getInstance().getCnx();
    }

    public ImageView convert_path_to_image(String path) throws MalformedURLException {
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

    public DatePicker convert_string_to_date(String date) {
        DatePicker date_picker = new DatePicker();
        date_picker.setValue(date_picker.getConverter().fromString(date));
        return date_picker;
    }

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
        ChoiceBox<String> type_event = new ChoiceBox<>();

        afficher.setOnAction(event
                -> {
                    

            afficher.setVisible(false);
            root = (AnchorPane) afficher.getScene().getRoot();
            Evenements_service afficher = new Evenements_service();

            InvitationParti_service invi_crud3 = new InvitationParti_service();
            AnchorPane.setTopAnchor(Liste_evenements_freelancerController.this.parentContainer, 14.0);
            AnchorPane.setLeftAnchor(Liste_evenements_freelancerController.this.parentContainer, 14.0);
            AnchorPane.setRightAnchor(Liste_evenements_freelancerController.this.parentContainer, 14.0);
            AnchorPane.setBottomAnchor(Liste_evenements_freelancerController.this.parentContainer, 14.0);
            // Créer un conteneur parent pour les FlowPane
            this.parentContainer = new TilePane();
            scroll.setContent(this.parentContainer);
            root.getChildren().add(scroll);
            this.parentContainer.setStyle("-fx-background-color:linear-gradient(from 25% 25% to 100% 100%,#091F4E,#18b9dF);");
            type_event.getItems().add("avec invitation");
            type_event.getItems().add("sans invitation");
            type_event.getItems().add("all");
            parentContainer.getChildren().add(type_event);
            type_event.getSelectionModel().select(2);
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
                }            });

            // Parcourir la liste des evenements et créer un FlowPane pour chaque event
            list_event = afficher.afficher_Evenement();
            for (Evenements evenement : list_event) {
                try {
                    ImageView imageView = new ImageView();
                    DatePicker date_picker = new DatePicker();
                    Label type = new Label();
                    // Créer un FlowPane pour le service
                    TilePane tilePane = new TilePane(Orientation.VERTICAL, 20, 20);
                    this.parentContainer.setHgap(10);
                    this.parentContainer.setVgap(10);
                    
                    tilePane.setStyle("-fx-background-color: #dddddd;");
                    tilePane.setHgap(100);
                    
                    Label nom = new Label(evenement.getName_event());
                    imageView = convert_path_to_image(evenement.getLogo_event());
                    Label description = new Label(evenement.getDesc_event());
                    Label emplacement = new Label(evenement.getEmplacement_event());
                    Label date = new Label(evenement.getDate_event());
                    date_picker = convert_string_to_date(evenement.getDate_event());
                    Label nombre_perso = new Label(Integer.toString(evenement.getNbr_perso_event()));
                    Label responsable = new Label(get_nom_user(evenement.getId_representant()));
                    Button bouton_Participer = new Button("Participer");
                    Button bouton_demander_Participation = new Button("demander invitation");
                    Button bouton_Participé = new Button("Participé");
                    Button bouton_demander_envoyée = new Button("demande envoyée");
                    Button bouton_demander_annuler = new Button("annuler demande");
                    Button bouton_participé_annuler = new Button("supprimer");
                    tilePane.getChildren().addAll(imageView, nom, description, emplacement, date_picker, nombre_perso, type, responsable, bouton_Participer, bouton_Participé, bouton_demander_Participation, bouton_demander_envoyée, bouton_demander_annuler, bouton_participé_annuler);
                    if (list_invi.isEmpty()) {
                        if (evenement.getType_event() == 1) {
                            
                            type.setText("sans invitation");
                            bouton_Participer.setVisible(true);
                            bouton_demander_Participation.setVisible(false);
                            bouton_demander_annuler.setVisible(false);
                            bouton_demander_envoyée.setVisible(false);
                            bouton_participé_annuler.setVisible(false);
                            bouton_Participé.setVisible(false);
                            
                        }
                        if (evenement.getType_event() == 0) {
                            
                            type.setText("avec invitation");
                            bouton_Participer.setVisible(false);
                            bouton_demander_Participation.setVisible(true);
                            bouton_demander_annuler.setVisible(false);
                            bouton_demander_envoyée.setVisible(false);
                            bouton_participé_annuler.setVisible(false);
                            bouton_Participé.setVisible(false);
                            
                        }
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
                        tilePane.setOnMouseEntered(enter1 -> {
                            tilePane.setStyle("-fx-background-color: #B0B0B0;");
                            bouton_Participer.setOnAction(participer1 -> {
                                InvitationParti_service invi_crud = new InvitationParti_service();
                                Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "participation");
                                invi_crud.ajouter_Invitation(invi1);
                                try {
                                    Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                    Scene participer_event_scene = new Scene(participer_event_parent);
                                    Stage participer_stage = (Stage) ((Node) participer1.getSource()).getScene().getWindow();
                                    participer_stage.hide();
                                    participer_stage.setScene(participer_event_scene);
                                    participer_stage.show();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            });
                            
                            bouton_demander_Participation.setOnAction(demande1 -> {
                                InvitationParti_service invi_crud1 = new InvitationParti_service();
                                Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "demande");
                                invi_crud1.ajouter_Invitation(invi1);
                                try {
                                    Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                    Scene participer_event_scene = new Scene(participer_event_parent);
                                    Stage participer_stage = (Stage) ((Node) demande1.getSource()).getScene().getWindow();
                                    participer_stage.hide();
                                    participer_stage.setScene(participer_event_scene);
                                    participer_stage.show();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            });
                        });
                        tilePane.setOnMouseExited(exit -> {
                            tilePane.setStyle("-fx-background-color: #dddddd;");
                        });
                    }
                    list_invi = invi_crud3.afficher_Invitation();
                    for (Invitations_participation invi : list_invi) {
                        
                        if (evenement.getType_event() == 1) {
                            if ("participation".equals(invi.getType())) {
                                type.setText("sans invitation");
                                bouton_Participer.setVisible(false);
                                bouton_demander_Participation.setVisible(false);
                                bouton_demander_annuler.setVisible(false);
                                bouton_demander_envoyée.setVisible(false);
                                bouton_participé_annuler.setVisible(true);
                                bouton_Participé.setVisible(true);
                                bouton_Participé.setDisable(true);
                            } else {
                                type.setText("sans invitation");
                                bouton_Participer.setVisible(true);
                                bouton_demander_Participation.setVisible(false);
                                bouton_demander_annuler.setVisible(false);
                                bouton_demander_envoyée.setVisible(false);
                                bouton_participé_annuler.setVisible(false);
                                bouton_Participé.setVisible(false);
                            }
                        }
                        if (evenement.getType_event() == 0) {
                            if ("demande".equals(invi.getType())) {
                                type.setText("avec invitation");
                                bouton_Participer.setVisible(false);
                                bouton_demander_Participation.setVisible(false);
                                bouton_demander_annuler.setVisible(true);
                                bouton_demander_envoyée.setVisible(true);
                                bouton_participé_annuler.setVisible(false);
                                bouton_Participé.setVisible(false);
                                bouton_demander_envoyée.setDisable(true);
                            } else {
                                type.setText("avec invitation");
                                bouton_Participer.setVisible(false);
                                bouton_demander_Participation.setVisible(true);
                                bouton_demander_annuler.setVisible(false);
                                bouton_demander_envoyée.setVisible(false);
                                bouton_participé_annuler.setVisible(false);
                                bouton_Participé.setVisible(false);
                            }
                        }
                        
                        tilePane.setOnMouseEntered(enter -> {
                            tilePane.setStyle("-fx-background-color: #B0B0B0;");
                            bouton_Participer.setOnAction(participer -> {
                                InvitationParti_service invi_crud = new InvitationParti_service();
                                Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "participation");
                                invi_crud.ajouter_Invitation(invi1);
                                try {
                                    Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                    Scene participer_event_scene = new Scene(participer_event_parent);
                                    Stage participer_stage = (Stage) ((Node) participer.getSource()).getScene().getWindow();
                                    participer_stage.hide();
                                    participer_stage.setScene(participer_event_scene);
                                    participer_stage.show();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            });
                            bouton_demander_Participation.setOnAction(demande -> {
                                InvitationParti_service invi_crud1 = new InvitationParti_service();
                                Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "demande");
                                invi_crud1.ajouter_Invitation(invi1);
                                try {
                                    Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                    Scene participer_event_scene = new Scene(participer_event_parent);
                                    Stage participer_stage = (Stage) ((Node) demande.getSource()).getScene().getWindow();
                                    participer_stage.hide();
                                    participer_stage.setScene(participer_event_scene);
                                    participer_stage.show();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            });
                            bouton_demander_annuler.setOnAction(annuler -> {
                                InvitationParti_service invi_crud2 = new InvitationParti_service();
                                invi_crud2.supprimer_Invitation(invi.getId_invi());
                                try {
                                    Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                    Scene participer_event_scene = new Scene(participer_event_parent);
                                    Stage participer_stage = (Stage) ((Node) annuler.getSource()).getScene().getWindow();
                                    participer_stage.hide();
                                    participer_stage.setScene(participer_event_scene);
                                    participer_stage.show();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            });
                            bouton_participé_annuler.setOnAction(anuuler_parti -> {
                                InvitationParti_service invi_crud2 = new InvitationParti_service();
                                invi_crud2.supprimer_Invitation(invi.getId_invi());
                                try {
                                    Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                    Scene participer_event_scene = new Scene(participer_event_parent);
                                    Stage participer_stage = (Stage) ((Node) anuuler_parti.getSource()).getScene().getWindow();
                                    participer_stage.hide();
                                    participer_stage.setScene(participer_event_scene);
                                    participer_stage.show();
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            });
                        });
                        tilePane.setOnMouseExited(exit -> {
                            tilePane.setStyle("-fx-background-color: #dddddd;");
                        });
                    }
                    
                    // Ajouter les éléments au FlowPane
                    tilePane.setPrefRows(4);
                    
                    // Ajouter le FlowPane créé au conteneur parent
                    this.parentContainer.getChildren().add(tilePane);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Liste_evenements_freelancerController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            type_event.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println(newValue.intValue());
                
                if (newValue.intValue() == 2) {
                    list_event.clear();
                    list_event = afficher.afficher_Evenement();
                    parentContainer.getChildren().clear();
                    parentContainer.getChildren().add(type_event);
                    for (Evenements evenement : list_event) {
                        try {
                            ImageView imageView = new ImageView();
                            DatePicker date_picker = new DatePicker();
                            Label type = new Label();
                            // Créer un FlowPane pour le service
                            TilePane tilePane = new TilePane(Orientation.VERTICAL, 20, 20);
                            this.parentContainer.setHgap(10);
                            this.parentContainer.setVgap(10);
                            
                            tilePane.setStyle("-fx-background-color: #dddddd;");
                            tilePane.setHgap(100);
                            
                            Label nom = new Label(evenement.getName_event());
                            imageView = convert_path_to_image(evenement.getLogo_event());
                            System.out.println(imageView.getImage().impl_getUrl());
                            Label description = new Label(evenement.getDesc_event());
                            Label emplacement = new Label(evenement.getEmplacement_event());
                            Label date = new Label(evenement.getDate_event());
                            date_picker = convert_string_to_date(evenement.getDate_event());
                            Label nombre_perso = new Label(Integer.toString(evenement.getNbr_perso_event()));
                            Label responsable = new Label(get_nom_user(evenement.getId_representant()));
                            Button bouton_Participer = new Button("Participer");
                            Button bouton_demander_Participation = new Button("demander invitation");
                            Button bouton_Participé = new Button("Participé");
                            Button bouton_demander_envoyée = new Button("demande envoyée");
                            Button bouton_demander_annuler = new Button("annuler demande");
                            Button bouton_participé_annuler = new Button("supprimer");
                            tilePane.getChildren().addAll(imageView, nom, description, emplacement, date_picker, nombre_perso, type, responsable, bouton_Participer, bouton_Participé, bouton_demander_Participation, bouton_demander_envoyée, bouton_demander_annuler, bouton_participé_annuler);
                            if (list_invi.isEmpty()) {
                                if (evenement.getType_event() == 1) {
                                    
                                    type.setText("sans invitation");
                                    bouton_Participer.setVisible(true);
                                    bouton_demander_Participation.setVisible(false);
                                    bouton_demander_annuler.setVisible(false);
                                    bouton_demander_envoyée.setVisible(false);
                                    bouton_participé_annuler.setVisible(false);
                                    bouton_Participé.setVisible(false);
                                    
                                }
                                if (evenement.getType_event() == 0) {
                                    
                                    type.setText("avec invitation");
                                    bouton_Participer.setVisible(false);
                                    bouton_demander_Participation.setVisible(true);
                                    bouton_demander_annuler.setVisible(false);
                                    bouton_demander_envoyée.setVisible(false);
                                    bouton_participé_annuler.setVisible(false);
                                    bouton_Participé.setVisible(false);
                                    
                                }
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
                                tilePane.setOnMouseEntered(enter1 -> {
                                    tilePane.setStyle("-fx-background-color: #B0B0B0;");
                                    bouton_Participer.setOnAction(participer1 -> {
                                        InvitationParti_service invi_crud = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "participation");
                                        invi_crud.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) participer1.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    
                                    bouton_demander_Participation.setOnAction(demande1 -> {
                                        InvitationParti_service invi_crud1 = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "demande");
                                        invi_crud1.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) demande1.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                });
                                
                            }
                            list_invi = invi_crud3.afficher_Invitation();
                            for (Invitations_participation invi : list_invi) {
                                
                                if (evenement.getType_event() == 1) {
                                    if ("participation".equals(invi.getType())) {
                                        type.setText("sans invitation");
                                        bouton_Participer.setVisible(false);
                                        bouton_demander_Participation.setVisible(false);
                                        bouton_demander_annuler.setVisible(false);
                                        bouton_demander_envoyée.setVisible(false);
                                        bouton_participé_annuler.setVisible(true);
                                        bouton_Participé.setVisible(true);
                                        bouton_Participé.setDisable(true);
                                    } else {
                                        type.setText("sans invitation");
                                        bouton_Participer.setVisible(true);
                                        bouton_demander_Participation.setVisible(false);
                                        bouton_demander_annuler.setVisible(false);
                                        bouton_demander_envoyée.setVisible(false);
                                        bouton_participé_annuler.setVisible(false);
                                        bouton_Participé.setVisible(false);
                                    }
                                }
                                if (evenement.getType_event() == 0) {
                                    if ("demande".equals(invi.getType())) {
                                        type.setText("avec invitation");
                                        bouton_Participer.setVisible(false);
                                        bouton_demander_Participation.setVisible(false);
                                        bouton_demander_annuler.setVisible(true);
                                        bouton_demander_envoyée.setVisible(true);
                                        bouton_participé_annuler.setVisible(false);
                                        bouton_Participé.setVisible(false);
                                        bouton_demander_envoyée.setDisable(true);
                                    } else {
                                        type.setText("avec invitation");
                                        bouton_Participer.setVisible(false);
                                        bouton_demander_Participation.setVisible(true);
                                        bouton_demander_annuler.setVisible(false);
                                        bouton_demander_envoyée.setVisible(false);
                                        bouton_participé_annuler.setVisible(false);
                                        bouton_Participé.setVisible(false);
                                    }
                                }
                                
                                tilePane.setOnMouseEntered(enter -> {
                                    tilePane.setStyle("-fx-background-color: #B0B0B0;");
                                    bouton_Participer.setOnAction(participer -> {
                                        InvitationParti_service invi_crud = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "participation");
                                        invi_crud.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) participer.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    bouton_demander_Participation.setOnAction(demande -> {
                                        InvitationParti_service invi_crud1 = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "demande");
                                        invi_crud1.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) demande.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    bouton_demander_annuler.setOnAction(annuler -> {
                                        InvitationParti_service invi_crud2 = new InvitationParti_service();
                                        invi_crud2.supprimer_Invitation(invi.getId_invi());
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) annuler.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    bouton_participé_annuler.setOnAction(anuuler_parti -> {
                                        InvitationParti_service invi_crud2 = new InvitationParti_service();
                                        invi_crud2.supprimer_Invitation(invi.getId_invi());
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) anuuler_parti.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                });
                                tilePane.setOnMouseExited(exit -> {
                                    tilePane.setStyle("-fx-background-color: #dddddd;");
                                });
                            }
                            
                            // Ajouter les éléments au FlowPane
                            tilePane.setPrefRows(4);
                            
                            // Ajouter le FlowPane créé au conteneur parent
                            this.parentContainer.getChildren().add(tilePane);
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(Liste_evenements_freelancerController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } else if (newValue.intValue() == 0) {
                    list_event.clear();
                    list_event = afficher.sort_event(newValue.intValue());
                    parentContainer.getChildren().clear();
                    parentContainer.getChildren().add(type_event);

                    for (Evenements evenement : list_event) {
                        try {
                            ImageView imageView = new ImageView();
                            DatePicker date_picker = new DatePicker();
                            Label type = new Label();
                            // Créer un FlowPane pour le service
                            TilePane tilePane = new TilePane(Orientation.VERTICAL, 20, 20);
                            this.parentContainer.setHgap(10);
                            this.parentContainer.setVgap(10);
                            
                            tilePane.setStyle("-fx-background-color: #dddddd;");
                            tilePane.setHgap(100);
                            
                            Label nom = new Label(evenement.getName_event());
                            imageView = convert_path_to_image(evenement.getLogo_event());
                            Label description = new Label(evenement.getDesc_event());
                            Label emplacement = new Label(evenement.getEmplacement_event());
                            Label date = new Label(evenement.getDate_event());
                            date_picker = convert_string_to_date(evenement.getDate_event());
                            Label nombre_perso = new Label(Integer.toString(evenement.getNbr_perso_event()));
                            Label responsable = new Label(get_nom_user(evenement.getId_representant()));
                            Button bouton_Participer = new Button("Participer");
                            Button bouton_demander_Participation = new Button("demander invitation");
                            Button bouton_Participé = new Button("Participé");
                            Button bouton_demander_envoyée = new Button("demande envoyée");
                            Button bouton_demander_annuler = new Button("annuler demande");
                            Button bouton_participé_annuler = new Button("supprimer");
                            tilePane.getChildren().addAll(imageView, nom, description, emplacement, date_picker, nombre_perso, type, responsable, bouton_Participer, bouton_Participé, bouton_demander_Participation, bouton_demander_envoyée, bouton_demander_annuler, bouton_participé_annuler);
                            if (list_invi.isEmpty()) {
                                if (evenement.getType_event() == 1) {
                                    
                                    type.setText("sans invitation");
                                    bouton_Participer.setVisible(true);
                                    bouton_demander_Participation.setVisible(false);
                                    bouton_demander_annuler.setVisible(false);
                                    bouton_demander_envoyée.setVisible(false);
                                    bouton_participé_annuler.setVisible(false);
                                    bouton_Participé.setVisible(false);
                                    
                                }
                                if (evenement.getType_event() == 0) {
                                    
                                    type.setText("avec invitation");
                                    bouton_Participer.setVisible(false);
                                    bouton_demander_Participation.setVisible(true);
                                    bouton_demander_annuler.setVisible(false);
                                    bouton_demander_envoyée.setVisible(false);
                                    bouton_participé_annuler.setVisible(false);
                                    bouton_Participé.setVisible(false);
                                    
                                }
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
                                tilePane.setOnMouseEntered(enter1 -> {
                                    tilePane.setStyle("-fx-background-color: #B0B0B0;");
                                    bouton_Participer.setOnAction(participer1 -> {
                                        InvitationParti_service invi_crud = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "participation");
                                        invi_crud.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) participer1.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    
                                    bouton_demander_Participation.setOnAction(demande1 -> {
                                        InvitationParti_service invi_crud1 = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "demande");
                                        invi_crud1.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) demande1.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                });
                                
                            }
                            list_invi = invi_crud3.afficher_Invitation();
                            for (Invitations_participation invi : list_invi) {
                                
                                if (evenement.getType_event() == 1) {
                                    if ("participation".equals(invi.getType())) {
                                        type.setText("sans invitation");
                                        bouton_Participer.setVisible(false);
                                        bouton_demander_Participation.setVisible(false);
                                        bouton_demander_annuler.setVisible(false);
                                        bouton_demander_envoyée.setVisible(false);
                                        bouton_participé_annuler.setVisible(true);
                                        bouton_Participé.setVisible(true);
                                        bouton_Participé.setDisable(true);
                                    } else {
                                        type.setText("sans invitation");
                                        bouton_Participer.setVisible(true);
                                        bouton_demander_Participation.setVisible(false);
                                        bouton_demander_annuler.setVisible(false);
                                        bouton_demander_envoyée.setVisible(false);
                                        bouton_participé_annuler.setVisible(false);
                                        bouton_Participé.setVisible(false);
                                    }
                                }
                                if (evenement.getType_event() == 0) {
                                    if ("demande".equals(invi.getType())) {
                                        type.setText("avec invitation");
                                        bouton_Participer.setVisible(false);
                                        bouton_demander_Participation.setVisible(false);
                                        bouton_demander_annuler.setVisible(true);
                                        bouton_demander_envoyée.setVisible(true);
                                        bouton_participé_annuler.setVisible(false);
                                        bouton_Participé.setVisible(false);
                                        bouton_demander_envoyée.setDisable(true);
                                    } else {
                                        type.setText("avec invitation");
                                        bouton_Participer.setVisible(false);
                                        bouton_demander_Participation.setVisible(true);
                                        bouton_demander_annuler.setVisible(false);
                                        bouton_demander_envoyée.setVisible(false);
                                        bouton_participé_annuler.setVisible(false);
                                        bouton_Participé.setVisible(false);
                                    }
                                }
                                
                                tilePane.setOnMouseEntered(enter -> {
                                    tilePane.setStyle("-fx-background-color: #B0B0B0;");
                                    bouton_Participer.setOnAction(participer -> {
                                        InvitationParti_service invi_crud = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "participation");
                                        invi_crud.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) participer.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    bouton_demander_Participation.setOnAction(demande -> {
                                        InvitationParti_service invi_crud1 = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "demande");
                                        invi_crud1.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) demande.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    bouton_demander_annuler.setOnAction(annuler -> {
                                        InvitationParti_service invi_crud2 = new InvitationParti_service();
                                        invi_crud2.supprimer_Invitation(invi.getId_invi());
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) annuler.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    bouton_participé_annuler.setOnAction(anuuler_parti -> {
                                        InvitationParti_service invi_crud2 = new InvitationParti_service();
                                        invi_crud2.supprimer_Invitation(invi.getId_invi());
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) anuuler_parti.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                });
                                tilePane.setOnMouseExited(exit -> {
                                    tilePane.setStyle("-fx-background-color: #dddddd;");
                                });
                            }
                            
                            // Ajouter les éléments au FlowPane
                            tilePane.setPrefRows(4);
                            
                            // Ajouter le FlowPane créé au conteneur parent
                            this.parentContainer.getChildren().add(tilePane);
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(Liste_evenements_freelancerController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                } else if (newValue.intValue() == 1) {
                    list_event.clear();
                    list_event = afficher.sort_event(newValue.intValue());
                    parentContainer.getChildren().clear();
                    parentContainer.getChildren().add(type_event);

                    for (Evenements evenement : list_event) {
                        try {
                            ImageView imageView = new ImageView();
                            DatePicker date_picker = new DatePicker();
                            Label type = new Label();
                            // Créer un FlowPane pour le service
                            TilePane tilePane = new TilePane(Orientation.VERTICAL, 20, 20);
                            this.parentContainer.setHgap(10);
                            this.parentContainer.setVgap(10);
                            
                            tilePane.setStyle("-fx-background-color: #dddddd;");
                            tilePane.setHgap(100);
                            
                            Label nom = new Label(evenement.getName_event());
                            imageView = convert_path_to_image(evenement.getLogo_event());
                            Label description = new Label(evenement.getDesc_event());
                            Label emplacement = new Label(evenement.getEmplacement_event());
                            Label date = new Label(evenement.getDate_event());
                            date_picker = convert_string_to_date(evenement.getDate_event());
                            Label nombre_perso = new Label(Integer.toString(evenement.getNbr_perso_event()));
                            Label responsable = new Label(get_nom_user(evenement.getId_representant()));
                            Button bouton_Participer = new Button("Participer");
                            Button bouton_demander_Participation = new Button("demander invitation");
                            Button bouton_Participé = new Button("Participé");
                            Button bouton_demander_envoyée = new Button("demande envoyée");
                            Button bouton_demander_annuler = new Button("annuler demande");
                            Button bouton_participé_annuler = new Button("supprimer");
                            tilePane.getChildren().addAll(imageView, nom, description, emplacement, date_picker, nombre_perso, type, responsable, bouton_Participer, bouton_Participé, bouton_demander_Participation, bouton_demander_envoyée, bouton_demander_annuler, bouton_participé_annuler);
                            if (list_invi.isEmpty()) {
                                if (evenement.getType_event() == 1) {
                                    
                                    type.setText("sans invitation");
                                    bouton_Participer.setVisible(true);
                                    bouton_demander_Participation.setVisible(false);
                                    bouton_demander_annuler.setVisible(false);
                                    bouton_demander_envoyée.setVisible(false);
                                    bouton_participé_annuler.setVisible(false);
                                    bouton_Participé.setVisible(false);
                                    
                                }
                                if (evenement.getType_event() == 0) {
                                    
                                    type.setText("avec invitation");
                                    bouton_Participer.setVisible(false);
                                    bouton_demander_Participation.setVisible(true);
                                    bouton_demander_annuler.setVisible(false);
                                    bouton_demander_envoyée.setVisible(false);
                                    bouton_participé_annuler.setVisible(false);
                                    bouton_Participé.setVisible(false);
                                    
                                }
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
                                tilePane.setOnMouseEntered(enter1 -> {
                                    tilePane.setStyle("-fx-background-color: #B0B0B0;");
                                    bouton_Participer.setOnAction(participer1 -> {
                                        InvitationParti_service invi_crud = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "participation");
                                        invi_crud.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) participer1.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    
                                    bouton_demander_Participation.setOnAction(demande1 -> {
                                        InvitationParti_service invi_crud1 = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "demande");
                                        invi_crud1.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) demande1.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                });
                                
                            }
                            list_invi = invi_crud3.afficher_Invitation();
                            for (Invitations_participation invi : list_invi) {
                                
                                if (evenement.getType_event() == 1) {
                                    if ("participation".equals(invi.getType())) {
                                        type.setText("sans invitation");
                                        bouton_Participer.setVisible(false);
                                        bouton_demander_Participation.setVisible(false);
                                        bouton_demander_annuler.setVisible(false);
                                        bouton_demander_envoyée.setVisible(false);
                                        bouton_participé_annuler.setVisible(true);
                                        bouton_Participé.setVisible(true);
                                        bouton_Participé.setDisable(true);
                                    } else {
                                        type.setText("sans invitation");
                                        bouton_Participer.setVisible(true);
                                        bouton_demander_Participation.setVisible(false);
                                        bouton_demander_annuler.setVisible(false);
                                        bouton_demander_envoyée.setVisible(false);
                                        bouton_participé_annuler.setVisible(false);
                                        bouton_Participé.setVisible(false);
                                    }
                                }
                                if (evenement.getType_event() == 0) {
                                    if ("demande".equals(invi.getType())) {
                                        type.setText("avec invitation");
                                        bouton_Participer.setVisible(false);
                                        bouton_demander_Participation.setVisible(false);
                                        bouton_demander_annuler.setVisible(true);
                                        bouton_demander_envoyée.setVisible(true);
                                        bouton_participé_annuler.setVisible(false);
                                        bouton_Participé.setVisible(false);
                                        bouton_demander_envoyée.setDisable(true);
                                    } else {
                                        type.setText("avec invitation");
                                        bouton_Participer.setVisible(false);
                                        bouton_demander_Participation.setVisible(true);
                                        bouton_demander_annuler.setVisible(false);
                                        bouton_demander_envoyée.setVisible(false);
                                        bouton_participé_annuler.setVisible(false);
                                        bouton_Participé.setVisible(false);
                                    }
                                }
                                
                                tilePane.setOnMouseEntered(enter -> {
                                    tilePane.setStyle("-fx-background-color: #B0B0B0;");
                                    bouton_Participer.setOnAction(participer -> {
                                        InvitationParti_service invi_crud = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "participation");
                                        invi_crud.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) participer.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    bouton_demander_Participation.setOnAction(demande -> {
                                        InvitationParti_service invi_crud1 = new InvitationParti_service();
                                        Invitations_participation invi1 = new Invitations_participation(evenement.getId_event(), evenement.getId_representant(), "demande");
                                        invi_crud1.ajouter_Invitation(invi1);
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) demande.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    bouton_demander_annuler.setOnAction(annuler -> {
                                        InvitationParti_service invi_crud2 = new InvitationParti_service();
                                        invi_crud2.supprimer_Invitation(invi.getId_invi());
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) annuler.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                    bouton_participé_annuler.setOnAction(anuuler_parti -> {
                                        InvitationParti_service invi_crud2 = new InvitationParti_service();
                                        invi_crud2.supprimer_Invitation(invi.getId_invi());
                                        try {
                                            Parent participer_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                                            Scene participer_event_scene = new Scene(participer_event_parent);
                                            Stage participer_stage = (Stage) ((Node) anuuler_parti.getSource()).getScene().getWindow();
                                            participer_stage.hide();
                                            participer_stage.setScene(participer_event_scene);
                                            participer_stage.show();
                                        } catch (IOException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    });
                                });
                                tilePane.setOnMouseExited(exit -> {
                                    tilePane.setStyle("-fx-background-color: #dddddd;");
                                });
                                
                            }
                            
                            // Ajouter les éléments au FlowPane
                            tilePane.setPrefRows(4);
                            
                            // Ajouter le FlowPane créé au conteneur parent
                            this.parentContainer.getChildren().add(tilePane);
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(Liste_evenements_freelancerController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }

            });
             
            this.parentContainer.prefHeightProperty().bind(root.heightProperty());
            this.parentContainer.prefWidthProperty().bind(root.widthProperty());

            // Ajouter le conteneur parent au root de la scène
            root.getChildren().add(this.parentContainer);

        });

    }

}
