package rbac_app.user;

import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import java.util.Collection;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Inject
    public UserService(UserDAO userDao) {
        this.userDAO = userDao;
    }

    public void createUser(MyUser MyUser){
        if(!userExist(MyUser.getEmail())) {
            userDAO.createUser(MyUser);
        }
        else {
            throw new RuntimeException("MyUser exists");
        }
    }

    public void updateUser(MyUser MyUser){
        var userToUpdate = userDAO.readUser(MyUser.getId());

        if(userToUpdate != null) {
            userDAO.updateUser(MyUser);
        }
        else {
            throw new RuntimeException("MyUser not found");
        }
    }
    public MyUser readUser(int id){
        var MyUser = userDAO.readUser(id);

        if (MyUser != null)
        {
            return MyUser;
        }
        throw new RuntimeException("MyUser not found");
    }
    public void deleteUser(int id){
        var MyUser = userDAO.readUser(id);
        if(MyUser != null){
            userDAO.deleteUser(id);
        }
        else {
            throw new RuntimeException("MyUser doesn't exists");
        }
    }

    public Collection<MyUser> getUsers(){
        return userDAO.getUsers();
    }
    private boolean userExist(String email){
        return userDAO.getUserByEmail(email) != null;
    }
}
