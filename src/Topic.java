import java.util.ArrayList;
import java.util.List;

// Representa un tópico o canal de comunicación en el sistema publicador-suscriptor.
// Cada tópico tiene un nombre y mantiene una lista de suscriptores interesados en los mensajes de este tópico.

public class Topic {
    private String topicName; // Nombre único que identifica al tópico.
    private List<Subscriber> subscribers; // Lista de suscriptores de este tópico.

    // Constructor protegido para evitar la creación de instancias sin nombre o tópico.
    protected Topic() {}

    public Topic(String topicName) {
        this.topicName = topicName;
        this.subscribers = new ArrayList<>();
    }

    // Agrega un suscriptor a la lista de suscriptores de este tópico.
    // "sub" El suscriptor que desea recibir mensajes de este tópico.

    public void subscribe(Subscriber sub) {
        subscribers.add(sub);
    }

    // Envía un mensaje a todos los suscriptores de este tópico.
    // Itera sobre la lista de suscriptores y llama al método 'update' de cada uno.
    public void notifySubscribers(String message) {
        for (Subscriber sub : subscribers) {
            sub.update(message); // Llama al método update del suscriptor
        }
    }

    // Verifica si el nombre de este tópico coincide con el nombre proporcionado.
    public boolean hasThisName(String name) {
        return topicName.equals(name);
    }

    public String getTopicName() {
        return topicName;
    }
}