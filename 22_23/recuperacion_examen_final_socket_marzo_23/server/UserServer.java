package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.interfaces.ManagerObjectInterface;
import server.logic.User;
import server.logic.UserManager;
import server.rest.OperationsManager;

public class UserServer {
    private static OperationsManager operationManager;  //Gestor de operaciones.
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Debes pasar el puerto a escuchar");
            System.exit(1);
        }

        final int port = Integer.parseInt(args[0]);

        //Recurso Compartido de los hilos. Trabaja directamente con la lista de Usuarios.
        final ManagerObjectInterface userManager = new UserManager<User>(); 

        //Gestor operaciones Rest
        operationManager = new OperationsManager(userManager );  

        System.out.println("Servidor a la escucha del puerto  " + port);
        System.out.println("Esperando conexión ......");
    
    
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socketClient = serverSocket.accept();
                System.out.printf("Establecida conexión con %s:%d%n",
                    socketClient.getInetAddress(),
                    socketClient.getPort()
                );
    
                new UserDataThread(socketClient,  operationManager).start();
            }
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
 
}
