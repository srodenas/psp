package server.rest.operations;

import java.io.PrintWriter;

import server.interfaces.ManagerObjectInterface;
import server.interfaces.RestOperationInterface;
import server.UserDataThread;

public class Logout implements RestOperationInterface{
    /*
     *  @param pw (flujo salida), args (argumentos del comando), context (hilo que atiende al cliente)
     *  @return boolean true (correcto), false(no correcto)
     */
    @Override
    public boolean execute(PrintWriter pw, String[] args, ManagerObjectInterface manager, Thread context) {
        
        if (!((UserDataThread)context).isLogged()){
            pw.println("Acción no permitidq. Debes estar registrado!!");
            pw.flush();
            return false;
        }


        ((UserDataThread)context).setLogged(false);
        ((UserDataThread)context).setExit();
        return true;
    }
    
}
