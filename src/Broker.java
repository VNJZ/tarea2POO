import java.util.ArrayList;
import java.util.List;

// Broker es intermediario entre "publicacdores" y "suscriptores", crea los tópicos y la suscripción de componentes a estos tópicos

public class Broker {
    private List<Topic> topics; // Lista de tópicos gestionados por el broker.
    // Inicializa la lista de tópicos.
    public Broker() {
        this.topics = new ArrayList<>();
    }

    // Busca un tópico existente por su nombre.
    // "topicName" es el nombre del tópico a buscar
    private Topic findTopic(String topicName) {
        for (Topic t : topics) {
            if (t.hasThisName(topicName)) {
                return t;
            }
        }
        return null; // Retorna null si el tópico no se encuentra.
    }

    // Crea un nuevo tópico o devuelve uno existente si ya ha sido creado con el mismo nombre.
    public Topic createTopic(String topicName) {
        Topic existingTopic = findTopic(topicName);
        if (existingTopic == null) {
            // Si el tópico no existe, crea uno nuevo y lo añade a la lista.
            existingTopic = new Topic(topicName);
            topics.add(existingTopic);
        }
        return existingTopic; // Devuelve el tópico (ya sea nuevo o el que ya existía).
    }

    // Suscribe un componente (suscriptor) a un tópico específico.
    // El tópico debe existir (haber sido creado previamente por un publicador).
    public boolean subscribe(Subscriber sub) {
        String topicName = sub.getTopicName(); // Obtiene el nombre del tópico del suscriptor.
        Topic targetTopic = findTopic(topicName);
        if (targetTopic != null) { // Si el tópico existe, suscribe al componente.
            targetTopic.subscribe(sub);
            return true; // Suscripción exitosa.
        } else {
            System.out.println("Error: Tópico '" + topicName + "' no existe. Suscripción fallida para " + sub.getName());
            return false; // Suscripción fallida.
        }
    }
    // "publish" ya no se usa, ya que ahora se publica mediante el objeto "topic"
}