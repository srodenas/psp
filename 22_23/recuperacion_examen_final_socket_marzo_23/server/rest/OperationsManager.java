package server.rest;

import java.io.PrintWriter;
import java.util.HashMap;

import server.UserDataThread;
import server.interfaces.ManagerObjectInterface;
import server.interfaces.RestOperationInterface;
import server.rest.operations.GetHash;
import server.rest.operations.Login;
import server.rest.operations.Logout;
import server.rest.operations.Register;
import server.rest.operations.UserData;
import server.rest.operations.UsersList;

/*  CLASE GESTOR DE OPERACIONES REST

 * - Esta clase, se encarga de llamar a las diferentes
 * operaciones, dependiendo del verbo y pasándole los
 * argumentos.
 * 
 * - Introduciremos cada objeto operación, dentro de una colección
 * y llamaremos según el verbo de tipo String.
 * 
 * 
 * - Necesita el recurso compartido UserManager que hereda de la interfaz managerObjectInterface.
 */
public class OperationsManager {
    /**
     *Contendrá los objetos operacion a ejecutar.
     */
    private final HashMap<String, RestOperationInterface> operationsMap;


    
/*
 * RECURSO COMPARTIDO ENTRE LOS DIFERENTES HILOS DE EJECUCIÓN.
 * CONTIENE LOS MÉTODOS SOBRE EL RECURSO.
 */
    private ManagerObjectInterface managerObject; 

    public OperationsManager(ManagerObjectInterface manager){
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
     */
    public boolean execute(PrintWriter pw, String line, UserDataThread context){
        String [] args = line.split(" ");  //separamos en líneas
        
        try{
            String verb = args[0]; 
            RestOperationInterface operation = operationsMap.get(verb);

            if (operation == null){
                pw.println("Error, debes pasar un comando válido");
                pw.flush();
                return false;
            }

            String [] operationsArgs = new String[args.length - 1];  //copiamos sólo argumentos.
            System.arraycopy(args, 1,  operationsArgs, 0, args.length - 1);
            //Ejecutamos la operación, pasamos su flujo de salida, los argumentos y el recurso compartido
            return(operation.execute(pw, operationsArgs, getManagerObject(), context));


        }catch(ArrayIndexOutOfBoundsException e){
            pw.println("Error, debes pasar la acción");
            pw.flush();
            return false;
        }
       
    }

    private ManagerObjectInterface getManagerObject() {
        return managerObject;
    }
}
