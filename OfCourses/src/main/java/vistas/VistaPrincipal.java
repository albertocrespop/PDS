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
import java.util.ArrayList;
import java.util.List;

public class VistaPrincipal extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;
    private ImageView imagenPerfilView;
    private VBox cursosContainer;
    private List<String> cursos = new ArrayList<>();
    private boolean menuAbierto = false;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Datos de ejemplo
        for (int i = 1; i <= 10; i++) {
            cursos.add("Curso " + i + " - " + (i % 2 == 0 ? "Programación" : "Diseño"));
        }
        
        // Contenedor principal
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a73e8, #0d47a1);");
        
        // Menú lateral
        VBox menuLateral = crearMenuLateral();
        
        // Barra superior con perfil de usuario
        HBox topBar = crearTopBar(menuLateral);
        
        // Panel central con lista de cursos
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
        primaryStage.setTitle("OfCourses - Mis Cursos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox crearMenuLateral() {
        VBox menu = new VBox(15);
        menu.setStyle("-fx-background-color: rgba(255,255,255,0.1);");
        menu.setPadding(new Insets(20, 15, 20, 15));
        menu.setMinWidth(200);
        menu.setMaxWidth(200);
        menu.setVisible(true); // Inicialmente oculto
        
        // Botón para añadir curso
        Button btnAddCurso = new Button("Añadir Curso");
        btnAddCurso.setGraphic(new ImageView(new Image("https://via.placeholder.com/20/ffffff?text=+")));
        styleMenuButton(btnAddCurso);
        btnAddCurso.setOnAction(e -> agregarNuevoCurso());
        
        // Botón para editar perfil
        Button btnEditarPerfil = new Button("Editar Perfil");
        btnEditarPerfil.setGraphic(new ImageView(new Image("https://via.placeholder.com/20/ffffff?text=✎")));
        styleMenuButton(btnEditarPerfil);
        btnEditarPerfil.setOnAction(e -> editarPerfil());
        
        // Botón para estadísticas
        Button btnEstadistica = new Button("Estadísticas");
        btnEstadistica.setGraphic(new ImageView(new Image("https://via.placeholder.com/20/ffffff?text="))); // Esto no sé pa qué sirve
        styleMenuButton(btnEstadistica);
        btnEstadistica.setOnAction(e -> estadisticas());
        
        
        // Botón para cerrar sesión
        Button btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setGraphic(new ImageView(new Image("https://via.placeholder.com/20/ffffff?text=⇥")));
        styleMenuButton(btnCerrarSesion);
        btnCerrarSesion.setOnAction(e -> cerrarSesion());
        
        
        // Espaciador para empujar los botones hacia arriba
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        menu.getChildren().addAll(btnAddCurso, btnEditarPerfil, btnEstadistica, spacer, btnCerrarSesion);
        
        return menu;
    }
    
    private HBox crearTopBar(VBox menuLateral) {
        // Botón para mostrar/ocultar menú
        /*Button btnMenu = new Button("☰");
        btnMenu.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 20;");
        btnMenu.setOnAction(e -> {
            menuAbierto = !menuAbierto;
            menuLateral.setVisible(menuAbierto);
        });
        */
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
        
        // Título
        Label title = new Label("Mis Cursos");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#1a73e8"));
        
        // Lista de cursos
        cursosContainer = new VBox(15);
        cursosContainer.setAlignment(Pos.TOP_CENTER);
        actualizarListaCursos();
        
        // ScrollPane para la lista de cursos
        ScrollPane scrollPane = new ScrollPane(cursosContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPadding(new Insets(10));
        
        // Agregar elementos al card
        centerCard.getChildren().addAll(title, scrollPane);
        
        // ScrollPane principal
        ScrollPane mainScroll = new ScrollPane(centerCard);
        mainScroll.setFitToWidth(true);
        mainScroll.setFitToHeight(true);
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        return mainScroll;
    }
    
    private void actualizarListaCursos() {
        cursosContainer.getChildren().clear();
        
        if (cursos.isEmpty()) {
            Label lblSinCursos = new Label("No tienes cursos aún. ¡Añade tu primer curso!");
            lblSinCursos.setFont(Font.font("Segoe UI", 14));
            lblSinCursos.setTextFill(Color.GRAY);
            cursosContainer.getChildren().add(lblSinCursos);
        } else {
            for (String curso : cursos) {
                HBox cursoBox = crearCursoBox(curso);
                cursosContainer.getChildren().add(cursoBox);
            }
        }
    }
    
    private HBox crearCursoBox(String nombreCurso) {
        // Nombre del curso
        Label lblCurso = new Label(nombreCurso);
        lblCurso.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblCurso.setTextFill(Color.web("#333333"));
        
        // Botón para editar
        Button btnEditar = new Button("Editar");
        btnEditar.setGraphic(new ImageView(new Image("https://via.placeholder.com/15/1a73e8?text=✎")));
        styleSecondaryButton(btnEditar);
        btnEditar.setOnAction(e -> editarCurso(nombreCurso));
        
        // Botón para continuar
        Button btnContinuar = new Button("Continuar");
        styleLoginButton(btnContinuar);
        btnContinuar.setOnAction(e -> abrirCurso(nombreCurso));
        
        // Contenedor de botones
        HBox botonesBox = new HBox(10, btnEditar, btnContinuar);
        botonesBox.setAlignment(Pos.CENTER_RIGHT);
        
        // Espaciador
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Contenedor del curso
        HBox cursoBox = new HBox(20, lblCurso, spacer, botonesBox);
        cursoBox.setAlignment(Pos.CENTER_LEFT);
        cursoBox.setPadding(new Insets(15));
        cursoBox.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 10;");
        
        return cursoBox;
    }
    
    private void agregarNuevoCurso() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo del curso");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos de curso", "*.pdf", "*.zip", "*.ocourse")
        );
        File file = fileChooser.showOpenDialog(primaryStage);
        
        if (file != null) {
            String nombreCurso = file.getName().replaceFirst("[.][^.]+$", "");
            cursos.add(nombreCurso);
            actualizarListaCursos();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Curso añadido");
            alert.setHeaderText(null);
            alert.setContentText("El curso '" + nombreCurso + "' ha sido añadido correctamente.");
            alert.showAndWait();
        }
    }
    
    private void editarCurso(String nombreCurso) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Editar curso");
        alert.setHeaderText(null);
        alert.setContentText("Editando el curso: " + nombreCurso);
        alert.showAndWait();
    }
    
    private void editarPerfil() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Editar perfil");
        alert.setHeaderText(null);
        alert.setContentText("Aquí se abriría el formulario para editar el perfil del usuario");
        alert.showAndWait();
    }
    
    private void estadisticas() {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Estadísticas");
        alert.setHeaderText(null);
        alert.setContentText("Aquí se mostrarán las estadísticas generales del usuario");
        alert.showAndWait();
    }
    
    private void abrirCurso(String nombreCurso) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Continuar curso");
        alert.setHeaderText(null);
        alert.setContentText("Abriendo el curso: " + nombreCurso);
        alert.showAndWait();
    }
    
    private void cerrarSesion() {
        try {
            Login login = new Login();
            Stage stage = new Stage();
            login.start(stage);
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
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
    
    private void styleSecondaryButton(Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #1a73e8; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 8 15; -fx-background-radius: 5; -fx-border-color: #1a73e8; -fx-border-width: 1; -fx-border-radius: 5;");
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        
        button.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                button.setStyle("-fx-background-color: #e8f0fe; -fx-text-fill: #1a73e8; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 8 15; -fx-background-radius: 5; -fx-border-color: #1a73e8; -fx-border-width: 1; -fx-border-radius: 5;");
            } else {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: #1a73e8; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 8 15; -fx-background-radius: 5; -fx-border-color: #1a73e8; -fx-border-width: 1; -fx-border-radius: 5;");
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