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

public class EjercicioEscrito extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        // Contenedor principal
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a73e8, #0d47a1);");
        
        // Barra superior con usuario
        HBox topBar = crearTopBar();
        
        // Panel central con ejercicio
        VBox centerCard = crearPanelEjercicio();
        
        // Configurar el layout
        root.setTop(topBar);
        root.setCenter(centerCard);
        
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
    }
    
    private HBox crearTopBar() {
        // Foto de perfil
        ImageView imagenPerfilView = new ImageView(new Image(getClass().getResourceAsStream("/images/logo.png")));
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
        // Panel principal
        VBox panelEjercicio = new VBox(20);
        panelEjercicio.setAlignment(Pos.TOP_CENTER);
        panelEjercicio.setPadding(new Insets(30, 50, 40, 50));
        panelEjercicio.setMaxWidth(800);
        panelEjercicio.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        panelEjercicio.setEffect(new DropShadow(20, Color.rgb(0, 0, 0, 0.3)));
        
        // Enunciado del ejercicio
        Label lblEnunciado = new Label("Ejercicio de Programación");
        lblEnunciado.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        lblEnunciado.setTextFill(Color.web("#1a73e8"));
        
        // Texto del enunciado con scroll
        TextArea taEnunciado = new TextArea();
        taEnunciado.setText("Escribe una función en Java que reciba un array de enteros y devuelva:\n\n"
                + "1. La suma de todos los elementos\n"
                + "2. El valor máximo\n"
                + "3. El valor mínimo\n\n"
                + "La función debe retornar un objeto con estos tres valores.\n\n"
                + "Ejemplo de entrada: [4, 2, 9, 5, 1]\n"
                + "Salida esperada: {suma: 21, max: 9, min: 1}");
        taEnunciado.setEditable(false);
        taEnunciado.setWrapText(true);
        taEnunciado.setStyle("-fx-font-size: 14; -fx-control-inner-background: #f8f9fa;");
        
        ScrollPane scrollEnunciado = new ScrollPane(taEnunciado);
        scrollEnunciado.setFitToWidth(true);
        scrollEnunciado.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollEnunciado.setPadding(new Insets(0));
        
        // Panel para enunciado y botón de pista
        HBox panelEnunciado = new HBox(15);
        panelEnunciado.setAlignment(Pos.CENTER_LEFT);
        
        Button btnPista = new Button("Pista");
        btnPista.setStyle("-fx-background-color: transparent; -fx-text-fill: #1a73e8; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 8 15; -fx-background-radius: 5; -fx-border-color: #1a73e8; -fx-border-width: 1; -fx-border-radius: 5;");
        btnPista.setOnAction(e -> mostrarPista());
        
        VBox.setVgrow(scrollEnunciado, Priority.ALWAYS);
        panelEnunciado.getChildren().addAll(scrollEnunciado, btnPista);
        
        // Área para la respuesta
        Label lblRespuesta = new Label("Tu solución:");
        lblRespuesta.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblRespuesta.setTextFill(Color.web("#333333"));
        
        TextArea taRespuesta = new TextArea();
        taRespuesta.setPromptText("Escribe tu código aquí...");
        taRespuesta.setWrapText(true);
        taRespuesta.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14;");
        
        // Botón de verificación
        Button btnVerificar = new Button("Verificar Solución");
        btnVerificar.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 25; -fx-background-radius: 5;");
        btnVerificar.setOnAction(e -> verificarSolucion(taRespuesta.getText()));
        
        // Configurar el panel
        panelEjercicio.getChildren().addAll(lblEnunciado, panelEnunciado, lblRespuesta, taRespuesta, btnVerificar);
        
        return panelEjercicio;
    }
    
    private void mostrarPista() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pista");
        alert.setHeaderText(null);
        alert.setContentText("Puedes crear una clase Resultado con tres propiedades: suma, max y min.\nLuego itera sobre el array para calcular estos valores.");
        
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
        
        // Aquí iría la lógica real de verificación
        mostrarAlerta("Resultado", "Tu solución está siendo verificada...", Alert.AlertType.INFORMATION);
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

}