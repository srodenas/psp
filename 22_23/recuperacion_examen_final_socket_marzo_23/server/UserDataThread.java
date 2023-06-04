package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import server.rest.RestOperationsManager;

/*
 * VERSIÓN DE Santiago Rodenas Herráiz, para PSP 22-23
 * 
 * El hilo se encargará de invocar a los servicios que pida el cliente.
 * Al crearse el hilo, se le pasa el recurso compartido a partir de Administrador
 * de operaciones Rest. Recordamos que dicho objeto, TIENE ACCESO AL RECURSO COMPARTIDO UserManager.
 */
public class UserDataThread extends Thread{

    private Socket socket;
    private PrintWriter pw;
    private Scanner sc;
    private RestOperationsManager operationManager;
    private boolean logged  = false;
    private boolean exit = false;

    public UserDataThread (Socket socket, RestOperationsManager operManager){
        this.socket = socket;
        this.operationManager = operManager;
    }

    @Override
    public void run() {
        try{
           
            pw = new PrintWriter(this.socket.getOutputStream());
            sc = new Scanner(this.socket.getInputStream());
            System.out.println("Esperando respuesta cliente");

            while (this.sc.hasNextLine()) {
              String line = this.sc.nextLine();
              InetAddress address = this.socket.getInetAddress();
              System.out.printf("Recibida conexión desde %s:%d: %s%n", address.getHostAddress(), socket.getPort(), line);
      
              //Ejecutamos el comando recibido del cliente.  
              this.operationManager.execute(pw, line, this);
              if (isExit()){
                socket.close();
                pw.close();
                sc.close();
                System.exit(0);
              }
             
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Error inesperado de E/S por el servidor");
            }
        }
         
    }

    /*
     * Método que setea cuando se loguea el usuario.
     */
    public boolean isLogged() {
        return this.logged;
    }

    /*
     * Método que devuelve si un usuario está logueado o no.
     */
    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isExit(){
        return this.exit;
    }

    public void setExit(){
        this.exit = true;
    }
}