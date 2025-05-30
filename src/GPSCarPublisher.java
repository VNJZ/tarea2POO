import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class GPSCarPublisher extends Publisher {

    private HBox view;
    private Label gpsDataLabel; // Muestra la info de GPS publicada
    private transient Scanner fileScanner; // se deja como transiente por el momento
    private double prevTime, currentTime, nextTime;
    private double prevX, currentX, nextX;
    private double prevY, currentY, nextY;
    private Timeline timeline;
    private boolean firstDataPoint = true;
    private boolean secondDataPointAvailable = false;
    private boolean moreDataInFile = true;


    public GPSCarPublisher(String name, Broker broker, String topicName, File gpsFile) {
        super(name, broker, topicName);
        this.view = new HBox(10);
        Label nameLabel = new Label(name + " (" + topicName + "):");
        this.gpsDataLabel = new Label("Cargando datos GPS...");
        this.view.getChildren().addAll(nameLabel, gpsDataLabel);

        try {
            this.fileScanner = new Scanner(gpsFile);
            this.fileScanner.useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo GPS no encontrado - " + e.getMessage());
            this.gpsDataLabel.setText("Error: Archivo no encontrado.");
            this.moreDataInFile = false;
            return;
        }

        if (readNextPointFromFile()) {
            prevX = nextX; prevY = nextY; prevTime = nextTime;
            currentTime = prevTime;

            if (readNextPointFromFile()) {
                secondDataPointAvailable = true;
            } else {
                gpsDataLabel.setText(String.format(Locale.US, "%.1f %.1f %.1f", prevTime, prevX, prevY));
                publishNewEvent(gpsDataLabel.getText());
                moreDataInFile = false;
            }
        } else {
            moreDataInFile = false;
            gpsDataLabel.setText("Archivo GPS vacío o formato incorrecto.");
        }


        if (moreDataInFile && secondDataPointAvailable) {
            this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> reportPosition()));
            this.timeline.setCycleCount(Timeline.INDEFINITE);
            this.timeline.play();
        } else if (moreDataInFile && !secondDataPointAvailable) {
            String dataString = String.format(Locale.US,"%.1f %.1f %.1f", prevTime, prevX, prevY);
            gpsDataLabel.setText(dataString);
            publishNewEvent(dataString);
            moreDataInFile = false;
            if(fileScanner != null) fileScanner.close();
        } else {
            if(fileScanner != null) fileScanner.close();
        }
    }

    private boolean readNextPointFromFile() {
        if (fileScanner != null && fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            try (Scanner lineScanner = new Scanner(line)) {
                lineScanner.useLocale(Locale.US);
                if (lineScanner.hasNextDouble()) {
                    nextTime = lineScanner.nextDouble();
                    if (lineScanner.hasNextDouble()) {
                        nextX = lineScanner.nextDouble();
                        if (lineScanner.hasNextDouble()) {
                            nextY = lineScanner.nextDouble();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    private void reportPosition() {
        if (!moreDataInFile && currentTime >= nextTime && !secondDataPointAvailable) {
            timeline.stop();
            if (fileScanner != null) fileScanner.close();
            gpsDataLabel.setText(String.format(Locale.US, "%.1f %.1f %.1f (Final)", prevTime, prevX, prevY)); // Publish last known
            publishNewEvent(String.format(Locale.US, "%.1f %.1f %.1f", prevTime, prevX, prevY));
            return;
        }

        if (currentTime > nextTime && secondDataPointAvailable) {
            prevTime = nextTime;
            prevX = nextX;
            prevY = nextY;
            if (readNextPointFromFile()) {
                // El nuevo punto 'next' se lee correctamente
            } else {
                secondDataPointAvailable = false;
            }
        }

        double t_interval = nextTime - prevTime;
        if (t_interval <= 0 && secondDataPointAvailable) {
            currentX = nextX;
            currentY = nextY;
        } else if (!secondDataPointAvailable) {
            currentX = prevX;
            currentY = prevY;
            if (currentTime > prevTime) {
                timeline.stop();
                if (fileScanner != null) fileScanner.close();
                String dataString = String.format(Locale.US,"%.1f %.1f %.1f (Final)", prevTime, prevX, prevY);
                gpsDataLabel.setText(dataString);
                publishNewEvent(String.format(Locale.US,"%.1f %.1f %.1f", prevTime, prevX, prevY));
                return;
            }
        }
        else {
            double ratio = (currentTime - prevTime) / t_interval;
            currentX = prevX + (nextX - prevX) * ratio;
            currentY = prevY + (nextY - prevY) * ratio;
        }

        String dataString = String.format(Locale.US,"%.1f %.1f %.1f", currentTime, currentX, currentY);
        gpsDataLabel.setText(dataString);
        publishNewEvent(dataString);

        currentTime += 1.0;


        if (!secondDataPointAvailable && currentTime > nextTime) {
            timeline.stop();
            if (fileScanner != null) fileScanner.close();
            String finalDataString = String.format(Locale.US,"%.1f %.1f %.1f (Final)", nextTime, nextX, nextY);
            gpsDataLabel.setText(finalDataString);
            publishNewEvent(String.format(Locale.US,"%.1f %.1f %.1f", nextTime, nextX, nextY));
        }
    }

    public HBox getView() {
        return view;
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
        if (fileScanner != null) {
            fileScanner.close();
        }
        System.out.println("GPSCarPublisher " + getName() + " detenido.");
    }
}