package server.rest.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import server.UserDataThread;
import server.interfaces.ManagerObjectInterface;
import server.interfaces.RestOperationInterface;

public class GetHash implements RestOperationInterface{

    /*
     *  @param pw (flujo salida), args (argumentos del comando), context (hilo que atiende al cliente)
     *  @return boolean true (correcto), false(no correcto)
     */
    @Override
    public boolean execute(PrintWriter pw, String[] args, ManagerObjectInterface manager, Thread context) {

        // CertUtil -hashfile file.ext MD5
        if (args.length < 1){
            pw.println("Debes pasar el nombre del usuario");
            pw.flush();
            return false;
        }
        if (!((UserDataThread)context).isLogged()){
            pw.println("Acción no permitidq. Debes estar registrado!!");
            pw.flush();
            return false;
        }

        final String  path="files/";  
        File file = new File(path + args[0] + ".dat");
        final String absolutePathFile = file.getAbsolutePath();
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
