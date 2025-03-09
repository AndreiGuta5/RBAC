package rbac_app;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.io.IOException;
import java.net.URI;

import rbac_app.rbac.*;
import rbac_app.role.*;
import rbac_app.user.*;

public class Main {
    private static final String BASE_URI = "http://0.0.0.0:7070/";

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.printf("Jersey app started with endpoints available at %s%nType 'q' to stop it!!", BASE_URI);
        while (true) {
            if (System.in.read() == 'q') {
                break;
            }
        }
        server.shutdownNow();
    }

    private static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("rbac_app");
        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();
        ServiceLocatorUtilities.addClasses(locator,
                UserController.class,
                UserService.class,
                UserDAO.class,
                RoleController.class,
                RoleService.class,
                RoleDAO.class,
                RbacController.class,
                RbacService.class,
                RbacDAO.class,
                EntityManagerInstance.class
        );
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc, locator);
    }

}
