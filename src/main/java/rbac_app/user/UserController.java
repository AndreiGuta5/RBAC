package rbac_app.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jvnet.hk2.annotations.Service;

@Service
@Path("user")
public class UserController {
    private final static ObjectMapper om = new ObjectMapper();

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @POST
    @Path("/get/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserById(@PathParam("id") int id) throws JsonProcessingException {
        return om.writeValueAsString(userService.readUser(id));
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(String user) throws JsonProcessingException {
        MyUser userFromJson = om.readValue(user, MyUser.class);
        userService.createUser(userFromJson);
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(String user) throws JsonProcessingException {
        MyUser userFromJson = om.readValue(user, MyUser.class);
        userService.updateUser(userFromJson);
    }

    @POST
    @Path("/delete/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void deleteUser(@PathParam("id") int userID) throws JsonProcessingException {
        userService.deleteUser(userID);
    }

    @POST
    @Path("/allUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() throws JsonProcessingException {
        return om.writeValueAsString(userService.getUsers());
    }

}
