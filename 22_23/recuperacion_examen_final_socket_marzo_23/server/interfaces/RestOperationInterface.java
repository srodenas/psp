package server.interfaces;
import java.io.PrintWriter;



/*
    - Estos son los métodos que pretendemos emular a partir
    de clases que implementen el método execute.
    - El gestor que lleve a cabo las operaciones rest, deberá implementar
    clases que sobreescriban el método execute.
    - La idea es hacer clases que representen una operación rest. 
    
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
    public boolean execute(PrintWriter pw, String [] args, ManagerObjectInterface manager, Thread context);
}
