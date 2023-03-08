/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JToolTip;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import static tn.esprit.codefellaz.controllers.Ajouter_evenementsController.isNumeric;
import tn.esprit.codefellaz.entities.Evenements;
import tn.esprit.codefellaz.services.Evenements_service;
import tn.esprit.codefellaz.utils.MyConnection;
import tn.esprit.codefellaz.utils.purgoMalumApi;

/**
 * FXML Controller class
 *
 * @author MAG-PC
 */
public class Modifier_evenementsController implements Initializable {
     private JXMapViewer mapViewer;
    String city = ""; // Replace with the name of the city you want to display
    String json = null;
     List<String> data = new ArrayList<>();
    List<String> data2 = new ArrayList<>();

    @FXML
    private TextField nom_ajout;
    @FXML
    private TextField logo_ajout;
    @FXML
    private TextField emplace_ajout;
    @FXML
    private TextArea desc_ajout;
    @FXML
    private DatePicker date_ajout;
    @FXML
    private TextField nbr_perso_ajout;
    @FXML
    private ChoiceBox<String> type_event;
    @FXML
    private Button but_sauv;
    @FXML
    private Button but_annuler;
    @FXML
    private TextField nom_res_ajout;
    @FXML
    private TextField id_hidden;

    /**
     * Initializes the controller class.
     */
    Connection cnx;
    @FXML
    private Label label_nom;
    @FXML
    private Label labe_logo;
    @FXML
    private Label label_desc;
    @FXML
    private Label label_emplac;
    @FXML
    private Label label_date;
    @FXML
    private Label label_nbr_per;
    @FXML
    private Label label_id_rep;
    @FXML
    private ChoiceBox<String> emplacement_choice;

    public Modifier_evenementsController() {
        cnx = MyConnection.getInstance().getCnx();
    }

