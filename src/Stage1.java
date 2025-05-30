import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.util.Optional;

// Ahora permite crear publicadores y suscriptores de video a través de un menú.
public class Stage1 extends Application {
    private VBox vBoxLeftPublishers;  // Contenedor vertical para mostrar los publicadores de video.
    private VBox vBoxRightSubscribers; // Contenedor vertical para mostrar los suscriptores de video.
    private Broker brokerInstance;       // Instancia única del Broker para toda la aplicación.

    @Override
    public void start(Stage primaryStage) {
        brokerInstance = new Broker(); // Crea la instancia del Broker.

        // --- Creación de la Barra de Menú ---
        MenuBar menuBar = new MenuBar();

        // Menú "Publisher"
        Menu menuPublisher = new Menu("Publisher");
        MenuItem menuItemVideoPub = new MenuItem("Video"); // Opción para crear publicador de video. [cite: 129]
        menuPublisher.getItems().addAll(menuItemVideoPub);

        // Menú "Subscriber"
        Menu menuSubscriber = new Menu("Subscriber");
        MenuItem menuItemVideoSubs = new MenuItem("Video"); // Opción para crear suscriptor de video. [cite: 129]
        menuSubscriber.getItems().addAll(menuItemVideoSubs);

        menuBar.getMenus().addAll(menuPublisher, menuSubscriber); // Añade los menús a la barra.

        // --- Configuración de los Contenedores para Publicadores y Suscriptores ---
        // VBox para la columna izquierda (publicadores).
        vBoxLeftPublishers = new VBox(10); // 10 es el espaciado vertical.
        vBoxLeftPublishers.setAlignment(Pos.TOP_CENTER); // Alineación.
        vBoxLeftPublishers.setStyle("-fx-padding: 10;"); // Estilo opcional.

        // VBox para la columna derecha (suscriptores).
        vBoxRightSubscribers = new VBox(10); // 10 es el espaciado vertical.
        vBoxRightSubscribers.setAlignment(Pos.TOP_CENTER); // Alineación.
        vBoxRightSubscribers.setStyle("-fx-padding: 10;"); // Estilo opcional.

        // --- Configuración del Layout Principal (BorderPane) ---
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar); // Barra de menú en la parte superior.
        borderPane.setLeft(new ScrollPane(vBoxLeftPublishers)); // Publicadores a la izquierda, con scroll.
        borderPane.setRight(new ScrollPane(vBoxRightSubscribers)); // Suscriptores a la derecha, con scroll.

        // --- Creación de la Escena y Configuración del Escenario ---
        ScrollPane rootScrollPane = new ScrollPane(borderPane); // Scroll principal si es necesario.
        rootScrollPane.setFitToWidth(true);
        rootScrollPane.setFitToHeight(true);
        Scene scene = new Scene(rootScrollPane, 800, 500); // Tamaño inicial de la ventana.

        primaryStage.setTitle("Publisher-Subscriber Simulator (Video Etapa 5.2)"); // Título de la ventana.
        primaryStage.setScene(scene); // Establece la escena en el escenario.
        primaryStage.show(); // Muestra la ventana.

        // --- Manejo de Acciones de los Menús ---
        // Acción para crear un nuevo publicador de video. [cite: 129]
        menuItemVideoPub.setOnAction(e -> addVideoPublisher());

        // Acción para crear un nuevo suscriptor de video.
        menuItemVideoSubs.setOnAction(e -> addVideoSubscriber());
    }

    // Utilizado para solicitar el nombre del publicador/suscriptor y el nombre del tópico.
    private String getInputString(String prompt) {
        String defaultValue = "default"; // Valor por defecto si el usuario no ingresa nada o cancela.
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(prompt); // Título de la ventana de diálogo.
        dialog.setHeaderText("Ingrese el " + prompt.toLowerCase() + ":"); // Cabecera del diálogo.
        dialog.setContentText(prompt + ":"); // Etiqueta para el campo de texto.

        Optional<String> result = dialog.showAndWait(); // Muestra el diálogo y espera la entrada.
        // Devuelve el resultado si está presente, sino devuelve el valor por defecto.
        return result.orElse(defaultValue);
    }

    // Lógica para añadir un nuevo publicador de video.
    private void addVideoPublisher() {
        String name = getInputString("Nombre del Publicador de Video");
        if (name.equals("default")) return; // Si el usuario cancela o no ingresa nombre.

        String topic = getInputString("Tópico para " + name);
        if (topic.equals("default")) return; // Si el usuario cancela o no ingresa tópico.

        // Crea el nuevo publicador de video.
        VideoPublisher newVideoPub = new VideoPublisher(name, brokerInstance, topic);
        // Añade la vista del publicador (su HBox) al VBox de la izquierda.
        vBoxLeftPublishers.getChildren().add(newVideoPub.getView());
    }

    // Lógica para añadir un nuevo suscriptor de video.
    private void addVideoSubscriber() {
        String name = getInputString("Nombre del Suscriptor de Video");
        if (name.equals("default")) return;

        String topic = getInputString("Tópico para " + name + " (debe existir)");
        if (topic.equals("default")) return;

        // Crea el nuevo seguidor de video.
        VideoFollower newVideoSub = new VideoFollower(name, topic);

        // Intenta suscribir el nuevo seguidor al broker.
        if (brokerInstance.subscribe(newVideoSub)) {
            // Si la suscripción es exitosa (el tópico existe), añade su vista.
            vBoxRightSubscribers.getChildren().add(newVideoSub.getView());
        } else {
            // Si la suscripción falla (ej. tópico no existe), muestra una alerta.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Suscripción");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo suscribir a '" + name + "' al tópico '" + topic + "'. Asegúrese de que el tópico exista (creado por un publicador).");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args); // Llama al método launch de la clase Application.
    }
}