module cl.utfsm.tarea2.tarea2poo {
    requires javafx.controls;
    requires javafx.fxml;


    opens cl.utfsm.tarea2.tarea2poo to javafx.fxml;
    exports cl.utfsm.tarea2.tarea2poo;
}