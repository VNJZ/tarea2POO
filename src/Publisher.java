// Interactúa con el 'Broker' para obtener o crear un 'Topic' y luego publica mensajes en ese tópico.
public class Publisher extends Component {
    private Topic topic; // El tópico específico al que este publicador enviará mensajes.
    // El Broker ya no se almacena como miembro, se usa para obtener el topic en el constructor.

    protected Publisher() {}

    public Publisher(String name, Broker broker, String topicName) {
        super(name, topicName);
        this.topic = broker.createTopic(topicName);
    }

    // Publica un nuevo evento (mensaje) en el tópico asociado, llama al método 'notifySubscribers' del tópico.
    public void publishNewEvent(String message) {
        if (this.topic != null) {
            this.topic.notifySubscribers(message.trim());
        } else {
            System.out.println("Error: El publicador " + getName() + " no tiene un tópico asignado para publicar.");
        }
    }
}