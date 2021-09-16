package Entrega1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int puerto = 0; // Número de puerto de escucha para la conexión de clientes
        //Pedimos el puerto de conexión       
        Scanner lecturaTeclado = new Scanner(System.in);
        System.out.print("Introduce el puerto de escucha para el Servidor: ");
        try {
            puerto = Integer.parseInt(lecturaTeclado.nextLine());
        } catch (Exception e) {
            System.out.println("Debe indicar un número " +
                    "de puerto válido que no esté en uso.");
            System.exit(1); //Cerramos aplicación con código de salida 1
        }
    

        if (puerto > 0) {
            //Instanciamos la clase Servidor y le pasamos como parámetro el puerto
            Servidor prepararServidor = new Servidor(puerto);
            //Abriremos hilo (Thread) para que
            //el servidor quede esperando conexiones de múltiples clientes indefinidamente
            prepararServidor.start();
        } else {
            System.out.println("Debe indicar un número " +
                    "de puerto válido que no esté en uso.");
            System.exit(1); //Cerramos aplicación con código de salida 1
        }
    }
}