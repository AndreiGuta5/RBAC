package rbac_app.role;

import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

import java.util.Collection;

@Service
public class RoleService {
    @Inject
    private RoleDAO roleDAO;

    public void createRole(Role role) {
        if (!roleExist(role.getName()))
            roleDAO.createRole(role);
        else {
            throw new RuntimeException("Role exists");
        }

    }

    public void updateRole(Role role) {
        var roleToUpdate = roleDAO.readRole(role.getId());

        if (roleToUpdate != null) {
            roleDAO.updateRole(role);
        }
        else {
            throw new RuntimeException("Role not found");
        }

    }

    public void deleteRole(int id) {
        var role = roleDAO.readRole(id);
        if (role != null) {
            roleDAO.deleteRole(id);
        }
        else {
            throw new RuntimeException("Role doesn't exists");
        }
    }

    public Role readRole(int id) {
        var role = roleDAO.readRole(id);

        if (role != null) {
            return role;
        }
        throw new RuntimeException("Role not found");
    }

    public Collection<Role> getRoles() {
        return roleDAO.getRoles();
    }

    private boolean roleExist(String name) {
        return roleDAO.getRoleByName(name) != null;
    }
}
