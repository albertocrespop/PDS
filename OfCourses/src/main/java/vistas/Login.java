package vistas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        // Contenedor principal - Cambiado a BorderPane
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a73e8, #0d47a1);");
        
        // Panel de login
        VBox loginCard = new VBox(20);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setPadding(new Insets(40, 50, 50, 50));
        loginCard.setMaxWidth(400);
        loginCard.setStyle("-fx-background-color: white; -fx-background-radius: 12;-fx-padding: 30;");
        loginCard.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));

        
        // Título
        Label title = new Label("OfCourses");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#1a73e8"));
        
        // Subtítulo
        Label subtitle = new Label("Inicia sesión en tu cuenta");
        subtitle.setFont(Font.font("Segoe UI", 14));
        subtitle.setTextFill(Color.GRAY);
        
        // Campos de formulario
        TextField usernameField = new TextField();
        usernameField.setPromptText("Nombre de usuario");
        styleTextField(usernameField);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        styleTextField(passwordField);
        
        // Botón de login
        Button loginButton = new Button("Iniciar Sesión");
        styleLoginButton(loginButton);
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if(username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Por favor ingresa usuario y contraseña");
            } else {
                showAlert("Éxito", "Bienvenido a OfCourses, " + username + "!");
            }
        });
        
        
        // Separador
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        
        // Enlace de registro
        Hyperlink registerLink = new Hyperlink("Crear una cuenta nueva");
        registerLink.setTextFill(Color.web("#1a73e8"));
        registerLink.setBorder(Border.EMPTY);
        registerLink.setPadding(new Insets(5));
        registerLink.setOnAction(e -> {
            showAlert("Registro", "Redirigiendo al formulario de registro...");
        });
        
        // Agregar elementos al card
        loginCard.getChildren().addAll(
            title, subtitle,
            usernameField, passwordField,
            loginButton, separator, registerLink
        );
        
        // Barra de título personalizada
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER_RIGHT);
        titleBar.setPadding(new Insets(10));
        
 
        Button closeButton = new Button("✕");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
        closeButton.setOnAction(e -> primaryStage.close());
  
  
        titleBar.getChildren().add(closeButton);
  
        //Separador para que loginCard no se pegue a root
        HBox spacer = new HBox();
        spacer.setPrefHeight(50); // Ajusta la altura del espaciador
        root.setBottom(spacer);
        
        // Configurar el layout
        root.setCenter(loginCard);
        root.setTop(titleBar);
        
        // Hacer la ventana arrastrable desde el card
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        
        // Configurar la escena
        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.TRANSPARENT);
        
        // Configurar el stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("OfCourses - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void styleTextField(TextField field) {
        field.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 12;");
        field.setFont(Font.font("Segoe UI", 14));
        
        field.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                field.setStyle("-fx-background-color: #e8f0fe; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 12;");
            } else {
                field.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 12;");
            }
        });
    }
    
    private void styleLoginButton(Button button) {
        button.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        
        button.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                button.setStyle("-fx-background-color: #0d47a1; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
            } else {
                button.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
            }
        });
        
        button.setOnMousePressed(e -> {
            button.setStyle("-fx-background-color: #0b3d91; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
        });
        
        button.setOnMouseReleased(e -> {
            button.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
        });
    }
}