    public void set_data_in_the_view(Evenements event) {
        Liste_evenementsController controller_liste = new Liste_evenementsController();

        id_hidden.setText(Integer.toString(event.getId_event()));
        nom_ajout.setText(event.getName_event());
        logo_ajout.setText(event.getLogo_event());
        emplace_ajout.setText(event.getEmplacement_event());
        desc_ajout.setText(event.getDesc_event());
        date_ajout.setValue(LocalDate.parse(event.getDate_event(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        nbr_perso_ajout.setText(Integer.toString(event.getNbr_perso_event()));
        if (event.getType_event() == 1) {
            type_event.setValue("sans invitation");
        }
        if (event.getType_event() == 0) {
            type_event.setValue("avec invitation");
        }
        nom_res_ajout.setText(controller_liste.get_nom_user(event.getId_representant()));

    }

    public int find_id_of_responsable(String nom) {
        Evenements_service es = new Evenements_service();
        List<Evenements> list_event = es.afficher_Evenement2(nom);
        int id_res = 0;
        for (Evenements evenement : list_event) {
            id_res = evenement.getId_representant();
        }
        return id_res;
    }

    public String getTheUserFilePath() {
        String path = "";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choisir l'image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"),
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
        );

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {

            path = file.getName();

        } else {
            System.out.println("error"); // or something else
        }
        return path;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WebService.setUserName("azizgar"); // add your username here
        type_event.getItems().add("avec invitation");
        type_event.getItems().add("sans invitation");
        type_event.getSelectionModel().select("sans invitation");
        Evenements_service afficher = new Evenements_service();
        logo_ajout.setOnMouseClicked(entered -> {
            logo_ajout.setText("C:/Users/MAG-PC/OneDrive/Pictures/" + getTheUserFilePath());

        });

        but_annuler.setOnAction(annuler -> {
            try {
                Parent annuler_modif_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                Scene annuler_modif_scene = new Scene(annuler_modif_event_parent);
                Stage annuler_modif_stage = (Stage) ((Node) annuler.getSource()).getScene().getWindow();
                annuler_modif_stage.hide();
                annuler_modif_stage.setScene(annuler_modif_scene);
                annuler_modif_stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        
        emplace_ajout.setOnMouseClicked(event1->{
            city = emplace_ajout.getText();
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
                    tooltip.setTipText("Java");
                    tooltip.setComponent(jXMapKit.getMainMap());
                    jXMapKit.getMainMap().add(tooltip);

                    jXMapKit.setZoom(11);
                    jXMapKit.setAddressLocation(gp);

                    jXMapKit.getMainMap().addMouseMotionListener(new MouseMotionListener() {
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            // ignore
                        }

                        @Override
                        public void mouseMoved(MouseEvent e) {
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
        
        emplace_ajout.setOnKeyPressed(event->{
             if (event.getCode() == KeyCode.ENTER) {
                //data2.clear();
                emplacement_choice.getItems().clear();
                emplacement_choice.setValue(null);
                ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
                searchCriteria.setQ(emplace_ajout.getText());
                ToponymSearchResult searchResult;
                try {
                    searchResult = WebService.search(searchCriteria);
                    for (Toponym toponym : searchResult.getToponyms()) {
                        data.add(toponym.getName() + " " + toponym.getCountryName());
                        data2.add(toponym.getCountryName());
                        System.out.println(toponym.getName() + " " + toponym.getCountryName());
                    }
                    emplacement_choice.getItems().addAll(data);
                    data.clear();

                    //choiceBox.setItems(FXCollections.observableList(data));
                } catch (Exception ex) {
                    ex.getMessage();
                }

            }
        });
        emplacement_choice.setOnAction(choice->{
            if (!data2.isEmpty()) {
                System.out.println(data2.get(0));
                city = data2.get(0);
                String url1 = "https://nominatim.openstreetmap.org/search?q=" + city + "&format=json&addressdetails=1&limit=1";
                data2.clear();
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
                        public void mouseDragged(MouseEvent e) {
                            // ignore
                        }

                        @Override
                        public void mouseMoved(MouseEvent e) {
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
            }
        });

        but_sauv.setOnAction(sauv -> {
            Evenements_service modifier = new Evenements_service();
            LocalDate today = LocalDate.now();
            int type;
            if ("sans invitation".equals(type_event.getSelectionModel().getSelectedItem())) {
                type = 1;
            } else {
                type = 0;
            }
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            purgoMalumApi test_bad_words = new purgoMalumApi();
            if (nom_ajout.getText().isEmpty() || logo_ajout.getText().isEmpty() || desc_ajout.getText().isEmpty() || emplace_ajout.getText().isEmpty() || date_ajout.getValue().format(dateFormatter).isEmpty() || nbr_perso_ajout.getText().isEmpty() || nom_res_ajout.getText().isEmpty()) {
                label_nom.setVisible(true);
                label_nom.setTextFill(Color.color(1, 0, 0));
                label_nom.setText("le nom ne peut pas étre vide!!!");
                nom_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

                labe_logo.setVisible(true);
                labe_logo.setTextFill(Color.color(1, 0, 0));
                labe_logo.setText("le logo ne peut pas étre vide!!!");
                logo_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

                label_desc.setVisible(true);
                label_desc.setTextFill(Color.color(1, 0, 0));
                label_desc.setText("la description peut pas étre vide!!!");
                desc_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

                label_emplac.setVisible(true);
                label_emplac.setTextFill(Color.color(1, 0, 0));
                label_emplac.setText("l'emplacement ne peut pas étre vide!!!");
                emplace_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

                label_date.setVisible(true);
                label_date.setTextFill(Color.color(1, 0, 0));
                label_date.setText("la date ne peut pas étre vide!!!");
                date_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

                label_nbr_per.setVisible(true);
                label_nbr_per.setTextFill(Color.color(1, 0, 0));
                label_nbr_per.setText("le nombre de personnes ne peut pas étre vide!!!");
                nbr_perso_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

                label_id_rep.setVisible(true);
                label_id_rep.setTextFill(Color.color(1, 0, 0));
                label_id_rep.setText("le nom du responsable ne peut pas étre vide!!!");
                nom_res_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            } else if (isNumeric(nbr_perso_ajout.getText()) == false) {
                label_nom.setVisible(false);
                labe_logo.setVisible(false);
                label_desc.setVisible(false);
                label_emplac.setVisible(false);
                label_date.setVisible(false);

                label_nbr_per.setVisible(true);
                label_nbr_per.setTextFill(Color.color(1, 0, 0));
                label_nbr_per.setText("le nombre de personnes ne peut etre que des chiffres!!!");
                nbr_perso_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            } else if (date_ajout.getValue().isBefore(today)) {
                label_date.setVisible(true);
                label_date.setTextFill(Color.color(1, 0, 0));
                label_date.setText("la date ne peut pas étre avant la date d'aujourd'hui!!!");
                date_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            else if(test_bad_words.test_if_has_bad_words(nom_ajout.getText()).contains("true") || test_bad_words.test_if_has_bad_words(desc_ajout.getText()).contains("true") || test_bad_words.test_if_has_bad_words(emplace_ajout.getText()).contains("true")){
                label_nom.setVisible(false);
                labe_logo.setVisible(false);
                label_desc.setVisible(false);
                label_emplac.setVisible(false);
                label_date.setVisible(false);
                label_nbr_per.setVisible(false);
                label_id_rep.setVisible(false);
                
                 label_nom.setVisible(true);
                label_nom.setTextFill(Color.color(1, 0, 0));
                label_nom.setText("le nom ne peut pas contenir de gros mot!!!");
                nom_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                
                 label_desc.setVisible(true);
                label_desc.setTextFill(Color.color(1, 0, 0));
                label_desc.setText("la description ne peut pas contenir de gros mot!!!");
                desc_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

                label_emplac.setVisible(true);
                label_emplac.setTextFill(Color.color(1, 0, 0));
                label_emplac.setText("l'emplacement ne peut pas contenir de gros mot!!!");
                emplace_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            else{
            /*else if (!(logo_ajout.getText().contains("C:/")) && (!(logo_ajout.getText().contains(".jpg")) || !(logo_ajout.getText().contains(".png")))){
                  labe_logo.setVisible(true);
                 labe_logo.setTextFill(Color.color(1, 0, 0));
                 labe_logo.setText("Veuillez respecter le format (C:/Votre_image.jpg)!!!");
                logo_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }*/
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to edit this event?");
            alert.setContentText("Click yes to continue or no to cancel.");

            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                // user clicked "Yes"
                // handle "Yes" button event
                Evenements event = new Evenements(Integer.parseInt(id_hidden.getText()), nom_ajout.getText(), logo_ajout.getText(), desc_ajout.getText(), emplace_ajout.getText(), date_ajout.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), Integer.parseInt(nbr_perso_ajout.getText()), type, find_id_of_responsable(nom_res_ajout.getText()));
            modifier.modifier_Evenement(event);
            try {
                Parent sauv_modif_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                Scene sauv_modif_scene = new Scene(sauv_modif_event_parent);
                Stage sauv_modif_stage = (Stage) ((Node) sauv.getSource()).getScene().getWindow();
                sauv_modif_stage.hide();
                sauv_modif_stage.setScene(sauv_modif_scene);
                sauv_modif_stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            } else {
                // user clicked "No"
                // handle "No" button event
                try {
                Parent annuler_modif_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                Scene annuler_modif_scene = new Scene(annuler_modif_event_parent);
                Stage annuler_modif_stage = (Stage) ((Node) sauv.getSource()).getScene().getWindow();
                annuler_modif_stage.hide();
                annuler_modif_stage.setScene(annuler_modif_scene);
                annuler_modif_stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            }
            }
        });

    }

}
