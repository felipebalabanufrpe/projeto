package com.example.demo9;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.ContentDisplay;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class HelloController {

    public class EventItem {
        private String id;
        private String title;
        private String date;
        private String location;
        private String description;
        private String price;
        private String maxParticipants;

        public EventItem(String id, String title, String date, String location,
                         String description, String price, String maxParticipants) {
            this.id = id;
            this.title = title;
            this.date = date;
            this.location = location;
            this.description = description;
            this.price = price;
            this.maxParticipants = maxParticipants;
        }

        // Getters
        public String getId() { return id; }
        public String getTitle() { return title; }
        public String getDate() { return date; }
        public String getLocation() { return location; }
        public String getDescription() { return description; }
        public String getPrice() { return price; }
        public String getMaxParticipants() { return maxParticipants; }

        @Override
        public String toString() {
            return String.format(
                    "Title: %s, \n Date: %s, \n Location: %s, \n Description: %s, \n Price: %s, \n Max: %s",
                    title, date, location, description, price, maxParticipants
            );
        }
    }




    private void subscribeToEvent(EventItem event) {
        System.out.println("Subscribing to event: " + event.getId());
        String user = Session.getLoggedInUser();
        if (user == null || user.isEmpty()) {
            System.out.println("No logged-in user found. Please log in first.");
            return;
        }
        if (event == null) {
            System.out.println("Event is null, cannot subscribe.");
            return;
        }
        try {
            // Build JSON: {"userName": "...", "eventName": "..."}
            String subscribeJson = String.format(
                    "{\"userName\":\"%s\",\"eventName\":\"%s\"}",
                    user, event.getTitle()
            );

            // Send POST to /subscribe
            String response = sendPostRequest(subscribeJson, "http://localhost:2001/subscribe");
            System.out.println("subscribeToEvent response: " + response);

            // Optionally parse and handle response
            if (!"default".equals(response)) {
                JSONObject respObj = (JSONObject) parser.parse(response);
                System.out.println("Subscribe call result: " + respObj.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final JSONParser parser = new JSONParser();

    @FXML
    private TextField nomeTextField;

    @FXML
    private PasswordField senhaTextField;

    @FXML
    private VBox certificadosVBox;

    @FXML
    private Button certificadosHomeButton;

    @FXML
    private Button inscricoesButton2;

    @FXML
    private Button contaButton2;

    @FXML
    private Button homeButton2;

    @FXML
    private Button contaButton3;

    @FXML
    private Button homeButton3;

    @FXML
    private TextField titleTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField locationTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField maxParticipantsTextField;

    @FXML
    private ListView<EventItem> eventsListView;  // import javafx.scene.control.ListView

    private String loggedInUser;

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @FXML
    private Button subscribeButton;






    @FXML
    protected void onCriarContaClick() {
        String nome = nomeTextField.getText();
        String senha = senhaTextField.getText();

        if (nome == null || nome.isEmpty() || senha == null || senha.isEmpty()) {
            System.out.println("Nome or Senha cannot be empty.");
            return;
        }

        String json = createJson(nome, senha);
        sendPostRequest(json, "http://localhost:2001/signup");

    }

    @FXML
    protected void onEntrarContaClick() {
        String nome = nomeTextField.getText();
        String senha = senhaTextField.getText();

        String json = createJson(nome, senha);
        String response = sendPostRequest(json, "http://localhost:2001/login");

        try {
            JSONObject responseObject = (JSONObject) parser.parse(response);
            System.out.println("Response Object: " + responseObject);

            String name = (String) responseObject.get("name");
            if (name != null) {
                // Store the logged-in user's name
                loggedInUser = name;
                Session.setLoggedInUser(name);
                System.out.println("Logged-in user: " + name);
                // Then change scene
                changeScene("home.fxml");
                loadEventsFromDatabase();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onCertificadosClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("certificados.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) certificadosVBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Error: UI element not available in the current scene.");
        } catch (Exception e) {
            System.out.println("Error loading certificados.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    protected void onInscricoesClick() {
        try {
            System.out.println("Inscricoes clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inscricoes.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) certificadosVBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Error: UI element not available in the current scene.");
        } catch (Exception e) {
            System.out.println("Error loading certificados.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML protected void onContaClick() {
        try {
            System.out.println("Conta clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("conta.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) certificadosVBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Error: UI element not available in the current scene.");
        } catch (Exception e) {
            System.out.println("Error loading certificados.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void onHomeClick1() {
        try {
            System.out.println("Home clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();
            HelloController homeController = loader.getController();
            homeController.setLoggedInUser(loggedInUser);
            homeController.loadEventsFromDatabase();
            Stage stage = (Stage) certificadosHomeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Error: UI element not available in the current scene.");
        } catch (Exception e) {
            System.out.println("Error loading certificados.fxml: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    protected void onHomeClick2() {
        try {
            System.out.println("Home clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();
            HelloController homeController = loader.getController();
            homeController.setLoggedInUser(loggedInUser);
            homeController.loadEventsFromDatabase();

            Stage stage = (Stage) homeButton2.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Error: UI element not available in the current scene.");
        } catch (Exception e) {
            System.out.println("Error loading certificados.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        loadEventsFromDatabase();
    }

    @FXML
    protected void onInscricoesClick2() {
        try {
            System.out.println("Inscricoes2 clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inscricoes.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) inscricoesButton2.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Error: UI element not available in the current scene.");
        } catch (Exception e) {
            System.out.println("Error loading certificados.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML protected void onContaClick2() {
        try {
            System.out.println("Conta clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("conta.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) contaButton2.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Error: UI element not available in the current scene.");
        } catch (Exception e) {
            System.out.println("Error loading certificados.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML protected void onContaClick3() {
        try {
            System.out.println("Conta clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("conta.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) contaButton3.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Error: UI element not available in the current scene.");
        } catch (Exception e) {
            System.out.println("Error loading certificados.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void onHomeClick3() {
        try {
            System.out.println("Home clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();

            HelloController homeController = loader.getController();
            homeController.setLoggedInUser(loggedInUser);
            homeController.loadEventsFromDatabase();

            Stage stage = (Stage) homeButton3.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("Error: UI element not available in the current scene.");
        } catch (Exception e) {
            System.out.println("Error loading certificados.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        loadEventsFromDatabase();
    }

    private String createJson(String nome, String senha) {
        return String.format("{\"nome\":\"%s\",\"senha\":\"%s\"}", nome, senha);
    }

    private String sendPostRequest(String json, String uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri)) // Replace with your server URL
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            System.out.println("JSON Payload: " + json);
            System.out.println("Request formed: " + request);

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Signup successful: " + response.body());
                return response.body();
            } else {
                System.out.println("Signup failed: " + response.statusCode());
                System.out.println("Response: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return "default";
    }

    private String sendGetRequest(String uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // If the server returns a 200, we treat that as success
                System.out.println("GET request successful. Response:");
                System.out.println(response.body());
                return response.body();
            } else {
                // If it's not 200, log an error
                System.out.println("GET request failed: " + response.statusCode());
                System.out.println("Response: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error during GET request: " + e.getMessage());
            e.printStackTrace();
        }
        // If something goes wrong, return "default" as you do in sendPostRequest
        return "default";
    }

    private void changeScene(String fxmlFile) {
        try {
            // Create an FXMLLoader instance to load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

            // Load the root element (the top-level container) of the new FXML file
            Parent root = loader.load();
            HelloController homeController = loader.getController();
            homeController.loadEventsFromDatabase();
            homeController.setLoggedInUser(this.loggedInUser);
            // Get the current stage (window) using the TextField's scene
            Stage stage = (Stage) nomeTextField.getScene().getWindow();

            // Set a new scene on the current stage, using the loaded root as its layout
            stage.setScene(new Scene(root));

            // Show the updated stage with the new scene
            stage.show();
        } catch (Exception e) {
            // If any exception occurs (e.g., the FXML file is not found or has errors),
            // print an error message and the stack trace to debug the issue
            System.out.println("Error loading new scene: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onCreateEvent() {
        try {
            // 1. Gather all text fields as strings
            String title = titleTextField.getText();
            String date = dateTextField.getText();
            String location = locationTextField.getText();
            String description = descriptionTextField.getText();
            String price = priceTextField.getText();
            String maxParticipants = maxParticipantsTextField.getText();

            // 2. Build JSON. All fields are strings, so we wrap them in quotes in the JSON.
            String eventJson = String.format(
                    "{" +
                            "\"title\":\"%s\"," +
                            "\"date\":\"%s\"," +
                            "\"location\":\"%s\"," +
                            "\"description\":\"%s\"," +
                            "\"price\":\"%s\"," +
                            "\"maxParticipants\":\"%s\"" +
                            "}",
                    title, date, location, description, price, maxParticipants
            );

            // 3. Send the JSON to /createEvent using the existing method
            //    This will receive a 200 status code if successful
            String response = sendPostRequest(eventJson, "http://localhost:2001/createEvent");
            System.out.println("onCreateEvent response: " + response);

            // 4. (Optional) parse the response if not "default"
            if (!"default".equals(response)) {
                try {
                    JSONObject responseObject = (JSONObject) parser.parse(response);
                    System.out.println("Created event: " + responseObject);
                    // Possibly show success message in the UI
                } catch (ParseException e) {
                    System.out.println("Error parsing createEvent response: " + e.getMessage());
                }
            } else {
                System.out.println("Error creating event (check server logs).");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        System.out.println("initialize");

        // If subscribeButton is present, set it up
        if (subscribeButton != null) {
            subscribeButton.setDisable(true);
        }

        // If eventsListView is present, set up its listeners
        if (eventsListView != null) {
            eventsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                System.out.println("Selection changed. New selection: " + newSelection);
                if (subscribeButton != null) {
                    subscribeButton.setDisable(newSelection == null);
                }
            });

            eventsListView.setOnMouseClicked(event -> {
                EventItem selected = eventsListView.getSelectionModel().getSelectedItem();
                System.out.println("ListView clicked. Selected item: " + selected);
            });
        }
    }
    @FXML
    private void onSubscribeButtonClick() {
        // Get the currently selected event from the ListView
        EventItem selectedEvent = eventsListView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            subscribeToEvent(selectedEvent);
        } else {
            System.out.println("No event selected.");
        }
    }
    public void loadEventsFromDatabase() {
        try {
            // 1) Send a GET request to /events
            String jsonResponse = sendGetRequest("http://localhost:2001/events");

            if ("default".equals(jsonResponse)) {
                System.out.println("Error fetching events. Check server or logs.");
                return;
            }

            // 2) Parse the JSON array
            org.json.simple.JSONArray eventsArray =
                    (org.json.simple.JSONArray) parser.parse(jsonResponse);

            // 3) Build an ObservableList<EventItem>
            ObservableList<EventItem> eventList = FXCollections.observableArrayList();

            for (Object item : eventsArray) {
                org.json.simple.JSONObject eventObj = (org.json.simple.JSONObject) item;

                // Extract fields
                String id = (String) eventObj.get("_id");
                String title = (String) eventObj.get("title");
                String date = (String) eventObj.get("date");
                String location = (String) eventObj.get("location");
                String description = (String) eventObj.get("description");
                String price = (String) eventObj.get("price");
                String maxParticipants = (String) eventObj.get("maxParticipants");

                // Create the EventItem object
                EventItem e = new EventItem(
                        id, title, date, location, description, price, maxParticipants
                );
                eventList.add(e);
            }

            // 4) Set to ListView
            eventsListView.setItems(eventList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}