package Monitor;

/**
 * Name: Sergio Fernández Castro
 * Date: 14/12/2023
 */

public class Main {
	
	public static void main(String[] args) {
        Puente puente = new Puente();

        Thread[] cochesNorte = new Thread[5];
        Thread[] cochesSur = new Thread[5];

        for (int i = 0; i < 5; i++) {
            cochesNorte[i] = new Thread(new Coche("Norte", puente));
            cochesSur[i] = new Thread(new Coche("Sur", puente));
        }

        for (int i = 0; i < 5; i++) {
            cochesNorte[i].start();
            cochesSur[i].start();
        }
    }
}


