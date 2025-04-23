package vistas;

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

public class VerdaderoFalso extends Application {
	
	private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;

    // <--------------------------------------------------------------->
    // <------------------- FUNCIONES DE BOTONES ---------------------->
    // <--------------------------------------------------------------->
    
    private void volverAtras() {
    	try {
            LeccionesCurso lecciones = new LeccionesCurso();
            Stage stage = new Stage();
            lecciones.start(stage);
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void siguientePregunta() {
    	// TODO: método para mostrar la siguiente pregunta
    }
    
    private void verificarRespuesta(ToggleGroup grupoOpciones) {
        if (grupoOpciones.getSelectedToggle() == null) {
            mostrarAlerta("Advertencia", "Debes seleccionar una opción.", Alert.AlertType.WARNING);
        } else {
            RadioButton seleccionada = (RadioButton) grupoOpciones.getSelectedToggle();
			String respuestaSeleccionada = seleccionada.getText();

            // TODO: Llamar al controlador para verificar si la respuestaSeleccionada es correcta
            boolean esCorrecto = respuestaSeleccionada.equals("Verdadero");

            String mensaje = esCorrecto ? "¡Correcto!" : "Incorrecto.";
            mostrarAlerta("Resultado", mensaje, Alert.AlertType.INFORMATION);
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
        ImageView imagenPerfilView = new ImageView(new Image("imagenes/foto-perfil-default.png"));
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
        btnCerrar.setOnAction(e -> System.exit(0));
        
        // Barra superior
        HBox topBar = new HBox(15, imagenPerfilView, lblNombreUsuario, spacer, btnCerrar);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15, 25, 15, 25));
        topBar.setStyle("-fx-background-color: rgba(0,0,0,0.1);");
        
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

        Label lblPregunta = new Label("Los antibióticos son efectivos "
        		+ "para tratar infecciones causadas tanto por bacterias como por virus, por "
        		+ "lo que se recomienda su uso en resfriados y gripes.");
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

        panelVF.getChildren().addAll(lblTitulo, lblPregunta, opciones, btnVerificar);

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
}
