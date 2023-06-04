package server.rest.operations;

import java.io.PrintWriter;

import server.UserDataThread;
import server.interfaces.ObjectManagerInterface;
import server.interfaces.RestOperationInterface;
import server.logic.User;

/*
 * VERSIÓN DE Santiago Rodenas Herráiz, para PSP 22-23
 */
public class UserData implements RestOperationInterface{

    /*
     *  @param pw (flujo salida), args (argumentos del comando), context (hilo que atiende al cliente)
     *  @return boolean true (correcto), false(no correcto)
     */
    @Override
    public boolean execute(PrintWriter pw, String[] args, ObjectManagerInterface manager, Thread context) {

        if (args.length < 1){
            pw.println("Debes pasar el email");
            pw.flush();
            return false;
        }
/*
 * Sólo se puede invocar si está registrado.
 */
        if (!((UserDataThread)context).isLogged()){
            pw.println("Acción no permitidq. Debes estar registrado!!");
            pw.flush();
            return false;
        }

        User u = (User)manager
        .FindByEmail(args[0]);  //Buscamos por email

        if (u == null){
            pw.println("Ese usuario no exixte ");
          
        }
        else
            pw.println("Datos del usuario: " + u.toString());
        pw.flush();
        return true;
    }
    
}
