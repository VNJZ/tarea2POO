import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PubSubsPatternSimulator extends Application {
    private VBox vBoxLeftPublishers;
    private VBox vBoxRightSubscribers;
    private Broker brokerInstance;
    private Stage primaryStage;
    private FileChooser fileChooser;

    private List<GPSCarPublisher> activeGpsPublishers = new ArrayList<>();
    private List<CarTracker> activeCarTrackers = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        brokerInstance = new Broker();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo de Posiciones GPS");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // --- Creación de la Barra de Menú ---
        MenuBar menuBar = new MenuBar();

        // Menú "Publisher"
        Menu menuPublisher = new Menu("Publisher");
        MenuItem menuItemVideoPub = new MenuItem("Video");
        MenuItem menuItemGPSPub = new MenuItem("Car's GPS");
        menuPublisher.getItems().addAll(menuItemVideoPub, menuItemGPSPub);

        // Menú "Subscriber"
        Menu menuSubscriber = new Menu("Subscriber");
        MenuItem menuItemVideoSubs = new MenuItem("Video");
        MenuItem menuItemGPSSubs = new MenuItem("Car's GPS");
        menuSubscriber.getItems().addAll(menuItemVideoSubs, menuItemGPSSubs);

        menuBar.getMenus().addAll(menuPublisher, menuSubscriber);

        // --- Configuración de los Contenedores para Publicadores y Suscriptores ---
        vBoxLeftPublishers = new VBox(10);
        vBoxLeftPublishers.setAlignment(Pos.TOP_CENTER);
        vBoxLeftPublishers.setStyle("-fx-padding: 10;");

        vBoxRightSubscribers = new VBox(10);
        vBoxRightSubscribers.setAlignment(Pos.TOP_CENTER);
        vBoxRightSubscribers.setStyle("-fx-padding: 10;");

        // --- Configuración del Layout Principal (BorderPane) ---
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setLeft(new ScrollPane(vBoxLeftPublishers));
        borderPane.setRight(new ScrollPane(vBoxRightSubscribers));

        ScrollPane rootScrollPane = new ScrollPane(borderPane);
        rootScrollPane.setFitToWidth(true);
        rootScrollPane.setFitToHeight(true);
        Scene scene = new Scene(rootScrollPane, 900, 600);

        primaryStage.setTitle("Publisher-Subscriber Simulator (Etapa 5.3)");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            for (GPSCarPublisher gpsPub : activeGpsPublishers) {
                gpsPub.stop();
            }
            for (CarTracker tracker : activeCarTrackers) {
                tracker.close();
            }
            System.out.println("Aplicación cerrada, publicadores GPS detenidos.");
        });

        // --- Manejo de Acciones de los Menús ---
        menuItemVideoPub.setOnAction(e -> addVideoPublisher());
        menuItemVideoSubs.setOnAction(e -> addVideoSubscriber());
        menuItemGPSPub.setOnAction(e -> addGPSCarPublisher());
        menuItemGPSSubs.setOnAction(e -> addCarTrackerSubscriber());
    }

    private String getInputString(String prompt, String defaultValueSuggestion) {
        String defaultValue = defaultValueSuggestion.isEmpty() ? "default" : defaultValueSuggestion;
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(prompt);
        dialog.setHeaderText("Ingrese el " + prompt.toLowerCase() + ":");
        dialog.setContentText(prompt + ":");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(defaultValueSuggestion.isEmpty() ? "default_cancelled" : defaultValue);
    }

    private void addVideoPublisher() {
        String name = getInputString("Nombre del Publicador de Video", "VideoPub");
        if (name.equals("default_cancelled")) return;

        String topic = getInputString("Tópico para " + name, "VideoTopic");
        if (topic.equals("default_cancelled")) return;

        VideoPublisher newVideoPub = new VideoPublisher(name, brokerInstance, topic);
        vBoxLeftPublishers.getChildren().add(newVideoPub.getView());
    }

    private void addVideoSubscriber() {
        String name = getInputString("Nombre del Suscriptor de Video", "VideoSub");
        if (name.equals("default_cancelled")) return;

        String topic = getInputString("Tópico para " + name + " (debe existir)", "VideoTopic");
        if (topic.equals("default_cancelled")) return;

        VideoFollower newVideoSub = new VideoFollower(name, topic);
        if (brokerInstance.subscribe(newVideoSub)) {
            vBoxRightSubscribers.getChildren().add(newVideoSub.getView());
        } else {
            showSubscriptionErrorAlert(name, topic);
        }
    }

    private void addGPSCarPublisher() {
        String name = getInputString("Nombre del Publicador GPS de Auto", "GPS_Pub1");
        if (name.equals("default_cancelled")) return;

        String topic = getInputString("Tópico para " + name, "Posiciones_Auto1");
        if (topic.equals("default_cancelled")) return;

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            GPSCarPublisher newGPSPub = new GPSCarPublisher(name, brokerInstance, topic, selectedFile);
            vBoxLeftPublishers.getChildren().add(newGPSPub.getView());
            activeGpsPublishers.add(newGPSPub);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operación Cancelada");
            alert.setHeaderText(null);
            alert.setContentText("No se seleccionó ningún archivo para el publicador GPS.");
            alert.showAndWait();
        }
    }

    private void addCarTrackerSubscriber() {
        String name = getInputString("Nombre del Suscriptor de Rastreo de Auto", "Tracker1");
        if (name.equals("default_cancelled")) return;

        String topic = getInputString("Tópico para " + name + " (debe existir)", "Posiciones_Auto1");
        if (topic.equals("default_cancelled")) return;

        CarTracker newCarTracker = new CarTracker(name, topic); // [cite: 143]
        if (brokerInstance.subscribe(newCarTracker)) {
            newCarTracker.show();
            activeCarTrackers.add(newCarTracker);
            Label trackerLabel = new Label("Tracker Activo: " + name + " (" + topic + ")");
            vBoxRightSubscribers.getChildren().add(trackerLabel);

        } else {
            showSubscriptionErrorAlert(name, topic);
        }
    }

    private void showSubscriptionErrorAlert(String name, String topic) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Suscripción");
        alert.setHeaderText(null);
        alert.setContentText("No se pudo suscribir a '" + name + "' al tópico '" + topic + "'. Asegúrese de que el tópico exista (creado por un publicador).");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}