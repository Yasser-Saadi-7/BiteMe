package controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import entities.Message1;
import entities.MessageType;
import entities.Restaurant;

/**
 * Controller class for the Restaurant Page in the GUI application.
 * This class manages the restaurant selection process and handles user interactions
 * such as showing the menu for the selected restaurant, navigating back to the previous page,
 * and logging out of the application.
 * The FXML file associated with this controller is "Restaurant.fxml".
 *
 * @author Yasser Saadi
 */
public class RestaurantController implements Initializable {

    /** Observable list of restaurants to be displayed in the table */
    ObservableList<Restaurant> resturants;
    /** Static reference to the selected restaurant */
    public static Restaurant resturant;

    @FXML
    private ListView<Restaurant> restaurantList;
    /** Button for showing the menu of the selected restaurant */
    @FXML
    private Button btnshowmenu;

    /**
     * Logs the user out and navigates them back to the login page.
     * This method is triggered when the "Log Out" button is clicked.
     *
     * @param event the ActionEvent triggered when the button is clicked
     */
    @FXML
    void logOut(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        LogInController logInController = new LogInController();
        try {
            logInController.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates the user back to the Client Page.
     * This method is triggered when the "Back" button is clicked.
     *
     * @param event the ActionEvent triggered when the button is clicked
     */
    @FXML
    void back(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
        ClientPageController AFrame = new ClientPageController();
        try {
            AFrame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the menu of the selected restaurant.
     * This method is triggered when the "Show Menu" button is clicked.
     * If no restaurant is selected, an error alert is shown.
     *
     * @param event the ActionEvent triggered when the button is clicked
     */
    @FXML
    void showMenu(ActionEvent event) {
        if (restaurantList.getSelectionModel().getSelectedItem() != null) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
            MealsTypeController AFrame = new MealsTypeController();
            try {
                AFrame.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Error");
            a.setHeaderText("should you Select a resturant:");
            a.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the TableView columns and populates the table with data retrieved from the server.
     *
     * @param location  the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fetch restaurants from server
        ClientUI.chat.accept(new Message1(MessageType.showRestaurant, null));
        resturants = FXCollections.observableArrayList(ChatClient.resList);

        // Fill logo filenames based on names
        assignLogos(resturants);

        // Bind list items
        restaurantList.setItems(resturants);

        // Custom cell: logo + name + details
        // ******************* FIX 1 HERE *******************
        restaurantList.setCellFactory(list -> new ListCell<Restaurant>() { // Removed <> and specified <Restaurant>
            private final HBox content;
            private final ImageView logo;
            private final VBox details;
            private final Label name;
            private final Label info;

            {
                logo = new ImageView();
                logo.setFitWidth(60);
                logo.setFitHeight(60);
                logo.setPreserveRatio(true);

                name = new Label();
                name.getStyleClass().add("heading-2");
                info = new Label();
                info.getStyleClass().add("muted");

                details = new VBox(name, info);
                details.setSpacing(4);

                content = new HBox(10, logo, details);
                content.setPadding(new Insets(8));
            }

            @Override
            protected void updateItem(Restaurant item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    // Load logo from resources (if you have a logoFile field in Restaurant)
                    try {
                        if (item.getLogoFile() != null) {
                            logo.setImage(new Image(getClass().getResourceAsStream("/images/" + item.getLogoFile())));
                        } else {
                            logo.setImage(new Image(getClass().getResourceAsStream("/images/default.png")));
                        }
                    } catch (Exception e) {
                        logo.setImage(new Image(getClass().getResourceAsStream("/images/default.png")));
                    }

                    name.setText(item.getResName());
                    info.setText(item.getResLocation() + " • " + item.getResPhoneNum());
                    setGraphic(content);
                }
            }
        });

        // Track selected restaurant
        restaurantList.setOnMousePressed((MouseEvent event) -> {
            resturant = restaurantList.getSelectionModel().getSelectedItem();
        });
    }

    /**
     * Starts the Restaurant Page.
     * This method is used to load the Restaurant.fxml file and display the Restaurant Page to the user.
     *
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/boundry/Restaurant.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Restaurant");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static final Map<String, String> LOGO_MAP = new HashMap<>();
    static {
        LOGO_MAP.put("Vivino", "vivino.jpg");
        LOGO_MAP.put("Alena", "alena.png");
        LOGO_MAP.put("Disfrutar", "disfrutar.png");
        LOGO_MAP.put("YasserFalfel", "yasserFalafel.png");
    }

    private static String norm(String s) {
        if (s == null)
            return "";
        return s.toLowerCase(Locale.ROOT)
                .replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]+", " ") // keep letters/digits, collapse symbols
                .trim()
                .replaceAll("\\s+", " "); // collapse spaces
    }

    private static void assignLogos(List<Restaurant> list) {
        // Build a normalized map once
        Map<String, String> normalized = new HashMap<>();
        // ******************* FIX 2 HERE *******************
        for (Map.Entry<String, String> e : LOGO_MAP.entrySet()) { // Replaced 'var' with the explicit type
            normalized.put(norm(e.getKey()), e.getValue());
        }

        for (Restaurant r : list) {
            String key = norm(r.getResName());
            String file = normalized.getOrDefault(key, "default.png");
            r.setLogoFile(file); // <-- requires getLogoFile/setLogoFile in Restaurant
        }
    }
}