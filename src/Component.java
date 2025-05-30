// Clase base para componentes del sistema publicador-suscriptor.
// Basado en la clase Component del profesor.

public class Component {
    protected String name; // ej. "Streamer_1", "Seguidor_1"
    protected String topicName; // Nombre del tópico al que el componente está asociado (ej. "Notificaciones_1")

    // Constructor protegido para evitar la creación de instancias sin nombre o tópico.
    protected Component() {
    }

    public Component(String componentName, String topicName) {
        this.name = componentName;
        this.topicName = topicName;
    }

    public String getName() {
        return name;
    }

    public String getTopicName() {
        return topicName;
    }
}