package server.logic;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


import server.interfaces.ManagerObjectInterface;

public class UserManager<T> implements ManagerObjectInterface<T>{
    private AtomicInteger automaticId;
    private List<User> userList;

    public UserManager(){
        automaticId = new AtomicInteger(0);
        userList = new ArrayList<User>();
     //   initialize();
    }

    @Override
    synchronized public void Add(T u) {
        // TODO Auto-generated method stub
       if (u instanceof User){
        User user = (User) u;
        user.setId(automaticId.incrementAndGet());
        userList.add(user);
       }    
    }


    @Override
    synchronized  public T Get(int i) {
        // TODO Auto-generated method stub
        User user = userList
            .stream()
            .filter(u -> u.getId()==i)
            .findFirst()
            .orElse(null);
        return (T)user;
    }


    @Override
    synchronized  public List<T> GetAll() {
        // TODO Auto-generated method stub
       return (List<T>) userList;
    }

    @Override
    synchronized  public T FindByEmail(String email) {
        // TODO Auto-generated method stub
        User user = userList
            .stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst()
            .orElse(null);
        
        return (T) user;
    }

    @Override
    synchronized  public T FindByEmailAndPassw(String email, String pass) {
        // TODO Auto-generated method stub
        User user = userList.stream()
        .filter(
            (u) -> u.getEmail().equals(email) && 
                    u.getPasswd().equals(pass))
        .findFirst()
        .orElse(null);

        return (T) user;
    }

    
    
   /*  private void initialize(){
        userList.add(new User("Santi", "srodenashe@gmail.com", "santi"));
        userList.add(new User("Sonia", "smenadel@gmail.com", "sonia"));
        userList.add(new User("David", "drodehe@gmail.com", "david"));
    }
*/
    
    
}
