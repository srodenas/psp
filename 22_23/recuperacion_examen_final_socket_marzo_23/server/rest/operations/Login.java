package server.rest.operations;

import java.io.PrintWriter;

import server.UserDataThread;
import server.interfaces.ObjectManagerInterface;
import server.interfaces.RestOperationInterface;
import server.logic.User;

/*
 * VERSIÓN DE Santiago Rodenas Herráiz, para PSP 22-23
 */
public class Login implements RestOperationInterface{
   
    /*
     *  @param pw (flujo salida), args (argumentos del comando), context (hilo que atiende al cliente)
     *  @return boolean true (correcto), false(no correcto)
     */
    @Override
    public boolean execute(PrintWriter pw, String[] args, ObjectManagerInterface manager,  Thread context) {

        if (args.length < 2){
            pw.println("Debes pasar el email y passw");
            pw.flush();
            return false;
        }


        User user = (User)manager
                .FindByEmailAndPassw(args[0], args[1]);  //Buscamos por email

        
        if(user == null){
            pw.println("Usuario no encontrado");
            pw.flush();
            return false;
        }
        else{
            pw.println("Usuario logueado correctamente ");
            pw.flush();
            ((UserDataThread)context).setLogged(true);
            return true;
        }
               
        
    }
    
}
