package cl.utfsm.tarea2.tarea2poo;

import java.util.HashMap;
import java.util.Map;

public class Broker {
    private static Broker instance = null;
    private Map<String, Topic> topics;

    private Broker() {
        topics = new HashMap<>();
    }

    public static Broker getInstance() {
        if (instance == null) {
            instance = new Broker();
        }
        return instance;
    }

    public Topic getTopic(String name) {
        return topics.computeIfAbsent(name, Topic::new);
    }
}
