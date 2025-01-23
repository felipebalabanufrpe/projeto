package com.example.demo9;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final JSONParser parser = new JSONParser();

    @FXML
    private TextField nomeTextField;

    @FXML
    private PasswordField senhaTextField;

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
            System.out.println("Parsed Message: " + name);
            if (name != null) {
            //login bem sucedido
                System.out.println("reached here");
                changeScene("home.fxml");
            }
        } catch (ParseException e) {
            System.out.println("Error parsing response: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    protected void onCertificadosClick() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("certificados.fxml"));

            // Load the root element (the top-level container) of the new FXML file


            // Get the current stage (window) using the TextField's scene


            // Set a new scene on the current stage, using the loaded root as its layout


            // Show the updated stage with the new scene

        } catch (Exception e) {
            // If any exception occurs (e.g., the FXML file is not found or has errors),
            // print an error message and the stack trace to debug the issue
            System.out.println("Error loading new scene: " + e.getMessage());
            e.printStackTrace();
        }



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

    private void changeScene(String fxmlFile) {
        try {
            // Create an FXMLLoader instance to load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

            // Load the root element (the top-level container) of the new FXML file
            Parent root = loader.load();

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

}