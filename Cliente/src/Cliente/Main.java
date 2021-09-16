package Cliente;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int puerto = 5000; //Número de puerto para conexión con servidor
        String servidor = "localhost"; //IP o nombre DNS del servidor al que nos conectaremos
        String mensaje = ""; //Numero de cuenta y valor a retirar
        DataInputStream canalEntradaDatos;
        String msjServidor = "";
        
        
        
        
        Scanner lecturaTeclado = new Scanner(System.in);        
        //Pedimos cuenta y valor a retirar
        System.out.print("Introduce la cuenta y el valor a enviar separado por coma: ");
        mensaje = lecturaTeclado.nextLine();
       
        try {
            System.out.println(FechaActual() + " Conectando con servidor " + servidor + " por puerto: " + puerto + "...");
            //Instanciamos clase Socket con servidor y puerto especificados
            Socket clientSocket = new Socket(servidor, puerto);                

            //Establecemos el canal de comunicación
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            //Enviamos el mensaje de texto al servidor
            System.out.println(FechaActual() + " Enviando cuenta y valor...");
            outputStream.writeUTF(mensaje);
            
            
            canalEntradaDatos = new DataInputStream(clientSocket.getInputStream());
            //Leemos mensaje del cliente
            msjServidor = canalEntradaDatos.readUTF();
            System.out.println(msjServidor);

            //Cerramos la conexión con el servidor
            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.out.println("Servidor no encontrado: " + ex.getMessage());
            System.exit(1); //Salimos del programa con código de salida 1
        } catch (IOException ex) {
            System.out.println("Error al conectar al servidor: " + ex.getMessage());
            System.exit(2); //Salimos del programa con código de salida 2
        }
        
    }

    /**
     * Función que devuelve la fecha y hora actuales con formato año-mes-dia hora:minuto:segundo:milisegundo
     */
    public static String FechaActual()
    {
        Date fechaHoraActual = new Date();
        String fechaFormateada =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(fechaHoraActual);
        return fechaFormateada;
    }
}