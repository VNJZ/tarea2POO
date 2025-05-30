import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Label;

public class VideoFollower extends Subscriber {

    private HBox view;
    private Button videoButton;
    private String lastUrl;
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Label nameLabel; // Para mostrar el nombre del seguidor

    public VideoFollower(String name, String topic) {
        super(name, topic);

        nameLabel = new Label(name + " (" + topic + "): "); // Mostrar el nombre del seguidor y su tópico
        videoButton = new Button("▶ Esperando video...");
        videoButton.setDisable(true); // Deshabilitado hasta que se reciba una URL

        videoButton.setOnAction(e -> {
            if (lastUrl != null && !lastUrl.isEmpty()) {
                System.out.println(getName() + " reproduciendo: " + lastUrl);
                playVideo(lastUrl);
            }
        });

        mediaView = new MediaView();
        // Opcional: ajustar el tamaño de la vista de medios
        mediaView.setFitWidth(200);
        mediaView.setFitHeight(150);

        view = new HBox(10);
        view.getChildren().addAll(nameLabel, videoButton, mediaView);
    }

    @Override
    // El método 'update' de Subscriber se usará para recibir la URL del video.
    public void update(String message) {
        lastUrl = message;
        videoButton.setDisable(false); // Habilitar el botón cuando se recibe una URL
        videoButton.setText("▶ Ver video");
    }

    private void playVideo(String url) {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Detener el video anterior si lo hay
        }
        try {
            Media media = new Media(url);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error al reproducir video desde URL: " + url + " - " + e.getMessage());
            videoButton.setText("▶ URL inválida");
            videoButton.setDisable(true);
        }
    }

    public HBox getView() {
        return view;
    }
}