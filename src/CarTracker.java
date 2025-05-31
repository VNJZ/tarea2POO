import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.Scanner;

public class CarTracker extends Subscriber {
    private Stage stage;
    private Label telemetryLabel;
    private Circle carRepresentation;
    private Pane drawingPane;

    private static final double DRAWING_PANE_WIDTH = 500.0; // Ancho del área de rastreo
    private static final double DRAWING_PANE_HEIGHT = 500.0; // Alto del área de rastreo
    private static final double CAR_RADIUS = 8.0; // Radio del círculo que representa el auto

    public CarTracker(String name, String topicName) {
        super(name, topicName);
        stage = new Stage();
        stage.setTitle("Car Tracker: " + name + ", Topic: " + topicName);

        telemetryLabel = new Label("Esperando datos de posición...");
        telemetryLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // Inicializa el círculo que representa el auto
        carRepresentation = new Circle(CAR_RADIUS, Color.RED);
        carRepresentation.setCenterX(CAR_RADIUS);
        carRepresentation.setCenterY(CAR_RADIUS);

        // Panel para dibujar el círculo
        drawingPane = new Pane();
        drawingPane.setPrefSize(DRAWING_PANE_WIDTH, DRAWING_PANE_HEIGHT);
        drawingPane.setStyle("-fx-background-color: white; -fx-border-color: lightgrey;"); // Fondo blanco y borde
        drawingPane.getChildren().add(carRepresentation); // Agrega el círculo al panel de dibujo

        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(drawingPane); // El panel de dibujo en el centro
        rootPane.setBottom(telemetryLabel);
        BorderPane.setAlignment(telemetryLabel, Pos.CENTER);

        // Ajustar el tamaño de la escena para que quepa el panel de dibujo y la etiqueta
        Scene scene = new Scene(rootPane, DRAWING_PANE_WIDTH, DRAWING_PANE_HEIGHT + 50); // 50px extra para la etiqueta
        stage.setScene(scene);
        stage.setResizable(false);
    }

    @Override
    public void update(String message) {
        try (Scanner scanner = new Scanner(message)) {
            scanner.useLocale(Locale.US);
            if (scanner.hasNextDouble()) {
                double time = scanner.nextDouble();
                if (scanner.hasNextDouble()) {
                    double posX = scanner.nextDouble();
                    if (scanner.hasNextDouble()) {
                        double posY = scanner.nextDouble();
                        telemetryLabel.setText(String.format(Locale.US, "t: %.1f, x: %.1f, y: %.1f", time, posX, posY));

                        // Actualiza la posición del círculo en el panel de dibujo [cite: 114]
                        // Se asume que las coordenadas (posX, posY) están dentro de [0, DRAWING_PANE_WIDTH/HEIGHT]
                        // Si posX o posY son mayores que las dimensiones del panel, el círculo podría no ser visible.
                        // No se realiza escalado aquí, se asume mapeo directo.
                        carRepresentation.setCenterX(posX);
                        carRepresentation.setCenterY(posY);

                        // System.out.println(getName() + " actualizado a: t=" + time + ", x=" + posX + ", y=" + posY);
                        return;
                    }
                }
            }
            // Si el formato del mensaje no es el esperado
            System.err.println("CarTracker " + getName() + ": Formato de mensaje incorrecto: " + message);
            telemetryLabel.setText("Error en datos: " + message);
        } catch (Exception e) {
            System.err.println("CarTracker " + getName() + ": Error al procesar mensaje: " + message + " - " + e.getMessage());
            telemetryLabel.setText("Error al procesar: " + message);
        }
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }
}