package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.interfaces.ObjectManagerInterface;
import server.logic.UserManager;
import server.rest.RestOperationsManager;

/*
 * VERSIÓN DE Santiago Rodenas Herráiz, para PSP 22-23
 * 
 * Creará un hilo por petición de cliente.
 * Existirá un único objeto que compartirán todos los hilos, que será el administrador
 * de operaciones Rest. Dicho objeto, creará el recurso compartido que son la lista de
 * Usuarios y sus operaciones. 
 */


public class UserServer {
    //Sólo tendremos un único objeto admnistrador de operaciones Rest. 
    private static RestOperationsManager restOperationsManager;  //Gestor de operaciones.
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Debes pasar el puerto a escuchar");
            System.exit(1);
        }

        final int port = Integer.parseInt(args[0]);

        //Recurso Compartido de los hilos. Trabaja directamente con la lista de Usuarios.
        final ObjectManagerInterface userManager; 

        //Gestor operaciones Rest. Este objeto es el principal y creará el recurso compartido.
        restOperationsManager = new RestOperationsManager(new UserManager<>());  

        System.out.println("Servidor a la escucha del puerto  " + port);
        System.out.println("Esperando conexión ......");
    
    
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socketClient = serverSocket.accept();
                System.out.printf("Establecida conexión con %s:%d%n",
                    socketClient.getInetAddress(),
                    socketClient.getPort()
                );
    
                //Creamos el hilo pasándole el administrador de servicios Rest
                new UserDataThread(socketClient,  restOperationsManager).start();
            }
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
 
}
