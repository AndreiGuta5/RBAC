package rbac_app.rbac;

import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import rbac_app.role.Role;
import rbac_app.role.RoleDAO;
import rbac_app.user.UserDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class RbacService {

    private final RbacDAO rbacDAO;
    private final UserDAO userDB;
    private final RoleDAO roleDB;
    @Inject
    public RbacService(RbacDAO rbacDAO, UserDAO userDB, RoleDAO roleDB) {
        this.rbacDAO = rbacDAO;
        this.userDB = userDB;
        this.roleDB = roleDB;
    }
    public void addRoleToUser(RbacItem rbacItem){
        if(!userExist(rbacItem.getUser().getId()) || !roleExist(rbacItem.getRole().getId())){
            throw new RuntimeException("User or Role not found");
        }
        else if(rbacItemExist(rbacItem)){
            throw new RuntimeException("RbacItem already exist");
        }
        rbacDAO.createRbacItem(rbacItem);
    }

  public void deleteRoleFromUser(RbacItem rbacItem) {
        if(!roleExist(rbacItem.getRole().getId())){
            throw new RuntimeException("Role doesn't exist");
        }
        rbacDAO.deleteRoleFromRbacItem(rbacItem);
   }

   public HashMap<String,List<Role>> getRbacItems(){
       HashMap<String,List<Role>> rbac_items = new HashMap<>();
       var rbacItems = rbacDAO.getRbacItems();

       rbacItems.forEach(rbacItem ->{
               var roles_name = getRbacItemRoles(rbacItem.getUser().getId());
            rbac_items.put(rbacItem.getUser().getName(),roles_name);
       });
       return rbac_items;
    }
    public List<Role> getRbacItemRoles(int id) {
        List<Role> roles = new ArrayList<>();
         var roles_id = rbacDAO.getRolesForUser(id);
         for(var role_id : roles_id){
             roles.add(roleDB.readRole(role_id));
         }
         return roles;
    }

  public HashMap<Role, Integer> createCounterRolesReport() {
        HashMap<Role, Integer> report = new HashMap<>();
      var rolesId = rbacDAO.getRbacItemRolesId();

      for(var roleId : rolesId){
          var role = roleDB.readRole(roleId);

          if(report.containsKey(role)) {
              var roleCount = report.get(role);
              report.put(role, roleCount + 1);
          }
          else
              report.put(role, 1);
      }
        return report;
    }

    private boolean userExist(int id){
        var user = userDB.readUser(id);
        return user != null;
    }
    private boolean roleExist(int id){
        var role = roleDB.readRole(id);
        return role != null;
    }
    private boolean rbacItemExist(RbacItem rbacItem){
        return rbacDAO.rbacItemExist(rbacItem);
    }
}
