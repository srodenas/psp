package server.rest;

import java.io.PrintWriter;
import java.util.HashMap;

import server.UserDataThread;
import server.interfaces.ObjectManagerInterface;
import server.interfaces.RestOperationInterface;
import server.rest.operations.GetHash;
import server.rest.operations.Login;
import server.rest.operations.Logout;
import server.rest.operations.Register;
import server.rest.operations.UserData;
import server.rest.operations.UsersList;


/*
 *  VERSIÓN DE Santiago Rodenas Herráiz, para PSP 22-23
 * 
 * CLASE GESTOR DE OPERACIONES REST
 * 'Esta clase es la gestora de todas las operaciones rest. Contendrá cada uno de
 * los objetos que represente una de las operaciones REST.'
 * Se encargará de llamar a las diferentes operaciones mediante objetos que implementen el
 * servicio a emular.
 * 
 * Los objetos de operaciones Rest, los contiene dentro de un HashMap, cuya clave será el Verbo.
 * 
 * Esta clase contiene un único método llamado execute, que se encargará de seleccionar el objeto
 * que emulará el servicio REST, dependiendo del verbo. Para llevarlo a cabo, necesita tener acceso al recurso compartido,
 * y el contexto del hilo que invoca a este objeto.
 */

public class RestOperationsManager {
    
    private final HashMap<String, RestOperationInterface> operationsMap;  //Contendrá los objetos operacion a ejecutar.

    private ObjectManagerInterface managerObject; //Recurso compartido por todos los hilos de ejecución.



/*
 * Crea cada uno de los objetos que implementen los servicios.
 * Por cada servicio, un objeto.
 */
    public RestOperationsManager(ObjectManagerInterface manager){
        operationsMap = new HashMap<>();
        operationsMap.put("reg" , new Register());
        operationsMap.put("log", new Login());
        operationsMap.put("datu", new UserData());
        operationsMap.put("list", new UsersList());
        operationsMap.put("hash5", new GetHash());
        operationsMap.put("fin", new Logout());
        this.managerObject = manager;
        
      
    }




    /*
     * Dependiendo de la línea recibida por el Cliente, deberá sacar el verbo
     * y los argumentos en un array. Despues llamar a la operación solicitada.
     * PrintWriter pw (el flujo de salida)
     * String line (la línea recibida con el vervo y los argumentos)
     * UserDataThread (Es el contexto o el hilo que invoca a este this)
     */
    public boolean execute(PrintWriter pw, String line, UserDataThread context){
        String [] args = line.split(" ");  //separamos en líneas
        
        try{
            String verb = args[0]; //extraemos el verbo del servicio.
            RestOperationInterface operation = operationsMap.get(verb);  //Seleccionamos el Servicio

            /*
             * Comprobamos si existe un servicio invocado, en caso contrario informamos.
             */
            if (operation == null){
                pw.println("Error, debes pasar un comando válido");
                pw.flush();
                return false;
            }

            /*
             * Separamos tanto el verbo (servicio), como sus argumentos.
             */
            String [] operationsArgs = new String[args.length - 1];  //copiamos sólo argumentos.
            System.arraycopy(args, 1,  operationsArgs, 0, args.length - 1);

            /*
             * Invocamos al objeto cuyo verbo corresponde con el servicio, pasándole tanto el flujo de salida,
             * como los parámetros, el recurso compartido y el contexto de quien invoca el servicio.
             * Devolvemos true/false, dependiendo de si se ha obtenido un resultado OK.
             * Podríamos haberlo complicado algo más, con objetos de código 400 o de código 200.
             */
            return(operation.execute(pw, operationsArgs, getManagerObject(), context));


        }catch(ArrayIndexOutOfBoundsException e){
            pw.println("Error, debes pasar la acción");
            pw.flush();
            return false;
        }
       
    }

    private ObjectManagerInterface getManagerObject() {
        return managerObject;
    }
}
