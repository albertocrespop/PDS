package vistas;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Login extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a73e8, #0d47a1);");
        
        VBox loginCard = new VBox(20);
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setPadding(new Insets(40, 50, 50, 50));
        loginCard.setMaxWidth(400);
        loginCard.setStyle("-fx-background-color: white; -fx-background-radius: 12;-fx-padding: 30;");
        loginCard.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));

        setupAnimations(loginCard);
        
        // Título
        Label title = new Label("OfCourses");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#1a73e8"));

        Label subtitle = new Label("Inicia sesión en tu cuenta");
        subtitle.setFont(Font.font("Segoe UI", 14));
        subtitle.setTextFill(Color.GRAY);

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

        Hyperlink registerLink = createRegisterLink();

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
        
        HBox spacer = new HBox();
        spacer.setPrefHeight(50); // Ajusta la altura del espaciador
                
        root.setCenter(loginCard);
        root.setTop(titleBar);
        root.setBottom(spacer);


        //Separador para que loginCard no se pegue a root
        // Hacer la ventana arrastrable desde el card
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });        
        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("OfCourses - Login");
        primaryStage.setScene(scene);
        // Quitar el foco inicial del campo user
        root.setFocusTraversable(true);
        root.requestFocus();
        primaryStage.show();
        
        // Centra la ventana
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double xCenter = (screenBounds.getWidth() - primaryStage.getWidth()) / 2;
        double yCenter = (screenBounds.getHeight() - primaryStage.getHeight()) / 2;
        primaryStage.setX(xCenter);
        primaryStage.setY(yCenter);    }

    private void setupAnimations(VBox loginCard) {
        // Animación de entrada
        loginCard.setOpacity(0);
        loginCard.setTranslateY(30);
        
        Timeline timeline = new Timeline();
        KeyValue kvOpacity = new KeyValue(loginCard.opacityProperty(), 1);
        KeyValue kvTranslate = new KeyValue(loginCard.translateYProperty(), 0);
        KeyFrame kf = new KeyFrame(Duration.millis(1500), kvOpacity, kvTranslate);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    private void handleLogin(TextField usernameField, PasswordField passwordField) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if(username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Por favor ingresa usuario y contraseña");
        } else {
            showAlert("Éxito", "Bienvenido a OfCourses, " + username + "!");
        }
    }

    private Hyperlink createRegisterLink() {
        Hyperlink registerLink = new Hyperlink("Crear una cuenta nueva");
        registerLink.setTextFill(Color.web("#1a73e8"));
        registerLink.setBorder(Border.EMPTY);
        registerLink.setPadding(new Insets(5));
        registerLink.setOnAction(e -> {
            showAlert("Registro", "Redirigiendo al formulario de registro...");
        });
        return registerLink;
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
            field.setStyle(newVal ? 
                "-fx-background-color: #e8f0fe; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 12;" :
                "-fx-background-color: #f5f5f5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 12;");
        });
    }

    private void styleLoginButton(Button button) {
        button.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        
        button.hoverProperty().addListener((obs, oldVal, newVal) -> {
            button.setStyle(newVal ? 
                "-fx-background-color: #0d47a1; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;" :
                "-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
        });
        
        button.setOnMousePressed(e -> {
            button.setStyle("-fx-background-color: #0b3d91; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
        });
        
        button.setOnMouseReleased(e -> {
            button.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 12 30; -fx-background-radius: 5;");
        });
    }
}