// Escribe los mensajes recibidos a un PrintStream (archivo de salida).
import java.io.PrintStream;

public class Follower extends Subscriber {
    private PrintStream output; // Flujo de salida para escribir los mensajes (ej. un archivo).

    public Follower(String name, String topic, PrintStream output) {
        super(name, topic); // Llama al constructor de la clase base (Subscriber).
        this.output = output;
    }

    // Implementación del método 'update' de la clase 'Subscriber', cuando el tópico publica un nuevo mensaje.
    @Override
    public void update(String message) {
        // Escribe el mensaje en el formato: nombreDelSeguidor nombreDelTopico mensaje
        output.println(getName() + " " + getTopicName() + " " + message);
    }
}