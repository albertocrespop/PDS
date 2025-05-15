package vistas;

import controlador.OfCourses;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RellenarPalabras extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;
    private ImageView imagenPerfilView;
    
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
    
    private void mostrarPista() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pista");
        alert.setHeaderText(null);
        
        // TODO: Llamar al controlador para obtener la pista
        alert.setContentText("Pista sobre el enunciado");
        
        // Estilo de la alerta
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: white;");
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14; -fx-text-fill: #333333;");
        
        alert.showAndWait();
    }
    
    private void verificarSolucion(String solucion) {
        if (solucion.isEmpty()) {
            mostrarAlerta("Error", "Por favor escribe tu solución antes de verificar", Alert.AlertType.WARNING);
            return;
        }
        
        // TODO: Llamar al controlador para verificar la respuesta
        mostrarAlerta("Resultado", "Tu solución está siendo verificada...", Alert.AlertType.INFORMATION);
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
 		contenedorConMargenes.setPadding(new Insets(20));
        
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
        primaryStage.setTitle("Ejercicio - OfCourses");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Centrar la ventana
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
        
        HBox vidasBox = crearIndicadorVidas(OfCourses.getUnicaInstancia().getVidas());
        
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
        VBox panelEjercicio = new VBox(20);
        panelEjercicio.setAlignment(Pos.TOP_CENTER);
        panelEjercicio.setPadding(new Insets(30, 50, 40, 50));
        panelEjercicio.setMaxWidth(800);
        panelEjercicio.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        panelEjercicio.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));

        Label lblEnunciado = new Label("Ejercicio con huecos");
        lblEnunciado.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        lblEnunciado.setTextFill(Color.web("#1a73e8"));

        Label lblInstruccion = new Label("Completa la frase:");
        lblInstruccion.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblInstruccion.setTextFill(Color.web("#333333"));
        
        // TODO: Llamar al controlador y obtener el texto de la pregunta
        String textoDePrueba = "La inteligencia artificial **?** ha revolucionado **?** muchos sectores como la medicina, la educación, y el transporte. " +
                "En el ámbito de la salud, por ejemplo, **?** puede ayudar a diagnosticar enfermedades a partir de imágenes médicas, " +
                "mientras que en la educación permite personalizar el **?** ritmo de aprendizaje de cada estudiante. " +
                "Por otro lado, en el transporte, los vehículos **?** autónomos están empezando a cambiar el paradigma de la movilidad urbana.";
        TextFlow fraseConHuecos = generarFraseConHuecos(textoDePrueba);
        fraseConHuecos.setTextAlignment(TextAlignment.LEFT);
        fraseConHuecos.setLineSpacing(5);

        Button btnVerificar = new Button("Verificar Solución");
        styleButton(btnVerificar);
        btnVerificar.setOnAction(e -> {
            StringBuilder solucion = new StringBuilder();
            for (javafx.scene.Node nodo : fraseConHuecos.getChildren()) {
            	if (nodo instanceof TextField) {
            	    TextField campo = (TextField) nodo;
            	    solucion.append("[").append(campo.getText()).append("]");
            	}
            }
            verificarSolucion(solucion.toString());
        });

        Button btnPista = new Button("Pista");
        styleSecondaryButton(btnPista);
        btnPista.setOnAction(e -> mostrarPista());

        HBox panelBotones = new HBox(btnPista, btnVerificar);
        panelBotones.setSpacing(20);
        panelBotones.setAlignment(Pos.CENTER);

        panelEjercicio.getChildren().addAll(lblEnunciado, lblInstruccion, fraseConHuecos, panelBotones);

        return panelEjercicio;
    }

    private TextFlow generarFraseConHuecos(String fraseOriginal) {
        TextFlow contenedor = new TextFlow();
        contenedor.setPadding(new Insets(10));
        contenedor.setPrefWidth(600); // Ajusta este valor según tu diseño
        contenedor.setLineSpacing(5);

        String[] partes = fraseOriginal.split("\\*\\*\\?\\*\\*");
        for (int i = 0; i < partes.length; i++) {
            Text texto = new Text(partes[i]);
            texto.setFont(Font.font("Segoe UI", 14));
            contenedor.getChildren().add(texto);

            if (i < partes.length - 1) {
                TextField campo = new TextField();
                campo.setPromptText("...");
                campo.setFont(Font.font("Segoe UI", 14));
                campo.setPrefColumnCount(8);
                campo.setMaxWidth(120);
                contenedor.getChildren().add(campo);
            }
        }
        return contenedor;
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
}