package server.rest.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import server.UserDataThread;
import server.interfaces.ObjectManagerInterface;
import server.interfaces.RestOperationInterface;
/*
 * VERSIÓN DE Santiago Rodenas Herráiz, para PSP 22-23
 * 
 * Esta clase, crea un proceso que devolverá el hash de un fichero
 * previamente existente.
 */
public class GetHash implements RestOperationInterface{

    /*
     *  @param pw (flujo salida), args (argumentos del comando), context (hilo que atiende al cliente)
     *  @return boolean true (correcto), false(no correcto)
     */
    @Override
    public boolean execute(PrintWriter pw, String[] args, ObjectManagerInterface manager, Thread context) {

        // CertUtil -hashfile file.ext MD5
        if (args.length < 1){
            pw.println("Debes pasar el nombre del usuario");
            pw.flush();
            return false;
        }

        /*
         * Este servicio, sólo se puede invocar si el usuario está logueado
         * Esto lo controlamos mediante el contexto.
         */
        if (!((UserDataThread)context).isLogged()){
            pw.println("Acción no permitidq. Debes estar registrado!!");
            pw.flush();
            return false;
        }

        final String  path="files/";  
        File file = new File(path + args[0] + ".dat");
        final String absolutePathFile = file.getAbsolutePath();

        /*
         * Si el fichero existe y no es un Directorio, ejecuta el comando
         * que devuelve el hash de ese fichero.
         */
        if (file.exists() && !file.isDirectory()){
            final String [] cmd = {"CertUtil", "-hashfile", absolutePathFile, "MD5"};
           
            String  msg = "";
            ProcessBuilder pb = new ProcessBuilder(cmd);
    
            try{
                Process p = pb.start();
                
                try{
                    int codRet = p.waitFor();
                    InputStream is = p.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String lineReciber = "";
                    while ( (lineReciber = br.readLine()) != null){
                        msg+=lineReciber;
                    }
                } catch(InterruptedException ex) {
                    pw.println("Error inesperado. Finalización interrumpida");
                    pw.flush();
                    return false;
                }
            }
           
            catch (IOException e){
                e.printStackTrace();
                pw.println("Error de E/S.");
                pw.flush();
                return false;
            }
    
            String [] hash = msg.split(":");
            String [] hash1 = hash[2].split("CertUtil");
            pw.println("Codigo Hash MD5 " + hash1[0]);
            pw.flush();
            return true;
        }
        pw.println("No se ha encontrado el fichero");
        pw.flush();
        return false;
       
    }

   
    
}
