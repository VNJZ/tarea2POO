package cl.utfsm.tarea2.tarea2poo;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class VideoFollower extends Subscriber {
    private HBox view;
    private Button videoButton;

    public VideoFollower(String name, String topic) {
        super(name, topic);

        videoButton = new Button("Esperando video...");
        videoButton.setDisable(true); // No hace nada aún

        view = new HBox(10);
        view.getChildren().add(videoButton);
    }

    @Override
    public void receive(String message) {
        videoButton.setText(message);
        videoButton.setDisable(false);
        // El botón aún no reproduce nada en esta etapa
    }

    public HBox getView() {
        return view;
    }
}
