package vistas;

import controlador.OfCourses;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Leccion;
import modelo.Pregunta;
import modelo.PreguntaFlashCard;
import modelo.PreguntaOrdenarPalabras;
import modelo.PreguntaRellenarPalabras;
import modelo.PreguntaVF;

public class VerdaderoFalso extends Application {
	
	private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;
    private ImageView imagenPerfilView;
    private String curso;
    private PreguntaVF pregunta;
    private Leccion leccionActual;
    private Button btnSiguiente;
    private HBox vidasBox;


    // <--------------------------------------------------------------->
    // <------------------- FUNCIONES DE BOTONES ---------------------->
    // <--------------------------------------------------------------->
    
    public VerdaderoFalso(String curso, PreguntaVF pregunta, Leccion leccion) {
    	this.pregunta = pregunta;
    	this.curso = curso;
    	this.leccionActual = leccion;
    }

	private void volverAtras() {
    	try {
            LeccionesCurso lecciones = new LeccionesCurso(curso);
            Stage stage = new Stage();
            lecciones.start(stage);
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void siguientePregunta() {

    	Pregunta pregunta = OfCourses.getUnicaInstancia().getSiguientePregunta(leccionActual);
        
        if(pregunta instanceof PreguntaOrdenarPalabras) {
    		OrdenarPalabras ej1 = new OrdenarPalabras(curso,(PreguntaOrdenarPalabras) pregunta, leccionActual);
            Stage stage = new Stage();
            ej1.start(stage);
            primaryStage.close();
        }else if(pregunta instanceof PreguntaRellenarPalabras) {
        	RellenarPalabras ej2 = new RellenarPalabras(curso,(PreguntaRellenarPalabras) pregunta, leccionActual);
            Stage stage2 = new Stage();
            ej2.start(stage2);
            primaryStage.close();
        }else if(pregunta instanceof PreguntaFlashCard) {
    		FlashCard ej3 = new FlashCard(curso, (PreguntaFlashCard) pregunta, leccionActual);
            Stage stage3 = new Stage();
            ej3.start(stage3);
            primaryStage.close();
        }else if(pregunta instanceof PreguntaVF) {
        	VerdaderoFalso ej4 = new VerdaderoFalso(curso,(PreguntaVF) pregunta, leccionActual);
            Stage stage4 = new Stage();
            ej4.start(stage4);
            primaryStage.close();
        }else {
        	LeccionesCurso vistaLecciones = new LeccionesCurso(curso);
            Stage stageLecciones = new Stage();
            stageLecciones.initStyle(StageStyle.TRANSPARENT);
            vistaLecciones.start(stageLecciones);
            primaryStage.close();
        }
    }
    
    private void verificarRespuesta(ToggleGroup grupoOpciones) {
        if (grupoOpciones.getSelectedToggle() == null) {
            mostrarAlerta("Advertencia", "Debes seleccionar una opción.", Alert.AlertType.WARNING);
        } else {
            RadioButton seleccionada = (RadioButton) grupoOpciones.getSelectedToggle();
            String respuestaSeleccionada = seleccionada.getText();

            boolean esCorrecto = pregunta.comprobarRespuesta(respuestaSeleccionada);
            
            if(!esCorrecto) {
            	OfCourses.getUnicaInstancia().perderVida();
            	actualizarIndicadorVidas();
            	if(!OfCourses.getUnicaInstancia().tieneVidas()) {
            		volverAtras();
            	}
            }
            
            String mensaje = esCorrecto ? "¡Correcto!" : "Incorrecto.";
            mostrarAlerta("Resultado", mensaje, Alert.AlertType.INFORMATION);

            // Mostrar botón solo si es correcto
            btnSiguiente.setVisible(esCorrecto);
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
        
        // Barra superior con usuario
        HBox topBar = crearTopBar();
        
        HBox botonVolver = crearPanelBotones();
        
        // Panel central con ejercicio
        VBox centerCard = crearPanelEjercicio();

		// Envolver el panel en otro VBox para dar márgenes arriba y abajo
		VBox contenedorConMargenes = new VBox(centerCard);
		contenedorConMargenes.setAlignment(Pos.CENTER);
		contenedorConMargenes.setPadding(new Insets(10));
		
		VBox panelCentro = new VBox(botonVolver, contenedorConMargenes);
        
        // Configurar el layout
        root.setTop(topBar);
        root.setCenter(panelCentro);
        
        // Configurar la escena
        Scene scene = new Scene(root, 900, 600);
        scene.setFill(Color.TRANSPARENT);
        
        // Hacer la ventana arrastrable desde la barra superior
        topBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        
        topBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        
        // Configurar el stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Ordenar Palabras - OfCourses");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Centra la ventana
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double xCenter = (screenBounds.getWidth() - primaryStage.getWidth()) / 2;
        double yCenter = (screenBounds.getHeight() - primaryStage.getHeight()) / 2;
        primaryStage.setX(xCenter);
        primaryStage.setY(yCenter);
    }
    
    private HBox crearPanelBotones() {
    	HBox panelBotones = new HBox();
    	
    	// Botón para volver atrás
    	Button btnVolver = new Button("Volver a lecciones");
        styleButton(btnVolver);
        btnVolver.setOnAction(e -> volverAtras());
        btnVolver.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));
        btnVolver.setPickOnBounds(false);
        
        panelBotones.getChildren().addAll(btnVolver);
        panelBotones.setPadding(new Insets(10,10,30,10));
        panelBotones.setSpacing(20);
        return panelBotones;
    }
    
    private HBox crearTopBar() {
        
        // Foto de perfil
    	imagenPerfilView = new ImageView(OfCourses.getUnicaInstancia().getFotoUsuarioActual());
        imagenPerfilView.setFitWidth(40);
        imagenPerfilView.setFitHeight(40);
        imagenPerfilView.setStyle("-fx-border-radius: 20; -fx-border-color: white; -fx-border-width: 2;");
        
        // Nombre de usuario
        
        String nombreUsuario = OfCourses.getUnicaInstancia().getNombreUsuario();
        Label lblNombreUsuario = new Label(nombreUsuario);
        lblNombreUsuario.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblNombreUsuario.setTextFill(Color.WHITE);
        
        // Espaciador
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        vidasBox = crearIndicadorVidas(OfCourses.getUnicaInstancia().getVidas());
        
        // Botón de cerrar ventana
        Button btnCerrar = new Button("✕");
        btnCerrar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18;");
        btnCerrar.setOnAction(e -> primaryStage.close());
        
        // Barra superior
        HBox topBar = new HBox(15, imagenPerfilView, lblNombreUsuario, vidasBox, spacer, btnCerrar);
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
    
    private VBox crearPanelEjercicio() {
        VBox panelVF = new VBox(20);
        panelVF.setAlignment(Pos.TOP_CENTER);
        panelVF.setPadding(new Insets(30, 50, 40, 50));
        panelVF.setMaxWidth(600);
        panelVF.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        panelVF.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));

        Label lblTitulo = new Label("Verdadero o Falso");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        lblTitulo.setTextFill(Color.web("#1a73e8"));

        Label lblPregunta = new Label(pregunta.getEnunciado());
        lblPregunta.setFont(Font.font("Segoe UI", 18));
        lblPregunta.setWrapText(true);
        lblPregunta.setTextFill(Color.web("#333333"));
        lblPregunta.setAlignment(Pos.CENTER);
        lblPregunta.setMaxWidth(500);
        lblPregunta.setStyle("-fx-border-color: transparent; -fx-padding: 20;");

        ToggleGroup grupoOpciones = new ToggleGroup();
        RadioButton opcionVerdadero = new RadioButton("Verdadero");
        opcionVerdadero.setToggleGroup(grupoOpciones);
        opcionVerdadero.setFont(Font.font("Segoe UI", 14));

        RadioButton opcionFalso = new RadioButton("Falso");
        opcionFalso.setToggleGroup(grupoOpciones);
        opcionFalso.setFont(Font.font("Segoe UI", 14));

        VBox opciones = new VBox(10, opcionVerdadero, opcionFalso);
        opciones.setAlignment(Pos.CENTER_LEFT);
        
        Button btnVerificar = new Button("Verificar respuesta");
        styleButton(btnVerificar);
        btnVerificar.setOnAction(e -> verificarRespuesta(grupoOpciones));

        btnSiguiente = new Button("Siguiente Pregunta");
        styleButton(btnSiguiente);
        btnSiguiente.setVisible(false);
        btnSiguiente.setOnAction(e -> siguientePregunta());

        panelVF.getChildren().addAll(lblTitulo, lblPregunta, opciones, btnVerificar, btnSiguiente);

        return panelVF;
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
    
    private void styleButton(Button button) {
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
    
    private HBox crearIndicadorVidas(int vidasActuales) {
        HBox vidasBox = new HBox(5);
        vidasBox.setAlignment(Pos.CENTER_RIGHT);
        
        for (int i = 0; i < 5; i++) {
            Image img = new Image("imagenes/" + (i < vidasActuales ? "vida_llena.png" : "vida_vacia.png"));
            ImageView view = new ImageView(img);
            view.setFitWidth(20);
            view.setFitHeight(20);
            vidasBox.getChildren().add(view);
        }
        return vidasBox;
    }
    
    private void actualizarIndicadorVidas() {
        vidasBox.getChildren().clear();

        int vidasActuales = OfCourses.getUnicaInstancia().getVidas();

        for (int i = 0; i < 5; i++) {
            Image img = new Image("imagenes/" + (i < vidasActuales ? "vida_llena.png" : "vida_vacia.png"));
            ImageView view = new ImageView(img);
            view.setFitWidth(20);
            view.setFitHeight(20);
            vidasBox.getChildren().add(view);
        }
    }
}
