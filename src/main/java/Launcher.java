import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by nikolaykombarov on 19.03.17.
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        String webappDirLocation = "src/main/webapp/";

        Server server = new Server(8080);
        WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);

        root.setParentLoaderPriority(true);

        server.setHandler(root);

        server.start();
        server.join();
    }

}
