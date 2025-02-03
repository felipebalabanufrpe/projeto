package com.example.demo9;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class InscricoesController {

    @FXML
    private ListView<EventItem> subscriptionsListView;

    @FXML
    private Button homeButton2;  // Used to get the current stage for navigation

    @FXML
    private VBox certificadosVBox;  // If needed for navigation reference

    private static final JSONParser parser = new JSONParser();

    @FXML
    public void initialize() {
        loadSubscriptions();
    }

    public void loadSubscriptions() {
        String userName = Session.getLoggedInUser();
        if (userName == null || userName.isEmpty()) {
            System.out.println("No logged-in user available.");
            return;
        }

        String uri = "http://localhost:2001/userSubscriptions?userName=" + userName;
        String jsonResponse = ApiService.sendGetRequest(uri);
        if ("default".equals(jsonResponse)) {
            System.out.println("Error fetching subscriptions. Check server logs.");
            return;
        }

        try {
            JSONArray subsArray = (JSONArray) parser.parse(jsonResponse);
            ObservableList<EventItem> subscriptionsList = FXCollections.observableArrayList();

            for (Object item : subsArray) {
                // Parse each event object (adjust field names as necessary)
                org.json.simple.JSONObject eventObj = (org.json.simple.JSONObject) item;
                String id = (String) eventObj.get("_id");
                String title = (String) eventObj.get("title");
                String date = (String) eventObj.get("date");
                String location = (String) eventObj.get("location");
                String description = (String) eventObj.get("description");
                String price = (String) eventObj.get("price");
                String maxParticipants = (String) eventObj.get("maxParticipants");

                EventItem event = new EventItem(id, title, date, location, description, price, maxParticipants);
                subscriptionsList.add(event);
            }

            subscriptionsListView.setItems(subscriptionsList);
            System.out.println("Loaded " + subscriptionsList.size() + " subscriptions.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Navigation method for Home button
    @FXML
    private void onHomeClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();

            // Retrieve the home controller and refresh its events list
            HelloController homeController = loader.getController();
            homeController.loadEventsFromDatabase(); // This repopulates the events list

            // Get the current stage from one of the UI elements (e.g., a button)
            Stage stage = (Stage) homeButton2.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Navigation method for Certificados button
    @FXML
    private void onCertificadosClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("certificados.fxml"));
            Parent root = loader.load();
            // Using the certificadosVBox to get the stage (or any other node)
            Stage stage = (Stage) certificadosVBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Navigation method for Conta button
    @FXML
    private void onContaClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("conta.fxml"));
            Parent root = loader.load();
            // You can also get the stage from any event source:
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}