package rbac_app.role;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jvnet.hk2.annotations.Service;

@Service
@Path("role")
public class RoleController {
    private final static ObjectMapper om = new ObjectMapper();
    @Inject
    private RoleService roleService;
    @POST
    @Path("/get/{Id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRoleByName(@PathParam("Id") int id) throws JsonProcessingException {
        return om.writeValueAsString(roleService.readRole(id));
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createRole(String role) throws JsonProcessingException {
        Role roleFromJson = om.readValue(role, Role.class);
        roleService.createRole(roleFromJson);
    }
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRole(String role) throws JsonProcessingException {
        Role roleFromJson = om.readValue(role, Role.class);
        roleService.updateRole(roleFromJson);
    }

    @POST
    @Path("delete/{Id}")
    @Produces(MediaType.TEXT_PLAIN)
    public void deleteRole(@PathParam("Id") int id) throws JsonProcessingException {
        roleService.deleteRole(id);
    }

    @POST
    @Path("/allRoles")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRoles() throws JsonProcessingException {
        return om.writeValueAsString(roleService.getRoles());
    }

}
