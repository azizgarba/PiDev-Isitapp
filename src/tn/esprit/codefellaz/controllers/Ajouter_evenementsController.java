/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.codefellaz.entities.Evenements;
import tn.esprit.codefellaz.services.Evenements_service;
import tn.esprit.codefellaz.utils.MyConnection;
import tn.esprit.codefellaz.utils.purgoMalumApi;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Rectangle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.TilePane;
import javax.swing.JFrame;
import javax.swing.JToolTip;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.geonames.Toponym;
import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.UserService;

/**
 * FXML Controller class
 *
 * @author MAG-PC
 */
public class Ajouter_evenementsController implements Initializable {
     private JXMapViewer mapViewer;
    String city = ""; // Replace with the name of the city you want to display
    String json = null;
     List<String> data = new ArrayList<>();
    List<String> data2 = new ArrayList<>();

    @FXML
    private ChoiceBox<String> type_event;
    @FXML
    private Button but_ajout;

    /**
     * Initializes the controller class.
     */
    Connection cnx;
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
    private TextField id_res_ajout;
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
    private Button but_annuler;
    @FXML
    private ChoiceBox<String> emplacement_choice;

    public Ajouter_evenementsController() {
        cnx = MyConnection.getInstance().getCnx();
    }
    public String fileName;
    public Path source;
    public Path destination;
    public String imagePath;

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
    

    public String returnPathOfImage(){
        
        FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose an image file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.jfif")
            );
            
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                try {
                    Image image = new Image(selectedFile.toURI().toString());
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
            
        return imagePath;    
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
        
       
        
        //get the current freelancer id
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
       
            
             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
                id_res_ajout.setText(Integer.toString(userconnected.getId()));
        id_res_ajout.setEditable(false);
                
     
            } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        type_event.getItems().add("avec invitation");
        type_event.getItems().add("sans invitation");
        type_event.getSelectionModel().select("sans invitation");
         WebService.setUserName("azizgar"); // add your username here

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
        logo_ajout.setOnMouseClicked(clicked -> {
            logo_ajout.setText(returnPathOfImage());
            //logo_ajout.setText("C:/Users/MAG-PC/OneDrive/Pictures/" + getTheUserFilePath());
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
        but_ajout.setOnAction(ajout -> {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate today = LocalDate.now();
            purgoMalumApi test_bad_words = new purgoMalumApi();

            if (nom_ajout.getText().isEmpty() || logo_ajout.getText().isEmpty() || desc_ajout.getText().isEmpty() || emplace_ajout.getText().isEmpty() || date_ajout.getValue().format(dateFormatter).isEmpty() || nbr_perso_ajout.getText().isEmpty() || id_res_ajout.getText().isEmpty()) {
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
                label_id_rep.setText("l'id du responsable ne peut pas étre vide!!!");
                id_res_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            } else if (isNumeric(nbr_perso_ajout.getText()) == false || isNumeric(id_res_ajout.getText()) == false) {
                label_nom.setVisible(false);
                labe_logo.setVisible(false);
                label_desc.setVisible(false);
                label_emplac.setVisible(false);
                label_date.setVisible(false);

                label_id_rep.setVisible(true);
                label_id_rep.setTextFill(Color.color(1, 0, 0));
                label_id_rep.setText("l'id ne peut etre que des chiffres!!!");
                id_res_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");

                label_nbr_per.setVisible(true);
                label_nbr_per.setTextFill(Color.color(1, 0, 0));
                label_nbr_per.setText("le nombre de personnes ne peut etre que des chiffres!!!");
                nbr_perso_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            } else if (date_ajout.getValue().isBefore(today)) {
                label_date.setVisible(true);
                label_date.setTextFill(Color.color(1, 0, 0));
                label_date.setText("la date ne peut pas étre avant la date d'aujourd'hui!!!");
                date_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            } else if (test_bad_words.test_if_has_bad_words(nom_ajout.getText()).contains("true") || test_bad_words.test_if_has_bad_words(desc_ajout.getText()).contains("true") || test_bad_words.test_if_has_bad_words(emplace_ajout.getText()).contains("true")) {
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
            } /*else if (!(logo_ajout.getText().contains("C:/")) && (!(logo_ajout.getText().contains(".jpg")) || !(logo_ajout.getText().contains(".png")))){
                  labe_logo.setVisible(true);
                 labe_logo.setTextFill(Color.color(1, 0, 0));
                 labe_logo.setText("Veuillez respecter le format (C:/Votre_image.jpg)!!!");
                logo_ajout.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }*/ else {
                Evenements_service ajouter = new Evenements_service();
                Evenements event = new Evenements(nom_ajout.getText(), logo_ajout.getText(), desc_ajout.getText(), city, date_ajout.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), Integer.parseInt(nbr_perso_ajout.getText()), type_event.getSelectionModel().getSelectedIndex(), Integer.parseInt(id_res_ajout.getText()));
                ajouter.ajouter_Evenement2(event);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Votre evenements a ete ajouter !!!");

                alert.show();

                try {
                    Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                    Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                    Stage ajout_stage = (Stage) ((Node) ajout.getSource()).getScene().getWindow();
                    ajout_stage.hide();
                    ajout_stage.setScene(ajouter_event_scene);
                    ajout_stage.show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        });

    }

}
