public abstract class Subscriber extends Component {
    protected Subscriber(String name, String topicName) {
        super(name, topicName); // Llama al constructor de la clase padre 'Component'.
    }

    public abstract void update(String message);
    // El método receiveMessage(String message) es reemplazado por update(String message).
}