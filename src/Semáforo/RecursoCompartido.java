package Semáforo;

/**
 * Name: Sergio Fernández Castro
 * Date: 14/12/2023
 */

import java.util.concurrent.Semaphore;
import java.util.Random;

// Clase que representa un recurso compartido con mecanismo de semáforo
public class RecursoCompartido {
    private Semaphore semaforo;          // Semáforo para gestionar el acceso concurrente al recurso
    private int unidadesDisponibles;    // Número de unidades disponibles en el recurso

    // Constructor de la clase RecursoCompartido
    public RecursoCompartido(int k) {
        semaforo = new Semaphore(k, true); 	// Se crea el semáforo con k permisos y con un orden justo (FIFO)
        unidadesDisponibles = k;            // Se inicializa el número de unidades disponibles
    }

    // Método para reservar unidades del recurso
    public void reserva(int r) {
        try {
            semaforo.acquire(r); // Se adquieren r permisos (bloquea si no hay suficientes permisos)
            unidadesDisponibles -= r; // Se actualiza la cantidad de unidades disponibles
            System.out.println(Thread.currentThread().getName() + " reservó " + r + " unidades. Unidades disponibles: " + unidadesDisponibles);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para liberar unidades del recurso
    public void libera(int l) {
        unidadesDisponibles += l; // Se incrementa la cantidad de unidades disponibles
        semaforo.release(l);      // Se liberan l permisos
        System.out.println(Thread.currentThread().getName() + " liberó " + l + " unidades. Unidades disponibles: " + unidadesDisponibles);
    }

    // Método para obtener la cantidad actual de unidades disponibles
    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }
}

// Clase que representa un generador de recursos
class GeneradorRecurso {
    private int totalUnidades; // Número total de unidades que puede tener un recurso

    // Constructor de la clase GeneradorRecurso
    public GeneradorRecurso(int totalUnidades) {
        this.totalUnidades = totalUnidades;
    }

    // Método para generar un recurso compartido con el número total de unidades
    public RecursoCompartido generarRecurso() {
        return new RecursoCompartido(totalUnidades);
    }
}

// Clase que representa un proceso que interactúa con un recurso compartido
class Proceso implements Runnable {
    private RecursoCompartido recurso; // Recurso compartido con el que interactúa el proceso
    private Random random = new Random(); // Generador de números aleatorios

    // Constructor de la clase Proceso
    public Proceso(RecursoCompartido recurso) {
        this.recurso = recurso;
    }

    // Método que se ejecuta cuando se inicia el hilo
    @Override
    public void run() {
        // El proceso continúa mientras haya unidades disponibles en el recurso
        while (recurso.getUnidadesDisponibles() > 0) {
            int unidadesNecesarias = random.nextInt(3) + 1; // Solicitar entre 1 y 3 unidades
            recurso.reserva(unidadesNecesarias); // El proceso reserva unidades

            int unidadesALiberar = random.nextInt(unidadesNecesarias) + 1; // Liberar entre 1 y el número de unidades reservadas
            recurso.libera(unidadesALiberar); // El proceso libera unidades
        }
    }
}


