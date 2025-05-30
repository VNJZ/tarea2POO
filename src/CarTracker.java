import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.Scanner;

public class CarTracker extends Subscriber {
    private Stage stage;
    private Label telemetryLabel;

    public CarTracker(String name, String topicName) {
        super(name, topicName);
        stage = new Stage();
        stage.setTitle("Car Tracker: " + name + ", Topic: " + topicName);

        telemetryLabel = new Label("Esperando datos de posición...");
        telemetryLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // carRepresentation = new Circle(10, Color.RED); // Para usar despues

        BorderPane rootPane = new BorderPane();
        rootPane.setBottom(telemetryLabel);
        BorderPane.setAlignment(telemetryLabel, Pos.CENTER);

        // Para usar en 5.4
        // Pane drawingPane = new Pane();
        // drawingPane.getChildren().add(carRepresentation);
        // rootPane.setCenter(drawingPane); // Example for 5.4

        Scene scene = new Scene(rootPane, 400, 300); // Default size for the tracker window
        stage.setScene(scene);
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

                        // Para usar en 5.4
                        // carRepresentation.setCenterX(posX);
                        // carRepresentation.setCenterY(posY);
                        // System.out.println(getName() + " updated to: t=" + time + ", x=" + posX + ", y=" + posY);
                        return;
                    }
                }
            }
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