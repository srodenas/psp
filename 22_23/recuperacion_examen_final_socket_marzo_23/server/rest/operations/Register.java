package server.rest.operations;

import java.io.FileWriter;
import java.io.PrintWriter;

import server.UserDataThread;
import server.interfaces.ManagerObjectInterface;
import server.interfaces.RestOperationInterface;
import server.logic.User;

public class Register implements RestOperationInterface{

   
    /*
     * Registra los datos recibidos como parámetro.
     */
    /*
     *  @param pw (flujo salida), args (argumentos del comando), context (hilo que atiende al cliente)
     *  @return boolean true (correcto), false(no correcto)
     */

    @Override
    public boolean execute(PrintWriter pw, String[] args, ManagerObjectInterface manager, Thread context) {
        /*
        Deberá comprar si existe ese usuario y en caso negativo, insertarlo.
        Debe comprobar que el número de argumentos sea al menos:
        nombre, email, passwd
        */

        if (args.length < 3){
            pw.println("Debes pasar el nombre, email y passw");
            pw.flush();
            return false;
        }

        User u = (User)manager
                .FindByEmail(args[1]);  //Buscamos por email

        if (u != null){
            pw.println("Ese usuario ya está registrado ");
            pw.flush();
            return false;
        }

        manager.Add(new User(args));  //registramos el usuario   

        try{
            FileWriter userFile = new FileWriter("files/" + args[0]+".dat"); 
            PrintWriter pwf = new PrintWriter(userFile);
            String info = "Nombre: " +
                args[0] + ", Email: " +
                args[1] + ", Passwd: " +
                args[2];

            pwf.println("Datos del usuario " + info);  //escribimos la información en fichero
            pwf.close();
        }catch (Exception e){
            pw.println("Error al crear fichero del usuario en registro");
            pw.flush();
            return false;
        }

        pw.println ("Usuario registrado correctamente...");
        pw.flush();
        ((UserDataThread)context).setLogged(true); //No tengo porqué loguearme después
      
        return true;
        
    }
    
}
