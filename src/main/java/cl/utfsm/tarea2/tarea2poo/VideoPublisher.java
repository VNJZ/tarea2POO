package cl.utfsm.tarea2.tarea2poo;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class VideoPublisher extends Publisher {
    private HBox view;

    public VideoPublisher(String name, String topic) {
        super(name, topic);

        TextField urlField = new TextField();
        urlField.setPromptText("Ingrese URL del video");

        urlField.setOnAction(e -> {
            String url = urlField.getText();
            if (!url.isEmpty()) {
                publish(url);
                urlField.clear();
            }
        });

        view = new HBox(10);
        view.getChildren().add(urlField);
    }

    public HBox getView() {
        return view;
    }
}
