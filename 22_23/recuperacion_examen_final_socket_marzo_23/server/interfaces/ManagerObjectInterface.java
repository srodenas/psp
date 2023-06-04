package server.interfaces;

import java.util.List;

/*
 *VERSIÓN DE Santiago Rodenas Herráiz, para PSP 22-23

 * Interface con los métodos de los objetos
 * pojo del recurso compartido por los hilos.
 * Implementa los métodos genéricos.
 * 
 * Esta interfaz, define qué métodos debe contener con objetos genéricos.
 * No sabemos qué tipo de objetos deberá tener, por tanto lo hacemos genérico.
 * QUE QUEDE CLARO, QUE ESTA INTERFAZ INDEPENDIZA DEL OBJETO A TRATAR. PARA ELLO, UTILIZAMOS
 * LOS GENÉRICOS
 */
public interface ManagerObjectInterface<T> {

   //Inserta un objeto genérico
   public void  Add(T o);      
   
   //Devuelve un objeto genérico
   public T Get(int i);      
   
   //Devuelve la lista de objetos genéricos
   public List<T> GetAll();  

   //Devuelve un objeto genérico, dado su email.
   public T FindByEmail(String email);

   //Encuentra un objetivo genérico, dado su email y pass.
   public T FindByEmailAndPassw(String email, String pass);
    
}
