# Implementación de round robin en Java

## Clases
Existen 5 clases principales en el proyecto:
- Proceso: La definición de cada proceso que crea el Generador, se usa para crear hilos con el tamaño y tiempo que se indiquen.
- Sistema: La clase principal, en la cual está contenido el main. En ésta se inician los hilos principales y se definen todos los datos, simula tener los recursos de la computadora.
- Generador: Genera hilos (simulan ser procesos), dandoles un tiempo y tamaño aleatorios y los coloca en la cola de espera.
- Administrador: Pasa los hilos de la cola de espera a la cola de listos si hay espacio en memoria para ello.
- Ejecutador: Ejecuta los hilos que se encuentran en la cola de espera durante un quantum de tiempo, para despues (en el caso de que aún no acaben) regresarlos al final de la cola de listos.

## Funcionamiento
El metodo main contenido en la clase Sistema define las colas de listos y en espera, usando la estructura de una lista ligada e implementando la interfaz de Queue, también define el tamaño de la memoria y la cantidad de memoria que se está utilizando, además de crear los hilos Generador, Administrador y Ejecutador, pasandoles como atributos (recursos compartidos) los recursos que necesitan para realizar sus operaciones.

Dentro de la clase Generador se crean hilos (procesos simulados) con valores de tiempo y tamaño aleatorios cada cierto tiempo también aleatorio, y al crearlos los agrega a la cola de espera.

Despues de generar los hilos, en la clase Administrador se revisa si la memoria tiene espacio para el siguiente trabajo, si es así, entonces se agrega a la cola de listos, si no, se espera a que haya espacio para meter el hilo a la cola.

Despues de agregar los hilos a la cola, la clase Ejecutador se encarga de ejecutar durante el tiempo especificado(quantum) el primer proceso, ésto mediante una llamada a sleep(quantum) seguida de restar el quantum al tiempo del hilo, posteriormente se revisa si el hilo aún tiene tiempo, si tiene tiempo entonces se saca de la cola de listos y se mete en la misma, quedando como el ultimo proceso, si ya no tiene tiempo solo se saca de la cola y se libera la memoria que ocupaba.

## Interfaz grafica
La clase Ventana se encarga de la interfaz grafica del proyecto, se instancia en la clase Sistema y es pasada a todas las otras clases para modificar la lista de procesos que se encuentran listos para ejecutarse, al agregarse un hilo a la cola de listos, también se agrega un bloque a la cola de bloques, y al terminar el quantum de tiempo de ejecución, si el tiempo terminó entonces se quita el bloque de la pantalla y de la cola de bloques, si no ha terminado se saca el bloque de la cola y se mete al final.

En cada cambio de bloque, ya sea que un proceso termina o que se va al final de la cola, se cambia el color del bloque que esté en la cabeza de la lista, el cual representa el proceso que está ejecutandose actualmente.
