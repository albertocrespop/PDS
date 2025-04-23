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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Screen;

import java.util.List;

public class VentanaEstadisticas extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;
    private ImageView imagenPerfilView;

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
        VBox panelEstadisticas = new VBox(25);
        panelEstadisticas.setAlignment(Pos.TOP_CENTER);
        panelEstadisticas.setPadding(new Insets(30, 50, 40, 50));
        panelEstadisticas.setMaxWidth(600);
        panelEstadisticas.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        panelEstadisticas.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));

        Label lblTitulo = new Label("Tus Estadísticas");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        lblTitulo.setTextFill(Color.web("#1a73e8"));
        
        // TODO: Pedir al controlador las estadísticas
        Label lblTiempoUso = new Label("Tiempo de uso: 3 horas y 42 minutos");
        Label lblMejorRacha = new Label("Mejor racha: 7 días consecutivos");
        Label lblPreguntasRespondidas = new Label("Preguntas respondidas: 182");

        for (Label lbl : List.of(lblTiempoUso, lblMejorRacha, lblPreguntasRespondidas)) {
            lbl.setFont(Font.font("Segoe UI", 16));
            lbl.setTextFill(Color.web("#333333"));
        }

        panelEstadisticas.getChildren().addAll(lblTitulo, lblTiempoUso, lblMejorRacha, lblPreguntasRespondidas);

        // Envolver con fondo para mantener consistencia
        StackPane fondoConTarjeta = new StackPane();
        fondoConTarjeta.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a73e8, #0d47a1);");
        fondoConTarjeta.setPadding(new Insets(30));
        fondoConTarjeta.getChildren().add(panelEstadisticas);

        return fondoConTarjeta;
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