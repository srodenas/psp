package server.rest.operations;

import java.io.PrintWriter;
import java.util.List;

import server.UserDataThread;
import server.interfaces.ManagerObjectInterface;
import server.interfaces.RestOperationInterface;
import server.logic.User;

public class UsersList implements RestOperationInterface {

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

        List <User> list = manager.GetAll();

        String msg="";
        for (User user : list )
            msg+="Id: " + user.getId()
                + " Nombre: " + user.getName()
                + " Email: " + user.getEmail()
                + " Passw: " + user.getPasswd()
                + "\n";
       
        
       
        pw.println(msg);
        pw.flush();
        return true;
    }
    
}
