package rbac_app.rbac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jvnet.hk2.annotations.Service;

@Service
@Path("rbac")
public class RbacController {
    private final static ObjectMapper om = new ObjectMapper();
    private final RbacService rbacService;
    @Inject
    public RbacController(RbacService rbacService){
        this.rbacService = rbacService;
    }

    @POST
    @Path("allUsers")
    @Produces(MediaType.TEXT_PLAIN)
    public String getRolesForAllUsers() throws JsonProcessingException{
        return om.writeValueAsString(rbacService.getRbacItems());
    }
    @POST
    @Path("addRoles")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addRolesToUser(String rbac) throws JsonProcessingException {
        RbacItem rbacItemFromJson = om.readValue(rbac, RbacItem.class);
        rbacService.addRoleToUser(rbacItemFromJson);
    }
    @POST
    @Path("deleteRole")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteRolesFromUser(String rbac) throws JsonProcessingException{
        RbacItem rbacItemFromJson = om.readValue(rbac, RbacItem.class);
        rbacService.deleteRoleFromUser(rbacItemFromJson);
    }
    @POST
    @Path("report")
    @Produces(MediaType.TEXT_PLAIN)
    public String getReport() throws JsonProcessingException{
        return om.writeValueAsString(rbacService.createCounterRolesReport());
    }
}
