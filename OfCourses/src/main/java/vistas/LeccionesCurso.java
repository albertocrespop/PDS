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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LeccionesCurso extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;
    private ImageView imagenPerfilView;
    private String nombreCurso = "Curso de Ejemplo";

    public LeccionesCurso() {
        // Puede permanecer vacío
    }
    
    public LeccionesCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

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
        
        // Panel central con lista de lecciones
        ScrollPane centerCard = crearCenterCard();
        
        // Configurar el layout
        root.setLeft(menuLateral);
        root.setTop(topBar);
        root.setCenter(centerCard);
        
        // Configurar la escena
        Scene scene = new Scene(root, 1000, 700);
        scene.setFill(Color.TRANSPARENT);
        
        // Configurar el stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("OfCourses - " + nombreCurso);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox crearMenuLateral() {
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: rgba(255,255,255,0.1);");
        menu.setPadding(new Insets(20, 15, 20, 15));
        menu.setMinWidth(200);
        menu.setMaxWidth(200);
        
        // Botón para volver a mis cursos
        Button btnVolver = new Button("Volver a Cursos");
        btnVolver.setGraphic(new ImageView(new Image("https://via.placeholder.com/20/ffffff?text=←")));
        styleMenuButton(btnVolver);
        btnVolver.setOnAction(e -> volverACursos());
        
        // Botón para añadir lección (solo visible para instructores)
        Button btnAddLeccion = new Button("Añadir Lección");
        btnAddLeccion.setGraphic(new ImageView(new Image("https://via.placeholder.com/20/ffffff?text=+")));
        styleMenuButton(btnAddLeccion);
        btnAddLeccion.setOnAction(e -> agregarNuevaLeccion());
        
        // Botón para estadísticas del curso
        Button btnEstadisticas = new Button("Estadísticas");
        btnEstadisticas.setGraphic(new ImageView(new Image("https://via.placeholder.com/20/ffffff?text=📊")));
        styleMenuButton(btnEstadisticas);
        btnEstadisticas.setOnAction(e -> mostrarEstadisticasCurso());
        
        // Espaciador para empujar los botones hacia arriba
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        menu.getChildren().addAll(btnVolver, btnAddLeccion, btnEstadisticas, spacer);
        
        return menu;
    }
    
    private HBox crearTopBar(VBox menuLateral) {
        // Foto de perfil
        imagenPerfilView = new ImageView(new Image(getClass().getResourceAsStream("/images/logo.png")));
        imagenPerfilView.setFitWidth(40);
        imagenPerfilView.setFitHeight(40);
        imagenPerfilView.setStyle("-fx-border-radius: 20; -fx-border-color: white; -fx-border-width: 2;");
        
        // Nombre de usuario
        Label lblNombreUsuario = new Label("Juan Pérez");
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
        
        return topBar;
    }
    
    private ScrollPane crearCenterCard() {
        // Panel central
        VBox centerCard = new VBox(20);
        centerCard.setAlignment(Pos.TOP_CENTER);
        centerCard.setPadding(new Insets(30, 50, 40, 50));
        centerCard.setMaxWidth(800);
        centerCard.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        centerCard.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));
        
        // Hacer la ventana arrastrable desde el card
        centerCard.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        centerCard.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        
        // Título del curso
        Label title = new Label(nombreCurso);
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#1a73e8"));
        
        // Descripción del curso
        Label descripcion = new Label("Este curso cubre los fundamentos y conceptos avanzados sobre el tema seleccionado.");
        descripcion.setFont(Font.font("Segoe UI", 14));
        descripcion.setTextFill(Color.web("#666666"));
        descripcion.setWrapText(true);
        descripcion.setMaxWidth(700);
        
        // Separador
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 20, 0));
        
        // Progreso general del curso
        HBox progresoBox = new HBox(10);
        progresoBox.setAlignment(Pos.CENTER_LEFT);
        
        Label lblProgreso = new Label("Progreso del curso: ");
        lblProgreso.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lblProgreso.setTextFill(Color.web("#333333"));
        
        ProgressBar progressBar = new ProgressBar(0.65); // Valor de ejemplo (65%)
        progressBar.setPrefWidth(200);
        progressBar.setStyle("-fx-accent: #1a73e8;");
        
        Label lblPorcentaje = new Label("65%");
        lblPorcentaje.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lblPorcentaje.setTextFill(Color.web("#1a73e8"));
        
        progresoBox.getChildren().addAll(lblProgreso, progressBar, lblPorcentaje);
        
        // Título de lecciones
        Label lblLecciones = new Label("Lecciones del Curso");
        lblLecciones.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblLecciones.setTextFill(Color.web("#333333"));
        lblLecciones.setPadding(new Insets(10, 0, 10, 0));
        
        // GridPane para las lecciones
        GridPane gridLecciones = new GridPane();
        gridLecciones.setHgap(20);
        gridLecciones.setVgap(20);
        gridLecciones.setAlignment(Pos.TOP_CENTER);
        gridLecciones.setPadding(new Insets(10));
        
        // Generar lecciones de ejemplo
        List<String> lecciones = generarLeccionesEjemplo();
        int columnas = 3;
        
        for (int i = 0; i < lecciones.size(); i++) {
            int row = i / columnas;
            int col = i % columnas;
            
            VBox leccionCard = crearLeccionCard(lecciones.get(i), i+1, new Random().nextBoolean());
            gridLecciones.add(leccionCard, col, row);
        }
        
        // Agregar elementos al card
        centerCard.getChildren().addAll(title, descripcion, separator, progresoBox, lblLecciones, gridLecciones);
        
        // ScrollPane principal
        ScrollPane mainScroll = new ScrollPane(centerCard);
        mainScroll.setFitToWidth(true);
        mainScroll.setFitToHeight(true);
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        return mainScroll;
    }
    
    private List<String> generarLeccionesEjemplo() {
        List<String> lecciones = new ArrayList<>();
        lecciones.add("Introducción al curso");
        lecciones.add("Conceptos básicos");
        lecciones.add("Primeros pasos prácticos");
        lecciones.add("Funciones avanzadas");
        lecciones.add("Casos de estudio");
        lecciones.add("Optimización");
        lecciones.add("Proyecto final");
        lecciones.add("Recursos adicionales");
        return lecciones;
    }
    
    private VBox crearLeccionCard(String nombreLeccion, int numeroLeccion, boolean completada) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(15));
        card.setPrefSize(200, 180);
        card.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 10;");
        card.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.1)));
        
        // Número de lección
        Label lblNumero = new Label("Lección " + numeroLeccion);
        lblNumero.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lblNumero.setTextFill(Color.web("#666666"));
        
        // Nombre de lección
        Label lblNombre = new Label(nombreLeccion);
        lblNombre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblNombre.setTextFill(Color.web("#333333"));
        lblNombre.setWrapText(true);
        lblNombre.setAlignment(Pos.CENTER);
        lblNombre.setMaxWidth(180);
        
        // Icono de estado
        ImageView iconoEstado = new ImageView();
        if (completada) {
            iconoEstado.setImage(new Image("https://via.placeholder.com/30/4CAF50?text=✓"));
        } else {
            iconoEstado.setImage(new Image("https://via.placeholder.com/30/FF9800?text=..."));
        }
        
        // Botón para realizar lección
        Button btnRealizar = new Button(completada ? "Repasar" : "Comenzar");
        styleLoginButton(btnRealizar);
        btnRealizar.setOnAction(e -> abrirLeccion(numeroLeccion, nombreLeccion));
        
        // Espaciador
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        card.getChildren().addAll(lblNumero, lblNombre, iconoEstado, spacer, btnRealizar);
        
        return card;
    }
    
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
    
    private void agregarNuevaLeccion() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Añadir lección");
        alert.setHeaderText(null);
        alert.setContentText("Aquí se abriría el formulario para añadir una nueva lección al curso.");
        alert.showAndWait();
    }
    
    private void mostrarEstadisticasCurso() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Estadísticas del curso");
        alert.setHeaderText(null);
        alert.setContentText("Aquí se mostrarían las estadísticas específicas de este curso.");
        alert.showAndWait();
    }
    
    private void abrirLeccion(int numeroLeccion, String nombreLeccion) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        switch(numeroLeccion) {
        	case 1:
        		OrdenarPalabras ej1 = new OrdenarPalabras();
                Stage stage = new Stage();
                ej1.start(stage);
                primaryStage.close();
                break;
        	case 2:
        		EjercicioEscrito ej2 = new EjercicioEscrito();
                Stage stage2 = new Stage();
                ej2.start(stage2);
                primaryStage.close();
                break;
            default:
            	alert.setTitle("Lección " + numeroLeccion);
                alert.setHeaderText(nombreLeccion);
                alert.setContentText("Aquí se abriría la lección seleccionada con su contenido.");
                alert.showAndWait();
                break;
        }
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