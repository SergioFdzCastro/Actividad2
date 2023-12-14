package Semáforo;

/**
 * Name: Sergio Fernández Castro
 * Date: 14/12/2023
 */

public class Main {
	 public static void main(String[] args) {
	        GeneradorRecurso generador = new GeneradorRecurso(10); // Se crea un generador de recursos con 10 unidades disponibles
	        RecursoCompartido recurso = generador.generarRecurso(); // Se genera un recurso compartido

	        int n = 2; // Número de procesos
	        Thread[] threads = new Thread[n];

	        // Se inician y ejecutan los hilos (procesos)
	        for (int i = 0; i < n; i++) {
	            threads[i] = new Thread(new Proceso(recurso), "Proceso " + i);
	            threads[i].start();
	        }

	        // Espera a que todos los hilos terminen antes de continuar
	        try {
	            for (Thread thread : threads) {
	                thread.join();
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}

