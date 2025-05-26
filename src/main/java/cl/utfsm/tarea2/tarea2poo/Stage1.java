package cl.utfsm.tarea2.tarea2poo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Stage1 extends Application {

    private VBox publishersBox = new VBox(10);
    private VBox subscribersBox = new VBox(10);

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Menú
        MenuBar menuBar = new MenuBar();

        Menu menuPublisher = new Menu("Publisher");
        MenuItem videoPublisherItem = new MenuItem("Video");
        videoPublisherItem.setOnAction(e -> addVideoPublisher());
        menuPublisher.getItems().add(videoPublisherItem);

        Menu menuSubscriber = new Menu("Subscriber");
        MenuItem videoSubscriberItem = new MenuItem("Video");
        videoSubscriberItem.setOnAction(e -> addVideoFollower());
        menuSubscriber.getItems().add(videoSubscriberItem);

        menuBar.getMenus().addAll(menuPublisher, menuSubscriber);

        // Layout principal
        HBox mainContent = new HBox(20);
        mainContent.getChildren().addAll(publishersBox, subscribersBox);

        root.setTop(menuBar);
        root.setCenter(mainContent);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Simulador Publicador-Suscriptor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addVideoPublisher() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo Publicador");
        dialog.setHeaderText("Ingrese nombre del publicador:");
        dialog.setContentText("Nombre:");
        String name = dialog.showAndWait().orElse(null);
        if (name == null || name.isEmpty()) return;

        dialog = new TextInputDialog();
        dialog.setTitle("Nuevo Publicador");
        dialog.setHeaderText("Ingrese nombre del tópico:");
        dialog.setContentText("Tópico:");
        String topic = dialog.showAndWait().orElse(null);
        if (topic == null || topic.isEmpty()) return;

        VideoPublisher vp = new VideoPublisher(name, topic);
        publishersBox.getChildren().add(vp.getView());
    }

    private void addVideoFollower() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo Suscriptor");
        dialog.setHeaderText("Ingrese nombre del suscriptor:");
        dialog.setContentText("Nombre:");
        String name = dialog.showAndWait().orElse(null);
        if (name == null || name.isEmpty()) return;

        dialog = new TextInputDialog();
        dialog.setTitle("Nuevo Suscriptor");
        dialog.setHeaderText("Ingrese nombre del tópico:");
        dialog.setContentText("Tópico:");
        String topic = dialog.showAndWait().orElse(null);
        if (topic == null || topic.isEmpty()) return;

        VideoFollower vf = new VideoFollower(name, topic);
        subscribersBox.getChildren().add(vf.getView());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
