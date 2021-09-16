package Entrega1;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.FileWriter;

/**
 * Clase Servidor que iniciará la escucha por un
 * puerto y recibirá los mensajes de los clientes.
 * Se extenderá a Thread para permitir programación concurrente
 * De forma que el servidor permita la conexión de múltiples clientes
 */
public class Servidor extends Thread {
    private int puerto; // Número de puerto de escucha para la conexión de clientes
    private DataInputStream canalEntradaDatos; //Canal de entrada de datos de clientes
    private DataOutputStream canalSalidaDatos;
    private String pathFile =  "cuentas/cuentas.txt";//Archivo de cuentas
    private int cuenta = 0;
    private int valor = 0;

    /**
     * Constructor
     * @param puerto: Número de puerto de escucha para la conexión de clientes por Socket
     */
    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    //Sobreescribimos método run para que el servidor se inicie y permita
    //conexiones de múltiples clientes (un hilo por cada cliente)
    @Override
    public void run() {
        try {
            //Creamos instancia de ServerSocket para
            //atender peticiones de clientes por el puerto indicado
            ServerSocket socketServidor = new ServerSocket(this.puerto);
            System.out.println(FechaActual() + " Servidor esperando " +
                    "peticiones de clientes por puerto " + puerto + "...");
            //Dejaremos el servidor siempre escuchando
            //Hasta que se reciba el mensaje: [[fin]]
            String mensajeCliente = "";
            while(!mensajeCliente.equalsIgnoreCase("[[fin]]")) {
                // Esperamos a que se conecte un cliente y aceptamos la conexión
                Socket socketCliente = socketServidor.accept();
                System.out.println(FechaActual() +
                        " Cliente conectado: " + socketCliente.getInetAddress());
                //Estableceremos canal de comunicación con cliente
                canalEntradaDatos = new DataInputStream(socketCliente.getInputStream());
                //Leemos mensaje del cliente
                mensajeCliente = canalEntradaDatos.readUTF();
                //Lo mostramos por consola                                
                System.out.println(FechaActual() +
                        " * Cuenta y valor recibido del cliente: " + mensajeCliente);
                System.out.println("Guardando informacion en archivo cuentas.txt");
                
                canalSalidaDatos = new DataOutputStream(socketCliente.getOutputStream());
                //Validamos cuenta y valor
                String[] datos = mensajeCliente.split(",");
                try {
                	cuenta = Integer.parseInt(datos[0]);
                	valor = Integer.parseInt(datos[1]);
                	
                	//Guardamos la cuenta
                    File file = new File(pathFile);
                    if(!file.exists()) {
                    	file.createNewFile();
                    }
                    FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("Cuenta:"+ Integer.toString(cuenta)+"\n");
                    bw.write("Valor:"+ Integer.toString(valor)+"\n\n");
                    bw.close();
                    System.out.println("Cuenta guardada");
                    canalSalidaDatos.writeUTF("OK");
				} catch (Exception e) {		
					//Mesaje de error
					canalSalidaDatos.writeUTF("NO-OK");
					System.out.println("Fomato incorrrecto");					
				}                                                              
            }
        } catch (IOException e) {
            System.out.println(FechaActual() + " Error al abrir puerto " + puerto +
                    " para escucha de servidor: " + e.getMessage());
            System.exit(2); //Cerramos aplicación con código de salida 2
        }
    }

    /**
     * Función que devuelve la fecha y hora actuales con formato año-mes-dia hora:minuto:segundo:milisegundo
     */
    public String FechaActual()
    {
        Date fechaHoraActual = new Date();
        String fechaFormateada =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(fechaHoraActual);
        return fechaFormateada;
    }
}