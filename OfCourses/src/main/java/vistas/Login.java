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
        // Crear el contenedor principal con efecto de gradiente
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a73e8, #0d47a1);");
        
        // Crear el panel de login (tarjeta blanca)
        VBox loginCard = new VBox(20);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setPadding(new Insets(40, 50, 50, 50));
        loginCard.setMaxWidth(400);
        loginCard.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        loginCard.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));

        // Logo de la aplicación
        ImageView logo = new ImageView(new Image("https://via.placeholder.com/80/1a73e8/ffffff?text=OC"));
        logo.setFitWidth(80);
        logo.setFitHeight(80);
        
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
        usernameField.setPromptText("Usuario o correo electrónico");
        styleTextField(usernameField);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        styleTextField(passwordField);
        
        // Checkbox de recordar usuario
        CheckBox rememberMe = new CheckBox("Recordar mi usuario");
        rememberMe.setTextFill(Color.GRAY);
        
        // Botón de login
        Button loginButton = new Button("Iniciar Sesión");
        styleLoginButton(loginButton);
        
        // Enlace de olvidó contraseña
        Hyperlink forgotPassword = new Hyperlink("¿Olvidaste tu contraseña?");
        forgotPassword.setTextFill(Color.GRAY);
        forgotPassword.setBorder(Border.EMPTY);
        forgotPassword.setPadding(new Insets(5));
        
        // Separador
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        
        // Enlace de registro
        Hyperlink registerLink = new Hyperlink("Crear una cuenta nueva");
        registerLink.setTextFill(Color.web("#1a73e8"));
        registerLink.setBorder(Border.EMPTY);
        registerLink.setPadding(new Insets(5));
        
        // Agregar elementos al card
        loginCard.getChildren().addAll(
            logo, title, subtitle,
            usernameField, passwordField,
            rememberMe, loginButton,
            forgotPassword, separator, registerLink
        );
        
        // Barra de título personalizada
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER_RIGHT);
        titleBar.setPadding(new Insets(10));
        
        Button closeButton = new Button("✕");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
        closeButton.setOnAction(e -> primaryStage.close());
        
        titleBar.getChildren().add(closeButton);
        
        // Configurar el layout
        root.getChildren().addAll(loginCard, titleBar);
        StackPane.setAlignment(titleBar, Pos.TOP_RIGHT);
        
        // Hacer la ventana arrastrable
        loginCard.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        loginCard.setOnMouseDragged(event -> {
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
    
    private void styleTextField(TextField field) {
        field.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 12;");
        field.setFont(Font.font("Segoe UI", 14));
        
        // Efecto de hover
        field.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                field.setStyle("-fx-background-color: #e8f0fe; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 12; -fx-border-color: #1a73e8; -fx-border-width: 1;");
            } else {
                field.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 12;");
            }
        });
    }
    
    private void styleLoginButton(Button button) {
        button.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        
        // Efecto de hover
        button.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                button.setStyle("-fx-background-color: #0d47a1; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
            } else {
                button.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
            }
        });
        
        // Efecto de pulsación
        button.setOnMousePressed(e -> {
            button.setStyle("-fx-background-color: #0b3d91; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
        });
        
        button.setOnMouseReleased(e -> {
            button.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
        });
    }
}