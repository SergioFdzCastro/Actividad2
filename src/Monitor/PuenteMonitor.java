package Monitor;

import java.util.Random;

/**
 * Name: Sergio Fernández Castro
 * Date: 14/12/2023
 */


// Clase que representa un puente con un monitor para gestionar el tráfico
class Puente {
    private int cochesEnElPuente; // Contador de coches en el puente

    // Constructor de la clase Puente
    public Puente() {
        cochesEnElPuente = 0; 	// Inicialización del contador de coches
    }

    // Método sincronizado para que los coches del Norte entren al puente
    public synchronized void entrarDesdeElNorte() throws InterruptedException {
        while (cochesEnElPuente > 0) {
            wait(); 		// Espera hasta que no haya coches en el puente
        }
        cochesEnElPuente++; // Incrementa el contador de coches en el puente
    }

    // Método sincronizado para que los coches del Norte salgan del puente
    public synchronized void salirHaciaElNorte() {
        cochesEnElPuente--; // Decrementa el contador de coches en el puente
        notifyAll(); 		// Notifica a todos los coches que están esperando
    }

    // Método sincronizado para que los coches del Sur entren al puente
    public synchronized void entrarDesdeElSur() throws InterruptedException {
        while (cochesEnElPuente > 0) {
            wait(); 		// Espera hasta que no haya coches en el puente
        }
        cochesEnElPuente++; // Incrementa el contador de coches en el puente
    }

    // Método sincronizado para que los coches del Sur salgan del puente
    public synchronized void salirHaciaElSur() {
        cochesEnElPuente--; // Decrementa el contador de coches en el puente
        notifyAll(); 		// Notifica a todos los coches que están esperando
    }
}

// Clase que representa un coche que cruza el puente
class Coche implements Runnable {
    private String direccion; // Dirección del coche (Norte o Sur)
    private Puente puente;    // Puente que el coche cruzará

    // Constructor de la clase Coche
    public Coche(String direccion, Puente puente) {
        this.direccion = direccion; // Inicialización de la dirección del coche
        this.puente = puente;       // Asignación del puente al coche
    }

    // Método que se ejecuta cuando se inicia el hilo del coche
    @Override
    public void run() {
        try {
            Random random = new Random();
            int esperaAleatoria = random.nextInt(5000); // Tiempo de espera aleatorio antes de llegar al puente
            Thread.sleep(esperaAleatoria);

            if (direccion.equals("Norte")) {
                puente.entrarDesdeElNorte(); 	// El coche del Norte intenta entrar al puente
                System.out.println("Coche del Norte cruzando el puente.");
                Thread.sleep(1000); 			// Tiempo de simulación de cruzar el puente
                System.out.println("Coche del Norte ha cruzado el puente.");
                puente.salirHaciaElNorte(); 	// El coche del Norte sale del puente
            } else if (direccion.equals("Sur")) {
                puente.entrarDesdeElSur(); 		// El coche del Sur intenta entrar al puente
                System.out.println("Coche del Sur cruzando el puente.");
                Thread.sleep(1000); 			// Tiempo de simulación de cruzar el puente
                System.out.println("Coche del Sur ha cruzado el puente.");
                puente.salirHaciaElSur(); 		// El coche del Sur sale del puente
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
