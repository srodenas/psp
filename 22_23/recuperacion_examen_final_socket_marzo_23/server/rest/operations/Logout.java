package server.rest.operations;

import java.io.PrintWriter;

import server.UserDataThread;
import server.interfaces.ObjectManagerInterface;
import server.interfaces.RestOperationInterface;

/*
 * VERSIÓN DE Santiago Rodenas Herráiz, para PSP 22-23
 */
public class Logout implements RestOperationInterface{
    /*
     *  @param pw (flujo salida), args (argumentos del comando), context (hilo que atiende al cliente)
     *  @return boolean true (correcto), false(no correcto)
     */
    @Override
    public boolean execute(PrintWriter pw, String[] args, ObjectManagerInterface manager, Thread context) {
        
        if (!((UserDataThread)context).isLogged()){
            pw.println("Acción no permitidq. Debes estar registrado!!");
            pw.flush();
            return false;
        }


        //modificamos el contexto con el login a falso.
        ((UserDataThread)context).setLogged(false);
        ((UserDataThread)context).setExit();
        return true;
    }
    
}
