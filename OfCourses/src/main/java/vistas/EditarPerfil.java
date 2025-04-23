package vistas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;

import java.io.File;

public class EditarPerfil extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;
    private ImageView imagenPerfilView;
    private File archivoEscogido = null;
    private ImageView vistaPreviaFoto;

    // <--------------------------------------------------------------->
    // <------------------- FUNCIONES DE BOTONES ---------------------->
    // <--------------------------------------------------------------->
    
    private void volverACursos() {
        try {
            VistaPrincipal cursos = new VistaPrincipal();
            Stage stage = new Stage();
            cursos.start(stage);
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
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
        
        // Menú lateral
        VBox menuLateral = crearMenuLateral();
        
        // Barra superior con perfil de usuario
        HBox topBar = crearTopBar(menuLateral);
        
        // Panel central con lista de cursos
        Node centerCard = crearCenterCard();
        
        // Configurar el layout
        root.setLeft(menuLateral);
        root.setTop(topBar);
        root.setCenter(centerCard);
        
        // Configurar la escena
        Scene scene = new Scene(root, 1000, 700);
        scene.setFill(Color.TRANSPARENT);
        
        // Configurar el stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("OfCourses - Mis Cursos");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Centrar la ventana
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double xCenter = (screenBounds.getWidth() - primaryStage.getWidth()) / 2;
        double yCenter = (screenBounds.getHeight() - primaryStage.getHeight()) / 2;
        primaryStage.setX(xCenter);
        primaryStage.setY(yCenter);
    }
    
    private VBox crearMenuLateral() {
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: rgba(255,255,255,0.1);");
        menu.setPadding(new Insets(20, 15, 20, 15));
        menu.setMinWidth(200);
        menu.setMaxWidth(200);
        menu.setVisible(true); // Inicialmente oculto
        
        // Botón para volver a mis cursos
        Button btnVolver = new Button("Volver a Cursos");
        btnVolver.setGraphic(new ImageView(new Image("imagenes/return.png")));
        styleMenuButton(btnVolver);
        btnVolver.setOnAction(e -> volverACursos());
        
        // Espaciador para empujar los botones hacia arriba
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        menu.getChildren().addAll(btnVolver);
        
        return menu;
    }
    
    private HBox crearTopBar(VBox menuLateral) {
        
        // Foto de perfil
    	
    	// TODO: pedir al controlador la imagen del usuario actual
    	imagenPerfilView = new ImageView(new Image("imagenes/foto-perfil-default.png"));
        imagenPerfilView.setFitWidth(40);
        imagenPerfilView.setFitHeight(40);
        imagenPerfilView.setStyle("-fx-border-radius: 20; -fx-border-color: white; -fx-border-width: 2;");
        
        // Nombre de usuario
        
        // TODO: pedir al controlador el nombre del usuario actual
        String nombreUsuario = "Juan Pérez";
        Label lblNombreUsuario = new Label(nombreUsuario);
        lblNombreUsuario.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblNombreUsuario.setTextFill(Color.WHITE);
        
        // Espaciador
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Botón de cerrar ventana
        Button btnCerrar = new Button("✕");
        btnCerrar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18;");
        btnCerrar.setOnAction(e -> primaryStage.close());
        
        // Barra superior
        HBox topBar = new HBox(15, imagenPerfilView, lblNombreUsuario, spacer, btnCerrar);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15, 25, 15, 15));
        topBar.setStyle("-fx-background-color: rgba(0,0,0,0.1);");
        
        topBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        topBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        
        return topBar;
    }
    
    private Node crearCenterCard() {
        VBox panelPerfil = new VBox(20);
        panelPerfil.setAlignment(Pos.TOP_CENTER);
        panelPerfil.setPadding(new Insets(30, 50, 40, 50));
        panelPerfil.setMaxWidth(600);
        panelPerfil.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        panelPerfil.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));

        Label lblTitulo = new Label("Editar Perfil");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        lblTitulo.setTextFill(Color.web("#1a73e8"));

        // Nombre
        TextField campoNombre = new TextField("Juan Pérez");
        campoNombre.setPromptText("Nombre completo");

        // Contraseña
        PasswordField campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Nueva contraseña");

        // Correo
        TextField campoCorreo = new TextField("juan@example.com");
        campoCorreo.setPromptText("Correo electrónico");

        // Vista previa de la imagen
        vistaPreviaFoto = new ImageView(new Image("imagenes/foto-perfil-default.png"));
        vistaPreviaFoto.setFitWidth(100);
        vistaPreviaFoto.setFitHeight(100);
        vistaPreviaFoto.setStyle("-fx-border-radius: 50;");
        vistaPreviaFoto.setClip(new Circle(50, 50, 50)); // Clip circular si lo quieres redondo

        // Botón cambiar foto
        Button btnFoto = new Button("Cambiar foto de perfil");
        styleLoginButton(btnFoto);
        btnFoto.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar nueva foto de perfil");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
            archivoEscogido = fileChooser.showOpenDialog(primaryStage);

            if (archivoEscogido != null) {
                vistaPreviaFoto.setImage(new Image(archivoEscogido.toURI().toString()));
            }
        });

        // Botón guardar
        Button btnGuardar = new Button("Guardar cambios");
        styleLoginButton(btnGuardar);
        btnGuardar.setOnAction(e -> {
            String nombre = campoNombre.getText();
            String contrasena = campoContrasena.getText();
            String correo = campoCorreo.getText();
            File foto = archivoEscogido;
            // TODO: Validar campos y enviar al controlador
            mostrarAlerta("Perfil actualizado", "Los cambios se han guardado correctamente.", Alert.AlertType.INFORMATION);
        });

        panelPerfil.getChildren().addAll(
            lblTitulo,
            vistaPreviaFoto,
            btnFoto,
            campoNombre,
            campoContrasena,
            campoCorreo,
            btnGuardar
        );

        StackPane fondoConTarjeta = new StackPane();
        fondoConTarjeta.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a73e8, #0d47a1);");
        fondoConTarjeta.setPadding(new Insets(30));
        fondoConTarjeta.getChildren().add(panelPerfil);

        return fondoConTarjeta;
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        
        // Estilo de la alerta
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: white;");
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14; -fx-text-fill: #333333;");
        
        alert.showAndWait();
    }
    
    private void styleLoginButton(Button button) {
        button.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 8 15; -fx-background-radius: 5;");
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        
        button.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                button.setStyle("-fx-background-color: #0d47a1; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 8 15; -fx-background-radius: 5;");
            } else {
                button.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 8 15; -fx-background-radius: 5;");
            }
        });
    }
    
    private void styleMenuButton(Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 15; -fx-background-radius: 5;");
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        button.setAlignment(Pos.CENTER_LEFT);
        button.setMaxWidth(Double.MAX_VALUE);
        
        button.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                button.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 15; -fx-background-radius: 5;");
            } else {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 15; -fx-background-radius: 5;");
            }
        });
    }
}