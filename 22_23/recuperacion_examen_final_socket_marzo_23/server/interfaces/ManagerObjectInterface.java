package server.interfaces;

import java.util.List;

/*
 * Interface con los métodos de los objetos
 * pojo del recurso compartido por los hilos.
 * Implementa los métodos genéricos.
 * 
 * Esta interfaz, define qué métodos debe contener con objetos genéricos.
 * No sabemos qué tipo de objetos deberá tener, por tanto lo hacemos genérico.
 */
public interface ManagerObjectInterface<T> {
   public void  Add(T o); 
   public T Get(int i);
   public List<T> GetAll();
   public T FindByEmail(String email);
   public T FindByEmailAndPassw(String email, String pass);
    
}
