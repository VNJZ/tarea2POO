#Tarea 2 Programación Orientada a Objetos

Integrantes: Benjamin Arias Vega	202230007-8
	     Joaquin Belmar		202330003-9


##INFORMACION PRINCIPAL

Se desarrollaron las 4 etapas principales, y se menciona que no se realizo la Etapa Extra por tiempo. Ademas, se adjunta la documentación solicitada

Adjunto Link de Repositorio

https://github.com/VNJZ/tarea2POO.git


##ETAPA 5.1 

##DESCRIPCIÓN 

En esta etapa implementa el simulador gráfico básico del patrón publicador-suscriptor usando JavaFX.  
Permite crear publicadores y suscriptores de videos que comparten URLs mediante tópicos comunes.

## Funcionalidades implementadas

- Creación de publicadores de video desde el menú, con ingreso de nombre y tópico.  
- Creación de suscriptores de video desde el menú, con ingreso de nombre y tópico.  
- Visualización de un botón en el suscriptor que muestra el último URL publicado en su tópico.  
- Actualización automática del botón al recibir una nueva publicación.  
- Arquitectura basada en clases `Publisher` y `Subscriber` reutilizadas de la tarea 1.

## Cómo ejecutar

1. Abrir el proyecto en IntelliJ IDEA.  
2. Ejecutar la clase principal `cl.utfsm.tarea2.tarea2poo.Stage1`.  
3. En el menú, usar `Publisher > Video` para crear un publicador.  
4. Usar `Subscriber > Video` para crear un suscriptor con el mismo tópico.  
5. Publicar URLs desde el publicador (no se reproduce video aún).

## Dependencias

- Java 17 o superior.  
- JavaFX configurado en el IDE para soporte gráfico.

##ETAPA 5.2

##JavaFX

Para esta fase de la tarea, hay que tener "JavaFX", por lo que se dejará una copia de la librería en este repositorio, en la carpeta `openjfx-24.0.1_windows-x64_bin-sdk`. Se recomienda dejar esta carpeta en alguna ubicación que prefiera. En nuestro caso, la dejaremos en la carpeta de descargas.

De todas formas, es muy importante que identifique dónde está la carpeta, y donde está la sub carpeta `\javafx-sdk-24.0.1\lib`, ya que aqui están los archivos .jar necesarios de la libería, y esta ubicación es primordial para los siguientes pasos, asi que se recomienda tenerla copiada en algún lugar.

## Ejecución en IntelliJ IDEA

Para ejecutar desde este programa, tan solo hay que abrir la carpeta del repositorio clonado, y configurar el proyecto para que pueda usar JavaFX. Para esto, se deben hacer los siguientes pasos:

### Importar libería

1. Haz click en las opciones del programa, que está en la esquina izquierda superior (al presionar el ícono, se mostrará que esta menú se llama "File").
2. Presionar la opción "Project Sructure".
3. En la ventana, presionar la opción "Libraries".
4. En "Libraries", presionar el ícono "+" para agregar una librería, se debe presionar que ésta es de "Java".
5. Busca o pega la ubicación de la librería ya identificada anteriormente y seleccionala.
6. Una vez que se vea en la lista de librerías la ubicación de "JavaFX", presiona "Apply"

### Cambiar configuración del archivo principal

1. Al lado izquierdo del ícono de Run, se encuentra el archivo se está corriendo (generalmente "Current File"), pulsa el nombre de ese archivo, y selecciona "Edit Configurations...".
2. Si no hay configuraciones creadas, crea una con el boton "Add new...", y seleccionando la opción "Aplication".
3. Selecciona la clase a ejecutar, en este caso, `Stage1`. Se puede añadir textualmente, o se puede buscar desde el mismo espacio de "Main class".
4. Crea las opciones de Virtual Machine. Para esto, debes presionar las teclas "Alt + v".
5. Una vez ya creado el espacio, debes poner lo siguiente como argumento, cambiando el espacio "UBICACION" con la dirección de la libería identificada anteriormente:
```bash
    --module-path "UBICACION" --add-modules javafx.controls,javafx.fxml,javafx.media
```
Es importante poner la ubicación exacta en el equipo de donde se encuentra, y dejar toda esta ubicación con los corchetes " ".
6. Presiona "Apply".

Y ya estará listo para ejercutar el archivo `Stage1` correctamente con JavaFX.


##ETAPA 5.3

## JavaFX

Para esta fase de la tarea, hay que tener "JavaFX", por lo que se dejará una copia de la librería en este repositorio, en la carpeta `openjfx-24.0.1_windows-x64_bin-sdk`. Se recomienda dejar esta carpeta en alguna ubicación que prefiera. En nuestro caso, la dejaremos en la carpeta de descargas.

De todas formas, es muy importante que identifique dónde está la carpeta, y donde está la sub carpeta `\javafx-sdk-24.0.1\lib`, ya que aqui están los archivos .jar necesarios de la libería, y esta ubicación es primordial para los siguientes pasos, asi que se recomienda tenerla copiada en algún lugar.

## Ejecución en IntelliJ IDEA

