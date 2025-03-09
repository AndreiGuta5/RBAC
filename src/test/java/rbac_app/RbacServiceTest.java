package rbac_app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rbac_app.rbac.RbacItem;
import rbac_app.rbac.RbacDAO;
import rbac_app.rbac.RbacService;
import rbac_app.role.Role;
import rbac_app.role.RoleDAO;
import rbac_app.user.MyUser;
import rbac_app.user.UserDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class RbacServiceTest {
    private final RbacDAO rbacDAOMock = mock(RbacDAO.class);
    private final UserDAO userDAOMock = mock(UserDAO.class);
    private final RoleDAO roleDAOMock = mock(RoleDAO.class);
    private final RbacItem rbacItemMock =mock(RbacItem.class);
    private final RbacService rbacService = new RbacService(rbacDAOMock, userDAOMock, roleDAOMock);

    @BeforeEach
    public void setup() {
        Role role = new Role(1,"Admin");
        MyUser user = new MyUser(1,"Mario","mario12@email","mario55");
        RbacItem rbacItem = new RbacItem(1,user, role);
        rbacDAOMock.createRbacItem(rbacItem);
    }

    @Test
    public void shouldDeleteRoleFromRbacItem() {
        Role roleDeleted = new Role(1,"Admin");
        MyUser user = new MyUser(1,"Mario","mario12@email","mario55");
        RbacItem rbacItemDeleted = new RbacItem(1,user, roleDeleted);

        when(roleDAOMock.readRole(roleDeleted.getId())).thenReturn(roleDeleted);
        when(rbacItemMock.getRole()).thenReturn(rbacItemDeleted.getRole());
        doNothing().when(rbacDAOMock).deleteRoleFromRbacItem(rbacItemDeleted);
        rbacService.deleteRoleFromUser(rbacItemDeleted);

        verify(rbacDAOMock).deleteRoleFromRbacItem(rbacItemDeleted);
    }

    @Test
    public void shouldAddRolesToUser() {
        Role roleToAdd = new Role(2,"client");
        MyUser user = new MyUser(1,"Mario","mario12@email","mario55");
        RbacItem rbacItemAdded = new RbacItem(2,user, roleToAdd);

        when(roleDAOMock.readRole(roleToAdd.getId())).thenReturn(rbacItemAdded.getRole());
        when(userDAOMock.readUser(user.getId())).thenReturn(rbacItemAdded.getUser());
        when(rbacItemMock.getRole()).thenReturn(rbacItemAdded.getRole());
        when(rbacItemMock.getUser()).thenReturn(rbacItemAdded.getUser());
        rbacService.addRoleToUser(rbacItemAdded);

        verify(rbacDAOMock).createRbacItem(rbacItemAdded);
    }
    @Test
    public void shouldReturnReport(){
        Map<Role, Integer> report = new HashMap<>();
        Role role1 = new Role(1,"Admin");
        Role role2 = new Role(2,"client");
        List<Integer> roles_id = new ArrayList<>();
        roles_id.add(role1.getId());
        roles_id.add(role2.getId());
        report.put(role1,1);
        report.put(role2,1);

        when(rbacDAOMock.getRbacItemRolesId()).thenReturn(roles_id);
        when(roleDAOMock.readRole(role1.getId())).thenReturn(role1);
        when(roleDAOMock.readRole(role2.getId())).thenReturn(role2);
        var resultReport = rbacService.createCounterRolesReport();

        Assertions.assertEquals(report, resultReport);
    }
    @Test
    public void shouldReturnRbacItems(){
        Map<String,List<Role>> allRbacItems =new HashMap<>();
        MyUser user1 = new MyUser(1,"Mario","mario12@email","mario55");
        Role role1 = new Role(1,"Admin");
        Role role2 = new Role(2,"client");
        List<RbacItem> rbacItems = new ArrayList<>();
        RbacItem rbacItem1 = new RbacItem(1,user1,role1);
        RbacItem rbacItem2 = new RbacItem(2,user1,role2);
        rbacItems.add(rbacItem1);
        rbacItems.add(rbacItem2);
        List<Role> roles = new ArrayList<>();
        List<Integer> roles_id = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        roles_id.add(role1.getId());
        roles_id.add(role2.getId());
        allRbacItems.put(user1.getName(),roles);

        when((rbacDAOMock.getRbacItems())).thenReturn(rbacItems);
        when(rbacService.getRbacItemRoles(user1.getId())).thenReturn(roles);
        when(rbacItemMock.getUser()).thenReturn(user1);
        when(rbacDAOMock.getRbacItemRolesId()).thenReturn(roles_id);
        when(roleDAOMock.readRole(role1.getId())).thenReturn(role1);
        when(roleDAOMock.readRole(role2.getId())).thenReturn(role2);
        when(rbacDAOMock.getRolesForUser(user1.getId())).thenReturn(roles_id);
        var resultGetRbacItems = rbacService.getRbacItems();
        Assertions.assertEquals(allRbacItems,resultGetRbacItems);
    }
}
