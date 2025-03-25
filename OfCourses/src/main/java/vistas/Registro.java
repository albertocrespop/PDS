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
import javafx.stage.FileChooser;
import java.io.File;

public class Registro extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private ImageView imagenPerfilView;
    private Stage primaryStage;

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

        // Logo
        ImageView logo = new ImageView(new Image("https://via.placeholder.com/80/1a73e8/ffffff?text=OC"));
        logo.setFitWidth(80);
        logo.setFitHeight(80);
        
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
            logo, title,
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
        
        // Configurar el layout
        root.setCenter(registroCard);
        root.setTop(titleBar);
        
        // Hacer la ventana arrastrable desde el card
        registroCard.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        registroCard.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        
        // Configurar la escena
        Scene scene = new Scene(root, 850, 700);
        scene.setFill(Color.TRANSPARENT);
        
        // Configurar el stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("OfCourses - Registro");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de perfil");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
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
            showAlert("Error", "Por favor completa todos los campos");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Las contraseñas no coinciden");
            return;
        }
        
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Error", "Por favor ingresa un email válido");
            return;
        }
        
        showAlert("Registro exitoso", "Usuario registrado correctamente\n" +
                "Nombre: " + username + "\n" +
                "Email: " + email);
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
                field.setStyle("-fx-background-color: #e8f0fe; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 12; -fx-border-color: #1a73e8; -fx-border-width: 1;");
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

    public static void main(String[] args) {
        launch(args);
    }
}