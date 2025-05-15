package vistas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Collections;
import java.util.List;

import controlador.OfCourses;

import java.util.ArrayList;

public class OrdenarPalabras extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private List<Label> palabrasLabels = new ArrayList<>();
    private HBox palabrasContainer = new HBox(10);
    private Stage primaryStage;
    private ImageView imagenPerfilView;

    // <--------------------------------------------------------------->
    // <------------------- FUNCIONES DE BOTONES ---------------------->
    // <--------------------------------------------------------------->
    
    private void verificarOrden() {
        StringBuilder fraseOrdenada = new StringBuilder();
        
        // Obtener el orden ACTUAL de las palabras en el contenedor visual
        for (Node node : palabrasContainer.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                fraseOrdenada.append(label.getText()).append(" ");
            }
        }
        
        String resultado = fraseOrdenada.toString().trim();
        String correcto = "SI A ENTONCES B SINO C";
        
        if (resultado.equalsIgnoreCase(correcto)) {
            mostrarAlerta("¡Correcto!", "Has ordenado las palabras correctamente: " + resultado, Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Incorrecto", "El orden correcto es: SI A ENTONCES B SINO C\nTu orden: " + resultado, Alert.AlertType.ERROR);
        }
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
        // Panel principal
        VBox panelEjercicio = new VBox(20);
        panelEjercicio.setAlignment(Pos.TOP_CENTER);
        panelEjercicio.setPadding(new Insets(30, 50, 40, 50));
        panelEjercicio.setMaxWidth(800);
        panelEjercicio.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        panelEjercicio.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));
        
        // Enunciado del ejercicio
        Label lblEnunciado = new Label("Ordena las palabras para formar una función if-else en la que se ejecute B sólo si \nse cumple A, o se ejecute C en caso contrario:");
        lblEnunciado.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblEnunciado.setTextFill(Color.web("#1a73e8"));
        
        // Contenedor de palabras (área de arrastre)
        palabrasContainer.setAlignment(Pos.CENTER);
        palabrasContainer.setPadding(new Insets(20));
        palabrasContainer.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 10;");
        palabrasContainer.setMinHeight(100);
        
        // Crear palabras desordenadas
        List<String> palabras = new ArrayList<>();
        palabras.add("SI");
        palabras.add("A");
        palabras.add("ENTONCES");
        palabras.add("B");
        palabras.add("SINO");
        palabras.add("C");
        
        Collections.shuffle(palabras);
        
        for (String palabra : palabras) {
            Label palabraLabel = crearPalabraDraggable(palabra);
            palabrasLabels.add(palabraLabel);
            palabrasContainer.getChildren().add(palabraLabel);
        }
        
        // Configurar eventos de arrastre para el contenedor
        configurarDropTarget(palabrasContainer);
        
        // Botón de verificación
        Button btnVerificar = new Button("Verificar Orden");
        styleButton(btnVerificar);
        btnVerificar.setOnAction(e -> verificarOrden());
        
        // Botón de pista
        Button btnPista = new Button("Pista");
        styleSecondaryButton(btnPista);
        btnPista.setOnAction(e -> mostrarPista());
        
        
        HBox panelBotones = new HBox(btnPista, btnVerificar);
        panelBotones.setSpacing(20);
        panelBotones.setAlignment(Pos.CENTER);
        
        // Configurar el panel
        panelEjercicio.getChildren().addAll(lblEnunciado, palabrasContainer, panelBotones);
        
        return panelEjercicio;
    }
    
    private void configurarDropTarget(HBox target) {
        target.setOnDragOver(event -> {
            if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        
        target.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            
            if (db.hasString()) {
                Label sourceLabel = (Label) event.getGestureSource();
                double mouseX = event.getX();
                int insertIndex = 0;
                
                // Determinar la posición de inserción
                for (int i = 0; i < target.getChildren().size(); i++) {
                    Node child = target.getChildren().get(i);
                    if (mouseX < child.getBoundsInParent().getMinX() + child.getBoundsInParent().getWidth() / 2) {
                        insertIndex = i;
                        break;
                    }
                    insertIndex = i + 1;
                }
                
                // Solo mover si es a una posición diferente
                if (target.getChildren().indexOf(sourceLabel) != insertIndex) {
                    target.getChildren().remove(sourceLabel);
                    
                    // Asegurarse de no exceder los límites
                    insertIndex = Math.min(insertIndex, target.getChildren().size());
                    insertIndex = Math.max(insertIndex, 0);
                    
                    target.getChildren().add(insertIndex, sourceLabel);
                }
                
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private Label crearPalabraDraggable(String texto) {
        Label label = new Label(texto);
        label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        label.setStyle("-fx-background-color: #e8f0fe; -fx-text-fill: #1a73e8; -fx-padding: 10 15; " +
                     "-fx-background-radius: 5; -fx-border-color: #1a73e8; -fx-border-width: 1; " +
                     "-fx-border-radius: 5;");

        label.setOnDragDetected(event -> {
            if (event.isPrimaryButtonDown()) {
                Dragboard db = label.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(label.getText());
                db.setContent(content);
                
                // El label se ve durate el drag
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT); // evita fondo blanco
                db.setDragView(label.snapshot(params, null));

                event.consume();
            }
        });

        label.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                event.consume();
            }
        });

        return label;
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