package cl.utfsm.tarea2.tarea2poo;

public abstract class Component {
    protected String name;
    protected String topic;

    public Component(String name, String topic){
        this.name = name;
        this.topic = topic;
    }

    public String getName(){
        return name;
    }

    public String getTopic(){
        return topic;
    }

    public void setTopic(String topic){
        this.topic = topic;
    }
}

