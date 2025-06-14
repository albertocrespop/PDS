package vistas;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.util.Duration;
import javafx.stage.FileChooser;
import javafx.stage.Screen;

import java.io.File;
import java.net.URL;

import controlador.OfCourses;

public class Registro extends Application {

	private OfCourses controlador = OfCourses.getUnicaInstancia();
	
    private double xOffset = 0;
    private double yOffset = 0;
    private ImageView imagenPerfilView;
    private Stage primaryStage;
    
    private String url = controlador.FOTO_DEFECTO.toString();

    // <--------------------------------------------------------------->
    // <------------------- FUNCIONES DE BOTONES ---------------------->
    // <--------------------------------------------------------------->
    
    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de perfil");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
        	url = file.toURI().toString();
            Image image = new Image(url);
            imagenPerfilView.setImage(image);
        }else {
        	Image image = new Image(url.toString());
            imagenPerfilView.setImage(image);
        }
    }
    
    private void volverALogin() {
        try {
            Login login = new Login();
            Stage stage = new Stage();
            login.start(stage);
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void registrarUsuario(String username, String password, String confirmPassword, String email) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            showAlert("Error", "Por favor completa todos los campos.");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Las contraseñas no coinciden.");
            return;
        }
        
        if(password.length() < 6) {
        	showAlert("Error", "La contraseña debe tener al menos 6 caracteres.");
        }
        
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Error", "Por favor ingresa un email válido.");
            return;
        }
        
        if(!controlador.registerUser(username, password, email, url)) {
            showAlert("Error", "Usuario existente.");
            return;        	
        }else {
        	try {
    			VistaPrincipal cursos = new VistaPrincipal();
    			Stage stage = new Stage();
    			cursos.start(stage);
    			primaryStage.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }
    }
    
    // <--------------------------------------------------------------->
 	// <--------------------------------------------------------------->
 	// <--------------------------------------------------------------->
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Contenedor principal
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a73e8, #0d47a1);");
        // Panel de registro
        VBox registroCard = new VBox(15);
        registroCard.setAlignment(Pos.CENTER);
        registroCard.setPadding(new Insets(30, 50, 40, 50));
        registroCard.setMaxWidth(450);
        registroCard.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        registroCard.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));
        
        setupAnimations(registroCard);
        
        // Título
        Label title = new Label("Crear Cuenta");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#1a73e8"));
        
        // Campos de formulario
        TextField usernameField = new TextField();
        usernameField.setPromptText("Nombre de usuario");
        styleTextField(usernameField);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        styleTextField(passwordField);
        
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Repetir contraseña");
        styleTextField(confirmPasswordField);
        
        TextField emailField = new TextField();
        emailField.setPromptText("Correo electrónico");
        styleTextField(emailField);
        
        // Imagen de perfil
        imagenPerfilView = new ImageView(new Image("https://via.placeholder.com/100/cccccc/969696?text=Imagen"));
        imagenPerfilView.setFitWidth(100);
        imagenPerfilView.setFitHeight(100);
        imagenPerfilView.setStyle("-fx-border-radius: 50; -fx-border-color: #1a73e8; -fx-border-width: 2;");
        
        Button btnSeleccionarImagen = new Button("Seleccionar imagen");
        styleSecondaryButton(btnSeleccionarImagen);
        btnSeleccionarImagen.setOnAction(e -> seleccionarImagen());
        
        VBox imagenContainer = new VBox(10, imagenPerfilView, btnSeleccionarImagen);
        imagenContainer.setAlignment(Pos.CENTER);
        
        // Botones
        HBox botonesBox = new HBox(20);
        botonesBox.setAlignment(Pos.CENTER);
        
        Button btnVolver = new Button("Volver a Login");
        styleSecondaryButton(btnVolver);
        btnVolver.setOnAction(e -> volverALogin());
        
        Button btnRegistrar = new Button("Registrarse");
        styleLoginButton(btnRegistrar);
        btnRegistrar.setOnAction(e -> registrarUsuario(
            usernameField.getText(),
            passwordField.getText(),
            confirmPasswordField.getText(),
            emailField.getText()
        ));
        
        botonesBox.getChildren().addAll(btnVolver, btnRegistrar);
        
        // Agregar elementos al card
        registroCard.getChildren().addAll(
            title,
            usernameField, passwordField, confirmPasswordField, emailField,
            imagenContainer,
            botonesBox
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
        root.setBottom(spacer);
        
        // Configurar el layout
        root.setCenter(registroCard);
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
        Scene scene = new Scene(root, 850, 750);
        root.requestFocus();
        scene.setFill(Color.TRANSPARENT);
        
        // Configurar el stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("OfCourses - Registro");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Centrar la ventana
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double xCenter = (screenBounds.getWidth() - primaryStage.getWidth()) / 2;
        double yCenter = (screenBounds.getHeight() - primaryStage.getHeight()) / 2;
        primaryStage.setX(xCenter);
        primaryStage.setY(yCenter);
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
    
    private void styleSecondaryButton(Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #1a73e8; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 20; -fx-background-radius: 5; -fx-border-color: #1a73e8; -fx-border-width: 1; -fx-border-radius: 5;");
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        
        button.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                button.setStyle("-fx-background-color: #e8f0fe; -fx-text-fill: #1a73e8; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 20; -fx-background-radius: 5; -fx-border-color: #1a73e8; -fx-border-width: 1; -fx-border-radius: 5;");
            } else {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: #1a73e8; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 20; -fx-background-radius: 5; -fx-border-color: #1a73e8; -fx-border-width: 1; -fx-border-radius: 5;");
            }
        });
        
        button.setOnMousePressed(e -> {
            button.setStyle("-fx-background-color: #d2e3fc; -fx-text-fill: #1a73e8; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 20; -fx-background-radius: 5; -fx-border-color: #1a73e8; -fx-border-width: 1; -fx-border-radius: 5;");
        });
        
        button.setOnMouseReleased(e -> {
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: #1a73e8; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 20; -fx-background-radius: 5; -fx-border-color: #1a73e8; -fx-border-width: 1; -fx-border-radius: 5;");
        });
    }
    
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
}