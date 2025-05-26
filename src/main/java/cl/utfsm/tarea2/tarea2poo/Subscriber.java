package cl.utfsm.tarea2.tarea2poo;

public abstract class Subscriber extends Component {
    protected Topic topicRef;

    public Subscriber(String name, String topic) {
        super(name, topic);
        this.topicRef = Broker.getInstance().getTopic(topic);
        this.topicRef.addSubscriber(this);
    }

    public abstract void receive(String message);
}