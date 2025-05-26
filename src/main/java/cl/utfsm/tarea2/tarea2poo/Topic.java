package cl.utfsm.tarea2.tarea2poo;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private String name;
    private List<Subscriber> subscribers;

    public Topic(String name) {
        this.name = name;
        this.subscribers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
    }

    public void publish(String message) {
        for (Subscriber s : subscribers) {
            s.receive(message);
        }
    }
}