Para ejecutar desde este programa, tan solo hay que abrir la carpeta del repositorio clonado, y configurar el proyecto para que pueda usar JavaFX. Para esto, se deben hacer los siguientes pasos:

### Importar libería

1. Haz click en las opciones del programa, que está en la esquina izquierda superior (al presionar el ícono, se mostrará que esta menú se llama "File").
2. Presionar la opción "Project Sructure".
3. En la ventana, presionar la opción "Libraries".
4. En "Libraries", presionar el ícono "+" para agregar una librería, se debe presionar que ésta es de "Java".
5. Busca o pega la ubicación de la librería ya identificada anteriormente y seleccionala.
6. Una vez que se vea en la lista de librerías la ubicación de "JavaFX", presiona "Apply"

### Cambiar configuración del archivo principal

1. Al lado izquierdo del ícono de Run, se encuentra el archivo se está corriendo (generalmente "Current File"), pulsa el nombre de ese archivo, y selecciona "Edit Configurations...".
2. Si no hay configuraciones creadas, crea una con el boton "Add new...", y seleccionando la opción "Aplication".
3. Selecciona la clase a ejecutar, en este caso, `Stage3`. Se puede añadir textualmente, o se puede buscar desde el mismo espacio de "Main class".
4. Crea las opciones de Virtual Machine. Para esto, debes presionar las teclas "Alt + v".
5. Una vez ya creado el espacio, debes poner lo siguiente como argumento, cambiando el espacio "UBICACION" con la dirección de la libería identificada anteriormente:
```bash
    --module-path "UBICACION" --add-modules javafx.controls,javafx.fxml,javafx.media
```
Es importante poner la ubicación exacta en el equipo de donde se encuentra, y dejar toda esta ubicación con los corchetes " ".

6. Presiona "Apply".

Y ya estará listo para ejercutar el archivo `Stage3` correctamente con JavaFX.

## Requisito para publicador tipo GPS

Para la etapa que el publicador de coordenadas GPS pueda tener datos de las ubicaciones, debe abrir un archivo txt que tenga el formato:

```bash
  <tiempo> <posición x> <posición y>
```

Puede crearlo ud. donde prefiera, o usar el nuestro que tenemos de ejemplo en la carpeta fuente.


##ETAPA 5.4

## JavaFX

Para esta fase de la tarea, hay que tener "JavaFX", por lo que se dejará una copia de la librería en este repositorio, en la carpeta `openjfx-24.0.1_windows-x64_bin-sdk`. Se recomienda dejar esta carpeta en alguna ubicación que prefiera. En nuestro caso, la dejaremos en la carpeta de descargas.

De todas formas, es muy importante que identifique dónde está la carpeta, y donde está la sub carpeta `\javafx-sdk-24.0.1\lib`, ya que aqui están los archivos .jar necesarios de la libería, y esta ubicación es primordial para los siguientes pasos, asi que se recomienda tenerla copiada en algún lugar.

## Ejecución en IntelliJ IDEA

Para ejecutar desde este programa, tan solo hay que abrir la carpeta del repositorio clonado, y configurar el proyecto para que pueda usar JavaFX. Para esto, se deben hacer los siguientes pasos:

### Importar libería

1. Haz click en las opciones del programa, que está en la esquina izquierda superior (al presionar el ícono, se mostrará que esta menú se llama "File").
2. Presionar la opción "Project Sructure".
3. En la ventana, presionar la opción "Libraries".
4. En "Libraries", presionar el ícono "+" para agregar una librería, se debe presionar que ésta es de "Java".
5. Busca o pega la ubicación de la librería ya identificada anteriormente y seleccionala.
6. Una vez que se vea en la lista de librerías la ubicación de "JavaFX", presiona "Apply"

### Cambiar configuración del archivo principal

1. Al lado izquierdo del ícono de Run, se encuentra el archivo se está corriendo (generalmente "Current File"), pulsa el nombre de ese archivo, y selecciona "Edit Configurations...".
2. Si no hay configuraciones creadas, crea una con el boton "Add new...", y seleccionando la opción "Aplication".
3. Selecciona la clase a ejecutar, en este caso, `PubSubsPatternSimulator`. Se puede añadir textualmente, o se puede buscar desde el mismo espacio de "Main class".
4. Crea las opciones de Virtual Machine. Para esto, debes presionar las teclas "Alt + v".
5. Una vez ya creado el espacio, debes poner lo siguiente como argumento, cambiando el espacio "UBICACION" con la dirección de la libería identificada anteriormente:
```bash
    --module-path "UBICACION" --add-modules javafx.controls,javafx.fxml,javafx.media
```
Es importante poner la ubicación exacta en el equipo de donde se encuentra, y dejar toda esta ubicación con los corchetes " ".

6. Presiona "Apply".

Y ya estará listo para ejercutar el archivo `PubSubsPatternSimulator` correctamente con JavaFX.

## Requisito para publicador tipo GPS

Para la etapa que el publicador de coordenadas GPS pueda tener datos de las ubicaciones, debe abrir un archivo txt que tenga el formato:

```bash
  <tiempo> <posición x> <posición y>
```

Puede crearlo ud. donde prefiera, o usar el nuestro que tenemos de ejemplo en la carpeta fuente.







