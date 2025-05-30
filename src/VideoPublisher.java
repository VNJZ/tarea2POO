import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label; // Añadimos Label para mostrar el nombre del publicador

public class VideoPublisher extends Publisher {
    private HBox view;

    public VideoPublisher(String name, Broker broker, String topic) {
        super(name, broker, topic); // Llama al constructor de Publisher

        Label nameLabel = new Label(name + " (" + topic + "): "); // Muestra el nombre del publicador y su tópico
        TextField urlField = new TextField();
        urlField.setPromptText("Ingrese URL del video");
        urlField.setPrefWidth(250); // Ajusta el ancho del campo de texto

        urlField.setOnAction(e -> {
            String url = urlField.getText();
            if (!url.isEmpty()) {
                // Publica la URL del video usando el método del Publisher
                publishNewEvent(url);
                urlField.clear(); // Limpia el campo de texto después de publicar
            }
        });

        view = new HBox(10);
        view.getChildren().addAll(nameLabel, urlField);
    }

    public HBox getView() {
        return view;
    }
}