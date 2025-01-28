package com.miromori.project_sympla_entrega_2.view;


import com.miromori.project_sympla_entrega_2.services.LoginFormService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginForm extends Application {
    private AnchorPane pane;
    private TextField emailField;
    private PasswordField passwordField;
    private Button signInButton, signUpButton;
    private static Stage stage;
    private LoginFormService loginFormService;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        initListeners();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();
        initLayout();
    }

    public void initComponents(){
        loginFormService = new LoginFormService();
        pane = new AnchorPane();
        pane.setPrefSize(400, 300);
        emailField = new TextField();
        emailField.setPromptText("email: ");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password: ");
        signInButton = new Button("Sign in");
        signUpButton = new Button("Sign up");
        pane.getChildren().addAll(emailField, passwordField, signInButton, signUpButton);
    }

    public void initLayout(){
        emailField.setLayoutX((pane.getWidth() - emailField.getWidth()) / 2);
        emailField.setLayoutY(50);
        passwordField.setLayoutX((pane.getWidth() - passwordField.getWidth()) / 2);
        passwordField.setLayoutY(100);
        signInButton.setLayoutX((pane.getWidth() - signInButton.getWidth()) / 2 - 35);
        signInButton.setLayoutY(150);
        signUpButton.setLayoutX((pane.getWidth() - signUpButton.getWidth()) / 2 + 35);
        signUpButton.setLayoutY(150);
    }

    public void initListeners(){

    }
}
