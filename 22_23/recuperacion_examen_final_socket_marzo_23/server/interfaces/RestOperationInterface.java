package server.interfaces;
import java.io.PrintWriter;



/*
 * VERSIÓN DE Santiago Rodenas Herráiz, para PSP 22-23
 * 
 * La idea es tener una Interfaz, que defina un método execute con los parámetros siguiente:
 * 
 * PrintWriter pw (Flujo de salida para el intercambio de datos) con el cliente.
 * String[] args (comando que reciba del cliente). 
 * ManagerObjectInterface manager (objeto que implemente las operaciones sobre la lista de genericos).
 * Thread context (Hilo que atiende al cliente).
 * 
 * -------------
 * Esta interfaz, recibe todo lo necesario para llamar al objeto que implemente la operación REST.
 * Si quisiéramos implementar más operaciones REST, sólo necesitamos añadir una nueva clase que implemente de esta
 * interfaz.
 */
/*

    
    
    *****OTRA OPCIÓN MÁS SENCILLA SERÍA HACER ESTO*********
    - Otra opción, habría sido la Interfaz con los métodos rest.
        //registrar un usuario. Almacena un fichero con sus datos.
    public boolean Register(PrintWriter pw, String [] args);

        //loguear un usuario. recibe el email y pass.
    public boolean Login(PrintWriter pw, String [] args, Thread contex); 

        // recibe el email. devolvería los datos de ese usuario. Sólo logueados.
    public boolean UserData(PrintWriter pw, String [] args, Thread contex);  

        // devolvería todos los usuarios. Sólo logueados.
    public boolean UsersList(PrintWriter pw, String [] args, Thread contex);

        // devolvería el hash de ese usuario. Pasamos su nombre. Sólo registrados.
    public boolean GetHash(PrintWriter pw, String [] args, Thread contex);

    public boolean Logout(PrintWriter pw, String [] args, Thread contex);
    
 */

public interface RestOperationInterface {
    /*
     *  @param pw (flujo salida), args (argumentos del comando), context (hilo que atiende al cliente)
     *  @return boolean true (correcto), false(no correcto)
     * 
     */
    public boolean execute(PrintWriter pw, String [] args, ObjectManagerInterface manager, Thread context);
}
