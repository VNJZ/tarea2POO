package cl.utfsm.tarea2.tarea2poo;

public abstract class Publisher extends Component {
    protected Topic topicRef;

    public Publisher(String name, String topic) {
        super(name, topic);
        this.topicRef = Broker.getInstance().getTopic(topic);
    }

    public void publish(String message) {
        topicRef.publish(message);
    }
}
