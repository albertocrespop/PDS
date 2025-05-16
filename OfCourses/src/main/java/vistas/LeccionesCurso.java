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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Curso;
import modelo.Leccion;
import modelo.Pregunta;
import modelo.PreguntaFlashCard;
import modelo.PreguntaOrdenarPalabras;
import modelo.PreguntaRellenarPalabras;
import modelo.PreguntaVF;

import java.util.ArrayList;
import java.util.List;

import controlador.OfCourses;

public class LeccionesCurso extends Application {

	private Curso cursoActual;
	
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;
    private ImageView imagenPerfilView;
    private String nombreCurso;


    public LeccionesCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
        cursoActual = OfCourses.getUnicaInstancia().getCurso(nombreCurso);
		for(Leccion l: cursoActual.getLecciones()) {
        	for(Pregunta p: l.getPreguntas()) {
        		System.out.println(p.getEnunciado());
        	}
		}
    }

    // <--------------------------------------------------------------->
    // <------------------- FUNCIONES DE BOTONES ---------------------->
    // <--------------------------------------------------------------->
    
    private void abrirLeccion(Leccion actual) {
    	
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	
    	if(!OfCourses.getUnicaInstancia().tieneVidas()) {
    		alert.setTitle("No tienes vidas");
            alert.setHeaderText(actual.getTitulo());
            alert.setContentText("Te has quedado sin vidas. Espera hasta mañana para que se recarguen.");
            alert.showAndWait();
    		return;
    	}
    	
        
        Pregunta pregunta = OfCourses.getUnicaInstancia().getPreguntaActual(actual);
         
        if(pregunta instanceof PreguntaOrdenarPalabras) {
    		OrdenarPalabras ej1 = new OrdenarPalabras(cursoActual.getTitulo(),(PreguntaOrdenarPalabras) pregunta, actual);
            Stage stage = new Stage();
            ej1.start(stage);
            primaryStage.close();
        }else if(pregunta instanceof PreguntaRellenarPalabras) {
        	RellenarPalabras ej2 = new RellenarPalabras(cursoActual.getTitulo(),(PreguntaRellenarPalabras) pregunta, actual);
            Stage stage2 = new Stage();
            ej2.start(stage2);
            primaryStage.close();
        }else if(pregunta instanceof PreguntaFlashCard) {
    		FlashCard ej3 = new FlashCard(cursoActual.getTitulo(), (PreguntaFlashCard) pregunta, actual);
            Stage stage3 = new Stage();
            ej3.start(stage3);
            primaryStage.close();
        }else if(pregunta instanceof PreguntaVF) {
        	VerdaderoFalso ej4 = new VerdaderoFalso(cursoActual.getTitulo(),(PreguntaVF) pregunta, actual);
            Stage stage4 = new Stage();
            ej4.start(stage4);
            primaryStage.close();
        }else {
        	alert.setTitle("Lección " + actual.getTitulo());
            alert.setHeaderText(actual.getTitulo());
            alert.setContentText("Aquí se abriría la lección seleccionada con su contenido.");
            alert.showAndWait();
        }
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
        
        // Panel central con lista de lecciones
        VBox centerCard = crearCenterCard();
        
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
        
        // Botón para volver a mis cursos
        Button btnVolver = new Button("Volver a Cursos");
        btnVolver.setGraphic(new ImageView(new Image("imagenes/return.png")));
        styleMenuButton(btnVolver);
        btnVolver.setOnAction(e -> volverACursos());
        
        menu.getChildren().addAll(btnVolver);
        
        return menu;
    }
    
    private HBox crearTopBar(VBox menuLateral) {
        // Foto de perfil
        imagenPerfilView = new ImageView(OfCourses.getUnicaInstancia().getFotoUsuarioActual());
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
    
    private VBox crearCenterCard() {
        VBox centerCard = new VBox(20);
        centerCard.setAlignment(Pos.TOP_CENTER);
        centerCard.setPadding(new Insets(30, 50, 40, 50));
        centerCard.setMaxWidth(800);
        centerCard.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        centerCard.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));
        
        Label title = new Label(nombreCurso);
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#1a73e8"));

        Label descripcion = new Label(cursoActual.getDescripcion());
        descripcion.setFont(Font.font("Segoe UI", 14));
        descripcion.setTextFill(Color.web("#666666"));
        descripcion.setWrapText(true);
        descripcion.setMaxWidth(700);

        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 20, 0));

        HBox progresoBox = new HBox(10);
        progresoBox.setAlignment(Pos.CENTER_LEFT);

        Label lblProgreso = new Label("Progreso del curso: ");
        lblProgreso.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lblProgreso.setTextFill(Color.web("#333333"));
        
        double porcentajeCompletado = cursoActual.obtenerProgreso();
        ProgressBar progressBar = new ProgressBar(porcentajeCompletado/100);
        progressBar.setPrefWidth(200);
        progressBar.setStyle("-fx-accent: #1a73e8;");
        
        String porc = (int)(porcentajeCompletado) + "%";
        Label lblPorcentaje = new Label(porc);
        lblPorcentaje.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lblPorcentaje.setTextFill(Color.web("#1a73e8"));

        progresoBox.getChildren().addAll(lblProgreso, progressBar, lblPorcentaje);

        Label lblLecciones = new Label("Lecciones del Curso");
        lblLecciones.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblLecciones.setTextFill(Color.web("#333333"));
        lblLecciones.setPadding(new Insets(10, 0, 10, 0));

        // Grid de lecciones
        GridPane gridLecciones = new GridPane();
        gridLecciones.setHgap(20);
        gridLecciones.setVgap(20);
        gridLecciones.setAlignment(Pos.TOP_CENTER);
        gridLecciones.setPadding(new Insets(10));

        List<Leccion> lecciones = cursoActual.getLecciones();
        int columnas = 3;

        for (int i = 0; i < lecciones.size(); i++) {
            int row = i / columnas;
            int col = i % columnas;

            VBox leccionCard = crearLeccionCard(lecciones.get(i), i + 1, OfCourses.getUnicaInstancia().isCompletada(lecciones.get(i)));
            gridLecciones.add(leccionCard, col, row);
        }

        // ScrollPane para las lecciones
        ScrollPane scrollLecciones = new ScrollPane(gridLecciones);
        scrollLecciones.setFitToWidth(true);
        scrollLecciones.setPrefViewportHeight(300);
        scrollLecciones.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollLecciones.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Agregar todo al centerCard
        centerCard.getChildren().addAll(title, descripcion, separator, progresoBox, lblLecciones, scrollLecciones);

        // Devolverlo envuelto en Pane
        VBox mainWrapper = new VBox(centerCard);
        mainWrapper.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        mainWrapper.setPadding(new Insets(30));

        return mainWrapper;
    }
    
    private VBox crearLeccionCard(Leccion leccion, int numeroLeccion, boolean completada) {
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
        Label lblNombre = new Label(leccion.getTitulo());
        lblNombre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblNombre.setTextFill(Color.web("#333333"));
        lblNombre.setWrapText(true);
        lblNombre.setAlignment(Pos.CENTER);
        lblNombre.setMaxWidth(180);
        
        // Espaciador
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Acción: botón o texto según el estado
        Node accion;

        if (completada) {
            Label lblCompletado = new Label("Completado");
            lblCompletado.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
            lblCompletado.setTextFill(Color.web("#2e7d32"));
            accion = lblCompletado;

        } else if (leccion.getUltimaPregunta() > 0) {
            Button btnContinuar = new Button("Continuar");
            styleLoginButton(btnContinuar);
            btnContinuar.setOnAction(e -> abrirLeccion(leccion));
            accion = btnContinuar;

        } else {
            Button btnComenzar = new Button("Comenzar");
            styleLoginButton(btnComenzar);
            btnComenzar.setOnAction(e -> abrirLeccion(leccion));
            accion = btnComenzar;
        }

        // Añadir elementos al card
        card.getChildren().addAll(lblNumero, lblNombre, spacer, accion);
        
        return card;
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